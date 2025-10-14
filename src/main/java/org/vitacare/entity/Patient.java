package org.vitacare.entity;

import jakarta.persistence.*;
import org.vitacare.entity.enums.BloodGroup;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@PrimaryKeyJoinColumn(name = "user_id")
public class Patient extends User {
    private String cin;
    private LocalDate birthDate;
    private String sex ;
    private String phone;
    private String address;
    @Enumerated(EnumType.STRING)
    private BloodGroup blood;

    public Patient() {}

    public Patient(String cin, LocalDate birthDate, String sex, String phone, String address, BloodGroup blood) {
        this.cin = cin;
        this.birthDate = birthDate;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
        this.blood = blood;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BloodGroup getBlood() {
        return blood;
    }

    public void setBlood(BloodGroup blood) {
        this.blood = blood;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "cin='" + cin + '\'' +
                ", birthDate=" + birthDate +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", blood=" + blood +
                '}';
    }
}
