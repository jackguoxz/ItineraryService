package com.example.developer.docker_microservices.ui.Services;

import com.example.developer.docker_microservices.ui.dto.ItineraryDto;

import java.util.List;
import java.util.Set;

public interface PathInterface {
    List<String> getShortestItineraryByTime(String originalCityId,List<ItineraryDto> itineraryDto);
    List<String> getShortestItineraryByConnection(String originalCityId,List<ItineraryDto> itineraryDto);
    Set<String> getOriginalCityIdList(List<ItineraryDto> itineraryDto);
}
