<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>å¾çæ»æ°--layuiåå°ç®¡çæ¨¡æ¿</title>
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
<form class="layui-form">
	<blockquote class="layui-elem-quote news_search">
		<div class="layui-inline">
			<input type="checkbox" name="selectAll" id="selectAll" lay-filter="selectAll" lay-skin="primary" title="å¨é">
		</div>
		<div class="layui-inline">
			<a class="layui-btn layui-btn-sm layui-btn-danger batchDel">æ¹éå é¤</a>
		</div>
		<div class="layui-inline">
			<a class="layui-btn layui-btn-sm uploadNewImg">ä¸ä¼ æ°å¾ç</a>
		</div>
	</blockquote>
	<ul class="layer-photos-demo" id="Images"></ul>
</form>
<script type="text/javascript" src="../../layui/layui.js"></script>
<script type="text/javascript" src="images.js"></script>
</body>
</html>