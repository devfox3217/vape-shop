package com.devfox.bbvape.repo;

import com.devfox.bbvape.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByTypeAndRefOrderByOrd(String type, int ref);
}
