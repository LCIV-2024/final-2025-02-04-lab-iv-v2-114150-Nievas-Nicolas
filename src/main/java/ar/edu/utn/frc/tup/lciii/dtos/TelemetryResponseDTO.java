package ar.edu.utn.frc.tup.lciii.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelemetryResponseDTO {
    private String ip;
    private LocalDateTime dataDate;
    private Double hostDiskFree;
    private String microphoneState;
    private Boolean screenCaptureAllowed;
    private Boolean audioCaptureAllowed;
    private String hostname;
}
