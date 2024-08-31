package org.codecool.backend.service;

import org.codecool.backend.model.dto.DtoMapper;
import org.codecool.backend.model.dto.SunriseDto;
import org.codecool.backend.model.dto.SunsetDto;
import org.codecool.backend.model.entity.City;
import org.codecool.backend.model.entity.Sunrise;
import org.codecool.backend.model.entity.Sunset;
import org.codecool.backend.repository.SunsetRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SunsetService {
    private final SunriseSunsetService sunriseSunsetService;
    private final SunsetRepository sunsetRepository;


    public SunsetService(SunriseSunsetService sunriseSunsetService, SunsetRepository sunsetRepository) {
        this.sunriseSunsetService = sunriseSunsetService;
        this.sunsetRepository = sunsetRepository;
    }

    public List<SunsetDto> getSunsetList(LocalDate localDate, List<City> cities) {
        return cities.stream().map((city) -> {
            Optional<Sunset> sunsetFromDB = sunsetRepository.findByDateAndCity(localDate, city);
            if (sunsetFromDB.isPresent()) {
                Sunset foundSunset = sunsetFromDB.get();
                return DtoMapper.toSunsetDto(foundSunset);
            } else {
                sunriseSunsetService.saveSunsetSunriseFromAPI(localDate, city);
                Sunset sunset = sunsetRepository.findByDateAndCity(localDate, city).orElseThrow();
                return DtoMapper.toSunsetDto(sunset);
            }
        }).toList();
    }
}
