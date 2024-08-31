package org.codecool.backend.service;

import org.codecool.backend.controller.exception.EntityAlreadyInDBException;
import org.codecool.backend.model.entity.City;
import org.codecool.backend.model.entity.Sunrise;
import org.codecool.backend.model.entity.Sunset;
import org.codecool.backend.model.report.sunrisesunset.ResultsReport;
import org.codecool.backend.repository.SunriseRepository;
import org.codecool.backend.repository.SunsetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
public class SunriseSunsetService {
    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(SunriseSunsetService.class);
    private final SunsetRepository sunsetRepository;
    private final SunriseRepository sunriseRepository;

    public SunriseSunsetService(WebClient webClient, SunsetRepository sunsetRepository, SunriseRepository sunriseRepository) {
        this.webClient = webClient;
        this.sunsetRepository = sunsetRepository;
        this.sunriseRepository = sunriseRepository;
    }

    public void saveSunsetSunriseFromAPI(LocalDate localDate, City city) {
        ResultsReport response = getResultsReportFromAPI(localDate, city);
        if (response != null) {
            sunsetRepository.save(new Sunset(localDate, response.results().sunset(), city));
            sunriseRepository.save(new Sunrise(localDate, response.results().sunrise(), city));
        } else throw new NoSuchElementException("No response from API, could not save Sunrise and Sunset");
    }

    private ResultsReport getResultsReportFromAPI(LocalDate localDate, City city) {
        String url = String.format("https://api.sunrise-sunset.org/json?lat=%s&lng=%s&date=%s",
                city.getLongitude(),
                city.getLatitude(),
                localDate);

        ResultsReport response = webClient.get().uri(url).retrieve().bodyToMono(ResultsReport.class).log().block();
        logger.info("Response from sunset and sunrise times API: {}", response);
        return response;
    }

}
