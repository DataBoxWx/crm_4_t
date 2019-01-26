package com.bjpowernode.crm.workbench.service;

import java.util.List;

import com.bjpowernode.crm.workbench.domain.ClueRemark;

public interface ClueRemarkService {

	int saveRemark(ClueRemark clueRemark);

	List<ClueRemark> displayRemark();

	int updateRemarkById(ClueRemark clueRemark);

	int deleteRemarkById(String id);

}
