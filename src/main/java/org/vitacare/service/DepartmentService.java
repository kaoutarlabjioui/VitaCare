package org.vitacare.service;

import org.vitacare.dto.department.DepartmentDTO;
import org.vitacare.entity.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    void save(DepartmentDTO departmentDto);
    List<DepartmentDTO> getAll();
    Optional<DepartmentDTO> getById(Long id);
    void delete(Long id);
    List<DepartmentDTO> searchByName(String name);
}
