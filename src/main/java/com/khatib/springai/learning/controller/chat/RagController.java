package com.khatib.springai.learning.controller.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.rag.retrieval.search.DocumentRetriever;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/chat/rag")
public class RagController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public RagController(ChatModel chatModel, VectorStore vectorStore) {
        this.chatClient = ChatClient.builder(chatModel).build();
        this.vectorStore = vectorStore;
    }

    @GetMapping(value = "/prompt-engineer/{msg}")
    public String promptEngineer(@PathVariable String msg) {
        DocumentRetriever documentRetriever = VectorStoreDocumentRetriever.builder().vectorStore(vectorStore).topK(75).build();
        Advisor advisor = RetrievalAugmentationAdvisor.builder().documentRetriever(documentRetriever).build();

        String jsonFormat = """
                
                    "summary": "<Overall summary of your response>",
                    "modelCreator": "<Creator of the model>",
                    "date": "<Date the model was created in format Month Day, Year>",
                    "license": "<License the model is released under>"
                
                """;

        String template = """
                You are a helpful assistant, that responds only in pure JSON format.
                
                Use the following json format to answer the question.
                In the response please give me only the json object and nothing else such as code blocks,
                backticks or any explanations.
                
                JSON Format:
                
                {jsonFormat}
                
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template);

        Message userMessage = new UserMessage(msg);
        return chatClient.prompt(promptTemplate.create(Map.of("jsonFormat", jsonFormat)))
                .advisors(advisor)
                .messages(userMessage)
                .call()
                .content();
    }
}
