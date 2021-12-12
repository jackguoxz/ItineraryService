package com.example.developer.docker_microservices.service.services;

import com.example.developer.docker_microservices.service.dao.ItineraryDao;
import com.example.developer.docker_microservices.service.dtos.ItineraryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItineraryServiceDB implements ItineraryService {
    private static ItineraryDao itineraryDAO;

    @Autowired
    public ItineraryServiceDB(ItineraryDao itineraryDAO) {
        this.itineraryDAO = itineraryDAO;
    }


    public static List<ItineraryDto> listItinerary() {
        return itineraryDAO
                .findAll()
                .stream()
                .map( itineraryObj -> ItineraryDto
                        .builder()
                        .originalCityId(itineraryObj.getOriginalCityId())
                        .destinationCityId(itineraryObj.getDestinationCityId())
                        .departureTimeName(itineraryObj.getDepartureTime())
                        .arrivalTimeName(itineraryObj.getArrivalTime())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
