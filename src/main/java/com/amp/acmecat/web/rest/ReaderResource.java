package com.amp.acmecat.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ReaderResource {
    private final Logger log = LoggerFactory.getLogger(ReaderResource.class);

    ReaderResource() {

    }

    @PostMapping("/category")
    public ResponseEntity<String> receiveCategory(@RequestBody String payload) {
        log.info("Received: {}", payload);
        return new ResponseEntity<>("{\"success\":1}", HttpStatus.OK);
    }

}
