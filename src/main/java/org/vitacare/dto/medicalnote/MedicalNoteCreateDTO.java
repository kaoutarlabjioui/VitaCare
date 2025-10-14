package org.vitacare.dto.medicalnote;

public class MedicalNoteCreateDTO {
    private String description;
    private Long doctorId;
    private Long patientId;

    public MedicalNoteCreateDTO(String description, Long patientId, Long doctorId) {
        this.description = description;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
