package org.vitacare.repository;

import org.vitacare.entity.Availability;
import org.vitacare.entity.Doctor;

import java.util.List;

public interface AvailabilityRepository {
    void save(Availability availability);
    List<Availability> findByDoctor(Doctor doctor);
    Availability findById(int id);
    void update(Availability availability);

}
