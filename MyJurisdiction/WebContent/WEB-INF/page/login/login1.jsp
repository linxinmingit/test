<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html class="loginHtml">
<head>
	<meta charset="utf-8">
	<title>登录--layui后台管理模板 2.0</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="icon" href="favicon.ico">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/public.css" media="all" />
	
 	<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
	<link rel="stylesheet" href="assets/css/form-elements.css">
    <link rel="stylesheet" href="assets/css/style.css">
    
    <link rel="shortcut icon" href="assets/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">
</head>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
<script type="text/javascript">
	$(function(){
		$("#codes").click(function(){
			$(this).attr('src', '<%=request.getContextPath()%>/code?' + Math.floor(Math.random()*100) ); 
		});
	});
</script>
<body class="loginBody">
	<form class="layui-form" action="${pageContext.request.contextPath }/login/verification" method="POST">
		<div class="login_face"><img src="${pageContext.request.contextPath }/images/face.jpg" class="userAvatar"></div>
		<div class="layui-form-item input-item">
			<label for="userName">用户名</label>
			<input type="text" placeholder="请输入用户名" autocomplete="off" id="name" name="name" class="layui-input" value="${name }" lay-verify="required">
		</div>
		<div class="layui-form-item input-item">
			<label for="password">密码</label>
			<input type="password" placeholder="请输入密码" autocomplete="off" id="passwd" name="passwd" class="layui-input" lay-verify="required">
		</div>
		<div class="layui-form-item input-item" id="imgCode">
			<label for="code">验证码</label>
			<input type="text" placeholder="请输入验证码" style="width:140px;" autocomplete="off" id="code" name="codes" class="layui-input">
			<img id="codes" src="${pageContext.request.contextPath }/code">
		</div>
		<div class="layui-form-item">
			<button class="layui-btn layui-block" type="submit" lay-filter="login" lay-submit>登录</button>
		</div>
		<div class="layui-form-item layui-row">
			<a href="javascript:;" class="seraph icon-qq layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>
			<a href="javascript:;" class="seraph icon-wechat layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>
			<a href="javascript:;" class="seraph icon-sina layui-col-xs4 layui-col-sm4 layui-col-md4 layui-col-lg4"></a>
		</div>
		<input type="hidden" id="hiddens" value="${error}">
	</form>
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/layui/layui.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/login.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/error.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/cache.js"></script>
</body>
</html>