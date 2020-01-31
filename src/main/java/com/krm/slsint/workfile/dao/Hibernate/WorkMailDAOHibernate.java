package com.krm.slsint.workfile.dao.Hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.util.SysConfig;
import com.krm.slsint.workfile.dao.WorkMailDAO;
import com.krm.slsint.workfile.vo.FileTransferData;
import com.krm.slsint.workfile.vo.OleFileData;

public class WorkMailDAOHibernate extends BaseDAOHibernate implements
WorkMailDAO{
	
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
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#getInbox(java.lang.Long, java.lang.String)
	 */
	public List getInbox(Long userId,String status) {
		Object[][] scalaries = {{"aPkId", Hibernate.LONG },
				{ "aMailId", Hibernate.LONG }, { "aStatus", Hibernate.STRING },
				{ "fPkId", Hibernate.LONG }, { "fOper", Hibernate.LONG },
				{"fDate",Hibernate.STRING},{"fTitle",Hibernate.STRING},
				{"uPkId",Hibernate.LONG},{"uName",Hibernate.STRING},{"itemType",Hibernate.STRING}};
		String sql = "select a.pkid as aPkId,a.mail_id as aMailId,a.status as aStatus,f.pkid as fPkId,f.p_oper as fOper,"+
		             " f.p_date as fDate,f.title as fTitle,u.pkid as uPkId,u.name as uName ,f.itemtype as itemType "+
		             "from info_acceptoper a,info_filetransfer f,umg_user u "+
		             "where f.pkid = a.mail_id and u.pkid = f.p_oper and a.a_operid = " + userId ;
		       if(!status.equals("9") && !status.equals("")){
		    	   sql+=" and a.status =  '" + status + "' " ;
		       }
		       if(status.equals("")){
		    	   sql += " and a.status <> '9' ";
		       }
		       if(status.equals("9")){
		    	   sql += "and a.status = '9' ";
		       }
		       sql += "order by f.p_date desc ,f.pkid desc,a.status desc";
		return this.list(sql,null,scalaries);
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#updataMail(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	public void updataMail(Long pkId,String status,Long userId) {
		
		String sql = "";
		Object[] scalaries = null;
		if(pkId.longValue() !=0){
			sql = "update info_acceptoper set status = ? where pkid = ? and a_operid = ? and status = ?";
			scalaries = new Object[]{"9",pkId,userId,status};
		}
		if(pkId.longValue() == 0){
			if(status.equals("") || status == null){				
				sql = "update info_acceptoper set status = ? where a_operid = ? ";
				scalaries = new Object[]{"9",userId};
			}else{				
				sql = "update info_acceptoper set status = ? where a_operid = ? and status = ?";
				scalaries = new Object[]{"9",userId,status};
			}			
		}
		// 2010-7-12 ����02:06:05 Ƥ���޸�
		// ��ɾ���ʼ���ʱ�򣬰ѳ���ɾ����ʼ�����Ϊɾ����
		// ���ǲ���ȷ�ģ���������˸���
		sql += " and status <> '7'"; // ���Գ���ɾ����ʼ���ɾ���־
		this.jdbcUpdate(sql,scalaries);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#deleteMail(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	public void deleteMail(Long pkId ,String status,Long userId) {
		String sql = "";
		Object[] scalaries = null;
			sql = "delete from info_filetransfer where pkid = ? and status = ? ";
			scalaries = new Object[]{pkId,status};
		jdbcUpdate(sql,scalaries);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#getOutBox(java.lang.Long, java.lang.Long)
	 */
	public List getOutBox(Long pOper) {
		Object[][] scalaries = {{"pkId",Hibernate.LONG},{"title",Hibernate.STRING},
		                        {"pDate",Hibernate.STRING},{"userName",Hibernate.STRING}};
		String sql = "select f.pkid as pkId,f.title as title,f.p_date as pDate,u.name as userName " +
					 " from info_acceptoper a , info_filetransfer f,umg_user u "+
		             "where a.mail_id = f.pkid and a.a_operid = u.pkid and f.p_oper = "+ pOper +" " +
		            " and f.status <> '9' order by f.p_date desc , f.pkid desc";
		return this.list(sql, null, scalaries);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#delOutBox(java.lang.Long)
	 */
	public void delOutBox(Long pkid) {
		String sql = "update info_filetransfer set status = ? where pkid = ? ";
		Object[] scalaries = new Object[]{"9",pkid};
		jdbcUpdate(sql,scalaries);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#mailContent(java.lang.Long)
	 */
	public List mailContent(Long pkId) {
		Object [][] scalaries = {{"userName",Hibernate.STRING}};
		String sql = "select {f.*}, "+
                    "u.name as userName "+
                    "from Info_filetransfer f ,umg_user u "+
                    "where f.pkid = "+pkId+" and  u.pkId = f.p_oper "+
                    "order by f.p_date";
		
		return this.list(sql,new Object[][]{{"f", FileTransferData.class}},scalaries);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#viewInBoxContent(java.lang.Long)
	 */
	public List viewInBoxContent(Long pkId) {
		Object [][] scalaries = {{"userName",Hibernate.STRING}};
		String sql = "select {f.*},"+
                        "u.name as userName "+
                        	"from  info_filetransfer f , info_acceptoper a ,umg_user u "+
                        		"where a.pkid = "+pkId+" and a.mail_id = f.pkid "+
                        			" and u.pkid = f.p_oper ";
//		log.debug(sql);
		List list =  this.list(sql,new Object[][]{{"f",FileTransferData.class}},scalaries);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#getUsers(java.lang.Long)
	 */
	public List getUsers(Long pkId) {
		Object [][] scalaries = {{"userName",Hibernate.STRING}};
		String sql = "select u.name as userName from info_acceptoper a ,info_filetransfer f ,umg_user u " +
				" where a.mail_id = f.pkid and f.pkid = "+ pkId +" and u.pkid = a.a_operid ";
		List list = this.list(sql,null,scalaries);
		return list;
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
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#getFile(java.lang.Long)
	 */
	public List getFile(Long fPkId) {
		Object [][] scalaries = {{"pkId",Hibernate.LONG},{"sFileName",Hibernate.STRING},{"dFileName",Hibernate.STRING}};
		String sql = "select o.pkid as pkId,o.s_filename as sFileName,o.d_filename as dFileName from info_olefile o" +
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
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#delFile(java.lang.Long)
	 */
	public void delFile(Long pkId){
		String sql="delete from code_file_repository where pkid=?";
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
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#changeMailStatus(java.lang.Long)
	 */
	public void updateMailStatus(Long pkId) {
		String sql = "update info_acceptoper set status=? where pkId = ? and status<>'9'";
		Object [] scalaries = new Object[]{new Long(1),pkId};
		this.jdbcUpdate(sql,scalaries);
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#getNewMail(java.lang.Long)
	 */
	public List getNewMail(Long userId) {
		Object[][] scalaries = {{"aPkId", Hibernate.LONG },
				{ "aMailId", Hibernate.LONG }, { "aStatus", Hibernate.STRING },
				{ "fPkId", Hibernate.LONG }, { "fOper", Hibernate.LONG },
				{"fDate",Hibernate.STRING},{"fTitle",Hibernate.STRING},
				{"uPkId",Hibernate.LONG},{"uName",Hibernate.STRING}};
		String sql = "select a.pkid as aPkId,a.mail_id as aMailId,a.status as aStatus,f.pkid as fPkId,f.p_oper as fOper, "+
		              "f.p_date as fDate,f.title as fTitle,u.pkid as uPkId,u.name as uName "+
		              "from info_acceptoper a,info_filetransfer f,umg_user u  "+
		               "where f.pkid = a.mail_id and u.pkid = f.p_oper and a.status='0' and a.a_operid = "+userId +" order by f.p_date desc , f.pkid desc";
		return this.list(sql,null,scalaries);
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
	
	
	
	public List getInboxa(Long userId,String bdate,String date,String zu,String status) {
		//informix ��ݿ������һ���������ԭ���ֶ� �����������ж�   2012.7.18 xm
		String sql = "select a.pkid as aPkId,a.mail_id as aMailId,a.status as aStatus,f.pkid as fPkId,f.p_oper as fOper,"+
		             " f.p_date as fDate,f.title as fTitle,u.pkid as uPkId,u.name as uName,a.reply as aReply ,f.itemtype as itemType";
		       if(SysConfig.DB == 'i'){      
		           sql+=",f.unlock as fLock from info_acceptoper a,info_filetransfer f,umg_user u where a.status <> '8' and f.pkid = a.mail_id and u.pkid = f.p_oper  and a.a_operid = " + userId ;
		       }else{
		    	   sql+=" from info_acceptoper a,info_filetransfer f,umg_user u where a.status <> '8' and f.pkid = a.mail_id and u.pkid = f.p_oper  and a.a_operid = " + userId ;
		       }
				if ("reply".equals(zu)) // 20100513 ���zuΪreply����ô��ʱ��status��ֵ����reply��ֵ4����
				{
					// ���ö�Ӧ�Ļظ�״̬
					if(status != null && !status.equals("")){
					   sql+=" and a.reply =  " + status + " and f.p_date >= '" + bdate + 
					   		"' and f.p_date <= '"+date+"' " +
					   		" and a.status <> '9'" + // �����Ѿ�ɾ����ʼ�	
					   		" and a.status <> '7'" + // ���˳���ɾ����ʼ�
					   		" order by f.p_date desc ,f.pkid desc,a.status desc";
					} else {
						log.warn("�����statusֵΪnull����Ϊ�գ�����");
						return new ArrayList();
					}
				}
				else if(!status.equals("9") && !status.equals("")){
			       sql+=" and a.status =  '" + status + "' " ;
			    }
			    if(status.equals("")){
			       sql += " and a.status <> '9' and a.status<>'7'";
			    }
				if(zu.equals("2")){
					sql+="and f.p_date >= '"+bdate+"' and f.p_date <= '"+date+"' order by f.p_date desc ,f.pkid desc,a.status desc";
				}
				if(zu.equals("0")){
					sql+="and f.p_date < '"+bdate+"00' order by f.p_date desc ,f.pkid desc,a.status desc";
				}
				if(zu.equals("1")){
					sql+="and f.p_date > '"+bdate+"' and f.p_date < '"+date+"' order by f.p_date desc ,f.pkid desc,a.status desc";
				}
				if(zu.equals("title")&&date.equals("posi")){
					sql += "order by f.title ,f.pkid desc,a.status desc";	
				}
				if(zu.equals("title")&&date.equals("nega")){
					sql += "order by f.title desc ,f.pkid desc,a.status desc";	
				}
				if(zu.equals("addresser")&&date.equals("posi")){
					sql += "order by u.name ,f.pkid desc,a.status desc";	
				}
				if(zu.equals("addresser")&&date.equals("nega")){
					sql += "order by u.name desc ,f.pkid desc,a.status desc";	
				}	
				
				if(SysConfig.DB == 'i'){   
				Object[][] scalaries = {{"aPkId", Hibernate.LONG },
						{ "aMailId", Hibernate.LONG }, { "aStatus", Hibernate.STRING },
						{ "fPkId", Hibernate.LONG }, { "fOper", Hibernate.LONG },
						{"fDate",Hibernate.STRING},{"fTitle",Hibernate.STRING},
						{"uPkId",Hibernate.LONG},{"uName",Hibernate.STRING},{ "aReply", Hibernate.LONG },{"itemType",Hibernate.STRING},{"fLock",Hibernate.STRING}};
				
				     return this.list(sql,null,scalaries);
				}else{
					Object[][] scalaries = {{"aPkId", Hibernate.LONG },
							{ "aMailId", Hibernate.LONG }, { "aStatus", Hibernate.STRING },
							{ "fPkId", Hibernate.LONG }, { "fOper", Hibernate.LONG },
							{"fDate",Hibernate.STRING},{"fTitle",Hibernate.STRING},
							{"uPkId",Hibernate.LONG},{"uName",Hibernate.STRING},{ "aReply", Hibernate.LONG },{"itemType",Hibernate.STRING}};
					
					return this.list(sql,null,scalaries);
				}
				
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#getOutBox(java.lang.Long, java.lang.Long)
	 */
	public List getOutBoxa(Long pOper,String bdate,String date,String zu) {
		Object[][] scalaries = {{"pkId",Hibernate.LONG},{"title",Hibernate.STRING},
		                        {"pDate",Hibernate.STRING},{"userName",Hibernate.STRING},
		                        { "aReply", Hibernate.LONG },{"itemType",Hibernate.STRING}};
		String sql = "select f.pkid as pkId,f.title as title,f.p_date as pDate,u.name as userName," +
					 " a.reply as aReply,f.itemtype as itemType " +
					 " from info_acceptoper a , info_filetransfer f,umg_user u ";// +" ";
		if (zu != null && zu.matches("reply_\\d"))
		{
			log.debug("��ѯ�ȴ����˻ظ����ʼ�!!!");
			// 20100514 piliang ������Ҫ���ʼ����͵�����ȥ����, ��ȥ����and f.itemtype<>'4'
			sql += " where a.mail_id = f.pkid and a.a_operid = u.pkid and a.status != '8'" + 
	            " and f.status <> '9' " + // ���˵���ɾ����ʼ�
	            " and f.status <> '7'" + // ���˵��ɾ����ʼ�
	            " and f.p_oper = "+ pOper;
			sql += "   and f.p_date >= '"+bdate+"' and f.p_date <= '"+date+"'" +
				" and a.reply=" + zu.split("_")[1] + 
				" order by f.p_date desc ,f.pkid desc,a.status desc";
		} else {
			sql += " where a.mail_id = f.pkid and f.itemtype<>'4' and a.a_operid = u.pkid and a.status != '8'"
				+ " and f.status <> '9' and f.p_oper = "+ pOper
            	+ " and f.status <> '7'"; // ���˵��ɾ����ʼ�
			if(zu.equals("2")){
				sql+="   and f.p_date >= '"+bdate+"' and f.p_date <= '"+date+"'" +
						" order by f.p_date desc ,f.pkid desc,a.status desc";
			}
			if(zu.equals("0")){
				sql+="    and f.p_date < '"+bdate+"00' order by f.p_date desc ,f.pkid desc,a.status desc";
			}
			if(zu.equals("1")){
				sql+= " and f.p_date > '"+bdate+"' and f.p_date < '"+date+"'  order by f.p_date desc ,f.pkid desc,a.status desc";
			}
			if(zu.equals("title")&&date.equals("posi")){
				sql += " order by f.title ,f.pkid desc,a.status desc";
			}
			if(zu.equals("title")&&date.equals("nega")){
				sql += "   order by f.title desc ,f.pkid desc,a.status desc";
			}
			if(zu.equals("addresser")&&date.equals("posi")){
				sql += "   order by u.name ,f.pkid desc,a.status desc";
			}
			if(zu.equals("addresser")&&date.equals("nega")){
				sql += "   order by u.name desc ,f.pkid desc,a.status desc";	
			}
		}
		return this.list(sql, null, scalaries);	
	}
	public List getOutBoxa(Long pOper,String date ,String status) {
		
		
		Object[][] scalaries = {{"pkId",Hibernate.LONG},{"title",Hibernate.STRING},
		                        {"pDate",Hibernate.STRING},{"userName",Hibernate.STRING},
		                        { "aReply", Hibernate.LONG },{"itemType",Hibernate.STRING},{"rmailId",Hibernate.LONG},{"aOperid",Hibernate.LONG}};
		String sql = "select s.pkId as pkId,s.title as title,s.pDate as pDate,u.name  as username,s.aReply as aReply,s.itemType as itemType,s.rmailId  as rmailId,s.a_operid as aOperid" +
				" from (select f.pkid as pkId,f.title as title,f.p_date as pDate,a.reply as aReply,f.itemtype as itemType,a.rmail_id as rmailId,a.a_operid as a_operid,a.status as status " +
				" from info_filetransfer f LEFT OUTER JOIN info_acceptoper a ON ( f.pkid = a.mail_id) " +
				" where f.status = '"+status+"' and f.p_oper = "+pOper+"     and f.itemtype is null";
						if(status.equals("8") || status.equals("9")){
							sql+=" and f.mailbox='1' ";
						}
				sql+=")s LEFT OUTER JOIN umg_user u ON (  s.a_operid = u.pkid ) ";
						if(date.equals("")){
							sql += "order by s.pDate desc ,s.pkid desc,s.status desc";
						}				
						else if(date.equals("posi")){
							sql += "order by s.title ,s.pkid desc,s.status desc";	
						}
						else if(date.equals("nega")){
							sql += "order by s.title desc ,s.pkid desc,s.status desc";	
						}
		log.info("��ѯ�ݸ������ʼ���SQLΪ��======== [" + sql + "]");
		return this.list(sql, null, scalaries);
		
	}
	public List getRecyclebin(Long pOper,String date ,String status) {
		Object[][] scalaries = {{"pkId",Hibernate.LONG},{"title",Hibernate.STRING},
		                        {"pDate",Hibernate.STRING},{"userName",Hibernate.STRING},{"itemType",Hibernate.STRING}};
		String sql = "select f.pkid as pkId,f.title as title,f.p_date as pDate,u.name as userName,f.itemtype as itemType " +
					 " from info_acceptoper a , info_filetransfer f,umg_user u "+
		             "where a.mail_id = f.pkid and a.a_operid = u.pkid and and f.status = '"+status+"' and a.status ="+status+" and f.p_oper = "+ pOper;// +" ";
						if(date.equals("")){
							sql += "order by f.p_date desc ,f.pkid desc,a.status desc";
						}				
						else if(date.equals("posi")){
							sql += "order by f.title ,f.pkid desc,a.status desc";	
						}
						else if(date.equals("nega")){
							sql += "order by f.title desc ,f.pkid desc,a.status desc";	
						}
						
		return this.list(sql, null, scalaries);
		
	}
	public List getInboxa(Long userId, String status) {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#updataMail(java.lang.Long, java.lang.String, java.lang.Long)
	 */
	public void selectUpdataMail(String pkIds,String status,Long userId) {
		
		String sql = "";
		Object[] scalaries = null;
		if(!pkIds.equals("0")){
			sql = "update info_acceptoper set status = ? where pkid in (?) and a_operid = ? and status = ?";
			scalaries = new Object[]{"9",pkIds,userId,status};
		}
		this.jdbcUpdate(sql,scalaries);
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkFileDAO#getUsers(java.lang.Long)
	 */
	public List getUsersID(Long pkId) {
		Object [][] scalaries = {{"userName",Hibernate.STRING},{"userId",Hibernate.LONG},{"pOper",Hibernate.LONG}};
		String sql = "select u.name as userName,a.a_operid as userId,f.p_oper as pOper from info_acceptoper a ,info_filetransfer f ,umg_user u " +
				" where a.mail_id = f.pkid and f.pkid = "+ pkId +" and u.pkid = a.a_operid ";
		List list = this.list(sql,null,scalaries);
		return list;
	}
	
	public void deleteAcceptoper(Long pkId ,String status,Long userId) {
		String sql = "";
		Object[] scalaries = null;
		if(userId.longValue() != 0){
			sql = "delete from info_acceptoper where mail_id = ? and a_operid = ? and status = ? ";
			scalaries = new Object[]{pkId,userId,status};
		}
		if(userId.longValue() == 0){
			sql = "delete from info_acceptoper where mail_id = ? and status = ? ";
			scalaries = new Object[]{pkId,status};
		}
		jdbcUpdate(sql,scalaries);
	}
	public List getOutBox(Long pOper,String status) {
		Object[][] scalaries = {{"pkId",Hibernate.LONG},{"title",Hibernate.STRING},
		                        {"pDate",Hibernate.STRING},{"userName",Hibernate.STRING},
		                        {"fPoper",Hibernate.LONG},{"apkId",Hibernate.LONG}};
		String sql = "select f.pkid as pkId,f.title as title,f.p_date as pDate,u.name as userName,f.p_oper as fPoper,a.pkid as apkId" +
					 " from info_acceptoper a , info_filetransfer f,umg_user u "+
		             "where a.mail_id = f.pkid and a.a_operid = u.pkid and f.p_oper = "+ pOper +" " +
		            " and f.status = '"+status+"' order by f.p_date desc , f.pkid desc";
		return this.list(sql, null, scalaries);
	}
	public List getOutBox(Long pOper,String status,String emailBox) {
		Object[][] scalaries = {{"pkId",Hibernate.LONG},{"title",Hibernate.STRING},
		                        {"pDate",Hibernate.STRING},{"userName",Hibernate.STRING},
		                        {"fPoper",Hibernate.LONG},{"apkId",Hibernate.LONG},{"itemType",Hibernate.STRING}};
		String sql = "select f.pkid as pkId,f.title as title,f.p_date as pDate,u.name as userName,f.p_oper as fPoper,a.pkid as apkId,f.itemtype as itemType " +
					 " from info_acceptoper a , info_filetransfer f,umg_user u "+
		             "where a.mail_id = f.pkid and a.a_operid = u.pkid and f.p_oper = "+ pOper +" " +
		            " and f.status = '"+status+"' and";
					if(emailBox.equals("0")){
						sql+=" f.mailbox="+emailBox;
					}else{
						sql+=" f.mailbox="+emailBox;
					}
						sql+=" order by f.p_date desc , f.pkid desc";
						
		return this.list(sql, null, scalaries);
	}
	public void returnUpdateMail(Long pkId,Long userId) {
			
			String sql = "update info_acceptoper set reply = ? where mail_id = ? and a_operid = ?";
			 Object[] scalaries = new Object[]{"3",pkId,userId};
			
			this.jdbcUpdate(sql,scalaries);
	}
	public void delOutBoxs(Long pkid) {
		String sql = "update info_filetransfer set status = ? where pkid = ? ";
		Object[] scalaries = new Object[]{"9",pkid};
		jdbcUpdate(sql,scalaries);
	}
	public void restoreOutBoxs(String status,Long pkid) {
		String sql = "update info_filetransfer set status = ? where pkid = ? ";
		Object[] scalaries = new Object[]{status,pkid};
		jdbcUpdate(sql,scalaries);
	}
	public void updateItemtype(String itemtype,Long pkid) {
		String sql = "update info_filetransfer set itemtype = ? where pkid = ? ";
		Object[] scalaries = new Object[]{itemtype,pkid};
		jdbcUpdate(sql,scalaries);
	}
	public void restoreinBoxs(String status,Long pkid) {
		String sql = "update info_acceptoper set status = ? where pkid = ? ";
		Object[] scalaries = new Object[]{status,pkid};
		jdbcUpdate(sql,scalaries);
	}
	public void updateFile(Long pkId,Long mailid){
		String sql="update code_file_repository set organ_id=? where pkid=?";
		Object [] scalaries = new Object[]{mailid,pkId};
		jdbcUpdate(sql,scalaries);
	}
	public List getAccepRmail(Long pkId) {
		Object [][] scalaries = {{"pkId",Hibernate.LONG},{"conId",Hibernate.LONG},
				                 {"sFileName",Hibernate.STRING},{"dFileName",Hibernate.STRING}};
		String sql = "select  from info_acceptoper a where  = "+pkId;
		List list = this.list(sql,null,scalaries);
		return list;
	}
	
	public List getInbox(Long userId,String status,String itemType) {
		//informix ��ݿ������һ���������ԭ���ֶ� �����������ж�   2012.7.18 xm
		      String sql="select a.pkid as aPkId,a.mail_id as aMailId,a.status as aStatus,f.pkid as fPkId,f.p_oper as fOper,"+
			             " f.p_date as fDate,f.title as fTitle,u.pkid as uPkId,u.name as uName ,f.itemtype as itemType ," +
			             "f.business_reportid as business_reportid,f.business_organcode as business_organcode,f.business_userid as business_userid,f.business_date as business_date";
		      if(SysConfig.DB == 'i'){          
		         sql+= ",f.unlock as fLock "+
		    		 "from info_acceptoper a,info_filetransfer f,umg_user u "+
		             "where f.pkid = a.mail_id and u.pkid = f.p_oper and a.a_operid = " + userId ; 
		      }else{
			        sql+=" from info_acceptoper a,info_filetransfer f,umg_user u "+
			             "where f.pkid = a.mail_id and u.pkid = f.p_oper and a.a_operid = " + userId ; 
		      }
               if(!itemType.equals("")){
            	   sql+=" and f.itemtype =  '" + itemType + "' " ;
               }
		       if(!status.equals("9") && !status.equals("")){
		    	   sql+=" and a.status =  '" + status + "' " ;
		       }
		       if(status.equals("")){
		    	   sql += " and a.status <> '9' and a.status <> '8' and a.status <> '7'";
		       }
		       if(status.equals("9")){
		    	   sql += "and a.status = '9' ";
		       }
		       sql += "order by f.p_date desc ,f.pkid desc,a.status desc";
		     
						if(SysConfig.DB == 'i'){
							 Object[][] scalaries = {{"aPkId", Hibernate.LONG },
										{ "aMailId", Hibernate.LONG }, { "aStatus", Hibernate.STRING },
										{ "fPkId", Hibernate.LONG }, { "fOper", Hibernate.LONG },
										{"fDate",Hibernate.STRING},{"fTitle",Hibernate.STRING},
										{"uPkId",Hibernate.LONG},{"uName",Hibernate.STRING},
										{"itemType",Hibernate.STRING},{"business_reportid",Hibernate.LONG},
										{"business_organcode",Hibernate.STRING},{"business_userid",Hibernate.LONG},{"business_date",Hibernate.STRING},{"fLock",Hibernate.STRING}};
							  return this.list(sql,null,scalaries);

						
						}else{
							  Object[][] scalaries = {{"aPkId", Hibernate.LONG },
										{ "aMailId", Hibernate.LONG }, { "aStatus", Hibernate.STRING },
										{ "fPkId", Hibernate.LONG }, { "fOper", Hibernate.LONG },
										{"fDate",Hibernate.STRING},{"fTitle",Hibernate.STRING},
										{"uPkId",Hibernate.LONG},{"uName",Hibernate.STRING},
										{"itemType",Hibernate.STRING},{"business_reportid",Hibernate.LONG},
										{"business_organcode",Hibernate.STRING},{"business_userid",Hibernate.LONG},{"business_date",Hibernate.STRING}};
							  return this.list(sql,null,scalaries);
						}
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.dao.WorkMailDAO#saveMail(com.krm.slsint.workfile.vo.FileTransferData)
	 */
//	public void saveMail(FileTransferData ft) {
//		Connection c = null;
//		try {
//			c = jdbc.getDataSource().getConnection();
//			Connection nc = DBDialect.getNativeConnection(c);
//			c.setAutoCommit(false);
//			String sql = "insert into info_filetransfer (pkid,p_oper,p_date,title,status,content) values " +
//					"("+DBDialect.genSequence("info_filetransfer_pkid_seq")+","+ft.getPOper()+"," +
//					"'"+ft.getDateDate()+"','"+ft.getTitle()+"','"+ft.getStatus()+"',?)";
//			PreparedStatement ps = nc.prepareStatement(sql);
//			DBDialect.setLongTextField(ps, 1, ft.getContent());
//			ps.execute();
//			ps.close();
//			c.commit();
//			c.setAutoCommit(true);
//			int pkid = jdbc.getJdbcTemplate().queryForInt(
//			"SELECT MAX(pkid) FROM info_filetransfer");// TODO
//			ft.setPkId(new Long(pkid));
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
//	
//	}
//	
//	/* (non-Javadoc)
//	 * @see com.krm.slsint.workfile.dao.WorkMailDAO#mailContent(java.lang.Long)
//	 */
//	public List mailContent(Long pkId) {
//		FileTransferData ft = null;
//		User u = null;
//		List mail = new ArrayList();
//		String sql = "select f.pkid as pkId, f.p_oper as Oper,"+
//                                            "f.p_date as aDate,"+
//                                            "f.title as title,"+
//                                            "f.content as content,"+
//                                            "u.name as userName "+
//                                            "from Info_filetransfer f ,umg_user u "+
//                                            "where f.status != 9 and f.pkid = "+pkId+" and  u.pkId = f.p_oper "+
//                                            "order by f.p_date";
//		Connection c = null;
//		Statement s = null;
//		ResultSet r = null;
//		try {
//			c = jdbc.getDataSource().getConnection();
//			s = c.createStatement();
//			r = s.executeQuery(sql);
//			if(r.next()){
//				ft = new FileTransferData();
//				u = new User();
//				ft.setPkId(new Long(r.getLong("pkid")));
//				ft.setPOper(new Long(r.getLong("oper")));
//				ft.setDateDate(r.getString("adate"));
//				ft.setTitle(r.getString("title"));
////				ft.setStatus(r.getString("status"));
//				String content = DBDialect.getLongTextField(r, "content");
//				ft.setContent(content);
//				u.setName(r.getString("username"));
//				mail.add(ft);
//				mail.add(u);
//			}
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
////		return this.list(sql,null,scalaries);
//		return mail;
//	}
//	
//	/* (non-Javadoc)
//	 * @see com.krm.slsint.workfile.dao.WorkMailDAO#viewInBoxContent(java.lang.Long)
//	 */
//	public List viewInBoxContent(Long pkId) {
//		FileTransferData ft = null;
//		User u = null;
//		List mail = new ArrayList();
//		String sql = "select f.pkid as pkId,"+
//					"f.p_oper as Oper, "+
//						"f.p_date as aDate,"+
//						"f.title as title,"+
//                        "f.content as content,"+
//                        "u.name as userName "+
//                        	"from  info_filetransfer f , info_acceptoper a ,umg_user u "+
//                        		"where a.pkid = "+pkId+" and a.status != 9 and a.mail_id = f.pkid "+
//                        			" and u.pkid = f.p_oper ";
//		Connection c = null;
//		Statement s = null;
//		ResultSet r = null;
//		try {
//			c = jdbc.getDataSource().getConnection();
//			s = c.createStatement();
//			r = s.executeQuery(sql);
//			if(r.next()){
//				ft = new FileTransferData();
//				u = new User();
//				ft.setPkId(new Long(r.getLong("pkid")));
//				ft.setPOper(new Long(r.getLong("oper")));
//				ft.setDateDate(r.getString("adate"));
//				ft.setTitle(r.getString("title"));
//				String content = DBDialect.getLongTextField(r, "content");
//				ft.setContent(content);
//				u.setName(r.getString("username"));
//				mail.add(ft);
//				mail.add(u);
//			}
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
//		
//		return mail;
//	}
	
	/**
	 * @see com.krm.slsint.workfile.dao.WorkMailDAO#getMailListToBeAnsweredByUser(java.lang.Long)
	 */
	public List getMailListToBeAnsweredByUser(Long userId)
	{
		String hql = "select accept.mailId from AcceptoperData accept, FileTransferData ftr where" +
				" accept.mailId=ftr.pkId and accept.aoperId=" + userId + 
				" and accept.reply=" + 1;
		return list(hql);
	}
	
	/**
	 * @see com.krm.slsint.workfile.dao.WorkMailDAO#getMailListToBeAnsweredToUser(java.lang.Long)
	 */
	public List getMailListToBeAnsweredToUser(Long userId)
	{
		String hql = "select distinct accept.mailId from AcceptoperData accept, FileTransferData ftr where " +
				" accept.mailId=ftr.pkId and " +
				" ftr.poper=" + userId + 
				" and accept.reply=" + 1 +
				" and accept.status <> '8' and ftr.status <> '9'"; // �ų���ɾ����ʼ�
		return list(hql);
	}
	/**
	 * ������4��ѯ��ͬ���������
	 * @param userId
	 * @param f_oper
	 * @param reportId
	 * @return
	 * add by ydw 20120416
	 */
	public List getSameContentMail(Long userId,Long f_oper,Long reportId) {
		Object[][] scalaries = {{"aPkId", Hibernate.LONG }};
		String sql = "select a.pkid as aPkId "+
		              "from info_acceptoper a,info_filetransfer f,umg_user u  "+
		               "where f.pkid = a.mail_id and u.pkid = f.p_oper and a.status='0'" +
		               " and f.BUSINESS_REPORTID="+reportId
		               +" and f.p_oper="+f_oper+" and a.a_operid = "+userId +" order by f.p_date desc , f.pkid desc";
		return this.list(sql,null,scalaries);
	}

}
