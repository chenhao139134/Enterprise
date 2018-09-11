<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
<link
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/js/libs/layui/css/layui.css"
	rel="stylesheet">
<style type="text/css">
#main {
	width: 1000px;
	height: 500px;
	margin: 40px auto;
}

.header {
	margin-bottom: 30px;
}

#indep, #outdep {
	width: 600px;
	height: 150px;
	margin: 10px auto;
	border: 1px solid #000;
}

.dep {
	float: left;
	height: 30px;
	line-height: 30px;
	margin: 15px 10px;
	background: #337ab7;
	color: #EEE;
	padding: 0 15px;
	border-radius: 4px;
	cursor: pointer;
}

.select {
	background: rgb(255, 104, 104);
}

#btn {
	text-align: center;
}
</style>
<title></title>
</head>
<body style="width: 600px;">
	<div id="main">
		<!-- onclick="return false" -->

		<form class="form-horizontal f1" onclick="return false;">

			<div class="form-group" class="header">
				<label for="name" class="col-sm-3 control-label">项目名</label>
				<div class="col-sm-8">
					<h3 data-pid="${requestScope.pro.id }">${requestScope.pro.name}</h3>
				</div>
			</div>
			<div id="indep">
				<c:forEach items="${requestScope.inList }" var="v">
					<div class="dep" data-did="${v.id }">${v.name }</div>
				</c:forEach>
			</div>
			<div class="form-group" id="btn">
				<div class="col-sm-offset-2 col-sm-6">
					<input type="button" class="btn btn-primary" id="add-btn"
						value="添加" /> <input type="button" class="btn btn-primary"
						id="remove-btn" value="移除" />
				</div>
			</div>
			<div id="outdep" class="form-group">
				<div id="inpro">
					<c:forEach items="${requestScope.outList }" var="v">
						<div class="dep" data-did="${v.id }">${v.name }</div>
					</c:forEach>
				</div>
			</div>
		</form>
	</div>
</body>

<script type="text/javascript">
	$().ready(function() {
		$(".dep").click(function() {
			$(this).toggleClass("select");
		})
	})
	$("#add-btn").click(function() {
		var pid = $("h3").data("pid");
		if ($("#outdep").find(".select").length > 0) {
			var did = "";
			$("#outdep").find(".select").each(function(index, item) {
				did += $(item).data("did") + ",";
			})
			$.ajax({
				url : "project",
				type : "post",
				data : {
					type : "manageAdd2",
					pid : pid,
					did : did
				},
				dataType : "json",
				success : function(data) {
					if (data == true) {
						var dep = $("#outdep").find(".select");
						dep.removeClass("select")
						$("#indep").append(dep);
					}
				}
			})
		}else{
			alert("请选中数据")
		}
	})
	$("#remove-btn").click(function() {
		var pid = $("h3").data("pid");
		if ($("#indep").find(".select").length > 0) {
			var did = "";
			$("#indep").find(".select").each(function(index, item) {
				did += $(item).data("did") + ",";
			})
			$.ajax({
				url : "project",
				type : "post",
				data : {
					type : "manageRemove2",
					pid : pid,
					did : did
				},
				dataType : "json",
				success : function(data) {
					if (data == true) {
						var dep = $("#indep").find(".select");
						dep.removeClass("select")
						$("#outdep").append(dep);
					}
				}
			})
		}else{
			alert("请选中数据")
		}
	})
</script>
</html>