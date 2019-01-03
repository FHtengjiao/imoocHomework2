package com.xtjnoob.service.impl;

import com.xtjnoob.entity.Category;
import com.xtjnoob.mapper.CategoryMapper;
import com.xtjnoob.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        categoryMapper.add(category);
    }
}
