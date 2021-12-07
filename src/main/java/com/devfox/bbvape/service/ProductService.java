package com.devfox.bbvape.service;

import com.devfox.bbvape.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product createProduct(Product product);

    List<Product> readProducts();

    List<Product> readProductsAsType(String type);

    Optional<Product> readProduct(long id);
}
