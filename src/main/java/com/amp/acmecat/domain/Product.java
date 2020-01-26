package com.amp.acmecat.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class Product {

    @Id
    public String product;
    public String category;

    public Product(String product, String category) {
        this.product = product;
        this.category = category;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Category{" +
            "product='" + product + '\'' +
            ", category='" + category + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product1 = (Product) o;
        return product.equals(product1.product) &&
            category.equals(product1.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, category);
    }
}
