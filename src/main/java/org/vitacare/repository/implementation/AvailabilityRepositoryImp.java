package org.vitacare.repository.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.vitacare.entity.Availability;
import org.vitacare.entity.Doctor;
import org.vitacare.repository.AvailabilityRepository;

import java.util.List;

@ApplicationScoped
public class AvailabilityRepositoryImp implements AvailabilityRepository {

    @Inject
    private EntityManager em;

    @Override
    public void save(Availability availability) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(availability);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            throw e;
        }
    }

    @Override
    public List<Availability> findByDoctor(Doctor doctor) {
        TypedQuery<Availability> query = em.createQuery(
                "SELECT a FROM Availability a WHERE a.doctor = :doctor ORDER BY a.day, a.startTime",
                Availability.class
        );
        query.setParameter("doctor", doctor);
        return query.getResultList();
    }

    @Override
    public Availability findById(int id) {
        return em.find(Availability.class, id);
    }

    @Override
    public void update(Availability availability) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(availability);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }


}
