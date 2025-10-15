package org.vitacare.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.vitacare.dto.auth.LoginResponseDTO;
import org.vitacare.dto.auth.RegisterRequestDTO;
import org.vitacare.entity.Doctor;
import org.vitacare.entity.Patient;
import org.vitacare.entity.Staff;
import org.vitacare.entity.User;
@ApplicationScoped
public class UserMapper {

    public static User toEntity(RegisterRequestDTO dto) {
        if (dto == null) return null;

        String type = dto.getUserType().toUpperCase();

        switch (type) {
            case "DOCTOR":
                Doctor doctor = new Doctor();
                doctor.setEmail(dto.getEmail());
                doctor.setPassword(dto.getPassword());
                doctor.setFirstName(dto.getFirstName());
                doctor.setLastName(dto.getLastName());
                doctor.setTitle(dto.getTitle());
                doctor.setRegistration(dto.getRegistration());
                doctor.setAdmin(false);
                return doctor;

            case "PATIENT":
                Patient patient = new Patient();
                patient.setEmail(dto.getEmail());
                patient.setPassword(dto.getPassword());
                patient.setFirstName(dto.getFirstName());
                patient.setLastName(dto.getLastName());
                patient.setCin(dto.getCin());
                patient.setPhone(dto.getPhone());
                patient.setAddress(dto.getAddress());
                patient.setAdmin(false);
                return patient;

            case "STAFF":
                Staff staff = new Staff();
                staff.setEmail(dto.getEmail());
                staff.setPassword(dto.getPassword());
                staff.setFirstName(dto.getFirstName());
                staff.setLastName(dto.getLastName());
                staff.setPosition(dto.getPosition());
                staff.setAdmin(false);
                return staff;

            default:
                throw new IllegalArgumentException("Invalid user type: " + dto.getUserType());
        }
    }


    public static LoginResponseDTO toLoginResponse(User user) {
        String type = user instanceof Doctor ? "DOCTOR"
                : user instanceof Patient ? "PATIENT"
                : user instanceof Staff ? "STAFF"
                : "USER";

        return new LoginResponseDTO(type, user.isAdmin(), user.getFirstName(), user.getLastName());
    }
}
