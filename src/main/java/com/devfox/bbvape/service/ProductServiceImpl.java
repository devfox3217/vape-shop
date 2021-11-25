package com.devfox.bbvape.service;

import com.devfox.bbvape.model.Product;
import com.devfox.bbvape.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> readProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> readProductsAsType(String type) {
        return productRepository.findByType(type);
    }
}
