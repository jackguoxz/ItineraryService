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


    @RequestMapping(value = "/getshortestitinerarybyconnection/{name}/{pwd}/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<String> getShortestItineraryByConnection(@PathVariable String name, @PathVariable String pwd,@PathVariable String id){
        List<String> result = new ArrayList<>();
        if(!name.equals("user")||!pwd.equals("password")) {
            return result;
        }
        int cityId=Integer.parseInt(id);
        List<String> shortestItineraryByConnection = itineraryService.getShortestItineraryByConnection(cityId);
        for (int i = 0; i < shortestItineraryByConnection.size(); i++) {
            result.add(shortestItineraryByConnection.get(i));
        }
        return result;
    }

    @RequestMapping(value = "/getshortestitinerarybytime/{name}/{pwd}/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<String> getShortestItineraryByTime(@PathVariable String name, @PathVariable String pwd,@PathVariable String id){
        List<String> result = new ArrayList<>();
        if(!name.equals("user")||!pwd.equals("password")) {
            return result;
        }
        int cityId=Integer.parseInt(id);
        List<String> shortestItineraryByTime = itineraryService.getShortestItineraryByTime(cityId);
        for (int i = 0; i < shortestItineraryByTime.size(); i++) {
            result.add(shortestItineraryByTime.get(i));
        }
        return result;
    }

    @RequestMapping(value = "/listitinerary/{name}/{pwd}/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public List<String> getShortestItinerary(@PathVariable String name, @PathVariable String pwd,@PathVariable String id){
        List<String> result = new ArrayList<>();
        if(!name.equals("user")||!pwd.equals("password")) {
            return result;
        }

        int cityId = Integer.parseInt(id);
        Set<Integer> originalCityList=itineraryService.getOriginalCityIdList();
        Iterator it = originalCityList.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        if(!originalCityList.contains(cityId) )
        {
            result.add("Invalid Departure CityId");
            return result;
        }
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

    @RequestMapping(value = "/listitinerary/bymap/{name}/{pwd}/{id}", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Map<String,List<String>> getShortestItineraryByMap(@PathVariable String name, @PathVariable String pwd,@PathVariable String id){
        Map<String,List<String>> map =new HashMap();
        if(!name.equals("user")||!pwd.equals("password")) {
            return map;
        }
        int cityId=Integer.parseInt(id);
        List<String> shortestItineraryByConnection = itineraryService.getShortestItineraryByConnection(cityId);
        List<String> shortestItineraryByTime = itineraryService.getShortestItineraryByTime(cityId);
        map.put("connection",shortestItineraryByConnection);
        map.put("time",shortestItineraryByConnection);
        return map;
    }
}
