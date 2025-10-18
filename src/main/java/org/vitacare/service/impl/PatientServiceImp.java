package org.vitacare.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.vitacare.dto.user.PatientDTO;
import org.vitacare.entity.Patient;
import org.vitacare.mapper.PatientMapper;
import org.vitacare.repository.UserRepository;
import org.vitacare.service.PatientService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class PatientServiceImp implements PatientService {

    @Inject
    private UserRepository userRepository;

    @Override
    public List<PatientDTO> getAllPatients() {
        return userRepository.getAllPatients().stream().map(PatientMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<PatientDTO> getById(Long id) {
        return userRepository.findById(id)
                .filter(user -> user instanceof Patient)
                .map(user -> PatientMapper.toDto((Patient) user));
    }
}