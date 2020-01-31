<!-- /ps/model/datafill/reportmove.jsp -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>

<c:set var="flagOrgan" value="1"/>
<c:set var="flagReport" value="1"/>
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
    <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
    
<script type="text/javascript">
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
	 $.post("reportView.do?method=repTreeAjax&levelFlag=1",function(data){
			var reportXml = eval(data)[0].reportXml;
			$('#combotreeRepTree').combotree('loadData', reportXml);
		}); 
 }
 
function setUnitCode(){
	var listObj = document.all.selUnit;
	document.all.unitCode.value = listObj.options[listObj.selectedIndex].value;
}

function repTree(){
    var oDate = document.reportViewForm.dataDate;
	var vDate = oDate.value;
	var oOrganId = document.reportViewForm.organId;
	var vOrganId = oOrganId.value;	
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId;
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

</script>
</head>
 

<body   class="easyui-layout" style="overflow:auto;">
				<table border="0" width="98%" align="left" cellSpacing=0 cellPadding=0>
					<tr>
						<td height="30" align="left">
							<html:form action="reportView.do?method=inputExtraCondition" method="post" >
								<table width="100%" border="0" align="left" cellSpacing="0"
									cellPadding="0">
									<input type="hidden" name="verifyFlag" id="verifyFlag"
										value="1" />
									<input type="hidden" name="canEdit" id="canEdit" value="0" />
									<input type="hidden" name="datapkids" id="datapkids"
										value="<c:out value='${datapkids}' />" />
									<input type="hidden" name="reportName"
										value="<c:out value='${reportName}' />" />
									<input type="hidden" name="organId"
										value="<c:out value='${organId}' />" />
									<input type="hidden" name="organName" />
									<input type="hidden" name="unitCode"
										value="<c:out value='${unitCode}' />" />
									<c:if test="${param.method=='enterViewSearch'}">
										<input type="hidden" name="mode" value="display" />
									</c:if>
									<c:if test="${param.method=='enterInputSearch'}">
										<input type="hidden" name="mode" value="edit" />
									</c:if>
									<tr>
										<td width="5%" align="left" nowrap class="TdBGColor1" align="right">
											&nbsp;&nbsp;&nbsp;<fmt:message key="reportdefine.report.beginDate"/>
										</td>
										<td class="TdBGColor1" width="5%">
										 <input id="startDate" name="startDate" type="text" 
											value="<c:out value='${startDate}'/>" 
											style="width:80px;" readonly="true" onClick="WdatePicker()"/>
									    </td>
										<td width="5%" align="left" nowrap class="TdBGColor1" align="right">
										&nbsp;&nbsp;&nbsp;<fmt:message key="reportdefine.report.endDate"/>
										</td>
										<td class="TdBGColor1" width="5%">
										 <input id="endDate" name="endDate" type="text" 
											value="<c:out value='${endDate}'/>" 
											style="width:80px;" readonly="true" onClick="WdatePicker()"/>
										</td>
										<td width="5%" align="left" nowrap class="TdBGColor1" align="right">
										   &nbsp;&nbsp;<fmt:message key="view.yuhm"/>
										</td>
										<td width="80px" align="left"  >
											<input type="text" id="userNum" name="userNum" value="<c:out value='${userNum }'/>"/>
										</td>
										<td  class="TdBgColor1" width="5%" align="right"><fmt:message key="select.organ"/></td>
										<td class="TdBgColor2" width="20%">
											<div id="flagOrgan1">
												<input id="combotreeOrganTree" class="easyui-combotree"  value="<c:out value='${organId}' />" style="width:250px;"/>
											</div>
											<div id="flagOrgan2">
												<input id="combotreeOrganTree" class="easyui-combotree" multiple value="<c:out value='${organId}' />"  style="width:250px;"/>
											</div>
										</td>
										<td align="left" >
										<div style="text-align:left;padding:5px">
									    	 <a href="#" class="easyui-linkbutton buttonclass1" data-options="iconCls:'icon-search'"  onclick="viewLog('_self','yes')"><fmt:message key="reportview.button.log"/></a>
									    </div>
										</td>
									</tr>
								</table>
							</html:form></td>
					</tr>
					<tr>
						<td align="center">
							<div align="center" style="width: 98%;" >
								<form name="logForm" action="viseAction.do?method=saveLogs"
									method="post">
									<display:table name="list" cellspacing="0" cellpadding="0"  
							   		 	requestURI="" id="rt" width="100%"  pagesize="18"
							    		styleClass="list list" >
										<display:column sort="true" headerClass="sortable" width="15%"
											titleKey="log.list.organ">
											<c:out value="${rt.organName}" />
										</display:column>
										<display:column sort="true" headerClass="sortable" width="10%"
											titleKey="groupmanage.del.displayinfo.logonname">
											<c:out value="${rt.logonName}" />
										</display:column>
										<display:column sort="true" headerClass="sortable" width="7%"
											titleKey="groupmanage.del.displayinfo.username">
											<c:out value="${rt.userName}" />
										</display:column>
										<display:column sort="true" headerClass="sortable" width="23%"
											titleKey="log.list.report">
											<c:out value="${rt.reportName}" />
										</display:column>
										<display:column sort="true" headerClass="sortable" width="15%"
											titleKey="log.list.datadate">
											<%-- <c:out value="<%=new SimpleDateFormat("yyyy-MM-dd HH24:mm:ss").format((list.get(i))[3]) %>"  /> &nbsp;&nbsp; --%>
											<c:out value="${rt.dataDate }"  />
										</display:column>
										<display:column sort="true" headerClass="sortable" width="30%"
											titleKey="log.list.operate">
											<c:out value="${rt.memo}" />
										</display:column>
									</display:table>
									<!-- <script type="text/javascript">
											highlightTableRows("rt");
									</script> -->
								</form>
							</div>
						</td>
					</tr>
					<tr height=17>
						<td></td>
					</tr>
				</table>
</body>
<script type="text/javascript">
	function viewLog(tg, sr) {
		var startDate = document.getElementById("startDate").value ;
		var endDate   = document.getElementById("endDate").value ;
		var organId   = document.getElementById("combotreeOrganTree").value ;
		var userNum   = document.getElementById("userNum").value ;
		
		if (startDate > endDate) {
			alert("<fmt:message key="js.validate.date2"/>");
			return false;
		}
		/* <c:if test="${unitCodeFlag == 1}">
			setUnitCode();
		</c:if> */
		
		window.location.href="sysLog.do?method=list&startDate="+startDate+"&endDate="+endDate+"&organId="+organId+"&userNum="+userNum;
	}
</script>
</html>
