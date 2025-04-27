package com.khatib.springai.learning.model.tools;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherToolModel(String city, String country, int temperatureCelsius) { }