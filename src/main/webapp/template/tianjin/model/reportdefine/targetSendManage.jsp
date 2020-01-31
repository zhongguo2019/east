<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%
   User user = (User)request.getSession().getAttribute("user");
   String orgId = user.getOrganId();   
%>
<c:set var="colName">
	<fmt:message key="common.list.reportname"/>
</c:set>
<c:set var="reportTreeURL">
	<c:out value="${hostPrefix}"/><c:url value='/reportView.do?method=repTreeAjax${params}'/>
</c:set>
<c:set var="reportButton">
	<fmt:message key="place.select"/>
</c:set>

<c:set var="colNames">
	<fmt:message key="exportXML.tree.org.name"/>
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}" /><c:url value="/treeAction.do?method=getOrganTreeXML${orgparam}"/>
</c:set>
<c:set var="orgButton">
	<fmt:message key="place.select"/>
</c:set>
<c:set var="displaySumType">
	<%= FuncConfig.getProperty("reportview.showCollectType")%>
</c:set>
<c:set var="studentLoanReportId">
	<%= FuncConfig.getProperty("studentloan.reportid")%>
</c:set>
<html>
<head>
<!-- targetManage.jsp -->
<title><fmt:message key="webapp.prefix" /></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/ps/scripts/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script>  
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>  
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>    
<script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script>
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
<style type="text/css">
.table {
	width: 100%;
	padding: 0px;
	margin: 0px;
	font-family: Arial, Tahoma, Verdana, Sans-Serif, '<fmt:message key="view.st"/>';
	border-left: 1px solid #ADD8E6;
	border-collapse: collapse;
}

.table th {
	font-size: 12px;
	font-weight: 600;
	color: #303030;
	border-right: 1px solid #ADD8E6;
	border-bottom: 1px solid #ADD8E6;
	border-top: 1px solid #ADD8E6;
	letter-spacing: 2px;
	text-align: left;
	padding: 10px 0px 10px 0px;
	background: url(../images/tablehdbg.png);
	white-space: nowrap;
	text-align: center;
	overflow: hidden;
}

