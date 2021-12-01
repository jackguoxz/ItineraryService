package com.example.developer.docker_microservices.ui.controller;

import com.example.developer.docker_microservices.ui.Services.PathService;
import com.example.developer.docker_microservices.ui.auth.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.expression.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Auth
@Controller
@RequestMapping("/")
@Api(value = "test", description = "description")
public class ItineraryController {

    private final RestTemplate restTemplate;
    private final String serviceHost;
    //private PathService pathService;

    private final PathService pathService;


    public ItineraryController(RestTemplate restTemplate, @Value("${service.host}") String serviceHost,PathService pathService) {
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
        String url="http://"+ serviceHost +"/itinerary/listitinerary/dijkstra/"+id;
        ResponseEntity<List<String>> result=ResponseEntity.ok(pathService.listItinerary(url));
        return  result;

    }

}
