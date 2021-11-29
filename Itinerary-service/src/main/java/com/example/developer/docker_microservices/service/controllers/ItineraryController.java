package com.example.developer.docker_microservices.service.controllers;


import com.example.developer.docker_microservices.service.services.ItineraryService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/itinerary")
public class ItineraryController {
    private final ItineraryService itineraryService;
    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    public boolean checkCityId(String id)
    {
        Set<String> originalCityList=itineraryService.getOriginalCityIdList();
        if(!originalCityList.contains(id) )
        {
            return  true;
        }
        return false;
    }
    @RequestMapping(value = "/getshortestitinerarybyconnection/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<String> getShortestItineraryByConnection(@PathVariable String id){
        List<String> result = new ArrayList<>();
        if(checkCityId(id))
        {
            result.add("Invalid Original City Id");
            return  result;
        };
        List<String> shortestItineraryByConnection = itineraryService.getShortestItineraryByConnectionByDijkstra(id);
        for (int i = 0; i < shortestItineraryByConnection.size(); i++) {
            result.add(shortestItineraryByConnection.get(i));
        }
        return result;
    }

    @RequestMapping(value = "/getshortestitinerarybytime/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<String> getShortestItineraryByTime(@PathVariable String id){
        List<String> result = new ArrayList<>();
        if(checkCityId(id))
        {
            result.add("Invalid Original City Id");
            return  result;
        };
        List<String> shortestItineraryByTime = itineraryService.getShortestItineraryByTimeByDijkstra(id);
        for (int i = 0; i < shortestItineraryByTime.size(); i++) {
            result.add(shortestItineraryByTime.get(i));
        }
        return result;
    }

    @RequestMapping(value = "/listitinerary/dijkstra/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<String> getShortestItineraryByDijkstra(@PathVariable String id){
        List<String> result = new ArrayList<>();
        if(checkCityId(id))
        {
            result.add("Invalid Original City Id");
            return  result;
        };
        //int cityId=Integer.parseInt(id);
        List<String>  shortestItineraryByTimeByDijkstra= itineraryService.getShortestItineraryByTimeByDijkstra(id);
        List<String>  shortestItineraryByConnectionByDijkstra= itineraryService.getShortestItineraryByConnectionByDijkstra(id);
        result.add("Time");
        for (int i = 0; i < shortestItineraryByTimeByDijkstra.size(); i++) {
            result.add(shortestItineraryByTimeByDijkstra.get(i));
        }
        result.add("Connection");
        for (int i = 0; i < shortestItineraryByConnectionByDijkstra.size(); i++) {
            result.add(shortestItineraryByConnectionByDijkstra.get(i));
        }
        return  result;
    }


}
