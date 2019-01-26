package com.bjpowernode.crm.domain;

public class User {
	private String id;
	private String loginAct;
	private String name;
	private String loginPwd;
	private String email;
	private String expireTime;
	private String lockState;
	private String deptCode;
	private String allowIp;
	private String creatTime;
	private String creatBy;
	private String editTime;
	private String editBy;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoginAct() {
		return loginAct;
	}
	public void setLoginAct(String loginAct) {
		this.loginAct = loginAct;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoginPwd() {
		return loginPwd;
	}
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	public String getLockState() {
		return lockState;
	}
	public void setLockState(String lockState) {
		this.lockState = lockState;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getAllowIp() {
		return allowIp;
	}
	public void setAllowIp(String allowIp) {
		this.allowIp = allowIp;
	}
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	public String getCreatBy() {
		return creatBy;
	}
	public void setCreatBy(String creatBy) {
		this.creatBy = creatBy;
	}
	public String getEditTime() {
		return editTime;
	}
	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
	public String getEditBy() {
		return editBy;
	}
	public void setEditBy(String editBy) {
		this.editBy = editBy;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", loginAct=" + loginAct + ", name=" + name + ", loginPwd=" + loginPwd + ", email="
				+ email + ", expireTime=" + expireTime + ", lockState=" + lockState + ", deptCode=" + deptCode
				+ ", allowIp=" + allowIp + ", creatTime=" + creatTime + ", creatBy=" + creatBy + ", editTime="
				+ editTime + ", editBy=" + editBy + "]";
	}
	
}
