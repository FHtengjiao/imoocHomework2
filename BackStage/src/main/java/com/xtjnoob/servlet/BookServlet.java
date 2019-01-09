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

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
        if (null == id) {
            response.sendRedirect("/category/list.do");
            return;
        }
        try {
            Long categoryId = null;
            String categoryName = "全部";
            if (!"".equals(id)) {
                categoryId = Long.parseLong(id);
                categoryName = categoryService.findCategoryName(categoryId);
            }
            List<Book> books = bookService.getBooks(categoryId);
            List<Category> categories = categoryService.getAllCategories();
            request.setAttribute(Constants.CATEGORY_NAME, categoryName);
            request.setAttribute(Constants.CATEGORIES, categories);
            request.setAttribute(Constants.BOOKS, books);
            request.getRequestDispatcher("/WEB-INF/jsp/book.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("/book/list.do?category=");
        }
    }

    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Category> categories = categoryService.getAllCategories();
        request.setAttribute(Constants.CATEGORIES, categories);
        request.getRequestDispatcher("/WEB-INF/jsp/add_book.jsp").forward(request, response);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Book> books = enctypeParser(request, response, "add");
        if (books != null) {
            bookService.addBooks(books);
            response.sendRedirect("/book/list.do?category=");
        } else {
            response.sendRedirect("/book/list.do?category=");
        }
    }

    public void toEdit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        try {
            Book book = bookService.getBookById(Long.parseLong(id));
            List<Category> categories = categoryService.getAllCategories();
            request.setAttribute(Constants.BOOK, book);
            request.setAttribute(Constants.CATEGORIES, categories);
            request.getRequestDispatcher("/WEB-INF/jsp/edit_book.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("/book/list.do?category=");
        }
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Book> books = enctypeParser(request, response, "edit");
        if (books == null) {
            response.sendRedirect("/book/list.do?category=");
        } else {
            Book book = books.get(0);
            bookService.updateBook(book);
            response.sendRedirect("/book/list.do?category="+book.getCategoryId());
        }
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        try {
            bookService.deleteBookById(Long.parseLong(id));
            response.sendRedirect("/book/list.do?category=");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private List<Book> enctypeParser(HttpServletRequest request, HttpServletResponse response, String method) throws IOException, ServletException {
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
                        case "id":
                            book.setId(Long.parseLong(value));
                            break;
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
                    if (!"".equals(fileItem.getName())) {
                        String realPath = request.getServletContext().getRealPath("/img");
                        book.setImgPath(fileItem.getName());
                        InputStream is = fileItem.getInputStream();
                        OutputStream os = new FileOutputStream(realPath + "/" + fileItem.getName());
                        int len = 0;
                        byte[] bytes = new byte[1024];
                        for (; (len = is.read(bytes)) != -1; ) {
                            os.write(bytes, 0, len);
                            os.flush();
                        }
                        is.close();
                        os.close();
                    } else {
                        if ("add".equals(method)) {
                            return null;
                        }
                    }
                }
                index++;
                if ("add".equals(method)) {
                    if (index % 5 == 0) {
                        System.out.println(book);
                        books.add(book);
                        book = new Book();
                    }
                }
            }
            if("edit".equals(method)){
                books.add(book);
            }
            return books;
        } catch (FileUploadException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}
