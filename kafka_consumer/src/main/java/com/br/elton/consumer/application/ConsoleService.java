package com.br.elton.consumer.application;

import com.br.elton.consumer.adapters.entity.ConsoleEntity;

import java.util.List;
import java.util.Optional;

public interface ConsoleService {

    public abstract ConsoleEntity saveConsole(ConsoleEntity consoleRe);

    public abstract Optional<ConsoleEntity> getConsoleById(Long consoleId);

    public abstract List<ConsoleEntity> getAllConsoles();

    public abstract void delete(ConsoleEntity console);

    public abstract ConsoleEntity update(ConsoleEntity console);

    public abstract Boolean validIfExistNameConsole(String name);

}
