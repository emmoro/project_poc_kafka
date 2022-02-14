package com.br.elton.producer.application.impl;

import com.br.elton.producer.adapters.kafka.producer.ProducerGeneric;
import com.br.elton.producer.application.ConsoleService;
import com.br.elton.producer.domain.model.ConsoleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class ConsoleServiceImpl implements ConsoleService {

    @Autowired
    private ProducerGeneric producerGeneric;

    @Override
    public void saveConsole(ConsoleResponse consoleResponse) throws ExecutionException, InterruptedException {
        producerGeneric.producerConsole(consoleResponse, "CONSOLE_REGISTER");
    }

    @Override
    public void deleteConsole(ConsoleResponse consoleResponse) throws ExecutionException, InterruptedException {
        producerGeneric.producerConsole(consoleResponse, "CONSOLE_DELETE");
    }

    @Override
    public void updateConsole(ConsoleResponse consoleResponse) throws ExecutionException, InterruptedException {
        producerGeneric.producerConsole(consoleResponse, "CONSOLE_UPDATE");
    }

}
