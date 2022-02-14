package com.br.elton.producer.adapters.controller;

import com.br.elton.producer.application.ConsoleService;
import com.br.elton.producer.config.Path;
import com.br.elton.producer.domain.model.ConsoleResponse;
import com.br.elton.producer.domain.model.ConsoleResponseSave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(Path.API)
public class ConsoleController {

    private static final Logger log = LoggerFactory.getLogger(ConsoleController.class);

    @Autowired
    private ConsoleService consoleService;

    @RequestMapping(value = Path.CONSOLE_SINGLE, method = RequestMethod.GET)
    public ResponseEntity<ConsoleResponse> getConsoleById(@PathVariable("id") Long id) {
        try {
            log.info("getConsoleById to ConsoleResponse by Id: " + id);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(headers);
            ResponseEntity<ConsoleResponse> response =
                    restTemplate.exchange((Path.URL + Path.CONSOLES + "/" + id), HttpMethod.GET, request, ConsoleResponse.class);
            ConsoleResponse console = response.getBody();

            if (console != null) {
                log.info("ConsoleResponse found with successfully: " + console.toString());
                return ResponseEntity.ok(console); // http 200
            } else {
                log.info("ConsoleResponse not found to id: " + id);
                return ResponseEntity.notFound().build(); //http 404
            }
        } catch (Exception e) {
            log.info(" Error to getConsoleById ConsoleResponse: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @RequestMapping(value = Path.CONSOLES, method = RequestMethod.GET)
    public ResponseEntity<List<ConsoleResponse>> getAllConsoles() {
        try {
            log.info("Find all Consoles");
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(headers);
            ResponseEntity<List<ConsoleResponse>> responses =
                    restTemplate.exchange((Path.URL + Path.CONSOLES), HttpMethod.GET, request, new ParameterizedTypeReference<List<ConsoleResponse>>() {});
            List<ConsoleResponse> consoles = responses.getBody();

            log.info("Result the search the Consoles, size: " + consoles.size());
            return ResponseEntity.ok(consoles);
        } catch (Exception e) {
            log.info(" Error to getAllConsoles ConsoleResponse: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @RequestMapping(value = Path.CONSOLES, method = RequestMethod.POST)
    public ResponseEntity<ConsoleResponseSave> saveConsole(@RequestBody ConsoleResponseSave consoleResponseSave) {
        log.info("Save new ConsoleResponse: " + consoleResponseSave);
        try {
            consoleService.saveConsole(new ConsoleResponse(null, consoleResponseSave.getName(), consoleResponseSave.getReleaseYear()));
            log.info("Sent consoleResponse to line with success!: " + consoleResponseSave.toString());

            var uri = ServletUriComponentsBuilder.fromCurrentRequest().path(consoleResponseSave.getName()).build().toUri();
            return ResponseEntity.created(uri).body(consoleResponseSave);
        } catch (ExecutionException e) {
            log.error(" Error :" + e.getMessage());
            e.printStackTrace();
            return (ResponseEntity<ConsoleResponseSave>) ResponseEntity.badRequest();
        } catch (InterruptedException e) {
            log.error(" Error :" + e.getMessage());
            return (ResponseEntity<ConsoleResponseSave>) ResponseEntity.badRequest();
        }
    }

    @RequestMapping(value = Path.CONSOLE_SINGLE, method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteConsole(@PathVariable("id") Long id) {
        try {
            log.info("Find Console Id to after to be deleted: " + id);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(headers);
            ResponseEntity<ConsoleResponse> response =
                    restTemplate.exchange((Path.URL + Path.CONSOLES + "/" + id), HttpMethod.GET, request, ConsoleResponse.class);
            ConsoleResponse console = response.getBody();

            if (console != null) {
                consoleService.deleteConsole(console);
                log.info("Sent consoleResponse to line with success!: " + console.toString());

                return ResponseEntity.noContent().build(); //http 204
            } else {
                log.info("Console not found: " + id);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error(" Error to delete Consoles :" + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @RequestMapping(value = Path.CONSOLE_SINGLE, method = RequestMethod.PUT)
    public ResponseEntity<ConsoleResponse> updateConsole(@PathVariable("id") Long id, @RequestBody ConsoleResponse consoleResponse) {
        try {
            log.info("Find Console Id to after to be updated: " + id + " , Console: " + consoleResponse.toString());
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity<String>(headers);
            ResponseEntity<ConsoleResponse> response =
                    restTemplate.exchange((Path.URL + Path.CONSOLES + "/" + id), HttpMethod.GET, request, ConsoleResponse.class);
            ConsoleResponse console = response.getBody();

            if (console != null) {
                log.info("Console Found: " + console.toString());
                ConsoleResponse newConsole = new ConsoleResponse();
                newConsole.setId(console.getId());
                newConsole.setName(consoleResponse.getName());
                newConsole.setReleaseYear(consoleResponse.getReleaseYear());

                consoleService.updateConsole(newConsole);
                log.info("Sent consoleResponse to line with success!: " + newConsole.toString());

                return ResponseEntity.ok(newConsole); //http 200
            } else {
                log.info("Console not found: " + id);
                return ResponseEntity.notFound().build(); //http 404
            }
        } catch (Exception e) {
            log.error(" Error to update Console :" + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

}
