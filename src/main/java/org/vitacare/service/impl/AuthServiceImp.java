package org.vitacare.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.vitacare.dto.auth.*;
import org.vitacare.entity.Patient;
import org.vitacare.entity.User;
import org.vitacare.repository.UserRepository;
import org.vitacare.service.AuthService;
@ApplicationScoped
public class AuthServiceImp implements AuthService {
     @Inject
    private  UserRepository userRepository;

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
        if (!"PATIENT".equalsIgnoreCase(dto.getUserType())) {
            throw new RuntimeException("Seuls les patients peuvent s'inscrire eux-mêmes.");
        }

        Patient patient = new Patient();
        patient.setEmail(dto.getEmail());
        patient.setPassword(dto.getPassword());
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setCin(dto.getCin());
        patient.setPhone(dto.getPhone());
        patient.setAddress(dto.getAddress());

        userRepository.save(patient);
    }
}
