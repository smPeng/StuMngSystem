package com.xa.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xa.entity.Academy;
import com.xa.service.AcademyService;

/**
 * Servlet implementation class academyServlet
 */
@WebServlet("/academyServlet")
public class AcademyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//依赖
	private AcademyService amyService=AcademyService.getAmyServiceInstance(); 
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AcademyServlet() {
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
		//存储转换后的json字符串
		String jsn="";
		//声明一个字符流
		PrintWriter pw=null;
		//判断
		//初始化
		if ("init".equals(flag)) {
			//ctrl+shift+o:导包
			//创建map，存储本次的查询条件
			Map<String,Object> map=new HashMap<String,Object>();
			//获取偏移量
			Integer offset = (Integer.parseInt(req.getParameter("offset")));
			map.put("offset", offset);
			//获取每页条数
			Integer pageSize = (Integer.parseInt(req.getParameter("limit")));
			map.put("pageSize", pageSize);
			String dname = req.getParameter("dname");
			if(dname!=null&&dname.length()>0){
				map.put("dname", dname);
			}
			//获取描述
			String ddesc = req.getParameter("ddesc");
			if(dname!=null&&dname.length()>0){
				map.put("ddesc", ddesc);
			}
			//获取全部部门列表
			List<Academy> dptList=amyService.findAmpInfoWithPage(map);
			int count=amyService.getAmyCount(map);
			Map<String,Object> result=new HashMap<String,Object>();
			result.put("rows", dptList);
			result.put("total", count);
			//将部门列表->json列表
			jsn=gsn.toJson(result);
			//获取响应的输出流
			pw=resp.getWriter();
			//将转换后的json字符串给前端
			pw.println(jsn);

		}else if("add".equals(flag)){
			//创建部门
			Academy dpt=new Academy();
			//获取名称
			String dname = req.getParameter("dname");
			//获取描述
			String ddesc = req.getParameter("ddesc");
			//判断
			if (dname!=null&&dname.length()>0) {
				//设置名称
				dpt.setDname(dname);
			}
			if (dname!=null&&dname.length()>0) {
				//设置名称
				dpt.setDdesc(ddesc);
			}
			if (amyService.saveAmyInfo(dpt)) {
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
			String[] dids = req.getParameterValues("didArr[]");
			int count=amyService.delAmyInfo(dids);
			//批量删除
			if (dids.length==count) {
				jsn="{\"result\":\"1\"}";
			} else {
				jsn="{\"result\":\"0\"}";
			}
			//获取输出流
			pw=resp.getWriter();
			//将结果写给前端
			pw.println(jsn);
		}else if("modify".equals(flag)){
			//创建部门
			Academy dpt=new Academy();
			//获取名称
			String dname = req.getParameter("dname");
			//设置名称
			dpt.setDname(dname);
			//获取描述
			String ddesc = req.getParameter("ddesc");
			//设置描述
			dpt.setDdesc(ddesc);
			//获取编号
			String did = req.getParameter("did");
			dpt.setDid(Integer.parseInt(did));
			if (amyService.modifyAmyInfo(dpt)) {
				jsn="{\"result\":\"1\"}";
			}else {
				jsn="{\"result\":\"0\"}";
			}
			//获取输出流
			pw=resp.getWriter();
			//将结果写给前端
			pw.println(jsn);
		}else if("sel".equals(flag)){
			//获取全部部门列表
			List<Academy> dptList=amyService.findAllAmyInfo();
			//将部门列表->json列表
			jsn=gsn.toJson(dptList);
			//获取响应的输出流
			pw=resp.getWriter();
			//将转换后的json字符串给前端
			pw.println(jsn);
		} 
	}

}
