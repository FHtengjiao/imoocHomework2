package com.xtjnoob.mapper;

import com.xtjnoob.entity.Book;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMapper {

    @Select("SELECT * FROM book WHERE category_id = #{categoryId}")
    @ResultMap("resultMap")
    List<Book> getBooksByCategory(Long categoryId);

    void addBooks(@Param("books") List<Book> books);
}
