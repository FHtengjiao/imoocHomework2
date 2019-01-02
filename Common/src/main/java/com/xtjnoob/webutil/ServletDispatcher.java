package com.xtjnoob.webutil;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@WebServlet(name = "servletDispatcher", urlPatterns = "*.do")
public class ServletDispatcher extends HttpServlet {

    private WebApplicationContext context;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = this.getServletContext();
        context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 获取servletPath
        String servletPath = request.getServletPath();

        // example: /home/add.do  -> home/add
        String servletMethod = servletPath.substring(1, (servletPath.indexOf(".do") + 1));

        String servlet = null;
        String method = null;

        if (servletMethod.contains("/")) {
            servlet = servletMethod.split("/")[0] + "Servlet";
            method = servletMethod.split("/")[1];
        } else {
            servlet = "selfServlet";
            method = servletMethod;
        }

        // 获取bean
        Object bean = context.getBean(servlet);

        try {
            Method targetMethod = bean.getClass().getMethod(method, HttpServletRequest.class, HttpServletResponse.class);
            targetMethod.invoke(bean, request, response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destroy() {
        context = null;
    }
}
