package com.example.developer.docker_microservices.service.controllers;

import com.example.developer.docker_microservices.service.dtos.ItineraryDto;
import com.example.developer.docker_microservices.service.services.ItineraryService;
import com.example.developer.docker_microservices.service.services.ItineraryServiceDB;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/itinerary")
@Api(value = "Itinerary Controller", description = "Controllers in Itinerary Service")
public class ItineraryController {
    private final ItineraryService itineraryService;
    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @ApiOperation(value="Get Itinerary Dto")
    @RequestMapping(value = "/getitinerarydto", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<ItineraryDto> getItineraryDto(){
        return ItineraryServiceDB.listItinerary();
    }

}
