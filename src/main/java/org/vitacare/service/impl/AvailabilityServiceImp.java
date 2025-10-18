package org.vitacare.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.vitacare.dto.availability.AvailabilityDTO;
import org.vitacare.entity.Availability;
import org.vitacare.entity.Doctor;
import org.vitacare.entity.User;
import org.vitacare.entity.enums.AvailabilityStatus;
import org.vitacare.mapper.AvailabilityMapper;
import org.vitacare.repository.AvailabilityRepository;
import org.vitacare.repository.UserRepository;
import org.vitacare.service.AvailabilityService;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AvailabilityServiceImp implements AvailabilityService {
    @Inject
    private UserRepository doctorRepository;
    @Inject
    private AvailabilityRepository availabilityRepository;

    @Inject
    private EntityManager em; // 🔥 Ajouté


    @Override
    public void createAvailability(AvailabilityDTO dto, int userId) {
        validateAvailability(dto);

        Doctor managedDoctor = doctorRepository.findByUserId(userId);
        if (managedDoctor == null) {
            throw new IllegalArgumentException("Aucun docteur associé à l'utilisateur ID : " + userId);
        }

        Availability availability = AvailabilityMapper.toEntity(dto, managedDoctor);
        availability.setStatus(AvailabilityStatus.AVAILABLE);
        availabilityRepository.save(availability);
    }

    @Override
    public List<AvailabilityDTO> getDoctorAvailabilities(int userId) {
        Doctor doctor = doctorRepository.findByUserId(userId);
        if (doctor == null) throw new IllegalArgumentException("Aucun docteur trouvé pour userId : " + userId);

        return availabilityRepository.findByDoctor(doctor)
                .stream()
                .map(AvailabilityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateAvailability(AvailabilityDTO dto, int userId) {
        validateAvailability(dto);

        Doctor managedDoctor = doctorRepository.findByUserId(userId);
        if (managedDoctor == null) {
            throw new IllegalArgumentException("Aucun docteur associé à l'utilisateur ID : " + userId);
        }

        Availability existing = availabilityRepository.findById(dto.getId());
        if (existing != null) {
            Availability updated = AvailabilityMapper.toEntity(dto, managedDoctor);
            updated.setId(existing.getId());
            availabilityRepository.update(updated);
        }
    }


    @Override
    public AvailabilityDTO getAvailabilityById(int id) {
        Availability availability = availabilityRepository.findById(id);
        if (availability == null) {
            throw new IllegalArgumentException("Aucune disponibilité trouvée avec l'ID : " + id);
        }

        return AvailabilityMapper.toDto(availability);
    }

    @Override
    public void markAsUnavailable(int id) {
        Availability availability = availabilityRepository.findById(id);
        if (availability != null) {
            availability.setStatus(AvailabilityStatus.UNAVAILABLE);
            availabilityRepository.update(availability);
        }
    }

    private void validateAvailability(AvailabilityDTO dto) {
        if (dto.getStartTime().isAfter(dto.getEndTime()) || dto.getStartTime().equals(dto.getEndTime())) {
            throw new IllegalArgumentException("L'heure de début doit être avant l'heure de fin.");
        }

        DayOfWeek dayOfWeek = dto.getDay();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("Impossible de définir une disponibilité pendant le weekend.");
        }
    }
}
