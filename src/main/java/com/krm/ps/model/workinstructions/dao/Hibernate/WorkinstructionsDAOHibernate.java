package com.krm.ps.model.workinstructions.dao.Hibernate;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Hibernate;
import org.hibernate.lob.SerializableBlob;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.workinstructions.dao.WorkinstructionsDAO;
import com.krm.ps.model.workinstructions.vo.TagsList;
import com.krm.ps.model.workinstructions.vo.Workaccessory;
import com.krm.ps.model.workinstructions.vo.Workcontext;
import com.krm.ps.model.workinstructions.vo.Workcontext1;
import com.krm.ps.model.workinstructions.vo.Workinstructions;
import com.krm.ps.util.SysConfig;







public class WorkinstructionsDAOHibernate extends BaseDAOHibernate implements WorkinstructionsDAO
{


	
	public List<Workinstructions> selectworkinstructiontree() {
		//                   0      1         2       3
		       String db = SysConfig.DATABASE;
		       String sql=null;
		       if("oracle".equalsIgnoreCase(db)) {
		    	    sql="select t.pkid,t.name,t.superid,t.type  from WORKINSTRUCTIONSTREE t  where t.status = 1 or t.status=2 connect by prior t.pkid=t.superid start with t.superid=0  order by px";
		       }else if("db2".equalsIgnoreCase(db)){
		    	   
		    	    sql= "with wks (pkid,name, superid,type) as(select pkid,name,superid,type from WORKINSTRUCTIONSTREE where superid=0 union all select w.pkid,w.name,w.superid,w.type from wks a ,WORKINSTRUCTIONSTREE w where a.pkid=w.superid ) select * from wks a";
		       }

		 		List list=list(sql, null, new Object[][] {
				{ "pkid",Hibernate.LONG },
				{ "name",Hibernate.STRING },
				{ "superid",Hibernate.LONG },
				//{ "status",Hibernate.INTEGER },
				//{ "px",Hibernate.INTEGER },
				{ "type",Hibernate.STRING },
				//{ "layer",Hibernate.INTEGER },
				});
		 		List<Workinstructions> list1=new ArrayList<Workinstructions>();
		 		Iterator it=list.iterator();
		 		while(it.hasNext()){
		 			Workinstructions w=new Workinstructions();
		 			Object[]o=(Object[])it.next();
		 			//System.out.println("11--"+o[0].toString());
		 			//System.out.println("22--"+o[1].toString());
		 			//System.out.println("33--"+o[2].toString());
		 			w.setPkid(Long.valueOf(o[0].toString()));
		 			w.setName(o[1].toString());
		 			w.setSuperid(Long.valueOf(o[2].toString()));
		 			//w.setType(o[3].toString());
		 			list1.add(w);
		 		}
				return list1;
		 		
		 		
	}

	@Override
	public void deleteid(long pkid) {
		String sql="delete from WORKINSTRUCTIONSTREE where pkid="+pkid;
		
		int a=jdbcUpdate(sql, null);
	}

	@Override
	public List selectworksuperid(long pkid) {
		String sql="select t.pkid,t.name,t.superid,t.type  from WORKINSTRUCTIONSTREE t  where (t.status = 1 or t.status=2)  connect by prior t.pkid=t.superid start with t.superid="+pkid;
		return list(sql, null, new Object[][] {
				{ "pkid",Hibernate.LONG },
				{ "name",Hibernate.STRING },
				{ "superid",Hibernate.LONG },
				//{ "status",Hibernate.INTEGER },
				//{ "px",Hibernate.INTEGER },
				{ "type",Hibernate.STRING },
				//{ "layer",Hibernate.INTEGER },
				});
	}

	@Override
	public void insertid(String name,long superid) {
		String sql="insert into WORKINSTRUCTIONSTREE (PKID,NAME,SUPERID,STATUS,PX,TYPE) values(WORKINSTRUCTIONSTREE_SEQ.NEXTVAL,'"+name+"',"+superid+",1,WORKINSTRUCTIONSTREE_SEQ.NEXTVAL,null)";
		
		int a=jdbcUpdate(sql, null);
	}

	@Override
	public void updateid(long pkid, String name) {
		String sql="update WORKINSTRUCTIONSTREE set name='"+name+"' where pkid="+pkid;
		
		int a=jdbcUpdate(sql, null);
	}

	@Override
	public List<Workcontext> selectcontext(long treeid) {
		String sql="select t.pkid,t.title,t.displaytitle,t.context from workcontext t where t.treeid="+treeid+" order by updatetime";
		
		
		return list(sql, null, null);
	}

