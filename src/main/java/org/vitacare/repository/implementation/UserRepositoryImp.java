package org.vitacare.repository.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import org.vitacare.entity.User;
import org.vitacare.repository.UserRepository;
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
    @Override
    public User findById(Long id){
     return em.find(User.class,id);

    }




}
