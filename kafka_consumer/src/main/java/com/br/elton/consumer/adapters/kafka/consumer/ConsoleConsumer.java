package com.br.elton.consumer.adapters.kafka.consumer;

import com.br.elton.consumer.domain.model.ConsoleResponse;
import com.br.elton.consumer.adapters.entity.ConsoleEntity;
import com.br.elton.consumer.adapters.kafka.modal.GsonDeserializer;
import com.br.elton.consumer.adapters.kafka.producer.ConsoleProducerError;
import com.br.elton.consumer.application.ConsoleService;
import com.br.elton.consumer.domain.model.Message;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Component
public class ConsoleConsumer<T> {

    private static final Logger log = LoggerFactory.getLogger(ConsoleConsumer.class);

    @Autowired
    private ConsoleService consoleService;

    @Autowired
    private ConsoleProducerError consoleProducerError;

    @Async
    public Properties consumerRegisterConsole() {
        var consumer = new KafkaConsumer<String, Message<T>>(getProperties(ConsoleConsumer.class.getSimpleName()));
        consumer.subscribe(Collections.singletonList("CONSOLE_REGISTER"));
        ConsoleResponse console = null;
        while(true) {
            try {
                var records = consumer.poll(Duration.ofMillis(100));
                if (!records.isEmpty()) {
                    for (var record : records) {
                        var message = record.value();
                        console = (ConsoleResponse) message.getPayload();;
                        try {
                            Boolean existConsoleWithThisName = consoleService.validIfExistNameConsole(console.getName());
                            if (!existConsoleWithThisName) {
                                consoleService.saveConsole(new ConsoleEntity(console.getId(), console.getName(), console.getReleaseYear()));
                            } else {
                                log.info("*** Console record already exists! " + console.getName());
                            }
                        } catch (Exception e) {
                            log.error(" Error to insert for line to Console " + console.toString() + " , Error: " + e.getMessage());
                            consoleProducerError.producerConsoleError(console, ConsoleConsumer.class.getSimpleName(), e.getMessage(), "CONSOLE_ERROR_REGISTER");
                        }

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            log.error("Error: " + e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                log.error(" Error to consume Message, Error: " + e.getMessage());
            }
        }
    }

    @Async
    public void consumerUpdateConsole() {
        var consumer = new KafkaConsumer<String, Message<T>>(getProperties(ConsoleConsumer.class.getSimpleName()));
        consumer.subscribe(Collections.singletonList("CONSOLE_UPDATE"));
        ConsoleResponse console = null;
        while(true) {
            try {
                var records = consumer.poll(Duration.ofMillis(100));
                if (!records.isEmpty()) {
                    for (var record : records) {
                        var message = record.value();
                        console = (ConsoleResponse) message.getPayload();
                        try {
                            consoleService.update(new ConsoleEntity(console.getId(), console.getName(), console.getReleaseYear()));
                        } catch (Exception e) {
                            log.error(" Error to insert for line to Console " + console.toString() + " , Error: " + e.getMessage());
                            consoleProducerError.producerConsoleError(console, ConsoleConsumer.class.getSimpleName(), e.getMessage(), "CONSOLE_ERROR_UPDATE");
                        }

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            log.error("Error: " + e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                log.error(" Error to consume Message, Error: " + e.getMessage());
            }
        }
    }

    @Async
    public void consumerDeleteConsole() {
        var consumer = new KafkaConsumer<String, Message<T>>(getProperties(ConsoleConsumer.class.getSimpleName()));
        consumer.subscribe(Collections.singletonList("CONSOLE_DELETE"));
        ConsoleResponse console = null;
        while(true) {
            try {
                var records = consumer.poll(Duration.ofMillis(100));
                if (!records.isEmpty()) {
                    for (var record : records) {
                        var message = record.value();
                        console = (ConsoleResponse) message.getPayload();
                        try {
                            consoleService.delete(new ConsoleEntity(console.getId(),
                                    console.getName(), console.getReleaseYear()));
                        } catch (Exception e) {
                            log.error(" Error to insert for line to Console " + console.toString() + " , Error: " + e.getMessage());
                            consoleProducerError.producerConsoleError(console, ConsoleConsumer.class.getSimpleName(), e.getMessage(), "CONSOLE_ERROR_DELETE");
                        }

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            log.error("Error: " + e.getMessage());
                        }
                    }
                }
            } catch (Exception e) {
                log.error(" Error to consume Message, Error: " + e.getMessage());
            }
        }
    }

    private Properties getProperties(String groupId) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9091");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return properties;
    }

}
