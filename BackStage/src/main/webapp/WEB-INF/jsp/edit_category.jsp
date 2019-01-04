<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>修改分类</title>
        <link rel="stylesheet" href="../../css/bootstrap.min.css">
        <link rel="stylesheet" href="../../css/add.css">
    </head>
    <body>
        <nav class="navbar navbar-default">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/category/list.do">
                        分类管理
                    </a>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="jumbotron">
                <h1>Hello, Admin!</h1>
                <p>请小心的修改分类记录，要是改了一个错误的就不好了。。。</p>
            </div>
            <div class="page-header">
                <h3><small>修改分类</small></h3>
            </div>
            <form class="form-horizontal" action="${pageContext.request.contextPath}/category/edit.do" method="post">
                <input type="hidden" name="id" value="${category.id}">
                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">名称 ：</label>
                    <div class="col-sm-8">
                        <input name="name" class="form-control" id="name" value="${category.name}">
                    </div>
                </div>
                
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary">保存</button>&nbsp;&nbsp;&nbsp;
                    </div>
                </div>
            </form>
        </div>
        <nav class="navbar navbar-default navbar-fixed-bottom">
            <div class="container">
                <p class="text-center">@慕课网</p>
            </div>
        </nav>
    </body>
</html>
