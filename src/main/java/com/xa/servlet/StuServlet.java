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
	//����
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
		//��ȡ��־λ
		String flag=req.getParameter("flag");
		//����Gson����
		Gson gsn=new Gson();
		//�洢ת��1���json�ַ���
		String jsn="";
		//����һ���ַ���
		PrintWriter pw=null;
		//�ж�
		//��ʼ��
		if ("init".equals(flag)) {
			//����map���洢��ѯ��Ա���б�
			Map<String,Object> map=new HashMap<String,Object>();
			//��ȡƫ����
			Integer offset = (Integer.parseInt(req.getParameter("offset")));			//����
			map.put("offset", offset);
			//��ȡÿҳ����
			Integer pageSize = Integer.parseInt(req.getParameter("limit"));
			//����
			map.put("pageSize", pageSize);
			//��ȡԱ������
			String eName=req.getParameter("eName");
			if(eName!=null&&eName.length()>0){
				map.put("eName", eName);
			}
			String did=req.getParameter("did");
			if(did!=null&&did.length()>0){
				map.put("did", did);
			}
			//��ȡȫ�������б�
			List<Stu> empList=new ArrayList<Stu>();
			empList=stuService.findAllStuInfoWithPage(map);
			int count=stuService.getStuCount(map);
			Map<String,Object> result=new HashMap<String,Object>();
			result.put("rows", empList);
			result.put("total", count);
			//�������б�->json�б�
			jsn=gsn.toJson(result);
			//��ȡ��Ӧ�������
			pw=resp.getWriter();
			//��ת�����json�ַ�����ǰ��
			pw.println(jsn);
		}else if("add".equals(flag)){
			//��������
			Stu emp=new Stu();
			//��ȡ����
			String eName = req.getParameter("eName");
			emp.seteName(eName);
			//��ȡ���ű��
			Integer did=Integer.parseInt(req.getParameter("did"));
			emp.setDid(did);
			if (stuService.saveStuInfo(emp)) {
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
			String[] eIds = req.getParameterValues("eIdArr[]");
			//����ɾ��
			if (stuService.batchStuInfo(eIds)) {
				jsn="{\"result\":\"1\"}";
			} else {
				jsn="{\"result\":\"0\"}";
			}
			//��ȡ�����
			pw=resp.getWriter();
			//�����д��ǰ��
			pw.println(jsn);
		}else if("edit".equals(flag)){
			//����һ��Ա��
			Stu emp=new Stu();
			//��ȡҪ�޸�Ա����Ϣ
			//��ȡԱ�����
			Integer eId=Integer.parseInt(req.getParameter("eId"));
			emp.seteId(eId);
			//��ȡ����
			String eName = req.getParameter("eName");
			emp.seteName(eName);
			//��ȡ���ű��
			Integer did=Integer.parseInt(req.getParameter("did"));
			emp.setDid(did);
			if (stuService.modifyStuInfo(emp)) {
				jsn="{\"result\":\"1\"}";
			}else {
				jsn="{\"result\":\"0\"}";
			}
			//��ȡ�����
			pw=resp.getWriter();
			//�����д��ǰ��
			pw.println(jsn);
		} 
	}

}
