package org.vitacare.dto.appointment;

import org.vitacare.entity.enums.AppointmentStatus;
import org.vitacare.entity.enums.AppointmentType;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class AppointmentDTO {
    private Integer id;
    private int patientId;
    private int doctorId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private AppointmentStatus status;
    private AppointmentType type;
    private String createdBy;

    private String patientFirstName;
    private String patientLastName;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorSpeciality;
    private String doctorDepartment;


    public AppointmentDTO() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }


    public LocalDateTime getAppointmentDate() {
        return startAt;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.startAt = appointmentDate;
    }

    public int getDuration() {
        if (startAt != null && endAt != null) {
            return (int) ChronoUnit.MINUTES.between(startAt, endAt);
        }
        return 30;
    }

    public void setDuration(int durationMinutes) {
        if (this.startAt != null) {
            this.endAt = this.startAt.plusMinutes(durationMinutes);
        }
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public String getDoctorSpeciality() {
        return doctorSpeciality;
    }

    public void setDoctorSpeciality(String doctorSpeciality) {
        this.doctorSpeciality = doctorSpeciality;
    }

    public String getDoctorDepartment() {
        return doctorDepartment;
    }

    public void setDoctorDepartment(String doctorDepartment) {
        this.doctorDepartment = doctorDepartment;
    }

    public String getPatientFullName() {
        return patientFirstName + " " + patientLastName;
    }

    public String getDoctorFullName() {
        return "Dr. " + doctorFirstName + " " + doctorLastName;
    }

    public LocalDateTime getEndDateTime() {
        return endAt;
    }

    @Override
    public String toString() {
        return "AppointmentDTO{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", duration=" + getDuration() + " min" +
                ", status=" + status +
                '}';
    }
}