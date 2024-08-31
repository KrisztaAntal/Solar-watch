package org.codecool.backend.model.dto;

import java.time.LocalDate;

public record SunriseDto(LocalDate date, String time, CityDto city) {
}
