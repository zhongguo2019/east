package com.krm.ps.model.vo;

import java.util.List;
import java.util.Map;

import com.krm.ps.model.reportview.util.FillDataCollectCallback;
import com.krm.ps.sysmanage.reportdefine.vo.ReportFormat;


/**
 * ��ȡ����XML������
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author
 */
public class ReportXMLParam {

	//������ϵͳ����
	public int organSystem = 0;
	
	//��������
	public String organId;
	
	//�������
	public Long reportId;
	
	//����
	public String dataDate;
	
	//Ƶ��
	public String frequency;
	
	//�����ʽ����
	public ReportFormat reportFormat;
	
	//���ݼ������ص�����
	public FillDataCollectCallback fcc;
	
	//������ɫ��Ϣ����
	public boolean addColorInfo = false;
	
	//ѡ�����ڶ���
	public String selectDate;
	
	//����λ
	public String rptUnit;
	
	//Ԥȡ��������б�
	public List preRepIDList;
	
	//��ʽ���϶��󻺴�Map
	public Map dataCollectMap;
}
