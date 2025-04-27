package com.khatib.springai.learning.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherModel (String city, String country, int temperatureCelsius) { }
