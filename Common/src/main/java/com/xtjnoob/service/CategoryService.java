package com.xtjnoob.service;

import com.xtjnoob.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    void addCategory(Category category);

    Category getCategoryById(Long id);

    void updateCategoryById(Category category);

    void removeCategoryById(Long categoryId);

    String findCategoryName(Long categoryId);
}
