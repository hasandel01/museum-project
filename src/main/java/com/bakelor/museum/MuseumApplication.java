package com.bakelor.museum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.bakelor.museum.controller.MqttController;
import com.bakelor.museum.controller.MqttSubscriber;

@SpringBootApplication
public class MuseumApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuseumApplication.class, args);
	}

	@Bean
	public MqttSubscriber mqttSubscriber(MqttController mqttController) {
		return new MqttSubscriber(mqttController);
	}
}
