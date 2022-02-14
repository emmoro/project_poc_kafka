## Project Spring boot with Kafka using Producer and Consumer

* Two project in Spring Boot with Kafka, project kafka_producer(producer) and project kafka_consumer(consumer)
* The project kafka_producer will create the Topic that will be consumed by  kafka_consumer

## Tools used in project
* Java 11
* Gradle
* IDE was used Intellij.
* Kafka(2.3.1)
* kafdrop
* Rest
* PostgreSql

## Getting Started
Necessary install Kafka or using a Docker, if you using a Docker you can use this command to start a Kafka with Docker(docke-compose up).

## How to use the system

* After start Kafka and Spring boot, you can do the call to create a Topic with Kafka in project 'kafka_producer'
* The Topics are creating after execute a call in Post, Update and Delete.
* After start 'kafka_consumer' Kafka and Spring boot, automatically an execute a consumer Topic.

## Note
* You can see a Topic in kafdrop(http://localhost:9000/)
* Port a Kafka(http://localhost:9091/)
* Port zookeeper(http://localhost:2181/)