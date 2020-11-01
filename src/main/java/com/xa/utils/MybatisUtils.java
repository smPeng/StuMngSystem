package com.xa.utils;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtils {
	//定义一个变量
	private static SqlSessionFactory factory;
	static{
		String resource = "Mybatis-config.xml";
		try {
			Reader reader = Resources.getResourceAsReader(resource);
			factory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static SqlSession getSqlSession(){
		//声明返回变量
		SqlSession sqlSession=null;
		if (factory!=null) {
			sqlSession=factory.openSession();
		}
		return sqlSession;
	}
	public static void close(SqlSession sqlSession){
		if(sqlSession!=null){
			sqlSession.close();
		}
	}
}
