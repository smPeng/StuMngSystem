package com.xa.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(urlPatterns="/*",filterName="encoding",initParams={@WebInitParam(name="character",value="utf-8")})
public class CharacterEncodingFilter implements Filter {
	//�洢��ǰ���ַ���
	private String character;
	//����ʵ����,��ִ��
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("CharacterEncodingFilter������ʵ��...");
		//��ȡ���õ��ַ���
		this.character=filterConfig.getInitParameter("character");
	}
	//�ж��������,��Ϊ��������
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("CharacterEncodingFilter��������ʼ����...");
		//���˵Ĵ���
		//�ж��ַ�����Ϊnull���Ҳ�Ϊ�մ�
		if (this.character!=null&&this.character.length()>0) {
			//��������Ľ����ַ���
			request.setCharacterEncoding(this.character);
			//������Ӧ�ı����ַ���
			response.setCharacterEncoding(this.character);
			//���ñ��ε�MIME����
			response.setContentType("text/html;charset="+this.character);
			//ִ����һ��������
			chain.doFilter(request, response);
		}
		System.out.println("CharacterEncodingFilter��������������...");
	}
	//ʵ������ǰ,��ִ��
	public void destroy() {
		System.out.println("CharacterEncodingFilter��������...");
	}

}
