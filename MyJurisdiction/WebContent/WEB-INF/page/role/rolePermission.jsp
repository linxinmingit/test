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
.laytable-cell-1-0{ 
	width: 50px; 
}
.laytable-cell-1-id{
	width: 163px;
}
.laytable-cell-1-name{
	width: 163px; 
}
.laytable-cell-1-headUrl{
	width: 163px; 
}
.laytable-cell-1-empno{
	width: 163px; 
}
.laytable-cell-1-phone{
	width: 163px; 
}
.laytable-cell-1-status{
	width: 163px; 
}
.laytable-cell-1-email{
	width: 163px; 
}
.laytable-cell-1-sex{ 
	width: 163px; 
}
.laytable-cell-1-addr{ 
	width: 163px; 
}
.laytable-cell-1-10{
	width: 175px; 
}
.layui-table-cell .layui-form-checkbox[lay-skin=primary]{
	top:-5px;
}
</style>
<script type="text/javascript">
	$(function(){
		
		$("#save").click(function(){//点击提交
			var array = [];
			var lengths = $(":checkbox").next("div").length;
			for (var int = 0; int < lengths; int++) {
				var o = $(":checkbox").next("div:eq("+int+")").attr("class");//获得需要传入的id值
				if(o == "layui-unselect layui-form-checkbox layui-form-checked"){//为选中满足
					var id = $(":checkbox").next("div:eq("+int+")").attr("data-find");//获得需要传入关联的的id值
					array.push(id);
				}
			}
			var id1 = "${rid}";
			$.ajax({//给角色赋权限
				type : "post",
				url : "${pageContext.request.contextPath }/Jurisdictions/role/empowerment",
				async : false,
				data : {
					"id1" : id1,
					"array" : array
				},
				traditional:true,//用传统的方式来序列化数据，那么就设置为 true	加上这个属性数组才能被识别,否则后台接受不到
				success : function() {
					window.location.href="${pageContext.request.contextPath }/Jurisdictions/role";
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					 alert(XMLHttpRequest.status);
					 alert(XMLHttpRequest.readyState);
					 alert(textStatus);
				}
			});
		});
		
		$.ajax({
			type : "get",
			url : "${pageContext.request.contextPath }/Jurisdictions/menu/menus",
			async : false,
			dataType : "json",
			success : function(data) {
				for ( var i in data) {
					var booleans = data[i].booleans;//获取属性
					if(booleans == true){//如果等于true就使用右三角
						var icon = "&#xe623;";
						var is = "onclick='myFunction("+data[i].id+");'";
					}else{//否则使用文档
						//var icon = "&#xe621;";
						var icon = "&#xe621;";
						//var is = "";
						var is = "onclick='myPermission("+data[i].id+");'";
					}
					all("tbodys", data[i], icon, is, 'layui-icon', "");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status);
				 alert(XMLHttpRequest.readyState);
				 alert(textStatus);
			}
		});
		
		$("#open").click(function(){//点击全部展开
			openAll();
				//如果该id已经打开了,则不做任何操作
			//}
		});
		
		$("#takeUp").click(function(){//点击全部关闭
			var array = [];
			var length = $("td[data-field='id']").children("div").length;
			for (var int = 0; int < length; int++) {
				var j = $("td[data-field='id']:eq("+int+")").children("div").text();
				array.push(j);
			}
			for ( var i in array) {
				var o = $("#"+array[i]+"s").children("i").attr("class");
				if(o == "layui-icon layui-icon-face-smile"){//表示关闭命令
					myFunction(array[i]);
				}
				//如果该id已经关闭了,则不做任何操作
			}
		});
		
		openAll();//进入页面就打开全部
		
		var id = "${rid}";//获得rid回显复选框
		$.ajax({//给角色赋权限
			type : "post",
			url : "${pageContext.request.contextPath }/Jurisdictions/role/getRolePer",
			async : false,
			data : {
				"id" : id
			},
			traditional:true,//用传统的方式来序列化数据，那么就设置为 true	加上这个属性数组才能被识别,否则后台接受不到
			dataType : "json",
			success : function(data) {
				for ( var i in data) {
					var pid = data[i].pid;
					$("[data-find="+pid+"]").attr("class", "layui-unselect layui-form-checkbox layui-form-checked");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status);
				 alert(XMLHttpRequest.readyState);
				 alert(textStatus);
			}
		});
	});
	
	//实现功能,点击打开树形结构,再次点击关闭
	function myFunction(datas){
		var o = $("#"+datas+"s").children("i").attr("class");//控制关闭或开启
		if(o == "layui-icon"){//表示打开命令
			$.ajax({
				type : "get",
				url : "${pageContext.request.contextPath }/Jurisdictions/menu/menuById",
				async : false,
				data : {
					"id" : datas
				},
				dataType : "json",
				success : function(data) {
						$("#"+datas+"s").children("i").empty();
						$("#"+datas+"s").children("[name='space']").after("<i class='layui-icon layui-icon-face-smile'>&#xe625;</i>");
						for ( var i in data) {
							var booleans = data[i].booleans;//获取属性
							if(booleans == true){//如果等于true就使用右三角
								var icon = "&#xe623;";
								var is = "onclick='myFunction("+data[i].id+");'";
							}else{//否则使用文档
								//var icon = "&#xe621;";
								var icon = "&#xe621;";
								//var is = "";
								var is = "onclick='myPermission("+data[i].id+");'";
							}
							var level = data[i].level;//获取菜单的级别
							var space = "";
							if(level != "1"){//不等于1满足条件
								for (var int = 0; int < level - 1; int++) {//增加空格数		根据级别循环次数
									space += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
								}
							}
							all(datas, data[i], icon, is, "layui-icon", space);
							if(booleans == false){//表示为根
								myPermission(data[i].id);		
							}
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					 alert(XMLHttpRequest.status);
					 alert(XMLHttpRequest.readyState);
					 alert(textStatus);
				}
			});
		}else{//表示关闭命令
			$.ajax({
				type : "get",
				url : "${pageContext.request.contextPath }/Jurisdictions/menu/menuByIds",
				async : false,
				data : {
					"id" : datas
				},
				dataType : "json",
				success : function(data) {
					$("#"+datas+"s").children("i").empty();
					$("#"+datas+"s").children("[name='space']").after("<i class='layui-icon'>&#xe623;</i>");
					for ( var i in data) {//循环出id,然后删除
						$("#"+data[i].id).remove();
						if(data[i].booleans == false){
							myPermission(data[i].id);//删除全部的子菜单的增删改查
						}
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					 alert(XMLHttpRequest.status);
					 alert(XMLHttpRequest.readyState);
					 alert(textStatus);
				}
			});
		}
	}
	
	function deletes(data){
		var r=confirm("是否确认删除？");
		if(r == true){
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath }/Jurisdictions/menu/menu",
				async : false,
				data : {
					"_method" : "DELETE",
					"id" : data
				},
				dataType : "json",
				success : function() {
					location.reload();
				}
			});
		}else{
			return false;
		}
	}
	
	//方法总添加函数		参数依次为需要哪个id后 , 循环的对象, 图标, 是否需要点击函数, class样式(通过这个判断是否为开启闭合), 需要在图标前加入的空格数
	function all(datas, data, icon, is, layui, space){
		var level = data.level;
		if(data.booleans == true){
			var booleans = "trues";
		}else{
			var booleans = "falses";
		}
		level = SectionToChinese(level) + "级菜单";//组成字符串
		var content = "<tr id='"+data.id+"' data-index='0' class=''>"
		+"<td data-field='id' style='width:266px;height:40px;' align='left' data-minwidth='60'>"
		+"<div name='xdk' class='layui-table-cell laytable-cell-1-id'>"+data.id+"</div>"
		+"</td>"
		+"<td "+is+" style='width:266px;height:40px;' data-field='name' align='left' data-minwidth='80'>"
		+"<div id='"+data.id+"s' class='layui-table-cell laytable-cell-1-name'><font name='space'>"+space+"</font><i class="+layui+">"+icon+"</i>&nbsp;"+data.name+"</div>"
		+"</td>"
		+"<td id='roles"+data.id+"' style='width:536px;height:40px;' data-field='name' align='left' data-minwidth='80'>"
		+"<div style='width:529px;' id='myRole"+data.id+"' class='layui-table-cell laytable-cell-1-0 laytable-cell-checkbox'></div>"
		+"</td>"
		+"<td style='width:268px;height:40px;' data-field='name' align='left' data-minwidth='80'>"
		+"<div class='layui-table-cell laytable-cell-1-url'>"+data.url+"</div>"
		+"</td>"
		+"<td id='button"+data.id+"' style='width:358px;height:40px;' data-field='10' align='center' data-content='' data-minwidth='175'>"
		+"<div>"
		+"</div>"
		+"</td>"
		+"</tr>";
		if(datas == "tbodys"){//为tbodys也就是初次加载
			$("#"+datas).append(content);
		}else{
			$("#"+datas).after(content);
		}
		
	}
	
	//数字转中文
	var chnNumChar = ["零","一","二","三","四","五","六","七","八","九"];
    var chnUnitSection = ["","万","亿","万亿","亿亿"];
    var chnUnitChar = ["","十","百","千"];

    function SectionToChinese(section){
        var strIns = '', chnStr = '';
        var unitPos = 0;
        var zero = true;
        while(section > 0){
            var v = section % 10;
            if(v === 0){
                if(!zero){
                    zero = true;
                    chnStr = chnNumChar[v] + chnStr;
                }
            }else{
                zero = false;
                strIns = chnNumChar[v];
                strIns += chnUnitChar[unitPos];
                chnStr = strIns + chnStr;
            }
            unitPos++;
            section = Math.floor(section / 10);
        }
        return chnStr;
    }

    //加载后面的权限节点
    function myPermission(id){
    	$.ajax({
			type : "get",
			url : "${pageContext.request.contextPath }/Jurisdictions/menu/getRole",
			async : false,
			data : {
				"id" : id
			},
			dataType : "json",
			success : function(data) {
				for ( var i in data) {
					$("#myRole"+id+"").append("<input type='checkbox' name='layTableCheckbox' lay-skin='primary'><div onclick='my(this);' data-find='"+data[i].id+"' class='layui-unselect layui-form-checkbox' lay-skin='primary'><i class='layui-icon'></i></div>&nbsp;"+data[i].name+"&nbsp;&nbsp;&nbsp;&nbsp;");
				}
				$("#button"+id).append("<a onclick='selects(this);' style='background-color:#009688' name='status' class='layui-btn layui-btn-xs layui-btn-warm' lay-event='usable'><i class='layui-icon'>&#xe642;</i>全选</a>"
				+"<a onclick='setUp(this);' style='background-color:#1E90FF' name='status' class='layui-btn layui-btn-xs layui-btn-warm' lay-event='usable'><i class='layui-icon'>&#xe60b;</i>取消</a>")
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert("无");
			}
		});
    }
    
    function my(data){//操作复选框		
    	var i = $(data).attr("class");
    	if(i == "layui-unselect layui-form-checkbox"){
			$(data).attr("class","layui-unselect layui-form-checkbox layui-form-checked");
    	}else{
    		$(data).attr("class","layui-unselect layui-form-checkbox");
    	}
    }
    
    //将指定的复选框全部选中
    function selects(data){
    	$(data).parent("td").siblings("[style='width:536px;height:40px;']").find("[lay-skin='primary']").attr("class","layui-unselect layui-form-checkbox layui-form-checked");
    }
    
    //将指定的复选框取消选中
    function setUp(data){
    	$(data).parent("td").siblings("[style='width:536px;height:40px;']").find("[lay-skin='primary']").attr("class","layui-unselect layui-form-checkbox");
    }
    
    function openAll(){
    	var array = [];
		var length = $("[name='xdk']").length;//获取这个选择器选中div的个数
		for (var int = 0; int < length; int++) {
			var j = $("td[data-field='id']:eq("+int+")").text();
			array.push(j);//把值全部加入数据
		}
		for ( var i in array) {
			var o = $("#"+array[i]+"s").children("i").attr("class");//获取到class
			if(o == "layui-icon"){//表示打开命令
				var datas = array[i];
				//var lavels = $("#"+datas+"t").attr("name");
				//if(lavels == "trues"){
					$.ajax({
						type : "get",
						url : "${pageContext.request.contextPath }/Jurisdictions/menu/menuByIds",
						async : false,
						data : {
							"id" : datas
						},
						dataType : "json",
						success : function(data) {
							$("#"+datas+"s").children("i").empty();
							$("#"+datas+"s").children("[name='space']").after("<i class='layui-icon layui-icon-face-smile'>&#xe625;</i>");
							for ( var i in data) {
								var booleans = data[i].booleans;//获取属性
								if(booleans == true){//如果等于true就使用右三角
									var icon = "&#xe625;";
									var is = "onclick='myFunction("+data[i].id+");'";
								}else{//否则	点击后可以查出权限表的数据
									//var icon = "&#xe621;";
									var icon = "&#xe621;";
									//var is = "";
									var is = "onclick='myPermission("+data[i].id+");'";//点击去后台根据id查询权限表的数据
								}
								var level = data[i].level;//获取菜单的级别
								var space = "";
								if(level != "1"){//不等于1满足条件
									for (var int = 0; int < level - 1; int++) {//增加空格数		根据级别循环次数
										space += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
									}
								}
								if(data[i].pid != 0){//不等于0时,加入到父节点的后面
									datas = data[i].pid;
									$("#"+datas+"s").children("i").attr("class","layui-icon layui-icon-face-smile");
								}
								all(datas, data[i], icon, is, "layui-icon layui-icon-face-smile", space);
								if(data[i].booleans == false){
									//显示全部的权限节点
									myPermission(data[i].id);
								}
							}
						}
					});
				}
			}
    }
