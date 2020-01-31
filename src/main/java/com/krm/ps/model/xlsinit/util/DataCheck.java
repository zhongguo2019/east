package com.krm.ps.model.xlsinit.util;

import java.util.List;
import java.util.Map;

import com.krm.ps.model.xlsinit.constants.TypeConfig;
import com.krm.ps.model.xlsinit.vo.DataSet;
import com.krm.ps.model.xlsinit.vo.RowSet;
import com.krm.ps.model.xlsinit.vo.Table;
import com.krm.ps.model.xlsinit.vo.TableField;


public class DataCheck {
	public static boolean checkLegal(DataSet dataSet,List tables){		
		boolean flag = true;
		//对xml中定义的每张表，检查excel数据是否合法
		for (int i=0;i<tables.size();i++){
			Table tab = (Table)tables.get(i);			
			flag = checkTable(dataSet,tab.getField());
			if (!flag) break;
		}
		return flag;
	}
	private static boolean checkTable(DataSet dataSet,List field){
		boolean flag = true;
		List data = dataSet.getXlsData();
		for (int i=0;i<data.size();i++){
			RowSet row = (RowSet)data.get(i);
			Map rowData = row.getRowData();
			for (int j=0;j<field.size();j++){
				TableField f = (TableField)field.get(j);
				if (!f.is_pk()&&("".equals(f.get_constval())||f.get_constval()==null)){
					if (rowData.containsKey(f.get_map())){
						String str = null;
						if (rowData.get(f.get_map())!=null){
							str = (String)rowData.get(f.get_map());
						}
						
						if (f.is_drop()&&(str!=null)) str = str.substring(str.indexOf("&")+1);						
						int type = f.get_type();
						if (str!=null){
							if ((type==TypeConfig.INTEGER)&&!TypeChecker.isInteger(str)) {flag = false;break;}
							if ((type==TypeConfig.DOUBLE)&&!TypeChecker.isNumber(str)) {flag = false;break;}
							if ((type==TypeConfig.DATE)&&!TypeChecker.isDate(str)) {flag = false;break;}
							if ((type==TypeConfig.DATETIME)&&!TypeChecker.isDatetime(str)) {flag = false;break;}
						}
					}
				}
			}
			if (!flag) break;
		}
		return flag;
	}
}
