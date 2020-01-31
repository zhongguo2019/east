package com.krm.ps.tarsk.service;

import java.util.List;

import com.krm.ps.tarsk.vo.SubTarskInfo;
import com.krm.ps.tarsk.vo.Tarsk;

public interface TarskService {
	
/**主任务初始化，初始化创建时间和任务id，status，type字段
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
/**删除子任务
 * @param stk
 */
public void deleteSubTarsk(SubTarskInfo stk);

/**删除主任务
 * @param t
 */
public void deleteTarsk(Tarsk t);

/**根据业务字段获取，判断是否已经存在当前正在运行的子任务
 * @param date
 * @param organId
 * @param repids
 * @return
 */
public List<SubTarskInfo> alreadySubTarsk(String date,String organId,List repids);

public abstract List getRepSubTarsks(String paramString1, String paramString2);

}
