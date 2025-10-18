package org.vitacare.mapper;

import org.vitacare.dto.availability.AvailabilityDTO;
import org.vitacare.entity.Availability;
import org.vitacare.entity.Doctor;

public class AvailabilityMapper {

    public static Availability toEntity(AvailabilityDTO dto, Doctor doctor) {
        if (dto == null) return null;

        Availability availability = new Availability();
        availability.setId(dto.getId());
        availability.setDay(dto.getDay());
        availability.setStartTime(dto.getStartTime());
        availability.setEndTime(dto.getEndTime());
        availability.setStatus(dto.getStatus());
        availability.setDoctor(doctor);

        return availability;
    }

    public static AvailabilityDTO toDto(Availability entity) {
        if (entity == null) return null;

        AvailabilityDTO dto = new AvailabilityDTO();
        dto.setId(entity.getId());
        dto.setDay(entity.getDay());
        dto.setStartTime(entity.getStartTime());
        dto.setEndTime(entity.getEndTime());
        dto.setStatus(entity.getStatus());

        if (entity.getDoctor() != null) {
            dto.setDoctorName(entity.getDoctor().getFirstName() + " " + entity.getDoctor().getLastName());
        }

        return dto;
    }
}
