package com.xtjnoob.mapper;

import com.xtjnoob.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    @Select("SELECT * FROM category")
    @ResultMap("resultMap")
    List<Category> getAllCategories();

    @Insert("INSERT category(name) VALUES(#{name})")
    void add(Category category);
}
