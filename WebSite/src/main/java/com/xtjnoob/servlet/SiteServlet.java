package com.xtjnoob.servlet;

import com.xtjnoob.entity.Book;
import com.xtjnoob.entity.Category;
import com.xtjnoob.service.BookService;
import com.xtjnoob.service.CategoryService;
import com.xtjnoob.webutil.Constants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: xtjnoob
 * @Date: 2019/1/9 13:52
 * @Version 1.0
 */
@Component("siteServlet")
public class SiteServlet extends HttpServlet {

    @Resource
    private BookService bookService;

    @Resource
    private CategoryService categoryService;

    public void list(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String category = request.getParameter("category");
        Long categoryId = null;
        List<Book> books = null;
        try {
            if (!StringUtils.isEmpty(category)) {
                categoryId = Long.parseLong(category);
            }
            books = bookService.getBooks(categoryId);
        } catch (NumberFormatException e) {
            response.sendRedirect("/site/list.do");
            return;
        }
        List<Category> categories = categoryService.getAllCategories();
        request.setAttribute(Constants.CATEGORIES, categories);
        request.setAttribute(Constants.BOOKS, books);
        request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
    }
}
