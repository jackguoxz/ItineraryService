package com.okta.developer.docker_microservices.service.controllers;


import com.okta.developer.docker_microservices.service.dtos.ItineraryDto;
import com.okta.developer.docker_microservices.service.services.ItineraryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@RestController("/class")
@RestController
@RequestMapping("/class")
public class ItineraryController {


    private final ItineraryService itineraryService;

    public ItineraryController(ItineraryService itineraryService) {
        //System.out.println(getById(id));
        this.itineraryService = itineraryService;
    }

    @GetMapping
    public List<ItineraryDto> listClasses(){
        return itineraryService.listClasses();
    }

    /*
    @GetMapping(path = "str")
    public List<ItineraryDto> listClassesByCityId(){
        return itineraryService.listClasses();
    }
    */

    @GetMapping(path = "str")
    public List<String> listClassesByCityId(){
        return itineraryService.getShortestItineraryByTime(1);
    }



}
