package com.xa.entity;

import java.util.List;

public class User {
	private Integer id;
	private String account;
	private String pwd;
	//��ɫ�б�
	private List<Roles> rolList;
	//Ȩ���б�
	private List<Permission> prmList;
	
	
	public List<Permission> getPrmList() {
		return prmList;
	}
	public void setPrmList(List<Permission> prmList) {
		this.prmList = prmList;
	}
	public List<Roles> getRolList() {
		return rolList;
	}
	public void setRolList(List<Roles> rolList) {
		this.rolList = rolList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
