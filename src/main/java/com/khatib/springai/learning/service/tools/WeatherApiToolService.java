package com.khatib.springai.learning.service.tools;

import com.khatib.springai.learning.model.weatherapi.WeatherApiResponseModel;
import com.khatib.springai.learning.service.WeatherApiService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class WeatherApiToolService {

    private static final String CURRENT_WEATHER_TOOL_DESCRIPTION = "This method can be used to fetch the current weather information for a given location.";
    private static final String CURRENT_WEATHER_TOOL_NAME = "WeatherApiTool";
    private static final String CURRENT_WEATHER_TOOL_CITY_PARAM_DESCRIPTION = "This is the city for which we want to determine the current weather";

    private final WeatherApiService weatherApiService;

    public WeatherApiToolService(final WeatherApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }

    @Tool(description = CURRENT_WEATHER_TOOL_DESCRIPTION, name = CURRENT_WEATHER_TOOL_NAME)
    public WeatherApiResponseModel getWeather(
            @ToolParam(description = CURRENT_WEATHER_TOOL_CITY_PARAM_DESCRIPTION) String city) throws Exception {
        return weatherApiService.getWeather(city);
    }
}