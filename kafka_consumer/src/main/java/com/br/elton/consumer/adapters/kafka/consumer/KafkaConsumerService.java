package com.br.elton.consumer.adapters.kafka.consumer;

import com.br.elton.consumer.adapters.kafka.modal.GsonDeserializer;
import com.br.elton.consumer.adapters.kafka.producer.KafkaDispatcher;
import com.br.elton.consumer.domain.model.Message;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaConsumerService<T> implements Closeable {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    private final KafkaConsumer<String, Message<T>> consumer;
    private ConsumerFunction parse;

    public KafkaConsumerService() {
        this.consumer = new KafkaConsumer<String, Message<T>>(getProperties());
        this.parse = parse;
    }

    public KafkaConsumerService(String groupId, String topic, ConsumerFunction<T> parse, Map<String, String> properties) {
        this(parse, groupId, properties);
        this.consumer.subscribe(Collections.singletonList(topic));
    }

    private KafkaConsumerService(ConsumerFunction<T> parse, String groupId, Map<String, String> properties) {
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(getProperties(groupId, properties));
    }

    public void run() throws ExecutionException, InterruptedException {
        try (var deadLetter = new KafkaDispatcher<>()) {
            while (true) {
                var records = consumer.poll(Duration.ofMillis(100));
                if (!records.isEmpty()) {
                    for (var record : records) {
                        try {
                            parse.consume(record);
                        } catch (Exception e) {
                            var message = record.value();
                            log.error(" Error : " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        consumer.close();
    }

    private Properties getProperties(String groupId, Map<String, String> overrideProperties) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9091");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        properties.putAll(overrideProperties);
        return properties;
    }

    private Properties getProperties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9091");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        return properties;
    }

}