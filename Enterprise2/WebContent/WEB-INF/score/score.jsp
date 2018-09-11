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
<link href="css/score.css" rel="stylesheet">

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
							placeholder="请输入名字" value="${requestScope.sco.emp.name }">
					</div>
					<div class="col-sm-2">
						<select id="dep-select" class="form-control">
							<option value="">请选择部门</option>
							<c:forEach items="${requestScope.map.dep }" var="v">
								<c:if test="${v.id eq requestScope.sco.emp.dep.id }">
									<option value="${v.id }" selected>${v.name }</option>
								</c:if>
								<c:if test="${v.id ne requestScope.sco.emp.dep.id }">
									<option value="${v.id }">${v.name }</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-2">
						<select id="pro-select" class="form-control">
							<option value="">请选择项目</option>
							<c:forEach items="${requestScope.map.pro }" var="v">
								<c:if test="${v.id eq requestScope.sco.pro.id }">
									<option value="${v.id }" selected>${v.name }</option>
								</c:if>
								<c:if test="${v.id ne requestScope.sco.pro.id }">
									<option value="${v.id }">${v.name }</option>
								</c:if>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-2">
						<select id="grade-select" class="form-control">
							<option value="">请选择等级</option>
							<c:forEach items="${requestScope.map.grade }" var="v">
								<c:if test="${v eq requestScope.sco.grade }">
									<option value="${v }" selected>${v }</option>
								</c:if>
								<c:if test="${v ne requestScope.sco.grade }">
									<option value="${v }">${v }</option>
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

			<button type="button" id="save-btn" class="btn btn-primary">保存</button>
		</div>
	</div>
	<div id="main">
		<table class="table  table-bordered">
			<thead>
				<tr>
					<th class="check"></th>
					<th class="name">姓名</th>
					<th class="dep">部门</th>
					<th class="pro">项目</th>
					<th class="value">成绩</th>
					<th class="grade">等级</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${requestScope.scos }" var="sco">
					<tr class="" data-id="${sco.id }">
						<td class="check"><input class="checkbox" type="checkbox"
							name="check" /></td>
						<td class="name" data-eid="${sco.emp.id }">${sco.emp.name }</td>
						<td class="dep" data-did="${sco.emp.dep.id }">${sco.emp.dep.name }</td>
						<td class="pro" data-pid="${sco.pro.id }">${sco.pro.name }</td>
						<td class="value">${sco.value eq 0 ? "" : sco.value }</td>
						<td class="grade">${sco.grade }</td>
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

	$("#search-btn").click(function() {
		loadData(1);
	})

	$("table tbody tr")
			.dblclick(
					function() {
						$("table").unbind();
						$("tr").each(function(index, item) {
							$(item).removeClass("modify");
						})
						
						$(this).addClass("modify");
						var value = $(this).find(".value").text();

						var value_input = '<input type="text" class="form-control col-sm-6" name="value" onBlur="save()" value="'
								+ value + '" style="text-align:center;"/></div>';

						$(this).find(".value").html(value_input);

					})

	function save() {
		
		var modify = $(".modify");
		
		
		var id = modify.data("id");
		var eid = modify.children(".name").data("eid");
		var pid = modify.children(".pro").data("pid");
		var value = modify.find("[name=value]").val();
		modify.find(".value").empty();
		modify.find(".value").text(value);
		modify.removeClass(".modify");
		$.ajax({
			url : "score",
			type : "post",
			data : {
				type : "modify",
				id : id,
				eid : eid,
				pid : pid,
				value : value
			},
			dataType : "json",
			success : function(data) {
				$("table").bind();

				if (data.type = "true") {
					modify.find(".grade").text(data.grade);
				} else {
					alert(data.type)
				}
			}

		})
	}

	$("#save-btn").click(function() {
		var array = new Array();
		$(".modify").each(function(index, element) {
			var id = $(this).data("id");
			var eid = $(this).children(".name").data("eid");
			var pid = $(this).children(".pro").data("pid");
			var value = $(this).find("[name=value]").val();
			alert(id + " " + eid + " " + pid + " " + value)
			var obj = {
				id : id,
				eid : eid,
				pid : pid,
				value : value
			}
			array.push(obj);
		})
		var str = JSON.stringify(array);
		str = str.replace(/{/g, "%7b");
		str = str.replace(/}/g, "%7d");
		location.href = "score?type=modify&data=" + str;
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

	function loadData(page) {
		var array = new Array();
		var name = $("#search").find("[name='name']").val();
		var pid = $("#search").find("#pro-select").find("option:selected")
				.val();
		var did = $("#search").find("#dep-select").find("option:selected")
				.val();
		var grade = $("#search").find("#grade-select").find("option:selected")
				.val();

		if (name.length > 0 || pid.length > 0 || did.length > 0
				|| grade.length > 0) {
			location.href = "score?page=" + page + "&name=" + name + "&did="
					+ did + "&pid=" + pid + "&grade=" + grade;
		} else {
			location.href = "score?page=" + page;
		}
	}
</script>

</html>