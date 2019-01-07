package com.xtjnoob.service.impl;

import com.xtjnoob.entity.Book;
import com.xtjnoob.mapper.BookMapper;
import com.xtjnoob.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("bookService")
public class BookServiceImpl implements BookService {

    @Resource
    private BookMapper bookMapper;

    @Override
    public Book getBookById(Long id) {
        return bookMapper.getBookById(id);
    }

    @Override
    public List<Book> getBooksByCategory(Long categoryId) {
        return bookMapper.getBooksByCategory(categoryId);
    }

    @Override
    public void addBooks(List<Book> books) {
        bookMapper.addBooks(books);
    }

    @Override
    public void updateBook(Book book) {
        bookMapper.updateBook(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookMapper.deleteBookById(id);
    }
}
