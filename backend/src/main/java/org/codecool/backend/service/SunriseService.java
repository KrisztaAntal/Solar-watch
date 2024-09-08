package org.codecool.backend.service;

import jakarta.persistence.EntityNotFoundException;
import org.codecool.backend.controller.exception.EntityAlreadyInDBException;
import org.codecool.backend.model.dto.DtoMapper;
import org.codecool.backend.model.dto.SunriseDto;
import org.codecool.backend.model.entity.City;
import org.codecool.backend.model.entity.Sunrise;
import org.codecool.backend.model.payload.CreateSunriseRequest;
import org.codecool.backend.repository.CityRepository;
import org.codecool.backend.repository.SunriseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SunriseService {
    private final SunriseSunsetService sunriseSunsetService;
    private final SunriseRepository sunriseRepository;
    private final CityRepository cityRepository;
    private final CityService cityService;

    public SunriseService(SunriseSunsetService sunriseSunsetService, SunriseRepository sunriseRepository, CityRepository cityRepository, CityService cityService) {
        this.sunriseSunsetService = sunriseSunsetService;
        this.sunriseRepository = sunriseRepository;
        this.cityRepository = cityRepository;
        this.cityService = cityService;
    }

    public List<SunriseDto> getSunriseList(LocalDate localDate, List<City> cities) {
        return cities.stream().map((city) -> {
                    Optional<Sunrise> sunriseFromDB = sunriseRepository.findByDateAndCity(localDate, city);
                    if (sunriseFromDB.isPresent()) {
                        Sunrise foundSunrise = sunriseFromDB.get();
                        return DtoMapper.toSunriseDto(foundSunrise);
                    } else {
                        sunriseSunsetService.saveSunsetSunriseFromAPI(localDate, city);
                        Sunrise sunrise = sunriseRepository.findByDateAndCity(localDate, city).orElseThrow();
                        return DtoMapper.toSunriseDto(sunrise);
                    }
                })
                .toList();
    }

    public SunriseDto addNewSunrise(CreateSunriseRequest request) {
        Optional<City> cityFromDB = cityRepository.findByLongitudeAndLatitude(request.getCity().lon(), request.getCity().lat());
        if (cityFromDB.isEmpty()) {
            City city = cityService.saveCityInDB(request.getCity().name(), request.getCity().country(), request.getCity().state(), request.getCity().lon(), request.getCity().lat());
            Sunrise newSunrise = saveSunriseInDB(request.getDate(), request.getTimeOfSunrise(), city);
            return DtoMapper.toSunriseDto(newSunrise);
        } else {
            City city = cityFromDB.orElseThrow();
            Sunrise newSunrise = saveSunriseInDB(request.getDate(), request.getTimeOfSunrise(), city);
            return DtoMapper.toSunriseDto(newSunrise);
        }
    }

    private Sunrise saveSunriseInDB(LocalDate date, String timeOfSunrise, City city) {
        Sunrise newSunrise = new Sunrise(date, timeOfSunrise, city);
        if (checkIfSunriseNotInDB(date, city)) {
            sunriseRepository.save(newSunrise);
            return sunriseRepository.findByDateAndCity(date, city).orElseThrow();
        } else throw new EntityAlreadyInDBException("Sunrise is already in the database");
    }

    private boolean checkIfSunriseNotInDB(LocalDate date, City city) {
        return sunriseRepository.findByDateAndCity(date, city).isEmpty();
    }

    public SunriseDto updateSunrise(LocalDate date, String name, String country, String state, SunriseDto requestDto) {
        City city = cityRepository.findByNameAndCountryAndState(name, country, state).orElseThrow(() -> new EntityNotFoundException("City in not in database"));
        Sunrise sunrise = sunriseRepository.findByDateAndCity(date, city).orElseThrow(() -> new EntityNotFoundException("Sunrise is not in database"));
        if (requestDto.time() != null) {
            sunrise.setTimeOfSunrise(requestDto.time());
        }
        sunriseRepository.save(sunrise);
        return new SunriseDto(sunrise.getDate(), sunrise.getTimeOfSunrise(), DtoMapper.toCityDto(sunrise.getCity()));
    }

    public void deleteSunrise(LocalDate date, String cityName, String cityCountry, String cityState) {
        City city = cityRepository.findByNameAndCountryAndState(cityName, cityCountry, cityState).orElseThrow(() -> new EntityNotFoundException("City in not in database"));
        Sunrise sunrise = sunriseRepository.findByDateAndCity(date, city).orElseThrow(() -> new EntityNotFoundException("Sunrise is not in database"));
        sunriseRepository.delete(sunrise);
    }
}
