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
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    /**
     * @Description:
     * create by: xtjnoob
     * create time: 10:37 2019/1/10
     * * @param request
     * * @param response
     * @return void
     */
    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Category> categories = categoryService.getAllCategories();
        request.setAttribute(Constants.CATEGORIES, categories);
        request.getRequestDispatcher("/WEB-INF/jsp/add_book.jsp").forward(request, response);
    }

    /**
     * @Description:
     * create by: xtjnoob
     * create time: 10:37 2019/1/10
     * * @param request
     * * @param response
     * @return void
     */
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Book> books = enctypeParser(request, response, "add");
        if (books != null) {
            bookService.addBooks(books);
            response.sendRedirect("/book/list.do?category=");
        } else {
            response.sendRedirect("/book/list.do?category=");
        }
    }

    /**
     * @Description:
     * create by: xtjnoob
     * create time: 10:38 2019/1/10
     * * @param request
     * * @param response
     * @return void
     */
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

    /**
     * @Description:
     * create by: xtjnoob
     * create time: 10:38 2019/1/10
     * * @param request
     * * @param response
     * @return void
     */
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

    /**
     * @Description:
     * create by: xtjnoob
     * create time: 10:38 2019/1/10
     * * @param request
     * * @param response
     * @return void
     */
    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        try {
            long bookId = Long.parseLong(id);
            // 先删除服务器上的图片
            String img = bookService.getBookById(bookId).getImgPath();
            new File(request.getServletContext().getRealPath("/img")+"/"+img).delete();
            // 后删除数据库中的记录
            bookService.deleteBookById(bookId);
            response.sendRedirect("/book/list.do?category=");
        } catch (NumberFormatException e) {
            response.sendRedirect("/book/list.do?category=");
            e.printStackTrace();
        }
    }

    /**
     * @Description: 处理enctype上传（book）
     * create by: xtjnoob
     * create time: 10:39 2019/1/10
     * * @param request
     * * @param response
     * * @param method
     * @return java.util.List<com.xtjnoob.entity.Book>
     */
    private List<Book> enctypeParser(HttpServletRequest request, HttpServletResponse response, String method) throws IOException, ServletException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        List<Book> books = new ArrayList<>();  // 用于保存批量增加的书本
        Book book = new Book();
        try {
            List<FileItem> fileItems = servletFileUpload.parseRequest(request);
            int index = 0;
            String filedName = null;  // 参数名
            String value = null;      // 参数值
            String imgName = null;    // 上传的图片名
            String dir = null;        // 服务器上保存图片的地址
            for (FileItem fileItem : fileItems) {
                // 处理常规提交的数据
                if (fileItem.isFormField()) {
                    filedName = fileItem.getFieldName();
                    value = fileItem.getString("UTF-8");
                    // 将对应的数据保存Book对象
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
                } else {   // 处理图片上传
                    imgName = fileItem.getName();  // 获取图片名称
                    if (!"".equals(imgName)) {
                        dir = request.getServletContext().getRealPath("/img");  // 获取图片保存的位置
                        imgName = getUUIDName(imgName); // 获取随机的文件名，避免同名文件无法保存
                        book.setImgPath(imgName);  //

                        // 将图片保存至服务器
                        InputStream is = fileItem.getInputStream();
                        OutputStream os = new FileOutputStream(dir + "/" + imgName);
                        int len = 0;
                        byte[] bytes = new byte[1024];
                        for (; (len = is.read(bytes)) != -1; ) {
                            os.write(bytes, 0, len);
                            os.flush();
                        }
                        is.close();
                        os.close();
                    } else {
                        // 如果是添加时没有上传图片返回null，编辑可以不上传图片
                        if ("add".equals(method)) {
                            return null;
                        }
                    }
                }
                index++;
                // 批量上传没5个参数为一本书
                if ("add".equals(method)) {
                    if (index % 5 == 0) {
                        System.out.println(book);
                        books.add(book);
                        book = new Book();
                    }
                }
            }
            // 编辑只能单本书编辑，有且仅有6个参数值
            if("edit".equals(method)){
                // 如果有新的图片上传就删除老图片
                if(book.getImgPath() != null)
                    new File(dir + "/" + bookService.getBookById(book.getId()).getImgPath()).delete();
                books.add(book);
            }
            return books;
        } catch (FileUploadException | NumberFormatException e) { // 产生异常就报错，返回null
            e.printStackTrace();
            return null;
        }
    }

    private String getUUIDName(String fileName) {
        String uuidStr = UUID.randomUUID().toString().replaceAll("-", "");
        int index = fileName.lastIndexOf(".");
        String suffix = fileName.substring(index);
        return uuidStr + suffix;
    }
}
