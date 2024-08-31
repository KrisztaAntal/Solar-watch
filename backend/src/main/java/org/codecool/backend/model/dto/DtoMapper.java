package org.codecool.backend.model.dto;

import org.codecool.backend.model.entity.City;
import org.codecool.backend.model.entity.Sunrise;
import org.codecool.backend.model.entity.Sunset;

public class DtoMapper {
    public static SunriseDto toSunriseDto(Sunrise sunrise) {
        return new SunriseDto(sunrise.getDate(), sunrise.getTimeOfSunrise(), toCityDto(sunrise.getCity()));
    }

    public static SunsetDto toSunsetDto(Sunset sunset) {
        return new SunsetDto(sunset.getDate(), sunset.getTimeOfSunset(), toCityDto(sunset.getCity()));
    }

    public static CityDto toCityDto(City city) {
        return new CityDto(city.getName(), city.getLatitude(), city.getLongitude(), city.getCountry(), city.getState());
    }
}
