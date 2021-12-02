package com.example.developer.docker_microservices.service.services;

import com.example.developer.docker_microservices.service.dtos.ItineraryDto;

import java.util.List;
import java.util.Set;

public interface ItineraryService {
    //List<ItineraryDto> listItinerary();
    List<String> getShortestItineraryByTime(String id);
    List<String> getShortestItineraryByConnection(String id);
    String getShortestItineraryByTime(String originalCityId, String arrivalCityID);
    String getShortestItineraryByConnection(String originalCityId, String arrivalCityID);
    Set<String> getOriginalCityIdList();
}
