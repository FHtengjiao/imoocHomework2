<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>Home</title>
	<!-- Custom Theme files -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="" />
	
	<!-- //Custom Theme files -->
	<link href="../../css/bootstrap.css" type="text/css" rel="stylesheet" media="all">
	<link href="../../css/style.css" type="text/css" rel="stylesheet" media="all">
	<!-- js -->
	<script src="../../js/jquery.min.js"></script>
	<script type="text/javascript" src="../../js/bootstrap-3.1.1.min.js"></script>
	<!-- //js -->
	<!-- cart -->
	<script src="../../js/simpleCart.min.js"> </script>
	<!-- cart -->
	<script type="text/javascript">
       
	</script>
</head>
<body>
<!--header-->
<div class="header">
	<div class="container">
		<nav class="navbar navbar-default" role="navigation">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<h1 class="navbar-brand"><a  href="">IMOOC</a></h1>
			</div>
			<!--navbar-header-->
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="/site/list.do" class="active">首页</a></li>
					<!-- 此处分类应从数据库中读取 -->
					<c:forEach items="${categories}" var="category">
						<li class="dropdown">
							<a href="/site/list.do?category=${category.id}">${category.name}</a>
						</li>
					</c:forEach>
				</ul>
			</div>
		</nav>
	</div>
		<div class="clearfix"> </div>
</div>
<!--//header-->
<!--banner-->
<div class="banner">
	<div class="container" style="height: 300px">
		<h2 class="hdng">IMOOC <span>图书</span></h2>
		<p>读万卷书，行万里路</p>
		<a href="#">SHOP NOW</a>
		<!--
		<div class="banner-text">
			<img src="../../../images/tushu.jpeg" alt=""/>
		</div>
		-->
	</div>
</div>
<!--//banner-->
<!--gallery-->
<div class="gallery">
	<div class="container">
		<div class="gallery-grids">
			<c:forEach var="book" items="${books}">
				<div class="col-md-3 gallery-grid " style="float:left">
					<a href="#">
						<img src="http://127.0.0.1:8080/img/${book.imgPath}" class="img-responsive" alt=""/>
						<div class="gallery-info">
							<p><span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> view</p>
							<a class="shop" href="#">SHOP NOW</a>
							<div class="clearfix"> </div>
						</div>
					</a>
					<div class="galy-info">
						<p style="size: 30px">${book.name}</p>
						<div class="galry">
							<div class="prices">
								<h5 class="item_price">￥${book.price}</h5>
							</div>
							<div class="rating">
								<c:forEach begin="1" end="${book.level}" var="i">
									<span>☆</span>
								</c:forEach>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
			</c:forEach>
		</div>
		<div  align="right">
			<!-- 分页部分可以参照MyBatis拦截器的例子-->
			
		</div>
	</div>

</div>
<!--//gallery-->
<!--subscribe-->
<!--//subscribe-->
<!--footer-->
<!--//footer-->
<div class="footer-bottom">
	<div class="container">
		<p>Copyright © 2017 imooc.com All Rights Reserved | 京ICP备 13046642号-2</p>
	</div>
</div>
</body>
</html>