package com.krm.ps.model.datafill.services.impl;

import java.util.List;

import com.krm.ps.model.datafill.dao.FromValidatorDAO;
import com.krm.ps.model.datafill.services.FromValidatorService;
import com.krm.ps.model.vo.FromValidator;

public class FromValidatorServiceImpl implements FromValidatorService {

	FromValidatorDAO datafillFromValidatorDAO;

	public FromValidatorDAO getFromValidatorDAO() {
		return datafillFromValidatorDAO;
	}

	public void setdatafillFromValidatorDAO(
			FromValidatorDAO datafillFromValidatorDAO) {
		this.datafillFromValidatorDAO = datafillFromValidatorDAO;
	}

	@Override
	public boolean saveFromValidator(FromValidator fromValidator) {
		return datafillFromValidatorDAO.saveFromValidator(fromValidator);
	}

	@Override
	public int deleteFromValidator(Long pkid) {
		return datafillFromValidatorDAO.deleteFromValidator(pkid);
	}

	@Override
	public List listFromValidator(Long pkid, String roportId, String targetfield) {
		return datafillFromValidatorDAO.listFromValidator(pkid, roportId,
				targetfield);
	}

	@Override
	public List listFromValidator(String roportId) {
		return datafillFromValidatorDAO.listFromValidator(roportId);
	}

	@Override
	public Long getFvPkid() {
		return datafillFromValidatorDAO.getFvPkid();
	}

	@Override
	public void updateFromValidator(FromValidator fv, List pkidstr) {
		datafillFromValidatorDAO.updateFromValidator(fv, pkidstr);
	}

}
