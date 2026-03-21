package mk.ukim.finki.wp.schedulero.repository;

import mk.ukim.finki.wp.schedulero.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    List<Appointment> findByEmployeeId(Long employeeId);

    List<Appointment> findByStartTimeBetween(
            LocalDateTime start,
            LocalDateTime end);

    List<Appointment> findByEmployeeIdAndStartTimeBetween(
            Long employeeId,
            LocalDateTime start,
            LocalDateTime end);

    boolean existsByEmployeeIdAndStartTimeLessThanAndEndTimeGreaterThan(
            Long employeeId,
            LocalDateTime endTime,
            LocalDateTime startTime
    );

    List<Appointment> findByCustomerId(Long customerId);

    List<Appointment> findByStatus(String status);

    List<Appointment> findByEmployeeIdOrderByStartTimeAsc(Long employeeId);
}