<%@ include file="/ps/framework/common/taglibs.jsp"%>
<html>
<head>
<title><fmt:message key="common.upload.title" /></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/style/default.css'/>"/> 
<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
<script type="text/javascript">
<!--
function onSelectFile()
{
	var selectObject = uploadForm.selectFiles;
	if(selectObject.selectedIndex >= 0)
	{
		uploadForm.updatedTitle.value = selectObject.options[selectObject.selectedIndex].text;
	}
}

function onUpdateTitle()
   {
   	var selectObject = uploadForm.selectFiles;
	if(uploadForm.updatedTitle.value != "" && selectObject.selectedIndex >= 0)
	{
		selectObject.options[selectObject.selectedIndex].text = uploadForm.updatedTitle.value;
	}
	updateFields();
	uploadForm.updatedTitle.focus();
   }
 
function onUpItem()
{
	var selectObject = uploadForm.selectFiles;
	var i = 0;
	while(i < selectObject.length && selectObject.options[i].selected)
	{
		i++;
	}	
	for(i++; i < selectObject.length; i++)
	{
		if (selectObject.options[i].selected)
		{
			swapOption(selectObject.options[i], selectObject.options[i-1]);
		}
	}
	updateFields();
}

function onDownItem()
{
	var selectObject = uploadForm.selectFiles;
	var i = selectObject.length-1;
	while(i >= 0 && selectObject.options[i].selected)
	{
		i--;
	}	
	for(i--; i >= 0; i--)
	{
		if (selectObject.options[i].selected)
		{
			swapOption(selectObject.options[i], selectObject.options[i+1]);
		}
	}
	updateFields();
}		

function onDeleteItem()
{
	var selectObject = uploadForm.selectFiles;
	var selectedIds = "";
	var selectedTitles = "";
	var unSelectedIds = "";
	var unSelectedTitles = "";
	for(i=0;i<selectObject.length;i++)
	{
		if(selectObject.options[i].selected)
		{
			selectedIds += "," + selectObject.options[i].value;
			selectedTitles += "," + selectObject.options[i].text;
		}
		else
		{
			unSelectedIds += "," + selectObject.options[i].value;
			unSelectedTitles += "," + selectObject.options[i].text;
		}
	}
	if(selectedIds != "")
	{
		selectedIds = selectedIds.substr(1);
	}
	if(selectedTitles != "")
	{
		selectedTitles = selectedTitles.substr(1);
	}
	if(unSelectedIds != "")
	{
		unSelectedIds = unSelectedIds.substr(1);
	}
	if(unSelectedTitles != "")
	{
		unSelectedTitles = unSelectedTitles.substr(1);
	}
	if(confirm('[' + selectedTitles + ']<fmt:message key="common.upload.javascript.confirm.delete" />'))
	{
		uploadForm.selectedIds.value = selectedIds;
		uploadForm.accessoryIds.value = unSelectedIds;
		uploadForm.accessoryTitles.value = unSelectedTitles;
		if(window.opener.updateFields)
		{
			window.opener.updateFields(uploadForm.accessoryIds.value, uploadForm.accessoryTitles.value, "<c:out value='${uploadForm.funArg}'/>");
		}
		uploadForm.onsubmit = null;
		return true;	
	}
	return false;			
} 		

function swapOption(opt1, opt2)
{
	tempValue = opt2.value;
	tempText = opt2.text;
	tempSelected = opt2.selected;
	opt2.value = opt1.value;
	opt2.text = opt1.text;
	opt2.selected = opt1.selected;
	opt1.value = tempValue;
	opt1.text = tempText;
	opt1.selected = tempSelected;
}
			
function init()
{
	if(uploadForm.accessoryIds.value == "")
	{
		if(window.opener.getAccessoryIds)
		{
			uploadForm.accessoryIds.value = window.opener.getAccessoryIds("<c:out value='${uploadForm.funArg}'/>");
		}
	}
	if(uploadForm.accessoryTitles.value == "")
	{
		if(window.opener.getAccessoryTitles)
		{
			uploadForm.accessoryTitles.value = window.opener.getAccessoryTitles("<c:out value='${uploadForm.funArg}'/>");
		}
	}
	if(window.opener.getFileDefaultTitle)
	{
		uploadForm.newTitle.value = window.opener.getFileDefaultTitle("<c:out value='${uploadForm.funArg}'/>");
		uploadForm.newTitle.readOnly = true;
	}
	refreshList();
}

