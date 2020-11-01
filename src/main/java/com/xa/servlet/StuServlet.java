package com.xa.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xa.entity.Stu;
import com.xa.service.StuService;

/**
 * Servlet implementation class EmpServlet
 */
@WebServlet("/stuServlet")
public class StuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//依赖
	private StuService stuService=StuService.getStuServiceInstance();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取标志位
		String flag=req.getParameter("flag");
		//创建Gson对象
		Gson gsn=new Gson();
		//存储转换1后的json字符串
		String jsn="";
		//声明一个字符流
		PrintWriter pw=null;
		//判断
		//初始化
		if ("init".equals(flag)) {
			//创建map，存储查询的员工列表
			Map<String,Object> map=new HashMap<String,Object>();
			//获取偏移量
			Integer offset = (Integer.parseInt(req.getParameter("offset")));			//搁置
			map.put("offset", offset);
			//获取每页条数
			Integer pageSize = Integer.parseInt(req.getParameter("limit"));
			//搁置
			map.put("pageSize", pageSize);
			//获取员工姓名
			String eName=req.getParameter("eName");
			if(eName!=null&&eName.length()>0){
				map.put("eName", eName);
			}
			String did=req.getParameter("did");
			if(did!=null&&did.length()>0){
				map.put("did", did);
			}
			//获取全部部门列表
			List<Stu> empList=new ArrayList<Stu>();
			empList=stuService.findAllStuInfoWithPage(map);
			int count=stuService.getStuCount(map);
			Map<String,Object> result=new HashMap<String,Object>();
			result.put("rows", empList);
			result.put("total", count);
			//将部门列表->json列表
			jsn=gsn.toJson(result);
			//获取响应的输出流
			pw=resp.getWriter();
			//将转换后的json字符串给前端
			pw.println(jsn);
		}else if("add".equals(flag)){
			//创建部门
			Stu emp=new Stu();
			//获取名称
			String eName = req.getParameter("eName");
			emp.seteName(eName);
			//获取部门编号
			Integer did=Integer.parseInt(req.getParameter("did"));
			emp.setDid(did);
			if (stuService.saveStuInfo(emp)) {
				jsn="{\"result\":\"1\"}";
			}else {
				jsn="{\"result\":\"0\"}";
			}
			//获取输出流
			pw=resp.getWriter();
			//将结果写给前端
			pw.println(jsn);
		}else if("batchDel".equals(flag)){
			//获取要删除的编号列表
			String[] eIds = req.getParameterValues("eIdArr[]");
			//批量删除
			if (stuService.batchStuInfo(eIds)) {
				jsn="{\"result\":\"1\"}";
			} else {
				jsn="{\"result\":\"0\"}";
			}
			//获取输出流
			pw=resp.getWriter();
			//将结果写给前端
			pw.println(jsn);
		}else if("edit".equals(flag)){
			//创建一个员工
			Stu emp=new Stu();
			//获取要修改员工信息
			//获取员工编号
			Integer eId=Integer.parseInt(req.getParameter("eId"));
			emp.seteId(eId);
			//获取名称
			String eName = req.getParameter("eName");
			emp.seteName(eName);
			//获取部门编号
			Integer did=Integer.parseInt(req.getParameter("did"));
			emp.setDid(did);
			if (stuService.modifyStuInfo(emp)) {
				jsn="{\"result\":\"1\"}";
			}else {
				jsn="{\"result\":\"0\"}";
			}
			//获取输出流
			pw=resp.getWriter();
			//将结果写给前端
			pw.println(jsn);
		} 
	}

}
