package com.example.developer.docker_microservices.service.services;

import com.example.developer.docker_microservices.service.dtos.ItineraryDto;

import java.util.List;
import java.util.Set;

public interface ItineraryService {
    List<ItineraryDto> listItinerary();
    List<String> getShortestItineraryByTime(int id);
    List<String> getShortestItineraryByConnection(int id);
    Set<Integer> getOriginalCityIdList();
}
