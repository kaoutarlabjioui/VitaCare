//package org.vitacare.repository.implementation;
//
//import jakarta.persistence.EntityManager;
//import org.vitacare.entity.Department;
//import org.vitacare.repository.DepartmentRepository;
//
//public class DepartmentRepositoryImp implements DepartmentRepository {
//
//    private final EntityManager em;
//
//    public DepartmentRepositoryImp(EntityManager em){
//        this.em = em ;
//    }
//    public void save(Department departement){
//        em.getTransaction().begin();
//        if(departement.getId() == 0){
//            em.persist(departement);
//        }else{
//            em.merge(departement);
//        }
//    }
//
//}
