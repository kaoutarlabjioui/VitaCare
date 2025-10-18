package org.vitacare.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.vitacare.dto.user.PatientDTO;
import org.vitacare.entity.Patient;
@ApplicationScoped
public class PatientMapper {


    public static PatientDTO toDto(Patient patient) {
        if (patient == null) {
            return null;
        }

        PatientDTO dto = new PatientDTO(
                patient.getCin(),
                patient.getPhone(),
                patient.getAddress()
        );


        dto.setId(patient.getId());
        dto.setFirstName(patient.getFirstName());
        dto.setLastName(patient.getLastName());
        dto.setEmail(patient.getEmail());
        dto.setActive(patient.isActive());

        return dto;
    }


    public static Patient toEntity(PatientDTO dto) {
        if (dto == null) {
            return null;
        }

        Patient patient = new Patient();
        patient.setId(dto.getId());
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setEmail(dto.getEmail());


        patient.setCin(dto.getCin());
        patient.setPhone(dto.getPhone());
        patient.setAddress(dto.getAddress());

        return patient;
    }
}
