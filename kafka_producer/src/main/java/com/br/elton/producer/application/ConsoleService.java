package com.br.elton.producer.application;

import com.br.elton.producer.domain.model.ConsoleResponse;

import java.util.concurrent.ExecutionException;

public interface ConsoleService {

    public abstract void saveConsole(ConsoleResponse consoleResponse) throws ExecutionException, InterruptedException;

    public abstract void deleteConsole(ConsoleResponse consoleResponse) throws ExecutionException, InterruptedException;

    public abstract void updateConsole(ConsoleResponse consoleResponse) throws ExecutionException, InterruptedException;

}
