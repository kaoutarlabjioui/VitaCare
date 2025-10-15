package org.vitacare.repository;

import org.vitacare.entity.Speciality;

import java.util.List;

public interface SpecialityRepository {
        List<Speciality> findAll();
        Speciality findById(Long id);
        void save(Speciality speciality);  
        void delete(Long id);

}
