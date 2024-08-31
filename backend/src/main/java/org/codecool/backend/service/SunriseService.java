package org.codecool.backend.service;

import org.codecool.backend.model.dto.DtoMapper;
import org.codecool.backend.model.dto.SunriseDto;
import org.codecool.backend.model.entity.City;
import org.codecool.backend.model.entity.Sunrise;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SunriseService {
    private final SunriseSunsetService sunriseSunsetService;


    public SunriseService(SunriseSunsetService sunriseSunsetService) {
        this.sunriseSunsetService = sunriseSunsetService;
    }

    public List<SunriseDto> getSunriseList(LocalDate localDate, List<City> cities) {
        return cities.stream().map((city) -> {
                    Sunrise sunrise = sunriseSunsetService.getSunriseFromApi(localDate, city);
                    return DtoMapper.toSunriseDto(sunrise);
                }).toList();
    }
}
