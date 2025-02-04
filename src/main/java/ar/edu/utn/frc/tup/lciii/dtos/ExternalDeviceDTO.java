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
public class ExternalDeviceDTO {
    private Long id;
    private String hostName;
    private String type;
    private String os;
    private String macAddress;
    private LocalDateTime createdDate;
}
