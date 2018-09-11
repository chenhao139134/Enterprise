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
<style type="text/css">
#main {
	width: 400px;
	margin: 20px auto;
}

ul {
	list-style: none;
}
</style>
<title></title>
</head>
<body style="width: 600px;">
	<div id="main">
		<!-- onclick="return false" -->
		<c:if test="${requestScope.pro==null}">
			<form
				data-action="project?type=add"
				class="form-horizontal f1" method="post">
		</c:if>
		<c:if test="${requestScope.pro!=null}">
			<form
				data-action="project?type=update"
				class="form-horizontal f1" method="post">
				<input type="hidden" name="id" value="${requestScope.pro.id}">
		</c:if>
		<div class="form-group">
			<label for="name" class="col-sm-3 control-label">项目名</label>
			<div class="col-sm-6">
				<input type="text" class="form-control input" name="name"
					placeholder="请输入名字" value="${requestScope.pro.name}">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-6">
				<input type="button" class="btn btn-primary" name="submit" value="添加" />
			</div>
		</div>
		</form>
	</div>
</body>

<script type="text/javascript">
	$("[name=submit]").click(function() {
		var id = $("[name=id]").val();
		var name = $("[name=name]").val();
	
		var a = $("form").data("action");
		location.href = a + "&id=" + id + "&name=" + name;
	})
</script>
</html>