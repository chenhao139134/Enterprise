<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门</title>
<script src="js/jquery.js"></script>
<link
	href="bootstrap/css/bootstrap.css"
	rel="stylesheet">
<link href="css/department.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/js/libs/layui/css/layui.css" rel="stylesheet">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/libs/layer/layer.js"></script>
<style type="text/css">
</style>
</head>
<body style="marign: 20px auto;">
	<div id="head">
		<div id="add">
			<button type="button" id="add-btn" class="btn btn-primary">增加</button>
			<button type="button" id="save-btn" class="btn btn-primary">批量保存</button>
			<button type="button" id="delete-btn" class="btn btn-primary">批量删除</button>
		</div>
	</div>
	<div id="main">
		<table class="table table-bordered">
			<thead>
				<tr>
					<th class="check"></th>
					<th class="name">部门名</th>
					<th class="empCount">人数</th>
					<th class="proCount">项目数</th>
					<th class="optioon">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${requestScope.deps}" var="row">
					<tr class="" data-id="${row.id}">
						<td class="check"><input class="checkbox" type="checkbox"
							name="check" /></td>
						<td class="name">${row.name}</td>
						<td class="empCount">${row.empCount}</td>
						<td class="proCount"><a href="department?type=proShow&id=${row.id }">${row.proCount}</a></td>
						<td>
							<div class="layui-table-cell ">
								<button class="layui-btn layui-btn-danger layui-btn-xs"
									onclick="del(${row.id});">删除</button>
								<button class="layui-btn layui-btn-xs"
									onclick="openedit(${row.id})">修改</button>
							</div>
						</td>
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
					end="${requestScope.pageInfo.endPage }" step="1"
					var="temp">
					<c:if test="${requestScope.pageInfo.pageNow eq temp}">
						<li class="active"><a class="page-btn" data-page="${temp}">
								<c:out value="${temp} " />
						</a></li>
					</c:if>
					<c:if test="${requestScope.pageInfo.pageNow ne temp}">
						<li><a class="page-btn" data-page="${temp}"> <c:out
									value="${temp} " /></a></li>
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

	var id = 0;
	$("table tbody tr").click(function() {
		var index = $(this).index("table tbody tr");
		id = $(this).data("id");
		if ($(this).hasClass("click")) {
			$(this).removeClass("click");
		} else {
			$(this).addClass("click");
		}

	})
	$("table tbody tr").dblclick(function() {
		
		if (!($(this).hasClass("modify"))) {
			$(this).addClass("modify");
			$(this).unbind();
			var name = $(this).find(".name").text();
			var name_input = '<input type="text" class="form-control" name="name" value="'+ name +'" />';
			
			$(this).find(".name").html(name_input);
		}
	})
	$("#save-btn").click(function() {
		var array = new Array();
		$(".modify").each(function(index, element) {
			var id = $(this).data("id");
			var name = $(this).find("input[name=name]").val();
			var obj = {
				id : id,
				name : name
			}
			array.push(obj);
		})
		
		var str = JSON.stringify(array);
		str = str.replace(/{/g, "%7b");
		str = str.replace(/}/g, "%7d");
		location.href = "department?type=modify&data=" + str;
	})

	$("#add-btn").click(function() {
		location.href = "department?type=addShow";
	})

	
	//批量删除
	$("#delete-btn").click(function() {
		var str = "";
		$(".click").each(function(index, element) {
			str += $(this).data("id") + ",";
		})
		if(str.length > 0){
			location.href = "department?type=deleteBatch&data" + str;
		}else{
			alert("请选中数据")
		}
	})
	
	$(".page-btn").click(function() {
		var num = parseInt($(this).text());
		
		$(".page-btn").parent().removeClass("active");
		$(this).parent().addClass("active");
		loadData(num);
	})

	$(".page-prev").click(function() {
		var num = pageNow - 1;
		if (num > 1) {
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
		location.href = "department?page=" + page;
	}
	
	function del(id) {
		if(confirm("确认删除？")){
			location.href="department?type=delete&id="+id;
		}
	}
	function openedit(id) {
		location.href="department?type=modifyShow&id="+id;
	}
	function pro(id) {
		location.href="department?type=proShow&id=" + id;
	}
	
</script>

</html>