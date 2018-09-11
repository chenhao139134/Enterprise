<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>后台管理</title>
<link href="css/login.css" rel="stylesheet" type="text/css" />
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">

<script type="text/javascript" src="js/jquery.js"></script>
<style type="text/css">
#form .input {
	margin: 2px 0;
}

#register {
	position: relative;
	right: 2px;
}


#register span {
	margin-left: 60px;
	color: red;
}
#register label {
	color: #999;
}#register a {
	color: #136;
	text-decoration: underline;
	font-style: italic;
}
</style>

</head>
<body style="background-color: #93defe;">
	<div class="login_box">
		<div class="login_l_img">
			<img src="img/login-img.png" />
		</div>
		<div class="login" style="height: 400px;">
			<div class="login_logo">
				<a href="#"><img src="img/login_logo.png" /></a>
			</div>
			<div class="login_name">
				<p>后台管理系统-登录</p>
			</div>
			<form method="post" class="form-horizontal f1" id="form">
				<div class="form-group input">
					<div class="col-sm-12">
						<input name="username" type="text" placeholder="用户名">
					</div>
				</div>
				<div class="form-group input">
					<div class="col-sm-12">
						<input name="password" type="password" id="password"
							placeholder="密码" />
					</div>
				</div>
				<div class="form-group input" id="randcode">
					<div class="col-sm-8">
						<input name="randcode" type="text" placeholder="验证码" />
					</div>
					<div class="col-sm-4" style="line-height: 50px; height: 50px;">
						<img src="user?type=randcode" style="cursor: pointer;">
					</div>
				</div>
				<div class="form-group input">
					<div class="col-sm-12">
						<input value="登录" style="width: 100%;" type="button" id="submit">
					</div>

				</div>
				<div id="msg">
					<div id="register">
						<label>没有账号？</label><a href="user?type=registerShow">去注册</a>
						<span></span> 
					</div>
				</div>
			</form>
		</div>

	</div>
</body>
<script type="text/javascript">
	
</script>


<script type="text/javascript">
	$().ready(function() {
		
		var i = 1;
		$("#randcode img").click(function() {
			$(this).attr("src", "user?type=randcode&i=" + (i++));
		})
		
		$("input").focus(function() {
			$("#msg span").empty();
		})
		
		$("#submit").click(function() {
			var username = $("[name=username]").val();
			var pwd = $("[name=password]").val();
			var randcode = $("[name=randcode]").val();
			
			
			
			$.ajax({
				url : "user",
				data : {
					username : username,
					pwd : pwd,
					randcode : randcode
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					if (data.type == "true") {
						location.href = "index";
					} else {
						$("#msg span").empty();
						$("#msg span").append(data.msg);
						$("#randcode img").attr("src","user?type=randcode&i="+ (i++));
					}
				}
			})
		})
	})
</script>
</html>