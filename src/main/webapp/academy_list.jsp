<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="common.jsp"%>
<script type="text/javascript">
	$(function() {
		//表单验证
		$('form').bootstrapValidator({
			message : 'This value is not valid',
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				dname : {
					message : '学院名称验证失败',
					validators : {
						notEmpty : {
							message : '学院名称不能为空'
						}
					}
				},
				ddesc : {
					message : '学院描述验证失败',
					validators : {
						notEmpty : {
							message : '学院描述不能为空'
						}
					}
				}
			}
		});
		//发送请求获取当前页的部门列表
		$("#table").bootstrapTable({
			url : 'academyServlet?flag=init',
			toolbar : '#toolbar',
			method : 'post',
			contentType : 'application/x-www-form-urlencoded',//数据编码类型
			sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
			pagination : true, //是否显示分页（*）
			pageSize : 5, //每页的记录行数（*）
			pageList : [ 5, 10, 15, 20 ],
			sortName : 'did',
			columns : [ {
				checkbox : true
			}, {
				field : 'did',
				title : '编号'
			}, {
				field : 'dname',
				title : '名称'
			}, {
				field : 'ddesc',
				title : '描述'
			} ]
		});
		//设置隐藏列
		$("#table").bootstrapTable('hideColumn', 'did');
		//给插入按钮注册单击事件
		$("#add").click(function() {
			//打开对话框
			openDptAddDialog();
		});
		//给保存按钮注册一个单击事件
		$("#dialog-dpt-add #btnSave").click(function() {
			//执行表单验证
			$('form').bootstrapValidator('validate');
			//获取本次是否验证通过
			var flag = $('form').data('bootstrapValidator').isValid();
			//输出到控制台
			//console.info(flag);
			//获取名称
			var dname = $.trim($("#form-add input[name=dname]").val());
			//获取名称
			var ddesc = $.trim($("#form-add input[name=ddesc]").val());
			if (flag) {
				//发送请求
				$.ajax({
					url : 'academyServlet',
					type : 'post',
					data : {
						flag : 'add',
						dname : dname,
						ddesc:ddesc
					},
					success : function(dt) {
						//判断
						if (dt) {
							var obj = eval("(" + dt + ")");
							//判断
							if (obj.result == 1) {
								//弹出消息框
								alert("添加成功！");
								//刷新
								$("#table").bootstrapTable('refresh');
								//关闭对话框
								$("#dialog-dpt-add").modal('hide');
							} else if (obj.result == 0) {
								//弹出对话框
								alert("添加失败");
							}
							$("#dialog-dpt-add").modal('hide');
						}
					}
				});
			}
		});
		//给重置按钮注册单击事件
		$("#btnUndo").click(function() {
			//重置表单
			$('form').data('bootstrapValidator').resetForm(true);
		});
		//给删除按钮注册单击事件
		$("#del").click(function() {
			//获取选中的行
			var dpts = $("#table").bootstrapTable('getSelections');
			//判断没有选中的行
			if (dpts.length == 0) {
				//弹出消息框
				alert("请选中要删除的学院");
				return;
			} else {
				//输出dpts
				//console.info(dpts);
				//存储关于部门编号的数组
				var didArr = [];
				//遍历dpts
				for (var int = 0; int < dpts.length; int++) {
					//将当前部门编号压入didArr
					didArr.push(dpts[int].did);
				}
				//输出要删除的编号列表
				//console.info(didArr);
				//弹出确认框,当用户按下确认时发出删除请求
				if (confirm("您确认要删除这些学院吗？")) {
					$.post("academyServlet", {
						'flag' : 'batchDel',
						'didArr[]' : didArr
					}, function(dt) {
						//判断服务器端返回不为空的情况下
						if (dt) {
							var obj = eval("(" + dt + ")");
							//判断
							if (obj.result == 1) {
								//弹出消息框
								alert("删除成功！");
								//刷新
								$("#table").bootstrapTable('refresh');
							} else if (obj.result == 0) {
								//弹出对话框
								alert("删除失败");
							}
						}
					});
				}

			}
		});
		//给修改按钮添加单击事件
		$("#modify").click(function() {
			//获取选中的行(部门)
			var dpts = $("#table").bootstrapTable('getSelections');
			//判断没有选中的行
			if (dpts.length == 0) {
				//弹出消息框
				alert("请选中要修改的学院");
				return;
			} else if (dpts.length > 1) {
				//弹出消息框
				alert("只能选中一个要修改的学院");
				//取消选中的所有行
				$("#table").bootstrapTable('uncheckAll');
				return;
			} else {
				var dname = dpts[0].dname;
				var ddesc = dpts[0].ddesc;
				var did = dpts[0].did;
				$("#form-modify input[name=dname]").val(dname);
				$("#form-modify input[name=did]").val(did);
				$("#form-modify input[name=ddesc]").val(ddesc);
				//弹出修改对话框
				$("#dialog-dpt-modify").modal('show');
			}
		});
		//修改
		$("#dialog-dpt-modify #btnSave").click(function() {
			//执行表单验证
			$('form').bootstrapValidator('validate');
			//获取本次是否验证通过
			var flag = $('form').data('bootstrapValidator').isValid();
			if (flag) {
				//获取名称
				var dname = $.trim($("#form-modify input[name=dname]").val());
				var ddesc = $.trim($("#form-modify input[name=ddesc]").val());
				//获取编号
				var did = $("#form-modify input[name=did]").val();
				//发送请求
				$.ajax({
					url : 'academyServlet',
					type : 'post',
					data : {
						flag : 'modify',
						dname : dname,
						did : did,
						ddesc : ddesc
					},
					success : function(dt) {
						//判断
						if (dt) {
							var obj = eval("(" + dt + ")");
							//判断
							if (obj.result == 1) {
								//弹出消息框
								alert("修改成功！");
								//刷新
								$("#table").bootstrapTable('refresh');
								//关闭对话框
								$("#dialog-dpt-modify").modal('hide');
							} else if (obj.result == 0) {
								//弹出对话框
								alert("修改失败");
							}
							$("#dialog-dpt-modify").modal('hide');
						}
					}
				});
			}

		});
		//给查询按钮注册单击事件
		$("#srch").click(function() {
			//获取用户所录入的对话框
			var dname = $.trim($("input[name=dnameSrch]").val());
			$("#table").bootstrapTable('refresh', {
				query : {
					dname : dname
				}
			});
		});
	});
	function openDptAddDialog() {
		//清空部门名称
		$("#form-add input[name=dname]").val('');
		//弹出插入对话框
		$("#dialog-dpt-add").modal('show');
	}
