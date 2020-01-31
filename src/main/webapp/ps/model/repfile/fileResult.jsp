<!-- /ps/model/reportrule/list_basicrule. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>

<%
   User user = (User)request.getSession().getAttribute("user");
   String orgId = user.getOrganId();   
 %>


<html>
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
	
}
</script>
    <head>
        <title><fmt:message key="webapp.prefix"/></title>

<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
 <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
    <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
 </head>					


<script language=JavaScript>
 
$(document).ready(function(){
	  getrepTreeXML();
});  

 function getOrganTreeXML(){ 
	 $.post("treeAction.do?method=getOrganTreeXML",function(data){
			var treeXml = eval(data)[0].treeXml;
			$('#combotreeOrganTree').combotree('loadData', treeXml);
		}); 
 }
 
 function getrepTreeXML(){
	 $.post("reportView.do?method=repTreeAjax&levelFlag=1",function(data){
			var reportXml = eval(data)[0].reportXml;
			$('#combotreeRepTree').combotree('loadData', reportXml);
		}); 
 }
 function viewReport(tg, sr) {
    var df = document.reportViewForm;
	var vReportId = $("#combotreeRepTree").combotree("getValue");
 	df.action = "repFileAction.do?method=createFileSearch&systemid=2&reportId="+vReportId;
 	df.target = tg ;
 	df.submit();
 }
 
 function viewMagear(sr) {
	   alert(sr);
  }
	 
</script>


<body   onload="forbiddon();">
<div name="optionbar"  class="navbar">
					<form name="reportViewForm" action="reportView.do?method=inputExtraCondition" method="post">
					<table >
						<tr>
							<td ><fmt:message key="reportview.form.date"/></td>
							<td>
									<input type="hidden" name="datapkids" id="datapkids" value="<c:out value='${datapkids}' />" />
									<input type="hidden" name="reportName"	/> 
									
							  	<input id="reportDate" name="reportDate" type="text"  value="<c:out value='${reportDate}'/>"  style="width:150;" readonly="true" onClick="WdatePicker()"/>
										 
							</td>
							<td>
								&nbsp;&nbsp;<fmt:message key="datagather.label.report"/>
							</td>
							<td width="80" align="left" nowrap class="TdBGColor1">
											<div id="flagReport1">
												<input id="combotreeRepTree" class="easyui-combotree"    style="width:260px;;" 
													<c:if test="${reportId == null || reportId == '' }">
														value="10000" 
													</c:if>
													<c:if test="${reportId != null || reportId != '' }">
														value="<c:out value='${reportId}' />" 
													</c:if>
												/>
											</div>
										</td>
							 
							
							 
							<td>
								<a href="#" class="easyui-linkbutton buttonclass1" data-options="iconCls:'icon-search'"   onclick="viewReport('_self','yes')"><fmt:message key="reportview.button.look"/></a>
							</td>
						
						
						</tr>
					</table>
				
									
					</form>

				
             </div>
						<div align="center" style="width: 98%">
								<form name="reportDataForm" action="dataFill.do?method=getDataByCondition" method="post">
									
									<display:table name="Showlist" cellspacing="0" cellpadding="0" requestURI=""
							   		 	 id="rt" width="100%"  pagesize="60" styleClass="list reportsList" >
											
											<display:column sort="true" headerClass="sortable" width="10%"
												titleKey="reportguide">
												<c:out value="${rt[1]}" />
											</display:column>
											<display:column sort="true" headerClass="sortable" width="10%"
												titleKey="reportview.list.date">
												<c:if test="${rt[4]!=null}">
												<c:out value="${rt[4]}" />
												</c:if>
												<c:if test="${rt[4]==null}">
												<c:out value="${reportDate}" />
												</c:if>
											</display:column>
											<display:column sort="true" headerClass="sortable" width="10%"
												titleKey="rep.createDate">
												
												<c:out value="${rt[2]}" />
											</display:column>
											<display:column sort="true" headerClass="sortable" width="10%"
												titleKey="common.statistics.overtime">
												<c:if test="${rt[3]==2}">
												 <c:out value="${rt[5]}" />
												</c:if>
											</display:column>
											<display:column sort="true" headerClass="sortable" width="10%" 
												titleKey="rep.status">
												<c:if test="${rt[3]==2}">
												<fmt:message key="rep.success"/> 
												</c:if>
												<c:if test="${rt[3]==3}">
												<a style="text-decoration: none;color: red;" href="javascript:void(0);" onclick="viewMagear('<c:out value="${rt[6]}"/>');" ><fmt:message key="rep.create"/></a>
												</c:if>
												<c:if test="${rt[3]==1}">
												<fmt:message key="rep.in.generation"/>
												</c:if>
												
											</display:column>
											
									</display:table>
								</form>
							</div>



</body>


</html>