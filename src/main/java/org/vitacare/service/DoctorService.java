package org.vitacare.service;

import org.vitacare.dto.user.DoctorDTO;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    Optional<DoctorDTO> getById(Long id);
    List<DoctorDTO> getAllDoctors();
    Optional<DoctorDTO> getByUserId(Long userId);
  //  List<DoctorDTO> searchByName(String name);
}
