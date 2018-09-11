<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工2</title>
<script src="js/jquery.js"></script>

<script src="js/jquery.form.js"></script>
<!-- <link href="bootstrap/css/bootstrap.css" rel="stylesheet"> -->
<link rel="stylesheet"
	href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link href="css/employee2.css" rel="stylesheet">
<script
	src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">
#main td {
	line-height: 60px;
}
#big{
	width: 300px;
	height: auto;
	position: absolute;
	border: 2px solid #666; 
	display: none;
}
</style>
</head>
<body style="marign: 20px auto;">
	<div id="head">
		<div id="search">
			<form>
				<div class="form-group">
					<div class="col-sm-2">
						<input type="text" class="form-control" name="name"
							placeholder="请输入名字" value="${requestScope.emp.name }">
					</div>
					<div class="col-sm-2">
						<select id="sex-select" class="form-control">
							<option value="">请选择性别</option>
							<option value="男"
								<c:if test="${requestScope.emp.sex eq '男' }"> selected </c:if>>男</option>
							<option value="女"
								<c:if test="${requestScope.emp.sex eq '女' }"> selected </c:if>>女</option>
						</select>
					</div>
					<div class="col-sm-2">
						<input type="text" class="form-control" name="age"
							placeholder="请输入年龄"
							value="${requestScope.emp.age==0 ? '' : requestScope.emp.age}">
					</div>
					<div class="col-sm-2">
						<select id="dep-select" class="form-control">
							<option value="">请选择部门</option>
							<c:forEach items="${requestScope.map }" var="v">
								<c:if test="${v.value eq requestScope.emp.dId }">
									<option value="${v.value }" selected>${v.key }</option>
								</c:if>
								<c:if test="${v.value ne requestScope.emp.dId }">
									<option value="${v.value }">${v.key }</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-offset-1 col-sm-1">
						<button type="button" id="search-btn" class="btn btn-primary">搜索</button>
					</div>
				</div>
			</form>
		</div>
		<div id="add">
			<button type="button" class="btn btn-primary" data-toggle="modal"
				data-target="#myModal">增加</button>
			<button type="button" class="btn btn-primary" id="add-btn">增加</button>
			<button type="button" id="modify-btn" class="btn btn-primary">修改</button>
			<button type="button" id="modify-btn-2" class="btn btn-primary">修改2</button>
			<button type="button" id="delete-btn" class="btn btn-primary">删除</button>
		</div>
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="myModalLabel">添加员工</h4>
					</div>
					<form id="form" action="employee2?type=add" class="form-horizontal"
						enctype="multipart/form-data" method="post">
						<div class="modal-body">
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
									<select id="dep-select" class="form-control" name="dep">
										<option value="">选择部门</option>
										<c:forEach items="${requestScope.map }" var="v">
											<option value="${v.value }">${v.key }</option>
										</c:forEach>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label for="img" class="col-sm-2 control-label">图片</label>
								<div class="col-sm-10">
									<input type="file" class="form-control" name="img"
										value="请选择图片">
								</div>
							</div>

						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<button type="submit" id="submit-btn" class="btn btn-primary">提交更改</button>
						</div>
					</form>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->

	</div>
	<div id="main">
		<table class="table  table-bordered">
			<thead>
				<tr>
					<th class="check"></th>
					<th class="name">姓名</th>
					<th class="sex">性别</th>
					<th class="age">年龄</th>
					<th class="dep">部门</th>
					<th class="option">头像</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${requestScope.emps }" var="emp">
					<tr class="" data-id="${emp.id }" data-dep="${emp.dId }">
						<td class="check"><input class="checkbox" type="checkbox"
							name="check" /></td>
						<td class="name">${emp.name }</td>
						<td class="sex">${emp.sex }</td>
						<td class="age">${emp.age }</td>
						<td class="dep"><c:forEach items="${requestScope.map }"
								var="v">
								<c:if test="${v.value eq emp.dId }">
									<div class="key">${v.key }</div>
								</c:if>
							</c:forEach></td>
						<td class="option"><img alt="${emp.name }" src="/img/${emp.img }" width="50px"
							height="50px" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<div id="page">
			<ul class="pagination">
				<c:if test="${requestScope.pageInfo.pageNow eq 1}">
					<li class="disabled"><a class="page-prev">&laquo;</a></li>
				</c:if>
				<c:if test="${requestScope.pageInfo.pageNow ne 1 }">
					<li><a class="page-prev">&laquo;</a></li>
				</c:if>
				<c:forEach begin="${requestScope.pageInfo.beginPage }"
					end="${requestScope.pageInfo.endPage }" step="1" varStatus="v">
					<c:if test="${requestScope.pageInfo.pageNow eq v.index}">
						<li class="active"><a class="page-btn" data-page="${v.index}">
								<c:out value="${v.index} " />
						</a></li>
					</c:if>
					<c:if test="${requestScope.pageInfo.pageNow ne v.index}">
						<li><a class="page-btn" data-page="${v.index}"> <c:out
									value="${v.index} " /></a></li>
					</c:if>
				</c:forEach>
				<c:if
					test="${requestScope.pageInfo.pageNow eq requestScope.pageInfo.pageCount}">
					<li class="disabled"><a class="page-next disabled" href="#">&raquo;</a></li>
				</c:if>
				<c:if
					test="${requestScope.pageInfo.pageNow ne requestScope.pageInfo.pageCount}">
					<li><a class="page-next" href="#">&raquo;</a></li>
				</c:if>
			</ul>
		</div>
		
		<div id="big">
			<img alt="" src="" width="300px" height="auto">
		</div>
		
	</div>
