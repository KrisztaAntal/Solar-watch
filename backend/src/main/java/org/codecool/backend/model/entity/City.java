package org.codecool.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class City {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String country;
    private String state;
    private double longitude;
    private double latitude;

    public City() {
    }

    public City(String name, String country, String state, double longitude, double latitude) {
        this.name = name;
        this.country = country;
        this.state = state;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
