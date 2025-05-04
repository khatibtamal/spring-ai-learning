package com.khatib.springai.learning.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FileUtils {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(FileUtils.class);

    public String loadStringFromFile(String filePath) {
        String content = null;
        try {
            Path path = new ClassPathResource(filePath).getFile().toPath();
            content = Files.readString(path);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return content;
    }
}
