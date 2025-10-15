package org.vitacare.service;

import org.vitacare.dto.specialityDTO.SpecialityDTO;

import java.util.List;

public interface SpecialityService {
    List<SpecialityDTO> findAllDto();
    SpecialityDTO findByIdDto(Long id);
    void create(SpecialityDTO dto);
    void update(Long id, SpecialityDTO dto);
    void delete(Long id);
}
