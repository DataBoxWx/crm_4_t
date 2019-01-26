package com.bjpowernode.crm.domain;

public class ActivityRemark {
	private String id;
	private String noteContent;
	private String creatBy;
	private String creatTime;
	private String editBy;
	private String editTime;
	private String editFlag;
	private String activityId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNoteContent() {
		return noteContent;
	}
	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
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
	public String getEditFlag() {
		return editFlag;
	}
	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	@Override
	public String toString() {
		return "ActivityRemark [id=" + id + ", noteContent=" + noteContent + ", creatBy=" + creatBy + ", creatTime="
				+ creatTime + ", editBy=" + editBy + ", editTime=" + editTime + ", editFlag=" + editFlag
				+ ", activityId=" + activityId + "]";
	}
	

}
