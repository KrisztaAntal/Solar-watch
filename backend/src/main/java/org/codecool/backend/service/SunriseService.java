package org.codecool.backend.service;

import org.codecool.backend.model.dto.DtoMapper;
import org.codecool.backend.model.dto.SunriseDto;
import org.codecool.backend.model.entity.City;
import org.codecool.backend.model.entity.Sunrise;
import org.codecool.backend.repository.SunriseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SunriseService {
    private final SunriseSunsetService sunriseSunsetService;
    private final SunriseRepository sunriseRepository;

    public SunriseService(SunriseSunsetService sunriseSunsetService, SunriseRepository sunriseRepository) {
        this.sunriseSunsetService = sunriseSunsetService;
        this.sunriseRepository = sunriseRepository;
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
}
