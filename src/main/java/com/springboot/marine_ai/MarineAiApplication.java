package com.springboot.marine_ai;

import com.springboot.marine_ai.util.ConsoleEncodingUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarineAiApplication {

	public static void main(String[] args) {
		ConsoleEncodingUtil.applyUtf8();
		SpringApplication.run(MarineAiApplication.class, args);
	}

}
