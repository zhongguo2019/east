package com.krm.ps.model.vo;

public class ReportComType {
	 
	private Long pkid;        //����
    private String typename ;  //����id
    private String flag; ////�����ı�־  0�ɼ��� 1Ŀ���2����Ԥ��
    private String systemcode;//ϵͳ��ʶ  1���б�׼��3�ͻ�����
     
	public ReportComType() {
		super();
	}

	
	
	public String getSystemcode() {
		return systemcode;
	}



	public void setSystemcode(String systemcode) {
		this.systemcode = systemcode;
	}



	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	

}
