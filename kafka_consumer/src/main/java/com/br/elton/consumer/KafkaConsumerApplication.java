package com.br.elton.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KafkaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerApplication.class, args);
	}

}
