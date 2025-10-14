package org.vitacare.dto.medicalnote;

import org.vitacare.dto.user.DoctorDTO;
import org.vitacare.dto.user.PatientDTO;

public class MedicalNoteDTO {
    private Long id;
    private String description;
    private DoctorDTO doctor;
    private PatientDTO patient;
}
