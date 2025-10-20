package org.vitacare.repository.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.vitacare.entity.Appointment;
import org.vitacare.entity.enums.AppointmentStatus;
import org.vitacare.repository.AppointmentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@ApplicationScoped
public class AppointmentRepositoryImp implements AppointmentRepository {
    @Inject
    private EntityManager entityManager;

    @Override
    public Appointment save(Appointment appointment) {
        entityManager.persist(appointment);
        entityManager.flush();
        return appointment;
    }

    @Override
    public Appointment update(Appointment appointment) {
        return entityManager.merge(appointment);
    }

    @Override
    public void delete(int appointmentId) {
        Optional<Appointment> appointment = findById(appointmentId);
        if (appointment.isPresent()) {
            entityManager.remove(entityManager.contains(appointment.get()) ?
                    appointment.get() : entityManager.merge(appointment.get()));
        }
    }

    @Override
    public Optional<Appointment> findById(int appointmentId) {
        try {
            Appointment appointment = entityManager.find(Appointment.class, appointmentId);
            return Optional.ofNullable(appointment);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Appointment> findAll() {
        TypedQuery<Appointment> query = entityManager.createQuery(
                "SELECT a FROM Appointment a ORDER BY a.startAt DESC",
                Appointment.class
        );
        return query.getResultList();
    }

    @Override
    public List<Appointment> findByPatientId(int patientId) {
        TypedQuery<Appointment> query = entityManager.createQuery(
                "SELECT a FROM Appointment a WHERE a.patient.id = :patientId ORDER BY a.startAt DESC",
                Appointment.class
        );
        query.setParameter("patientId", patientId);
        return query.getResultList();
    }

    @Override
    public List<Appointment> findUpcomingByPatientId(int patientId) {
        TypedQuery<Appointment> query = entityManager.createQuery(
                "SELECT a FROM Appointment a " +
                        "WHERE a.patient.id = :patientId " +
                        "AND a.status = org.vitacare.entity.enums.AppointmentStatus.PLANNED " +
                        "AND a.startAt > CURRENT_TIMESTAMP " +
                        "ORDER BY a.startAt ASC",
                Appointment.class
        );
        query.setParameter("patientId", patientId);
        return query.getResultList();
    }



    @Override
    public List<Appointment> findFilteredAppointments(
            int patientId,
            AppointmentStatus status,
            LocalDate startDate,
            LocalDate endDate) {

        StringBuilder jpql = new StringBuilder(
                "SELECT a FROM Appointment a WHERE a.patient.id = :patientId"
        );

        if (status != null) {
            jpql.append(" AND a.status = :status");
        }

        if (startDate != null) {
            jpql.append(" AND DATE(a.startAt) >= :startDate");
        }

        if (endDate != null) {
            jpql.append(" AND DATE(a.startAt) <= :endDate");
        }

        jpql.append(" ORDER BY a.startAt DESC");

        TypedQuery<Appointment> query = entityManager.createQuery(jpql.toString(), Appointment.class);
        query.setParameter("patientId", patientId);

        if (status != null) {
            query.setParameter("status", status);
        }

        if (startDate != null) {
            query.setParameter("startDate", startDate);
        }

        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }

        return query.getResultList();
    }

    @Override
    public List<Appointment> findByDoctorId(int doctorId) {
        TypedQuery<Appointment> query = entityManager.createQuery(
                "SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId ORDER BY a.startAt DESC",
                Appointment.class
        );
        query.setParameter("doctorId", doctorId);
        return query.getResultList();
    }

    @Override
    public List<Appointment> findByDoctorAndDate(int doctorId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        TypedQuery<Appointment> query = entityManager.createQuery(
                "SELECT a FROM Appointment a " +
                        "WHERE a.doctor.id = :doctorId " +
                        "AND a.startAt >= :startOfDay " +
                        "AND a.startAt <= :endOfDay " +
                        "ORDER BY a.startAt ASC",
                Appointment.class
        );
        query.setParameter("doctorId", doctorId);
        query.setParameter("startOfDay", startOfDay);
        query.setParameter("endOfDay", endOfDay);

        return query.getResultList();
    }


    @Override
    public List<Appointment> findActiveDoctorAppointmentsByDate(int doctorId, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        TypedQuery<Appointment> query = entityManager.createQuery(
                "SELECT a FROM Appointment a " +
                        "WHERE a.doctor.id = :doctorId " +
                        "AND a.startAt >= :startOfDay " +
                        "AND a.startAt <= :endOfDay " +
                        "AND a.status != org.vitacare.entity.enums.AppointmentStatus.CANCELLED " +
                        "ORDER BY a.startAt ASC",
                Appointment.class
        );
        query.setParameter("doctorId", doctorId);
        query.setParameter("startOfDay", startOfDay);
        query.setParameter("endOfDay", endOfDay);

        return query.getResultList();
    }

    @Override
    public List<Appointment> findByStatus(AppointmentStatus status) {
        TypedQuery<Appointment> query = entityManager.createQuery(
                "SELECT a FROM Appointment a WHERE a.status = :status ORDER BY a.startAt DESC",
                Appointment.class
        );
        query.setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public List<Appointment> findCompletedByPatientId(int patientId) {
        TypedQuery<Appointment> query = entityManager.createQuery(
                "SELECT a FROM Appointment a " +
                        "WHERE a.patient.id = :patientId " +
                        "AND a.status = org.vitacare.entity.enums.AppointmentStatus.DONE " +
                        "ORDER BY a.startAt DESC",
                Appointment.class
        );
        query.setParameter("patientId", patientId);
        return query.getResultList();
    }

    @Override
    public long countByPatientId(int patientId) {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(a) FROM Appointment a WHERE a.patient.id = :patientId",
                    Long.class
            );
            query.setParameter("patientId", patientId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }

    @Override
    public long countByDoctorId(int doctorId) {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(a) FROM Appointment a WHERE a.doctor.id = :doctorId",
                    Long.class
            );
            query.setParameter("doctorId", doctorId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return 0;
        }
    }

    @Override
    public boolean existsById(int appointmentId) {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(a) FROM Appointment a WHERE a.id = :id",
                    Long.class
            );
            query.setParameter("id", appointmentId);
            return query.getSingleResult() > 0;
        } catch (NoResultException e) {
            return false;
        }
    }



}
