package com.example.developer.docker_microservices.ui.controller;

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

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ItineraryController {

    private final RestTemplate restTemplate;
    private final String serviceHost;

    public ItineraryController(RestTemplate restTemplate, @Value("${service.host}") String serviceHost) {
        this.restTemplate = restTemplate;
        this.serviceHost = serviceHost;
    }

    @RequestMapping("")
    public ModelAndView index() {
        return new ModelAndView("index");
    }


    @GetMapping("/itinerarylist/{id}")
    public ResponseEntity<List<String>> listItinerary(@PathVariable String id){
        String url="http://"+ serviceHost +"/itinerarylist/get/zhangsan/12345/"+id;
        System.out.println(url);
        return restTemplate
                .exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<String>>() {});

    }

}
