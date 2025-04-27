package com.khatib.springai.learning.model.weatherapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
record Location(String name, String region, String country) { }