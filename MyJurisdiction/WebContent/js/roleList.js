layui.use(['form','layer','table','laytpl'],function(){
		var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;
    	var projectUrl = $("#projectUrl").val();
    	
    	var tableIns = table.render({
        elem: '#userList',
        url : projectUrl+'/Jurisdictions/role/roles',
        page : true,//控制是否开启分页
        limit:5,//默认每页多少条
		limits:[3,5,10],//允许每页有多少条的下拉框
		height : "full-125",
        id : "userListTable",
        where:{//初始化搜索框的值
        	search:null
        },cols : [[
            {type: "checkbox", fixed:"left", width:50},
            //field是实体类的属性,title显示的表头,minWidth是列的宽度,align是字体位置,templet能使用函数给值判断重新赋值
            {field: 'id', title: 'ID', minWidth:60, align:"center"},
            {field: 'name', title: '角色名称', minWidth:80, align:"center"},
            {field: 'desc', title: '角色详情', align:'center',minWidth:150},
            {title: '操作', minWidth:175, templet:'#userListBar',fixed:"right",align:"center",templet:function(d){
            	return "<a style= 'border: 1px solid #555;color:#555;background: #fff;' name='status' class='layui-btn layui-btn-xs layui-btn-warm' lay-event='roles'><i class='layui-icon'>&#xe631;</i>角色权限</a>"
            	+"<a style='background-color:#2E9AFE' class='layui-btn layui-btn-xs' lay-event='edit'><i class='layui-icon'>&#xe642;</i>编辑</a>"
        		+"<a class='layui-btn layui-btn-xs layui-btn-danger' lay-event='del'><i class='layui-icon'>&#xe640;</i>删除</a>";
            }}
        ]]
    });
    	
    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        /*if($(".searchVal").val() != ''){*/
        	table.reload("userListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    search: $(".searchVal").val()  //搜索的关键字
                }
            })
        /*}else{
            layer.msg("请输入搜索的内容");
        }*/
    });

    //添加用户
   /* function addUser(edit){
        var index = layui.layer.open({
            title : "添加用户",
            type : 2,
            content : projectUrl+"/Jurisdictions/user/insert",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".userName").val(edit.userName);  //登录名
                    body.find(".userEmail").val(edit.userEmail);  //邮箱
                    body.find(".userSex input[value="+edit.userSex+"]").prop("checked","checked");  //性别
                    body.find(".userGrade").val(edit.userGrade);  //会员等级
                    body.find(".userStatus").val(edit.userStatus);    //用户状态
                    body.find(".userDesc").text(edit.userDesc);    //用户简介
                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        window.sessionStorage.setItem("index",index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(window.sessionStorage.getItem("index"));
        })
    }
    $(".addNews_btn").click(function(){
        addUser();
    })*/

/*    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('userListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].id);
            }
            layer.confirm('确定删除选中的用户？', {icon: 3, title: '提示信息'}, function (index) {
            	$.ajax({//删除用户
					type : "post",
					url : projectUrl+"/Jurisdictions/user/delete",
					async : false,
					data : {
						"_method" : "DELETE",
						"id" : newsId
					},
					traditional:true,//用传统的方式来序列化数据，那么就设置为 true	加上这个属性数组才能被识别,否则后台接受不到
					dataType : "json",
					success : function(data) {
						tableIns.reload();
		                layer.close(index);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						 alert(XMLHttpRequest.status);
						 alert(XMLHttpRequest.readyState);
						 alert(textStatus);
					}
				});
            })
        }else{
            layer.msg("请选择需要删除的用户");
        }
    });
*/
    //列表操作
    table.on('tool(userList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.location.href = projectUrl + "/Jurisdictions/role/role/" + data.id;
        }else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此用户？',{icon:3, title:'提示信息'},function(index){
            	var id = data.id;
            	$.ajax({//删除用户
					type : "post",
					url : projectUrl+"/Jurisdictions/role/role",
					async : false,
					data : {
						"_method" : "DELETE",
						"id" : id
					},
					success : function(data) {
						tableIns.reload();
						layer.close(index);
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						 alert(XMLHttpRequest.status);
						 alert(XMLHttpRequest.readyState);
						 alert(textStatus);
					}
				});
            });
        }else if(layEvent == "roles"){//角色赋权
        	 window.location.href = projectUrl + "/Jurisdictions/role/jumpRolePer?id="+data.id;
        }
    });

})
