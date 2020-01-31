package com.krm.ps.model.datafill.services;

import java.util.List;

import com.krm.ps.model.vo.FromValidator;

public interface FromValidatorService {

	public List listFromValidator(String roportId);

	public List listFromValidator(Long pkid, String roportId, String targetfield);

	public boolean saveFromValidator(FromValidator fromValidator);

	public int deleteFromValidator(Long pkid);

	public Long getFvPkid();

	public void updateFromValidator(FromValidator fv, List pkidstr);

}
