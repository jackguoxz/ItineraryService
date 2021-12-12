package com.example.developer.docker_microservices.ui.Services;

import com.example.developer.docker_microservices.ui.Algorithm.Dijkstra.Dijkstra;
import com.example.developer.docker_microservices.ui.auth.Auth;
import com.example.developer.docker_microservices.ui.dto.ItineraryDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.*;

@Auth
@Service @Slf4j
public class PathService implements PathInterface {
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


    public Set<String> getOriginalCityIdList(List<ItineraryDto> itineraryDto)
    {
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
