package org.codecool.backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Sunrise {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;
    private String timeOfSunrise;
    @ManyToOne
    private City city;

    public Sunrise() {
    }

    public Sunrise(LocalDate date, String timeOfSunrise, City city) {
        this.date = date;
        this.timeOfSunrise = timeOfSunrise;
        this.city = city;
    }

}
