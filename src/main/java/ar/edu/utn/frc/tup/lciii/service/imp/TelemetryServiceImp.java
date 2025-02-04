package ar.edu.utn.frc.tup.lciii.service.imp;

import ar.edu.utn.frc.tup.lciii.dtos.TelemetryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.TelemetryResponseDTO;
import ar.edu.utn.frc.tup.lciii.model.Device;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import ar.edu.utn.frc.tup.lciii.repository.DeviceRepository;
import ar.edu.utn.frc.tup.lciii.repository.TelemetryRepository;
import ar.edu.utn.frc.tup.lciii.service.TelemetryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TelemetryServiceImp implements TelemetryService {

    private final TelemetryRepository telemetryRepository;
    private final DeviceRepository deviceRepository;

    @Override
    public void addTelemetry(TelemetryDTO telemetryDTO, HttpServletRequest request) {
        Device device = deviceRepository.findById(telemetryDTO.getHostname())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Device not found with hostname: " + telemetryDTO.getHostname()));

        Telemetry telemetry = Telemetry.builder()
                .hostname(telemetryDTO.getHostname())
                .ip(getClientIp(request))
                .dataDate(telemetryDTO.getDataDate())
                .hostDiskFree(telemetryDTO.getHostDiskFree())
                .cpuUsage(telemetryDTO.getCpuUsage())
                .microphoneState(telemetryDTO.getMicrophoneState())
                .screenCaptureAllowed(telemetryDTO.getScreenCaptureAllowed())
                .audioCaptureAllowed(telemetryDTO.getAudioCaptureAllowed())
                .build();

        telemetryRepository.save(telemetry);
    }

    @Override
    public List<TelemetryResponseDTO> getAllTelemetry(String hostname) {
        List<Telemetry> telemetryList;

        if (hostname != null && !hostname.isEmpty()) {
            telemetryList = telemetryRepository.findByDevice_HostName(hostname);
        } else {
            telemetryList = telemetryRepository.findAll();
        }

        if (telemetryList.isEmpty()) {
            String message = hostname != null ?
                    "No telemetry data found for hostname: " + hostname :
                    "No telemetry data found";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
        }

        return telemetryList.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private TelemetryResponseDTO convertToResponseDTO(Telemetry telemetry) {
        if (telemetry == null || telemetry.getDevice() == null) {
            throw new IllegalArgumentException("Telemetry or associated Device is null");
        }

        return TelemetryResponseDTO.builder()
                .ip(telemetry.getIp())
                .dataDate(telemetry.getDataDate())
                .hostDiskFree(telemetry.getHostDiskFree())
                .microphoneState(telemetry.getMicrophoneState())
                .screenCaptureAllowed(telemetry.getScreenCaptureAllowed())
                .audioCaptureAllowed(telemetry.getAudioCaptureAllowed())
                .hostname(telemetry.getDevice().getHostName())
                .build();
    }

    private String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");

        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        }

        if (clientIp != null && clientIp.contains(",")) {
            clientIp = clientIp.split(",")[0].trim();
        }

        return clientIp;
    }
}
