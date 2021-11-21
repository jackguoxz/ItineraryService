package com.example.developer.docker_microservices.service.controllers;


import com.example.developer.docker_microservices.service.services.ItineraryService;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/itinerarylist")
public class ItineraryController {
    private final ItineraryService itineraryService;

    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @RequestMapping(value = "/get/{name}/{pwd}/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<String> getShortestItinerary(@PathVariable String name, @PathVariable String pwd,@PathVariable String id){
        List<String> result = new ArrayList<>();
        if(!name.equals("zhangsan")||!pwd.equals("12345")) {
            return result;
        }
        int cityId=Integer.parseInt(id);
        List<String> shortestItineraryByConnection = itineraryService.getShortestItineraryByConnection(cityId);
        List<String> shortestItineraryByTime = itineraryService.getShortestItineraryByTime(cityId);
        result.add("shortestItineraryByConnection");
        for (int i = 0; i < shortestItineraryByConnection.size(); i++) {
            result.add(shortestItineraryByConnection.get(i));
        }
        result.add("shortestItineraryByTime");
        for (int i = 0; i < shortestItineraryByTime.size(); i++) {
            result.add(shortestItineraryByTime.get(i));
        }
        return result;

    }

}
