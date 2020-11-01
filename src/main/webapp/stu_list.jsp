<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="common.jsp"%>
<script type="text/javascript">
		$(function(){
			$('form').bootstrapValidator({
					message: 'This value is not valid', 
		        	/* 验证的一些图标 */
		        	feedbackIcons: {       
		        		valid: 'glyphicon glyphicon-ok',               
		        		invalid: 'glyphicon glyphicon-remove',                
		        		validating: 'glyphicon glyphicon-refresh'          
		        	},
		            fields: {
		            	eName: {
		                    message: '姓名验证失败',
		                    /* 验证类型 */
		                    validators: {
		                    	/* 非空验证 */
		                        notEmpty: {
		                            message: '姓名不能为空'
		                        }
		                    }
		                },
		                did: {
		                    message: '所属学院验证失败',
		                    validators: {
		                        notEmpty: {
		                            message: '必选'
		                        }
		                    }
		                }
		            }
		     });
			 //发送请求获取当前页的员工列表
			 $("#table").bootstrapTable({
		         url:'stuServlet?flag=init',
				 toolbar:'#toolbar',
				 method:'post',
		         contentType:'application/x-www-form-urlencoded',//数据编码类型
		         sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
				 pagination: true,                   //是否显示分页（*）
		         pageSize: 5,                       //每页的记录行数（*）
		         pageList: [5, 10, 15, 20],
		         sortName:'eId',
		         columns:[
	                 {
	                	checkbox:true 
	                 },
	                 {
	                	field:'eId',
	                	title:'编号'
	                	
	                 },{
	                	 field:'eName',
		                 title:'姓名'
	                 },{
	                	 field:'dname',
		                 title:'所属学院'
	                 }]
				 });
			 $("#table").bootstrapTable('hideColumn','eId');
			 //给插入按钮添加事件
			 $("#add").click(function(){
				//清空
				$("#form-emp-add input[name=eName]").val('');
				$("#form-emp-add select option[value=0]").prop("selected","selected");
				
				//弹出插入对话框
				$("#dialog-emp-add").modal('show');
			 });
			 //清空下拉框选项
			 $("#form-emp-add select").empty();
			 //获取隶属部门下拉列表
			 initSubjectionDpt();
			//给保存按钮注册单击事件
			$("#dialog-emp-add #btnSave").click(function(){
				//执行表单验证
				$('form').bootstrapValidator('validate');
				//获取本次是否验证通过
				var flag=$('form').data('bootstrapValidator').isValid();
				if(flag){
					var eName=$.trim($("#form-emp-add input[name=eName]").val());
					var did=$("#form-emp-add select[name=did] option:selected").val();
					//发送请求
					$.ajax({
						url:'stuServlet',
						type:'post',
						data:{
							flag:'add',
							eName:eName,
							did:did
						},
						success:function(dt){
							if(dt){
								var obj=eval("("+dt+")");
								//判断
								if(obj.result==1){
									alert("添加成功！");
									//刷新
									$("#table").bootstrapTable('refresh');
									//关闭对话框
									$("#dialog-emp-add").modal('hide');
								}else if(obj.result==0){
									alert("添加失败！");
								}
								$("#dialog-emp-add").modal('hide');
							}
						}
					});
				}
			});
			//给重置按钮注册单击事件
			$("#btnUndo").click(function(){
				//重置表单
				$('form').data('bootstrapValidator').resetForm(true);
			});
			//给删除按钮注册单击事件
			$("#del").click(function(){
				//获取选中的行
				var emps=$("#table").bootstrapTable('getSelections');
				//判断没有选中的行
				if (emps.length==0) {
					//弹出消息框
					alert("请选中要删除的学生");
					return;
				}else{
					//输出emps
					//console.info(emps);
					//存储关于部门编号的数组
					var eIdArr=[];
					//遍历dpts
					for (var int = 0; int < emps.length; int++) {
						//将当前部门编号压入didArr
						eIdArr.push(emps[int].eId);
					}
					//输出要删除的编号列表
					//console.info(emps);
					//return;
					//弹出确认框,当用户按下确认时发出删除请求
					if(confirm("您确认要删除这些学生吗？")){
						$.post("stuServlet",{'flag':'batchDel','eIdArr[]':eIdArr},function(dt){
							//判断服务器端返回不为空的情况下
							if(dt){
								var obj=eval("("+dt+")");
								//判断
								if (obj.result==1){
									//弹出消息框
									alert("删除成功！");
									//刷新
									$("#table").bootstrapTable('refresh');
									
								} else if(obj.result==0){
									//弹出对话框
									alert("删除失败");
								}
							}
						});
					}
					
				}
			});
			//给修改按钮注册事件
			$("#edit").click(function(){
				//获取表中选中的行
				var emps=$("#table").bootstrapTable("getSelections");
				if (emps==0) {
					alert("请选中要修改的学生信息");
					return;
				}else if(emps.length>1){
					alert("只能选中一个学生修改信息");
					//取消多选操作
					$("#table").bootstrapTable('uncheckAll');
				}else{
					//从选中行逐项获取
					var eId=emps[0].eId;
					var eName=emps[0].eName;
					var did=emps[0].did;
					//搁置到模态框
					$("#form-emp-edit input[name=eId]").val(eId);
					$("#form-emp-edit input[name=eName]").val(eName);
					$("#form-emp-edit select option[value='"+did+"']").attr("selected","selected");
					//打开模态框
					$("#dialog-emp-edit").modal('show');
				}
			});
			//给保存按钮注册事件
			$("#dialog-emp-edit #btnSave").click(function(){
				//执行表单验证
				$('form').bootstrapValidator('validate');
				//获取本次是否验证通过
				var flag=$('form').data('bootstrapValidator').isValid();
				//获取员工信息
				var eId=$("#form-emp-edit input[name=eId]").val();
				var eName=$.trim($("#form-emp-edit input[name=eName]").val());
				var did=$("#form-emp-edit select option:selected").val();
				$.ajax({
					url:'stuServlet',
					data:{
						flag:'edit',
						eId:eId,
						eName:eName,
						did:did
					},
					success:function(dt){
						if(dt){
							var obj=eval("("+dt+")");
							//判断
							if(obj.result==1){
								alert("修改成功！");
								//刷新
								$("#table").bootstrapTable('refresh');
								//关闭对话框
								$("#dialog-emp-edit").modal('hide');
							}else if(obj.result==0){
								alert("修改失败！");
							}
							$("#dialog-emp-edit").modal('hide');
						}
					}
				});
			});
			//给查询添加注册事件
			$("#srch").click(function(){
				//获取用户所录入的对话框
				var eName=$.trim($("input[name=eNameSrch]").val());
				var did=$("#toolbar select option:selected").val();
				$("#table").bootstrapTable('refresh',{query:{eName:eName,did:did}});
				
			});
		});
		//获取隶属部门下拉框
		function initSubjectionDpt(){
			//发送获取部门列表的请求
			$.ajax({
				url:'academyServlet',
				type:'post',
				data:{
					flag:'sel'
				},
				success:function(dt){
					if(dt){
						var dptArr=eval("("+dt+")");
						var subject=$("select[name=did]");
						subject.append('<option value="0">'+"请选择"+'</option>');
						//遍历dptArr
						for (var int = 0; int < dptArr.length; int++) {
							var str="<option value='"+dptArr[int].did+"'>"+dptArr[int].dname+"</option>";
							subject.append(str);
						}
					}
				}
			});
		}
		
