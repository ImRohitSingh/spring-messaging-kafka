package com.test.messaging.kafka.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.test.messaging.kafka.models.AirQualityIndex;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Consumer {

	@KafkaListener(topics = "${spring.kafka.template.default-topic}", groupId = "${spring.kafka.consumer.group-id}", concurrency = "3")
	public void consumer(AirQualityIndex data) {
		log.info("Consumed {}", data);
	}

}
