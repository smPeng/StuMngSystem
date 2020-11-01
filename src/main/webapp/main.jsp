<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 引入该页面的样式文件main.css -->
<link rel="stylesheet" type="text/css" href="css/main.css" />
<%@include file="common.jsp" %>
</head>
<body>
	<!-- 顶部用户信息  -->
	<nav class="navbar navbar-default">
	  <div class="container-fluid">
	    <!-- Brand and toggle get grouped for better mobile display -->
	    <div class="navbar-header">
	      <a class="navbar-brand" href="#">Logo</a>
	    </div>
	
	    <!-- Collect the nav links, forms, and other content for toggling -->
	    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	      <ul class="nav navbar-nav navbar-right">
	        <li><a href="#">当前用户:<span class="badge"><shiro:principal /></span></a></li>
	        <li><a href="exit"><span class="glyphicon glyphicon-lock">退出系统</span></a></li>
	      </ul>
	    </div><!-- /.navbar-collapse -->
	  </div><!-- /.container-fluid -->
	</nav>
	<!-- 中间部分 -->
	<div class="pageContainer">
		<!-- 左侧导航栏 -->
		<div class="pageSidebar">
			<ul class="nav nav-pills nav-stacked">
				<shiro:hasAnyRoles name="Tea"><li><a href="academy_list.jsp" target="openArea">学院管理</a></li></shiro:hasAnyRoles>
				<shiro:hasAnyRoles name="Tea,Stu"><li><a href="stu_list.jsp" target="openArea">学生管理</a></li></shiro:hasAnyRoles>
			</ul>
		
		</div>
		<!-- 分割线 -->
		<div class="splitter"></div>
		<!-- 操作部分 -->
		<div class="pageContent">
			<iframe src="" width="100%" height="100%" frameborder="0" name="openArea"></iframe>
		</div>
	</div>
	
	
</body>
</html>