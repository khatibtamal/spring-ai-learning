package com.khatib.springai.learning.controller.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat/conversational")
public class ChatBotController {

    private final ChatClient memoryAdvisorChatClient;
    private final ChatClient promptAdvisorChatClient;

    public ChatBotController(ChatModel chatModel) {
        this.memoryAdvisorChatClient = ChatClient
                .builder(chatModel)
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();

        this.promptAdvisorChatClient = ChatClient
                .builder(chatModel)
                .defaultAdvisors(new PromptChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }

    @GetMapping(value = "/message-memory/{content}")
    public String memoryQuery(@PathVariable String content) {
        return memoryAdvisorChatClient
                .prompt(content)
                .call()
                .content();
    }

    @GetMapping(value = "/prompt-memory/{content}")
    public String promptQuery(@PathVariable String content) {
        return promptAdvisorChatClient
                .prompt(content)
                .call()
                .content();
    }
}
