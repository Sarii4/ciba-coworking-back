
package com.cibacoworking.cibacoworking.models;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Space {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(name = "space_type", length = 50, nullable = false)
    private String spaceType;

    @Column(name = "space_status", length = 20, nullable = false)
    private String spaceStatus;

    @Column(length = 150)
    private String description;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    public Space() {}

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

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
