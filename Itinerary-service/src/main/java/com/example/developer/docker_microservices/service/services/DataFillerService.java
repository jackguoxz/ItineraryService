package com.example.developer.docker_microservices.service.services;

import com.example.developer.docker_microservices.service.dao.ItineraryDao;
import com.example.developer.docker_microservices.service.entities.Itinerary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
public class DataFillerService {
    private final ItineraryDao itineraryDAO;

    public DataFillerService(ItineraryDao itineraryDAO) {
        this.itineraryDAO = itineraryDAO;

    }

    @PostConstruct
    @Transactional
    public void fillData(){
        /*
        itineraryDAO.save(new Itinerary(0,1,"10", "13"));
        itineraryDAO.save(new Itinerary(1,0,"10", "18"));
        itineraryDAO.save(new Itinerary(2,0,"10", "15"));
        itineraryDAO.save(new Itinerary(0,3,"10", "17"));
        itineraryDAO.save(new Itinerary(3,0,"10", "12"));
        itineraryDAO.save(new Itinerary(1,2,"10", "12"));
        itineraryDAO.save(new Itinerary(2,3,"10", "11"));
        itineraryDAO.save(new Itinerary(1,5,"10", "11"));
        */
        itineraryDAO.save(new Itinerary(101,1+101,"10", "13"));
        itineraryDAO.save(new Itinerary(1+101,101,"10", "18"));
        itineraryDAO.save(new Itinerary(2+101,101,"10", "15"));
        itineraryDAO.save(new Itinerary(101,3+101,"10", "17"));
        itineraryDAO.save(new Itinerary(3+101,101,"10", "12"));
        itineraryDAO.save(new Itinerary(1+101,2+101,"10", "12"));
        itineraryDAO.save(new Itinerary(2+101,3+101,"10", "11"));
        itineraryDAO.save(new Itinerary(1+101,5+101,"10", "11"));

    }
}
