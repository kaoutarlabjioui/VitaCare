package org.vitacare.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.vitacare.dto.department.DepartmentDTO;
import org.vitacare.entity.Department;
import org.vitacare.mapper.DepartmentMapper;
import org.vitacare.repository.DepartmentRepository;
import org.vitacare.service.DepartmentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class DepartmentServiceImp implements DepartmentService {

    @Inject
    private DepartmentRepository departmentRepository;

    public void save(DepartmentDTO departmentDto){
        Department departmentEntity = DepartmentMapper.toEntity(departmentDto);
        departmentRepository.save(departmentEntity);
    }

    @Override
    public List<DepartmentDTO> getAll() {
        return departmentRepository.findAll().stream().map(DepartmentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<DepartmentDTO> getById(Long id) {
        return departmentRepository.findById(id).map(DepartmentMapper::toDto);
    }

    public void delete(Long id){
        departmentRepository.delete(id);
    }
  public  List<DepartmentDTO> searchByName(String name){
        return departmentRepository.searchByName(name).stream().map(DepartmentMapper::toDto).collect(Collectors.toList());

    }
}
