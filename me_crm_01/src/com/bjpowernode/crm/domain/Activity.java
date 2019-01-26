package com.bjpowernode.crm.domain;

public class Activity {
	private String id ;
	private String owner;
	private String name;
	private String namePingYin;
	private String startTime;
	private String endTime;
	private String cost;
	private String description;
	private String creatBy;
	private String creatTime;
	private String editBy;
	private String editTime;
	public String getNamePingYin() {
		return namePingYin;
	}
	public void setNamePingYin(String namePingYin) {
		this.namePingYin = namePingYin;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatBy() {
		return creatBy;
	}
	public void setCreatBy(String creatBy) {
		this.creatBy = creatBy;
	}
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	public String getEditBy() {
		return editBy;
	}
	public void setEditBy(String editBy) {
		this.editBy = editBy;
	}
	public String getEditTime() {
		return editTime;
	}
	public void setEditTime(String editTime) {
		this.editTime = editTime;
	}
	@Override
	public String toString() {
		return "Activity [id=" + id + ", owner=" + owner + ", name=" + name + ", startTime=" + startTime + ", endTime="
				+ endTime + ", cost=" + cost + ", description=" + description + ", creatBy=" + creatBy + ", creatTime="
				+ creatTime + ", editBy=" + editBy + ", editTime=" + editTime + "]";
	}
	
}
