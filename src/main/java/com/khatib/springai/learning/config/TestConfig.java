package com.khatib.springai.learning.config;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Configuration
public class TestConfig {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(TestConfig.class);
    private final PgVectorStore pgVectorStore;

    public TestConfig(PgVectorStore pgVectorStore) {
        this.pgVectorStore = pgVectorStore;
    }

    @PostConstruct
    public void initHtml() {
        // Need to store the html contect for qwen in resources/static/qwen.html
//        initQwen();
    }

    private void initQwen() {
        String htmlContent = loadStringFromFile("static/qwen.html");
        Map<String, Object> metadata = Map.of(
                "source", "qwen",
                "title", "qwen",
                "company", "qwen"
        );

        loadContentIntoVectorDB(htmlContent, metadata);
    }

    private String loadStringFromFile(String filePath) {
        String content = null;
        try {
            Path path = new ClassPathResource(filePath).getFile().toPath();
            content = Files.readString(path);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return content;
    }

    private void loadContentIntoVectorDB(String content, Map<String, Object> metadata) {
        Document document = new Document(content, metadata);
        TextSplitter textSplitter = new TokenTextSplitter();

        List<Document> documents = textSplitter.split(document);
        pgVectorStore.add(documents);
    }
}