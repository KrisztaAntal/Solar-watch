package org.codecool.backend.repository;

import org.codecool.backend.model.entity.City;
import org.codecool.backend.model.entity.Sunset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SunsetRepository extends JpaRepository<Sunset, Long> {
    Optional<Sunset> findByDateAndCity(LocalDate localDate, City city);
}
