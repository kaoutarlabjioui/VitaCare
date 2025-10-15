package org.vitacare.dto.specialityDTO;

public class SpecialityDTO {
    private Long id;
    private String name;
    private String description;
    private String code;
    private String departmentName;
    public SpecialityDTO(Long id, String name,String description,String code,String departmentName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.code = code;
        this.departmentName = departmentName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
