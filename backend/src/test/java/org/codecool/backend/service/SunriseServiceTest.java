package org.codecool.backend.service;

import org.codecool.backend.controller.exception.EntityAlreadyInDBException;
import org.codecool.backend.model.dto.CityDto;
import org.codecool.backend.model.dto.DtoMapper;
import org.codecool.backend.model.dto.SunriseDto;
import org.codecool.backend.model.entity.City;
import org.codecool.backend.model.entity.Sunrise;
import org.codecool.backend.model.payload.CreateSunriseRequest;
import org.codecool.backend.repository.CityRepository;
import org.codecool.backend.repository.SunriseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SunriseServiceTest {
    private SunriseSunsetService sunriseSunsetServiceMock;
    private SunriseRepository sunriseRepositoryMock;
    private CityService cityServiceMock;
    private CityRepository cityRepositoryMock;

    @BeforeEach
    public void setUp() {
        sunriseSunsetServiceMock = Mockito.mock(SunriseSunsetService.class);
        sunriseRepositoryMock = Mockito.mock(SunriseRepository.class);
        cityServiceMock = Mockito.mock(CityService.class);
        cityRepositoryMock = Mockito.mock(CityRepository.class);
    }

    @Test
    public void testGetSunriseListIfInDB() {
        SunriseService sunriseService = new SunriseService(sunriseSunsetServiceMock, sunriseRepositoryMock, cityRepositoryMock, cityServiceMock);
        LocalDate localDate = LocalDate.of(2024, 8, 17);
        City city = new City("Budapest", "HU", null, 19.0403594, 47.4979937);
        String dateOfSunrise = "3:41:41 AM";
        Sunrise sunrise = new Sunrise(localDate, dateOfSunrise, city);
        SunriseDto sunriseDto = DtoMapper.toSunriseDto(sunrise);

        Mockito.when(sunriseRepositoryMock.findByDateAndCity(localDate, city)).thenReturn(Optional.of(sunrise));

        List<City> cities = List.of(city);

        List<SunriseDto> expected = List.of(sunriseDto);
        List<SunriseDto> actual = sunriseService.getSunriseList(localDate, cities);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetSunriseListIfNotInDB() {
        SunriseService sunriseService = new SunriseService(sunriseSunsetServiceMock, sunriseRepositoryMock, cityRepositoryMock, cityServiceMock);
        LocalDate localDate = LocalDate.of(2024, 8, 17);
        City city = new City("Budapest", "HU", null, 19.0403594, 47.4979937);
        Optional<Sunrise> sunrise = Optional.empty();
        Mockito.when(sunriseRepositoryMock.findByDateAndCity(localDate, city)).thenReturn(sunrise);
        List<City> cities = List.of(city);

        assertThrows(NoSuchElementException.class, () -> sunriseService.getSunriseList(localDate, cities));
        Mockito.verify(sunriseSunsetServiceMock, Mockito.times(1)).saveSunsetSunriseFromAPI(localDate, city);
    }

    @Test
    public void testAddNewSunriseIfSunriseInDBAndSunriseCouldNotBeSaved() {
        SunriseService sunriseService = new SunriseService(sunriseSunsetServiceMock, sunriseRepositoryMock, cityRepositoryMock, cityServiceMock);
        LocalDate localDate = LocalDate.of(2024, 8, 17);
        City city = new City("Budapest", "HU", null, 19.0403594, 47.4979937);
        CityDto cityDto = new CityDto("Budapest",47.4979937,19.0403594,"HU", ""  );
        String timeOfSunrise = "3:41:41 AM";
        Sunrise newSunrise = new Sunrise(localDate, timeOfSunrise, city);
        CreateSunriseRequest request = new CreateSunriseRequest(localDate, timeOfSunrise, cityDto);

        Mockito.when(cityRepositoryMock.findByLongitudeAndLatitude(19.0403594, 47.4979937)).thenReturn(Optional.of(city));
        Mockito.when(sunriseRepositoryMock.findByDateAndCity(localDate, city)).thenReturn(Optional.of(newSunrise));

        assertThrows(EntityAlreadyInDBException.class, ()->sunriseService.addNewSunrise(request));
    }
}
