package org.vitacare.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.vitacare.dto.specialityDTO.SpecialityDTO;
import org.vitacare.entity.Department;
import org.vitacare.entity.Speciality;
import org.vitacare.mapper.SpecialityMapper;
import org.vitacare.repository.DepartmentRepository;
import org.vitacare.repository.SpecialityRepository;
import org.vitacare.service.SpecialityService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class SpecialityServiceImp implements SpecialityService {
    @Inject
    private SpecialityRepository specialityRepository;
@Inject
    private DepartmentRepository departmentRepository;
    @Override
    public List<SpecialityDTO> findAllDto() {
        List<Speciality> entities = specialityRepository.findAll();
        // 🔹 On mappe directement ici, sans passer par toDtoList()
        return entities.stream()
                .map(SpecialityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpecialityDTO findByIdDto(Long id) {
        Speciality entity = specialityRepository.findById(id);
        return SpecialityMapper.toDto(entity);
    }

    public void create(SpecialityDTO dto) {
        List<Department> departments = departmentRepository.searchByName(dto.getDepartmentName());
        System.out.println("Résultat recherche département : " + departments);
        Department department = departments.isEmpty() ? null : departments.get(0);

        Speciality entity = SpecialityMapper.toEntity(dto, department);
        specialityRepository.save(entity);
    }



    public void update(Long id, SpecialityDTO dto) {
        Speciality existing = specialityRepository.findById(id);
        if (existing != null) {
            List<Department> departments = departmentRepository.searchByName(dto.getDepartmentName());
            Department department = departments.isEmpty() ? null : departments.get(0);

            existing.setName(dto.getName());
            existing.setDescription(dto.getDescription());
            existing.setCode(dto.getCode());
            existing.setDepartment(department);
            specialityRepository.save(existing);
        }
    }

    public void delete(Long id) {
        specialityRepository.delete(id);
    }

}