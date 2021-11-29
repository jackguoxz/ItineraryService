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
    private final ItineraryDao itineraryDAO;

    @Autowired
    public ItineraryServiceDB(ItineraryDao itineraryDAO) {
        this.itineraryDAO = itineraryDAO;
    }


    @Override
    public List<String> getShortestItineraryByTimeByDijkstra(String originalCityId)
    {
        List<String> result=new ArrayList<>();
        List<ItineraryDto> itineraryDto=listItinerary();
        Dijkstra.buildEdgesByTime(itineraryDto);
        result=Dijkstra.convert(originalCityId);
        return result;
    }

    @Override
    public List<String> getShortestItineraryByConnectionByDijkstra(String originalCityId)
    {
        List<String> result=new ArrayList<>();
        List<ItineraryDto> itineraryDto=listItinerary();
        Dijkstra.buildEdgesByConnection(itineraryDto);
        result=Dijkstra.convert(originalCityId);
        return result;
    }


    private Vector<Integer> getShortestItineraryByTime(int [][]graph, int number,int originalCityId, int destinationCityId)
    {
        Vector<Integer> path;
        GFG gfg=new GFG(number);
        gfg.initialise(number, graph);
        gfg.floydWarshall(number);
        path=gfg.constructPath(originalCityId,destinationCityId);
        return path;
    }

    private Vector<Integer> getShortestItineraryByConnection(int [][]graph, int number,int originalCityId, int destinationCityId)
    {
        Vector<Integer> path;
        GFG gfg=new GFG(number);
        gfg.initialise(number, graph);
        gfg.floydWarshall(number);
        path=gfg.constructPath(originalCityId,destinationCityId);
        return path;
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

    @Override
    public List<ItineraryDto> listItinerary() {
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
