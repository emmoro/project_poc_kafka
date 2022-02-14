package com.br.elton.consumer.adapters.kafka.producer;

import com.br.elton.consumer.domain.model.ConsoleResponse;
import com.br.elton.consumer.domain.model.MessageError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class ConsoleProducerError {

    private static final Logger log = LoggerFactory.getLogger(ConsoleProducerError.class);

    public void producerConsoleError(ConsoleResponse consoleResponse, String name, String msgError, String topic) throws ExecutionException, InterruptedException {
        try {
            var consoleDispatcher = new KafkaDispatcher<MessageError>(name);
            var messageError = new MessageError<ConsoleResponse>(msgError, consoleResponse);
            consoleDispatcher.send(topic, consoleResponse.getName(), messageError);
        } catch (Exception e) {
            log.error("Error to put message in the line : " + e.getMessage());
        }
    }

}
