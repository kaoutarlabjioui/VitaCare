package org.vitacare.dto.appointment;

import java.time.LocalDate;

public class AppointmentCreateDTO {
    private LocalDate startAt;
    private LocalDate endAt;
    private String status;
    private Long doctorId;
    private Long patientId;

}
