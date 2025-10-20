package org.vitacare.entity.enums;

public enum AppointmentStatus {
    PLANNED,
    DONE,
    CANCELLED;
    public boolean canBeCancelled() {

        return this == PLANNED;
    }

    public boolean isCompleted() {
        return this == DONE;
    }

    public boolean isCancelled() {
        return this == CANCELLED;
    }
}
