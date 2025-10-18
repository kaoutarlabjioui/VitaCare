package org.vitacare.mapper;

import jakarta.enterprise.context.ApplicationScoped;

import org.vitacare.dto.user.DoctorDTO;
import org.vitacare.dto.user.StaffDTO;

import org.vitacare.entity.Doctor;
import org.vitacare.entity.Staff;

@ApplicationScoped
public class DoctorMapper {


    public static DoctorDTO toDto(Doctor doctor) {
        if (doctor == null) {
            return null;
        }

        DoctorDTO dto = new DoctorDTO(
                doctor.getRegistration(),
                doctor.getTitle(),
                SpecialityMapper.toDto(doctor.getSpeciality())

        );


        dto.setId(doctor.getId());
        dto.setFirstName(doctor.getFirstName());
        dto.setLastName(doctor.getLastName());
        dto.setEmail(doctor.getEmail());
        dto.setActive(doctor.isActive());

        return dto;
    }


    public static Doctor toEntity(DoctorDTO dto) {
        if (dto == null) {
            return null;
        }

        Doctor doctor = new Doctor();
        doctor.setId(dto.getId());
        doctor.setFirstName(dto.getFirstName());
        doctor.setLastName(dto.getLastName());
        doctor.setEmail(dto.getEmail());


        doctor.setRegistration(dto.getRegistration());
        doctor.setSpeciality(SpecialityMapper.toEntity(dto.getSpeciality()));
        doctor.setTitle(dto.getTitle());


        return doctor;
    }
}
