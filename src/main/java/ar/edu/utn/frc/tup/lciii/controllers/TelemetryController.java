package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.TelemetryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.TelemetryResponseDTO;
import ar.edu.utn.frc.tup.lciii.service.TelemetryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/telemetry")
public class TelemetryController {

    private final TelemetryService telemetryService;

    @PostMapping
    public ResponseEntity<Void> addTelemetry(@RequestBody TelemetryDTO telemetryDTO, HttpServletRequest request) {
        telemetryService.addTelemetry(telemetryDTO, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<TelemetryResponseDTO>> getAllTelemetry(
            @RequestParam(required = false) String hostname) { // Parámetro opcional
        List<TelemetryResponseDTO> telemetryList = telemetryService.getAllTelemetry(hostname);
        return ResponseEntity.ok(telemetryList);
    }
}