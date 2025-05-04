package com.khatib.springai.learning.config;

import com.khatib.springai.learning.utils.DocumentUtils;
import com.khatib.springai.learning.utils.FileUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class VectorDBDataInitializerConfig {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VectorDBDataInitializerConfig.class);
    private final PgVectorStore pgVectorStore;
    private final FileUtils fileUtils;
    private final DocumentUtils documentUtils;

    public VectorDBDataInitializerConfig(PgVectorStore pgVectorStore, FileUtils fileUtils, DocumentUtils documentUtils) {
        this.pgVectorStore = pgVectorStore;
        this.fileUtils = fileUtils;
        this.documentUtils = documentUtils;
    }

    @PostConstruct
    public void initHtml() {
        // Need to store the html contect for qwen in resources/static/qwen.html
//        initQwen();
    }

    private void initQwen() {
        String htmlContent = fileUtils.loadStringFromFile("static/qwen.html");
        Map<String, Object> metadata = Map.of(
                "source", "qwen",
                "title", "qwen",
                "company", "qwen"
        );

        pgVectorStore.add(documentUtils.createDocuments(htmlContent, metadata));
    }
}