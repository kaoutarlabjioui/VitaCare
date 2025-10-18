package org.vitacare.dto.availability;

import org.vitacare.entity.enums.AvailabilityStatus;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

        public class AvailabilityDTO {
            private int id;
            private DayOfWeek day;
            private LocalTime startTime;
            private LocalTime endTime;
            private AvailabilityStatus status;
            private String doctorName;

            public AvailabilityDTO() {
            }

            public int getId() { return id; }
            public void setId(int id) { this.id = id; }

            public DayOfWeek getDay() { return day; }
            public void setDay(DayOfWeek day) { this.day = day; }

            public LocalTime getStartTime() { return startTime; }
            public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

            public LocalTime getEndTime() { return endTime; }
            public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

            public AvailabilityStatus getStatus() { return status; }
            public void setStatus(AvailabilityStatus status) { this.status = status; }

            public String getDoctorName() { return doctorName; }
            public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
        }




