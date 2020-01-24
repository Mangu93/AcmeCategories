package com.amp.acmecat.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.amp.acmecat.repository")
public class MongoConfiguration extends AbstractMongoConfiguration {
    @Override
    public MongoClient mongoClient() {
        // TODO Properly use configuration
        return new MongoClient("localhost", 27017);
    }

    @Override
    protected String getDatabaseName() {
        return "product";
    }
}
