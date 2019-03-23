<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %><!-- 导入springmvc的form标签库 -->
<!DOCTYPE html>
<html>
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
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/addr.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/ajaxfileupload.js"></script>
</head>
<script type="text/javascript">
	$(function(){
		$("#userFace").click(function(){//点击头像
			$("#files").click();//触发文件上传事件
		});
		
		$("#files").change(function(){//实现图片上传参数配置
			changes();//调用ajax
		});
		
		
		var addr = "${user.addr.province}";//省
		$("#cmbProvince option[value="+addr+"]").attr("selected","selected");
		provinces(addr);//将省设置入联动中
		
		var city = "${user.addr.city}";//市
		$("#cmbCity option[value="+city+"]").attr("selected","selected");
		area(city);//将市级设置入联动中
		
		var county = "${user.addr.area}";//县
		$("#cmbArea option[value="+county+"]").attr("selected","selected");
		
		/* $("#submits").click(function(){//改善--可以注释 
			$("#forms").submit();
		}); */
		
		function changes(){//用ajax实现图片上传预览
			$.ajaxFileUpload({
                type: "POST",
                url:"${pageContext.request.contextPath}/Jurisdictions/user/upload",
                dataType: "json",
                fileElementId:"files",  // 文件的id
                success: function(d){
                	$("#userFace").attr("src",d.url);
                	$("#filesCopy").val(d.url);//将路径显示到隐藏框中上传到后台
                	$("#file1").empty();
                 	$("#file1").append("<input type='file' id='files' name='files' accept='image/*'>");
                 	$("#files").change(function(){//必须重新绑定事件，否则原来绑定的事件会失效，因为是由ajaxFileUpload插件造成的，每次提交后都会被file新元素覆盖file旧元素，而绑定的change事件则就失效了，需要重新绑定
                 		changes();
                 	});
                },
                error: function () {
                    alert("上传失败");
                },
            });
		}
	});
</script>
<body class="childrenBody">
<!-- target属性form表单提交是在整个窗口中打开。  -->
<form:form method="POST" id="forms" action="${pageContext.request.contextPath }/Jurisdictions/user/userInfo" target="_top" modelAttribute="user" >
	<form:hidden path="id"/>
	<input type="hidden" id="filesCopy" name="filesCopy" value="${user.headUrl }">
	<input type="hidden" name="_method" value="PUT">
	<div class="layui-col-md3 layui-col-xs12 user_right">
		<div class="layui-upload-list">
			<img src="${pageContext.request.contextPath }/Jurisdictions/user/icon" class="layui-upload-img layui-circle userFaceBtn userAvatar" id="userFace">
		</div>
		<div id="file1" style="display: none;">
			<input type="file" id="files" name="files" accept="image/*">
		</div>
		<button type="button" class="layui-btn layui-btn-primary userFaceBtn"><i class="layui-icon">&#xe67c;</i> 掐指一算，我要换一个头像了</button>
		<!-- <p>由于是纯静态页面，所以只能显示一张随机的图片</p> -->
	</div>
	<div class="layui-col-md6 layui-col-xs12">
		<div class="layui-form-item">
			<label class="layui-form-label">用户名</label>
			<div class="layui-input-block">
				<%-- <form:input path="name" disabled="disabled" class="layui-input layui-disabled" /> --%>
				<form:input path="name" disabled="disabled" class="layui-input layui-disabled"/>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">工号</label>
			<div class="layui-input-block">
				<form:input path="empno" disabled="disabled" class="layui-input layui-disabled"/>
			</div>
		</div>
		<!-- <div class="layui-form-item">
			<label class="layui-form-label">用户组</label>
			<div class="layui-input-block">
				<input type="text" value="超级管理员" disabled class="layui-input layui-disabled">
			</div>
		</div> -->
		<!-- <div class="layui-form-item">
			<label class="layui-form-label">真实姓名</label>
			<div class="layui-input-block">
				<input type="text" value="" placeholder="请输入真实姓名" lay-verify="required" class="layui-input realName">
			</div>
		</div> -->
		<div class="layui-form-item" pane="">
			<label class="layui-form-label">性别</label>
			<div class="layui-input-block userSex">
				<form:radiobuttons path="sex" items="${sexs}"/>
				<!-- <input type="radio" name="sex" value="男" title="男" checked="">
				<input type="radio" name="sex" value="女" title="女">
				<input type="radio" name="sex" value="保密" title="保密"> -->
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">手机号码</label>
			<div class="layui-input-block">
				<form:input path="phone" placeholder="请输入手机号码" lay-verify="phone" class="layui-input userPhone"/>
			</div>
		</div>
		<!-- <div class="layui-form-item">
			<label class="layui-form-label">出生年月</label>
			<div class="layui-input-block">
				<input type="text" value="" placeholder="请输入出生年月" lay-verify="userBirthday" readonly class="layui-input userBirthday">
			</div>
		</div> -->
		<div class="layui-form-item userAddress">
			<label class="layui-form-label">家庭住址</label>
			<div>
				<select id="cmbProvince" name="cmbProvince" class="layui-select">
				</select>
				<select id="cmbCity" name="cmbCity" class="layui-select" >
				</select>
				<select id="cmbArea" name="cmbArea" class="layui-select">
				</select>
			</div>
		</div>
		<!-- <div class="layui-form-item">
			<label class="layui-form-label">掌握技术</label>
			<div class="layui-input-block userHobby">
				<input type="checkbox" name="like[javascript]" title="Javascript">
				<input type="checkbox" name="like[C#]" title="C#">
				<input type="checkbox" name="like[php]" title="PHP">
				<input type="checkbox" name="like[html]" title="HTML(5)">
				<input type="checkbox" name="like[css]" title="CSS(3)">
				<input type="checkbox" name="like[.net]" title=".net">
				<input type="checkbox" name="like[ASP]" title="ASP">
				<input type="checkbox" name="like[Angular]" title="Angular">
				<input type="checkbox" name="like[VUE]" title="VUE">
				<input type="checkbox" name="like[XML]" title="XML">
			</div>
		</div> -->
		<div class="layui-form-item">
			<label class="layui-form-label">邮箱</label>
			<div class="layui-input-block">
				<form:input path="email" placeholder="请输入邮箱" lay-verify="email" class="layui-input userEmail"/>
			</div>
		</div>
		<!-- <div class="layui-form-item">
			<label class="layui-form-label">自我评价</label>
			<div class="layui-input-block">
				<textarea placeholder="请输入内容" class="layui-textarea myself"></textarea>
			</div>
		</div> -->
		<div class="layui-form-item">
			<div class="layui-input-block">
				<!-- lay-submit="" 进入等待的效果 -->
				<button id="submits" type="submit" class="layui-btn" lay-filter="changeUser">立即提交</button>
				<button type="reset" id="resets" class="layui-btn layui-btn-primary">重置</button>
			</div>
		</div>
	</div>
</form:form>
<script type="text/javascript" src="${pageContext.request.contextPath }/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/userInfo.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/cacheUserInfo.js"></script>
</body>
</html>