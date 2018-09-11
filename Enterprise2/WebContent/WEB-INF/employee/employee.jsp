<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工</title>
<script src="js/jquery.js"></script>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="css/employee.css" rel="stylesheet">

<style type="text/css">
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
							<option value="男" <c:if test="${requestScope.emp.sex eq '男' }"> selected </c:if>   >男</option>
							<option value="女" <c:if test="${requestScope.emp.sex eq '女' }"> selected </c:if>   >女</option>
						</select>
					</div>
					<div class="col-sm-2">
						<input type="text" class="form-control" name="age"
							placeholder="请输入年龄" value="${requestScope.emp.age==0 ? '' : requestScope.emp.age}">
					</div>
					<div class="col-sm-2">
						<select id="dep-select" class="form-control">
							<option value="">请选择部门</option>
							<c:forEach items="${requestScope.map }" var="v">
								<c:if test="${v.value eq requestScope.emp.dId }">
									<option value="${v.value }" selected>${v.key } </option>
								</c:if>
								<c:if test="${v.value ne requestScope.emp.dId }">
									<option value="${v.value }">${v.key } </option>
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
			<button type="button" id="add-btn" class="btn btn-primary">增加</button>
			<button type="button" id="modify-btn" class="btn btn-primary">修改</button>
			<button type="button" id="modify-btn-2" class="btn btn-primary">修改2</button>
			<button type="button" id="delete-btn" class="btn btn-primary">删除</button>
		</div>
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
					<th class="option">操作</th>
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
						<td class="option">操作</td>
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
	</div>
</body>
<script type="text/javascript">
	var pageNow = ${requestScope.pageInfo.pageNow};
	var pageCount = ${requestScope.pageInfo.pageCount};

	$("#reset-btn").click(function() {
		location.href = "employee?page=1";
	})
	$("#search-btn").click(function() {
		loadData(1);
	})
	$("#add-btn").click(function() {
		location.href = "employee?type=addShow";
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
		location.href = "employee?type=modify&data=" + str;
	})
	$("#modify-btn").click(function() {
		data("modifyShow")
	})
	$("#delete-btn").click(function() {
		data("delete");
	})

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
			location.href = "employee?type=" + type + "&id=" + ids;
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
		
		if (name.length > 0 || age.length > 0 || sex.length > 0 || dId.length > 0) {
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
			location.href = "employee?page=" + page + "&data=" + str;
		} else {
			location.href = "employee?page=" + page;
		}

	}
</script>

</html>