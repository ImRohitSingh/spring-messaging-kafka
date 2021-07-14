# spring-messaging-kafka

A spring boot application that:
1. produces ```100 messages```, which are nothing but AQI at a given time, to a Kafka Topic, at a regular interval of ```10 seconds```;
2. consumes** them in ```3``` concurrent threads based on the need;
3. has an endpoint exposed, using which a single message can be posted at a time.

** In an ideal scenario as seen in micro-service based applications that uses messaging, ```producers``` and ```consumers``` are part of separate applications.


## Pre-requisite

* Make sure you have [Kafka](https://kafka.apache.org/) installed.

## Kafka Installation

1. Download the binary from the [link](https://www.apache.org/dyn/closer.cgi?path=/kafka/2.8.0/kafka_2.13-2.8.0.tgz) (you can download a latest version as well, when available) and extract it to a local folder.
2. Go to ```kafka_2.12-2.8.0\config``` and open ```server.properties```.
3. Modify ```log.dirs``` property to a valid location where logs will be generated.
4. Go to ```kafka_2.12-2.8.0\config``` and open ```zookeeper.properties```.
5. Modify ```dataDir``` property to a valid location where zookeeper data will be generated.
6. From the root folder, i.e., from ```kafka_2.12-2.8.0``` open two ```bash```** terminals and run the following commands:
```sh
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
```
The commands will start the zookeeper instance and the Kafka server respectively.

** ```cmd``` would not be able to run the commands. You can use ```Git Bash``` instead.

> Note: The instructions are for Windows machine. You may follow this [link](https://kafka.apache.org/quickstart) for setup in different environments.

## Properties

### Producer:
```sh
spring.kafka.producer.bootstrap-servers=localhost:9092
```
Using the default value. This can be initialized with comma separated values of hostnames of Kafka server/s.

```sh
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
```
Using the default value. Kept for reference.

```sh
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
```
Default is ```StringSerializer```. Since we will be posting a simple DTO, ```JsonSerializer``` is used instead.

```sh
spring.kafka.producer.properties.spring.json.add.type.headers=false
```
Default is ```true```.  Used to disable the ```JsonSerializer```'s default behavior of sending type information in headers.

### Consumer:
```sh
spring.kafka.consumer.bootstrap-servers=localhost:9092
```
Using the default value. This can be initialized with comma separated values of hostnames of Kafka server/s.

```sh
spring.kafka.consumer.group-id=aqi-group
```
A recommended configuration. Go through this [link](https://www.tutorialspoint.com/apache_kafka/apache_kafka_fundamentals.htm) to learn fundamental concepts of Kafka.

```sh
spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer
```
Using the default value. Kept for reference.

```sh
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
```
Default is ```StringDeserializer```. Since we will be consuming a simple DTO, ```JsonDeserializer``` is used instead.

```sh
spring.kafka.consumer.properties.spring.json.use.type.headers=false
```
Default is ```true```.  Used to disable the ```JsonSerializer```'s default behavior of sending type information in headers.

```sh
spring.kafka.consumer.properties.spring.json.trusted.packages=com.test.messaging.kafka.models
```
The instance of the DTO class will be serialized by ```JsonSerializer``` to ```byte array```. Kafka finally stores this byte array into the given partition.

During deserialization, ```JsonDeserializer``` is used to for receiving JSON from Kafka as ```byte array``` and return the DTO to application.

In order to do so, Kafka needs to know if its dealing with the correct object. Else a different application can post a similar object to the same partition.

'*' means all packages can be deserialized. Also, this property may be initialed with comma-separated values.

```sh
spring.kafka.consumer.properties.spring.json.value.default.type=com.test.messaging.kafka.models.AirQualityIndex
```
See the [link](https://docs.spring.io/spring-kafka/api/org/springframework/kafka/support/serializer/JsonDeserializer.html#VALUE_DEFAULT_TYPE).



