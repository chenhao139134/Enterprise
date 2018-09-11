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
	width: 400px;
	margin: 20px auto;
}

ul {
	list-style: none;
}

#main table {
	width: 450px;
	height: auto;
	position: relative;
	text-align: center;
}

table td, table th {
	text-align: center;
}

table thead tr {
	height: 40px;
}

table tbody tr {
	position: relative;
	height: 30px;
}

.dep-name {
	width: 40%;
}

.empCount {
	width: 20%;
}

.remove {
	width: 40%;
}

.form-group {
	margin-bottom: 30px;
}
</style>
<title></title>
</head>
<body style="width: 600px;">
	<div id="main">
		<!-- onclick="return false" -->

		<form class="form-horizontal f1" onclick="return false;">

			<div class="form-group">
				<label for="name" class="col-sm-3 control-label">项目名</label>
				<div class="col-sm-8">
					<h3>${requestScope.pro.name}</h3>
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-3 control-label">项目组部门</label>
				<div class="col-sm-5">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th class="dep-name">部门名</th>
								<th class="empCount">部门人数</th>
								<th class="remove"></th>
							</tr>
						</thead>
						<tbody id="tb">


							<c:forEach items="${requestScope.inList }" var="in">
								<tr date-id="${in.id }">
									<td class="dep-name">${in.name }</td>
									<td class="empCount">${in.empCount }</td>
									<td class="remove"><button
											class="layui-btn layui-btn-danger layui-btn-xs"
											onclick="remove(${in.id});">移除</button></td>
								</tr>
							</c:forEach>


						</tbody>
					</table>
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-3 control-label">可添加部门</label>
				<div class="col-sm-4">
					<select class="form-control" id="out">
						<c:forEach items="${requestScope.outList }" var="out">
							<option value="${out.id }">${out.name }</option>
						</c:forEach>
					</select>
				</div>
				<label for="add" class="col-sm-3 control-label"><button
						class="btn btn-primary" id="add-btn">添加</button></label>

			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-6">
					<input type="button" class="btn btn-primary" onclick="save()"
						value="确认" />
				</div>
			</div>
		</form>
	</div>
</body>

<script type="text/javascript">
var pid = ${requestScope.pro.id};

	$().ready(function() {
		if($("#out").children().length == 0){
			$("#add-btn").unbind("click");
			$("#add-btn").addClass("disabled");
		}
	})
	$("#add-btn").click(function() {
		var did = $("option:selected").val();

		loadData("manageAdd", did);
	})
	function remove(did) {
		loadData("manageRemove", did);
	}
	function loadData(type, did) {

		$.ajax({
			url : "project",
			type : "post",
			data : {type : type, pid : pid, did : did },
			dataType : "json",
			success : function (data) {
				var inList = data.inList;
				$("#tb").empty();
				$(inList).each(function(index, item) {
					var inHtml = "";
					inHtml += '<tr date-id="'+ item.id +'">';
					inHtml += '<td class="dep-name">'+ item.name +'</td>';	
					inHtml += '<td class="empCount">'+ item.empCount +'</td>';
					inHtml += '<td class="remove"><button	class="layui-btn layui-btn-danger layui-btn-xs"	onclick="remove('+ item.id +');">移除</button></td>';
					inHtml += '</tr>';
				
					$("#tb").append($(inHtml));	
				})
				
				var outList = data.outList;
				$("#out").empty();
				$(outList).each(function(index, item) {
					var outHtml = '<option value="'+ item.id +'">'+ item.name +'</option>';
					$("#out").append($(outHtml));	
				})
			}
		
		})
	}
	
	function save() {
		location.href = "project?page=1";
	}
</script>
</html>