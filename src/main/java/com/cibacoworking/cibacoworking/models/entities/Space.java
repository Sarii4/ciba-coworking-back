
package com.cibacoworking.cibacoworking.models.entities;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "spaces")
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

}
