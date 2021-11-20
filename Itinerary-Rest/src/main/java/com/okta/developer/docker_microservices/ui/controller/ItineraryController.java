package com.okta.developer.docker_microservices.ui.controller;

import com.okta.developer.docker_microservices.ui.dto.ItineraryDto;
import com.okta.developer.docker_microservices.ui.dto.TeachingClassDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
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

    /*
    @GetMapping("/classes")
    public ResponseEntity<List<TeachingClassDto>> listClasses(){

        return restTemplate
                .exchange("http://"+ serviceHost +"/class", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<TeachingClassDto>>() {});
    }
    */

    @GetMapping("/classes")
    public ResponseEntity<List<String>> listClasses(){
        return restTemplate
                .exchange("http://"+ serviceHost +"/class/str", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<String>>() {});
    }

}
