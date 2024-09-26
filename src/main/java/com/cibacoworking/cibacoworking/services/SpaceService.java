package com.cibacoworking.cibacoworking.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cibacoworking.cibacoworking.exception.CibaCoworkingException;
import com.cibacoworking.cibacoworking.models.dtos.SpaceDTO;
import com.cibacoworking.cibacoworking.models.entities.Space;
import com.cibacoworking.cibacoworking.repositories.SpaceRepository;

@Service
public class SpaceService {
    
    @Autowired 
    private SpaceRepository spaceRepository;

    @Autowired
    private DTOMapper dtoMapper;

    // Obtener todas las mesas con información completa por fechas concretas
    public List<SpaceDTO> getAllTablesByDate(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) throws CibaCoworkingException {
        List<Space> tablesByDate = spaceRepository.findTablesByDate(startDate, endDate, startTime, endTime);
        
        return tablesByDate.stream()
                .map(dtoMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    //Comprobar el estatus de una mesa por su Id
    public boolean checkTableStatus(int spaceId) {
        String tableStatus = spaceRepository.findSpaceStatusById(spaceId);
        return "actiu".equals(tableStatus);
    }

    //Cambiar el estatus de una mesa por su Id
    public void updateTableStatus(int id) throws CibaCoworkingException {
        Optional<Space> spaceOpt = spaceRepository.findById(id);
        if (!spaceOpt.isPresent()) {
            throw new CibaCoworkingException("No s'ha trobat la taula per actualitzar", HttpStatus.NOT_FOUND);
        }
    
        try {
            Space existingTable = spaceOpt.get();
            existingTable.setSpaceStatus("inactiu");
            spaceRepository.save(existingTable);
        } catch (Exception e) {
            throw new CibaCoworkingException("No s'ha pogut actualitzar l'estat de la taula", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    
    }
    
}
