package com.bjpowernode.crm.domain;

import java.util.List;

public class PaginationVo<T> {
	private Long total;
	private List<T> dataList;
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public List<T> getDataList() {
		return dataList;
	}
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
}
