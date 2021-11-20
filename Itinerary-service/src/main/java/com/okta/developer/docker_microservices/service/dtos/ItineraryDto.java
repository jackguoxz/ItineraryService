package com.okta.developer.docker_microservices.service.dtos;

import lombok.Builder;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Builder
public class ItineraryDto {
    private long id;
    private int originalCityId;
    private int destinationCityId;
    private String departureTimeName;
    private String arrivalTimeName;
}
