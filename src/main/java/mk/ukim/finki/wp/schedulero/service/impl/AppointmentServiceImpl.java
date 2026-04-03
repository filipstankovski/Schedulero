package mk.ukim.finki.wp.schedulero.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.schedulero.dto.CreateAppointmentDto;
import mk.ukim.finki.wp.schedulero.enums.AppointmentStatus;
import mk.ukim.finki.wp.schedulero.exceptions.InvalidAppointmentException;
import mk.ukim.finki.wp.schedulero.model.Appointment;
import mk.ukim.finki.wp.schedulero.model.Customer;
import mk.ukim.finki.wp.schedulero.model.DetailService;
import mk.ukim.finki.wp.schedulero.repository.AppointmentRepository;
import mk.ukim.finki.wp.schedulero.service.AppointmentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment findById(Long id) {
        Optional<Appointment> optional = appointmentRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new InvalidAppointmentException("The Appointment doesn't exist!");
        }
    }

    @Override
    public Appointment create(CreateAppointmentDto dto,
                              Customer customer,
                              DetailService service) {

        Appointment appointment = new Appointment();
        appointment.setCustomer(customer);
        appointment.setService(service);
        appointment.setStartTime(dto.startTime);

        LocalDateTime endTime = dto.startTime.plusMinutes(service.getDurationMinutes());
        appointment.setEndTime(endTime);

        boolean conflict = appointmentRepository
                .existsByServiceIdAndStartTimeLessThanAndEndTimeGreaterThan(
                        service.getId(),
                        endTime,
                        dto.startTime
                );

        if (conflict) {
            throw new InvalidAppointmentException("Time slot already booked!");
        }

        appointment.setStatus(AppointmentStatus.PENDING);
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment accept(Long id) {
        Appointment a = findById(id);
        a.setStatus(AppointmentStatus.ACCEPTED);
        return appointmentRepository.save(a);
    }

    @Override
    public Appointment decline(Long id) {
        Appointment a = findById(id);
        a.setStatus(AppointmentStatus.DECLINED);
        return appointmentRepository.save(a);
    }

    @Override
    public void deleteById(Long id) {
        appointmentRepository.delete(findById(id));
    }

    @Override
    public List<Appointment> findByStartTimeBetween(LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findByStartTimeBetween(start, end);
    }

    @Override
    public Appointment reschedule(Long id, LocalDateTime newStart) {
        Appointment a = findById(id);

        LocalDateTime newEnd = newStart.plusMinutes(a.getService().getDurationMinutes());

        boolean conflict = appointmentRepository
                .existsByServiceIdAndStartTimeLessThanAndEndTimeGreaterThan(
                        a.getService().getId(),
                        newEnd,
                        newStart
                );

        if (conflict) {
            throw new InvalidAppointmentException("Conflict!");
        }

        a.setStartTime(newStart);
        a.setEndTime(newEnd);
        return appointmentRepository.save(a);
    }

    @Override
    public List<Appointment> findByCustomerUsername(String username) {
        return appointmentRepository.findByCustomer_Username(username);
    }
}