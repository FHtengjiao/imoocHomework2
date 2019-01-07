package com.xtjnoob.service;

import com.xtjnoob.entity.Book;

import java.util.List;

public interface BookService {
    void getBookById();

    List<Book> getBooksByCategory(Long categoryId);

    void addBooks(List<Book> books);
}