.table td {
	border-right: 1px solid #ADD8E6;
	border-bottom: 1px solid #ADD8E6;
	background: #fff;
	font-size: 12px;
	padding: 3px 3px 3px 6px;
	color: #303030;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}


.table td.color {
	background: #edf7f9;
}

.table td a:link {
	font-weight: 400;
	color: #2259D7;
	text-decoration: none;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}

.table td a:visited {
	font-weight: 400;
	color: #2259D7;
	text-decoration: none;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}

.table td a:hover {
	font-weight: 400;
	text-decoration: underline;
	color: #303030;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}

.table td a:active {
	font-weight: 400;
	text-decoration: none;
	color: #2259D7;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}

.btn {
	BORDER-RIGHT: #7b9ebd 1px solid;
	PADDING-RIGHT: 2px;
	BORDER-TOP: #7b9ebd 1px solid;
	PADDING-LEFT: 2px;
	FONT-SIZE: 12px;
	FILTER: progid : DXImageTransform.Microsoft.Gradient ( GradientType = 0,
		StartColorStr = #ffffff, EndColorStr = #cecfde );
	BORDER-LEFT: #7b9ebd 1px solid;
	CURSOR: hand;
	COLOR: #0D82AE;
	PADDING-TOP: 2px;
	BORDER-BOTTOM: #7b9ebd 1px solid;
	background-color: #FFFFFF
}
</style>
<script type="text/javascript">
$(function() {
	$('#one1').click(function() {
		var size = $('#left>option:selected').size();
		if (size != 0) {
			$('#left > option:selected').appendTo($('#right'));
			$('#right > option:selected').attr("selected", "");
		} else {
			$('#left>option:first-child').appendTo($('#right'));
		}
	});
	$('#all1').click(function() {
		$('#left > option').appendTo($('#right'));
	});
	$('#one2').click(function() {
		var size = $('#right>option:selected').size();
		if (size != 0) {
			$('#right > option:selected').appendTo($('#left'));
			$('#left > option:selected').attr("selected", "");
		} else {
			$('#right>option:first-child').appendTo($('#left'));
		}
	});
	$('#all2').click(function() {
		$('#right > option').appendTo($('#left'));
	});

	$("#left").dblclick(function() {
		$('#left > option:selected').appendTo($('#right'));
		$('#right > option:selected').attr("selected", "");
	});

	$("#right").dblclick(function() {
		$('#right > option:selected').appendTo($('#left'));
		$('#left > option:selected').attr("selected", "");
	});
});

function saveTargets() {
	var arr1 = "";
	var arr2 = "";
	
	var arr3 = "";
	var arr4 = "";
/* 	$("#left option").each(function(){
		arr = arr + $(this).val() + ",";
	}); */
	$("input[name='target1']").each(function(){
		if(!$(this).attr("checked")) {
			arr1 = arr1 + $(this).val() + ",";
		}
	});
	$("input[name='target2']").each(function(){
		if($(this).attr("checked")) {
			arr2 = arr2 + $(this).val() + ",";
		}
	});
	
	arr3= $("input[name='radio1'][checked]").val();
	arr4= $("input[name='radio2'][checked]").val();
	
	$("#tTargets1").val(arr1);
	$("#tTargets2").val(arr2);
	$("#Rradio1").val(arr3);
	$("#Rradio2").val(arr4);
	
	$("#targetForm").submit();
}

function delStrdreport(){
	var df=document.targetForm;
	var reportId  = $("#reportId").val();
	var reportId1 = $("#reportId1").val();
	df.action = '<c:out value="${hostPrefix}" /><c:url value="/reportTargetAction.do" />?method=delstrdReport&reportId='+reportId+'&reportId1='+reportId1+'&levelFlag='+2;
	df.submit();
}
</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" onload="forbiddon()">
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
	
}

</script>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%"  >
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
				<p style="margin-top: 3"><font class=b><b><fmt:message key="view.bbdy.bswh"/></b></font></p>
          </td>
		  <td  ></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td class="TdBGColor1"  width="100%" bgcolor="#EEEEEE" valign="top">
    <br>
    <br>
	<!--<form name="reportViewForm" action="reportView.do?method=inputExtraCondition" method="post" onsubmit="return reportCheck()">  -->
	<html:form action="reportView.do?method=inputExtraCondition" method="post" onsubmit="return reportCheck();" >
		<table width="90%" border="0" align="center" cellSpacing="1" cellPadding="0"  >
		<input type="hidden" name="reportId" id="reportId" value="<c:out value='${reportId}' />"/>
		<input type="hidden" name="reportId1" id="reportId1" value="<c:out value='${reportId1}' />"/>
		<input type="hidden" name="targetFlag" id="targetFlag" value="1"/>
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
				<td class="TdBGColor1"  width="50" align="left" nowrap ><fmt:message key="select.template"/>:</td>
				<td class="TdBGColor1"  width="80" align="left" nowrap >
					<slsint:ActiveXTree left="220" top="325" width="380" height="${activeXTreeHeight }"
			      		xml="${reportTreeURL}&levelFlag=2"
			      		bgcolor="0xFFD3C0" 
			      		rootid="10000" 
			      		columntitle="${colName}" 
			      		columnwidth="380,0,0,0" 
			      		formname="reportViewForm" 
			      		idstr="reportId" 
			      		namestr="reportName" 
			      		checkstyle = "0" 
			      		filllayer="2" 
			      		txtwidth="300"
			      		buttonname="${reportButton}"
			      		onhide="setReportUnit()">
			      	</slsint:ActiveXTree>
				</td>
<%-- 				<td class="TdBGColor1"  align="left" colspan="3"><input  align="middle" type="button" value="<fmt:message key="reportDefine.reportTarget.manage"/>" style="width:100;" onclick="viewReport('_self','yes')"></td>
 --%>			
 			<td></td>
 			</tr>
			<tr>
				<td class="TdBGColor1"  width="50" align="left" nowrap ><fmt:message key="select.model"/>:</td>
				<td class="TdBGColor1"  width="80" align="left" nowrap >
					<slsint:ActiveXTree left="220" top="325" width="380" height="${activeXTreeHeight }"
			      		xml="${reportTreeURL}&levelFlag=1"
			      		bgcolor="0xFFD3C0" 
			      		rootid="10000" 
			      		columntitle="${colName}" 
			      		columnwidth="380,0,0,0" 
			      		formname="reportViewForm" 
			      		idstr="reportId1" 
			      		namestr="organName" 
			      		checkstyle = "0" 
			      		filllayer="2" 
			      		txtwidth="300"
			      		buttonname="${reportButton}"
			      		onhide="setReportUnit()">
			      	</slsint:ActiveXTree>
				</td>
				<td class="TdBGColor1"  align="left" colspan="3"><input  align="middle" type="button" value="<fmt:message key="button.template.edit"/>" style="width:100;" onclick="viewReport('_self','yes')"></td>
 			</tr>
		</table>
	</html:form>
</td>
</tr>
<c:if test="${targetList!=null}">
<tr>
<td class="TdBGColor1">
<form action="<c:url value='reportTargetAction.do?method=saveTemplateTargets&levelFlag=2'></c:url>" method="post" id="targetForm" name="targetForm">
<table align="left"  width="100%" style="margin-top: 0px" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="left" height="20">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="btn" id="b1" value='<fmt:message key="button.save"/>'  onclick="saveTargets();"/>&nbsp;&nbsp;
				<c:if test="${template.name != model.name}">
			    	<c:if test="${Rradio1 !=t.targetField }">
					   <input type="button" class="btn" id="del" value='<fmt:message key="strdreport.del"/>' onclick="delStrdreport();"/>
					</c:if>
			    </c:if>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: top;" class="TdBGColor1" align="left" >
				<input type="hidden" value="<c:out value='${reportId}'></c:out>" name="reportId" id="reportId"/>
				<input type="hidden" value="<c:out value='${reportId1}'/>" name="reportId1"  id="reportId1"/>
				<input type="hidden" name="pkidupdate" id="pkidupdate" value="<c:out value='${pkidstrd}' />"/>
				<input type="hidden" value="" name="tTargets1" id="tTargets1"/>
					<input type="hidden" value="" name="tTargets2" id="tTargets2"/>
				<input type="hidden" value="" name="Rradio1" id="Rradio1"/>
					<input type="hidden" value="" name="Rradio2" id="Rradio2"/>
				<c:if test="${template.name != model.name}">
				   <input type="hidden" value="2" name="tableas" id="tableas"/>
				</c:if>
				<c:if test="${template.name == model.name}">
					<input type="hidden" value="1" name="tableas" id="tableas"/>
				</c:if>
				<table style="vertical-align: top;" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td valign="top" class="TdBGColor1" align="center" width="100%">
							<table>
								<tr >
									<td class="TdBGColor1" align="left" width="30%"  valign="top" >
									<div style="border: 1px;color: #0673DA">
										<table >
											<tr>
												<td width="100%"  valign="top" class="TdBGColor1"   align="left" ><label><font color="#0673DA"><c:if test="${template.name != model.name}"><fmt:message key="view.xzglzd"/></c:if> &nbsp;&nbsp;<c:out value="${template.name}"/></font></label>
													<c:forEach var="t" items="${templateTargets}">
														<tr>
															<td width="80%" align="left">
																<c:if test="${template.name != model.name}">
																  <input name="radio1" type="radio" value="<c:out value='${t.targetField}' />" <c:if test="${Rradio1==t.targetField }"><c:out value='checked="checked"'/></c:if>/><c:out value='${t.targetName}'/></input>
																</c:if> 
															<c:if test="${template.name == model.name}">
															    <c:out value='${t.targetName}'/>
														    </c:if> 
															</td>
															<td width="20%" align="right" >
																<a href="<c:out value='reportTargetAction.do?method=delTemplateTarget&levelFlag=2&targetId=${t.targetField}&reportId=${reportId}&reportId1=${reportId1}'/>"><img src="<c:url value='/ps/framework/images/del.gif'/>" name="<c:out value='${t.pkid}' />" border="0" width="25" height="25"/></a>
															</td>
														</tr>
													</c:forEach>
												</td>
												</tr>
											</table>
											</div>
											</td>
											<td width="40%" align="center">
											</td>
											<td class="TdBGColor1"  align="right" width="30%"  valign="top" >
											<table>									
												<tr>
													<td align="right">
													<label><font color="#0673DA"><c:if test="${template.name != model.name}"><fmt:message key="view.xzglzd"/></c:if> &nbsp;&nbsp;&nbsp;<c:out value="${model.name}"/></font></label>
														<c:forEach var="t" items="${targetListChecked}">
															<tr>
																<td align="left">
																	<c:if test="${template.name != model.name}">
																       <input name="radio2" type="radio" value="<c:out value='${t.targetField}'/>" <c:if test="${Rradio2==t.targetField }">checked="checked"</c:if> /></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																    </c:if>
																	<input name="target1" type="checkbox" value="<c:out value='${t.targetField}' />"  checked="checked"/><c:out value='${t.targetName}'/></input>
																</td>
															</tr>
														</c:forEach>
														<c:forEach var="t" items="${targetList}">
															<tr>
																<td align="left">
																    <c:if test="${template.name != model.name}">
																   		 <input name="radio2" type="radio" value="<c:out value='${t.targetField}' />" <c:if test="${Rradio2==t.targetField }">checked="checked"</c:if> /></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																    </c:if> 
																	<input name="target2" type="checkbox" value="<c:out value='${t.targetField}'/>"><c:out value="${t.targetName}" /></input>
																	<%-- <input type="hidden" value="<c:out value='${t.pkid}'/>"> --%>
																</td>
															</tr>
														</c:forEach>
												</td>
											</tr>
										</table>
										</td>
								</tr>
							</table></td>
					</tr>
				</table>
			</td>
		</tr>
</table>
</form>
</td>
</tr>
</c:if>
</table>
</body>

<script type="text/javascript">

var df=document.reportViewForm

<c:if test="${reportBalance!=null}">
	alert("<c:out value='${reportBalance}'/>");
</c:if>

<c:if test="${viewResult == '1'}">
	alert("<fmt:message key="reportview.alert.noformat"/>");
</c:if>


function viewReport(tg,sr){
	oReportId = df.reportId;
	vReportId = oReportId.value;
	var 	reportId1 = df.reportId1.value;
	if (vReportId==""||vReportId == null){
		alert("<fmt:message key="js.validate.modeltarget"/>");
		return;
	}

	if (reportId1==""||reportId1 == null){
		alert("<fmt:message key="js.validate.modeltarget"/>");
		return;
	} 
if((reportId1==""||reportId1 == null) && (vReportId !=""||vReportId != null)){
	alert("<fmt:message key="js.validate.model"/>");
	return;
} 
	 if((vReportId < 10000) &&(df.reportId1.value >= 10000)){
		alert("<fmt:message key="js.validate.model"/>");
		return false;
	} 
    if (vReportId >= 10000 || df.reportId1.value >= 10000){
    	alert("<fmt:message key="js.validate.modeltarget"/>");
		return false;
	}
    
    // pan duan ye wu shi fou dui ying
  /**  if(vReportId%100 <=2){
    	if(df.reportId1.value%100 >10) {
    		alert("<fmt:message key="js.validate.agreement"/>");
    		return false
    	}
    } else if(vReportId<1000) {
    	if(df.reportId1.value%100 <= 10) {
    		alert("<fmt:message key="js.validate.agreement"/>");
    		return false;
    	}
    }**/
    
	df.action="reportTargetAction.do?method=listTarget&openmode="+tg +"&levelFlag=2";
	<c:if test="${unitCodeFlag == 1}">
	setUnitCode();
	</c:if>
	if(tg==="_blank"){
		var viewOrganId = df.organId.value;
		var viewDataDate = df.dataDate.value;
		var viewReportId = df.reportId.value;
		tg = viewReportId+viewDataDate+viewDataDate+"_window";
	}
	df.target=tg;
	//df.showret.value=sr;
	df.submit();
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

function changeOrg(){
    var oDate = document.reportViewForm.dataDate;
	var vDate = oDate.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/treeAction.do?method=getOrganTreeXML&date="+vDate;
	document.all.objTree1.SetXMLPath(url);
   	document.all.objTree1.Refresh();	
}


function changeRep(){
    // var oDate = document.reportViewForm.dataDate;
	// var vDate = oDate.value;
	var oOrganId = document.reportViewForm.organId;
	var vOrganId = oOrganId.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId;	
	<c:if test="${systemName=='1104'}">
		var url = "<c:out value="${hostPrefix}"/>/slsint/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId;
	</c:if>
	<c:if test="${systemName=='billExchange'}">
		var url = "<c:out value="${hostPrefix}"/>/slspj/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId;
	</c:if>
	document.all.objTree2.SetXMLPath(url);
   	document.all.objTree2.Refresh();	
}

function reportCheck(){
	oReportId = df.reportId;
    vReportId = oReportId.value;
    var reportId1=df.reportId1.value;
	if (vReportId==""||vReportId == null){
       alert("<fmt:message key="js.validate.report"/>");
       return false;
	}
	if (vReportId >= 10000){
		return false;
	}
	
	if (vReportId >= 10000){
		return false;
	}

}

function change(){
	changeOrg();
	changeRep();
	setReportList();
}

function changeTree1(){
	changeRep();
}

function changeTree2(){}

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
Calendar.prototype.hide = function () {oldHide.apply(this);	change()}
</script>

<script type="text/javascript">
	//changeDate();  <!-- set checkbox -->

function changeDate(){
	var jdate = document.getElementById("dataDate").value;
	jdate = jdate.substring(5,7);
	setCheckbox(jdate);
}

function setCheckbox(month){
	var str = "";
	if(month == "03" || month == "09"){
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency1' value='1' ><fmt:message key='common.list.frequency1'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency2' value='2' ><fmt:message key='common.list.frequency2'/>";
	}else if(month == "06"){
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency1' value='1' ><fmt:message key='common.list.frequency1'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency2' value='2' ><fmt:message key='common.list.frequency2'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency3' value='3' ><fmt:message key='common.list.frequency3'/>";
	}else if(month == "12"){
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency1' value='1' ><fmt:message key='common.list.frequency1'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency2' value='2' ><fmt:message key='common.list.frequency2'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency3' value='3' ><fmt:message key='common.list.frequency3'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency4' value='4' ><fmt:message key='common.list.frequency4'/>";
	}else{
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency1' value='1' ><fmt:message key='common.list.frequency1'/>";
	}

	str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency5' value='5' ><fmt:message key='common.list.frequency5'/>";
	
	var div = document.getElementById("freq");
	div.innerHTML = str;
}

</script>

<script language='javascript'><!--

	var arrreport = new Array();
	<c:forEach var="report" items="${reportList}" varStatus="status">
		arrreport[<c:out value='${status.index}'/>] = new Array("<c:out value='${report.pkid}'/>",
		"<c:out value='${report.name}'/>",
		"<c:out value='${report.code}'/>",
		"<c:out value='${report.reportType}'/>",		
		"<c:out value='${report.frequency}'/>")
	</c:forEach>	

	var arr1 = new Array();
	<c:forEach var="report" items="${reportList}" varStatus="status">
		arr1[<c:out value='${status.index}'/>] = new Array("<c:out value='${report.pkid}'/>",
		"<c:out value='${report.name}'/>",
		"<c:out value='${report.code}'/>",
		"<c:out value='${report.reportType}'/>",		
		"<c:out value='${report.frequency}'/>")
	</c:forEach>		
	

 function getPosition( obj )

     { 

     var top = 0,left = 0;

      do 

{

    top += obj.offsetTop;

        left += obj.offsetLeft;

     }

while ( obj = obj.offsetParent );      

      var arr = new Array();      

      arr[0] = top;

      arr[1] = left;       

      return arr;    

   }


function createMenu( textBox, menuid ){ 

       if( document.getElementById( menuid ) == null )

       {

         var left_new=getPosition( textBox )[1];

         var top_new=getPosition( textBox )[0];    

         var newControl = document.createElement("div"); //???      

         newControl.id = menuid;        

         document.body.appendChild( newControl );       

         newControl.style.position = "absolute"; 

         newControl.style.border = "solid 1px #000000";

         newControl.style.backgroundColor = "#FFFFFF";

         newControl.style.width = "360px"; //????

         newControl.style.left = "40px";           //??

         newControl.style.top =  "35px";  //????????2?????JS?????????   
		var res = createMenuBody(textBox);

		var newNode = document.createElement("p");

		newNode.innerHTML = res;

		newControl.appendChild(newNode);



        return newControl;

       } else {

		//document.all.suggest.style.display="block";

		document.getElementById(menuid).style.display="block";
		
		document.getElementById(menuid).innerHTML="";

//		var res = createMenuBody( document.getElementById(textBox).getAttribute("value"));
		var res = createMenuBody(textBox);

		var newNode1 = document.createElement("p");

		newNode1.innerHTML = res;

		document.getElementById(menuid).appendChild(newNode1);


			return document.getElementById( menuid );
	   }
    }

  function getSearchResult( textBox )
    {
    var key = document.getElementById(textBox).value;
	var arr = new Array();
    var k = 0;
    if(textBox == "txt1"){
    	for(i = 0; i < arr1.length; i++){
		if(arr1[i][1].indexOf(key) > -1 || arr1[i][1].indexOf(key.toUpperCase()) > -1  || arr1[i][1].indexOf(key.toLowerCase()) > -1){
			arr[k] = arr1[i];			
			k++;
		} else{
			continue;
		}	
	}
    }else{
    	for(i = 0; i < arr1.length; i++){
		if(arr1[i][2].indexOf(key) > -1 || arr1[i][2].indexOf(key.toUpperCase()) > -1  || arr1[i][2].indexOf(key.toLowerCase()) > -1){
			arr[k] = arr1[i];			
			k++;
		} else{
			continue;
		}	
	}
    }

 return arr;

}


  function createMenuBody( textBox )

    {

      var result = "";

      var j = 0;

      arr = getSearchResult( textBox ); //???????

       //????????

      if(arr.length > 10)

      {

        j = 10;

      }

      else

      {

        j = arr.length;

      }

	if(textBox == 'txt1'){
		for ( var i = 0; i < j; i++ ) {
			result += "<table border=\"0\" cellpadding=\"2\" cellspacing=\"0\" id=\"menuItem"+(i+1)+"\" onmouseover=\"forceMenuItem( "+(i+1)+");\" width=\"100%\" onclick=\"givNumberForMouse("+i+")\"><tr><td class=\"TdBGColor1\"  align=\"left\">" + arr[i][1] + "</td><td class=\"TdBGColor1\"  align=\"right\">" + (i+1) + " </td></tr></table>";
		}
	}else{
		for ( var i = 0; i < j; i++ ) {
			result += "<table border=\"0\" cellpadding=\"2\" cellspacing=\"0\" id=\"menuItem"+(i+1)+"\" onmouseover=\"forceMenuItem( "+(i+1)+");\" width=\"100%\" onclick=\"givNumberForMouse("+i+")\"><tr><td class=\"TdBGColor1\"  align=\"left\">" + arr[i][2] + "--" + arr[i][1] + "</td><td class=\"TdBGColor1\"  align=\"right\">" + (i+1) + " </td></tr></table>";
		}
	}
    return result;
 	}

var menuFocusIndex = 0;
function forceMenuItem(index){

//var menuFocusIndex = 0;

lastMenuItem = document.getElementById( "menuItem" + menuFocusIndex )

       if ( lastMenuItem != null )

       {

         //??????

         lastMenuItem.style.backgroundColor="white";       

         lastMenuItem.style.color="#000000";

       }

       var menuItem = document.getElementById( "menuItem" + index );

       if ( menuItem == null )

        {

          document.getElementById("txt1").focus(); 

        }

        else 

        {

         menuItem.style.backgroundColor = "#5555CC";

         menuItem.style.color = "#FFFFFF";

         menuFocusIndex = index;

      }

}


function forceMenuItem(index,textBox){

//var menuFocusIndex = 0;

lastMenuItem = document.getElementById( "menuItem" + menuFocusIndex )

       if ( lastMenuItem != null )

       {

         //??????

         lastMenuItem.style.backgroundColor="white";       

         lastMenuItem.style.color="#000000";

       }

       var menuItem = document.getElementById( "menuItem" + index );

       if ( menuItem == null )

        {
		if(textBox == "tet1"){
			document.getElementById("txt1").focus(); 
		}else{
			document.getElementById("txt2").focus(); 
		}

        }

        else 

        {

         menuItem.style.backgroundColor = "#5555CC";

         menuItem.style.color = "#FFFFFF";

         menuFocusIndex = index;

      }

}


function catchKeyBoard(e,textBox, menuid){

	var keyNumber = event.keyCode;
	var txtcontent = document.getElementById(textBox).value;
	if(keyNumber =='40'){ 
       if(menuFocusIndex == 10){
         return true;
       }else if (menuFocusIndex == null){     
           forceMenuItem( 1, textBox);
           givNumber( 0 );
       } else{
          forceMenuItem( menuFocusIndex+1, textBox); 
          givNumber(menuFocusIndex-1);
		}
	} else if(keyNumber == '38'){  
        if(menuFocusIndex == 1){
           forceMenuItem(menuFocusIndex-1, textBox); 
         } else{
          forceMenuItem(menuFocusIndex-1, textBox); 
          givNumber(menuFocusIndex-1);
        } 
   } else if(txtcontent == null || txtcontent == "" || keyNumber == '13' || keyNumber == '27'){
   		suggestclose();
   }else {
		createMenu(textBox, menuid); 
   } 
}

 function givNumber( index )

     {

       document.getElementById("txt1").value = arr[index][1];
       
       document.getElementById("txt2").value = arr[index][2];  
       
       document.getElementById("reportId").value = arr[index][0];

       document.getElementById("txtTree2").value = arr[index][1];       

       document.getElementById("txt1").focus();     
       
       changeRep();  
	   
	   //document.all.suggest.style.display="none";

     }

 function givNumberForMouse( index )

     {
     
       document.getElementById("txt1").value = arr[index][1];

       document.getElementById("txt2").value = arr[index][2];
       
       document.getElementById("reportId").value = arr[index][0];
       
       document.getElementById("txtTree2").value = arr[index][1];       

       document.getElementById("txt1").focus(); 

	   document.all.suggestName.style.display="none";
	   
	   document.all.suggestCode.style.display="none";	   
	   
	   
	   changeRep();
	   
//		document.getElementById("suggest").innerHTML="";

     }
     
    var typerep = new Array();
    
//	changeType();

function changeType(){

		var type = document.forms[0].reportType.value;
	
		typerep = new Array();
		var j = 0;
		for(i=0; i< arrreport.length; i++){
			if(arrreport[i][3] == type){
				typerep[j] = arrreport[i];
				j++;
			}else if(type == 0){
				typerep[j] = arrreport[i];
				j++;
			}else {
				continue;
			}
		}
		checkfeq(this);
	}    

 var freqArr = new Array();
 function checkfeq(freq){
 	var freqs = $("#freq > input");
	var cnt = 0;
	freqArr = new Array();
	freqs.each(function(i){
		if(this.checked){
			freqArr[i] = this.value;
			cnt++;
		}
	});
	if(cnt == 0){
		alert("<fmt:message key="batch.checkdata.form.checkfreq"/>");
		freq.checked = true;
		freqArr[0] = freq.value;
 	}
	filterReport(freqArr);
 }	 
 
function filterReport(freqArr_){
	arr2 = new Array();
	var k = 0;
	for(i = 0; i < freqArr_.length; i++){
		for(j = 0; j < typerep.length; j++){
			if(freqArr_[i] == typerep[j][4]){
				arr2[k] = typerep[j];
				k++;
			}else{
				continue;
			}
		}
	
	}
	var m = 0;
	arr1 = new Array();
	for(i = 0; i < arr2.length; i++){
			arr1[m] = arr2[i];
			m++;
	}
	return arr1;
} 
 
 function setReportList(){
    var oDate = document.reportViewForm.dataDate;
	var vDate = oDate.value;
    var oOrganId = document.reportViewForm.organId;
	var vOrganId = oOrganId.value;	
	var options={onSuccess:setReportUnitResponse}
	var url='<c:out value="${hostPrefix}" /><c:url value="/reportView.do" />?method=getReportListAjax&paramDate='+vDate+"&paramOrgan=" + vOrganId;
	pu = new Ajax.Updater($("users"),url,options);
}

var allRep = new Array();
var repLen = 0;
function setReportUnitResponse(ajaxRequest){
	var struts = ajaxRequest.responseText;
	var reports = eval(struts);
	arr1 = new Array();
	arrreport = new Array();
	for(i = 0; i < reports.length; i++){
		arr1[i] = new Array(reports[i].id,reports[i].name,reports[i].code,reports[i].reportType,reports[i].frequency);
		arrreport[i] = new Array(reports[i].id,reports[i].name,reports[i].code,reports[i].reportType,reports[i].frequency);
	}
	changeType();
}

 function darkSearch(){
 var displayStyle = document.all.darkSearch.style.display;
 if(displayStyle == 'none'){
 	document.all.darkSearch.style.display = "block";
 }else{
 	document.all.darkSearch.style.display = "none";
 }
 }

 function suggestclose(){
  var suggestNameStyle = document.all.suggestName.style.display;
  var suggestCodeStyle = document.all.suggestCode.style.display;  
  if(suggestName != 'none' || suggestCodeStyle != 'none'){
 	document.all.suggestName.style.display = "none";
 	document.all.suggestCode.style.display = "none"; 
 	}
 }

</script>



</html>
