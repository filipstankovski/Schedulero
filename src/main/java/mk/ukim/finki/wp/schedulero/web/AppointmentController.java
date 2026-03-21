package mk.ukim.finki.wp.schedulero.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.wp.schedulero.dto.CreateAppointmentDto;
import mk.ukim.finki.wp.schedulero.dto.DisplayAppointmentDto;
import mk.ukim.finki.wp.schedulero.enums.AppointmentStatus;
import mk.ukim.finki.wp.schedulero.model.Appointment;
import mk.ukim.finki.wp.schedulero.model.Customer;
import mk.ukim.finki.wp.schedulero.model.DetailService;
import mk.ukim.finki.wp.schedulero.model.Employee;
import mk.ukim.finki.wp.schedulero.repository.CustomerRepository;
import mk.ukim.finki.wp.schedulero.repository.DetailServiceRepository;
import mk.ukim.finki.wp.schedulero.repository.EmployeeRepository;
import mk.ukim.finki.wp.schedulero.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final DetailServiceRepository serviceRepository;

    @PostMapping
    public DisplayAppointmentDto create(@RequestBody CreateAppointmentDto dto) {

        Customer customer = customerRepository.findById(dto.customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Employee employee = employeeRepository.findById(dto.employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        DetailService service = serviceRepository.findById(dto.serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Appointment appointment = appointmentService.create(dto, customer, employee, service);

        return DisplayAppointmentDto.from(appointment);
    }



    @PostMapping("/{id}/accept")
    public DisplayAppointmentDto accept(@PathVariable Long id) {
        return DisplayAppointmentDto.from(appointmentService.accept(id));
    }

    @PostMapping("/{id}/decline")
    public DisplayAppointmentDto decline(@PathVariable Long id) {
        return DisplayAppointmentDto.from(appointmentService.decline(id));
    }

    @PostMapping("/{id}/reschedule")
    public DisplayAppointmentDto reschedule(@PathVariable Long id,
                                            @RequestBody CreateAppointmentDto dto) {

        Appointment a = appointmentService.reschedule(id, dto.startTime);
        return DisplayAppointmentDto.from(a);
    }

    @GetMapping
    public List<DisplayAppointmentDto> findAll(
            @RequestParam(required = false) Long employeeId) {

        List<Appointment> appointments;

        if (employeeId != null) {
            appointments = appointmentService.findByEmployeeId(employeeId);
        } else {
            appointments = appointmentService.findAll();
        }

        return appointments.stream()
                .map(DisplayAppointmentDto::from)
                .toList();
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }
}