</script>
</head>
<body>
	<h2>学生管理</h2>
	<!-- 工具栏 -->
	<div id="toolbar" class="form-inline">
		<shiro:hasPermission name="add">
			<button type="button" class="btn btn-default" id="add">
				<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 插入
			</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="del">
			<button type="button" class="btn btn-default" id="del">
				<span class="glyphicon glyphicon-minus" aria-hidden="true"></span>
				删除
			</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="edit">
			<button type="button" class="btn btn-default" id="edit">
				<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
				修改
			</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="srch">
			<div class="form-group">
				<label for="eNameSrch">姓名</label> <input type="text"
					class="form-control" name="eNameSrch" placeholder="员工姓名">
				<div class="form-group">
					<label for="did">所属学院</label> <select class="form-control"
						name="did">
						<option value="">------请选择----</option>
					</select>
				</div>
				<button class="btn btn-default" id="srch">
					<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
					查询
				</button>
			</div>
		</shiro:hasPermission>
	</div>
	<table id="table"></table>
	<!-- 插入员工信息 -->
	<div class="modal fade" tabindex="-1" role="dialog" id="dialog-emp-add">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">学生插入</h4>
				</div>
				<div class="modal-body">
					<form id="form-emp-add">
						<div class="form-group">
							<label for="eName">姓名</label> <input type="text"
								class="form-control" name="eName" placeholder="姓名">
						</div>
						<div class="form-group">
							<label for="did">所属学院</label> <select class="form-control"
								name="did">
								<option value="0">------请选择----</option>
							</select>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="btnUndo">重置</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="btnSave">保存</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
	<!-- 修改员工信息-->
	<div class="modal fade" tabindex="-1" role="dialog"
		id="dialog-emp-edit">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">学生修改</h4>
				</div>
				<div class="modal-body">
					<form id="form-emp-edit">
						<div class="form-group">
							<label for="eName">姓名</label> <input type="hidden" name="eId">
							<input type="text" class="form-control" name="eName"
								placeholder="姓名">
						</div>
						<div class="form-group">
							<label for="did">隶属学院</label> <select class="form-control"
								name="did">
								<option value="">------请选择----</option>
							</select>
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