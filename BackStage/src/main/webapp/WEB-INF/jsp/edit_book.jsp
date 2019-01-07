<%@ page isELIgnored="false" contentType="text/html;charset=utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>新建</title>
        <link rel="stylesheet" href="../../css/bootstrap.min.css">
        <link rel="stylesheet" href="../../css/add.css">
        <script src="../../js/jquery.min.js"></script>
    </head>
    <body>
        <nav class="navbar navbar-default">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/book/list.do">
                        慕课网图书管理
                    </a>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="jumbotron">
                <h1>Hello,Admin!</h1>
                <p>请小心的修改图书记录，要是改了一个错误的就不好了。。。</p>
            </div>
            <div class="page-header">
                <h3><small>修改图书</small></h3>
            </div>
            <form class="form-horizontal" action="${pageContext.request.contextPath}/book/edit.do" method="post"  enctype="multipart/form-data">
                <div id="content">
                    <input type="hidden" value="${book.id}" name="id">
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">名称 ：</label>
                        <div class="col-sm-8">
                            <input name="name" type="text" class="form-control" id="name" value="${book.name}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">分类 ：</label>
                        <div class="col-sm-8">
                            <select name="categoryId" class="col-sm-2 form-control" style="width: auto">
                                <!-- 此处数据需要从数据库中读取 -->
                                <c:forEach items="${categories}" var="category">
                                    <option id="category_${category.id}" value="${category.id}">${category.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">星级 ：</label>
                        <div class="col-sm-8">
                            <select name="level" class="col-sm-2 form-control" style="width: auto">
                                <option id="1" value="1">1星</option>
                                <option id="2" value="2">2星</option>
                                <option id="3" value="3">3星</option>
                                <option id="4" value="4">4星</option>
                                <option id="5" value="5">5星</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">价格 ：</label>
                        <div class="col-sm-8">
                            <input name="price" type="number" class="form-control" id="price" value="${book.price}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">图片 ：</label>
                        <div class="col-sm-8">
                            <input name="smallImg" class="file-loading"
                                   type="file" multiple accept=".jpg,.jpeg,.png" data-min-file-count="1"
                                   data-show-preview="true">
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary">保存</button>&nbsp;&nbsp;&nbsp;
                    </div>
                </div>
            </form>
        </div>
        <footer class="text-center" >
            copy@imooc
        </footer>
    <script type="text/javascript">
        $(function () {
            let level = "${book.level}";
            let categoryId = "${book.categoryId}";
            $("select[name=level]").val(level);
            $("select[name=categoryId]").val(categoryId);
        });
    </script>
    </body>
</html>
