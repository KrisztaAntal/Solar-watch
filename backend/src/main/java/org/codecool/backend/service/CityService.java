package org.codecool.backend.service;

import org.codecool.backend.controller.exception.InvalidCityException;
import org.codecool.backend.model.entity.City;
import org.codecool.backend.model.report.city.OpenWeatherCityReport;
import org.codecool.backend.repository.CityRepository;
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

    private final CityRepository cityRepository;

    private static final Logger logger = LoggerFactory.getLogger(CityService.class);

    @Autowired
    public CityService(WebClient webClient, CityRepository cityRepository) {
        this.webClient = webClient;
        this.cityRepository = cityRepository;
    }

    public List<City> getAllCityByName(String city) {
        List<City> citiesFromDB = cityRepository.findAllByName(city);
        if (citiesFromDB.isEmpty()) {
            OpenWeatherCityReport[] citiesData = getOpenWeatherDataArray(city);
            saveCityDataFromOWCApi(citiesData);
            return cityRepository.findAllByName(city);
        } else return citiesFromDB;
    }

    private void saveCityDataFromOWCApi(OpenWeatherCityReport[] citiesData) {
        for (OpenWeatherCityReport cityReport : citiesData) {
            City newCity = new City(cityReport.name(), cityReport.country(), cityReport.state(), cityReport.lon(), cityReport.lat());
            if (checkIfNotInDB(newCity)) {
                cityRepository.save(newCity);
            }
        }
    }

    private boolean checkIfNotInDB(City city) {
        return cityRepository.findByLongitudeAndLatitude(city.getLongitude(), city.getLatitude())
                .isEmpty();
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
