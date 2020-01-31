<!-- /ps/model/datavalidation/com_logicrule. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
 

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
	df.action='<c:out value="${hostPrefix}" /><c:url value="/datavalidation.do" />?method=enterComLogicRule';
	df.submit();
}

function  listRulecheck(){
	var df=document.reportFormatForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/datavalidation.do" />?method=listRulecheckPercen';
	df.submit();
}
</script>
<body >
                   
					<form name="reportFormatForm" action="reporFormat.do?method=listReportFormats" method="post" style="margin: 0;">
					<input type="hidden" value="<c:out value="${levelFlag}"/>" name="levelFlag"/>
					<div  name="optionbar" class="navbar2" >
						<table>
							<tr>
								<td><fmt:message key="view.gzlx"/></td>
								<td>
									<select name="repruletype">
										<option value="0"><fmt:message key="view.sylx"/></option>
										<c:forEach items="${ruleType}" var="type"><c:if
										 test="${ruletypeid==type.pkid}"><option value="<c:out
											 value="${type.pkid}"/>" selected><c:out value="${type.typename}"/></option></c:if>
											<c:if test="${reportType_reportFormat!=type.pkid}"><option value="<c:out
											 value="${type.pkid}"/>"><c:out value="${type.typename}"/></option></c:if></c:forEach>
									</select>
								</td>
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
								</td>
								  
								<td>
									<a href="javascript:void(0);" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-search'"  name="ss" onclick="return listReportRule();" ><fmt:message key='button.search'/></a>
								    <a href="javascript:void(0);" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-search'"  name="ss" onclick="return listRulecheck();" ><fmt:message key='reprule.check.searchdetailed'/></a>
								</td>
							</tr>
						</table>
					</div>
					 
					</form>	

				    <div align="center" style=" width:100%">
						 
						<display:table name="rulecheckpercenList" cellspacing="0" cellpadding="0"  
							    requestURI="" id="rt" width="100%"  pagesize="900"
							    styleClass="list reportsList" >
							    
							    <display:column sort="true" 
							    	headerClass="sortable" width="15%" 
							    	titleKey="reportrule.rulecode">
							    	<c:out value="${rt[0]}" />
							    </display:column>
							      <display:column sort="true" 
							    	headerClass="sortable" width="30%"   
							    	titleKey="reportrule.modelname">
							    	<c:out value="${rt[1]}" />
							    </display:column>
                                <display:column sort="true" 
							    	headerClass="sortable" width="25%" 
							    	titleKey="reportrule.ruleitem">
							    	<c:out value="${rt[2]}" />
							    </display:column>
			                   <display:column sort="true" headerClass="sortable" width="15%" 
							    	titleKey="common.statistics.overtime">
							    	<c:out value="${rt[3]}" />
								</display:column>
								<display:column sort="true" headerClass="sortable" width="15%" 
							    	titleKey="reportrule.status">
							    	<c:if test="${rt[3]!=null}"><fmt:message key="reprule.check.status.ok"/>	</c:if>								
								    <c:if test="${rt[3]==null}"><fmt:message key="reprule.check.status.not"/>	</c:if>
								</display:column>
								
							</display:table>
							
				    </div>

		 
	    
	    


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