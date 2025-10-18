package org.vitacare.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.vitacare.dto.user.DoctorDTO;
import org.vitacare.entity.Doctor;
import org.vitacare.mapper.DoctorMapper;
import org.vitacare.repository.UserRepository;
import org.vitacare.service.DoctorService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class DoctorServiceImp implements DoctorService {
    @Inject
    private UserRepository userRepository;
    @Override
    public Optional<DoctorDTO> getById(Long id) {
        return userRepository.findById(id)
                .filter(user->user instanceof Doctor)
                .map(user-> DoctorMapper.toDto((Doctor) user));

    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return userRepository.getAllDoctors().stream().map(DoctorMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public Optional<DoctorDTO> getByUserId(Long userId) {
        return userRepository.getAllDoctors().stream()
                .filter(doc -> Long.valueOf(doc.getId()).equals(userId))
                .map(DoctorMapper::toDto)
                .findFirst();
    }
}
