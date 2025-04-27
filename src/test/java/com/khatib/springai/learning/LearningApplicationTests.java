package com.khatib.springai.learning;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest()
@TestPropertySource(properties = {"spring.ai.openai.api-key=blah1", "weather.api.key=blah2"})
class LearningApplicationTests {

	@Test
	void contextLoads() {
	}
}