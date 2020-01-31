<!-- /ps/model/datavalidation/com_logicrule. -->
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


<html>
    <head>
        <title><fmt:message key="webapp.prefix"/></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
	<script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
    </head>					


<script language=JavaScript>


function  listReportRule(){
	var df=document.reportFormatForm;	
	var tabName=$("#tabName").val();
	var dataDate=$("#dataDate").val();
	df.action='<c:out value="${hostPrefix}" /><c:url value="/datavalidation.do" />?method=listResultFull&dataDate='+dataDate+"&tabName="+tabName;
	df.submit();
}

</script>
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
	
}
</script>
<script type="text/javascript">
		 //window.parent.document.getElementById("ppppp").value= "<fmt:message key="view.sjxy"/>";  
</script>
<body  onload="forbiddon();">

					<form name="reportFormatForm" action="datavalidation.do?method=listResultFull" method="post" style="margin: 0;">
					<input type="hidden" value="<c:out value="${levelFlag}"/>" name="levelFlag"/>
					<div  name="optionbar" class="navbar2" >
						<table>
								<tr>
									<td>
									<fmt:message key="view.xyrq"/> 
								</td>
								<td>
									  <input id="dataDate" name="dataDate" type="text" value="<c:out value='${dataDate}'/>" 
										style="width:150;" readonly="true" onClick="WdatePicker()"/>
								</td>
						<%-- 		
								<td>
									<fmt:message key="view.sjmx"/>
								</td>
								<td>
									<select name="reportn">
										<option value="0"><fmt:message key="view.sylx"/></option>
										<c:forEach items="${reportList}" var="rep"><c:if
										 test="${reportid==rep.pkid}"><option value="<c:out
											 value="${rep.pkid}"/>" selected><c:out value="${rep.name}"/></option></c:if>
											<c:if test="${reportid!=rep.pkid}"><option value="<c:out
											 value="${rep.pkid}"/>"><c:out value="${rep.name}"/></option></c:if></c:forEach>
									</select>
								</td> --%>
								<td>
									<fmt:message key="view.sjmx"/>
								</td>
								<td>
									<input  type="text" name="tabName" id="tabName" value='<c:out value="${tabName}"/>'/>
								</td>
								<td>
									<a href="#" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-search'"  name="ss" onclick="return listReportRule();" ><fmt:message key='button.search'/></a>
								</td>
							</tr>
						</table>
					</div>
					</form>	
				    <div align="center" style=" width:100%">
						<form name="logForm" action="viseAction.do?method=saveLogs" method="post" style="margin: 0">
						 	<% 
						 	int i = 0;
						 	%>
						<display:table name="resultfullList" cellspacing="0" cellpadding="0"  
							    requestURI="" id="rt" width="100%"  pagesize="80"
							    styleClass="list resultfullList" >
							    <%-- Table columns --%>
							    <%
							    	i++;
							    %>
							     <display:column sort="true" 
							    	headerClass="sortable" width="10%" 
							    	titleKey="reportrule.rulecode">
							    	<c:out value="${rt[0]}" />
							    </display:column>
							      <display:column sort="true" 
							    	headerClass="sortable" width="15%"   
							    	titleKey="view.sjmx">
							    	<c:out value="${rt[1]}" />
							    </display:column>
                                <display:column sort="true" 
							    	headerClass="sortable" width="50%" 
							    	titleKey="usermanage.dic.description">
							    	<c:out value="${rt[2]}" />
							    </display:column>
			                   
			                    <display:column sort="true" 
							    	headerClass="sortable" width="10%" 
							    	titleKey="view.xyrq">
							    	<c:out value="${rt[3]}" />			    	
							    </display:column>
								<display:column sort="true" headerClass="sortable" width="10%" titleKey="reportrule.edit">
							<!-- 	http://192.168.0.14:9080/east/dataFill.do?              method=editReportDataEntryForDataInput&openmode=_self&canEdit=1&levelFlag=0&organCode=1000000000&reportDate=2016-07-31&reportId=1001&flag2=1&flagtap=1 -->
								    <a style="text-decoration: none;" href="#" onclick="window.parent.parent.window.addPaneltab('<fmt:message key="view.sjbl"/>','dataFill.do?method=editReportDataEntryForDataInput&openmode=_self&canEdit=1&levelFlag=0&organCode=1000000000&reportDate=<c:out value="${rt[3]}"/>&reportId=1022&flag2=1&flagtap=')"><fmt:message key="view.sjbl"/></a>&nbsp;&nbsp;
								</display:column>
							</display:table>
							</form>
				    </div>

		 <tr><td class="TdBGColor1" colspan="2">
	   
			
	    
	    


<div id="loading"
			style="position: absolute; display: none; color: black; background: #f0f0f0;">
			<table>
			<tr>
			<td>
				<img src="<c:url value='/images/loading1.gif'/>"/>
				</td>
				</tr>
				</table>
		</div>
</body>
</html>