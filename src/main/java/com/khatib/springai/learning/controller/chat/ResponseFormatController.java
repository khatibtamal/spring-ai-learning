package com.khatib.springai.learning.controller.chat;

import com.khatib.springai.learning.model.tools.WeatherToolModel;
import com.khatib.springai.learning.service.StockService;
import com.khatib.springai.learning.service.tools.WeatherApiToolService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat/response-format")
public class ResponseFormatController {

    private final ChatClient chatClient;
    private final WeatherApiToolService weatherApiToolService;
    private final StockService stockService;

    public ResponseFormatController(ChatModel chatModel, WeatherApiToolService weatherApiToolService, StockService stockService) {
        this.chatClient = ChatClient.builder(chatModel).build();
        this.weatherApiToolService = weatherApiToolService;
        this.stockService = stockService;
    }

    @RequestMapping("/weather-tool/{content}")
    public WeatherToolModel weatherQueryWithResponseModel(@PathVariable String content) {
        Message systemMessage = new SystemMessage("You are a helpful assistant that uses tools.");

        return chatClient
                .prompt(content)
                .messages(systemMessage)
                .tools(weatherApiToolService, stockService)
                .call()
                .entity(WeatherToolModel.class);
    }
}