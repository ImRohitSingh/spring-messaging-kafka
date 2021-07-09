package com.test.messaging.kafka.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.test.messaging.kafka.models.AirQualityIndex;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Producer {

	@Value("${spring.kafka.template.default-topic}")
	private String topic;

	@Autowired
	private KafkaTemplate<String, AirQualityIndex> kafkaTemplate;

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
