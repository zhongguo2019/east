package com.krm.ps.model.datafill.dao;

import java.util.List;

import com.krm.ps.model.vo.FromValidator;

public interface FromValidatorDAO {

	public List listFromValidator(Long pkid, String roportId, String targetfield);

	public List listFromValidator(String roportId);

	public boolean saveFromValidator(FromValidator fv);

	public int deleteFromValidator(Long pkid);

	public Long getFvPkid();

	public void updateFromValidator(FromValidator fv, List pkidstr);

}
