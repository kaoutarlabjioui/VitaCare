package org.vitacare.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "staffs")
@PrimaryKeyJoinColumn(name = "user_id")
public class Staff extends User {
    private String position;

    public Staff(){}
    public Staff(String position) {
        this.position = position;
    }

    public Staff(int id, String firstName, String lastName, String email, String password, boolean isActive, boolean isAdmin, String position) {
        super(id, firstName, lastName, email, password, isActive, isAdmin);
        this.position = position;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "position='" + position + '\'' +
                '}';
    }
}
