<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<title></title>
<script src="js/jquery.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css" />
<style type="text/css">
body {
	margin: 0;
	padding: 0;
}

.menu {
	background: #2E3344;
	width: 200px;
	height: 100%;
	color: white;
	position: fixed;
	float: left;
}

.main-menu {
	height: 40px;
	line-height: 40px;
	cursor: pointer;
}

.menu ul {
	padding: 0;
	margin: 0;
}

.menu li {
	list-style: none;
	background: #3A3F51;
	height: 40px;
	line-height: 40px;
	padding-left: 50px;
	cursor: pointer;
}

.menu-icon {
	color: #27C24C;
	padding-left: 15px;
	padding-right: 5px;
}

.menu .angle-icon {
	height: 40px;
	line-height: 40px;
	float: right;
	margin-right: 20px;
}

iframe {
	position: fixed;
	width: 100%;
}

.header {
	height: 50px;
	width: 100%;
	background: #3A3F51;
}

#user-msg {
	position: absolute;
	right: 3%;
	top: 2%;
	color: #FFF;
}

#user-msg a {
	color: #CCC;
}
</style>
</head>
<body>
	<div class="header">
		<div id="user-msg">
			<label>当前用户</label>&nbsp;&nbsp;&nbsp;&nbsp;${sessionScope.username }
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="user?type=logout">退出</a>
		</div>
		<div id="num">
			你是本站第<span id="count">${sessionScope.num}</span>位访问者
		</div>
		<div id="numNow">
			当前在线 <span id="onLine"></span>人
		</div>
	</div>
	<form method="get" target="iframe" onclick="return false">
		<div class="menu">
			<div class="menu-item">
				<div class="main-menu">
					<i class="fa fa-home fa-lg menu-icon"></i> 首页
				</div>

			</div>
			<div class="menu-item">
				<div class="main-menu" id="employee">
					<i class="fa fa-home fa-lg menu-icon"></i> 员工<span
						class="angle-icon"><i class="fa fa-angle-right  "></i></span>
				</div>
				<ul>
					<li class="child-menu" id="employee?">员工管理 <from></li>
					<li class="child-menu" id="employee2?type=list&">员工管理2 <from></li>
				</ul>
			</div>
			<div class="menu-item">
				<div class="main-menu">
					<i class="fa fa-home fa-lg menu-icon"></i> 部门 <span
						class="angle-icon"><i class="fa fa-angle-right"></i></span>
				</div>
				<ul>
					<li class="child-menu" id="department?">部门管理</li>
					<!-- 					<li class="child-menu">部门项目</li> -->
				</ul>
			</div>
			<div class="menu-item">
				<div class="main-menu">
					<i class="fa fa-home fa-lg menu-icon"></i> 项目 <span
						class="angle-icon"><i class="fa fa-angle-right"></i></span>
				</div>
				<ul>
					<li class="child-menu" id="project?">项目管理</li>

				</ul>
			</div>
			<div class="menu-item">
				<div class="main-menu">
					<i class="fa fa-home fa-lg menu-icon"></i> 绩效 <span
						class="angle-icon"><i class="fa fa-angle-right"></i></span>
				</div>
				<ul>
					<li class="child-menu" id="score2?">绩效查看</li>
					<li class="child-menu" id="score?">绩效管理</li>
				</ul>
			</div>
		</div>

	</form>

	<iframe id="iframe" name="iframe" scrolling="auto" frameborder="0"
		style="width: 1166px; height: 100%; margin-left: 200px;"></iframe>
</body>
<script type="text/javascript">
	var websocket = null;

	//判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		websocket = new WebSocket(
				"ws://192.168.0.154:8080/Enterprise2/websocket");
	} else {
		alert('没有建立websocket连接')
	}

	//连接发生错误的回调方法
	websocket.onerror = function() {
		console.log("错误");
	};

	//连接成功建立的回调方法
	websocket.onopen = function(event) {
		console.log("建立连接");
	}

	//接收到消息的回调方法
	websocket.onmessage = function(event) {
		var data = event.data;
		$("#onLine").html(data);
		console.log("收到消息  " + data);
	}

	//连接关闭的回调方法
	websocket.onclose = function() {
		console.log("close");
	}

	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口。
	window.onbeforeunload = function() {
		websocket.close();
	}

	//关闭连接
	function closeWebSocket() {
		websocket.close();
	}
</script>


<script type="text/javascript">
	$().ready(function() {
		$('.main-menu').next("ul").hide();
	})
	$('.main-menu').click(function() {
		$(this).next().slideToggle();
		var has = $(this).find('.angle-icon').hasClass("fa-rotate-90");
		if (has) {
			$(this).find('.angle-icon').removeClass("fa-rotate-90");
		} else {
			$(this).find('.angle-icon').addClass("fa-rotate-90");
		}
	});
	var menuwidth = $('.menu').width();
	var sreenwidth = window.screen.width;
	var framewidth = sreenwidth - menuwidth;
	$('iframe').width(parseInt(framewidth));
	function show(url) {
		$('iframe').attr('src', url);
	}
	$(".menu li").click(function() {
		var path = $(this).prop("id");
		var dir = $(this).parent().prev("div").prop("id");
		path = path + "page=1";
		$.ajax({
			type : "get",
			url : path,
			success : function() {
				show(path)
			}
		})
	})
</script>
</html>