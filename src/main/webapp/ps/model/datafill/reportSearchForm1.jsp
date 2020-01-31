<!-- /ps/model/datafill/reportSearchForm1. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<%
   User user = (User)request.getSession().getAttribute("user");

   String orgId = user.getOrganId();   
%>
<c:set var="flagOrgan" value="1"/>
<c:set var="flagReport" value="1"/>
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>

	<script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
</head>

<body onload=" forbiddon()" class="easyui-layout">
<div data-options="region:'center',border:false,title:' <fmt:message key="navigation.dataFill.dataFill1"/> '" style="width:100%;padding:0px;overflow-y:hidden ">
<div class="pbox">
<fmt:message key="report.tips1"/>
</div>
 <script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}

$(document).ready(function(){
	var flagOrgan = <c:out value='${flagOrgan}' />
	var flagReport =<c:out value='${flagReport}' />
	if(flagOrgan==2){
		$("#flagOrgan1").remove();
	}else{
		$("#flagOrgan2").remove();
	} 
	if(flagReport==2){//1单选，2多选
		$("#flagReport1").remove();
	}else{
		$("#flagReport2").remove();
	} 
	  getOrganTreeXML();
	  getrepTreeXML();
});  

function getOrganTreeXML(){//机构树
	 $.post("treeAction.do?method=getOrganTreeXML",function(data){
			var treeXml = eval(data)[0].treeXml;
			$('#combotreeOrganTree').combotree('loadData', treeXml);
		}); 
}

function getrepTreeXML(){//报表树
	 $.post("reportView.do?method=repTreeAjax&levelFlag=0",function(data){
			var reportXml = eval(data)[0].reportXml;
			$('#combotreeRepTree').combotree('loadData', reportXml);
		}); 
}
</script>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%"  valign="top">
    <br>
    
	<html:form action="reportView.do?method=inputExtraCondition" method="post" onsubmit="return reportCheck()" >
		<table width="90%" border="0"  cellSpacing="1" cellPadding="3" class="TableBGColor">
		<input type="hidden" name="reportId" id="reportId" value="10000"/>
		<input type="hidden" name="reportName"/>
		<input type="hidden" name="organId" value="<c:out value='${organId}' />"/>
		<input type="hidden" name="organName"/>
		<input type="hidden" name="unitCode" value="<c:out value='${unitCode}' />"/>
		<c:if test="${param.method=='enterViewSearch'}">
            <input type="hidden" name="mode" value="display"/>
		</c:if>
       	<c:if test="${param.method=='enterInputSearch'}">
	        <input type="hidden" name="mode" value="edit"/>
		</c:if>
		<tr>
				<td width="120" align="right" nowrap >
					<fmt:message key="reportview.form.day"/><fmt:message key="reportview.form.qi"/>
				</td>
				<td >
					<input type="hidden" name="showret" value="yes">
					  <input id="dataDate" name="dataDate" type="text" 
									value="<c:out value='${dataDate}'/>" 
									style="width:150;" readonly="true" onClick="WdatePicker()"/>
				</td>
		  </tr>
			<tr>
				<td   align="right"><fmt:message key="select.organ"/></td>
				<td >
					<div id="flagOrgan1">
					<input id="combotreeOrganTree" class="easyui-combotree"  value="<%=orgId %>"  style="width:380px"/>
				</div>
				<div id="flagOrgan2">
					<input id="combotreeOrganTree" class="easyui-combotree" multiple value="<%=orgId %>"  style="width:380px"/>
				</div>
			</tr>	
			<tr>
				<td align="right" nowrap ><fmt:message key="standard.dataFill.templateName"/></td>
				<td >
					<div id="flagReport1">
					<input id="combotreeRepTree" class="easyui-combotree"  value="10000"   style="width:380px"/>
				</div>
				<div id="flagReport2">
					<input id="combotreeRepTree" class="easyui-combotree" value="10000" multiple style="width:380px">
				</div>		
				</td>
			</tr>
			
			<tr>
			
				<td  align="right" nowrap ></td>
					<td >
					<a href="#" class="easyui-linkbutton c6" data-options="iconCls:'icon-import'" onclick="importDataFromXls('_self','yes')"><fmt:message key="standard.dataFill.importData"/></a>
					<a href="#" class="easyui-linkbutton c6" data-options="iconCls:'icon-input'" onclick="importDataOne('_self','yes')"><fmt:message key="standard.dataFill.importDataOne"/></a>
					<!--  <input type="button" value="<fmt:message key="standard.dataFill.importData"/>" style="width:80;" onclick="importDataFromXls('_self','yes')">
					 	<input name="Input" type="button" value="<fmt:message key="standard.dataFill.importDataOne"/>" style="width:80;" onclick="importDataOne('_self','yes')">-->
					 		</td>
			</tr>
			<tr>
				<td width="50" height="16" colspan="2" nowrap class="FormBottom"></td>
			</tr>			
		</table>
	</html:form>
	<div id="users" style="display:none"></div>	