</script>
</head>
<body>
	<h2>学院管理</h2>
	<!--工具栏  -->
	<div id="toolbar" class="form-inline">
		<shiro:hasPermission name="add">
			<button class="btn btn-default" id="add">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 插入
			</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="del">
			<button class="btn btn-default" id="del">
				<span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
				删除
			</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="edit">
			<button class="btn btn-default" id="modify">
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
				修改
			</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="srch">
			<div class="form-group">
				<label class="sr-only" for="dnameSrch">名称</label> <input type="text"
					class="form-control" name="dnameSrch" placeholder="名称">
				<button class="btn btn-default" id="srch">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					查询
				</button>
			</div>
		</shiro:hasPermission>

	</div>
	<!-- 展示我们当前页的列表 -->
	<table id="table" class="table table-bordered"></table>
	<!--插入对话框  -->
	<div class="modal fade" tabindex="-1" role="dialog" id="dialog-dpt-add">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">学院插入</h4>
				</div>
				<div class="modal-body">
					<form id="form-add">
						<div class="form-group">
							<label for="dname">名称</label> <input type="text"
								class="form-control" name="dname" placeholder="名称">
						</div>
						<div class="form-group">
							<label for="ddesc">描述</label> <input type="text"
								class="form-control" name="ddesc" placeholder="描述">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="btnUndo">重置</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="btnSave">保存</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<!--修改对话框  -->
	<div class="modal fade" tabindex="-1" role="dialog"
		id="dialog-dpt-modify">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">部门修改</h4>
				</div>
				<div class="modal-body">
					<form id="form-modify">
						<div class="form-group">
							<label for="dname">名称</label> <input type="hidden" name="did">
							<input type="text" class="form-control" name="dname"
								placeholder="名称">
						</div>
						<div class="form-group">
							<label for="ddesc">描述</label> <input type="hidden" name="did">
							<input type="text" class="form-control" name="ddesc"
								placeholder="描述">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="btnSave">保存</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</body>
</html>