package org.vitacare.dto.appointment;

import org.vitacare.dto.user.DoctorDTO;
import org.vitacare.dto.user.PatientDTO;

import java.time.LocalDate;

public class AppointmentDTO {
    private Long id;
    private LocalDate startAt;
    private LocalDate endAt;
    private String status;
    private DoctorDTO doctor;
    private PatientDTO patient;
}
