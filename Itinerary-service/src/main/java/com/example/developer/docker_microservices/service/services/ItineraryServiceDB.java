package com.example.developer.docker_microservices.service.services;

import com.example.developer.docker_microservices.service.Algorithm.Dijkstra.Dijkstra;
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


    @Override
    public List<String> getShortestItineraryByTimeByDijkstra(String originalCityId)
    {
        List<String> result=new ArrayList<>();
        result=Dijkstra.getInstance().convertByTime(originalCityId);
        return result;
    }

    @Override
    public List<String> getShortestItineraryByConnectionByDijkstra(String originalCityId)
    {
        List<String> result=new ArrayList<>();
        result=Dijkstra.getInstance().convertByConnection(originalCityId);
        return result;
    }

    public Set<String> getDestinationCityList(List<ItineraryDto> itineraryDto)
    {
        Set<String> destinationCityList=new HashSet<>();
        for (int i = 0; i < itineraryDto.size(); i++) {
            ItineraryDto dto = itineraryDto.get(i);
            String destinationCityId=dto.getDestinationCityId();
            destinationCityList.add(destinationCityId);
        }
        return destinationCityList;
    }

    @Override
    public Set<String> getOriginalCityIdList(){
        Set originalCityIdList = new HashSet();
        List<ItineraryDto> itineraryDto=listItinerary();
        for(int i=0;i<itineraryDto.size();i++)
        {
            ItineraryDto dto = itineraryDto.get(i);
            String originalCityId=dto.getOriginalCityId();
            originalCityIdList.add(originalCityId);
        }
        return originalCityIdList;
    }

    //@Override
    public static List<ItineraryDto> listItinerary() {
        return itineraryDAO
                .findAll()
                .stream()
                .map( classObj -> ItineraryDto
                        .builder()
                        .originalCityId(classObj.getOriginalCityId())
                        .destinationCityId(classObj.getDestinationCityId())
                        .departureTimeName(classObj.getDepartureTime())
                        .arrivalTimeName(classObj.getArrivalTime())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
