<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>layui后台管理模板 2.0</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-3.2.1.js"></script>
	<link rel="icon" href="${pageContext.request.contextPath }/favicon.ico">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/index.css" media="all" />
</head>
<script type="text/javascript">
	var datas;//创建一个全局变量
	$(function(){
		$.ajax({//从后台将json的格式传出来
			url: "<%=request.getContextPath()%>/Jurisdictions/menu/getJson",
			type: "POST",
			async: false,
			dataType: "text",
			success : function(data){//显示用户数据
				datas=data;//将值赋给datas变量
				datas = eval('(' + datas + ')'); //将json字符串转成json对象
			},error : function(){
				alert("错误");
			}
		});
		
		var locale = getQueryString("locale");//获取url参数
		if(locale != "" && locale != null){
			if(locale == "en_US"){//回显下拉框
				$("#mySelect").val(2);
			}else{
				$("#mySelect").val(1);
			}
		}
		
		$("#mySelect").change(function(){//下拉框被选中
			var value = $(this).val();
			if(value == "1"){
				window.location.href="<%=request.getContextPath() %>/index?locale=zh_CN";
			}else{
				window.location.href="<%=request.getContextPath() %>/index?locale=en_US";
			}
		});
		
	});
	
	function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return decodeURI(r[2]);
		return null;
	}
