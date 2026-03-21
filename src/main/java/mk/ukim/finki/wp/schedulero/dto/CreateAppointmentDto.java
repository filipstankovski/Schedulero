package mk.ukim.finki.wp.schedulero.dto;

import java.time.LocalDateTime;

public class CreateAppointmentDto {

    public Long customerId;
    public Long serviceId;
    public Long employeeId;

    public LocalDateTime startTime;

}