package org.codecool.backend.model.payload;

import lombok.Data;
import org.codecool.backend.model.dto.CityDto;

import java.time.LocalDate;

@Data
public class CreateSunsetRequest {
    private LocalDate date;
    private String timeOfSunset;
    private CityDto city;

    public CreateSunsetRequest(LocalDate date, String timeOfSunset, CityDto city) {
        this.date = date;
        this.timeOfSunset = timeOfSunset;
        this.city = city;
    }

    public CreateSunsetRequest() {
    }
}
