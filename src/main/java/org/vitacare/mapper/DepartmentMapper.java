package org.vitacare.mapper;

import org.vitacare.dto.department.DepartmentDTO;
import org.vitacare.entity.Department;

public class DepartmentMapper {


    public static DepartmentDTO toDto(Department entity){
        if(entity == null) return null;
        return new DepartmentDTO(entity.getId(),entity.getName());
    }

    public static Department toEntity(DepartmentDTO dto){
        if(dto == null) return null;
        Department department = new Department();
        department.setId(dto.getId() != null ? dto.getId() :0L);
        department.setName(dto.getName());
        return department;
    }


}
