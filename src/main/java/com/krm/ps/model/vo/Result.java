package com.krm.ps.model.vo;

/**
*
* <p>类名称:			Result
* <p>功能:				函数的返回值信息
* <p>创建时间:			2006.4.16
* @author				nxd
* @version			v1.1 2006.4.16
* @since 修改记录：――――――――――――――――――――――――――
* <p>修改时间			修改者			修改的方法			修改的原因
*/

public class Result {
	/**
	 * 错误信息描述
	 */
	public static final Result ERR_THREAD_EXIST = new Result(-1001,"数据生成异步线程已经启动，不能同时启动多个生成线程。");
	public static final Result ERR_THREAD_NOTEXIST = new Result(-1002,"数据生成异步线程没有启动或已经停止。");
	public static final Result ERR_THREAD_ASYNC = new Result(-1003,"数据生成不支持异步操作。");
	public static final Result ERR_DATA_NOTFOUND = new Result(-9998,"数据不存在。");
	public static final Result ERR_BREAK = new Result(-9999,"生成过程出现错误，系统中断处理。");
	/**
	 * 返回值id
	 * 当id=0时表示返回成功，否则为失败。
	 */
	private int id = 0;
	
	/**
	 * 返回信息
	 * 当成功时message为空，否则为出错信息。
	 */
	private String message = "";
	
	/**
     * <p>方法的名称及参数:	Result()
     * <p>方法的描述:		默认构造函数
	 */
	public Result()
	{
		
	}
	
	/**
     * <p>方法的名称及参数:	Result(int id,String message)
     * <p>方法的描述:		构造函数
     * @param				id 返回值id
     * @param				message 出错信息
     * @return				Result 返回值对象
     */
	public Result(int id,String message)
	{
		this.id = id;
		this.message = message;
	}
	
	/**
     * <p>方法的名称及参数:	public void setId(int id)
     * <p>方法的描述:		设置返回值id
     * @param				id 返回值id
     */
	public void setId(int id)
	{
		this.id = id;
	}
	
	/**
     * <p>方法的名称及参数:	public int getId()
     * <p>方法的描述:		得到返回值id
     * @return				int 返回值id
     */
	public int getId()
	{
		return id;
	}
	
	/**
     * <p>方法的名称及参数:	public void setMessage(String message)
     * <p>方法的描述:		设置出错信息
     * @param				message 出错信息
     */
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	/**
     * <p>方法的名称及参数:	public String getMessage()
     * <p>方法的描述:		得到出错信息
     * @return				String 返回值信息
     */
	public String getMessage()
	{
		return message;
	}
	
	/**
     * <p>方法的名称及参数:	public boolean isSuccess()
     * <p>方法的描述:		是否成功
     * @return				boolean true:成功；false:失败
     */
	public boolean isSuccess()
	{
		return (id == 0);
	}
	
	/**
     * <p>方法的名称及参数:	public boolean isFailed()
     * <p>方法的描述:		是否失败
     * @return				boolean true:失败；false:成功
     */
	public boolean isFailed()
	{
		return (id != 0);
	}
}
