package org.vitacare.service;

import org.vitacare.dto.user.StaffDTO;

import java.util.List;
import java.util.Optional;

public interface StaffService {
    Optional<StaffDTO> getById(Long id);
    List<StaffDTO> getAllStaffs();

}
