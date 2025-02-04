package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.TelemetryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.TelemetryResponseDTO;
import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TelemetryService {
    void addTelemetry(TelemetryDTO telemetryDTO, HttpServletRequest request);
    List<TelemetryResponseDTO> getAllTelemetry(String hostname);
}
