package com.khatib.springai.learning.controller.chat;

import com.khatib.springai.learning.model.tools.WeatherToolModel;
import com.khatib.springai.learning.service.tools.WeatherApiToolService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RestController
@RequestMapping("/chat/response-format")
public class ResponseFormatController {

    private final ChatClient chatClient;
    private final WeatherApiToolService weatherApiToolService;

    public ResponseFormatController(ChatModel chatModel, WeatherApiToolService weatherApiToolService) {
        this.chatClient = ChatClient.builder(chatModel).build();
        this.weatherApiToolService = weatherApiToolService;
    }

    @RequestMapping("/weather-tool/{content}")
    public WeatherToolModel weatherQueryWithResponseModel(@PathVariable String content) {
        Message systemMessage = new SystemMessage("You are a helpful assistant that uses tools.");

        return chatClient
                .prompt(content)
                .messages(systemMessage)
                .tools(weatherApiToolService)
                .call()
                .entity(WeatherToolModel.class);
    }

    @RequestMapping("/weather-tool/parameterized-type-reference/{content}")
    public List<WeatherToolModel> weatherQueryWithParameterizedTypeReference(@PathVariable String content) {
        Message systemMessage = new SystemMessage("You are a helpful assistant that uses tools.");

        return chatClient
                .prompt(content)
                .messages(systemMessage)
                .tools(weatherApiToolService)
                .call()
                .entity(new ParameterizedTypeReference<List<WeatherToolModel>>() {});
    }

    // Todo: Based on spring documentation, the streaming part will be improved
    @RequestMapping("/weather-tool/streaming-response/{content}")
    public Flux<String> weatherQueryWithStreamingResponse(@PathVariable String content) {
        Message systemMessage = new SystemMessage("You are a helpful assistant that uses tools.");
        var converter = new BeanOutputConverter<>(new ParameterizedTypeReference<List<WeatherToolModel>>() {});

        Consumer<ChatClient.PromptUserSpec> promptUserSpecConsumer = promptUserSpec -> {
            promptUserSpec.text("""
                    {content}
                    {format}
                    """);
            promptUserSpec.params(Map.of("content", content, "format",  converter.getFormat()));
        };

        return chatClient
                .prompt()
                .user(promptUserSpecConsumer)
                .messages(systemMessage)
                .tools(weatherApiToolService)
                .stream()
                .content();
    }
}