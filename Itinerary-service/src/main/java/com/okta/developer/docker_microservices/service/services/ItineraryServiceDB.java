package com.okta.developer.docker_microservices.service.services;

import com.okta.developer.docker_microservices.service.dao.ItineraryDao;
import com.okta.developer.docker_microservices.service.dtos.ItineraryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

@Service
public class ItineraryServiceDB implements ItineraryService {

    private final ItineraryDao itineraryDAO;

    @Autowired
    public ItineraryServiceDB(ItineraryDao itineraryDAO) {
        this.itineraryDAO = itineraryDAO;
    }



    @Override
    public List<String> getShortestItineraryByTime(int originalCityId)
    {
        List<String> result=new ArrayList<>();
        List<ItineraryDto> itineraryDto=listClasses();
        int [][]graph=getItineraryGraphByFlightTime(itineraryDto);
        List<Integer> list=getDestinationCityList(itineraryDto);
        for(int i=0;i<list.size();i++) {
            int destinationCityId = list.get(i);
            if(destinationCityId!=originalCityId) {
                Vector<Integer> path = getShortestItineraryByTime(graph, 4, originalCityId, destinationCityId);
                result.add(path.toString());
            }
        }
        return result;
    }

    @Override
    public List<String> getShortestItineraryByConnection(int originalCityId)
    {
        List<String> result=new ArrayList<>();
        List<ItineraryDto> itineraryDto=listClasses();
        int [][]graph=getItineraryGraphByFlightConnection(itineraryDto);
        List<Integer> list=getDestinationCityList(itineraryDto);
        for(int i=0;i<list.size();i++) {
            int destinationCityId = list.get(i);
            if(destinationCityId!=originalCityId) {
                Vector<Integer> path = getShortestItineraryByTime(graph, 4, originalCityId, destinationCityId);
                result.add(path.toString());
            }
        }
        return result;
    }


    public Vector<Integer> getShortestItineraryByTime(int [][]graph, int number,int originalCityId, int destinationCityId)
    {
        Vector<Integer> path;
        GFG gfg=new GFG();
        gfg.initialise(number, graph);
        gfg.floydWarshall(number);
        path=gfg.constructPath(originalCityId,destinationCityId);
        gfg.printPath(path);
        return path;

    }

    public Vector<Integer> getShortestItineraryByConnection(int [][]graph, int number,int originalCityId, int destinationCityId)
    {
        Vector<Integer> path;
        GFG gfg=new GFG();
        gfg.initialise(number, graph);
        gfg.floydWarshall(number);
        path=gfg.constructPath(originalCityId,destinationCityId);
        gfg.printPath(path);
        return path;

    }

    public List<Integer> getDestinationCityList(List<ItineraryDto> itineraryDto)
    {
        final int MAXN = 100;
        List<Integer> destinationCityList=new ArrayList<>();
        for (int i = 0; i < itineraryDto.size(); i++) {
            ItineraryDto dto = itineraryDto.get(i);
            int destinationCityId=dto.getDestinationCityId();
            if(!destinationCityList.contains(destinationCityId))
            {
                destinationCityList.add(destinationCityId);
            }
        }
        return destinationCityList;


    }
    public  int[][] getItineraryGraphByFlightTime(List<ItineraryDto> itineraryDto)
    {

        final int MAXN = 100;
        final int INF = (int) 1e7;
        int [][]itineraryMap = new int[MAXN][MAXN];
        for(int i=0;i<MAXN;i++){
            for(int j=0;j<MAXN;j++)
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

    }

    public  int[][] getItineraryGraphByFlightConnection(List<ItineraryDto> itineraryDto)
    {
        final int MAXN = 100;
        final int INF = (int) 1e7;
        int [][]itineraryMap = new int[MAXN][MAXN];
        for(int i=0;i<MAXN;i++){
            for(int j=0;j<MAXN;j++)
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
    }
    @Override
    public List<ItineraryDto> listClasses() {
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
