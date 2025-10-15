package org.vitacare.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.vitacare.dto.department.DepartmentDTO;
import org.vitacare.entity.Department;
@ApplicationScoped
public class DepartmentMapper {


    public static DepartmentDTO toDto(Department entity){
        if(entity == null) return null;
        return new DepartmentDTO(entity.getId(),entity.getName(),entity.getDescription(),entity.getCode());
    }

    public static Department toEntity(DepartmentDTO dto){
        if(dto == null) return null;
        Department department = new Department();
        if (dto.getId() != null) {
            department.setId(dto.getId().longValue()); // update
        }
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        department.setCode(dto.getCode());
        return department;
    }


}
