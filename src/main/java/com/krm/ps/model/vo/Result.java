package com.krm.ps.model.vo;

/**
*
* <p>������:			Result
* <p>����:				�����ķ���ֵ��Ϣ
* <p>����ʱ��:			2006.4.16
* @author				nxd
* @version			v1.1 2006.4.16
* @since �޸ļ�¼���D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D
* <p>�޸�ʱ��			�޸���			�޸ĵķ���			�޸ĵ�ԭ��
*/

public class Result {
	/**
	 * ������Ϣ����
	 */
	public static final Result ERR_THREAD_EXIST = new Result(-1001,"���������첽�߳��Ѿ�����������ͬʱ������������̡߳�");
	public static final Result ERR_THREAD_NOTEXIST = new Result(-1002,"���������첽�߳�û���������Ѿ�ֹͣ��");
	public static final Result ERR_THREAD_ASYNC = new Result(-1003,"�������ɲ�֧���첽������");
	public static final Result ERR_DATA_NOTFOUND = new Result(-9998,"���ݲ����ڡ�");
	public static final Result ERR_BREAK = new Result(-9999,"���ɹ��̳��ִ���ϵͳ�жϴ���");
	/**
	 * ����ֵid
	 * ��id=0ʱ��ʾ���سɹ�������Ϊʧ�ܡ�
	 */
	private int id = 0;
	
	/**
	 * ������Ϣ
	 * ���ɹ�ʱmessageΪ�գ�����Ϊ������Ϣ��
	 */
	private String message = "";
	
	/**
     * <p>���������Ƽ�����:	Result()
     * <p>����������:		Ĭ�Ϲ��캯��
	 */
	public Result()
	{
		
	}
	
	/**
     * <p>���������Ƽ�����:	Result(int id,String message)
     * <p>����������:		���캯��
     * @param				id ����ֵid
     * @param				message ������Ϣ
     * @return				Result ����ֵ����
     */
	public Result(int id,String message)
	{
		this.id = id;
		this.message = message;
	}
	
	/**
     * <p>���������Ƽ�����:	public void setId(int id)
     * <p>����������:		���÷���ֵid
     * @param				id ����ֵid
     */
	public void setId(int id)
	{
		this.id = id;
	}
	
	/**
     * <p>���������Ƽ�����:	public int getId()
     * <p>����������:		�õ�����ֵid
     * @return				int ����ֵid
     */
	public int getId()
	{
		return id;
	}
	
	/**
     * <p>���������Ƽ�����:	public void setMessage(String message)
     * <p>����������:		���ó�����Ϣ
     * @param				message ������Ϣ
     */
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	/**
     * <p>���������Ƽ�����:	public String getMessage()
     * <p>����������:		�õ�������Ϣ
     * @return				String ����ֵ��Ϣ
     */
	public String getMessage()
	{
		return message;
	}
	
	/**
     * <p>���������Ƽ�����:	public boolean isSuccess()
     * <p>����������:		�Ƿ�ɹ�
     * @return				boolean true:�ɹ���false:ʧ��
     */
	public boolean isSuccess()
	{
		return (id == 0);
	}
	
	/**
     * <p>���������Ƽ�����:	public boolean isFailed()
     * <p>����������:		�Ƿ�ʧ��
     * @return				boolean true:ʧ�ܣ�false:�ɹ�
     */
	public boolean isFailed()
	{
		return (id != 0);
	}
}