</td>
</tr>
</table>
</div>
</body>

<script type="text/javascript">

var df=document.reportViewForm;


function importDataOne(tg,sr){
	/* var oReportId = df.reportId;
    var vReportId = oReportId.value; */
    var vReportId = $("#combotreeRepTree").combotree("getValue");
	var organCode = $("#combotreeOrganTree").combotree("getValue");
	if (vReportId==""||vReportId == null){
       alert("<fmt:message key="js.validate.modeltarget"/>");
       return false;
	}
	if (vReportId >= 10000){
	alert("<fmt:message key="js.validate.modeltarget"/>");
		return false;
	}
	
		df.action="dataFill.do?method=importDataOne&reportId="+vReportId+"&organId="+organCode;
		df.target=tg;
		df.showret.value=sr;
		df.submit();
	
}





function importDataFromXls(tg,sr){
	/* oReportId = df.reportId;
	vReportId = oReportId.value; */
	var vReportId = $("#combotreeRepTree").combotree("getValue");
	var organCode = $("#combotreeOrganTree").combotree("getValue");
	 if(vReportId==10000||vReportId==""||vReportId == null)
     {
      alert('<fmt:message key="js.validate.modeltarget"/>!');
      return false;
     }
    else if(vReportId>10000)
     {
     alert('<fmt:message key="place.select"/>'+document.getElementById('reportName').value+'<fmt:message key="view.xzjtmb"/>!');
     return false;
     }
	 
	 url="dataFill.do?method=importDataFromXls&reportId="+vReportId+"&organId="+organCode+"&dataDate="+df.dataDate.value;
	 
	 window.parent.window.OpenPanel(url,'600','200','<fmt:message key="historydata.filecount"/>');
	/* df.action="dataFill.do?method=importDataFromXls&reportId="+vReportId+"&organId="+organCode+"&dataDate="+df.dataDate.value;
	<c:if test="${unitCodeFlag == 1}">
		setUnitCode();
	</c:if>
	if(tg==="_blank"){
		
		var viewOrganId = $("#combotreeOrganTree").combotree("getValue");
		var viewDataDate = df.dataDate.value;
		
		var viewReportId = $("#combotreeRepTree").combotree("getValue");
		tg = viewReportId+viewOrganId+viewDataDate+"_window";
	}
	df.target=tg;
	df.submit(); */
}


function addDataOne(tg,sr){
	/* oReportId = df.reportId;
	vReportId = oReportId.value; */
	var vReportId = $("#combotreeRepTree").combotree("getValue");
	var organCode = $("#combotreeOrganTree").combotree("getValue");
	 if(vReportId==10000||vReportId==""||vReportId == null)
     {
      alert('<fmt:message key="js.validate.modeltarget"/>!');
      return false;
     }
    else if(vReportId>10000)
     {
     alert('<fmt:message key="place.select"/>'+document.getElementById('reportName').value+'<fmt:message key="view.xzjtmb"/>!');
     return false;
     }
	
	
	df.action="dataFill.do?method=editReportDataEntryForDataInput&openmode="+tg+"&canEdit=<c:out value='${canEdit}'/>&levelFlag=0&reportId="+vReportId+"&organCode="+organCode;
	<c:if test="${unitCodeFlag == 1}">
	setUnitCode();
	</c:if>
	if(tg==="_blank"){
		/* var viewOrganId = df.organId.value; */
		var viewOrganId = $("#combotreeOrganTree").combotree("getValue");
		var viewDataDate = df.dataDate.value;
		/* var viewReportId = df.reportId.value; */
		var viewReportId = $("#combotreeRepTree").combotree("getValue");
		tg = viewReportId+viewOrganId+viewDataDate+"_window";
	}
	df.target=tg;
	df.showret.value=sr;
	df.submit();
}

