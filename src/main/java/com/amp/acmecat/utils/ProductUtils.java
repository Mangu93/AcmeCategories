package com.amp.acmecat.utils;

import com.amp.acmecat.domain.Product;
import com.amp.acmecat.repository.ProductRepository;
import com.google.gson.JsonElement;

import java.util.Map;
import java.util.Set;

public class ProductUtils {
    public static Set<Map.Entry<String, JsonElement>> insertEntries(JsonElement jsonElement, ProductRepository productRepository) {
        Set<Map.Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
        for (Map.Entry<String, JsonElement> entry: jsonElement.getAsJsonObject().entrySet()) {
            Product product = new Product(entry.getKey(), entry.getValue().getAsString());
            productRepository.insert(product);
        }
        return entries;
    }
}
