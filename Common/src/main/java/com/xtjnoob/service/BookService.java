package com.xtjnoob.service;

import com.xtjnoob.entity.Book;

import java.util.List;

public interface BookService {
    Book getBookById(Long id);

    List<Book> getBooks(Long categoryId);

    void addBooks(List<Book> books);

    void updateBook(Book book);

    void deleteBookById(Long id);

    List<Book> getBooksWithPage(Long categoryId, int page, int size);
}
