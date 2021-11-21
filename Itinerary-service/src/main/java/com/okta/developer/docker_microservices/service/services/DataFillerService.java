package com.okta.developer.docker_microservices.service.services;

import com.okta.developer.docker_microservices.service.dao.*;
import com.okta.developer.docker_microservices.service.entities.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
public class DataFillerService {
    private final ItineraryDao itineraryDAO;

    public DataFillerService(ItineraryDao itineraryDAO) {
        this.itineraryDAO = itineraryDAO;

    }

    @PostConstruct
    @Transactional
    public void fillData(){
        itineraryDAO.save(new Itinerary(0,1,"10", "13"));
        itineraryDAO.save(new Itinerary(1,0,"10", "18"));
        itineraryDAO.save(new Itinerary(2,0,"10", "15"));
        itineraryDAO.save(new Itinerary(0,3,"10", "17"));
        itineraryDAO.save(new Itinerary(3,0,"10", "12"));
        itineraryDAO.save(new Itinerary(1,2,"10", "12"));
        itineraryDAO.save(new Itinerary(2,3,"10", "11"));

    }
}
