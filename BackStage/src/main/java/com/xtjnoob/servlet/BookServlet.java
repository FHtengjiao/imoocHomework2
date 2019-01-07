package com.xtjnoob.servlet;

import com.xtjnoob.entity.Book;
import com.xtjnoob.entity.Category;
import com.xtjnoob.service.BookService;
import com.xtjnoob.service.CategoryService;
import com.xtjnoob.webutil.Constants;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

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

    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Category> categories = categoryService.getAllCategories();
        request.setAttribute(Constants.CATEGORIES, categories);
        request.getRequestDispatcher("/WEB-INF/jsp/add_book.jsp").forward(request, response);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Book> books = enctypeParser(request, response);
        bookService.addBooks(books);
        response.sendRedirect("/book/list.do?category=1");
    }

    private List<Book> enctypeParser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        try {
            List<FileItem> fileItems = servletFileUpload.parseRequest(request);
            int index = 0;
            String filedName = null;
            String value = null;
            for (FileItem fileItem : fileItems) {
                if (fileItem.isFormField()) {
                    filedName = fileItem.getFieldName();
                    value = fileItem.getString("UTF-8");
                    System.out.println(filedName + "::" + value);
                    switch (filedName) {
                        case "name":
                            book.setName(value);
                            break;
                        case "categoryId":
                            book.setCategoryId(Long.parseLong(value));
                            break;
                        case "level":
                            book.setLevel(Integer.parseInt(value));
                            break;
                        case "price":
                            book.setPrice(Integer.parseInt(value));
                            break;
                    }
                } else {
                    String realPath = request.getServletContext().getRealPath("/img");
                    book.setImgPath(realPath+"/"+fileItem.getName());
                    InputStream is = fileItem.getInputStream();
                    OutputStream os = new FileOutputStream(realPath+"/"+fileItem.getName());
                    int len = 0;
                    byte[] bytes = new byte[1024];
                    for (; (len = is.read(bytes)) != -1; ) {
                        os.write(bytes, 0, len);
                        os.flush();
                    }
                    is.close();
                    os.close();
                }
                index++;
                if (index % 5 == 0) {
                    book.setCreateTime(new Date());
                    books.add(book);
                    book = new Book();
                }
            }
            return books;
        } catch (FileUploadException e) {
            e.printStackTrace();
            return null;
        }
    }
}
