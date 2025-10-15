package org.vitacare.service;

import org.vitacare.dto.specialityDTO.SpecialityDTO;
import org.vitacare.entity.Speciality;

import java.util.List;

public interface SpecialityService {
    Speciality findById(Long id);

    List<SpecialityDTO> findAllDto();
    SpecialityDTO findByIdDto(Long id);
    void create(SpecialityDTO dto);
    void update(Long id, SpecialityDTO dto);
    void delete(Long id);

}
