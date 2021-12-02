package com.example.developer.docker_microservices.service.controllers;

import com.example.developer.docker_microservices.service.dtos.ItineraryDto;
import com.example.developer.docker_microservices.service.services.ItineraryService;
import com.example.developer.docker_microservices.service.services.ItineraryServiceDB;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.net.CacheRequest;
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

    @RequestMapping(value = "/getitinerarybyconnection", method = RequestMethod.GET)
    @ResponseBody
    public String getItineraryByConnection(@RequestParam(value="departurecity")String departureCityId,@RequestParam(value="arrivalcity")String arrivalCityId){
        String result= "";
        try {
            result=itineraryService.getShortestItineraryByConnection(departureCityId,arrivalCityId);
        } catch(Exception e)
        {
            System.out.println(e.toString());
        }

        return result;
    }

    @RequestMapping(value = "/getitinerarybytime", method = RequestMethod.GET)
    @ResponseBody
    public String getItineraryByTime(@RequestParam(value="departurecity")String departureCityId,@RequestParam(value="arrivalcity")String arrivalCityId){
        String result= "";
        try {
             result = itineraryService.getShortestItineraryByTime(departureCityId, arrivalCityId);
        }catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return result;
    }

}
