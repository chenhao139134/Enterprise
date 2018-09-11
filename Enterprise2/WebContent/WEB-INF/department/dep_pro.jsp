<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门</title>
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<link
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/department.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/js/libs/layui/css/layui.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/libs/layer/layer.js"></script>
<style type="text/css">
#head {
	padding-top: 30px;
	padding-left: 40px;
}
h2{
	display: inline;
}
h3{
display: inline;}
</style>
</head>
<body style="marign: 20px auto;">
	<div id="head">
		<h2>${requestScope.depName }</h2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<h3>所属项目</h3>
	</div>
	<div id="main">
		<table class="table table-bordered">
			<thead>
				<tr>
					<th class="check"></th>
					<th class="name">项目名</th>
					<th class="empCount">${requestScope.depName } &nbsp;参与人数</th>
					<!-- 					<th class="proCount">项目数</th> -->
					<th class="option">操作</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${requestScope.pros}" var="row">
					<tr class="" data-id="${row.id}">
						<td class="check"><input class="checkbox" type="checkbox"
							name="check" /></td>
						<td class="name">${row.name}</td>
						<td class="empCount"><c:forEach
								items="${requestScope.proEmpCount }" var="map">
								<c:if test="${map.key eq row.id }">
									<a
										href="department?type=emp2pro&did=${requestScope.dId }&pid=${row.id }">${map.value }</a>
								</c:if>
							</c:forEach></td>
						<!-- 						<td class="pro_count">1</td> -->
						<td class="option">
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
	</div>
</body>
</html>