</body>
<script type="text/javascript">
	var pageNow = ${requestScope.pageInfo.pageNow};
	var pageCount = ${requestScope.pageInfo.pageCount};

	$("#reset-btn").click(function() {
		location.href = "employee2?type=list&page=1";
	})
	$("#search-btn").click(function() {
		loadData(1);
	})
	$("#add-btn").click(function() {
		location.href = "employee2?type=addShow";
	})

	$("img").mouseover(function(ee) {
		$(this).css({"border":"2px solid #665``6","z-index":"2"})
		var p = $(this).prop("src");
		$("#big").find("img").prop("src",p)
		$("#big").css({
				"display" : "block",
				"left" : ee.pageX - 350 + "px",
				"top" : ee.pageY - 250 + "px",
			})
		$(document).mousemove(function(e) {
			$("#big").css({
				"left" : e.pageX - 320 + "px",
				"top" : e.pageY - 250 + "px",
			})
		})
	})
	$("img").mouseleave(function(e) {
		$("#big").css("display","none")		
		$(this).css("border","0px solid #666")
	})
	 
	$("table tbody tr").click(function() {
		var index = $(this).index("table tbody tr");
		id = $(this).data("id");
		if ($(this).hasClass("click")) {
			$(this).removeClass("click");
		} else {
			$(this).addClass("click");
		}
	})

	$("table tbody tr")
			.dblclick(
					function() {
						$(this).unbind("click")
						if (!($(this).hasClass("modify"))) {
							$(this).addClass("modify");
							var name = $(this).find(".name").text();
							var sex = $(this).find(".sex").text();
							var age = $(this).find(".age").text();
							var dep = $(this).data("dep");
							var opsex = "";
							if (sex == "男") {
								opsex = "女";
							} else {
								opsex = "男";
							}
							var name_input = '<input type="text" class="form-control" name="name" value="'+ name +'" />';
							var age_input = '<input type="text" class="form-control" name="age" value="'+ age +'" />';
							var sex_input = '<select class="sex form-control"><option>'
									+ sex
									+ '</option><option>'
									+ opsex
									+ '</option></select>';
							var dep_input = "<div class='dep-name'><select class='form-control'><option value='0'>选择部门</option><c:forEach items='${requestScope.map}' var='d'>"
									+ "<option value='${d.value}'>${d.key }</option>"
									+ "</c:forEach></select></div>";
							$(this).find(".name").html(name_input);
							$(this).find(".sex").html(sex_input);
							$(this).find(".age").html(age_input);
							$(this).find(".dep").html(dep_input);
							$(this).find(".dep-name").find("option").each(
									function(index, element) {
										if ($(element).val() == dep) {
											$(element).attr("selected", true);
										}
									})
						}
					})

	$("#modify-btn-2").click(function() {
		var array = new Array();
		$(".modify").each(function(index, element) {
			var id = $(this).data("id");
			var name = $(this).find("input[name='name']").val();
			var dId = $(this).find(".dep-name").find("option:selected").val();
			var sex = $(this).find(".sex").find("option:selected").text();
			var age = $(this).find("input[name='age']").val();
			var obj = {
				id : id,
				name : name,
				sex : sex,
				age : age,
				dId : dId
			}
			array.push(obj);
		})
		var str = JSON.stringify(array);
		str = str.replace(/{/g, "%7b");
		str = str.replace(/}/g, "%7d");
		location.href = "employee2?type=modify&data=" + str;
	})
	$("#modify-btn").click(function() {
		data("modifyShow")
	})
	$("#delete-btn").click(function() {
		data("delete");
	})

	// 	$("#submit-btn").click(
	// 			function() {
	// 				var array = new Array();
	// 				var name = $("#form").find("[name='name']").val();
	// 				var age = $("#form").find("[name='age']").val();
	// 				var sex = $("#form").find("[name=sex]:checked").val();
	// 				var dId = $("#form").find("#dep-select")
	// 						.find("option:selected").val();

	// 				location.href = "employee2?type=add&name=" + name + "&age="
	// 						+ age + "&sex=" + sex + "&dId=" + dId;
	// 			})

	$(".page-btn").click(function() {
		var num = $(this).data("page");
		loadData(num);
	})

	$(".page-prev").click(function() {
		var num = pageNow - 1;
		if (num > 0) {
			loadData(num);
		}
	})

	$(".page-next").click(function() {
		var num = parseInt(pageNow) + 1;
		if (num <= pageCount) {
			loadData(num);
		}
	})

	function data(type) {
		var ids = new Array();
		$(".click").each(function(index, element) {
			var id = $(this).data("id");
			ids.push(id);
		})
		if (ids.length > 0) {
			location.href = "employee2?type=" + type + "&id=" + ids;
		} else {
			alert("请选中数据")
		}

	}

	function loadData(page) {
		var array = new Array();
		var name = $("[name='name']").val();
		var age = $("[name='age']").val();
		var sex = $("#sex-select").find("option:selected").val();
		var dId = $("#dep-select").find("option:selected").val();

		if (name.length > 0 || age.length > 0 || sex.length > 0
				|| dId.length > 0) {
			var obj = {
				name : name,
				sex : sex,
				age : age,
				dId : dId
			}
			array.push(obj);
			var str = JSON.stringify(array);
			str = str.replace(/{/g, "%7b");
			str = str.replace(/}/g, "%7d");
			location.href = "employee2?type=list&page=" + page + "&data=" + str;
		} else {
			location.href = "employee2?type=list&page=" + page;
		}

	}
</script>

</html>