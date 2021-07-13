package com.test.messaging.kafka.models;

import java.io.Serializable;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AirQualityIndex implements Serializable {

	private static final long serialVersionUID = 6421821321035187239L;

	private Double aqi;

	private ZonedDateTime timestamp;

}
