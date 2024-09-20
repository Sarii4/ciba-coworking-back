package com.cibacoworking.cibacoworking.models.dtos;

public class SpaceDTO {
    private Integer id;
    private String name;
    private String spaceType;
    private String spaceStatus;
    private String description;

    public SpaceDTO() {}

    public SpaceDTO(Integer id, String name, String spaceType, String spaceStatus, String description) {
        this.id = id;
        this.name = name;
        this.spaceType = spaceType;
        this.spaceStatus = spaceStatus;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpaceType() {
        return spaceType;
    }

    public void setSpaceType(String spaceType) {
        this.spaceType = spaceType;
    }

    public String getSpaceStatus() {
        return spaceStatus;
    }

    public void setSpaceStatus(String spaceStatus) {
        this.spaceStatus = spaceStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
