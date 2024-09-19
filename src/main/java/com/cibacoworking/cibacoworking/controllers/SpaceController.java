package com.cibacoworking.cibacoworking.controllers;

import com.cibacoworking.cibacoworking.models.dtos.SpaceDTO;
import com.cibacoworking.cibacoworking.models.entities.Space;
import com.cibacoworking.cibacoworking.services.DTOMapper;
import com.cibacoworking.cibacoworking.repositories.SpaceRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spaces")
public class SpaceController {

    @Autowired
    private DTOMapper dtoMapper;

    @Autowired
    private SpaceRepository spaceRepository;

    @GetMapping("/{id}")
    public SpaceDTO getSpaceById(@PathVariable int id) {
        Space space = spaceRepository.findById(id).orElse(null);
        return dtoMapper.convertToDTO(space);
    }

    @PostMapping
    public SpaceDTO createSpace(@RequestBody SpaceDTO spaceDTO) {
        Space space = dtoMapper.convertToEntity(spaceDTO);
        Space savedSpace = dtoMapper.saveSpace(space);
        return dtoMapper.convertToDTO(savedSpace);
    }
}
