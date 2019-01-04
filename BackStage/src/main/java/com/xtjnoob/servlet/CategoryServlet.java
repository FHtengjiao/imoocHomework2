package com.xtjnoob.servlet;

import com.xtjnoob.entity.Category;
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

    /**
     * @Description: 跳转编辑分类页面
     * create by: xtjnoob
     * create time: 12:57 2019/1/4
     * * @param request
     * * @param response
     * @return void
     */
    public void toEdit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id)) {
            response.sendRedirect("/category/list.do");
            return;
        }
        try {
            long categoryId = Long.parseLong(id);
            Category category = categoryService.getCategoryById(categoryId);
            request.setAttribute(Constants.CATEGORY, category);
            request.getRequestDispatcher("/WEB-INF/jsp/edit_category.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("/category/list.do");
        }
    }

    /**
     * @Description: 处理编辑分类提交的数据
     * create by: xtjnoob
     * create time: 13:08 2019/1/4
     * * @param request
     * * @param response
     * @return void
     */
    public void edit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        String name  = request.getParameter("name");

        if (StringUtils.isEmpty(id) || StringUtils.isEmpty(name)) {
            response.sendRedirect("/category/list.do");
            return;
        }
        try {
            long categoryId = Long.parseLong(id);
            Category category = new Category();
            category.setId(categoryId);
            category.setName(name);
            categoryService.updateCategoryById(category);
            response.sendRedirect("/category/list.do");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("/category/list.do");
        }
    }

    /**
     * @Description: 删除分类
     * create by: xtjnoob
     * create time: 14:16 2019/1/4
     * * @param request
     * * @param response
     * @return void
     */
    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id)) {
            response.sendRedirect("/category/list.do");
            return;
        }
        try {
            long categoryId = Long.parseLong(id);
            categoryService.removeCategoryById(categoryId);
            response.sendRedirect("/category/list.do");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("/category/list.do");
        }
    }
}
