package com.krm.ps.sysmanage.usermanage.services.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.krm.ps.sysmanage.usermanage.dao.UserDAO;
import com.krm.ps.sysmanage.usermanage.services.LoginSecurityService;
import com.krm.ps.sysmanage.usermanage.vo.User;

public class LoginSecurityServiceImpl implements LoginSecurityService
{
	private UserDAO udao;
	

	public UserDAO getUdao()
	{
		return udao;
	}

	public void setUdao(UserDAO udao)
	{
		this.udao = udao;
	}

	public String findWrongName(Map datamap, Date laterDate)
	{
		String returnStr = "";
		//有2个数据，IP地址+登录名， 登录时间栈。
		Set set = datamap.keySet();
		Iterator itset = set.iterator();
		while(itset.hasNext())
		{
			String ipLogonName = (String) itset.next();
			List datelist = (List) datamap.get(ipLogonName);
			//调用 5min 记录3次登录错误的逻辑
			System.out.println(" ipLogonName = " + ipLogonName);
			
			int count = compareTime(datelist,laterDate);
				if(count >= 3)
				{
					System.out.println("登录错误的次数大于3次。");
					returnStr = ipLogonName ;
					break;
				}
		}
		return returnStr;
	}
	
	/**
	 * 5min内，记录三次登录的逻辑。
	 * */
	private int compareTime(List dateList,Date d)
	{
		int count = 0;
		//比较之前打印一些信息
		System.out.println("*********比较之前打印一些信息**********");
		System.out.println("时间堆栈 dateList = " + dateList);
		System.out.println("传入用来比较的时间 d = " + d);
		for(int i = 0 ; i<dateList.size();i++){
			Date date = (Date) dateList.get(i);
			long milli =  d.getTime();
			if(milli <= date.getTime())
			{
				//记录次数
				count++;
				System.out.println(" 记录次数 count = "+ count);
			}
			else
			{
				//如果用户登录错误后，又一次登录错误时间超过了5分钟，就重新记录信息
				//将该用户的登录时间栈去掉，记录当前登录时间。登录错误次数记录为1次。
				System.out.println("超过了5分钟！！");
				count = 1;
				System.out.println(" 记录次数 count = "+ count);
				dateList.clear();
				dateList.add(new Date(date.getTime()+5*60*1000));
				break;
			}
		}
		return count;
	}
	
	public Date getPasswordUpdateDate(String username, String password)
	{
		Date returnDate = null;
		User user = udao.getUser(username,password);
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		try
		{
			if(null == user.getCreatetime() || "".equals(user.getCreatetime()))
			{
				returnDate = df.parse("11111111");
			}else
			{
			returnDate = df.parse(user.getCreatetime());
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return returnDate;
	}

	public void clearLoginHistory(Map datamap)
	{
		datamap.clear();
	}

	public boolean compareDate(Date nowdate, Date updatedate)
	{
		//计算比较现在时间和密码最后一次更新时间的毫秒差
		long seconds = (nowdate.getTime() - updatedate.getTime())/1000;
		//对于密码过期的处理，时间不需要那么精确，不需要精确到 时分秒，
		//因为数据库中保存时间的字段没有精确到时分秒，避免修改数据库中的字段。
		long days = seconds/(24*3600);
		if(days >= 30)
		{
			return true;
		}
		return false;
	}
	
	public boolean isUser(String loginName)
	{
		User user = udao.getUser(loginName);
		if(user!=null)
		{
			return true;
		}
		return false;
	}
	
	public boolean isLocked(Date lockedDate)
	{
		Date nowDate = new Date();
		//计算被锁住的时间和再次登录的时间的 秒差值
		long seconds = (nowDate.getTime() - lockedDate.getTime())/1000;
		//24小时的秒数
		long second24 = 24*3600;
		if(seconds < second24)
		{
			return true;
		}
		return false;
	}




}
