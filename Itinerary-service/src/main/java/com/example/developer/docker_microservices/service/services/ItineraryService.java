package com.example.developer.docker_microservices.service.services;

import com.example.developer.docker_microservices.service.dtos.ItineraryDto;

import java.util.List;

public interface ItineraryService {
    List<ItineraryDto> listClasses();
    List<String> getShortestItineraryByTime(int id);
    List<String> getShortestItineraryByConnection(int id);
}
