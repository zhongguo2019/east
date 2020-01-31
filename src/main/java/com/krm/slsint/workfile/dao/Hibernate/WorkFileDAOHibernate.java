package com.krm.slsint.workfile.dao.Hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.util.SysConfig;
import com.krm.slsint.workfile.dao.WorkFileDAO;
import com.krm.slsint.workfile.vo.OleFileData;
import com.krm.slsint.workfile.vo.WorkDirectData;

public class WorkFileDAOHibernate extends BaseDAOHibernate implements
		WorkFileDAO {
	
	/*
	 * (non-Javadoc) 
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#getWorkFileMaxOrderNo()
	 */
	public Long getWorkFileMaxOrderNo() {
		Object[][] scalaries = { { "showOrder", Hibernate.LONG } };
		String sql = "select MAX(show_order) as showOrder from Info_workdirect";
		List list = list(sql, null, scalaries);
		Iterator it = list.iterator();
		Long order = (Long) it.next();
		if(order == null){
			return new Long(0);
		}else{
			return order;
		}
	}
	/*
	 * (non-Javadoc) 
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#getAccessoryMaxOrderNo()
	 */
	public Long getAccessoryMaxOrderNo() {
		Object[][] scalaries = { { "showOrder", Hibernate.LONG } };
		String sql = "select MAX(show_order) as showOrder from info_olefile";
		List list = list(sql, null, scalaries);
		Iterator it = list.iterator();
		Long order = (Long)it.next();
		if (order == null) {
			return new Long(0);
		}else{
			return order;
		}
	}
	/*
	 * (non-Javadoc) 
	 * @see com.krm.slsint.workfile.dao.WorkFileListDAO#getFileList()
	 */
	public List getFileList() {
		Object[][] scalaries = { { "workPkid", Hibernate.LONG },
				{ "workKindId", Hibernate.LONG },
				{ "Dicdicname", Hibernate.STRING },
				{ "workTitle", Hibernate.STRING },
				{ "workOperName", Hibernate.STRING },
				{ "workOrganName", Hibernate.STRING },
				{ "workIssDate", Hibernate.STRING },
				{ "showOrder1", Hibernate.LONG },
				{"dicPkId",Hibernate.LONG}};
		//BY Shengping 20120611 ����pkid
		String sql = "select t1.pkid as workPkid, "+
					       "t1.kind_id as workKindId, "+
					       "t3.dicname as Dicdicname, "+
					        "t1.title as workTitle, "+
					       "t1.oper_name as workOperName, "+
					       "t1.organ_name as workOrganName, "+
					       "t1.iss_date as workIssDate, "+
					       "t1.show_order as showOrder1, "+
					       "t3.pkid as dicPkId "+
					"from info_workdirect t1, code_dictionary t3 "+
					"where t1.status<>'9' and t1.kind_id = t3.pkid order by t1.pkid desc ";
		return this.list(sql, null, scalaries);
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#getFileList(java.lang.String, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String)
	 */
	public List getFileList(String title,String content,Long kind,Long fileSource,String issDate) {
		Object[][] scalaries = { { "workPkid", Hibernate.LONG },
				{ "workKindId", Hibernate.LONG },
				{ "Dicdicname", Hibernate.STRING },
				{ "workTitle", Hibernate.STRING },
				{ "workOperName", Hibernate.STRING },
				{ "workOrganName", Hibernate.STRING },
				{ "workIssDate", Hibernate.STRING },
				{ "showOrder1", Hibernate.LONG },
				{"dicPkId",Hibernate.LONG},
				{"workcontent",Hibernate.STRING}};
		String sql = "select t1.pkid as workPkid, "+
				       "t1.kind_id as workKindId, "+
				       "t3.dicname as Dicdicname, "+
				       "t1.title as workTitle, "+
				       "t1.oper_name as workOperName, "+
				       "t1.organ_name as workOrganName, "+
				       "t1.iss_date as workIssDate, "+
				       "t1.show_order as showOrder1, "+
				       "t3.pkid as dicPkId, "+
				       "t1.content as workcontent "+
				"from info_workdirect t1,code_dictionary t3 "+
				"where t1.status<>'9' and t1.kind_id = t3.pkid  ";
		if(title != null && !title.equals("")){
			sql += " and t1.title like '%" + title + "%' ";
		}
		if(kind != null && kind.longValue() >0){
			sql += " and t1.kind_id = " + kind;
		}
		if(fileSource != null && fileSource.longValue() >0){
			sql += " and t1.file_source_id = " + fileSource;
		}
		if(issDate != null && !issDate.equals("")){
			sql += " and t1.iss_date like '%" + issDate + "%' ";
		}
		List res = new ArrayList();
		if('i'!=SysConfig.DB){
			if(content != null && !content.equals("")){
				sql += " and t1.content like '%" + content + "%' ";
			}
			sql += " order by workKindId,showOrder1,t1.iss_date desc";
			return list(sql, null, scalaries);
		}else{
			sql += " order by workKindId,showOrder1,t1.iss_date desc";
			List l = list(sql, null, scalaries);
			if(content != null && !content.equals("")){
				for(int i=0;i<l.size();i++){
					Object[] obj = (Object[]) l.get(i);
					String workcontent = (String) obj[9];
					if(workcontent.contains(content)){
						res.add(obj);
					}
				}
				return res;
			}
			return l;
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#getFiles(java.lang.Long)
	 */
	public List getFiles(Long pkId) {
		Object [][] scalaries = {{"oPkId",Hibernate.LONG},{"KindId",Hibernate.STRING},
				                  {"ConId",Hibernate.LONG},{"showOrder",Hibernate.LONG},
				                  {"sFileName",Hibernate.STRING},{"dFileName",Hibernate.STRING}};
		String sql = "select pkid as oPkId,kind_id as KindId,con_id as ConId,show_order as showOrder,s_filename as sFileName,d_filename as dFileName from info_olefile t where t.con_id = "+ pkId;
		List list = list(sql,null,scalaries);
		return list;
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#delFile(java.lang.Long)
	 */
	public void delFile(Long pkId,String userName,Long userId,String date) {
		String sql = "update info_workdirect set status = ? , del_oper = ? , del_opername = ? , " +
				    " del_date = ? where pkid = ? ";
		Object[] scalaries = new Object[]{"9",userId,userName,date,pkId};
		this.jdbcUpdate(sql,scalaries);
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#getFile(java.lang.Long)
	 */
	public List getFile(Long fPkId) {
		Object [][] scalaries = {{"pkId",Hibernate.LONG},{"sFileName",Hibernate.STRING}};
		String sql = "select o.pkid as pkId,o.s_filename as sFileName from info_olefile o" +
					" where o.con_id = "+ fPkId + "and o.kind_id = '2' order by o.show_order ";
		List list = list(sql,null,scalaries);
		return list;
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#getOleFile(java.lang.Long)
	 */
	public List getOleFile(Long pkId) {
		Object [][] scalaries = {{"pkId",Hibernate.LONG},{"kindId",Hibernate.STRING},
				                 {"conId",Hibernate.LONG},{"showOrder",Hibernate.LONG},
				                 {"sFileName",Hibernate.STRING},{"dFileName",Hibernate.STRING}};
		String sql="select o.pkid as pkId , o.kind_id as kindId , o.con_id as conId , o.show_order as showOrder ," +
				   "o.s_filename as sFileName , o.d_filename as dFileName " +
				   "from info_olefile o , info_workdirect w " +
				   "where w.pkid = "+ pkId +" and o.con_id = w.pkid and o.kind_id = '1' and w.status <> '9' ";
		List list = list(sql,null,scalaries);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#updateWork(java.lang.Object)
	 */
	public void updateWork(WorkDirectData wo,Long pkId) {
		String sql = "update info_workdirect " +
				        " set iss_date = ? , iss_oper = ? , oper_name = ? ," +
				        		"organ_id = ? , organ_name = ? , title = ? ," +
				        				"content = ? ,file_source_id = ? , file_source_name = ? ,kind_id =? " +
				        						"where pkid = ? and status <> '9'";
		Object [] scalaries = new Object[]{wo.getIssDate(),wo.getIssOper(),wo.getOperName(),wo.getOrganId(),wo.getOrganName(),wo.getTitle(),wo.getContent(),wo.getFileSourceId(),wo.getFileSourceName(),wo.getKindId(),pkId};
		this.jdbcUpdate(sql,scalaries);
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#updateOle(com.krm.slsint.workfile.vo.OleFileData)
	 */
	public void updateOle(OleFileData of ,Long pkId) {
		String sql = "update info_olefile " +
				        " set s_filename = ? , d_filename = ? where con_id = ? ";
		Object [] scalaries = new Object[]{of.getSFileName(),of.getDFileName(),pkId};
		this.jdbcUpdate(sql,scalaries);
	}
	
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#isOle(java.lang.Long)
	 */
	public List isOle(Long pkId) {
		Object [][] scalaries = {{"pkId",Hibernate.LONG},{"conId",Hibernate.LONG},
				                 {"sFileName",Hibernate.STRING},{"dFileName",Hibernate.STRING}};
		String sql = "select o.pkid as pkId,o.con_id as conId,o.s_filename as sFileName,o.d_filename as dFilename from info_olefile o where o.con_id = "+pkId;
		List list = this.list(sql,null,scalaries);
		return list;
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#insertOle(com.krm.slsint.workfile.vo.OleFileData, java.lang.Long)
	 */
	public void insertOle(OleFileData of, Long pkId) {
		String sql = "insert into info_olefile (s_filename,d_filename,con_id,kind_id,show_order) " +
        " values (?,?,?,?,?)";
		Object [] scalaries = new Object[]{of.getSFileName(),of.getDFileName(),pkId,"1",of.getShowOrder()};
		this.jdbcUpdate(sql,scalaries);
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#getNewFile()
	 */
	public List getNewFile() {
		Object[][] scalaries = { { "workPkid", Hibernate.LONG },
				{ "workKindId", Hibernate.LONG },
				{ "Dicdicname", Hibernate.STRING },
				{ "workTitle", Hibernate.STRING },
				{ "workOperName", Hibernate.STRING },
				{ "workOrganName", Hibernate.STRING },
				{ "workIssDate", Hibernate.STRING },
				{ "showOrder1", Hibernate.LONG },
				{ "dicPkId",Hibernate.LONG}};
		String sql = "select t1.pkid as workPkid, "+
					       "t1.kind_id as workKindId, "+
					       "t3.dicname as Dicdicname, "+
					        "t1.title as workTitle, "+
					       "t1.oper_name as workOperName, "+
					       "t1.organ_name as workOrganName, "+
					       "t1.iss_date as workIssDate, "+
					       "t1.show_order as showOrder1, "+
					       "t3.pkid as dicPkId "+
					"from info_workdirect t1, code_dictionary t3 "+
					"where t1.status<>'9' and t1.kind_id = t3.pkid " +
					"order by showOrder1,t1.iss_date desc";
		return this.list(sql, null, scalaries);
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#changeFileStatus(java.lang.Long)
	 */
	public void changeFileStatus(Long pkId) {
		String sql = "update info_workdirect set status=? where pkId = ? and status<>'9'";
		Object [] scalaries = new Object[]{new Long(1),pkId};
		this.jdbcUpdate(sql,scalaries);
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#delFile(java.lang.Long)
	 */
	public void delFile(Long pkId){
		String sql="delete from info_olefile where pkid=?";
		Object [] scalaries = new Object[]{pkId};
		this.jdbcUpdate(sql,scalaries);
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#getUsers(java.lang.String)
	 */
	public List getUserId(String organCode) {
		Object [][] scalaries = {{"pkId",Hibernate.LONG}};
		String sql = "select u.pkid as pkId "+
						"from umg_user u "+
                       " where u.organ_id = '" +organCode +"' ";
		List list = this.list(sql,null,scalaries);
		return list;
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#getUserName(java.lang.String)
	 */
	public List getUserName(String organCode) {
		Object [][] scalaries = {{"userName",Hibernate.STRING}};
		String sql = "select u.name as userName from umg_user u where u.organ_id = '"+organCode+"' ";
		List list = this.list(sql,null,scalaries);
		return list;
	}
	public void updateMailStatus(Long pkId) {
	}
	public List getDicNameById(Long dicId) {
		Object [][] scalaries = {{"dicName",Hibernate.STRING}};
		String sql = "select dicname as dicName from code_dictionary where pkid = "+dicId;
		return this.list(sql,null,scalaries);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#getOleInfo(java.lang.Long)
	 */
	public List getOleInfo(Long pkId) {
		Object [][] scalaries = {{"fileName",Hibernate.STRING},{"rondomFileName",Hibernate.STRING},{"pOper",Hibernate.LONG}};
		String sql = "select o.s_filename as fileName,o.d_filename as rondomFileName,f.p_oper as pOper "+
						"from info_olefile o,info_filetransfer f "+
						"where o.pkid= "+pkId+" and o.con_id = f.pkid and o.kind_id = '2'";
		return this.list(sql,null,scalaries);
	}
	public void deleteWorkFile(Long fileSourceId)
	{
		String sql = "delete from info_workdirect where file_source_id = "+fileSourceId;
		jdbcUpdate(sql, null);
	}
	

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#saveWorkFile(com.krm.slsint.workfile.vo.WorkDirectData, java.lang.String)
	 */
//	public void saveWorkFile(WorkDirectData wo) {
//		Connection c = null;
//		try {
//			c = jdbc.getDataSource().getConnection();
//			Connection nc = DBDialect.getNativeConnection(c);
//			c.setAutoCommit(false);
//			String sql = "insert into info_workdirect (pkid,kind_id,iss_date,iss_oper,oper_name,depart_id," +
//					"depart_name,organ_id,organ_name,title,show_order,content,file_source_id,file_source_name," +
//					"status) values ("+DBDialect.genSequence("info_workdirect_pkid_seq")+"," +
//							        ""+wo.getKindId()+"," +
//							       "'"+wo.getIssDate()+"'," +
//							       	""+wo.getIssOper()+",'"+wo.getOperName()+"',"+wo.getDepartId()+"," +
//							       	 "'"+wo.getDepartName()+"','"+wo.getOrganId()+"','"+wo.getOrganName()+"'," +
//							       	 "'"+wo.getTitle()+"',"+wo.getShowOrder()+",?,"+wo.getFileSourceId()+"," +
//							       	 "'"+wo.getFileSourceName()+"','"+wo.getStatus()+"')";
//			PreparedStatement ps = nc.prepareStatement(sql);
//			DBDialect.setLongTextField(ps, 1, wo.getContent());			
//			ps.execute();
//			ps.close();
//			c.commit();
//			c.setAutoCommit(true);
//			
//			int pkid = jdbc.getJdbcTemplate().queryForInt(
//			"SELECT MAX(pkid) FROM info_workdirect");// TODO
//			wo.setPkId(new Long(pkid));
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally{
//			try {
//				if(c!=null)
//					c.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	public WorkDirectData viewWorkFileContent(Long pkId) {
//		String sql = "select * from info_workdirect where pkid = "+pkId;
//		WorkDirectData wd = null;
//		Connection c = null;
//		Statement s = null;
//		ResultSet r = null;
//		try {
//			c = jdbc.getDataSource().getConnection();
//			s = c.createStatement();
//			r = s.executeQuery(sql);
//			if(r.next()){
//				wd = new WorkDirectData();
//				wd.setPkId(new Long(r.getLong("pkid")));
//				wd.setKindId(new Long(r.getLong("kind_id")));
//				wd.setIssDate(r.getString("iss_date"));
//				wd.setIssOper(new Long(r.getLong("iss_oper")));
//				wd.setOperName(r.getString("oper_name"));
//				wd.setDepartId(new Long(r.getLong("depart_id")));
//				wd.setDepartName(r.getString("depart_name"));
//				wd.setOrganId(r.getString("organ_id"));
//				wd.setOrganName(r.getString("organ_name"));
//				wd.setTitle(r.getString("title"));
//				wd.setShowOrder(new Long(r.getLong("show_order")));
//				String content = DBDialect.getLongTextField(r, "content");
//				wd.setContent(content);
//				wd.setFileSourceId(new Long(r.getLong("file_source_id")));
//				wd.setFileSourceName(r.getString("file_source_name"));
//				wd.setStatus(r.getString("status"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally{
//			try {
//				if (c != null && !c.isClosed()) {
//					c.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return wd;
//	}
//	
//	/* (non-Javadoc)
//	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#updateWork(com.krm.slsint.workfile.vo.WorkDirectData, java.lang.Long)
//	 */
//	public void updateWork(WorkDirectData wo,Long pkId) {
//		Connection c = null;
//		String sql = "update info_workdirect set kind_id = "+wo.getKindId()+",iss_date = '"+wo.getIssDate()+"'," +
//				"iss_oper = "+wo.getIssOper()+",oper_name = '"+wo.getOperName()+"'," +
//				"organ_id = '"+wo.getOrganId()+"',organ_name = '"+wo.getOrganName()+"',title = '"+wo.getTitle()+"',content = ?," +
//						"file_source_id = "+wo.getFileSourceId()+",file_source_name = '"+wo.getFileSourceName()+"'" +
//								" where pkid = "+pkId+" and status <> '9'";
//		try {
//			c = jdbc.getDataSource().getConnection();
//			Connection nc = DBDialect.getNativeConnection(c);
//			PreparedStatement ps = nc.prepareStatement(sql);
//			DBDialect.setLongTextField(ps, 1, wo.getContent());
//			ps.execute();
//			ps.close();
//			c.commit();
//			c.setAutoCommit(true);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			try {
//				if (c != null && !c.isClosed()) {
//					c.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}		
//	}
}
