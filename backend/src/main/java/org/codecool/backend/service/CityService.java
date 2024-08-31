package org.codecool.backend.service;

import org.codecool.backend.controller.exception.InvalidCityException;
import org.codecool.backend.model.entity.City;
import org.codecool.backend.model.report.city.OpenWeatherCityReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
public class CityService {
    @Value("${codecool.app.api.key}")
    private String API_KEY;

    private final WebClient webClient;

    private static final Logger logger = LoggerFactory.getLogger(CityService.class);

    @Autowired
    public CityService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<City> getAllCityByName(String city) {
        return Arrays.stream(getOpenWeatherDataArray(city))
                .map((cityData) -> new City(
                        cityData.name(),
                        cityData.country(),
                        cityData.state(),
                        cityData.lon(),
                        cityData.lat()))
                .toList();
    }

    private OpenWeatherCityReport[] getOpenWeatherDataArray(String city) {
        String url = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=10&appid=%s", city, API_KEY);
        OpenWeatherCityReport[] cityDataArray = webClient.get().uri(url).retrieve().bodyToMono(OpenWeatherCityReport[].class).log().block();

        logger.info("Response from Geocoding API: {}", cityDataArray);

        if (cityDataArray == null || cityDataArray.length == 0) {
            throw new InvalidCityException();
        }
        return cityDataArray;
    }
}
