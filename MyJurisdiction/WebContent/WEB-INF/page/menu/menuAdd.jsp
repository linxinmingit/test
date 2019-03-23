<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %><!-- 导入springmvc的form标签库 -->
<head>
	<meta charset="utf-8">
	<title>个人资料--layui后台管理模板 2.0</title>
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
<script type="text/javascript">
	$(function(){
		//显示数据库数据的下拉框
		$.ajax({
			type : "get",
			url : "${pageContext.request.contextPath }/Jurisdictions/menu/queryAll",
			async : false,
			dataType : "json",
			success : function(data) {
				for ( var i in data) {
					var name = "${menu.name}";
					if(data[i].name != name){//父级菜单不能为自己,这里排除
						$("#selects").append("<option value="+data[i].id+">"+data[i].name+"</option>");
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status);
				 alert(XMLHttpRequest.readyState);
				 alert(textStatus);
			}
		});
		
		//显示数据库数据的图标
		$.ajax({
			type : "get",
			url : "${pageContext.request.contextPath }/Jurisdictions/menu/getPic",
			async : false,
			dataType : "json",
			success : function(data) {
				for ( var i in data) {
					if(i == 0){
						$("#pics").append("<i class='layui-icon'>"+data[i].url+"</i><input type='radio' name='pic' value='"+data[i].id+"' checked=''>");
					}else{
						$("#pics").append("<i class='layui-icon'>"+data[i].url+"</i><input type='radio' name='pic' value='"+data[i].id+"'>")
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status);
				 alert(XMLHttpRequest.readyState);
				 alert(textStatus);
			}
		});
		//如果不是指定菜单添加按钮则什么都不回显
		var pid = "${pid}";//这里是父级菜单的值
		if(pid != null && pid != ""){//指定菜单添加页面回显下拉框
			$("#selects").val(pid);
		}else{
			var id = "${menu.id}";//这里是id
			if(id != null && id != ""){//说明修改页面进入
				var picId = "${picId}";
				var pid = "${menu.pid}";
				$("[name=pic]").each(function(){
					if($(this).val() == picId){
						$(this).attr("checked","checked");
					}
				});
				$("#selects").val(pid);//回显下拉框
			}
		}
	});
	
	function myFunction(){//提交前进行判断
		var selects = $("#selects").val();
		if(selects == -1){//复选框没选不允许提交
			return false;
		}
		return true;
	}
</script>
<body class="childrenBody">				
<form:form class="layui-form layui-row" onsubmit="return myFunction();" method="POST" action="${pageContext.request.contextPath }/Jurisdictions/menu/menu" target="_self" modelAttribute="menu" >
	<c:if test="${menu.id != null}">
		<form:hidden path="id"/>
		<input type="hidden" name="_method" value="PUT">
	</c:if>
	
	<div class="layui-col-md6 layui-col-xs12">
		<div class="layui-form-item">
			<label class="layui-form-label">菜单名</label>
			<div class="layui-input-block">
				<form:input path="name" placeholder="请输入菜单名" class="layui-input"/>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">URL</label>
			<div class="layui-input-block">
				<form:input path="url" placeholder="请输入URL" lay-verify="required" class="layui-input realName"/>
			</div>
		</div>
		<div class="layui-form-item userAddress">
			<label class="layui-form-label">上级菜单</label>
			<div class="layui-input-inline">
				<select id="selects" name="pid" lay-filter="province" class="province">
					<option value="-1">请选择上级菜单</option>
					<option value="0">无</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item" pane="">
			<label class="layui-form-label">图标</label>
			<div id="pics" class="layui-input-block userSex">
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-filter="changeUser">立即提交</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
				<button type="button" onClick="javascript :history.back(-1);" class="layui-btn layui-btn-primary">返回</button>
			</div>
		</div>
	</div>
</form:form>
<script type="text/javascript" src="${pageContext.request.contextPath }/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/userInfo.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/cacheUserInfo.js"></script>
</body>
</html>