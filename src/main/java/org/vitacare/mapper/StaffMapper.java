package org.vitacare.mapper;

import jakarta.enterprise.context.ApplicationScoped;

import org.vitacare.dto.user.StaffDTO;
import org.vitacare.entity.Patient;
import org.vitacare.entity.Staff;

@ApplicationScoped
public class StaffMapper {


    public static StaffDTO toDto(Staff staff) {
        if (staff == null) {
            return null;
        }

        StaffDTO dto = new StaffDTO(
                staff.getPosition()

        );


        dto.setId(staff.getId());
        dto.setFirstName(staff.getFirstName());
        dto.setLastName(staff.getLastName());
        dto.setEmail(staff.getEmail());
        dto.setActive(staff.isActive());

        return dto;
    }


    public static Staff toEntity(StaffDTO dto) {
        if (dto == null) {
            return null;
        }

        Staff staff = new Staff();
        staff.setId(dto.getId());
        staff.setFirstName(dto.getFirstName());
        staff.setLastName(dto.getLastName());
        staff.setEmail(dto.getEmail());

        
        staff.setPosition(dto.getPosition());


        return staff;
    }
}
