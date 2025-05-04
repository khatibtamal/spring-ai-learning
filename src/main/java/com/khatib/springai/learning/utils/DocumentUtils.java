package com.khatib.springai.learning.utils;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DocumentUtils {

    public List<Document> createDocuments(String content, Map<String, Object> metadata) {
        Document document = new Document(content, metadata);
        TextSplitter textSplitter = new TokenTextSplitter();

        return textSplitter.split(document);
    }
}
