package ar.edu.utn.frc.tup.lciii.models;

import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TelemetryTest {

    @Test
    void testTelemetryBuilder() {
        Telemetry telemetry = Telemetry.builder()
                .id(1L)
                .ip("192.168.1.1")
                .hostDiskFree(100.5)
                .cpuUsage(30.2)
                .microphoneState("ENABLED")
                .screenCaptureAllowed(true)
                .audioCaptureAllowed(false)
                .hostname("device123")
                .build();

        assertThat(telemetry).isNotNull();
        assertThat(telemetry.getIp()).isEqualTo("192.168.1.1");
        assertThat(telemetry.getHostDiskFree()).isEqualTo(100.5);
        assertThat(telemetry.getCpuUsage()).isEqualTo(30.2);
        assertThat(telemetry.getMicrophoneState()).isEqualTo("ENABLED");
        assertThat(telemetry.getScreenCaptureAllowed()).isTrue();
        assertThat(telemetry.getAudioCaptureAllowed()).isFalse();
        assertThat(telemetry.getHostname()).isEqualTo("device123");
    }
}