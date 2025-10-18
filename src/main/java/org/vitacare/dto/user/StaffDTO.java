package org.vitacare.dto.user;

public class StaffDTO extends UserDTO {
    private String position;

    public StaffDTO() {
        super();
    }


    public StaffDTO(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}