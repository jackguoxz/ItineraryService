package com.okta.developer.docker_microservices.service.services;

import com.okta.developer.docker_microservices.service.dtos.ItineraryDto;

import java.util.List;

public interface ItineraryService {
    List<ItineraryDto> listClasses();
    List<String> getShortestItineraryByTime(int id);
}
