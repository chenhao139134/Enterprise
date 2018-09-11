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
#imgs img{
	height : 100px;
	width: auto;
	margin: 3px;
}
</style>
<title></title>
</head>
<body>
	<div id="main">
		<form action="employee2?type=add" class="form-horizontal" role="form"
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
					<select id="dep-select" class="form-control" name="dId">
						<option value="">选择部门</option>
						<c:forEach items="${requestScope.map }" var="v">
							<option value="${v.value }">${v.key }</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-2 control-label">图片</label>
				<div class="col-sm-6">
					<input type="file" class="form-control" name="img">
				</div>
				<button type="button" id="upload" class="btn btn-primary">上传</button>
			</div>
			
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" id="save-btn" class="btn btn-primary">保存</button>
				</div>
			</div>

			<div id="imgs">
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">
	$("#upload").click(function() {
		var formData = new FormData();
		for(var i = 0; i < $("[name=img]")[0].files.length; i++){
			formData.append("img", $("[name=img]")[0].files[i]);
		}
		$.ajax({
			url : "employee2?type=upload",
			type : "post",
			data : formData,
			cache : false,
			processData : false,
			contentType : false,
			dataType : "text",
			success : function(data) {
				var p = '<img src="/img/'+ data +'"/>'
				var i = '<input type="hidden" name="imgName" value="'+ data +'" />'
				$("#imgs").append(p);
				$("#imgs").append(i);
			}
			
		})
	})
	$(document).on("click", "#imgs img", function() {
		var img = $(this);
		var imgName = $(img).prop("src");
		$.ajax({
			url : "employee2?type=removeImg",
			type : "post",
			data : {imgName : imgName},
			dataType : "text",
			success : function(data) {
				if(data = "true"){
					$(img).next().remove();
					$(img).remove();
				}
			}
		})
	})
</script>
</html>