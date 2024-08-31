package org.codecool.backend.model.dto;

import java.time.LocalDate;

public record SunsetDto(LocalDate date, String time, CityDto city) {
}
