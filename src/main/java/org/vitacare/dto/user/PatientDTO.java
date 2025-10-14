package org.vitacare.dto.user;

public class PatientDTO extends UserDTO{
    private String cin;
    private String phone;
    private String address;

    public PatientDTO(String cin, String phone, String address) {
        this.cin = cin;
        this.phone = phone;
        this.address = address;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
