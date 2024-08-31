package org.codecool.backend.repository;

import org.codecool.backend.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByNameAndCountryAndState(String name, String country, String state);

    List<City> findAllByName(String name);

    Optional<City> findByLongitudeAndLatitude(double longitude, double latitude);
}