	@Override
	public List<Workcontext> selectstatus2() {
		String sql="select t.pkid,t.title,t.displaytitle,t.context,t.begintime,t.updatetime,t.AUTHORID from workcontext t where t.status=2";
	//                       0       1        2               3          4         5	
		List list1=list(sql, null, new Object[][] {
				{ "pkid",Hibernate.LONG },
				{ "title",Hibernate.STRING },
				{ "displaytitle",Hibernate.STRING },
				//{ "status",Hibernate.INTEGER },
				//{ "px",Hibernate.INTEGER },
				{ "context",Hibernate.BLOB},
				{ "begintime",Hibernate.STRING },
				{ "updatetime",Hibernate.STRING },
				{ "AUTHORID",Hibernate.LONG},
				//{ "layer",Hibernate.INTEGER },
				});
		
		Iterator it=list1.iterator();
		List list=new ArrayList();
		while(it.hasNext()){
			Object[]o=(Object[])it.next();
			Workcontext w=new Workcontext();
			w.setPkid(Long.valueOf(o[0].toString()));
			w.setTitle(o[1].toString());
			//w.setDisplaytitle(o[2].toString());
			w.setBegintime(o[4].toString());
			w.setUpdatetime(o[5].toString());
			w.setAuthorid(Long.valueOf(o[6].toString()));
			//System.out.println(o[3]);
			byte[] bytes;
			
			BufferedInputStream bis = null;
			try {
				SerializableBlob blob = (SerializableBlob) o[3];
				bis = new BufferedInputStream(blob.getBinaryStream());
				bytes = new byte[(int)blob.length()];
				int len = bytes.length;
				int offest = 0;
				int read = 0;
				while(offest<len&&(read=bis.read(bytes, offest, len-offest))>0){
					offest+=read;
				}
			} catch (Exception e) {
				bytes = null;
				e.printStackTrace();
			} finally{
				if(bis!=null){
					try {
						bis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					bis = null;
				}
			}

			w.setContext(bytes);
			list.add(w);
		}
		return list;
	}

	@Override
	public void updatecontext(Workcontext w) {
		
		saveObject(w);
	}

	@Override
	public List selectinfo(long pkid) {
		String  sql="select  t.pkid,t.displaytitle,t.title,t.begintime,t.updatetime,t.status,t.treeid from WORKCONTEXT t where t.treeid="+pkid+" order by updatetime desc";
		//                    0           1          2        3              4          
		return list(sql, null, new Object[][] {
				{ "pkid",Hibernate.LONG },
				{ "displaytitle",Hibernate.STRING },
				{ "title",Hibernate.STRING },
				{ "begintime",Hibernate.STRING },
				{ "updatetime",Hibernate.STRING },
				{ "status",Hibernate.INTEGER },
				{ "treeid",Hibernate.LONG },
				//{ "status",Hibernate.INTEGER },
				//{ "px",Hibernate.INTEGER },
				//{ "layer",Hibernate.INTEGER },
				});
	}

	@Override
	public void saveinfo(Workcontext w) {
		saveObject(w);
		
	}

	@Override
	public void deleteinfo(long pkid) {
		String sql="delete WORKCONTEXT where pkid="+pkid;
		
		int a=jdbcUpdate(sql, null);
	}

	@Override
	public List<Workcontext> queryinfo(long pkid) {
		String sql="select t.pkid,t.title,t.displaytitle,t.context,t.begintime,t.updatetime,t.treeid,t.status,t.AUTHORID  from WORKCONTEXT t where t.pkid="+pkid;
		List list1=list(sql, null, new Object[][] {
				{ "pkid",Hibernate.LONG },
				{ "title",Hibernate.STRING },
				{ "displaytitle",Hibernate.STRING },
				//{ "status",Hibernate.INTEGER },
				//{ "px",Hibernate.INTEGER },
				{ "context",Hibernate.BLOB},
				{ "begintime",Hibernate.STRING },
				{ "updatetime",Hibernate.STRING },
				{ "treeid",Hibernate.LONG },
				{ "status",Hibernate.INTEGER },
				{ "AUTHORID",Hibernate.LONG },
				//{ "layer",Hibernate.INTEGER },
				});
		
		Iterator it=list1.iterator();
		List list=new ArrayList();
		while(it.hasNext()){
			Object[]o=(Object[])it.next();
			Workcontext w=new Workcontext();
			w.setPkid(Long.valueOf(o[0].toString()));
			w.setTitle(o[1].toString());
			//w.setDisplaytitle(o[2].toString());
			w.setBegintime(o[4].toString());
			w.setUpdatetime(o[5].toString());
			w.setTreeid(Long.valueOf(o[6].toString()));
			w.setStatus(Integer.parseInt(o[7].toString()));
			w.setAuthorid(Long.valueOf(o[8].toString()));
			//System.out.println(o[3]);
			byte[] bytes;
			
			BufferedInputStream bis = null;
			try {
				SerializableBlob blob = (SerializableBlob) o[3];
				bis = new BufferedInputStream(blob.getBinaryStream());
				bytes = new byte[(int)blob.length()];
				int len = bytes.length;
				int offest = 0;
				int read = 0;
				while(offest<len&&(read=bis.read(bytes, offest, len-offest))>0){
					offest+=read;
				}
			} catch (Exception e) {
				bytes = null;
				e.printStackTrace();
			} finally{
				if(bis!=null){
					try {
						bis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					bis = null;
				}
			}

			w.setContext(bytes);
			list.add(w);
		}
		return list;
	}

	@Override
	public void updatestatus(long pkid, int status,String updatetime) {
		String sql="update WORKCONTEXT set status="+status+" , updatetime='"+updatetime+"' where  pkid="+pkid;
		
		int a=jdbcUpdate(sql, null);
	}

	@Override
	public void updatestatus1(int status,int status2,String updatetime) {
		String sql;
		if(updatetime==null){
			 sql="update WORKCONTEXT set status="+status+" where status="+status2 ;
		}else{
			 sql="update WORKCONTEXT set status="+status+" , updatetime='"+updatetime+"' where status="+status2 ;
		}
		
		int a=jdbcUpdate(sql, null);
	}

	@Override
	public void saveaccessory(Workaccessory w) {
		saveObject(w);
		
	}
	
	
	public List queryaccessory(long pkid) {
		String sql="select t1.pkid, t1.filename,t1.houzhui,t1.file1 from WORKACCESSORY t1,WORKCONTEXT t where t.pkid=t1.infoid and t.pkid="+pkid ;
		System.out.println("sql=="+sql);
		return 	list(sql, null, new Object[][] {
				{"pkid",Hibernate.LONG },
				{"filename",Hibernate.STRING },
				{"houzhui",Hibernate.STRING },
				{ "file1",Hibernate.BLOB},

				//{ "layer",Hibernate.INTEGER },
				});
		

		
	}

	@Override
	public List queryaccessory1(long pkid) {
		String sql="select t1.pkid, t1.file1,t1.filename,t1.houzhui from WORKACCESSORY t1 where t1.pkid="+pkid;
		return list(sql, null, new Object[][] {
				{"pkid",Hibernate.LONG },
				{ "file1",Hibernate.BLOB},
				{ "filename",Hibernate.STRING},
				{ "houzhui",Hibernate.STRING},
				//{ "layer",Hibernate.INTEGER },
				});
		// List <Workaccessory>list=new ArrayList<Workaccessory>();

		// return list;
	}

	@Override
	public void deleteaccessory(long pkid) {
		String sql="delete WORKACCESSORY where pkid="+pkid;
		int a=jdbcUpdate(sql, null);
	}
	
	
	public List<Workcontext1> selectinfo(String title,String context, int pageNo,int pageSize){
		TagsList tag=new TagsList();
		int a=0; int b=0;
		String sql="";
		 sql=" select pkid,displaytitle,title,begintime,updatetime,status,treeid ,context,name from (select c.pkid,c.displaytitle, c.title,c.begintime,c.updatetime,c.status,c.treeid,c.context,c.name, rownum r from(select  t.pkid,t.displaytitle,t.title,t.begintime,t.updatetime,t.status,t.treeid ,t.context,u.name from WORKCONTEXT t ,UMG_USER u  " +
			"where 1=1 and u.pkid=t.AUTHORID"; // where ";
		if(title!=null&&!title.equals("")){
			sql+=" and  title like '%"+title+"%'";
			b=1;
		}
		if(context!=null&&!context.equals("")){
			sql+=" and dbms_lob.instr(context,utl_raw.cast_to_raw('"+context+"'),1,1)<>0";
			a=1;
		}
		
		
		
		sql+=" order by t.updatetime desc)c where rownum<="+pageNo*pageSize+") where  r>"+(pageNo-1)*pageSize+"order by updatetime desc";
		System.out.println("sql===="+sql);
		List list1= list(sql, null, new Object[][] {
				{ "pkid",Hibernate.LONG },
				{ "displaytitle",Hibernate.STRING },
				{ "title",Hibernate.STRING },
				{ "begintime",Hibernate.STRING },
				{ "updatetime",Hibernate.STRING },
				{ "status",Hibernate.INTEGER },
				{ "treeid",Hibernate.LONG },
				{ "context",Hibernate.BLOB},
				{ "name",Hibernate.STRING},
				//{ "status",Hibernate.INTEGER },
				//{ "px",Hibernate.INTEGER },
				//{ "layer",Hibernate.INTEGER },
				});
		Iterator it=list1.iterator();
		List list=new ArrayList();
		while(it.hasNext()){
			Object[]o=(Object[])it.next();
			Workcontext1 w=new Workcontext1();
			w.setPkid(Long.valueOf(o[0].toString()));
			w.setName(o[8].toString());
			w.setUpdatetime(o[4].toString());
			String title1="";
			if(b==1){
				
				if(o[2].toString().contains(title)){
					String[] title2=o[2].toString().replaceAll(title,",-").split(",");
					//System.out.println("biaotichangdu="+title2.length);
					for(int j=0;j<title2.length;j++){
						
						//title1=title1+"<font color='#000000' size='3'>"+title2[j]+"</font>"+"<font color='ff0000' size='3'>"+title+"</font>";
						
						if(j!=title2.length-1){
							title1=title1+"<font color='#000000' >"+title2[j]+"</font>"+"<font color='ff0000' >"+title+"</font>";
							
						}else{
		
								//System.out.println("2111");
						
								title1=title1+"<font color='#000000' >"+title2[j]+"</font>";
							}
							
						
					}
				}
				//System.out.println("biaoti=="+title1);
				w.setTitle(title1.replaceAll("-",""));
			}else{
				w.setTitle("<font color='#000000' >"+o[2].toString()+"</font>");
				//System.out.println("biaoti=="+title1);
			}
			
			
			//w.setDisplaytitle(o[2].toString());
			w.setBegintime(o[3].toString());
			w.setUpdatetime(o[4].toString());
			w.setTreeid(Long.valueOf(o[6].toString()));
			w.setStatus(Integer.parseInt(o[5].toString()));
			
		//	System.out.println(o[3]);
			byte[] bytes;
			
			BufferedInputStream bis = null;
			try {
				SerializableBlob blob = (SerializableBlob) o[7];
				bis = new BufferedInputStream(blob.getBinaryStream());
				bytes = new byte[(int)blob.length()];
				int len = bytes.length;
				int offest = 0;
				int read = 0;
				while(offest<len&&(read=bis.read(bytes, offest, len-offest))>0){
					offest+=read;
				}
			} catch (Exception e) {
				bytes = null;
				e.printStackTrace();
			} finally{
				if(bis!=null){
					try {
						bis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					bis = null;
				}
			}

			try {
				String context1=new String(bytes,"GBK").replaceAll("<.*?>", "");;
				//System.out.println(context1);
				//System.out.println(tag.subStringHTML(context1, 50));
				context1=tag.subStringHTML(context1, 50);
				String context3="";
				if(a==1){								  
					if(context1.contains(context)){
						String[] context2=context1.replaceAll(context,",").split(",");
						for(int j=0;j<context2.length;j++){
							if(j!=context2.length-1){
								context3=context3+context2[j]+"<font color='ff0000'>"+context+"</font>";
							}else{
								context3=context3+context2[j];
							}
						}
					}			
						w.setContext(context3);
					
					
				}else{
					w.setContext(context1);
				}
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			list.add(w);
		}
		return list;
		
	}

	public int selectinfonum(String title,String context){
		String sql="select  t.pkid,t.displaytitle,t.title,t.begintime,t.updatetime,t.status,t.treeid ,t.context from WORKCONTEXT t where 1=1 ";
		if(title!=null&&!title.equals("")){
			sql+=" and  title like '%"+title+"%'";
			
		}
		if(context!=null&&!context.equals("")){
			sql+=" and dbms_lob.instr(context,utl_raw.cast_to_raw('"+context+"'),1,1)<>0";
			
		}
		return list(sql, null, new Object[][] {
				{ "pkid",Hibernate.LONG },
				{ "displaytitle",Hibernate.STRING },
				{ "title",Hibernate.STRING },
				{ "begintime",Hibernate.STRING },
				{ "updatetime",Hibernate.STRING },
				{ "status",Hibernate.INTEGER },
				{ "treeid",Hibernate.LONG },
				{ "context",Hibernate.BLOB},
				//{ "status",Hibernate.INTEGER },
				//{ "px",Hibernate.INTEGER },
				//{ "layer",Hibernate.INTEGER },
				}).size();
		
	}

	@Override
	public String selectname(long userid) {
		String sql="select name, '' as aa from UMG_USER where PKID="+userid;
		
		List list= list(sql, null, new Object[][] {
				{ "name",Hibernate.STRING },
				{ "aa",Hibernate.STRING },
				});
			Iterator it=list.iterator();
			String name = "";
			while(it.hasNext()){
				Object[]o=(Object[])it.next();
				name=o[0].toString();
			}
			//System.out.println("0000000"+name);
			return name;
	}


	
	
	
}



