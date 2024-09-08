package org.codecool.backend.service;

import org.codecool.backend.model.entity.City;
import org.codecool.backend.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CityServiceTest {

    private CityRepository cityRepositoryMock;
    private WebClient webClientMock;

    @BeforeEach
    public void setup(){
        cityRepositoryMock = Mockito.mock(CityRepository.class);
        webClientMock = Mockito.mock(WebClient.class);
    }

    @Test
    public void testGetAllCityByNameIfInDB(){
        CityService cityService = new CityService(webClientMock, cityRepositoryMock);
        City city1 = new City("Budapest", "HU", null, 19.0403594, 47.4979937);
        City city2 = new City("Budapest", "US", "Missouri", 36.7689441, -90.710953);
        String cityName = "Budapest";
        List<City> cities = List.of(city1, city2);
        Mockito.when(cityRepositoryMock.findAllByName(cityName)).thenReturn(cities);

        List<City> expected = cities;
        List<City> actual = cityService.getAllCityByName(cityName);

        assertEquals(expected, actual);
    }
}
