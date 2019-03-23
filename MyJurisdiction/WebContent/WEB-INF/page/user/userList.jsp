<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>用户中心--layui后台管理模板 2.0</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/public.css" media="all" />
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
</head>
<style>
	<!-- 分页居中样式 -->
	/* #layui-table-page1{
		text-align:center;
	} */
	.layui-form-checkbox[lay-skin=primary] i{top:5px;}
</style>
<script type="text/javascript">
</script>
<body class="childrenBody">
<form class="layui-form">
	<input id="pageSize" type="hidden" />
	<input id="pageNumber" type="hidden" value="1"/>
	<blockquote class="layui-elem-quote quoteBox">
		<form class="layui-form">
			<div class="layui-inline">
				<div class="layui-input-inline">
					<input type="text" id="search" class="layui-input searchVal" placeholder="请输入搜索的内容" />
				</div>
				<a class="layui-btn search_btn" id="searchs" data-type="reload">
					<i class="layui-icon">&#xe615;</i>搜索
				</a>
			</div>
			<div class="layui-inline">
				<a href="${pageContext.request.contextPath }/Jurisdictions/user/user" class="layui-btn layui-btn-normal addNews_btn">
					<i class="layui-icon">&#xe608;</i>添加用户
				</a>
			</div>
			<div class="layui-inline">
				<a class="layui-btn layui-btn-danger layui-btn-normal delAll_btn"><i class="layui-icon">&#xe640;</i>批量删除</a>
			</div>
		</form>
	</blockquote>
	<table id="userList" lay-filter="userList"></table>
	<div class="pagination"><!-- 分页 -->
		<ul id="paging">
		</ul>
	</div>
	<!--操作-->
	<script type="text/html" id="userListBar">
		<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
		<a {{d.status == "1" ? "class='layui-btn layui-btn-xs' lay-event='usable'>已启用" : "class='layui-btn layui-btn-xs layui-btn-danger' lay-event='usable'>已禁用"}}</a>
		<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
	</script>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath }/layui/layui.js"></script>
<!-- 加了?time是清除缓存 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/userList.js?time="></script>
</body>
</html>