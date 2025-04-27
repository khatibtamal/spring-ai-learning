package com.khatib.springai.learning.service;

import com.khatib.springai.learning.model.weatherapi.WeatherApiResponseModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class WeatherApiService {

    private static final String BASE_URL = "https://api.weatherapi.com/v1/current.json";
    private final String weatherApiKey;
    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherApiService(@Value("${weather.api.key}") String weatherApiKey) {
        this.weatherApiKey = weatherApiKey;
    }

    public WeatherApiResponseModel getWeather(String location) throws URISyntaxException {
        URI uri = new URI(String.format("%s%s", BASE_URL, String.format("?key=%s&q=%s&aqi=no", weatherApiKey, location)));
        return restTemplate.getForEntity(uri, WeatherApiResponseModel.class).getBody();
    }
}