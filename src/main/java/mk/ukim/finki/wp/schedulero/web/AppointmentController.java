package mk.ukim.finki.wp.schedulero.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.schedulero.dto.CreateAppointmentDto;
import mk.ukim.finki.wp.schedulero.dto.DisplayAppointmentDto;
import mk.ukim.finki.wp.schedulero.model.Appointment;
import mk.ukim.finki.wp.schedulero.model.Customer;
import mk.ukim.finki.wp.schedulero.model.DetailService;
import mk.ukim.finki.wp.schedulero.repository.CustomerRepository;
import mk.ukim.finki.wp.schedulero.repository.DetailServiceRepository;
import mk.ukim.finki.wp.schedulero.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final CustomerRepository customerRepository;
    private final DetailServiceRepository serviceRepository;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateAppointmentDto dto,
                                    @AuthenticationPrincipal UserDetails userDetails) {

        Customer customer = customerRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Customer profile not found for user: "
                        + userDetails.getUsername()));

        DetailService service = serviceRepository.findById(dto.serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Appointment appointment = appointmentService.create(dto, customer, service);

        return ResponseEntity.ok(DisplayAppointmentDto.from(appointment));
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<DisplayAppointmentDto> accept(@PathVariable Long id) {
        return ResponseEntity.ok(DisplayAppointmentDto.from(appointmentService.accept(id)));
    }

    @PostMapping("/{id}/decline")
    public ResponseEntity<DisplayAppointmentDto> decline(@PathVariable Long id) {
        return ResponseEntity.ok(DisplayAppointmentDto.from(appointmentService.decline(id)));
    }

    @PostMapping("/{id}/reschedule")
    public ResponseEntity<?> reschedule(@PathVariable Long id,
                                        @RequestBody CreateAppointmentDto dto) {
        try {
            Appointment a = appointmentService.reschedule(id, dto.startTime);
            return ResponseEntity.ok(DisplayAppointmentDto.from(a));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<DisplayAppointmentDto>> findAll(
            @AuthenticationPrincipal UserDetails userDetails) {

        boolean isBusiness = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_BUSINESS"));

        List<Appointment> appointments = isBusiness
                ? appointmentService.findAll()
                : appointmentService.findByCustomerUsername(userDetails.getUsername());

        return ResponseEntity.ok(appointments.stream()
                .map(DisplayAppointmentDto::from)
                .toList());
    }
}