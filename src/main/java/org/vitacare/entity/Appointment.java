package org.vitacare.entity;






import jakarta.persistence.*;
import org.vitacare.entity.enums.AppointmentStatus;
import org.vitacare.entity.enums.AppointmentType;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate startAt;
    private LocalDate endAt;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    @Enumerated(EnumType.STRING)
    private AppointmentType type;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToMany(mappedBy = "appointment")
    private List<MedicalNote> medicalNotes;

    private String createdBy;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Appointment() {}

    public Appointment( LocalDate startAt, LocalDate endAt, Doctor doctor, AppointmentStatus status, List<MedicalNote> medicalNotes, String createdBy,Patient patient) {
        this.id = id;
        this.startAt = startAt;
        this.endAt = endAt;
        this.doctor = doctor;
        this.status = status;
        this.medicalNotes = medicalNotes;
        this.patient = patient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDate startAt) {
        this.startAt = startAt;
    }

    public LocalDate getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDate endAt) {
        this.endAt = endAt;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public AppointmentType getType() {
        return type;
    }

    public void setType(AppointmentType type) {
        this.type = type;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<MedicalNote> getMedicalNotes() {
        return medicalNotes;
    }

    public void setMedicalNotes(List<MedicalNote> medicalNotes) {
        this.medicalNotes = medicalNotes;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", status=" + status +
                ", type=" + type +
                ", doctor=" + doctor +
                ", medicalNotes=" + medicalNotes +
                ", createdBy='" + createdBy + '\'' +
                ", patient=" + patient +
                '}';
    }
}
