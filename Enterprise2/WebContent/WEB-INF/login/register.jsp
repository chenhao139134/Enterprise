<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>后台管理</title>
<link href="css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>
<style type="text/css">
.m{
	margin-top : 0px;
	color: red;
}
#form{
	height: 320px;
	margin-top: 60px;
}

#form input{
	margin-bottom: 1px;
}
#form .input{
	height: 71px;
}

#register {
	position: relative;
	right: 2px;
}

#login span {
	margin-left: 60px;
	color: red;
}

#login label {
	color: #999;
}

#login a {
	color: #136;
	text-decoration: underline;
	font-style: italic;
}
</style>
</head>
<body>
	<div class="login_box">
		<div class="login_l_img">
			<img src="img/login-img.png" />
		</div>
		<div class="login">
			<div class="login_logo">
				<a href="#"><img src="img/login_logo.png" /></a>
			</div>
			<div class="login_name">
				<p>后台管理系统-注册</p>
			</div>
			<div id="form" >
				<form method="post" class="form-horizontal f1">
					<div class="form-group input">
						<div class="col-sm-12 input">
							<input name="username" type="text" placeholder="用户名">
							<div id="name-msg" class="m"></div>
						</div>
					</div>
					<div class="form-group input">
						<div class="col-sm-12 input">
							<input name="password" type="password" id="password"
								placeholder="密码" />
							<div id="password-msg" class="m"></div>
						</div>
					</div>
					<div class="form-group input">
						<div class="col-sm-12 input">
							<input name="rePassword" type="password" id="rePassword"
								placeholder="重复密码" />
							<div id="rePassword-msg" class="m"></div>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-12">
							<input value="注册" style="width: 100%;" type="button" id="submit">
						</div>
					</div>
					<div id="msg">
						<div id="login">
							<label>已有账号？</label><a href="user?type=loginShow">去登录</a>
							<span></span> 
						</div>
					</div>
				</form>
			</div>
		</div>

	</div>
</body>
<script type="text/javascript">
	$().ready(function() {
		$("input").blur(function() {
			$(".m").empty();
			$("input").css("border","#DCDEE0 1px solid");
		})
		
		
		$("#submit").click(function() {
			var username = $("[name=username]").val();
			var pwd = $("[name=password]").val();
			var rePwd = $("[name=rePassword]").val();
			var flag = true;
			if(username.length < 1){
				$("[name=username]").css("border","#900 1px solid");
				$("#name-msg").html("用户名不能为空！");
				flag = false;
			}
			if(pwd.length < 3){
				$("[name=password]").css("border","#900 1px solid");
				$("#password-msg").html("密码小于三位！");
				flag = false;
			}
			if(rePwd.length < 3){
				$("[name=rePassword]").css("border","#900 1px solid");
				$("#rePassword-msg").html("密码小于三位！");
				flag = false;
			}
			if(pwd != rePwd){
				$("[name=rePassword]").css("border","#900 1px solid");
				$("#rePassword-msg").html("两次密码不一致！");
				flag = false;
			}
			if(flag){
				$.ajax({
					url : "user",
					data : {
						type : "register",
						username : username,
						pwd : pwd
					},
					type : "post",
					dataType : "json",
					success : function(data) {
						if (data.type == "true") {
							location.href = "index";
						} else {
							$("#msg").html(data.msg);
						}
					}
				})
			}
		})
	})
</script>
</html>