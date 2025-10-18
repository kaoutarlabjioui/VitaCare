package org.vitacare.service;

import org.vitacare.dto.auth.LoginRequestDTO;
import org.vitacare.dto.auth.LoginResponseDTO;
import org.vitacare.dto.auth.RegisterRequestDTO;
import org.vitacare.entity.Doctor;
import org.vitacare.entity.Patient;
import org.vitacare.entity.Staff;

import java.util.List;


public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO dto);

    void register(RegisterRequestDTO dto);
    void deactivateUser(Long id);
    List<Doctor> getAllDoctors();

    List<Patient> getAllPatients();

    List<Staff> getAllStaff();

}
