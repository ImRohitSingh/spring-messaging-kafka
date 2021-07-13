package com.test.messaging.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringMessagingKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMessagingKafkaApplication.class, args);
	}

}
