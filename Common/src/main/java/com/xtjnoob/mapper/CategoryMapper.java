package com.xtjnoob.mapper;

import com.xtjnoob.entity.Category;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper {
    @Select("SELECT * FROM category")
    @ResultMap("resultMap")
    List<Category> getAllCategories();

    @Insert("INSERT category(name) VALUES(#{name})")
    void add(Category category);

    @Select("SELECT * FROM category WHERE id = #{id}")
    Category getCategoryById(Long id);

    @Update("UPDATE category SET name=#{name}, update_time=#{updateTime} WHERE id=#{id}")
    void updateCategoryById(Category category);

    @Delete("DELETE FROM category WHERE id = #{id}")
    void removeCategoryById(long id);

    @Select("SELECT name FROM category WHERE id = #{categoryId}")
    String findName(Long categoryId);
}
