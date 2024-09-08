package org.codecool.backend.model.payload;

import lombok.Data;

@Data
public class CreateCityRequest {
    private String name;
    private String country;
    private String state;
    private double longitude;
    private double latitude;
}
