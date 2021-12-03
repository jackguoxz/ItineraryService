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
        itineraryDAO.save(new Itinerary(101+"",102+"","10", "13"));
        itineraryDAO.save(new Itinerary(102+"",101+"","10", "18"));
        itineraryDAO.save(new Itinerary("B",101+"","10", "15"));
        itineraryDAO.save(new Itinerary(101+"",104+"","10", "17"));
        itineraryDAO.save(new Itinerary(104+"",101+"","10", "12"));
        itineraryDAO.save(new Itinerary(102+"","B","10", "12"));
        itineraryDAO.save(new Itinerary("B",104+"","10", "11"));
        itineraryDAO.save(new Itinerary(102+"","A","10", "11"));
    }
}
