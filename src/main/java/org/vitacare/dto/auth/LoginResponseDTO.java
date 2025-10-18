package org.vitacare.dto.auth;

public class LoginResponseDTO {
    private int id;
    private String lastName;
    private String firstName;
    private boolean isAdmin;
    private String userType; // DOCTOR / PATIENT / STAFF
    public LoginResponseDTO(String userType, boolean isAdmin, String firstName, String lastName) {
        this.userType = userType;
        this.isAdmin = isAdmin;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public LoginResponseDTO(int id ,String userType, boolean isAdmin, String firstName,String lastName) {
        this.id = id;
        this.userType = userType;
        this.isAdmin = isAdmin;
        this.firstName = firstName;
        this.lastName = lastName;

    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }


    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
