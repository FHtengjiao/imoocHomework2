package com.xtjnoob.service.impl;

import com.xtjnoob.entity.Category;
import com.xtjnoob.mapper.CategoryMapper;
import com.xtjnoob.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

    @Override
    public void addCategory(Category category) {
        category.setCreateTime(new Date());
        categoryMapper.add(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryMapper.getCategoryById(id);
    }

    @Override
    public void updateCategoryById(Category category) {
        category.setUpdateTime(new Date());
        categoryMapper.updateCategoryById(category);
    }

    @Override
    public void removeCategoryById(Long categoryId) {
        categoryMapper.removeCategoryById(categoryId);
    }

    @Override
    public String findCategoryName(Long categoryId) {
        return categoryMapper.findName(categoryId);
    }
}
