package com.amp.acmecat.repository;

import com.amp.acmecat.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * https://docs.spring.io/spring-data/mongodb/docs/1.2.0.RELEASE/reference/html/mongo.repositories.html
 */
public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{ 'category' : ?0 }")
    List<Product> findByCategory(String category);
}
