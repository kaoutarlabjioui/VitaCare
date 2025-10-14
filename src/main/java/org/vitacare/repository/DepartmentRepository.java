package org.vitacare.repository;

import org.vitacare.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {

    void save(Department department);
    List<Department> findAll();
    Optional<Department> findById(Long id);
    void delete(Long id);
    List<Department> searchByName(String name);

}
