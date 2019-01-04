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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: xtjnoob
 * @Date: 2019/1/4 14:17
 * @Version 1.0
 */
@Component("bookServlet")
public class BookServlet {
    @Resource
    private BookService bookService;

    @Resource
    private CategoryService categoryService;

    /**
     * @Description:
     * create by: xtjnoob
     * create time: 15:13 2019/1/4
     * * @param request
     * * @param response
     * @return void
     */
    public void list(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("category");
        if (StringUtils.isEmpty(id)) {
            response.sendRedirect("/category/list.do");
            return;
        }
        try {
            long categoryId = Long.parseLong(id);
            List<Book> books = bookService.getBooksByCategory(categoryId);
            List<Category> categories = categoryService.getAllCategories();
            String categoryName = categoryService.findCategoryName(categoryId);
            request.setAttribute(Constants.CATEGORY_NAME, categoryName);
            request.setAttribute(Constants.CATEGORIES, categories);
            request.setAttribute(Constants.BOOKS, books);
            request.getRequestDispatcher("/WEB-INF/jsp/book.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("/category/list.do");
        }
    }

}