function refreshList()
{	
	ids = uploadForm.accessoryIds.value;
   	titles = uploadForm.accessoryTitles.value;
   	if(ids != "" && titles != "")
   	{
   		var values = ids.split(",");
   		var texts = titles.split(",");
   		var selectObject = uploadForm.selectFiles;
   		for(i = 0; i < values.length; i++)
   		{
   			selectObject.options.add(new Option(texts[i], values[i]));
   		}			   		
   	}
   	if(window.opener.updateFields)
   	{
   		window.opener.updateFields(ids, titles, "<c:out value='${uploadForm.funArg}'/>", "<c:out value='${uploadForm.fileURL}'/>");
   	}
   	uploadForm.newTitle.focus();
}	

function updateFields()
{
	ids = "";
	titles = "";
	var selectObject = uploadForm.selectFiles;
	for(i = 0; i < selectObject.length; i++)
	{
		ids += "," + selectObject.options[i].value;
		titles += "," + selectObject.options[i].text;
	}
	if(ids != "" && titles != "")
	{
		ids = ids.substr(1);
		titles = titles.substr(1);
	}
	uploadForm.accessoryIds.value = ids;
	uploadForm.accessoryTitles.value = titles;
	if(window.opener.updateFields)
	{
		window.opener.updateFields(ids, titles, "<c:out value='${uploadForm.funArg}'/>", "<c:out value='${uploadForm.fileURL}'/>");
	}
}			
//-->
</script>
</head>
<html:form action="uploadFile" method="post" styleId="uploadForm" 
	enctype="multipart/form-data" onsubmit="return validateUploadForm(this)">
	<html:hidden property="accessoryClassName"/>
	<html:hidden property="accessoryIds"/>	
	<html:hidden property="accessoryTitles"/>
	<html:hidden property="selectedIds"/>
	<html:hidden property="entityId"/>
	<html:hidden property="entityKind"/>
	<html:hidden property="infoStructId"/>
	<html:hidden property="status"/>
	<html:hidden property="funArg"/>
	
	<table width="100%" align="center" cellpadding="0" cellspacing="0">
		<tr>
			<td style="width: 600px;" colspan="3">
				<p><span id="progress" style="display:none"><fmt:message key="common.upload.prompt.progress" /></span>
				<fmt:message key="common.upload.prompt.step1" />
			</td>
		</tr>
		<tr>
			<td style="width: 20%;" nowrap>
				<fmt:message key="common.upload.prompt.title" />
			</td>
			<td style="width: 80%;" colspan="2">
				<html:text property="newTitle" style="width: 85%;"/>
			</td>
		</tr>
		<tr>
			<td><fmt:message key="common.upload.prompt.file" /></td>
			<td colspan="2">
				<input name="e" type="text" style="width: 84%;" disabled><input name="newFile" type="file"  style="width:0px"  onChange="e.value=this.value">
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<fmt:message key="common.upload.prompt.step2" /> 
				<html:submit property="method.upload"><fmt:message key="common.upload.button.upload" /></html:submit>
			</td>
		</tr>
		<tr>
			<td style="width: 20%;">
				<fmt:message key="common.upload.prompt.attached" />
			</td>
			<td style="width: 70%;">
				<select id="selectFiles" name="selectFiles"  multiple="true"  style="width: 96%; height: 150px;" onchange="onSelectFile();"/>
			</td>
			<td style="width: 10%;">
				<br>
				<html:button property="up"  style="width: 65px;" onclick="onUpItem();">
					<fmt:message key="common.upload.button.up" />					
				</html:button>
				<br><br>
				<html:button property="down"  style="width: 65px;" onclick="onDownItem();">
					<fmt:message key="common.upload.button.down" />					
				</html:button>
				<br><br>
				<html:submit property="method.delete"  style="width: 65px;" onclick="return onDeleteItem();">
					<fmt:message key="common.upload.button.delete" />					
				</html:submit>
				<br><br>									
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<input type="text" id="updatedTitle" style="width: 75%;"/>
				<html:button property="updateTitle"  style="width: 20%;" onclick="onUpdateTitle();">
					<fmt:message key="common.upload.button.updatetitle" />					
				</html:button>
			</td>
			<td></td>
		</tr>
		<tr><td colspan="3"><fmt:message key="common.upload.prompt.continue" /></td></tr>
		<tr>
			<td colspan="3">
				<fmt:message key="common.upload.prompt.step3" />
				<html:button property="finish"  style="width: 65px;" onclick="window.close();">
					<fmt:message key="common.upload.button.finish" />					
				</html:button>
			</td>
		</tr>
	</table>
</html:form>
</html>
<html:javascript formName="uploadForm" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/ps/scripts/validator.jsp"/>"></script>
<script type="text/javascript">init();</script>