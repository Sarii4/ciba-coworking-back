package com.cibacoworking.cibacoworking.models.dtos;

import java.time.LocalDate;
import java.time.LocalTime;


public class ReservationDTO {
    private Integer id;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private UserDTO userDTO;
    private SpaceDTO spaceDTO;

    public ReservationDTO() {}

    public ReservationDTO(Integer id, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, UserDTO userDTO, SpaceDTO spaceDTO) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userDTO = userDTO;
        this.spaceDTO = spaceDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public SpaceDTO getSpaceDTO() {
        return spaceDTO;
    }

    public void setSpaceDTO(SpaceDTO spaceDTO) {
        this.spaceDTO = spaceDTO;
    }

   

    
}
