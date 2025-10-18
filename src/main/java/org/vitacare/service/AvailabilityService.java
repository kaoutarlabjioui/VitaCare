package org.vitacare.service;

import org.vitacare.dto.availability.AvailabilityDTO;
import org.vitacare.entity.Doctor;

import java.util.List;

public interface AvailabilityService {
    void createAvailability(AvailabilityDTO dto, int userId);
    List<AvailabilityDTO> getDoctorAvailabilities(int doctorId);
    void updateAvailability(AvailabilityDTO dto, int userId);
    void markAsUnavailable(int id);
    AvailabilityDTO getAvailabilityById(int id);
}
