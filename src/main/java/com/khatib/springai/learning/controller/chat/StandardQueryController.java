package com.khatib.springai.learning.controller.chat;

import com.khatib.springai.learning.service.StockService;
import com.khatib.springai.learning.service.tools.BasicWeatherToolService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat/standard-query")
public class StandardQueryController {

    private final ChatClient chatClient;
    private final BasicWeatherToolService basicWeatherToolService;
    private final StockService stockService;

    /**
     * The built ChatClient is the DefaultChatClient
     *
     * @param chatModel: this project only has OpenAI configured, therefore
     *                 the ChatModel is OpenAIChatModel with default configs.
     */
    public StandardQueryController(ChatModel chatModel, BasicWeatherToolService basicWeatherToolService, StockService stockService) {
        this.chatClient = ChatClient.builder(chatModel).build();
        this.basicWeatherToolService = basicWeatherToolService;
        this.stockService = stockService;
    }

    /**
     * A regular query to the chat model with no specifications,
     * persona expectations or scope limitations.
     * The prompt(content) by default creates a UserMessage with the content.
     *
     * @param content: The text content to be sent to the chat model.
     * @return the response from the chat model
     */
    @GetMapping(value = "/regular/{content}")
    public String regularQuery(@PathVariable String content) {
        return chatClient
                .prompt(content)
                .call()
                .content();
    }

    /**
     * A query with an angry persona.
     *
     * @param content: The text content to be sent to the chat model.
     * @return the response from the chat model
     */
    @GetMapping(value = "/angry-persona/{content}")
    public String angryPersonaSystemParamQuery(@PathVariable String content) {
        Message systemMessage = new SystemMessage("You are an angry assistant.");
        return chatClient
                .prompt(content)
                .messages(systemMessage)
                .call()
                .content();
    }

    /**
     * A query with an angry persona and scope.
     *
     * @param content: The text content to be sent to the chat model.
     * @return the response from the chat model
     */
    @GetMapping(value = "/angry-persona/scope-limit/{content}")
    public String angryPersonaAndScopeSystemParamQuery(@PathVariable String content) {
        Message systemMessage = new SystemMessage("You are an angry assistant.");
        return chatClient
                .prompt(content)
                .messages(systemMessage)
                .call()
                .content();
    }

    /**
     * A query where we are trying to get an answer to a question using tools
     *
     * @param content: The text content to be sent to the chat model.
     * @return the response from the chat model
     */
    @GetMapping(value = "/tool/assistant-with-tools/{content}")
    public String toolUsageQuery(@PathVariable String content) {
        Message systemMessage = new SystemMessage("You are a helpful assistant that uses tools.");

        return chatClient
                .prompt(content)
                .messages(systemMessage)
                .tools(basicWeatherToolService, stockService)
                .call()
                .content();
    }

    /**
     * A query where we are trying to get an answer to a question using tools
     * also we are attempting extract information of the tools that were used.
     *
     * @param content: The text content to be sent to the chat model.
     * @return the response from the chat model and also the tools that were used
     */
    @GetMapping(value = "/tool/assistant-with-tools/tool-calls/{content}")
    public String assistantWithResultAndToolDetails(@PathVariable String content) {
        Message systemMessage = new SystemMessage("You are a helpful assistant that uses tools.");

        ChatClient.CallResponseSpec callResponseSpec = chatClient
                .prompt(content)
                .messages(systemMessage)
                .tools(basicWeatherToolService, stockService)
                .call();

        ChatResponse chatResponse = callResponseSpec.chatResponse();
        assert chatResponse != null;

        StringBuilder toolCallsBuilder = new StringBuilder();
        toolCallsBuilder.append("\n");
        toolCallsBuilder.append("Following tools were used: ");

        AssistantMessage assistantMessage = chatResponse.getResult().getOutput();
        assistantMessage.getToolCalls().forEach(toolCall -> {
            toolCallsBuilder.append(toolCall.name());
        });

        return toolCallsBuilder.toString();
    }
}