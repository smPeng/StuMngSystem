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
	//存储当前的字符集
	private String character;
	//创建实例后,会执行
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("CharacterEncodingFilter创建了实例...");
		//获取设置的字符集
		this.character=filterConfig.getInitParameter("character");
	}
	//有多个过滤器,称为过滤器链
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("CharacterEncodingFilter过滤器开始过滤...");
		//过滤的代码
		//判断字符集不为null并且不为空串
		if (this.character!=null&&this.character.length()>0) {
			//设置请求的解析字符集
			request.setCharacterEncoding(this.character);
			//设置响应的编码字符集
			response.setCharacterEncoding(this.character);
			//设置本次的MIME类型
			response.setContentType("text/html;charset="+this.character);
			//执行下一个过滤器
			chain.doFilter(request, response);
		}
		System.out.println("CharacterEncodingFilter过滤器结束过滤...");
	}
	//实例销毁前,会执行
	public void destroy() {
		System.out.println("CharacterEncodingFilter被销毁了...");
	}

}