function setUnitCode(){
	var listObj = document.all.selUnit;
	document.all.unitCode.value = listObj.options[listObj.selectedIndex].value;
}

/* function repTree(){
    var oDate = document.reportViewForm.dataDate;
	var vDate = oDate.value;
	var oOrganId = document.reportViewForm.organId;
	var vOrganId = oOrganId.value;	
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId+"&levelFlag=0";
	
	if (window.XMLHttpRequest){
		req = new XMLHttpRequest();
	}else if (window.ActiveXObject){
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (req){
		req.open("GET", url, true);
		req.send();
	}	
} 

function changeOrg(){
    var oDate = document.reportViewForm.dataDate;
	var vDate = oDate.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/treeAction.do?method=getOrganTreeXML&date="+vDate;

	//1104 system use url:slsint...
	//var url = "<c:out value="${hostPrefix}"/>/slsint/reportView.do?method=orgTree&orgDate="+vDate;
	//bill exchange system use url: slspj...
	//var url = "<c:out value="${hostPrefix}"/>/slspj/reportView.do?method=orgTree&orgDate="+vDate;

	document.all.objTree1.SetXMLPath(url);
   	document.all.objTree1.Refresh();	
}
*/


function reportCheck(){
	/* oReportId = df.reportId;
    vReportId = oReportId.value; */
    var vReportId = $("#combotreeRepTree").combotree("getValue");
	if (vReportId==""||vReportId == null){
       alert("<fmt:message key="js.validate.report"/>");
       return false;
	}
	if (vReportId >= 10000){
		return false;
	}
}

/* function change(){
	changeOrg();
	changeRep();
} 

 function changeTree1(){
	changeRep();
} 

 function changeRep(){
    var oDate = document.reportViewForm.dataDate;
	var vDate = oDate.value;
	var oOrganId = document.reportViewForm.organId;
	var vOrganId = oOrganId.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId+"&levelFlag=0";	
	<c:if test="${systemName=='1104'}">
		var url = "<c:out value="${hostPrefix}"/>/slsint/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId+"&levelFlag=0";
	</c:if>
	<c:if test="${systemName=='billExchange'}">
		var url = "<c:out value="${hostPrefix}"/>/slspj/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId+"&levelFlag=0";
	</c:if>
	document.all.objTree2.SetXMLPath(url);
   	document.all.objTree2.Refresh();	
} 


function setReportUnit(){
	<c:if test="${unitCodeFlag == 1}">
	var ajaxOption = {method:'get',onSuccess:setReportUnitResponse};
	var url = "<c:out value='${hostPrefix}'/><c:url value='/reportView.do?method=getReportUnit&reportID='/>" + document.all.objTree2.getCurrentItem();
	var doAjax = new Ajax.Request(url,ajaxOption);
	</c:if>
}

function setReportUnitResponse(ajaxRequest){
	var unitCode = ajaxRequest.responseText;
	if (unitCode == "0"){
		return;
	}
	var selObj = document.all.selUnit;
	for (var i = 0 ; i < selObj.options.length ; i++){
		if (selObj.options[i].value == unitCode){
			selObj.selectedIndex = i;
			break;
		}
	}
}
 var oldHide=Calendar.prototype.hide;
Calendar.prototype.hide = function () {oldHide.apply(this);	change()} */
</script>

</html>
