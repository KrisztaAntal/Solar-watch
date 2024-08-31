package org.codecool.backend.service;

import org.codecool.backend.model.dto.DtoMapper;
import org.codecool.backend.model.dto.SunriseDto;
import org.codecool.backend.model.dto.SunsetDto;
import org.codecool.backend.model.entity.City;
import org.codecool.backend.model.entity.Sunrise;
import org.codecool.backend.model.entity.Sunset;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SunsetService {
    private final SunriseSunsetService sunriseSunsetService;

    public SunsetService(SunriseSunsetService sunriseSunsetService) {
        this.sunriseSunsetService = sunriseSunsetService;
    }

    public List<SunsetDto> getSunsetList(LocalDate localDate, List<City> cities) {
        return cities.stream().map((city) -> {
            Sunset sunset = sunriseSunsetService.getSunsetFromApi(localDate, city);
            return DtoMapper.toSunsetDto(sunset);
        }).toList();
    }
}
