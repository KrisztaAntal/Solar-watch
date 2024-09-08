package org.codecool.backend.utils;

import org.codecool.backend.model.dto.CityDto;
import org.codecool.backend.model.entity.City;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class Patcher {

    public void cityPatcher(City existingCity, CityDto incompleteCity) throws IllegalAccessException {
        Class<?> cityClass = City.class;
        Field[] cityFields = cityClass.getDeclaredFields();
        for (Field cityField : cityFields) {
            cityField.setAccessible(true);
            Object value = cityField.get(incompleteCity);
            if (value != null) {
                cityField.set(existingCity, value);
            }
            cityField.setAccessible(false);
        }
    }
}
