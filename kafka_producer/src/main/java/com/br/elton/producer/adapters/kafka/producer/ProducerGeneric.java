package com.br.elton.producer.adapters.kafka.producer;

import com.br.elton.producer.domain.model.ConsoleResponse;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class ProducerGeneric {

    public void producerConsole(ConsoleResponse consoleResponse, String topic) throws ExecutionException, InterruptedException {
        try {
            var consoleDispatcher = new KafkaDispatcher<ConsoleResponse>(ProducerGeneric.class.getSimpleName());
            consoleDispatcher.send(topic, consoleResponse.getName(), consoleResponse);
        } catch (Exception e) {
            throw new ExecutionException(e);
        }
    }

}
