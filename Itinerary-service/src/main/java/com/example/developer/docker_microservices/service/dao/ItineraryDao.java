package com.example.developer.docker_microservices.service.dao;

import com.example.developer.docker_microservices.service.entities.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraryDao extends JpaRepository<Itinerary, Long> {
}
