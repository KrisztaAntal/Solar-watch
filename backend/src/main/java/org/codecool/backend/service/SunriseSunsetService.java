package org.codecool.backend.service;

import org.codecool.backend.model.entity.City;
import org.codecool.backend.model.entity.Sunrise;
import org.codecool.backend.model.entity.Sunset;
import org.codecool.backend.model.report.sunrisesunset.ResultsReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;

@Service
public class SunriseSunsetService {
    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(SunriseSunsetService.class);

    public SunriseSunsetService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Sunset getSunsetFromApi(LocalDate localDate, City city) {
        ResultsReport response = getResultsReportFromAPI(localDate, city);
        return new Sunset(localDate, response.results().sunset(), city);
    }

    public Sunrise getSunriseFromApi(LocalDate localDate, City city) {
        ResultsReport response = getResultsReportFromAPI(localDate, city);
        return new Sunrise(localDate, response.results().sunrise(), city);
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
