<!-- /ps/model/datafill/LeftReportGuide.jsp -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>


<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>

<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
 <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
</head>

 <script type="text/javascript">

</script>

<body>
				<table border="0" width="100%"  align="center" cellSpacing=0 cellPadding=0>
					
								<form name="reportDataForm" action="dataFill.do?method=getDataByCondition"
									method="post">
									<input type="hidden" name="organId" value="<c:out value="${organId }"/>" id="organIds" />
									<input type="hidden" name="reportDate" value="<c:out value="${dataDate }"/>" id="reportDates" />
									<input type="hidden" name="reportIds" value="<c:out value="${reportIds}"/>" id="rIds" />
								    <input type="hidden" name="treportIds" value="<c:out value="${treportIds}"/>" id="tIds" />
							       
									<display:table name="reportList" cellspacing="0" cellpadding="0"  
							   		 	requestURI="" id="rt" width="100%"  pagesize="18" 
							    		styleClass="list reportsList" >
							    		
							    		
							    	<c:if test="${leveType != 1}"> 	
									
											<display:column sort="true" headerClass="sortable" width="30%"
												titleKey="reportguideorgan">
												<c:out value="${organInfo.full_name}" />
											</display:column>
											<display:column sort="true" headerClass="sortable" width="30%"
												titleKey="reportguide">
												<c:out value="${rt[3]}" />
											</display:column>
											<display:column sort="true" headerClass="sortable" width="10%"
												titleKey="reportguidejilu">
												<c:out value="${rt[2]}" />
											</display:column>
											<display:column sort="true" headerClass="sortable" width="10%"
												titleKey="reportguidejiluy">
												<c:out value="${rt[7]}" />
											</display:column>
											<display:column sort="true" headerClass="sortable" width="30%"
												titleKey="reportrule.edit">
											<c:if test="${rt[5]!=0}">
												<a style="text-decoration: none;" href="#" onclick="window.parent.parent.window.addPaneltab('状态查询','dataFill.do?method=getReportGuideDetailed&organId=<c:out value="${rt[1]}" />&reportId=<c:out value="${rt[0]}" />&targetTable=<c:out value="${rt[4]}" />&reportDate=<c:out value="${dataDate }"/>')"><fmt:message key="view.xxxx"/></a>&nbsp;&nbsp;								
											</c:if> 
											<c:if test="${rt[2]!=0}">
													<a style="text-decoration: none;" href="#" onclick="window.parent.parent.window.addPaneltab('数据补录','dataFill.do?method=editReportDataEntryForDataInput&openmode=_self&canEdit=1&levelFlag=0&organCode=<c:out value="${rt[1]}" />&reportDate=<c:out value="${dataDate }"/>&reportId=<c:out value="${rt[0]}" />&flag2=1&flagtap=<c:out value="${flagtap}" />')"><fmt:message key="view.sjbl"/></a>&nbsp;&nbsp;
											</c:if>	
										
											</display:column>
									</c:if>	
							    	
									</display:table>
									
								</form>
							
					
				</table>

</body>
</html>
