<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %><!-- 导入springmvc的form标签库 -->
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>文章列表--layui后台管理模板 2.0</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/public.css" media="all" />
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/addr.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/ajaxfileupload.js"></script>
</head>
<script type="text/javascript">
	$(function(){
		
	});
</script>
<body class="childrenBody">
<!-- target属性form表单提交是在整个窗口中打开。  -->
<form:form method="POST" id="forms" action="${pageContext.request.contextPath }/Jurisdictions/role/role" target="_self" modelAttribute="role" >
	<c:if test="${role.id != null}">
		<form:hidden path="id"/>
		<input type="hidden" name="_method" value="PUT" >
	</c:if>
	<div class="layui-col-md6 layui-col-xs12">
		<div class="layui-form-item">
			<label class="layui-form-label">角色名字</label>
			<div class="layui-input-block">
				<form:input path="name" placeholder="请输入角色名字" class="layui-input"/>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">角色说明</label>
			<div class="layui-input-block">
				<form:input path="desc" placeholder="请输角色说明" class="layui-input"/>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<!-- lay-submit="" 进入等待的效果 -->
				<button id="submits" type="submit" class="layui-btn" lay-filter="changeUser">立即提交</button>
				<button type="reset" id="resets" class="layui-btn layui-btn-primary">重置</button>
				<button type="button" onClick="javascript :history.back(-1);" class="layui-btn layui-btn-primary">返回</button>
			</div>
		</div>
	</div>
</form:form>
<script type="text/javascript" src="${pageContext.request.contextPath }/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/userAdd.js"></script>
</body>
</html>