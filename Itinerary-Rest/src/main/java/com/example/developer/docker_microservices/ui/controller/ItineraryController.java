package com.example.developer.docker_microservices.ui.controller;

import com.example.developer.docker_microservices.ui.Services.PathService;
import com.example.developer.docker_microservices.ui.auth.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.expression.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Auth
@Controller
@RequestMapping("/")
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


    @GetMapping("/itinerary/listitinerary/{id}")
    public ResponseEntity<List<String>> listItinerary(@PathVariable String id){
        String url="http://"+ serviceHost +"/itinerary/listitinerary/dijkstra/"+id;
        ResponseEntity<List<String>> result=ResponseEntity.ok(pathService.listItinerary(url,id));
        return  result;

    }

}
