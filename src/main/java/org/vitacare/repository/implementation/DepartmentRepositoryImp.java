package org.vitacare.repository.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.vitacare.entity.Department;
import org.vitacare.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class DepartmentRepositoryImp implements DepartmentRepository {
@Inject
    private  EntityManager em;

    public void save(Department department) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            if (department.getId() == null) {
                em.persist(department);
            } else {
                em.merge(department);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        }
    }

   public List<Department> findAll(){

       TypedQuery<Department> query = em.createQuery("SELECT d FROM Department d", Department.class);
       return query.getResultList();

   }

   public Optional<Department> findById(Long id){
        Department department = em.find(Department.class ,id);
        return Optional.ofNullable(department);
   }

   public void delete(Long id){
        em.getTransaction().begin();
        Department department = em.find(Department.class,id);
        if(department != null) {
            em.remove(department);
        }
        em.getTransaction().commit();
   }

   public List<Department> searchByName(String name){
        TypedQuery<Department> query = em.createQuery("SELECT d FROM Department d WHERE LOWER(d.name) LIKE LOWER(:name)",Department.class);
        query.setParameter("name","%" + name + "%");
        return query.getResultList();
   }

}