</script>
<body class="main_body">
	<div class="layui-layout layui-layout-admin">
		<!-- 顶部 -->
		<div class="layui-header header">
			<div class="layui-main mag0">
				<a href="#" class="logo">layuiCMS 2.0</a>
				<!-- 显示/隐藏菜单 -->
				<a href="javascript:;" class="seraph hideMenu icon-caidan"></a>
				<!-- 顶级菜单 -->
				<ul class="layui-nav mobileTopLevelMenus" mobile>
					<li class="layui-nav-item" data-menu="contentManagement">
						<a href="javascript:;"><i class="seraph icon-caidan"></i><cite>layuiCMS</cite></a>
						<dl class="layui-nav-child">
							<dd class="layui-this" data-menu="contentManagement"><a href="javascript:;"><i class="layui-icon" data-icon="&#xe63c;">&#xe63c;</i><cite>内容管理</cite></a></dd>
							<dd data-menu="memberCenter"><a href="javascript:;"><i class="seraph icon-icon10" data-icon="icon-icon10"></i><cite>用户中心</cite></a></dd>
							<dd data-menu="systemeSttings"><a href="javascript:;"><i class="layui-icon" data-icon="&#xe620;">&#xe620;</i><cite>系统设置</cite></a></dd>
							<dd data-menu="seraphApi"><a href="javascript:;"><i class="layui-icon" data-icon="&#xe705;">&#xe705;</i><cite>使用文档</cite></a></dd>
						</dl>
					</li>
				</ul>
				<ul class="layui-nav topLevelMenus" >
					<li class="layui-nav-item layui-this" data-menu="contentManagement">
						<a href="javascript:;"><i class="layui-icon" data-icon="&#xe63c;">&#xe63c;</i><cite><fmt:message key="contentManagement" /></cite></a>
					</li>
					<li class="layui-nav-item" data-menu="memberCenter" pc>
						<a href="javascript:;"><i class="seraph icon-icon10" data-icon="icon-icon10"></i><cite><fmt:message key="userCenter" /></cite></a>
					</li>
					<li class="layui-nav-item" data-menu="systemeSttings" pc>
						<a href="javascript:;"><i class="layui-icon" data-icon="&#xe620;">&#xe620;</i><cite><fmt:message key="systemSetup" /></cite></a>
					</li>
					<li class="layui-nav-item" data-menu="seraphApi" pc>
						<a href="javascript:;"><i class="layui-icon" data-icon="&#xe705;">&#xe705;</i><cite><fmt:message key="usingDocuments" /></cite></a>
					</li>
				</ul>
			    <!-- 顶部右侧菜单 -->
			    <ul class="layui-nav top_menu">
			    	<div style="float: left">
						<select id="mySelect" style="height: 30px;" id="language" name="language" class="layui-select">
							<option value="1">中文</option>
							<option value="2">English</option>
						</select>
					</div>
					<li class="layui-nav-item" pc>
						<a href="javascript:;" class="clearCache"><i class="layui-icon" data-icon="&#xe640;">&#xe640;</i><cite><fmt:message key="clearCaching" /></cite><span class="layui-badge-dot"></span></a>
					</li>
					<li class="layui-nav-item lockcms" pc>
						<a href="javascript:;"><i class="seraph icon-lock"></i><cite><fmt:message key="lockScreen" /></cite></a>
					</li>
					<li class="layui-nav-item" id="userInfo">
						<a href="javascript:;"><img src="${pageContext.request.contextPath }/Jurisdictions/user/icon" class="layui-nav-img userAvatar" width="35" height="35"><cite class="adminName">${user.name}</cite></a>
						<dl class="layui-nav-child">															
							<dd><a href="javascript:;" data-url="${pageContext.request.contextPath }/Jurisdictions/user/userInfo/${user.id}"><i class="seraph icon-ziliao" data-icon="icon-ziliao"></i><cite>个人资料</cite></a></dd>
							<dd><a href="javascript:;" data-url="page/user/changePwd.jsp"><i class="seraph icon-xiugai" data-icon="icon-xiugai"></i><cite>修改密码</cite></a></dd>
							<dd><a href="javascript:;" class="showNotice"><i class="layui-icon">&#xe645;</i><cite>系统公告</cite><span class="layui-badge-dot"></span></a></dd>
							<dd pc><a href="javascript:;" class="functionSetting"><i class="layui-icon">&#xe620;</i><cite>功能设定</cite><span class="layui-badge-dot"></span></a></dd>
							<dd pc><a href="javascript:;" class="changeSkin"><i class="layui-icon">&#xe61b;</i><cite>更换皮肤</cite></a></dd>
							<dd><a href="login" class="signOut"><i class="seraph icon-tuichu"></i><cite>退出</cite></a></dd>
						</dl>
					</li>
				</ul> 
			</div>
		</div>
		<!-- 左侧导航 -->
		<div class="layui-side layui-bg-black">
			<div class="user-photo">
				<a class="img" href="javascript:;" title="我的头像" ><img src="${pageContext.request.contextPath }/Jurisdictions/user/icon" class="userAvatar"></a>
				<p><fmt:message key="hello" />！<span class="userName">${user.name}</span>, <fmt:message key="welcomeLanding" /></p>
			</div>
			<!-- 搜索 -->
			<div class="layui-form component">
				<select name="search" id="search" lay-search lay-filter="searchPage">
					<option value="0"><fmt:message key="searchPageOrFunction" /></option>
					<option value="1">layer</option>
					<option value="2">form</option>
				</select>
				<i class="layui-icon">&#xe615;</i>
			</div>
			<div class="navBar layui-side-scroll" id="navBar">
				<ul class="layui-nav layui-nav-tree">
					<li class="layui-nav-item layui-this">
						<a href="javascript:;" data-url="page/main.jsp"><i class="layui-icon" data-icon=""></i><cite><fmt:message key="" /></cite></a>
					</li>
				</ul>
			</div>
		</div>
		<!-- 右侧内容 -->
		<div class="layui-body layui-form">
			<div class="layui-tab mag0" lay-filter="bodyTab" id="top_tabs_box">
				<ul class="layui-tab-title top_tab" id="top_tabs">
					<li class="layui-this" lay-id=""><i class="layui-icon">&#xe68e;</i> <cite><fmt:message key="backstageShou'ye" /></cite></li>
				</ul>
				<ul class="layui-nav closeBox">
				  <li class="layui-nav-item">
				    <a href="javascript:;"><i class="layui-icon caozuo">&#xe643;</i> 页面操作</a>
				    <dl class="layui-nav-child">
					  <dd><a href="javascript:;" class="refresh refreshThis"><i class="layui-icon">&#x1002;</i> 刷新当前</a></dd>
				      <dd><a href="javascript:;" class="closePageOther"><i class="seraph icon-prohibit"></i> 关闭其他</a></dd>
				      <dd><a href="javascript:;" class="closePageAll"><i class="seraph icon-guanbi"></i> 关闭全部</a></dd>
				    </dl>
				  </li>
				</ul>
				<div class="layui-tab-content clildFrame">
					<div class="layui-tab-item layui-show">
						<iframe src="main"></iframe>
					</div>
				</div>
			</div>
		</div>
		<input id="sss" type="hidden" value="<fmt:message key="backstageShou'ye" />" />
		<!-- 底部 -->
		<%@include file="../../bottom.jsp" %>
	</div>
	
	<!-- 移动导航 -->
	<div class="site-tree-mobile"><i class="layui-icon">&#xe602;</i></div>
	<div class="site-mobile-shade"></div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath }/layui/layui.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/index.js?time="></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/js/cache.js"></script>
</body>
</html>