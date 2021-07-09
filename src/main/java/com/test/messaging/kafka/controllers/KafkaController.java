package com.test.messaging.kafka.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.test.messaging.kafka.models.AirQualityIndex;
import com.test.messaging.kafka.services.Producer;

@RestController
public class KafkaController {

	private Producer producer;

	@Autowired
	public KafkaController(Producer producer) {
		this.producer = producer;
	}

	@PostMapping(path = "/produce/{aqi}")
	@ResponseStatus(code = HttpStatus.OK, reason = "Message successfully sent")
	public void produceToQueue(@PathVariable(name = "aqi", required = true) Double aqi) {
		producer.produce(new AirQualityIndex(aqi, LocalDateTime.now()));
	}

}
