package com.krm.ps.model.datafill.services.impl;

import java.util.Date;

import com.krm.common.logger.KRMLogger;
import com.krm.common.logger.KRMLoggerUtil;
import com.krm.ps.model.datafill.dao.ReportDefineDAO;
import com.krm.ps.model.datafill.services.ReportMoveVerifyService;

public class ReportMoveVerifyServiceImpl implements ReportMoveVerifyService {
	KRMLogger logger = KRMLoggerUtil
			.getLogger(ReportMoveVerifyServiceImpl.class);
	private ReportDefineDAO dao;

	public ReportDefineDAO getDao() {
		return dao;
	}

	public void setDao(ReportDefineDAO dao) {
		this.dao = dao;
	}

	@Override
	// status的状态 0待补录 1待校验，2待提交,3待修订，4已提交 5 保存未提交 6为提交失败的数据
	public boolean reportMove(String reportid) {
		try {
			logger.info("迁移数据的方法被调用" + new Date());
			String status1 = "2";
			dao.reportSeleMove(status1,reportid); // 将待提交的数据update到f表
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	// status的状态 0待补录 1待校验，2待提交,3待修订，4已提交 5 保存未提交 6为提交失败的数据
		public boolean reportMove() {
			try {
				logger.info("迁移数据的方法被调用" + new Date());
				String status1 = "2";
				dao.reportSeleMove(status1,null); // 将待提交的数据update到f表
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
			return true;
		}

	@Override
	public String reportVerify() {
		// TODO Auto-generated method stub
		try {
			logger.info("校验补录数据的的方法被调用" + new Date());
			String status1 = "1"; // 待校验
			dao.reportSeleMove(status1,null); // 将待补录的数据update到f表
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return null;
	}

}
