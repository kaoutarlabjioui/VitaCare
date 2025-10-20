package org.vitacare.repository;

import org.vitacare.entity.Appointment;
import org.vitacare.entity.enums.AppointmentStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface AppointmentRepository {

    Appointment save(Appointment appointment);

    Appointment update(Appointment appointment);

    void delete(int appointmentId);

    Optional<Appointment> findById(int appointmentId);

    List<Appointment> findAll();

    List<Appointment> findByPatientId(int patientId);

    List<Appointment> findUpcomingByPatientId(int patientId);

    List<Appointment> findFilteredAppointments(
            int patientId,
            AppointmentStatus status,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Appointment> findByDoctorId(int doctorId);

    List<Appointment> findByDoctorAndDate(int doctorId, LocalDate date);

    List<Appointment> findActiveDoctorAppointmentsByDate(int doctorId, LocalDate date);

    List<Appointment> findByStatus(AppointmentStatus status);

    List<Appointment> findCompletedByPatientId(int patientId);

    long countByPatientId(int patientId);

    long countByDoctorId(int doctorId);

    boolean existsById(int appointmentId);
}