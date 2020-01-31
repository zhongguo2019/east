package com.krm.ps.model.repfile.process;

import java.util.List;

import org.hibernate.transaction.JOnASTransactionManagerLookup;

import net.sf.json.JSONArray;

import com.krm.ps.common.dto.DTO;
import com.krm.ps.common.dto.VO;
import com.krm.ps.common.process.KProcessImpl;
import com.krm.ps.tarsk.service.TarskService;
import com.krm.ps.tarsk.vo.SubTarskInfo;

public class ListSubTarskProcess extends KProcessImpl {

	@Override
	public void doCheck(VO in, DTO out) {
assertNotNull("主任务id不能为空", in.getString("tId"));
	}

	@Override
	public void execute(VO in, DTO out) {
String tid=in.getString("tId");
TarskService ts=(TarskService)getService().getBean("tarskService");
SubTarskInfo stk=new SubTarskInfo();
stk.settId(Long.valueOf(tid));
List<SubTarskInfo> subList= ts.getSubTarsks(stk);
out.put("subTarsks", subList);
	}

}
