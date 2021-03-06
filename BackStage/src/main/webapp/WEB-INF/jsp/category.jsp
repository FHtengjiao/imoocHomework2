<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>分类列表</title>
        <link rel="stylesheet" href="../../css/index.css">
        <link rel="stylesheet" href="../../css/bootstrap.min.css">
    </head>
    <body>
        <header>
            <div class="container">
                <c:forEach var="category" items="${categories}">
                    <nav>
                        <a href="${pageContext.request.contextPath}/book/list.do?category=${category.id}">${category.name}</a>
                    </nav>
                </c:forEach>
                <nav>
                    <a href="${pageContext.request.contextPath}/book/list.do?category=">全部</a>
                </nav>
                <nav>
                    <a href="${pageContext.request.contextPath}/category/list.do">分类</a>
                </nav>
            </div>
        </header>
        <div class="clearfix" style="margin-bottom: 10px;"></div>
        <div class="container">
            <div class="row">
                <div class="jumbotron">
                    <h1>图书</h1>
                    <p>图书分类列表</p>
                </div>
            </div>
            <div class="row">
                <div class="panel panel-default">
                    <table class="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th>名称</th>
                            <th>创建时间</th>
                            <th>最后修改时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${categories}" var="category">
                            <tr>
                                <td>${category.name}</td>
                                <td><fmt:formatDate value="${category.createTime}" pattern="yyyy-MM-DD HH:mm:ss"/></td>
                                <td><fmt:formatDate value="${category.updateTime}" pattern="yyyy-MM-DD HH:mm:ss"/></td>
                                <td><a href="${pageContext.request.contextPath}/category/toEdit.do?id=${category.id}" class="btn btn-primary btn-sm " role="button">修改</a></td>
                                <td><a href="${pageContext.request.contextPath}/category/delete.do?id=${category.id}" class="btn btn-primary btn-sm " role="button">删除</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="row">
                <a href="${pageContext.request.contextPath}/category/toAdd.do" role="button" class="btn btn-primary">新&nbsp;建</a>
            </div>
        </div>
        <nav class="navbar navbar-default navbar-fixed-bottom">
            <div class="container">
                <p class="text-center">@慕课网</p>
            </div>
        </nav>
    </body>
</html>