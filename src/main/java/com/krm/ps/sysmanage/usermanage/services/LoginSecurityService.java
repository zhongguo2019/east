package com.krm.ps.sysmanage.usermanage.services;

import java.util.Date;
import java.util.Map;

public interface LoginSecurityService
{
	/**
	 * 在5min中内，判读登录三次失败的同一个IP的登录用户名。
	 * @param dateMap 数据结构为：key String ：IP信息+logonName登录名，value list：登录时间记录栈
	 * @param laterDate  比较的时间。
	 * @return String 返回登录用户名。
	 * */
	public String findWrongName(Map datamap,Date laterDate);
	
	/**
	 * 从数据库中提取密码最后更新的时间
	 * @param username 用户名
	 * @param password 密码
	 * @return Date 返回密码最后更新的时间
	 * */
	public Date getPasswordUpdateDate(String username,String password);
	
	/**
	 * 清楚错误登录记录
	 * @param datemap
	 * */
	public void clearLoginHistory(Map datamap);
	
	/**
	 * 计算用户更新密码，超过30天提示“请更新密码”
	 * @param nowdate 登录时间
	 * @param updatedate 从数据库中取出的最近更新密码的时间
	 * @return boolean
	 * */
	public boolean compareDate(Date nowdate,Date updatedate);
	
	/**
	 *查询用户输入登录在数据库中是否存在
	 *@param loginName 用户输入的登录名称
	 *@return boolean 
	 * */
	public boolean isUser(String loginName);
	
	/**
	 * 判断被锁住的用户是否经过了24小时。
	 * @param lockedDate 传入被锁住的时间。
	 * @return boloean 
	 * */
	public boolean isLocked(Date lockedDate);
}
