package org.codecool.backend.controller.time;

import org.codecool.backend.model.dto.SunriseDto;
import org.codecool.backend.model.dto.SunsetDto;
import org.codecool.backend.model.entity.City;
import org.codecool.backend.service.CityService;
import org.codecool.backend.service.SunriseService;
import org.codecool.backend.service.SunsetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api")
@RestController
public class SunTimeController {

    private final CityService cityService;
    private final SunsetService sunsetService;
    private final SunriseService sunriseService;

    @Autowired
    public SunTimeController(CityService cityService, SunsetService sunsetService, SunriseService sunriseService) {
        this.cityService = cityService;
        this.sunsetService = sunsetService;
        this.sunriseService = sunriseService;
    }

    @GetMapping("/sunrise")
    public List<SunriseDto> getSunrise(@RequestParam(required = false) LocalDate date, @RequestParam String city) {
        if (date == null) date = LocalDate.now();
        List<City> cities = getCities(city);
        return sunriseService.getSunriseList(date, cities);
    }

    @GetMapping("/sunset")
    public List<SunsetDto> getSunset(@RequestParam(required = false) LocalDate date, @RequestParam String city) {
        if (date == null) date = LocalDate.now();
        List<City> cities = getCities(city);
        return sunsetService.getSunsetList(date, cities);
    }

    private List<City> getCities(String cityName) {
        return cityService.getAllCityByName(cityName);
    }
}
