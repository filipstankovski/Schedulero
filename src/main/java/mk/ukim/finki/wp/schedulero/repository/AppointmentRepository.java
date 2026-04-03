package mk.ukim.finki.wp.schedulero.repository;

import mk.ukim.finki.wp.schedulero.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    boolean existsByServiceIdAndStartTimeLessThanAndEndTimeGreaterThan(
            Long serviceId, LocalDateTime end, LocalDateTime start);

    List<Appointment> findByCustomerId(Long customerId);

    List<Appointment> findByCustomer_Username(String username);
}