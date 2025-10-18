package org.vitacare.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.vitacare.dto.specialityDTO.SpecialityDTO;
import org.vitacare.entity.Department;
import org.vitacare.entity.Speciality;

@ApplicationScoped
public class SpecialityMapper {

    public static SpecialityDTO toDto(Speciality entity){
        if(entity == null) return null;
        return new SpecialityDTO(entity.getId(),entity.getCode(),entity.getDescription(),entity.getName(),entity.getDepartment().getName());
    }

    public static Speciality toEntity(SpecialityDTO dto, Department department){
        Speciality speciality = new Speciality();
        speciality.setId(dto.getId());
        speciality.setName(dto.getName());
        speciality.setDescription(dto.getDescription());
        speciality.setCode(dto.getCode());
        speciality.setDepartment(department);
        return speciality;
    }

    public static Speciality toEntity(SpecialityDTO dto) {
        if (dto == null) return null;
        Speciality speciality = new Speciality();
        speciality.setId(dto.getId());
        speciality.setName(dto.getName());
        speciality.setDescription(dto.getDescription());
        speciality.setCode(dto.getCode());

        return speciality;
    }



}
