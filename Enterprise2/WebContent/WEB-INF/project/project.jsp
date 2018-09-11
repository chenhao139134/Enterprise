<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门</title>
<script src="js/jquery.js"></script>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="css/project.css" rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/js/libs/layui/css/layui.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/libs/layer/layer.js"></script>
<script
	src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<style typ="text/css">
#indep, #outdep {
	width: 500px;
	height: 150px;
	margin: 10px auto;
	border: 1px solid #000;
}

.dep {
	position: relative;
	float: left;
	height: 30px;
	line-height: 30px;
	margin: 15px 10px;
	background: #337ab7;
	color: #EEE;
	padding: 0 15px;
	border-radius: 4px;
	cursor: pointer;
	user-select: none;
}

.select {
	background: rgb(255, 104, 104);
}

#btn {
	text-align: center;
}
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
					<th class="id">编号</th>
					<th class="name">项目</th>
					<th class="optioon">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${requestScope.pros}" var="row">
					<tr class="" data-id="${row.id}">
						<td class="check"><input class="checkbox" type="checkbox"
							name="check" /></td>
						<td class="id">${row.id}</td>
						<td class="proname">${row.name}</td>
						<td>
							<div class="layui-table-cell ">
								<button class="layui-btn layui-btn-danger layui-btn-xs"
									onclick="del(${row.id});">删除</button>
								<button class="layui-btn layui-btn-xs"
									onclick="openedit(${row.id})">修改</button>
								<button class="layui-btn layui-btn-xs"
									onclick="manage(${row.id})">管理</button>
								<button class="layui-btn layui-btn-xs"
									onclick="manage2(${row.id})">管理2</button>
								<button class="layui-btn layui-btn-xs"
									onclick="manage3(${row.id})">管理3</button>
								<button class="layui-btn layui-btn-xs"
									onclick="manage4(${row.id})">管理4</button>
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
					end="${requestScope.pageInfo.endPage }" step="1" var="temp">
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
	<!-- 模态框（Modal） -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="myModalLabel"></h4>
					</div>
					<div class="modal-body" id="model-body">
						
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="button" class="btn btn-primary">提交更改</button>
					</div>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
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
	
	//批量修改后保存
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
		location.href = "project?type=modify&data=" + str;
	})

	$("#add-btn").click(function() {
		location.href = "project?type=addShow";
	})

	
	//批量删除
	$("#delete-btn").click(function() {
		var str = "";
		$(".click").each(function(index, element) {
			str += $(this).data("id") + ",";
		})
		if(str.length > 0){
			location.href = "project?type=deleteBatch&data" + str;
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
		location.href = "project?page=" + page;
	}
	
	function del(id) {
		if(confirm("确认删除？")){
			location.href="project?type=delete&id="+id;
		}
	}
	function openedit(id) {
		location.href="project?type=modifyShow&id="+id;
	}
	
	function manage(id) {
		location.href="project?type=manageShow&id=" + id;
	}
	function manage2(id) {
		location.href="project?type=manageShow2&id=" + id;
	}
	function manage3(id) {
		location.href="project?type=manageShow3&id=" + id;
	}

	function manage4(id) {
		$("#myModal").modal("show")
		$("#model-body").load("project?type=manageShow4&id=" + id);
	}

	
// 	function manage4(id) {
// 		$.ajax({
// 			url : "project",
// 			data : {type : "manageShow4", id : id},
// 			dataType : "json",
// 			success : function(data) {
// 				var html = '';
// 				var inList = data.inList;
// 				var outList = data.outList;
				
// 				$("h3").empty();
// 				$("#indep").empty();	
// 				$("#outdep").empty();	
				
// 				var name = '';
// 				name += data.pro.name;
				
// 				$("h3").append($(name));	
				
// 				var inHtml = '';
// 				$(inList).each(function(index, item) {
// 					inHtml += '<div class="dep" data-did="' + item.id + '" >' + item.name + '</div>'; 
// 				})
// 				$("#indep").append($(inHtml));	
// 				var outHtml = '';	
// 				$(outList).each(function(index, item) {
// 					outHtml += '<div class="dep" data-did="' + item.id + '" >' + item.name + '</div>'; 
// 				})
// 				$("#outdep").append($(outHtml));	
				
				
// 			}
			
// 		})

// 	}
	$("#add-btn").click(function() {
		var pid = $("h3").data("pid");
		if ($("#outdep").find(".select").length > 0) {
			var did = "";
			$("#outdep").find(".select").each(function(index, item) {
				did += $(item).data("did") + ",";
			})
			add(pid, did)
		} else {
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
			remove(pid, did)
		} else {
			alert("请选中数据")
		}
	})
//  		//pro的容器坐标
// 		var proLeft=$("#outdep").offset().left;
// 		var proTop=$("#outdep").offset().top;
// 		//pro的容器高度和宽度
// 		//parseFloat去掉单位转化为纯数字
// 		var proWidth=parseFloat($("#outdep").css("width"));
// 		var proHeight=parseFloat($("#outdep").css("height"));
// 		//定义初始坐标的变量
// 		var startLeft;
// 		var startTop;
// 		$(".dep").draggable({
				
// 		      start: function() {
// 		    	  //定义初始坐标,else需要用到
// 		    	  startLeft=$(this).offset().left;
// 		    	  startTop=$(this).offset().top;
// 		        },
		        
// 		        stop: function() {
// 		        	//停止时的坐标点
// 		        	var stopLeft=$(this).offset().left;
// 		        	var stopTop=$(this).offset().top;
// 		        	//判断停止的坐标是否在pro的容器内
// 		        	if(stopLeft>=proLeft && stopLeft<=proLeft+proWidth && stopTop>=proTop && stopTop<=proTop+proHeight){
// 		        		//拿到proId
// // 		        		var proId =$(this).data("id");
// // 		        		var pro=$(this);
		        		
// 		        		alert("success")
// // 		        		$.ajax({
// // 		        			url:"project",
// // 							type:"post",
// // 							data:{type:"manageAdd2", depId:${dep.id}, proId:proId}, 
// // 							dataType:"text",
// // 							success:function(data){
								
// // 								if(data="true"){
// // 									//设成流布局才能自动排列到最后
// // 									pro.css("position","static");
									
// // 									//将拖动的pro拼到最后一个pro的后面
// // 									//ajax里面的this值得是整个ajax这里不能用
// // 									$("#pro").append(pro);
									
// // 									//拼好之后再变成相对布局才可以继续拖动
// // 									pro.css("position","relative");
									
// // 									//默认0,0则他就会在他为流布局时该在的位置 
// // 									pro.css("left","0");
// // 									pro.css("top","0");
// // 								}				
// // 							}
// // 		        		})
// 		        	}else{
// 		        		//返回原位置
// 		        		$(this).offset({left:startLeft, top:startTop});
// 		        	}
		        	
		        	
// 		        }
// 		      });
		
	
	
	
//  	})
// 	function remove(pid, did) {
// 		$.ajax({
// 			url : "project",
// 			type : "post",
// 			data : {
// 				type : "manageRemove2",
// 				pid : pid,
// 				did : did
// 			},
// 			dataType : "json",
// 			success : function(data) {
// 				if (data == true) {
// 					var dep = $(".move");
// 					dep.removeClass("select")
// 					dep.removeClass("move")
// 					dep.css("position","static")
// 					$("#outdep").append(dep);
// 				}
// 			}
// 		})

	
	
</script>

</html>