package com.devfox.bbvape.service;

import com.devfox.bbvape.model.Category;

import java.util.List;

public interface CategoryService {
    Code saveCategory(Category category);

    Code updateCategory(Category category);

    List<Category> getCategories();

    List<Category> getHighCategory(String type);

    void deleteCategory(int id);

    List<Category> readCategoryAsType(String type);
}
