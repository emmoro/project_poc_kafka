package com.br.elton.consumer.application.impl;

import com.br.elton.consumer.adapters.entity.ConsoleEntity;
import com.br.elton.consumer.adapters.repository.ConsoleRepository;
import com.br.elton.consumer.application.ConsoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsoleServiceImpl implements ConsoleService {

    private static final Logger log = LoggerFactory.getLogger(ConsoleServiceImpl.class);

    @Autowired
    private ConsoleRepository consoleRepository;

    @Override
    public ConsoleEntity saveConsole(ConsoleEntity console) {
        return consoleRepository.saveAndFlush(console);
    }

    @Override
    public Optional<ConsoleEntity> getConsoleById(Long consoleId) {
        return consoleRepository.findById(consoleId);
    }

    @Override
    public List<ConsoleEntity> getAllConsoles() {
        return consoleRepository.findAllByOrderById();
    }

    @Override
    public void delete(ConsoleEntity console) {
        consoleRepository.delete(console);
    }

    @Override
    public ConsoleEntity update(ConsoleEntity console) {
        return consoleRepository.saveAndFlush(console);
    }

    @Override
    public Boolean validIfExistNameConsole(String name) {
        ConsoleEntity console = consoleRepository.findByName(name);
        if (console == null) {
            return false;
        } else {
            return true;
        }
    }

}
