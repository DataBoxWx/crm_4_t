package com.wkcto.beans;

import java.io.Serializable;

public class Books  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private int money;
	private String press;
	@Override
	public String toString() {
		return "Books [name=" + name + ", money=" + money + ", press=" + press + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
}
