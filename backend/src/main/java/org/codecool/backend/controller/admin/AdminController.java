package org.codecool.backend.controller.admin;

import org.codecool.backend.model.dto.CityDto;
import org.codecool.backend.model.dto.SunriseDto;
import org.codecool.backend.model.dto.SunsetDto;
import org.codecool.backend.model.payload.CreateCityRequest;
import org.codecool.backend.model.payload.CreateSunriseRequest;
import org.codecool.backend.model.payload.CreateSunsetRequest;
import org.codecool.backend.service.CityService;
import org.codecool.backend.service.MemberService;
import org.codecool.backend.service.SunriseService;
import org.codecool.backend.service.SunsetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

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

    //City

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

    //Sunrise

    @PostMapping("/edit/add/sunrise")
    public SunriseDto addSunrise(@RequestBody CreateSunriseRequest sunriseRequest) {
        return sunriseService.addNewSunrise(sunriseRequest);
    }

    @PatchMapping("/edit/update/sunrise/{date}+{cityName}+{cityCountry}+{cityState}")
    public SunriseDto updateSunrise(@PathVariable String date, @PathVariable String cityName, @PathVariable String cityCountry, @PathVariable String cityState, @RequestBody SunriseDto requestDto) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate lDate = LocalDate.parse(date, format);
        return sunriseService.updateSunrise(lDate, cityName, cityCountry, cityState, requestDto);
    }

    @DeleteMapping("edit/delete/sunrise/{date}+{cityName}+{cityCountry}+{cityState}")
    public ResponseEntity<?> deleteSunrise(@PathVariable String date, @PathVariable String cityName, @PathVariable String cityCountry, @PathVariable String cityState) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate lDate = LocalDate.parse(date, format);
        sunriseService.deleteSunrise(lDate, cityName, cityCountry, cityState);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //Sunset

    @PostMapping("/edit/add/sunset")
    public SunsetDto addSunset(@RequestBody CreateSunsetRequest sunsetRequest) {
        return sunsetService.addNewSunset(sunsetRequest);
    }

    @PatchMapping("/edit/update/sunset/{date}+{cityName}+{cityCountry}+{cityState}")
    public SunsetDto updateSunset(@PathVariable String date, @PathVariable String cityName, @PathVariable String cityCountry, @PathVariable String cityState, @RequestBody SunsetDto requestDto) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate lDate = LocalDate.parse(date, format);
        return sunsetService.updateSunset(lDate, cityName, cityCountry, cityState, requestDto);
    }

    @DeleteMapping("edit/delete/sunset/{date}+{cityName}+{cityCountry}+{cityState}")
    public ResponseEntity<?> deleteSunset(@PathVariable String date, @PathVariable String cityName, @PathVariable String cityCountry, @PathVariable String cityState) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate lDate = LocalDate.parse(date, format);
        sunsetService.deleteSunset(lDate, cityName, cityCountry, cityState);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //Member

    @PatchMapping("/edit/user/{userid}/add-admin-role")
    public ResponseEntity<?> addAdminRoleToMember(@PathVariable UUID userid) {
        memberService.addAdminRole(userid);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/edit/user/{userid}/delete-admin-role")
    public ResponseEntity<?> removeAdminRoleToMember(@PathVariable UUID userid) {
        memberService.removeAdminRole(userid);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("edit/delete/user/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID userId) {
        memberService.deleteMemberById(userId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
