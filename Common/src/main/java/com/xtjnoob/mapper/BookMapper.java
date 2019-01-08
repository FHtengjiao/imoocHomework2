package com.xtjnoob.mapper;

import com.xtjnoob.entity.Book;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMapper {

    List<Book> getBooks(@Param("categoryId") Long categoryId);

    void addBooks(@Param("books") List<Book> books);

    @Select("SELECT * FROM book WHERE id = #{id}")
    @ResultMap("resultMap")
    Book getBookById(Long id);

    void updateBook(Book book);

    @Delete("DELETE FROM book WHERE id = #{id}")
    void deleteBookById(Long id);
}
