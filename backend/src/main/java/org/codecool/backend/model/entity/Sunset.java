package org.codecool.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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
