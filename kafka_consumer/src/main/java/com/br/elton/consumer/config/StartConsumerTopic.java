package com.br.elton.consumer.config;

import com.br.elton.consumer.adapters.kafka.consumer.ConsoleConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class StartConsumerTopic {

    @Autowired
    private ConsoleConsumer consoleConsumer;

    @EventListener(ContextRefreshedEvent.class)
    public void startConsumerConsole() {
        startRegisterConsumer();
        startUpdateConsumer();
        startDeleteConsumer();
    }

    @Async
    private void startRegisterConsumer() {
        consoleConsumer.consumerRegisterConsole();
    }

    @Async
    private void startUpdateConsumer() {
        consoleConsumer.consumerUpdateConsole();
    }

    @Async
    private void startDeleteConsumer() {
        consoleConsumer.consumerDeleteConsole();
    }

}
