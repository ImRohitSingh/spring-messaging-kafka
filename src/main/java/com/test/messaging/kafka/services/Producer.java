package com.test.messaging.kafka.services;

import java.time.ZonedDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.test.messaging.kafka.models.AirQualityIndex;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Producer {

	private static final Random RANDOM = new Random();

	@Value("${spring.kafka.template.default-topic}")
	private String topic;

	@Autowired
	private KafkaTemplate<String, AirQualityIndex> kafkaTemplate;

	@Scheduled(fixedRate = 10000)
	public void produce() {
		for (int i = 1; i <= 100; ++i) {
			produce(new AirQualityIndex(10.0 + RANDOM.nextInt(700), ZonedDateTime.now()));
		}
	}

	public void produce(AirQualityIndex aqiEntity) {
		log.info("Attempt producing {}", aqiEntity);
		ListenableFuture<SendResult<String, AirQualityIndex>> future = kafkaTemplate.send(topic, aqiEntity);

		future.addCallback(new ListenableFutureCallback<SendResult<String, AirQualityIndex>>() {

			@Override
			public void onSuccess(SendResult<String, AirQualityIndex> result) {
				log.info("Produced with offset {}", result.getRecordMetadata().offset());

			}

			@Override
			public void onFailure(Throwable ex) {
				log.error("Failed to produce due to {}", ex.getMessage());
			}
		});
	}

}
