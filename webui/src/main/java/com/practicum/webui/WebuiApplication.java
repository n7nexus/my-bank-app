package com.practicum.webui;

import com.practicum.webui.configuration.GatewayClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GatewayClientProperties.class)
public class WebuiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebuiApplication.class, args);
	}

}
