package org.vitacare.dto.auth;

import java.time.LocalDate;
import org.vitacare.entity.enums.BloodGroup;

public class RegisterRequestDTO {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String userType; // "DOCTOR" | "PATIENT" | "STAFF"

    // Doctor only
    private String registration;
    private String title;
    private Long specialityId;

    // Patient only
    private String cin;
    private LocalDate birthDate;
    private String sexe;
    private String phone;
    private String address;
    private BloodGroup blood;

    // Staff only
    private String position;

    public RegisterRequestDTO() {}

    public RegisterRequestDTO(
            String email,
            String password,
            String firstName,
            String lastName,
            String userType,
            String registration,
            String title,
            String cin,
            LocalDate birthDate,
            String sexe,
            String phone,
            String address,
            BloodGroup blood,
            String position,
            Long specialityId
    ) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.registration = registration;
        this.title = title;
        this.cin = cin;
        this.birthDate = birthDate;
        this.sexe = sexe;
        this.phone = phone;
        this.address = address;
        this.blood = blood;
        this.position = position;
        this.specialityId = specialityId;
    }

    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }

    public String getRegistration() { return registration; }
    public void setRegistration(String registration) { this.registration = registration; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public BloodGroup getBlood() { return blood; }
    public void setBlood(BloodGroup blood) { this.blood = blood; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public Long getSpecialityId() { return specialityId; }
    public void setSpecialityId(Long specialityId) { this.specialityId = specialityId; }
}
