package com.krm.ps.model.reportrule.services.impl;

import java.util.List;

import com.krm.ps.model.reportrule.dao.FromValidatorDAO;
import com.krm.ps.model.reportrule.services.FromValidatorService;
import com.krm.ps.model.vo.FromValidator;

public class FromValidatorServiceImpl implements FromValidatorService {

	FromValidatorDAO reportruleFromValidatorDAO;

	public FromValidatorDAO getReportruleFromValidatorDAO() {
		return reportruleFromValidatorDAO;
	}

	public void setReportruleFromValidatorDAO(
			FromValidatorDAO reportruleFromValidatorDAO) {
		this.reportruleFromValidatorDAO = reportruleFromValidatorDAO;
	}

	@Override
	public boolean saveFromValidator(FromValidator fromValidator) {
		return reportruleFromValidatorDAO.saveFromValidator(fromValidator);
	}

	@Override
	public int deleteFromValidator(Long pkid) {
		return reportruleFromValidatorDAO.deleteFromValidator(pkid);
	}

	@Override
	public List listFromValidator(Long pkid, String roportId, String targetfield) {
		return reportruleFromValidatorDAO.listFromValidator(pkid, roportId,
				targetfield);
	}

	@Override
	public List listFromValidator(String roportId) {
		return reportruleFromValidatorDAO.listFromValidator(roportId);
	}

	@Override
	public Long getFvPkid() {
		return reportruleFromValidatorDAO.getFvPkid();
	}

	@Override
	public void updateFromValidator(FromValidator fv, List pkidstr) {
		reportruleFromValidatorDAO.updateFromValidator(fv, pkidstr);
	}

}
