package com.br.elton.producer.adapters.kafka.producer;

import com.br.elton.producer.adapters.kafka.modal.GsonSerializer;
import com.br.elton.producer.domain.model.CorrelationId;
import com.br.elton.producer.domain.model.Message;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaDispatcher<T> implements Closeable {

    private final KafkaProducer<String, Message<T>> producer;
    private String name;

    public KafkaDispatcher() {
        this.producer = new KafkaProducer<>(properties());
    }

    public KafkaDispatcher(String name) {
        this.producer = new KafkaProducer<>(properties());
        this.name = name;
    }


    public void send(String topic, String key, T payload) throws ExecutionException, InterruptedException {
        var value = new Message<>(new CorrelationId(name), payload);
        var record = new ProducerRecord<>(topic, key, value);
        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
        };
        producer.send(record, callback).get();
    }

    @Override
    public void close() {
        producer.close();
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9091");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        return properties;
    }

}
