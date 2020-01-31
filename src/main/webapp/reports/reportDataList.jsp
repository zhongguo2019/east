<!-- /ps/model/flexiblequery/reportDataList. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="/birt.tld" prefix="birt" %>
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

<script type="text/javascript">

/* 
function resetdata(obj,reportda,resulttablename,dataId,repid) {
	if(!confirm('<fmt:message key="view.qrcz" />')){
		return;
	}
	var params={"resulttablename": resulttablename,"reportDate":reportda,"reportId":repid,"dataId":dataId ,"method":"resetReportData"};
	$.ajax({ 
		type: "POST", 
		async: true, //ajax
		url: "flexiblequery.do", 
		data: jQuery.param(params), 
		success: function(result){
			if(result==1){
				//chan chu ben hang shu ju
				var tr=obj.parentNode.parentNode;
				var tbody=tr.parentNode;
				tbody.removeChild(tr);
				alert('<fmt:message key="view.sjczcg" />');
			}else if(result==2){
				alert('<fmt:message key="view.sjczsb" />');
			}else{
				alert('<fmt:message key="view.sjczsb" />');
			}
		}
	});
} */

/*  function etirSar(dataId,page){
	   var url = "dataFill.do?method=geteditDataDetail&singleOrMore=<c:out value='${singleOrMore}' />&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&pager="+page+"&dataId="+dataId+"&levelFlag=<c:out value='${levelFlag}'/>"; 
        window.location.href=url;//zhe shi dang qian ye diao zhuan <c:out value='${page.pageNo}'/>

 } */
 
 
/*  function delSar(obj,pkid,rId,reportda,organId){
	 if(pkid=="0"){
		 if(!confirm(reportda+'<fmt:message key="view.cyqrsc" />')){
				return;
			} 
	 }else{
		 if(!confirm('<fmt:message key="view.qrsc" />')){
				return;
			}
	 }
		var params={"pkid": pkid ,"reportId": rId ,"dataDate": reportda ,"organId":organId,"method":"delSingelData"};
		$.ajax({ 
			type: "POST", 
			async: false, //ajax
			url: "dataFill.do", 
			data: jQuery.param(params), 
			success: function(result){
				if(result==1){
						var tr=obj.parentNode.parentNode;
						var tbody=tr.parentNode;
						tbody.removeChild(tr);
					    alert('<fmt:message key="view.sjsccg" />');
				}else if(result==2){
					    alert('<fmt:message key="view.sjscsb" />');
				}
			}
		});
 } */
 
/*  function buttongjcx(organId,levelFlag){
	   var zhi1=$("#zhi").val();
   	document.dataMergeForm.action='<c:out value="${hostPrefix}" /><c:url value="/dataFill.do" />?method=getDataDetailgjcx&levelview=1&zhi='+zhi1+"&organId="+organId+"&levelFlag="+levelFlag ;
  	document.dataMergeForm.submit();
 } */
</script>
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
	 <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='${tableCss}/tablecss.css'/>"/>
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
	
}

$(document).ready(function(){
	var flagOrgan = <c:out value='${flagOrgan}' />
	if(flagOrgan==2){
		$("#flagOrgan1").remove();
	}else{
		$("#flagOrgan2").remove();
	} 
	  getOrganTreeXML();
});  

 function getOrganTreeXML(){//机构树
	 $.post("treeAction.do?method=getOrganTreeXML",function(data){
			var treeXml = eval(data)[0].treeXml;
			$('#combotreeOrganTree').combotree('loadData', treeXml);
		}); 
 }
 
 
</script>

</head>
<body  onload="forbiddon();">
				<table border="0" width="100%" align="left" cellSpacing=0 cellPadding=0>
					<tr>
						<td height="35" class="navbar" align="left">
							<form name="searchForm" action=""  target="_self"  method="post">
						
								<input type="hidden" name="reportId" value="<c:out value="${reportId }"/>"/>
								<input type="hidden" name="reportName" value="<c:out value="${reportName}"/>">
								<input type="hidden" name="organId" value="<c:out value='${organId}' />"/>
								<input type="hidden" name="organName"/>

					<table width="100%" border="0" align="left" cellSpacing="0"
						cellPadding="0">

						<tr>
							<td width="50px" align="left" nowrap align="right">&nbsp;&nbsp;&nbsp;<fmt:message
									key="reportview.form.date" /></td>
							<td width="50px"><input id="reportDate" name="reportDate"
								type="text" value="<c:out value='${dataDate}'/>"
								style="width: 150px;" readonly="true" onClick="WdatePicker()" />
							</td>

							<td width="50px" align="left" nowrap align="right">&nbsp;&nbsp;&nbsp;<fmt:message
									key="reportview.form.organ" /></td>
							<td width="80px" align="left" nowrap>
								<div id="flagOrgan1">
									<input id="combotreeOrganTree" class="easyui-combotree"
										value="<c:out value='${organId}' />" style="width: 300" />
								</div>
								<div id="flagOrgan2">
									<input id="combotreeOrganTree" class="easyui-combotree"
										multiple value="<c:out value='${organId}' />"
										style="width: 300" />
								</div>
							</td>
                             <td width="200px" align="left" nowrap>
                             <select id="reportId" name="reportId" style="width:200px;">
                             <c:forEach items="${reports}" var="reps">
                             <option  <c:if test="${reportId eq reps.pkid}">selected="selected"</c:if>  id="<c:out value='${reps.pkid}' />"  value="<c:out value='${reps.phyTable}'/>"><c:out value="${reps.name}" /> </option>
                              </c:forEach>
                             
                             </select>
                             </td>


							<td align="left" colspan="3" nowrap="nowrap"><a href="#"
								class="easyui-linkbutton buttonclass2" onclick="sub();"><fmt:message
										key="reportview.button.look" /></a>&nbsp;&nbsp;
						</tr>

					</table>
				</form></td>
					</tr>
					<tr>
						<td align="center" >
						<div id="showbirt" name="showbirt" >
						<c:if test="${showbirt=='yes'}">
						<birt:viewer id="birtViewer1"
					reportDesign='<%=request.getAttribute("prtName").toString()%>'
					baseURL="<%=request.getContextPath()%>" pattern="frameset"
					title="" showTitle="false" height="600" width="2000"
					format="html" frameborder="false" isHostPage="false"
					 showParameterPage="false">
					<birt:param name="reportDate" value='<%=request.getAttribute("dataDate").toString()%>'/>
				 <birt:param name="organId" value='<%=request.getAttribute("organId").toString() %>'></birt:param>
				</birt:viewer>
				</c:if>
						</div>
			
						</td>
					</tr>
					<tr height=17>
						<td></td>
					</tr>
				</table>

</body>

</html>

<script type="text/javascript">
var df=document.searchForm;

function sub(){
//	$("#showbirt").show();
	var viewOrganId = $("#combotreeOrganTree").combotree("getValue");
	var viewDataDate = df.reportDate.value;
	//alert(viewDataDate);
	var repname=$("#reportId option:selected").val();
	var repid=$("#reportId option:selected").attr("id");
	var url='<c:out value="${hostPrefix}" /><c:url value="/showBirt.do?method=showBirt" />&organId='+viewOrganId+"&dataDate="+viewDataDate+"&reportId="+repid+"&reportName="+repname+"&showbirt=yes";
	df.action=url;
	df.submit();
}


</script>