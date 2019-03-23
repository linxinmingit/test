layui.use(['form','layer','jquery'],function(){
	$(function(){
		var error = $("#hiddens").val();
		if(error != null && error != ""){
			$("#name").click();
			layer.msg(error);
		}
	});
})