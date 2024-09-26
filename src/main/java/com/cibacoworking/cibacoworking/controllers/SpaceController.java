package com.cibacoworking.cibacoworking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cibacoworking.cibacoworking.config.ConstantsSecurity;
import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.DateRangeRequestDTO;
import com.cibacoworking.cibacoworking.models.dtos.SpaceDTO;
import com.cibacoworking.cibacoworking.services.SpaceService;

@RestController
public class SpaceController {


    @Autowired
    private SpaceService spaceService;

    // Obtener todas las mesas con información completa por fechas concretas
    @GetMapping(ConstantsSecurity.GET_TABLES_BY_DATE)
    public ResponseEntity<List<SpaceDTO>> getAllTablesByDate(
            @RequestBody DateRangeRequestDTO dateRange) throws CibaCoworkingException {
        
        List<SpaceDTO> availableSpaces = spaceService.getAllTablesByDate(
            dateRange.getStartDate(), 
            dateRange.getEndDate(),
            dateRange.getStartTime(),
            dateRange.getEndTime());
        return ResponseEntity.ok(availableSpaces);
    }

    

   /*  @GetMapping("/{id}")
    public SpaceDTO getSpaceById(@PathVariable int id) {
        Space space = spaceService.findById(id).orElse(null);
        return dtoMapper.convertToDTO(space);
    } */

    /* @PostMapping
    public SpaceDTO createSpace(@RequestBody SpaceDTO spaceDTO) {
        Space space = dtoMapper.convertToEntity(spaceDTO);
        Space savedSpace = dtoMapper.saveSpace(space);
        return dtoMapper.convertToDTO(savedSpace);
    } */
}
