package org.vitacare.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.vitacare.dto.user.StaffDTO;
import org.vitacare.entity.Staff;
import org.vitacare.mapper.StaffMapper;
import org.vitacare.repository.UserRepository;
import org.vitacare.service.StaffService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class StaffServiceImp implements StaffService {
   @Inject
   private UserRepository userRepository;
    @Override
    public Optional<StaffDTO> getById(Long id) {
    return userRepository.findById(id)
            .filter(user->user instanceof Staff)
            .map(user->StaffMapper.toDto((Staff) user));

    }

    @Override
    public List<StaffDTO> getAllStaffs() {
    return userRepository.getAllStaff().stream().map(StaffMapper::toDto).collect(Collectors.toList());
    }
}
