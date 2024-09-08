package org.codecool.backend.service;

import jakarta.persistence.EntityNotFoundException;
import org.codecool.backend.controller.exception.EntityAlreadyInDBException;
import org.codecool.backend.model.dto.CityDto;
import org.codecool.backend.model.dto.DtoMapper;
import org.codecool.backend.model.dto.SunsetDto;
import org.codecool.backend.model.entity.City;
import org.codecool.backend.model.entity.Sunset;
import org.codecool.backend.model.payload.CreateSunsetRequest;
import org.codecool.backend.repository.CityRepository;
import org.codecool.backend.repository.SunsetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SunsetServiceTest {
    private SunriseSunsetService sunriseSunsetServiceMock;
    private SunsetRepository sunsetRepositoryMock;
    private CityService cityServiceMock;
    private CityRepository cityRepositoryMock;

    @BeforeEach
    public void setUp() {
        sunriseSunsetServiceMock = Mockito.mock(SunriseSunsetService.class);
        sunsetRepositoryMock = Mockito.mock(SunsetRepository.class);
        cityRepositoryMock = Mockito.mock(CityRepository.class);
        cityServiceMock = Mockito.mock(CityService.class);
    }

    @Test
    public void testSunsetIfInDB(){
        SunsetService sunsetService = new SunsetService(sunriseSunsetServiceMock, sunsetRepositoryMock, cityRepositoryMock, cityServiceMock);
        LocalDate localDate = LocalDate.of(2024, 8, 17);
        City city = new City("Budapest", "HU", null, 19.0403594, 47.4979937);
        String dateOfSunset = "3:16:40 PM";
        Sunset sunset = new Sunset(localDate, dateOfSunset, city);
        SunsetDto sunsetDto = DtoMapper.toSunsetDto(sunset);

        Mockito.when(sunsetRepositoryMock.findByDateAndCity(localDate, city)).thenReturn(Optional.of(sunset));

        List<City> cities = List.of(city);

        List<SunsetDto> expected = List.of(sunsetDto);
        List<SunsetDto> actual = sunsetService.getSunsetList(localDate, cities);

        assertEquals(expected, actual);
    }

    @Test
    public void testSunsetIfNotInDB() {
        SunsetService sunsetService = new SunsetService(sunriseSunsetServiceMock, sunsetRepositoryMock, cityRepositoryMock, cityServiceMock);
        LocalDate localDate = LocalDate.of(2024, 8, 17);
        City city = new City("Budapest", "HU", null, 19.0403594, 47.4979937);
        Optional<Sunset> sunset = Optional.empty();
        Mockito.when(sunsetRepositoryMock.findByDateAndCity(localDate, city)).thenReturn(sunset);
        List<City> cities = List.of(city);

        assertThrows(EntityNotFoundException.class, () -> sunsetService.getSunsetList(localDate, cities));
        Mockito.verify(sunriseSunsetServiceMock, Mockito.times(1)).saveSunsetSunriseFromAPI(localDate, city);
    }

    @Test
    public void testAddNewSunsetIfSunsetInDBAndSunsetCouldNotBeSaved() {
        SunsetService sunsetService = new SunsetService(sunriseSunsetServiceMock, sunsetRepositoryMock, cityRepositoryMock, cityServiceMock);
        LocalDate localDate = LocalDate.of(2024, 8, 18);
        City city = new City("Budapest", "HU", null, 19.0403594, 47.4979937);
        CityDto cityDto = new CityDto("Budapest",47.4979937,19.0403594,"HU", ""  );
        String timeOfSunset = "3:16:40 PM";
        Sunset newSunset = new Sunset(localDate, timeOfSunset, city);
        CreateSunsetRequest request = new CreateSunsetRequest(localDate, timeOfSunset, cityDto);

        Mockito.when(cityRepositoryMock.findByLongitudeAndLatitude(19.0403594, 47.4979937)).thenReturn(Optional.of(city));
        Mockito.when(sunsetRepositoryMock.findByDateAndCity(localDate, city)).thenReturn(Optional.of(newSunset));

        assertThrows(EntityAlreadyInDBException.class, ()->sunsetService.addNewSunset(request));
    }
}
