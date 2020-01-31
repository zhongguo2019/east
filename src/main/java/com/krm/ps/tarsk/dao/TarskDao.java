package com.krm.ps.tarsk.dao;

import java.util.List;

import com.krm.ps.tarsk.vo.SubTarskInfo;
import com.krm.ps.tarsk.vo.Tarsk;

public interface TarskDao  {
	/**任务初始化，初始化创建时间和任务id，status，type字段
	 * @param tarsk
	 */
	public Tarsk tarskInit(Tarsk t);
	/**更新已初始化的任务启动时间，status
	 * @param t
	 */
	public void tarskStart(Tarsk t);
	/**更新已完成任务为结束状态，endTime字段
	 * @param t
	 */
	public void tarskEnd(Tarsk t);
	/**更新任务信息
	 * @param t
	 */
	public void updateTarsk(Tarsk t);
	/**获取任务信息
	 * @param t
	 * @return
	 */
	public List<Tarsk> getTarsks(Tarsk t);
	/**初始化子任务信息
	 * @param stk
	 */
	public SubTarskInfo subTarskInit(SubTarskInfo stk) ;

	/**更新子任务信息
	 * @param stk
	 */
	public void updateSubTarsk(SubTarskInfo stk);
	/**获取子任务信息列表
	 * @param stk
	 * @return
	 */
	public List<SubTarskInfo>getSubTarsks(SubTarskInfo stk);
	
	public void deleteSubTarsk(SubTarskInfo stk);
	
    public List<SubTarskInfo>	getSubTarskInfos(String date, String organId, String repids);

    public abstract List getSubTarsks(String paramString1, String paramString2);
}
