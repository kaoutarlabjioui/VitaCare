package org.vitacare.repository.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import org.vitacare.entity.Doctor;
import org.vitacare.entity.Patient;
import org.vitacare.entity.Staff;
import org.vitacare.entity.User;
import org.vitacare.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserRepositoryImp implements UserRepository {
    @Inject
    private  EntityManager em;

    @Override
    public User findByEmail(String email) {
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(User user) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(user);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }
    public Optional<User> findById(Long id) {
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }

    public List<Doctor> getAllDoctors() {
        return em.createQuery("SELECT d FROM Doctor d", Doctor.class).getResultList();
    }

    public List<Patient> getAllPatients() {
        return em.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
    }

    public List<Staff> getAllStaff() {
        return em.createQuery("SELECT s FROM Staff s", Staff.class).getResultList();
    }
    @Override
    public Doctor findByUserId(int userId) {
        try {
            
            return em.createQuery("SELECT d FROM Doctor d WHERE d.id = :uid", Doctor.class)
                    .setParameter("uid", userId)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }




}
