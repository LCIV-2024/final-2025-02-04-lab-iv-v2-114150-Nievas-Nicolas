package ar.edu.utn.frc.tup.lciii.repository;

import ar.edu.utn.frc.tup.lciii.enums.DeviceType;
import ar.edu.utn.frc.tup.lciii.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {
    boolean existsByMacAddress(String macAddress);
    List<Device> findByType(DeviceType type);
}
