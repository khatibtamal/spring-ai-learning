package com.khatib.springai.learning.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    @Tool(description = "This method can be used to fetch the current weather information for a given location.", name = "getWeather")
    public String getWeather(@ToolParam(description = "This is the location for which we want to determine the current weather") String location) {
        return String.format("{ \"location\": \"%s\", \"temperature\": \"21°C\", \"condition\": \"Clear skies\" }", location);
    }
}