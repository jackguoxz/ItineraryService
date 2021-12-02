package com.example.developer.docker_microservices.ui.Services;

import com.example.developer.docker_microservices.ui.Algorithm.Dijkstra.Dijkstra;
import com.example.developer.docker_microservices.ui.auth.Auth;
import com.example.developer.docker_microservices.ui.dto.ItineraryDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

@Auth
@Service
public class PathService implements PathServiceInterface {
    @Resource
    private RestTemplate restTemplate;


    @Override
    public List<String> getShortestItineraryByTime(String originalCityId,List<ItineraryDto> itineraryDto)
    {
        Dijkstra dijkstra=new Dijkstra(itineraryDto);
        return dijkstra.convertByTime(originalCityId);
    }

    @Override
    public List<String> getShortestItineraryByConnection(String originalCityId,List<ItineraryDto> itineraryDto) {
        Dijkstra dijkstra=new Dijkstra(itineraryDto);
        return dijkstra.convertByConnection(originalCityId);
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

    public Set<String> getOriginalCityIdList(List<ItineraryDto> itineraryDto){
        Set originalCityIdList = new HashSet();
        for(int i=0;i<itineraryDto.size();i++)
        {
            ItineraryDto dto = itineraryDto.get(i);
            String originalCityId=dto.getOriginalCityId();
            originalCityIdList.add(originalCityId);
        }
        return originalCityIdList;
    }

    public boolean checkCityId(String id,List<ItineraryDto> itineraryDto)
    {
        Set<String> originalCityList=getOriginalCityIdList(itineraryDto);
        if(!originalCityList.contains(id) )
        {
            return  true;
        }
        return false;
    }

    public  List<ItineraryDto> getItineraryDto(String url)
    {
        ResponseEntity<List<ItineraryDto>> itineraryDtoList= restTemplate
                .exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<ItineraryDto>>() {});
        List<ItineraryDto> result = itineraryDtoList.getBody();
        return result;

    }

    public  List<String> getShortestItinerary(String id,String url,List<ItineraryDto> itineraryDto)
    {
        List<String> result=new ArrayList<>();
        List<String> shortestItineraryByTime=getShortestItineraryByTime(id,url,itineraryDto);
        List<String> shortestItineraryByConnection=getShortestItineraryByTime(id,url,itineraryDto);
        result.add("Time");
        for (int i = 0; i < shortestItineraryByTime.size(); i++) {
            result.add(shortestItineraryByTime.get(i));
        }
        result.add("Connection");
        for (int i = 0; i < shortestItineraryByConnection.size(); i++) {
            result.add(shortestItineraryByConnection.get(i));
        }
        return result;

    }


    public  List<String> getShortestItineraryByTime(String departureCity,String url,List<ItineraryDto> itineraryDto)
    {
        Set<String> arrivalCity=getDestinationCityList(itineraryDto);
        List<String> result =new ArrayList<>();
        try {
            for (String id : arrivalCity) {
                result.add(getShortestItineraryByTime(departureCity,id, url));
            }
        }catch (Exception e)
        {
            System.out.print(e.toString());
        }

        return result;
    }

    public  List<String> getShortestItineraryByConnection(String departureCity,String url,List<ItineraryDto> itineraryDto)
    {
        Set<String> arrivalCity=getDestinationCityList(itineraryDto);
        List<String> result =new ArrayList<>();
        for(String id: arrivalCity)
        {
            result.add(getShortestItineraryByConnection(departureCity,id,url));
        }
        return result;
    }


    public  String getShortestItineraryByTime(String departureCity,String arrivalCity,String url)
    {
        Map<String,String> map = new HashMap();
        map.put("departurecity",departureCity);
        map.put("arrivalcity",arrivalCity);
        ResponseEntity<String> result= restTemplate
                .exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<String>() {},map);
        return result.getBody();

    }

    public  String getShortestItineraryByConnection(String departureCity,String arrivalCity,String url)
    {
        Map<String,String> map = new HashMap();
        map.put("departurecity",departureCity);
        map.put("arrivalcity",arrivalCity);
        ResponseEntity<String> result= restTemplate
                .exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<String>() {},map);
        return result.getBody();

    }
    public List<String> listItinerary(String originalCityId,List<ItineraryDto> itineraryDto)
    {
        List<String> result=new ArrayList<>();
        result.add("Time");
        List<String> shortestItineraryByTime=getShortestItineraryByTime(originalCityId,itineraryDto);
        for (int i = 0; i < shortestItineraryByTime.size(); i++) {
            result.add(shortestItineraryByTime.get(i));
        }
        result.add("Connection");
        List<String> shortestItineraryByConnection=getShortestItineraryByConnection(originalCityId,itineraryDto);
        for (int i = 0; i < shortestItineraryByConnection.size(); i++) {
            result.add(shortestItineraryByConnection.get(i));
        }
        return result;
    }
}
