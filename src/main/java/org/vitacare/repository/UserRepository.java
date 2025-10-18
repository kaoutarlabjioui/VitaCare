package org.vitacare.repository;

import org.vitacare.entity.Doctor;
import org.vitacare.entity.Patient;
import org.vitacare.entity.Staff;
import org.vitacare.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<Staff> getAllStaff();
    List<Patient> getAllPatients();
    List<Doctor> getAllDoctors();
    User findByEmail(String email);
    void save(User user);
    Optional<User> findById(Long id);
    Doctor findByUserId(int userId);
}
