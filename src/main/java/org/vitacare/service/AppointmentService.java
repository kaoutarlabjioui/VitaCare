package org.vitacare.service;

import org.vitacare.dto.appointment.AppointmentDTO;
import org.vitacare.entity.enums.AppointmentStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentService {

    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);

    Optional<AppointmentDTO> getById(int appointmentId);


    List<LocalDateTime> getAvailableSlots(
            int doctorId,
            LocalDate date,
            int durationMinutes,
            int bufferMinutes,
            LocalTime lunchStart,
            LocalTime lunchEnd,
            int minLeadHours
    );

    boolean hasOverlap(int doctorId, LocalDateTime startTime, int durationMinutes, int bufferMinutes);

    boolean isDoctorAvailable(int doctorId, LocalDateTime appointmentDateTime);

    void cancelAppointment(int appointmentId);

    List<AppointmentDTO> getPatientUpcomingAppointments(int patientId);

    List<AppointmentDTO> getPatientAllAppointments(int patientId);

    List<AppointmentDTO> getFilteredAppointments(
          int patientId,
            AppointmentStatus status,
            LocalDate startDate,
            LocalDate endDate
    );

    List<AppointmentDTO> getDoctorAppointmentsByDate(int doctorId, LocalDate date);

    void updateAppointmentStatus(int appointmentId, AppointmentStatus status);

    boolean canCancelAppointment(int appointmentId);
}