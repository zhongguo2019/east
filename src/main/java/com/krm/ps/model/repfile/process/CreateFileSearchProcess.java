package com.krm.ps.model.repfile.process;

import com.krm.ps.common.dto.DTO;
import com.krm.ps.common.dto.VO;
import com.krm.ps.common.process.KProcessImpl;
import com.krm.ps.common.task.Task;
import com.krm.ps.common.task.TaskServiceImpl;
import com.krm.ps.model.repfile.service.RepFileService;
import com.krm.ps.model.sysLog.services.SysLogService;
import com.krm.ps.model.sysLog.util.LogUtil;
import com.krm.ps.model.vo.RepFlfile;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.tarsk.service.TarskService;
import com.krm.ps.tarsk.util.Constant;
import com.krm.ps.tarsk.vo.SubTarskInfo;
import com.krm.ps.tarsk.vo.Tarsk;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateFileSearchProcess extends KProcessImpl{
	
	private final String PARAM_IN_PROCESSID="processid";
	private final Map<String, String> schedule = new HashMap<String, String>();//进度
	@Override
	public void doCheck(VO in, DTO out) {
		assertNotNull("报表日期不允许为空", in.get("condates"));
		assertNotNull("报表编码不允许为空", in.get("reportId"));
		assertNotNull("机构编码不能为空", in.get("organId"));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(VO in, DTO out) {
		
		//获取该进程信息
		TaskServiceImpl tsi = (TaskServiceImpl)getService().getBean("taskService");
		Task ta = tsi.getTask(this.getProcessId());
	//	Task ta=new Task(this.getProcessId());
		String condate = in.getString("condates");
		String begindate=in.getString("begindate");
		String enddate = in.getString("enddate") ;
		String batch=in.getString("batch");
		String cstatus=in.getString("cstatus");
		String repIds=in.getString("reportId");
		//String[] repIDarray=in.getString("reportId").split(",");
		String organCode =in.getString("organId");
		String systemid =in.getString("system_id");
		User user = (User) in.get("user");
		RepFileService rpf = (RepFileService)getService().getBean("repfileservice");

		TarskService tarskService=(TarskService)getService().getBean("tarskService");
		//创建主任务
		Tarsk t = new Tarsk();
		if(!StringUtils.isEmpty(in.getString("tId"))){
		t.settId(Long.valueOf(in.getString("tId")));
		}
		t.setCreateTime((new SimpleDateFormat("yyyyMMddHHmmss"))
				.format(new Date()));
		t.setStatus(Constant.TARSK_STATUS_CREATE);
		//t = tarskService.tarskInit(t);
		SubTarskInfo stk=new SubTarskInfo();
		if(!StringUtils.isEmpty(in.getString("stId"))){
			stk.setPkid(Long.valueOf(in.getString("stId")));
		}
		//下面向业务逻辑service中传入任务对象。
		
		
		List<Report> reportList = new ArrayList();
		// 获取页面选取报表对象集合
		reportList = rpf.getReports(repIds);
		// 插入系统日志
		insertLog(user.getPkid() + "", user.getName(), "-1",
				LogUtil.LogType_User_Operate, organCode,
				"报送数据文件生成->上报文件生成", "-1");
		// 生成上报文件
		List<RepFlfile> repList = rpf.createFile(begindate, enddate,
				reportList, organCode, condate, null, systemid, batch,
				user.getOrganTreeIdxid(), cstatus,t,stk,schedule);
		
		
		ta.put("maintarsk", t);
		out.put("repList", repList);
		out.put("succehints", "文件已经生成、请到文件下载里下载报送");
		out.put("begindate", begindate);
		out.put("enddate", enddate);
		out.put("condates", condate);
		out.put("forwards", "createfileprompt");
		out.put("tarsk", t);
		out.put("tId", t.gettId());
		//List<Tarsk> tList=tarskService.getTarsks(t);
		//JSONArray json=JSONArray.fromObject(tList);
		//out.put("tarskJson", json.toString());
		
		//List reporidsList2 = new ArrayList();//未进行审核的报表编码记录集合showlevel=2
		//未锁定报表showlevel=1
		//List listUnLock = rpf.getRepislock(organCode, condate);
//		
//		if (listUnLock.size() > 0) {
//			reporidsList2 = rpf.getUncheckReports(condate, organCode, repIds);
//			if (reporidsList2.size() != 0) {// 选择的报表中存在审核未通过的报表，so不会继续生成上报文件
//				String reporNametstr = getUncheckReportsName(reporidsList2);
//				out.put("reporNametstr", reporNametstr);
//				out.put("forwards", "reportlistName");
//			} else {
//				TarskService tarskService=(TarskService)getService().getBean("tarskService");
//				//创建主任务
//				Tarsk t = new Tarsk();
//				if(!StringUtils.isEmpty(in.getString("tId"))){
//				t.settId(Long.valueOf(in.getString("tId")));
//				}
//				t.setCreateTime((new SimpleDateFormat("yyyyMMddhhmmss"))
//						.format(new Date()));
//				t.setStatus(Constant.TARSK_STATUS_CREATE);
//				//t = tarskService.tarskInit(t);
//				SubTarskInfo stk=new SubTarskInfo();
//				if(!StringUtils.isEmpty(in.getString("stId"))){
//					stk.setPkid(Long.valueOf(in.getString("stId")));
//				}
//				//下面向业务逻辑service中传入任务对象。
//				
//				
//				List<Report> reportList = new ArrayList();
//				// 获取页面选取报表对象集合
//				reportList = rpf.getReports(repIds);
//				// 插入系统日志
//				insertLog(user.getPkid() + "", user.getName(), "-1",
//						LogUtil.LogType_User_Operate, organCode,
//						"报送数据文件生成->上报文件生成", "-1");
//				// 生成上报文件
//				List<RepFlfile> repList = rpf.createFile(begindate, enddate,
//						reportList, organCode, condate, null, systemid, batch,
//						user.getOrganTreeIdxid(), cstatus,t,stk);
//				
//				
//				ta.put("maintarsk", t);
//				out.put("repList", repList);
//				out.put("succehints", "文件已经生成、请到文件下载里下载报送");
//				out.put("begindate", begindate);
//				out.put("enddate", enddate);
//				out.put("condates", condate);
//				out.put("forwards", "createfileprompt");
//				out.put("tarsk", t);
//				out.put("tId", t.gettId());
//				//List<Tarsk> tList=tarskService.getTarsks(t);
//				//JSONArray json=JSONArray.fromObject(tList);
//				//out.put("tarskJson", json.toString());
//				System.out.println("tId:"+t.gettId());
//			}
//		} else {
//			out.put("reporNametstr", "没有审核通过的记录！请先审核");
//			out.put("forwards", "reportlistName");
//		}
		
	}
/**获取校验位通过报表名称集合字符串
 * @param reporidsList2
 * @return
 */
private String getUncheckReportsName(List reporidsList2){
	String reporNametstr = "";
	Map map=null;
	for(int i=0;i<reporidsList2.size();i++){
		map=(Map)reporidsList2.get(i);
		reporNametstr+=map.get("name")+"\n";
	}
	reporNametstr += "\n以上报表请先审签,再生成上报文件";
	return reporNametstr;
}
private void insertLog(String userId, String userName, String reportId, String logType, String organId, String memo, String modelId)
{
	SysLogService syslogService=(SysLogService)getService().getBean("syslogService");
	syslogService.insertLog(userId, userName, "-1",
			logType, organId,
			memo, modelId);
}
@Override
public void dofinally() {
	System.out.println("回收："+this.getProcessId());
	//super.dofinally();
}

public Map<String, String> getSchedule() {
	return schedule;
}

}
