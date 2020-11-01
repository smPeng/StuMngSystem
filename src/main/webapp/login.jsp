<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="common.jsp"%>
<script>
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
	            	account: {
	                    message: '账号验证失败',
	                    /* 验证类型 */
	                    validators: {
	                    	/* 非空验证 */
	                        notEmpty: {
	                            message: '账号不能为空'
	                        }
	                    }
	                },
	                pwd: {
	                    message: '密码验证失败',
	                    validators: {
	                        notEmpty: {
	                            message: '密码不能为空'
	                        }
	                    }
	                }
	            }
	     });
		showLoginDialog();
		//给登录按钮注册单击事件
		$('#btnLogin').click(function(){
			//执行表单验证
			$('form').bootstrapValidator('validate');
			//获取本次是否验证通过
			var flg=$('form').data('bootstrapValidator').isValid();
			if(flg){
				login();
			}
		});
	});
	//打开登录对话框
	function showLoginDialog(){
		$("#dialog-login").modal('show');
	}
	//登录
	function login(){
		//获取表单内容
		//获取账号
		var account=$('#form-login input[name=account]').val();
		//获取密码
		var pwd=$('#form-login input[name=pwd]').val();
		$.ajax({
			url:'userServlet',
			data:{
				flag:'login',
				account:account,
				pwd:pwd
			},
			type:'post',
			success:function(dt){
				if(dt){
					var obj=eval("("+dt+")");
					//判断
					//ok
					if(obj.result==0){
						//跳转到main界面
						location.href="main.jsp";
						//关闭弹出框
						$('#dialog-login').modal('hide');
						//账号错误
					}else if(obj.result==1){
						alert("账号错误");
						//密码错误
					}else if(obj.result==2){
						alert("密码错误");
						//验证没通过
					}else if(obj.result==3){
						alert("验证没通过");
					}
				}
			}
		});
	}
</script>
</head>
<body>
	<div class="modal fade" tabindex="-1" role="dialog" id="dialog-login">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">登录</h4>
				</div>
				<div class="modal-body">
					<form id="form-login">
						<div class="form-group">
							<label for="account">账号</label> 
							<input type="text" class="form-control" name="account" placeholder="账号">
						</div>
						<div class="form-group">
							<label for="pwd">密码</label> <input type="password"
								class="form-control" name="pwd" placeholder="密码">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="btnLogin">登录</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</body>
</html>