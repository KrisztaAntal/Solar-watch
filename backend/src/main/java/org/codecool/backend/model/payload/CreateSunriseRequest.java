package org.codecool.backend.model.payload;

import lombok.Data;
import org.codecool.backend.model.dto.CityDto;

import java.time.LocalDate;

@Data
public class CreateSunriseRequest {
    private LocalDate date;
    private String timeOfSunrise;
    private CityDto city;

    public CreateSunriseRequest(LocalDate date, String timeOfSunrise, CityDto city) {
        this.date = date;
        this.timeOfSunrise = timeOfSunrise;
        this.city = city;
    }

    public CreateSunriseRequest() {
    }
}
