package mk.ukim.finki.wp.schedulero.service;

import mk.ukim.finki.wp.schedulero.dto.CreateAppointmentDto;
import mk.ukim.finki.wp.schedulero.model.Appointment;
import mk.ukim.finki.wp.schedulero.model.Customer;
import mk.ukim.finki.wp.schedulero.model.DetailService;
import mk.ukim.finki.wp.schedulero.model.Employee;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    List<Appointment> findAll();

    Appointment findById(Long id);

    Appointment create(CreateAppointmentDto dto,
                       Customer customer,
                       Employee employee,
                       DetailService service);

    Appointment save(Appointment appointment);

    Appointment accept(Long id);
    Appointment decline(Long id);

    void deleteById(Long id);

    List<Appointment> findByEmployeeId(Long employeeId);

    List<Appointment> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Appointment> findByEmployeeIdAndStartTimeBetween(
            Long employeeId,
            LocalDateTime start,
            LocalDateTime end
    );

    Appointment reschedule(Long id, LocalDateTime newStart);
}