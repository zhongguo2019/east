package com.krm.ps.tarsk.service.impl;

import java.util.List;

import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.tarsk.dao.TarskDao;
import com.krm.ps.tarsk.service.TarskService;
import com.krm.ps.tarsk.vo.SubTarskInfo;
import com.krm.ps.tarsk.vo.Tarsk;

public class TarskServiceImpl  implements TarskService{
	private TarskDao tskDao;
	public TarskDao getTskDao() {
		return tskDao;
	}

	public void setTskDao(TarskDao tskDao) {
		this.tskDao = tskDao;
	}

	@Override
	public Tarsk tarskInit(Tarsk t) {
		return  tskDao.tarskInit(t);		
	}

	@Override
	public void tarskStart(Tarsk t) {
		tskDao.tarskStart(t);		
	}

	@Override
	public void tarskEnd(Tarsk t) {
		tskDao.tarskEnd(t);		
	}

	@Override
	public void updateTarsk(Tarsk t) {
		tskDao.updateTarsk(t);		
	}

	@Override
	public List<Tarsk> getTarsks(Tarsk t) {
		return tskDao.getTarsks(t);
	}

	@Override
	public SubTarskInfo subTarskInit(SubTarskInfo stk) {
	return	tskDao.subTarskInit(stk);		
	}

	@Override
	public void updateSubTarsk(SubTarskInfo stk) {
		tskDao.updateSubTarsk(stk);		
	}

	@Override
	public List<SubTarskInfo> getSubTarsks(SubTarskInfo stk) {

		return tskDao.getSubTarsks(stk);
	}

	@Override
	public void deleteSubTarsk(SubTarskInfo stk) {
		tskDao.deleteSubTarsk(stk);
	}

	@Override
	public void deleteTarsk(Tarsk t) {
		
	}

	@Override
	public List<SubTarskInfo> alreadySubTarsk(String date, String organId, List repids) {
		String reps="";
		boolean repeat=false;
		for(int i=0;i<repids.size();i++){
			if(i==0){
				reps+=((Report)repids.get(i)).getPkid();
			}else{
				reps+=","+((Report)repids.get(i)).getPkid();
			}
		}
		List<SubTarskInfo> list=tskDao.getSubTarskInfos(date, organId, reps);
		
		return list;
	}

	
	 public List getRepSubTarsks(String datadate, String repid) {
	      return this.tskDao.getSubTarsks(datadate, repid);
	  }

}
