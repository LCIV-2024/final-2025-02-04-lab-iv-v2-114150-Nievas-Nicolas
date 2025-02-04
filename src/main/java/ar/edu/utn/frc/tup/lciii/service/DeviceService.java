package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.DeviceDTO;
import ar.edu.utn.frc.tup.lciii.enums.DeviceType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeviceService {
    void addDevice(DeviceDTO deviceDTO);
    List<DeviceDTO> getDevicesByType(DeviceType type);
    void saveConsumedDevices();
}
