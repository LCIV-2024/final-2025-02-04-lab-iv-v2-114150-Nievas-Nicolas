package ar.edu.utn.frc.tup.lciii.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "TELEMETRY")
@EqualsAndHashCode
public class Telemetry {

    @Id
    @SequenceGenerator(name = "telemetry_seq", sequenceName = "TELEMETRY_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "telemetry_seq")
    @Column(name = "ID")
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "HOSTNAME", referencedColumnName = "HOSTNAME", insertable = false, updatable = false)
    private Device device;

    @Column(name = "IP")
    private String ip;

    @Column(name = "DATA_DATE", nullable = false)
    private LocalDateTime dataDate;

    @Column(name = "HOST_DISK_FREE")
    private Double hostDiskFree;

    @Column(name = "CPU_USAGE")
    private Double cpuUsage;

    @Column(name = "MICROPHONE_STATE")
    private String microphoneState;

    @Column(name = "SCREEN_CAPTURE_ALLOWED")
    private Boolean screenCaptureAllowed;

    @Column(name = "AUDIO_CAPTURE_ALLOWED")
    private Boolean audioCaptureAllowed;

    @Column(name = "HOSTNAME")
    private String hostname;

}
