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
		var id = "${user.id}";
		$.ajax({//回显用户的角色,勾选复选框
			type : "get",
			url : "${pageContext.request.contextPath }/Jurisdictions/user/getUserRole",
			async : false,
			dataType : "json",
			data : {
				"id" : id
			},
			success : function(data) {
				for ( var i in data) {//成功就回显复选框
					$("input:checkbox").each(function(){
						if($(this).val() == data[i].id){
							$(this).attr("checked","checked");
						}
					});
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status);
				 alert(XMLHttpRequest.readyState);
				 alert(textStatus);
			}
		});
	});
</script>
<body class="childrenBody">
<!-- target属性form表单提交是在整个窗口中打开。  -->
<form:form method="POST" class="layui-form layui-row" action="${pageContext.request.contextPath }/Jurisdictions/user/saveRole" target="_self" modelAttribute="user" >
	<form:hidden path="id"/>
	<div class="layui-col-md6 layui-col-xs12">
		<div class="layui-form-item">
			<div id="ids" class="layui-input-block">
				<form:checkboxes path="role" items="${list}" itemValue="id" itemLabel="name" delimiter="&nbsp;&nbsp;"/>
				<br/><br/><br/>
				<!-- lay-submit="" 进入等待的效果 -->
				<button id="submits" type="submit" class="layui-btn" lay-filter="changeUser">立即提交</button>
				<button type="reset" id="resets" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</div>
</form:form>
<script type="text/javascript" src="${pageContext.request.contextPath }/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/userAdd.js"></script>
</body>
</html>