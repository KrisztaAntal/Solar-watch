package org.codecool.backend.model.report.sunrisesunset;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultsReport(SunriseSunsetReport results) {
}
