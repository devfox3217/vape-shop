package com.devfox.bbvape.repo;

import com.devfox.bbvape.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
