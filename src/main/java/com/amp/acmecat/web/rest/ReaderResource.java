package com.amp.acmecat.web.rest;

import com.amp.acmecat.domain.Product;
import com.amp.acmecat.repository.ProductRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.amp.acmecat.utils.ProductUtils.insertEntries;

@RestController
@RequestMapping("/api")
public class ReaderResource {
    private final Logger log = LoggerFactory.getLogger(ReaderResource.class);

    private final ProductRepository productRepository;
    ReaderResource(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping("/products")
    public ResponseEntity<String> receiveProducts(@RequestBody String payload) {
        log.info("Received: {}", payload);
        JsonElement jsonElement = JsonParser.parseString(payload);
        Set<Map.Entry<String, JsonElement>> entries = insertEntries(jsonElement, productRepository);
        log.info("Inserted {} entities", entries.size());
        return new ResponseEntity<>("{\"success\":1}", HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<Object> readAllProducts() {
        List<Product> productList = productRepository.findAll();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return new ResponseEntity<>(gson.toJson(productList), HttpStatus.OK);
    }

    @GetMapping("/products/{category}")
    public ResponseEntity<Object> readAllProductsByCategory(@PathVariable String category) {
        Query query = new Query();
        query.addCriteria(Criteria.where("category").is(category));
        List<Product> productList = productRepository.findByCategory(category);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return new ResponseEntity<>(gson.toJson(productList), HttpStatus.OK);
    }
}
