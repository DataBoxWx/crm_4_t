package com.bjpowernode.crm.domain;

public class Dept {
	private int code;
	private String name;
	private String manager;
	private String phone;
	private String description;
	public Dept() {
		super();
	}
	public Dept(int code, String name, String manager, String phone, String description) {
		super();
		this.code = code;
		this.name = name;
		this.manager = manager;
		this.phone = phone;
		this.description = description;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Dept [code=" + code + ", name=" + name + ", manager=" + manager + ", phone=" + phone + ", description="
				+ description + "]";
	}
	
}
