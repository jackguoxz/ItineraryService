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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Auth
@Service
public class PathService implements PathServiceInterface {
    @Resource
    private RestTemplate restTemplate;


    @Override
    public List<String> getShortestItineraryByTime(String originalCityId,List<ItineraryDto> itineraryDto)
    {
        List<String> result=new ArrayList<>();
        Dijkstra dijkstra=new Dijkstra(itineraryDto);
        result= dijkstra.convertByTime(originalCityId);
        return result;
    }

    @Override
    public List<String> getShortestItineraryByConnection(String originalCityId,List<ItineraryDto> itineraryDto) {
        List<String> result = new ArrayList<>();
        Dijkstra dijkstra=new Dijkstra(itineraryDto);
        result = dijkstra.convertByConnection(originalCityId);
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

    public  List<ItineraryDto> getItineraryDto(String url)
    {
        ResponseEntity<List<ItineraryDto>> itineraryDtoList= restTemplate
                .exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<ItineraryDto>>() {});
        List<ItineraryDto> result = itineraryDtoList.getBody();
        return result;

    }
    public List<String> listItinerary(String url)
    {
        ResponseEntity<List<String>> listOfString= restTemplate
                .exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<String>>() {});
        List<String> result = listOfString.getBody();
        return result;
    }
}
