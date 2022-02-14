package com.br.elton.consumer.adapters.controller;

import com.br.elton.consumer.domain.model.ConsoleResponse;
import com.br.elton.consumer.adapters.entity.ConsoleEntity;
import com.br.elton.consumer.application.ConsoleService;
import com.br.elton.consumer.config.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Path.API)
public class ConsoleController {

    private static final Logger log = LoggerFactory.getLogger(ConsoleController.class);

    @Autowired
    private ConsoleService consoleService;

    @RequestMapping(value = Path.CONSOLE_SINGLE, method = RequestMethod.GET)
    public ResponseEntity<ConsoleResponse> getConsoleById(@PathVariable("id") Long id) {
        log.info("Get console By Id: " + id);
        Optional<ConsoleEntity> consoleEntity = consoleService.getConsoleById(id);
        if (consoleEntity.isPresent()) {
            return ResponseEntity.ok(new ConsoleResponse(consoleEntity.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = Path.CONSOLES, method = RequestMethod.GET)
    public ResponseEntity<List<ConsoleResponse>> getAllConsoles() {
        List<ConsoleEntity> list = consoleService.getAllConsoles();
        List<ConsoleResponse> consoleResponses = new ArrayList<ConsoleResponse>();
        list.stream().forEach(console -> {
            consoleResponses.add(new ConsoleResponse(console));
        });

        return ResponseEntity.ok(consoleResponses);
    }

}
