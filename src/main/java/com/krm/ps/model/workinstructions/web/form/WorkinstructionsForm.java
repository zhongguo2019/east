package com.krm.ps.model.workinstructions.web.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.krm.ps.framework.common.web.form.BaseForm;



/**
 * 
 * @struts.form name="workinstructionsForm"
 */
public class WorkinstructionsForm  extends BaseForm{

	 private List<FormFile> files = new ArrayList<FormFile>();

	    public FormFile getFile(int i) {
	        return files.get(i);
	    }

	    public void setFile(int i,FormFile file) {
	        files.add(file);
	    }
	    
	    public List getFiles(){
	        return files;
	    }

	
	

}