</script>
<body class="childrenBody">
<form class="layui-form">
	<blockquote class="layui-elem-quote quoteBox">
		<form class="layui-form">
		<input type="hidden" id="rid" name="rid" value="${rid}">
		<div style="width:50%;padding:0;margin:0;float:left;box-sizing:border-box;">
			<button type="button" id="open" class="layui-btn">
			 	全部展开
			</button>
			<button type="button" id="takeUp" class="layui-btn">
				 全部收起
			</button>
			<a onClick="javascript :history.back(-1);">
			<button type="button" class="layui-btn">
			<i class='layui-icon'>&#xe65c;</i>
				返回
			</button>
			</a>
			<button id="save" type="button" class="layui-btn">
			<i class='layui-icon'>&#xe631;</i>
				保存设置
			</button>
		</div>
		<div class="layui-inline" style="padding-left: 435px;">
			<div class="layui-input-inline">
				<input type="text" class="layui-input searchVal" placeholder="请输入搜索的内容" />
			</div>
			<a class="layui-btn search_btn" data-type="reload"><i class="layui-icon">&#xe615;</i>搜索</a>
		</div>
		<a href="${pageContext.request.contextPath }/Jurisdictions/menu/menu">
			<button type="button" class="layui-btn">
			  <i class="layui-icon">&#xe608;</i> 添加菜单
			</button>
		</a>
		</form>
	</blockquote>
	<table id="userList" lay-filter="userList"></table>
	
	<!-- 内容  -->
		<div class="layui-form layui-border-box layui-table-view" lay-filter="LAY-table-1" style="height: 667px;">
		<div class="layui-table-box">
			<div class="layui-table-header">
				<table cellspacing="0" cellpadding="0" border="0" class="layui-table">
				<thead>
				<tr>
				<th data-field="id" style="width:300px;height:40px;" data-minwidth="150">
					<div align="center">
						<span>ID</span>
					</div>
				</th>
				<th data-field="name" style="width:300px;height:40px;" data-minwidth="100">
				<div align="center">
					<span>菜单名</span>
				</div>
				</th>
				<th data-field="empno" style="width:600px;height:40px;" data-minwidth="100">
				<div align="center">
					<span>权限节点</span>
				</div>
				</th>
				<th data-field="phone" style="width:300px;height:40px;" data-minwidth="100">
				<div align="center">
					<span>URL</span>
				</div>
				</th>
				<th data-field="10" style="width:400px;height:40px;" data-minwidth="175">
				<div align="center">
					<span>操作</span>
				</div>
				</th>
				</tr>
				</thead>
				</table>
				</div>
				<div class="layui-table-body layui-table-main" style="height: 586px;">
				<table cellspacing="0" cellpadding="0" border="0" class="layui-table">
				<tbody id="tbodys">
				
					
				
				</tbody>
				</table>
				</div>
				<div class="layui-table-fixed layui-table-fixed-l">
				<div class="layui-table-header">
				<table cellspacing="0" cellpadding="0" border="0" class="layui-table">
				<thead>
				</thead></table>
				</div><div class="layui-table-body" style="height: auto;">
				<table cellspacing="0" cellpadding="0" border="0" class="layui-table">
				<tbody>
				</tbody>
				</table>
				</div>
				</div>
		</div>
	</div>
	
	
	<ul id="demo"></ul>
	<!--操作-->
	<script type="text/html" id="userListBar">
		<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
		<a class="layui-btn layui-btn-xs layui-btn-warm" lay-event="usable">已启用</a>
		<a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
	</script>
</form>
<script type="text/javascript" src="${pageContext.request.contextPath }/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/menuList.js?time="></script>
</body>
</html>