package org.vitacare.repository.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.vitacare.entity.Speciality;
import org.vitacare.repository.SpecialityRepository;

import java.util.List;

@ApplicationScoped
public class SpecialityRepositoryImp implements SpecialityRepository {
  @Inject
  private EntityManager em;

    public List<Speciality> findAll(){
        TypedQuery<Speciality> query = em.createQuery("SELECT s FROM Speciality s ",Speciality.class);
        return query.getResultList();
    }

    public Speciality findById(Long id){
        return em.find(Speciality.class,id);
    }

    public void save(Speciality speciality) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            if (speciality.getId() == null) {
                em.persist(speciality);
            } else {
                em.merge(speciality);
            }

            tx.commit();
            System.out.println(" Speciality saved : " + speciality.getName());
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        em.getTransaction().begin();
        Speciality speciality = findById(id);

        if (speciality != null) {
            if (em.contains(speciality)) {
                em.remove(speciality);
            } else {
                Speciality managedSpeciality = em.merge(speciality);
                em.remove(managedSpeciality);
            }
        }
        em.getTransaction().commit();
    }
}
