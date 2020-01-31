package com.krm.ps.model.xlsinit.services;

import org.apache.struts.upload.FormFile;

import com.krm.ps.sysmanage.usermanage.vo.User;


public interface XLSInitService {
	public void XLSInit(FormFile data,FormFile config,User usr) throws Exception;
}
