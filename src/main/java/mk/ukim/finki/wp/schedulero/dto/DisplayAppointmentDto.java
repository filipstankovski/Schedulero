package mk.ukim.finki.wp.schedulero.dto;

import mk.ukim.finki.wp.schedulero.model.Appointment;

import java.time.LocalDateTime;

public class DisplayAppointmentDto {

    public Long id;
    public String customerName;
    public String employeeName;
    public String serviceName;

    public LocalDateTime startTime;
    public LocalDateTime endTime;

    public String status;

    public static DisplayAppointmentDto from(Appointment a) {
        DisplayAppointmentDto dto = new DisplayAppointmentDto();

        dto.id = a.getId();
        dto.customerName = a.getCustomer().getName();
        dto.employeeName = a.getEmployee().getName();
        dto.serviceName = a.getService().getName();
        dto.startTime = a.getStartTime();
        dto.endTime = a.getEndTime();
        dto.status = a.getStatus().name();

        return dto;
    }
}
