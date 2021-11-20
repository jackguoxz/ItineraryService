package com.okta.developer.docker_microservices.ui.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItineraryDto {
    private long id;
    private int originalCityId;
    private int destinationCityId;
    private String departureTimeName;
    private String arrivalTimeName;
}
