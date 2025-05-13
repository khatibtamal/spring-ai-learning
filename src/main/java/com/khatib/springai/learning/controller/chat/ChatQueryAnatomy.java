package com.khatib.springai.learning.controller.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.DefaultChatClient;
import org.springframework.ai.chat.client.advisor.DefaultAroundAdvisorChain;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAroundAdvisorChain;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat/anatomy")
public class ChatQueryAnatomy {

    private final ChatClient chatClient;

    public ChatQueryAnatomy(ChatModel chatModel) {
        this.chatClient = ChatClient.builder(chatModel).build();
    }

    @GetMapping(value = "/simple/{content}")
    public String regularQuery(@PathVariable String content) {

        ChatClient.ChatClientRequestSpec afterPrompt = chatClient.prompt(content);
        ChatClient.CallResponseSpec afterCall = afterPrompt.call();
        String afterCallingContent = afterCall.content();
//        CallAroundAdvisorChain
//        CallAroundAdvisor
//        BaseAdvisor
//        DefaultAroundAdvisorChain
//        DefaultAroundAdvisorChain.builder()
//        DefaultChatClient.DefaultChatClientRequestSpec

        return afterCallingContent;
    }
}
