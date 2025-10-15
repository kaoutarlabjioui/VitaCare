package org.vitacare.dto.user;

import org.vitacare.dto.department.DepartmentDTO;
import org.vitacare.dto.specialityDTO.SpecialityDTO;

public class DoctorDTO extends UserDTO {
    private String registration;
    private String title;
    private SpecialityDTO speciality;




    public DoctorDTO(String registration, String title, SpecialityDTO speciality) {
        this.registration = registration;
        this.title = title;
        this.speciality = speciality;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SpecialityDTO getSpeciality() {
        return speciality;
    }

    public void setSpeciality(SpecialityDTO speciality) {
        this.speciality = speciality;
    }


}
