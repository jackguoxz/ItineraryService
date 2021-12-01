package com.example.developer.docker_microservices.service.services;

import com.example.developer.docker_microservices.service.dtos.ItineraryDto;

import java.util.List;
import java.util.Set;

public interface ItineraryService {
    //List<ItineraryDto> listItinerary();
    List<String> getShortestItineraryByTimeByDijkstra(String id);
    List<String> getShortestItineraryByConnectionByDijkstra(String id);
    Set<String> getOriginalCityIdList();
}
