package com.krm.ps.model.datafill.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.upload.FormFile;

import com.krm.ps.model.xlsinit.vo.DataSet;
import com.krm.ps.model.xlsinit.vo.RowSet;


public class XLSBuild {
	public static DataSet constructData(FormFile in) throws Exception{
		Workbook wb = null;
		Sheet[] sheets = null;
		List data = new ArrayList();
		List title = new ArrayList();
		try {
			wb = Workbook.getWorkbook(in.getInputStream());
			sheets = wb.getSheets();
			int rows = sheets[0].getRows();
			int cols = sheets[0].getColumns();
			//取得excel数据的标题
			for (int i=0;i<cols;i++){
				title.add(sheets[0].getCell(i,0).getContents().trim());
			}
			//构造excel数据集
			for (int j=1;j<rows;j++){
				Map datas = new HashMap(cols);
				//第一列为空，则认为记录结束
				if (!"".equals(sheets[0].getCell(0,j).getContents().trim())&&
						sheets[0].getCell(0,j).getContents().trim()!=null){
					for (int k=0;k<cols;k++){
						String str = sheets[0].getCell(k,j).getContents().trim();
						datas.put(title.get(k),"".equals(str)?null:str);						
					}
					RowSet rowDatas = new RowSet(cols);
					rowDatas.setRowData(datas);
					data.add(rowDatas);
				}else{
					break;
				}				
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception("constructData failed!");
		} 
		return new DataSet(data);
	}
}
