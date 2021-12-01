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
    /*
    private int[][] getItineraryGraphByFlightTime(List<ItineraryDto> itineraryDto)
    {

        final int size = itineraryDto.size();
        final int INF = (int) 1e7;
        int [][]itineraryMap = new int[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++)
            {
                if(j==i)
                    itineraryMap[i][j]=0;
                else
                    itineraryMap[i][j]=INF;
            }
        }

        for (int i = 0; i < itineraryDto.size(); i++) {
            ItineraryDto dto = itineraryDto.get(i);
            int originalCityId=dto.getOriginalCityId();
            int destinationCityId=dto.getDestinationCityId();
            String departureTime =dto.getDepartureTimeName();
            String arrivalTime=dto.getArrivalTimeName();
            int flightTime=Integer.parseInt(arrivalTime)-Integer.parseInt(departureTime);
            itineraryMap[originalCityId][destinationCityId]=flightTime;
        }
        return itineraryMap;
    }*/

    /*
    private int[][] getItineraryGraphByFlightConnection(List<ItineraryDto> itineraryDto)
    {
        final int size = itineraryDto.size();
        final int INF = (int) 1e7;
        int [][]itineraryMap = new int[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++)
            {
                itineraryMap[i][j]=INF;
            }
        }
        for (int i = 0; i < itineraryDto.size(); i++) {
            ItineraryDto dto = itineraryDto.get(i);
            int originalCityId=dto.getOriginalCityId();
            int destinationCityId=dto.getDestinationCityId();
            itineraryMap[originalCityId][destinationCityId]=1;
        }
        return itineraryMap;
    }*/

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
