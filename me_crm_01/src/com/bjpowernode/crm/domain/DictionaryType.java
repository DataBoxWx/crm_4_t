package com.bjpowernode.crm.domain;

public class DictionaryType {
	private String code;
	private String name;
	private String discription;
	public DictionaryType() {
		super();
	}
	public DictionaryType(String code, String name, String discription) {
		super();
		this.code = code;
		this.name = name;
		this.discription = discription;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	@Override
	public String toString() {
		return "DictionaryType [code=" + code + ", name=" + name + ", discription=" + discription + "]";
	}
}
