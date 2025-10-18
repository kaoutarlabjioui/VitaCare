package org.vitacare.service;

import org.vitacare.dto.user.PatientDTO;
import org.vitacare.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    List<PatientDTO> getAllPatients();
    Optional<PatientDTO> getById(Long id);
}
