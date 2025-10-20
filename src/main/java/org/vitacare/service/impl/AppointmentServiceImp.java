package org.vitacare.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.vitacare.dto.appointment.AppointmentDTO;
import org.vitacare.entity.Appointment;
import org.vitacare.entity.Doctor;
import org.vitacare.entity.Patient;
import org.vitacare.entity.enums.AppointmentStatus;
import org.vitacare.mapper.AppointmentMapper;
import org.vitacare.repository.AppointmentRepository;
import org.vitacare.repository.UserRepository;
import org.vitacare.service.AppointmentService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class AppointmentServiceImp implements AppointmentService {
    @Inject
    private AppointmentRepository appointmentRepository;

    @Inject
    private UserRepository userRepository;

    private static final int SLOT_DURATION_MINUTES = 15;
    private static final LocalTime WORK_START = LocalTime.of(8, 0);
    private static final LocalTime WORK_END = LocalTime.of(17, 0);

    @Override
    @Transactional
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {

        Optional<Doctor> doctorOptional = userRepository.findById((long) appointmentDTO.getDoctorId())
                .filter(user -> user instanceof Doctor)
                .map(user -> (Doctor) user);

        Optional<Patient> patientOptional = userRepository.findById((long) appointmentDTO.getPatientId())
                .filter(user -> user instanceof Patient)
                .map(user -> (Patient) user);

        if (!doctorOptional.isPresent() || !patientOptional.isPresent()) {
            throw new IllegalArgumentException("Médecin ou patient introuvable");
        }

        Doctor doctor = doctorOptional.get();
        Patient patient = patientOptional.get();


        Appointment appointment = AppointmentMapper.toEntity(appointmentDTO, doctor, patient);
        appointment.setStatus(AppointmentStatus.PLANNED);


        Appointment savedAppointment = appointmentRepository.save(appointment);

        return AppointmentMapper.toDTO(savedAppointment);
    }

    @Override
    public Optional<AppointmentDTO> getById(int appointmentId) {
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        return appointment.map(AppointmentMapper::toDTO);
    }

    @Override
    public List<LocalDateTime> getAvailableSlots(
            int doctorId,
            LocalDate date,
            int durationMinutes,
            int bufferMinutes,
            LocalTime lunchStart,
            LocalTime lunchEnd,
            int minLeadHours) {

        List<LocalDateTime> availableSlots = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minDateTime = now.plusHours(minLeadHours);

        List<AppointmentDTO> doctorAppointments = getDoctorAppointmentsByDate(doctorId, date);

        LocalDateTime slotStart = date.atTime(WORK_START);
        LocalDateTime slotEnd = date.atTime(WORK_END);

        if (date.equals(now.toLocalDate())) {
            slotStart = minDateTime;
        } else if (slotStart.isBefore(minDateTime)) {
            slotStart = minDateTime;
        }

        // Génère tous les créneaux possibles
        LocalDateTime current = slotStart;
        while (current.plusMinutes(durationMinutes).isBefore(slotEnd) ||
                current.plusMinutes(durationMinutes).isEqual(slotEnd)) {


            if (!isInLunchBreak(current.toLocalTime(), durationMinutes, lunchStart, lunchEnd)) {


                if (!hasOverlapWithAppointments(current, durationMinutes, bufferMinutes, doctorAppointments)) {
                    availableSlots.add(current);
                }
            }

            current = current.plusMinutes(SLOT_DURATION_MINUTES);
        }

        return availableSlots;
    }
    @Override
    public boolean hasOverlap(int doctorId, LocalDateTime startTime, int durationMinutes, int bufferMinutes) {
        List<AppointmentDTO> doctorAppointments = getDoctorAppointmentsByDate(
                Math.toIntExact(doctorId),
                startTime.toLocalDate()
        );

        return hasOverlapWithAppointments(startTime, durationMinutes, bufferMinutes, doctorAppointments);
    }


    @Override
    public boolean isDoctorAvailable(int doctorId, LocalDateTime appointmentDateTime) {
        Optional<Doctor> doctorOptional = userRepository.findById((long)doctorId)
                .filter(user -> user instanceof Doctor)
                .map(user -> (Doctor) user);

        if (!doctorOptional.isPresent()) {
            return false;
        }

        Doctor doctor = doctorOptional.get();

        // Vérifie que le médecin est actif
        if (!doctor.isActive()) {
            return false;
        }

        // Récupère les rendez-vous du médecin pour cette date
        List<AppointmentDTO> dayAppointments = getDoctorAppointmentsByDate(Math.toIntExact(doctorId),
                appointmentDateTime.toLocalDate()
        );

        // Filtre les rendez-vous non annulés qui chevauchent
        return dayAppointments.stream()
                .filter(apt -> apt.getStatus() != AppointmentStatus.CANCELLED)
                .noneMatch(apt -> appointmentDateTime.isBefore(apt.getEndAt()) &&
                        appointmentDateTime.isAfter(apt.getStartAt()));
    }

    @Override
    @Transactional
    public void cancelAppointment(int appointmentId) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentId);

        if (!appointmentOptional.isPresent()) {
            throw new IllegalArgumentException("Rendez-vous introuvable");
        }

        Appointment appointment = appointmentOptional.get();

        if (!appointment.getStatus().canBeCancelled()) {
            throw new IllegalStateException("Ce rendez-vous ne peut pas être annulé");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.update(appointment);
    }
    @Override
    public List<AppointmentDTO> getPatientUpcomingAppointments(int patientId) {
        List<Appointment> upcomingAppointments = appointmentRepository.findUpcomingByPatientId(patientId);
        return upcomingAppointments.stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getPatientAllAppointments(int patientId) {
        List<Appointment> allAppointments = appointmentRepository.findByPatientId(patientId);
        return allAppointments.stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDTO> getFilteredAppointments(
            int patientId,
            AppointmentStatus status,
            LocalDate startDate,
            LocalDate endDate) {

        List<Appointment> filteredAppointments = appointmentRepository.findFilteredAppointments(
                patientId,
                status,
                startDate,
                endDate
        );

        return filteredAppointments.stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }



    @Override
    public List<AppointmentDTO> getDoctorAppointmentsByDate(int doctorId, LocalDate date) {
        List<Appointment> appointments = appointmentRepository.findByDoctorAndDate(doctorId, date);
        return appointments.stream()
                .map(AppointmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateAppointmentStatus(int appointmentId, AppointmentStatus status) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentId);

        if (!appointmentOptional.isPresent()) {
            throw new IllegalArgumentException("Rendez-vous introuvable");
        }

        Appointment appointment = appointmentOptional.get();
        appointment.setStatus(status);
        appointmentRepository.update(appointment);
    }

    @Override
    public boolean canCancelAppointment(int appointmentId) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentId);

        if (!appointmentOptional.isPresent()) {
            return false;
        }

        Appointment appointment = appointmentOptional.get();

        // Vérifie que le statut permet l'annulation
        if (!appointment.getStatus().canBeCancelled()) {
            return false;
        }

        // Vérifie le délai de 12 heures
        LocalDateTime now = LocalDateTime.now();
        long hoursUntil = ChronoUnit.HOURS.between(now, appointment.getStartAt());

        return hoursUntil >= 12;
    }
    // ============ MÉTHODES PRIVÉES ============

    private boolean isInLunchBreak(LocalTime startTime, int durationMinutes,
                                   LocalTime lunchStart, LocalTime lunchEnd) {
        LocalTime endTime = startTime.plusMinutes(durationMinutes);
        return (startTime.isBefore(lunchEnd) && endTime.isAfter(lunchStart));
    }

    private boolean hasOverlapWithAppointments(LocalDateTime startTime, int durationMinutes,
                                               int bufferMinutes, List<AppointmentDTO> appointments) {
        LocalDateTime bufferStart = startTime.minusMinutes(bufferMinutes);
        LocalDateTime bufferEnd = startTime.plusMinutes(durationMinutes + bufferMinutes);

        return appointments.stream()
                .filter(apt -> apt.getStatus() != AppointmentStatus.CANCELLED)
                .anyMatch(apt -> {
                    LocalDateTime aptStart = apt.getStartAt();
                    LocalDateTime aptEnd = apt.getEndAt();

                    return !(bufferEnd.isBefore(aptStart) || bufferStart.isAfter(aptEnd));
                });
    }

}
