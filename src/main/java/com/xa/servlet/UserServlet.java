package com.xa.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//��ȡ��־λ
		String flag=req.getParameter("flag");
		//�洢json�ַ���
		String jsn="";
		//�����ַ���
		PrintWriter pw=null;
		//��¼
		//��ȡ��ǰ�û������⣨һ���û�һ�����⣩
		Subject subject= SecurityUtils.getSubject();
		if("login".equals(flag)){
			//�ж�
			//��ǰ�û��Ƿ���֤��
			if(!subject.isAuthenticated()){
				//���˺ţ������װ��������,ֱ�������ݿ��е����ݽ���ƥ��
				UsernamePasswordToken token=new UsernamePasswordToken(req.getParameter("account"),req.getParameter("pwd"));
				try {
					//��¼
					subject.login(token);
					jsn="{\"result\":\"0\"}";
				} catch (UnknownAccountException uae) {
					jsn="{\"result\":\"1\"}";
				}catch(IncorrectCredentialsException ice){
					jsn="{\"result\":\"2\"}";
				}catch(LockedAccountException lae){
					jsn="{\"result\":\"3\"}";
				}
				pw=resp.getWriter();
				pw.println(jsn);
			}
		}else if("exit".equals(flag)){
			subject.logout();
			resp.sendRedirect(req.getContextPath()+"/login.jsp");
		}
	}
}
