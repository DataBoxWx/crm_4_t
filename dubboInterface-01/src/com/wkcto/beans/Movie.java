package com.wkcto.beans;

import java.io.Serializable;

public class Movie implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String time;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Movie [name=" + name + ", time=" + time + "]";
	}
	
}
