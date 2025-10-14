package org.vitacare.dto.availability;

import java.time.LocalDateTime;

public class AvailabilityCreateDTO {
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Long doctorId;

    public AvailabilityCreateDTO(LocalDateTime startAt, LocalDateTime endAt, Long doctorId) {
        this.startAt = startAt;
        this.endAt = endAt;
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

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
