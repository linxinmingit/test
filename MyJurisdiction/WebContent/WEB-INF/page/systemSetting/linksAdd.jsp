<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>æç« åè¡¨--layuiåå°ç®¡çæ¨¡æ¿ 2.0</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="../../layui/css/layui.css" media="all" />
	<link rel="stylesheet" href="../../css/public.css" media="all" />
</head>
<body class="childrenBody">
<form class="layui-form linksAdd">
	<div class="layui-form-item">
		<div class="layui-upload-list linkLogo">
			<img class="layui-upload-img linkLogoImg">
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">ç½ç«åç§°</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input linkName" lay-verify="required" placeholder="è¯·è¾å¥ç½ç«åç§°" />
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">ç½ç«å°å</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input linkUrl" lay-verify="required|url" placeholder="è¯·è¾å¥ç½ç«å°å" />
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">ç«é¿é®ç®±</label>
		<div class="layui-input-block">
			<input type="text" class="layui-input masterEmail" lay-verify="required|email" placeholder="è¯·è¾å¥ç«é¿é®ç®±" />
		</div>
	</div>
	<div class="layui-form-item">
		<label class="layui-form-label">å±ç¤ºä½ç½®</label>
		<div class="layui-input-block">
			<input type="checkbox" class="layui-input showAddress" lay-text="é¦é¡µ|å­é¡µ" lay-skin="switch" />
		</div>
	</div>
	<div class="layui-form-item">
		<button class="layui-btn layui-block" lay-filter="addLink" lay-submit>æäº¤</button>
	</div>
</form>
<script type="text/javascript" src="../../layui/layui.js"></script>
<script type="text/javascript" src="linkList.js"></script>
</body>
</html>