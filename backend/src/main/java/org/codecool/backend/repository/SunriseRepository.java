package org.codecool.backend.repository;

import org.codecool.backend.model.entity.City;
import org.codecool.backend.model.entity.Sunrise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SunriseRepository extends JpaRepository<Sunrise, Long> {
    Optional<Sunrise> findByDateAndCity(LocalDate localDate, City city);
}
