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
	//����
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
		//��ȡ��־λ
		String flag=req.getParameter("flag");
		//����Gson����
		Gson gsn=new Gson();
		//�洢ת�����json�ַ���
		String jsn="";
		//����һ���ַ���
		PrintWriter pw=null;
		//�ж�
		//��ʼ��
		if ("init".equals(flag)) {
			//ctrl+shift+o:����
			//����map���洢���εĲ�ѯ����
			Map<String,Object> map=new HashMap<String,Object>();
			//��ȡƫ����
			Integer offset = (Integer.parseInt(req.getParameter("offset")));
			map.put("offset", offset);
			//��ȡÿҳ����
			Integer pageSize = (Integer.parseInt(req.getParameter("limit")));
			map.put("pageSize", pageSize);
			String dname = req.getParameter("dname");
			if(dname!=null&&dname.length()>0){
				map.put("dname", dname);
			}
			//��ȡ����
			String ddesc = req.getParameter("ddesc");
			if(dname!=null&&dname.length()>0){
				map.put("ddesc", ddesc);
			}
			//��ȡȫ�������б�
			List<Academy> dptList=amyService.findAmpInfoWithPage(map);
			int count=amyService.getAmyCount(map);
			Map<String,Object> result=new HashMap<String,Object>();
			result.put("rows", dptList);
			result.put("total", count);
			//�������б�->json�б�
			jsn=gsn.toJson(result);
			//��ȡ��Ӧ�������
			pw=resp.getWriter();
			//��ת�����json�ַ�����ǰ��
			pw.println(jsn);

		}else if("add".equals(flag)){
			//��������
			Academy dpt=new Academy();
			//��ȡ����
			String dname = req.getParameter("dname");
			//��ȡ����
			String ddesc = req.getParameter("ddesc");
			//�ж�
			if (dname!=null&&dname.length()>0) {
				//��������
				dpt.setDname(dname);
			}
			if (dname!=null&&dname.length()>0) {
				//��������
				dpt.setDdesc(ddesc);
			}
			if (amyService.saveAmyInfo(dpt)) {
				jsn="{\"result\":\"1\"}";
			}else {
				jsn="{\"result\":\"0\"}";
			}
			//��ȡ�����
			pw=resp.getWriter();
			//�����д��ǰ��
			pw.println(jsn);
		}else if("batchDel".equals(flag)){
			//��ȡҪɾ���ı���б�
			String[] dids = req.getParameterValues("didArr[]");
			int count=amyService.delAmyInfo(dids);
			//����ɾ��
			if (dids.length==count) {
				jsn="{\"result\":\"1\"}";
			} else {
				jsn="{\"result\":\"0\"}";
			}
			//��ȡ�����
			pw=resp.getWriter();
			//�����д��ǰ��
			pw.println(jsn);
		}else if("modify".equals(flag)){
			//��������
			Academy dpt=new Academy();
			//��ȡ����
			String dname = req.getParameter("dname");
			//��������
			dpt.setDname(dname);
			//��ȡ����
			String ddesc = req.getParameter("ddesc");
			//��������
			dpt.setDdesc(ddesc);
			//��ȡ���
			String did = req.getParameter("did");
			dpt.setDid(Integer.parseInt(did));
			if (amyService.modifyAmyInfo(dpt)) {
				jsn="{\"result\":\"1\"}";
			}else {
				jsn="{\"result\":\"0\"}";
			}
			//��ȡ�����
			pw=resp.getWriter();
			//�����д��ǰ��
			pw.println(jsn);
		}else if("sel".equals(flag)){
			//��ȡȫ�������б�
			List<Academy> dptList=amyService.findAllAmyInfo();
			//�������б�->json�б�
			jsn=gsn.toJson(dptList);
			//��ȡ��Ӧ�������
			pw=resp.getWriter();
			//��ת�����json�ַ�����ǰ��
			pw.println(jsn);
		} 
	}

}
