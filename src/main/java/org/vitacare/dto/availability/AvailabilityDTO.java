package org.vitacare.dto.availability;

import java.time.LocalDateTime;

public class AvailabilityDTO {
    private int id;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public AvailabilityDTO(LocalDateTime endAt, LocalDateTime startAt, int id) {
        this.endAt = endAt;
        this.startAt = startAt;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
