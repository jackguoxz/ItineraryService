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
    public List<String> getShortestItineraryByTime(String originalCityId)
    {
        List<String> result=new ArrayList<>();
        result=Dijkstra.getInstance().convertByTime(originalCityId);
        return result;
    }

    @Override
    public List<String> getShortestItineraryByConnection(String originalCityId)
    {
        List<String> result=new ArrayList<>();
        result=Dijkstra.getInstance().convertByConnection(originalCityId);
        return result;
    }

    @Override
    public String getShortestItineraryByTime(String originalCityId, String arrivalCityID)
    {
        return Dijkstra.getInstance().convertByTime(originalCityId,arrivalCityID);
    }

    @Override
    public String getShortestItineraryByConnection(String originalCityId, String arrivalCityID)
    {

        return Dijkstra.getInstance().convertByConnection(originalCityId,arrivalCityID);
    }

    public Set<String> getDestinationCityIdList()
    {
        List<ItineraryDto> itineraryDto=listItinerary();
        Set<String> destinationCityIdList=new HashSet<>();
        for (int i = 0; i < itineraryDto.size(); i++) {
            ItineraryDto dto = itineraryDto.get(i);
            String destinationCityId=dto.getDestinationCityId();
            destinationCityIdList.add(destinationCityId);
        }
        return destinationCityIdList;
    }

    @Override
    public Set<String> getOriginalCityIdList()
    {
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
