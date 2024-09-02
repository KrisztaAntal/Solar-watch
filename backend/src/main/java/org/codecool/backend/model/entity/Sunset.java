package org.codecool.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Sunset {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;
    private String timeOfSunset;
    @ManyToOne
    private City city;

    public Sunset() {
    }

    public Sunset(LocalDate date, String timeOfSunset, City city) {
        this.date = date;
        this.timeOfSunset = timeOfSunset;
        this.city = city;
    }

}
