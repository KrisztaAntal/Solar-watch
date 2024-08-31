package org.codecool.backend.model.report.city;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenWeatherCityReport(String name, double lat, double lon, String country, String state) {
}
