package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.DeviceDTO;
import ar.edu.utn.frc.tup.lciii.enums.DeviceType;
import ar.edu.utn.frc.tup.lciii.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DeviceController {
    private final DeviceService deviceService;

    @PostMapping("/device")
    public ResponseEntity<Void> addDevice(@RequestBody DeviceDTO deviceDTO) {
        deviceService.addDevice(deviceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/save-consumed-devices")
    public ResponseEntity<Void> saveConsumedDevices() {
        deviceService.saveConsumedDevices();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/device")
    public ResponseEntity<List<DeviceDTO>> getDevicesByType(
            @RequestParam(required = false) DeviceType type) {
        List<DeviceDTO> devices = deviceService.getDevicesByType(type);
        return ResponseEntity.ok(devices);
    }

    @GetMapping("/device")
    public ResponseEntity<List<DeviceDTO>> getDevicesByCpuUsageRange(
            @RequestParam double lowThreshold,
            @RequestParam double upThreshold) {
        List<DeviceDTO> devices = deviceService.getDevicesByCpuUsageRange(lowThreshold, upThreshold);
        return ResponseEntity.ok(devices);
    }
}