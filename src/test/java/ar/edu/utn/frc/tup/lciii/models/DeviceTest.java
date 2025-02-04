package ar.edu.utn.frc.tup.lciii.models;

import java.time.LocalDateTime;

import ar.edu.utn.frc.tup.lciii.enums.DeviceType;
import ar.edu.utn.frc.tup.lciii.model.Device;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DeviceTest {

    @Test
    void testDeviceBuilder() {
        Device device = Device.builder()
                .hostName("device123")
                .type(DeviceType.LAPTOP)
                .os("Windows 10")
                .macAddress("00:1A:2B:3C:4D:5E")
                .build();

        assertThat(device).isNotNull();
        assertThat(device.getHostName()).isEqualTo("device123");
        assertThat(device.getType()).isEqualTo(DeviceType.LAPTOP);
        assertThat(device.getOs()).isEqualTo("Windows 10");
        assertThat(device.getMacAddress()).isEqualTo("00:1A:2B:3C:4D:5E");
    }
}
