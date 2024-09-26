package com.cibacoworking.cibacoworking.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.SpaceDTO;
import com.cibacoworking.cibacoworking.models.entities.Space;
import com.cibacoworking.cibacoworking.repositories.SpaceRepository;

@Service
public class SpaceService {
    
    @Autowired 
    private SpaceRepository spacerepository;

    @Autowired
    private DTOMapper dtoMapper;

    // Obtener todas las mesas con información completa por fechas concretas
    public List<SpaceDTO> getAllTablesByDate(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) throws CibaCoworkingException {
        List<Space> tablesByDate = spacerepository.findTablesByDate(startDate, endDate, startTime, endTime);
        
        return tablesByDate.stream()
                .map(dtoMapper::convertToDTO)
                .collect(Collectors.toList());
    }

}
