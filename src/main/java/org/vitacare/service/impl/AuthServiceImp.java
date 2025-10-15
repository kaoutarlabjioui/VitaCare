package org.vitacare.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.vitacare.dto.auth.*;
import org.vitacare.entity.Doctor;
import org.vitacare.entity.Patient;
import org.vitacare.entity.Speciality;
import org.vitacare.entity.User;
import org.vitacare.mapper.UserMapper;
import org.vitacare.repository.UserRepository;
import org.vitacare.service.AuthService;
import org.vitacare.service.SpecialityService;

@ApplicationScoped
public class AuthServiceImp implements AuthService {
     @Inject
    private  UserRepository userRepository;
     @Inject
     private SpecialityService specialityService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail());
        if (user == null || !user.getPassword().equals(dto.getPassword())) {
            return null;
        }

        return new LoginResponseDTO(
                user.getClass().getSimpleName().toUpperCase(),
                user.isAdmin(),
                user.getFirstName(),
                user.getLastName()
        );
    }
    @Override
    public void register(RegisterRequestDTO dto) {

        User user = UserMapper.toEntity(dto);

        if (user instanceof Doctor) {
            Doctor doctor = (Doctor) user;

            if (dto.getSpecialityId() != null) {
                Speciality speciality = specialityService.findById(dto.getSpecialityId());
                doctor.setSpeciality(speciality);
            }

            doctor.setAdmin(false);
            doctor.setActive(true);
            userRepository.save(doctor);
            return;
        }

        if (user instanceof Patient) {
            user.setActive(true);
            userRepository.save(user);
            return;
        }

        if ("STAFF".equalsIgnoreCase(dto.getUserType())) {
            user.setAdmin(false);
            user.setActive(true);
            userRepository.save(user);
            return;
        }

        throw new RuntimeException("Type d'utilisateur non supporté : " + dto.getUserType());
    }


    public void deactivateUser(Long id){
        User user = userRepository.findById(id);

        if(user == null){
            throw new RuntimeException("User not found  with id :  " + id);
        }

        user.setActive(false);
        userRepository.save(user);
    }



//    @Override
//    public void register(RegisterRequestDTO dto) {
//        if (!"PATIENT".equalsIgnoreCase(dto.getUserType())) {
//            throw new RuntimeException("Seuls les patients peuvent s'inscrire eux-mêmes.");
//        }
//
//        Patient patient = new Patient();
//        patient.setEmail(dto.getEmail());
//        patient.setPassword(dto.getPassword());
//        patient.setFirstName(dto.getFirstName());
//        patient.setLastName(dto.getLastName());
//        patient.setCin(dto.getCin());
//        patient.setPhone(dto.getPhone());
//        patient.setAddress(dto.getAddress());
//
//        userRepository.save(patient);
//    }
}
