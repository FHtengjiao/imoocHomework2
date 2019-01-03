package com.xtjnoob.servlet;

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
 * @Date: 2019/1/3 11:09
 * @Version 1.0
 */
@Component("categoryServlet")
public class CategoryServlet {

    @Resource
    private BookService bookService;

    @Resource
    private CategoryService categoryService;

    /**
     * @Description: 默认首页展示分类详情
     * create by: xtjnoob
     * create time: 15:28 2019/1/3
     * * @param request
     * * @param response
     * @return void
     */
    public void list(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<Category> categories = categoryService.getAllCategories();
        request.setAttribute(Constants.CATEGORIES, categories);
        request.getRequestDispatcher("/WEB-INF/jsp/category.jsp").forward(request, response);
    }

    /**
     * @Description: 跳转至分类添加页
     * create by: xtjnoob
     * create time: 15:29 2019/1/3
     * * @param request
     * * @param response
     * @return void
     */
    public void toAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/WEB-INF/jsp/add_category.jsp").forward(request, response);
    }

    /**
     * @Description: noob处理提交的添加分类参数
     *      * create by: xtj
     * create time: 15:30 2019/1/3
     * * @param request
     * * @param response
     * @return void
     */
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        if (StringUtils.isEmpty(name)) {
            response.sendRedirect("/category/toAdd.do");
        } else {
            Category category = new Category();
            category.setName(name);
            categoryService.addCategory(category);
            response.sendRedirect("/category/list.do");
        }
    }

    public void toEdit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id)) {
            response.sendRedirect("/category/list.do");
            return;
        }

        long categoryId = Long.parseLong(id);


        bookService.getBookById();
    }
}
