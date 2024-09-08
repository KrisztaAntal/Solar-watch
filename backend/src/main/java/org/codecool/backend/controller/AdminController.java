package org.codecool.backend.controller;

import org.codecool.backend.model.dto.CityDto;
import org.codecool.backend.model.dto.SunriseDto;
import org.codecool.backend.model.payload.CreateCityRequest;
import org.codecool.backend.model.payload.CreateSunriseRequest;
import org.codecool.backend.service.CityService;
import org.codecool.backend.service.MemberService;
import org.codecool.backend.service.SunriseService;
import org.codecool.backend.service.SunsetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequestMapping("/api")
@RestController
public class AdminController {

    private final CityService cityService;
    private final SunriseService sunriseService;
    private final SunsetService sunsetService;
    private final MemberService memberService;

    public AdminController(CityService cityService, SunriseService sunriseService, SunsetService sunsetService, MemberService memberService) {
        this.cityService = cityService;
        this.sunriseService = sunriseService;
        this.sunsetService = sunsetService;
        this.memberService = memberService;
    }

    @PostMapping("/edit/add/city")
    public CityDto addCity(@RequestBody CreateCityRequest createCityRequest) {
        return cityService.addNewCity(createCityRequest);
    }

    @PatchMapping("edit/update/city/{cityName}+{cityCountry}+{cityState}")
    public CityDto updateCity(@PathVariable String cityName, @PathVariable String cityCountry, @PathVariable String cityState, @RequestBody CityDto requestDto) {
        return cityService.updateCity(cityName, cityCountry, cityState, requestDto);
    }

    @DeleteMapping("edit/delete/city/{cityName}+{cityCountry}+{cityState}")
    public ResponseEntity<?> deleteCity(@PathVariable String cityName, @PathVariable String cityCountry, @PathVariable String cityState) {
        cityService.deleteCity(cityName, cityCountry, cityState);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
