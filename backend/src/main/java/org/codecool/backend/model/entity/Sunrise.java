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
