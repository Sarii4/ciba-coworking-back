package com.cibacoworking.cibacoworking.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpaceDTO {
    private Integer id;
    private String name;
    private String spaceType;
    private String spaceStatus;
    private String description;
}
