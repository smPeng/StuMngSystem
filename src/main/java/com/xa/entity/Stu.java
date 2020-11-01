package com.xa.entity;

import java.io.Serializable;

public class Stu implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer eId;
	private String eName;
	private String dname;
	private Integer did;
	
	public Integer getDid() {
		return did;
	}
	public void setDid(Integer did) {
		this.did = did;
	}
	public Integer geteId() {
		return eId;
	}
	public void seteId(int eId) {
		this.eId = eId;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
}
