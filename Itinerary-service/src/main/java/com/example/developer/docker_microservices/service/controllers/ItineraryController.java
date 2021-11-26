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


    public boolean checkCityId(String Id)
    {
        int cityId = Integer.parseInt(Id);
        Set<Integer> originalCityList=itineraryService.getOriginalCityIdList();
        if(!originalCityList.contains(cityId) )
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
        int cityId=Integer.parseInt(id);
        List<String> shortestItineraryByConnection = itineraryService.getShortestItineraryByConnection(cityId);
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
        int cityId=Integer.parseInt(id);
        List<String> shortestItineraryByTime = itineraryService.getShortestItineraryByTime(cityId);
        for (int i = 0; i < shortestItineraryByTime.size(); i++) {
            result.add(shortestItineraryByTime.get(i));
        }
        return result;
    }

    @RequestMapping(value = "/listitinerary/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<String> getShortestItinerary(@PathVariable String id){
        List<String> result = new ArrayList<>();
        if(checkCityId(id))
        {
            result.add("Invalid Original City Id");
            return  result;
        };
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

    @RequestMapping(value = "/listitinerary/bymap/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String,List<String>> getShortestItineraryByMap(@PathVariable String id){
        Map<String,List<String>> map =new HashMap();
        if(checkCityId(id)) {
            List<String> string=new ArrayList<>();
            string.add(id);
            map.put("Invalid Original City Id",string);
            return  map;
        };
        int cityId=Integer.parseInt(id);
        List<String> shortestItineraryByConnection = itineraryService.getShortestItineraryByConnection(cityId);
        List<String> shortestItineraryByTime = itineraryService.getShortestItineraryByTime(cityId);
        map.put("connection",shortestItineraryByConnection);
        map.put("time",shortestItineraryByTime);
        return map;
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
        int cityId=Integer.parseInt(id);
        List<String>  shortestItineraryByTimeByDijkstra= itineraryService.getShortestItineraryByTimeByDijkstra(cityId);
        List<String>  shortestItineraryByConectionByDijkstra= itineraryService.getShortestItineraryByTimeByDijkstra(cityId);
        result.add("Connection");
        for (int i = 0; i < shortestItineraryByTimeByDijkstra.size(); i++) {
            result.add(shortestItineraryByTimeByDijkstra.get(i));
        }
        result.add("Time");
        for (int i = 0; i < shortestItineraryByConectionByDijkstra.size(); i++) {
            result.add(shortestItineraryByConectionByDijkstra.get(i));
        }
        return  result;
    }


}
