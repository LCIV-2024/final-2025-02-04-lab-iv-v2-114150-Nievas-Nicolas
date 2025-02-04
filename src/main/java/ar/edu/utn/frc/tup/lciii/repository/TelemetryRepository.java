package ar.edu.utn.frc.tup.lciii.repository;

import ar.edu.utn.frc.tup.lciii.model.Telemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelemetryRepository extends JpaRepository<Telemetry, Long> {
    List<Telemetry> findByDevice_HostName(String hostname);
    @Query("SELECT t FROM Telemetry t WHERE t.device.hostName = :hostname ORDER BY t.dataDate DESC")
    List<Telemetry> findLatestTelemetryByDevice(@Param("hostname") String hostname);
}
