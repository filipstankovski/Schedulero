package mk.ukim.finki.wp.schedulero.service;

import mk.ukim.finki.wp.schedulero.dto.CreateAppointmentDto;
import mk.ukim.finki.wp.schedulero.model.Appointment;
import mk.ukim.finki.wp.schedulero.model.Customer;
import mk.ukim.finki.wp.schedulero.model.DetailService;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    List<Appointment> findAll();

    Appointment findById(Long id);

    Appointment create(CreateAppointmentDto dto,
                       Customer customer,
                       DetailService service);

    Appointment save(Appointment appointment);

    Appointment accept(Long id);
    Appointment decline(Long id);

    void deleteById(Long id);

    List<Appointment> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    Appointment reschedule(Long id, LocalDateTime newStart);

    List<Appointment> findByCustomerUsername(String username);
}