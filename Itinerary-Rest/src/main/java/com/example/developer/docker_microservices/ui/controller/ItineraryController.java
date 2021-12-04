package com.example.developer.docker_microservices.ui.controller;

import com.example.developer.docker_microservices.ui.Services.PathService;
import com.example.developer.docker_microservices.ui.auth.Auth;
import com.example.developer.docker_microservices.ui.dto.ItineraryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Auth
@Controller
@RequestMapping("/")
@Api(value = "Itinerary Controller", description = "Itinerary Controller in Path Service")
public class ItineraryController {
    private final RestTemplate restTemplate;
    private final String serviceHost;
    private final PathService pathService;


    public ItineraryController(RestTemplate restTemplate, @Value("${service.host}") String serviceHost, PathService pathService) {
        this.restTemplate = restTemplate;
        this.serviceHost = serviceHost;
        this.pathService=pathService;
    }

    @RequestMapping("")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @ApiOperation(value="Get Shortest itinerary by combination of least Time and least number of Connection")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="cityId",required=true,paramType="form"),
    })
    @ResponseBody
    @GetMapping("/itinerary/listitinerary/{id}")
    public ResponseEntity<List<String>> listItinerary(@PathVariable String id){
        String url ="http://"+ serviceHost +"/itinerary/getitinerarydto";
        List<ItineraryDto> itineraryDto = pathService.getItineraryDto(url);
        if(pathService.checkCityId(id,itineraryDto))
        {
            List<String> result=new ArrayList<>();
            result.add("Invalid Departure City Id");
            return  ResponseEntity.ok(result);
        };
        ResponseEntity<List<String>> result=ResponseEntity.ok(pathService.listItinerary(id,itineraryDto));
        return  result;

    }

    @ApiOperation(value="Get Shortest itinerary by least number of Connection")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="cityId",required=true,paramType="form"),
    })
    @ResponseBody
    @GetMapping("/itinerary/getshortestitinerarybyconnection/{id}")
    public ResponseEntity<List<String>> getshortestitinerarybyconnection(@PathVariable String id){
        String url ="http://"+ serviceHost +"/itinerary/getitinerarydto";
        List<ItineraryDto> itineraryDto = pathService.getItineraryDto(url);
        if(pathService.checkCityId(id,itineraryDto))
        {
            List<String> result=new ArrayList<>();
            result.add("Invalid Departure City Id");
            return  ResponseEntity.ok(result);
        };
        List<String> itineraryListByConnection=pathService.getShortestItineraryByConnection(id,itineraryDto);
        ResponseEntity<List<String>> result=ResponseEntity.ok(itineraryListByConnection);
        return  result;
    }

    @ApiOperation(value="Get Shortest itinerary by least Time")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="cityId",required=true,paramType="form"),
    })
    @ResponseBody
    @GetMapping("/itinerary/getshortestitinerarybytime/{id}")
    public ResponseEntity<List<String>> getshortestitinerarybytime(@PathVariable String id){
        String url ="http://"+ serviceHost +"/itinerary/getitinerarydto";
        List<ItineraryDto> itineraryDto = pathService.getItineraryDto(url);
        if(pathService.checkCityId(id,itineraryDto))
        {
            List<String> result=new ArrayList<>();
            result.add("Invalid Departure City Id");
            return  ResponseEntity.ok(result);
        };
        List<String> itineraryListByTime=pathService.getShortestItineraryByTime(id,itineraryDto);
        ResponseEntity<List<String>> result=ResponseEntity.ok(itineraryListByTime);
        return  result;
    }

    @ApiOperation(value="Get Shortest itinerary by combination of least Time and least number of Connection")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="cityId",required=true,paramType="form"),
    })
    @ResponseBody
    @GetMapping("/itinerary/getshortestitinerary/{id}")
    public ResponseEntity<List<String>> getshortestitineraryTest(@PathVariable String id){
        String urlGetDTO ="http://"+ serviceHost +"/itinerary/getitinerarydto";
        List<ItineraryDto> itineraryDto = pathService.getItineraryDto(urlGetDTO);
        List<String> result=new ArrayList<>();
        if(pathService.checkCityId(id,itineraryDto))
        {
            result.add("Invalid Departure City Id");
            return  ResponseEntity.ok(result);
        };
        String urlGetItineraryByTime ="http://"+ serviceHost +"/itinerary/getitinerarybytime?departurecity={departurecity}&arrivalcity={arrivalcity}";
        List<String> shortestItineraryByTime =pathService.getShortestItineraryByTime(id,urlGetItineraryByTime,itineraryDto);
        String urlGetItineraryByConnection ="http://"+ serviceHost +"/itinerary/getitinerarybyconnection?departurecity={departurecity}&arrivalcity={arrivalcity}";
        List<String> shortestItineraryByConnection =pathService.getShortestItineraryByConnection(id,urlGetItineraryByConnection,itineraryDto);
        result.add("Time");
        for (int i = 0; i < shortestItineraryByTime.size(); i++) {
            if(shortestItineraryByTime.get(i)!=null) {
                result.add(shortestItineraryByTime.get(i));
            }
        }
        result.add("Connection");
        for (int i = 0; i < shortestItineraryByConnection.size(); i++) {
            if(shortestItineraryByConnection.get(i)!=null) {
                result.add(shortestItineraryByConnection.get(i));
            }
        }
        return ResponseEntity.ok(result);
    }
}
