<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery.js"></script>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<style type="text/css">
#main {
	position: relative;
	width: 400px;
	height: 300x;
	margin: 20px auto;
}

ul {
	list-style: none;
}
</style>
<title></title>
</head>
<body>
	<div id="main">
		<form action="employee" class="form-horizontal" role="form"
			method="post">
			<input type="hidden" name="type" value="add" />
			<div class="form-group">
				<label for="firstname" class="col-sm-2 control-label">姓名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="name"
						placeholder="请输入名字">
				</div>
			</div>
			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">性别</label>
				<div class="col-sm-10">
					<input type="radio" name="sex" checked value="男">男 <input
						type="radio" name="sex" value="女">女
				</div>
			</div>
			<div class="form-group">
				<label for="firstname" class="col-sm-2 control-label">年龄</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="age"
						placeholder="请输入年龄"">
				</div>
			</div>
			<div class="form-group">
				<label for="firstname" class="col-sm-2 control-label">部门</label>
				<div class="col-sm-10">
					<select id="dep-select" class="form-control">
						<option value="">选择部门</option>
						<c:forEach items="${requestScope.map }" var="v">
							<option value="${v.value }">${v.key }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="button" id="save-btn" class="btn btn-primary">保存</button>
				</div>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
	$("#save-btn").click(function() {
		var array = new Array();
		var name = $("[name='name']").val();
		var age = $("[name='age']").val();
		var sex = $("[name=sex]:checked").val();
		var dId = $("#dep-select").find("option:selected").val();
		
		location.href = "employee?type=add&name=" + name + "&age=" + age + "&sex=" + sex + "&dId=" + dId;
	})


</script>

</html>