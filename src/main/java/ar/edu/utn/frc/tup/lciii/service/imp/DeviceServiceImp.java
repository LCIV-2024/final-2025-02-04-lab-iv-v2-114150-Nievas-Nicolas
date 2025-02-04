package ar.edu.utn.frc.tup.lciii.service.imp;

import ar.edu.utn.frc.tup.lciii.dtos.DeviceDTO;
import ar.edu.utn.frc.tup.lciii.dtos.ExternalDeviceDTO;
import ar.edu.utn.frc.tup.lciii.enums.DeviceType;
import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import ar.edu.utn.frc.tup.lciii.repository.DeviceRepository;
import ar.edu.utn.frc.tup.lciii.repository.TelemetryRepository;
import ar.edu.utn.frc.tup.lciii.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceServiceImp implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final TelemetryRepository telemetryRepository;

    private final RestTemplate restTemplate;
    private static final String EXTERNAL_API_URL = "https://67a106a15bcfff4fabe171b0.mockapi.io/api/v1/device/device";

    @Override
    public void addDevice(DeviceDTO deviceDTO) {
        if (deviceRepository.existsById(deviceDTO.getHostname())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Device with hostname " + deviceDTO.getHostname() + " already exists.");
        }

        if (deviceRepository.existsByMacAddress(deviceDTO.getMacAddress())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Device with MAC address " + deviceDTO.getMacAddress() + " already exists.");
        }

        Device device = Device.builder()
                .hostName(deviceDTO.getHostname())
                .type(DeviceType.valueOf(deviceDTO.getType().toUpperCase()))
                .os(deviceDTO.getOs())
                .macAddress(deviceDTO.getMacAddress())
                .build();

        deviceRepository.save(device);
    }

    @Override
    public List<DeviceDTO> getDevicesByType(DeviceType type) {
        List<Device> devices;

        if (type == null) {
            devices = deviceRepository.findAll();
        } else {
            devices = deviceRepository.findByType(type);
        }

        if (devices.isEmpty()) {
            String message = type == null ?
                    "No devices found in the database" :
                    "No devices found with type: " + type;
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        }

        return devices.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }



    @Override
    public void saveConsumedDevices() {
        ExternalDeviceDTO[] externalDevices = restTemplate.getForObject(EXTERNAL_API_URL, ExternalDeviceDTO[].class);

        if (externalDevices == null || externalDevices.length == 0) {
            throw new RuntimeException("No devices found in the external API");
        }

        List<ExternalDeviceDTO> devices = Arrays.asList(externalDevices);

        List<ExternalDeviceDTO> selectedDevices = selectRandomDevices(devices);

        selectedDevices.forEach(this::saveDevice);
    }

    @Override
    public List<DeviceDTO> getDevicesByCpuUsageRange(double lowThreshold, double upThreshold) {
        if (lowThreshold > upThreshold) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "lowThreshold cannot be greater than upThreshold");
        }

        List<Device> devices = deviceRepository.findAll();

        return devices.stream()
                .filter(device -> {
                    List<Telemetry> latestTelemetry = telemetryRepository.findLatestTelemetryByDevice(device.getHostName());
                    if (!latestTelemetry.isEmpty()) {
                        double cpuUsage = latestTelemetry.get(0).getCpuUsage();
                        return cpuUsage >= lowThreshold && cpuUsage <= upThreshold;
                    }
                    return false;
                })
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }


    private DeviceDTO convertToResponseDTO(Device device) {
        return DeviceDTO.builder()
                .hostname(device.getHostName())
                .type(device.getType().name())
                .os(device.getOs())
                .macAddress(device.getMacAddress())
                .build();
    }

    private List<ExternalDeviceDTO> selectRandomDevices(List<ExternalDeviceDTO> devices) {
        Collections.shuffle(devices);

        int halfSize = devices.size() / 2;

        return devices.stream()
                .limit(halfSize)
                .collect(Collectors.toList());
    }

    private void saveDevice(ExternalDeviceDTO externalDevice) {
        Device device = Device.builder()
                .hostName(externalDevice.getHostName())
                .type(DeviceType.valueOf(externalDevice.getType().toUpperCase()))
                .os(externalDevice.getOs())
                .macAddress(externalDevice.getMacAddress())
                .build();

        deviceRepository.save(device);
    }
}
