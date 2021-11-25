package com.devfox.bbvape.service;

import com.devfox.bbvape.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product);

    List<Product> readProducts();

    List<Product> readProductsAsType(String type);
}
