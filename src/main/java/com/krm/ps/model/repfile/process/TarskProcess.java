package com.krm.ps.model.repfile.process;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;

import com.krm.ps.common.dto.DTO;
import com.krm.ps.common.dto.VO;
import com.krm.ps.common.process.KProcessImpl;
import com.krm.ps.common.task.Task;
import com.krm.ps.common.task.TaskServiceImpl;
import com.krm.ps.tarsk.service.TarskService;
import com.krm.ps.tarsk.vo.SubTarskInfo;
import com.krm.ps.tarsk.vo.Tarsk;

public class TarskProcess extends KProcessImpl {
	private final String PARAM_IN_PROCESSID = "processid";

	@Override
	public void doCheck(VO in, DTO out) {
		// assertNotNull("主任务id不能为空", in.getString("tId"));
		// assertNotNull("子任务id不能为空", in.getString("stId"));
	}

	@Override
	public void execute(VO in, DTO out) {
		Tarsk t = new Tarsk();
		TaskServiceImpl tsi = (TaskServiceImpl) getService().getBean(
				"taskService");
		long proccessid = 0;
		if (!StringUtils.isEmpty(in.getString(PARAM_IN_PROCESSID))) {
			proccessid = Long.valueOf(in.getString(PARAM_IN_PROCESSID));
			Task ta = tsi.getTask(proccessid);
			if (ta != null)
				t = (Tarsk) ta.get("maintarsk");

		} else {
			String tid = in.getString("tId");
			if (!StringUtils.isEmpty(tid)) {
				t.settId(Long.valueOf(tid));
			}
		}
		TarskService ts = (TarskService) getService().getBean("tarskService");
		String jsons = "";
		JSONArray json = null;
		if(null!=t) {
			List<Tarsk> tList = ts.getTarsks(t);
			if (tList.size() > 0) {
				json = JSONArray.fromObject(tList);
				jsons = json.toString();
			}
		}
		

		out.put("tarskJson", jsons);

	}

	public void initTarsk(VO in, DTO out) {
		String tId = in.getString("tId");
		String stId = in.getString("stId");
		SubTarskInfo stk = new SubTarskInfo();

	}
}
