package com.khatib.springai.learning.service.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class BasicWeatherToolService {

    private static final String CURRENT_WEATHER_TOOL_DESCRIPTION = "This method can be used to fetch the current weather information for a given location.";
    private static final String CURRENT_WEATHER_TOOL_NAME = "BasicWeatherTool";
    private static final String CURRENT_WEATHER_TOOL_LOCATION_PARAM_DESCRIPTION = "This is the location for which we want to determine the current weather";

    @Tool(description = CURRENT_WEATHER_TOOL_DESCRIPTION, name = CURRENT_WEATHER_TOOL_NAME)
    public String getWeather(@ToolParam(description = CURRENT_WEATHER_TOOL_LOCATION_PARAM_DESCRIPTION) String location) {
        return String.format("{ \"location\": \"%s\", \"temperature\": \"21°C\", \"condition\": \"Clear skies\" }", location);
    }
}