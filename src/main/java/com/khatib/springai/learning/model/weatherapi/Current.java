package com.khatib.springai.learning.model.weatherapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Current(
        @JsonProperty("temp_c") double tempCelsius
) { }