package com.singhdevhub.autosuggestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.singhdevhub.autosuggestion/service/", "com.singhdevhub.autosuggestion/config/",
"com.singhdevhub.autosuggestion/handler/"})
public class AutosuggestionApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutosuggestionApplication.class, args);
	}

}
