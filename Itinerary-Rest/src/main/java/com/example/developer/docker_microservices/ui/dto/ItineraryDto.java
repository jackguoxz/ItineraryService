package com.example.developer.docker_microservices.ui.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItineraryDto {
    private long id;
    private String originalCityId;
    private String destinationCityId;
    private String departureTimeName;
    private String arrivalTimeName;
}
