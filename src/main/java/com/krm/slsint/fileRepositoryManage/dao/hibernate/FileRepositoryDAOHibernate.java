package com.krm.slsint.fileRepositoryManage.dao.hibernate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.util.Constants;
import com.krm.ps.util.FuncConfig;
import com.krm.ps.util.SysConfig;
import com.krm.slsint.fileRepositoryManage.dao.FileRepositoryDAO;
import com.krm.slsint.fileRepositoryManage.services.FileRepositoryService;
import com.krm.slsint.fileRepositoryManage.vo.FileRepositoryRecord;

public class FileRepositoryDAOHibernate extends BaseDAOHibernate implements
		FileRepositoryDAO
{

	private boolean validateParams(Object[] params)
	{
		boolean validateResult = false;
		for (int i = 0; i < params.length; i++)
		{
			if (validateResult)
			{
				break;
			}
			validateResult = validateResult || params[i] == null;
		}
		return validateResult;
	}

	private String getClause(Map paramMap, String statement, String paramName,
		Object paramValue)
	{
		String clause = statement;
		if(paramValue == null) return statement;
		if (paramMap.keySet().size() == 0)
		{
			clause = " frr." + paramName + " = :" + paramName;
		}
		else
		{
			clause = clause + " AND frr." + paramName + " = :" + paramName;
		}
		paramMap.put(paramName, paramValue);
		return clause;
	}

	public FileRepositoryRecord getRecord(Long pkid)
	{
		String hql = "from FileRepositoryRecord frr where frr.pkid = :pkid";
		Map params = new HashMap();
		params.put("pkid", pkid);
		FileRepositoryRecord record = (FileRepositoryRecord) super
			.uniqueResult(hql, params);

		return record;
	}
	
	public FileRepositoryRecord getRecord(String funId, String prefix,
			String fileName, String postfix, String path){
		List list = getFileList(funId, null, fileName, 
				prefix, postfix, path, null, null, null, null);
		if (list.size() == 0){
			return null;
		}
		return (FileRepositoryRecord) list.get(0);
	}

	public List getFileList(String funId, String editTime, String fileName,
		String prefix, String postfix, String path, Long organId, Long userId,
		String description, String memo)
	{
		String hql = "from FileRepositoryRecord frr ";
		Object[] paramArr = new Object[]{funId, editTime, fileName, prefix,
			postfix, path, organId, userId, description, memo};
		String[] paramNameArr = new String[]{"funId", "editTime", "fileName",
			"namePrefix", "namePostfix", "filePath", "organId", "userId", "description",
			"memo"};

		Map params = new HashMap();
		List queryResult;
		String whereClause = "";
		if (validateParams(paramArr))
		{
			hql = hql.concat("WHERE");
			for (int i = 0; i < paramArr.length; i++)
			{
				whereClause = getClause(params, whereClause, paramNameArr[i],
					paramArr[i]);
			}
			hql = hql.concat(whereClause);
			queryResult = list(hql, params);
		}
		else
		{
			queryResult = list(hql);
		}

		return queryResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.fileRepositoryManage.dao.FileRepositoryDAO#getNextShowOrder(java.lang.String)
	 */
	public Long getNextShowOrder(String funId)
	{
		String hql = "from FileRepositoryRecord frr where frr.funId := funId";
		Map params = new HashMap();
		params.put("funId", funId);
		FileRepositoryRecord rec = (FileRepositoryRecord) super.uniqueResult(
			hql, params);
		Long nextOrder = new Long(rec.getShowOrder().intValue() + 1);
		return nextOrder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.fileRepositoryManage.dao.FileRepositoryDAO#getFileRepositoryMaxOrderNo(java.lang.String)
	 */
	public Long getFileRepositoryMaxOrderNo(String fun_id)
	{
		Object[][] scalaries = {{"showOrder", Hibernate.LONG}};
		String sql = "select MAX(show_order) as showOrder from code_file_repository where fun_id = "
			+ fun_id;
		List list = list(sql, null, scalaries);
		Iterator it = list.iterator();
		Long order = (Long) it.next();
		if (order == null)
		{
			return new Long(0);
		}
		else
		{
			// System.out.println("code_file_repository show_order is : " +
			// order);
			return order;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.fileRepositoryManage.dao.FileRepositoryDAO#getFileList(java.lang.Long,
	 *      java.lang.String)
	 */
	public List getFileList(Long user_id, String fun_id)
	{
		String hql = "Select f from FileRepositoryRecord f where  f.status <> '"
			+ Constants.STATUS_DEL
			+ "' and f.funId =:fun_id "
			+ " and f.pkid in(select uf.mpkid from FileShareData uf where uf.user_id =:user_id) order by f.showOrder";
		Map map = new HashMap();
		map.put("user_id", user_id);
		map.put("fun_id", fun_id);
		return list(hql, map);
	}

	public List getFileList(Long user_id, String funId, String editTime,
		String fileName, String organId, Long status)
	{
		
		StringBuffer sb = new StringBuffer(
			"select new com.krm.slsint.fileRepositoryManage.web.form.FileRepositoryCfgForm(");
		sb.append("f.pkid,f.funId,dic.dicname,f.editTime,f.fileName,f.namePrefix,");
		sb.append("f.namePostfix,f.filePath,f.organId,f.organId,f.userId,f.showOrder,f.status,");
		sb.append("f.description,f.memo");
		sb.append(") from com.krm.slsint.fileRepositoryManage.vo.FileRepositoryRecord f,Dictionary dic");
//		sb.append(" where f.status <> '" + Constants.STATUS_DEL+"'");
		//��DB2�Ļ����£��޸��﷨  2012-04-01   ����
		
		//��Ժ��� DB2�� status �ֶ�Ϊ �ַ�����  ������޸�   2012-4-28    ��� 
		
		if(SysConfig.DB=='d') {
			if(!FuncConfig.isOpenFun("file.repository.hubei")){
				sb.append(" where f.status <> " + Constants.STATUS_DEL+"");
			}else{			
				sb.append(" where f.status <> '" + Constants.STATUS_DEL+"'");
			}		
		} else {
			sb.append(" where f.status <> '" + Constants.STATUS_DEL+"'");
		}
		//�˴�funIdΪ�ַ���,dicidΪchar��.��DB2����ת��������Ƚ�
		if(SysConfig.DB=='d'){
			sb.append(" and INT(f.funId)=dic.dicid");
		}else{
			sb.append(" and f.funId=dic.dicid");
		}
		//sb.append(" and f.organId=organ.code");
		if (funId != null && !funId.equals("-1"))
		{
			sb.append(" and f.funId ='" + funId + "'");
		}
		if (editTime != null && !editTime.equals(""))
		{
			sb.append(" and f.editTime like '" + editTime + "%'");
		}
		if (fileName != null && !fileName.equals(""))
		{
			sb.append(" and f.fileName like '" + fileName + "%'");
		}
		if (organId != null && !organId.equals(""))
		{
			if(SysConfig.DB=='d'){//DB2��ݿ���ֶ�����Ϊvarchar����������Ҫ�ӵ����  add by ydw 20120323
				sb.append(" and f.organId ='" + organId+"'");
			}else{
				sb.append(" and f.organId =" + organId);
			}
		}
		if (status != null && status.intValue() != -1)
		{
			sb.append(" and f.status ='" + status + "'");
		}
		sb.append(" and dic.parentid=" + FileRepositoryService.FUN_ID);
		// sb.append(" and f.pkid in (select uf.mpkid from FileShareData uf where uf.user_id =" + user_id + ")");
		sb.append(" order by f.showOrder");
		List list = list(sb.toString());
		return list;

	}

	public List getfuns(String funId)
	{
		String hql = "select dic from Dictionary dic where dic.parentid="
			+ funId;
		return list(hql);
	}

	public Long getShowOrder(final String funId)
	{
		final String sql = "select max(show_order) as showOrder from CODE_FILE_REPOSITORY"
			+ " where fun_id = ? ";
		String itemId = "1";
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
				throws HibernateException, SQLException
			{
				PreparedStatement ps = session.connection().prepareStatement(
					sql);
				setPreparedStatementParameters(ps, new Object[]{funId});
				ResultSet rs = ps.executeQuery();
				if (rs.next())
				{
					return rs.getObject(1);
				}
				return 0 + "";
			}
		};
		Object obj = getHibernateTemplate().execute(callback);
		if (obj != null)
		{
			itemId = obj + "";
		}
		return new Long(itemId);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.fileRepositoryManage.dao.FileRepositoryDAO#getRepostioryRecord(java.lang.String, java.lang.String)
	 */
	public List getRepostioryRecord(String funId, String filePath)
	{
		String sql = "select {f.*} from code_file_repository f where fun_id = '"+funId+"' and file_path = '" +filePath+ "'";
		List result = list(sql, new Object[][]{{"f", FileRepositoryRecord.class}}, null, null);
		return result;
	}
	
	public List getRepostioryRecord(String funId)
	{
		String sql = "select {f.*} from code_file_repository f where fun_id = '"+funId+"'";
		List result = list(sql, new Object[][]{{"f", FileRepositoryRecord.class}}, null, null);
		return result;
	}

	public List getFileRecord(String organCode, String fun_id, String postfix) {
		String sql = "select {f.*} from code_file_repository f where fun_id = '"+fun_id+"' and f.organ_id='"+
					organCode+"' and f.file_path like '%"+postfix+"%'";
		List result = list(sql, new Object[][]{{"f", FileRepositoryRecord.class}}, null, null);
		return result;
	}

	public List getMapFile(String funId, String postfix) {
		String sql = "select {f.*} from code_file_repository f where fun_id = '"+funId+"'"+
		" and f.name_postfix='"+postfix+"'";
		List result = list(sql, new Object[][]{{"f", FileRepositoryRecord.class}}, null, null);
		return result;
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.fileRepositoryManage.dao.FileRepositoryDAO#getRepostioryRecord(java.lang.String, java.lang.String)
	 */
	public List getfileRepository(String funId, String organ_id)
	{
		log.info("��ѯ�ʼ�[" + organ_id + "]������[" + funId + "]�ĸ���������" );
		String sql = "select {f.*} from code_file_repository f where fun_id = '"+funId+"' and organ_id = '" +organ_id
					+ "' order by f.file_name, f.edit_time";
		List result = list(sql, new Object[][]{{"f", FileRepositoryRecord.class}}, null, null);
		return result;
	}

	/**
	 * @see com.krm.slsint.fileRepositoryManage.dao.FileRepositoryDAO#getFileListByNamePrefix(java.lang.String, java.lang.String)
	 */
	
	public List getFileListByNamePrefix(String funId, String namePrefix)
	{	
		String hql = "";
		//���ϵͳ��ݿ�����,�õ��ʺϵ�sql���
		if(SysConfig.DB=='d'){
			hql = "Select f from FileRepositoryRecord f where  f.status <> '"
					+ Constants.STATUS_DEL
					+ "' and f.funId =:funId "
					+ " and f.namePrefix =:namePrefix"
					+ " order by f.showOrder";
		}else{
		hql = "Select f from FileRepositoryRecord f where  f.status <> '"
				+ Constants.STATUS_DEL
				+ "' and f.funId =:funId "
				+ " and f.namePrefix =:namePrefix"
				+ " order by f.showOrder";
		}
		Map map = new HashMap();
		map.put("funId", funId);
		map.put("namePrefix", namePrefix);
		return list(hql, map);	
	}
	
}
