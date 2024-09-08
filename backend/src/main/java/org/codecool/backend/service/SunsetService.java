package org.codecool.backend.service;

import jakarta.persistence.EntityNotFoundException;
import org.codecool.backend.controller.exception.EntityAlreadyInDBException;
import org.codecool.backend.model.dto.DtoMapper;
import org.codecool.backend.model.dto.SunriseDto;
import org.codecool.backend.model.dto.SunsetDto;
import org.codecool.backend.model.entity.City;
import org.codecool.backend.model.entity.Sunrise;
import org.codecool.backend.model.entity.Sunset;
import org.codecool.backend.model.payload.CreateSunsetRequest;
import org.codecool.backend.repository.CityRepository;
import org.codecool.backend.repository.SunsetRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SunsetService {
    private final SunriseSunsetService sunriseSunsetService;
    private final SunsetRepository sunsetRepository;
    private final CityRepository cityRepository;
    private final CityService cityService;


    public SunsetService(SunriseSunsetService sunriseSunsetService, SunsetRepository sunsetRepository, CityRepository cityRepository, CityService cityService) {
        this.sunriseSunsetService = sunriseSunsetService;
        this.sunsetRepository = sunsetRepository;
        this.cityRepository = cityRepository;
        this.cityService = cityService;
    }

    public List<SunsetDto> getSunsetList(LocalDate localDate, List<City> cities) {
        return cities.stream().map((city) -> {
            Optional<Sunset> sunsetFromDB = sunsetRepository.findByDateAndCity(localDate, city);
            if (sunsetFromDB.isPresent()) {
                Sunset foundSunset = sunsetFromDB.get();
                return DtoMapper.toSunsetDto(foundSunset);
            } else {
                sunriseSunsetService.saveSunsetSunriseFromAPI(localDate, city);
                Sunset sunset = sunsetRepository.findByDateAndCity(localDate, city).orElseThrow(()-> new EntityNotFoundException("Sunset is not in database"));
                return DtoMapper.toSunsetDto(sunset);
            }
        }).toList();
    }

    public SunsetDto addNewSunset(CreateSunsetRequest request) {
        Optional<City> cityFromDB = cityRepository.findByLongitudeAndLatitude(request.getCity().lon(), request.getCity().lat());
        if (cityFromDB.isEmpty()) {
            City city = cityService.saveCityInDB(request.getCity().name(), request.getCity().country(), request.getCity().state(), request.getCity().lon(), request.getCity().lat());
            Sunset newSunset = saveSunsetInDB(request.getDate(), request.getTimeOfSunset(), city);
            return DtoMapper.toSunsetDto(newSunset);
        } else {
            City city = cityFromDB.orElseThrow();
            Sunset newSunset = saveSunsetInDB(request.getDate(), request.getTimeOfSunset(), city);
            return DtoMapper.toSunsetDto(newSunset);
        }
    }

    private Sunset saveSunsetInDB(LocalDate date, String timeOfSunset, City city) {
        Sunset newSunset = new Sunset(date, timeOfSunset, city);
        if (checkIfSunsetNotInDB(date, city)) {
            sunsetRepository.save(newSunset);
            return sunsetRepository.findByDateAndCity(date, city).orElseThrow(()-> new EntityNotFoundException("Sunset is not in database"));
        } else throw new EntityAlreadyInDBException("Sunset is already in the database");
    }

    private boolean checkIfSunsetNotInDB(LocalDate date, City city) {
        return sunsetRepository.findByDateAndCity(date, city).isEmpty();
    }

    public SunsetDto updateSunset(LocalDate date, String cityName, String cityCountry, String cityState, SunsetDto requestDto) {
        City city = cityRepository.findByNameAndCountryAndState(cityName, cityCountry, cityState).orElseThrow(()-> new EntityNotFoundException("City is not in database"));
        Sunset sunset = sunsetRepository.findByDateAndCity(date, city).orElseThrow(()-> new EntityNotFoundException("Sunset is not in database"));
        if (requestDto.time() != null) {
            sunset.setTimeOfSunset(requestDto.time());
        }
        sunsetRepository.save(sunset);
        return new SunsetDto(sunset.getDate(), sunset.getTimeOfSunset(), DtoMapper.toCityDto(sunset.getCity()));
    }

    public void deleteSunset(LocalDate date, String cityName, String cityCountry, String cityState) {
        City city = cityRepository.findByNameAndCountryAndState(cityName, cityCountry, cityState).orElseThrow(()-> new EntityNotFoundException("City is not in database"));
        Sunset sunset = sunsetRepository.findByDateAndCity(date, city).orElseThrow(()-> new EntityNotFoundException("Sunset is not in database"));
        sunsetRepository.delete(sunset);
    }
}
