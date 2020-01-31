<!-- /ps/model/reportrule/list_privaterule.jsp -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>



<html style="height: 100%;">
<script type="text/javascript">

function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
	
}

</script>


    <head>
        <title><fmt:message key="webapp.prefix"/></title>
        
        <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />


 </head>					


<script language=JavaScript>
function  listReportRule(){
	var df=document.reportFormatForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/reportrule.do" />?method=listPriRule';
	df.submit();
}
function addReportRule(){
	window.parent.window.OpenPanel('<c:out value="${hostPrefix}" /><c:url value="/reportrule.do" />?method=enterPriRule&levelFlag=1','600','420','<fmt:message key="view.ywzbgzpi"/> >> <fmt:message key="view.yssjgzpz"/>');
}

function deleteReportRule(val1,val2){
	if(!confirm('<fmt:message key="view.delete"/>')){
		return;
	}
	  var params={"rulecode": val1,"method":"deleteReportRule"};
		$.ajax({ 
			type: "POST", 
			async: false, //ajax
			url: "reportrule.do", 
			data: jQuery.param(params), 
			success: function(result){
				window.location.href="reportrule.do?method=listPriRule&levelFlag=1";
				alert('<fmt:message key="symbol.bracket.left"/>'+val2+'<fmt:message key="symbol.bracket.right"/>'+result);
			}
		});
}


</script>

<body style="height: 100%;" onload="forbiddon();">
<div name="optionbar"  class="navbar">

					<form name="reportFormatForm" action="reporFormat.do?method=listReportFormats" method="post" style="margin: 0">
					<input type="hidden" value="<c:out value="${levelFlag}"/>" name="levelFlag"/>
					<table >
						<tr>
							<td ><fmt:message key="view.gzlx"/></td>
							<td><select name="repruletype" style="width: 100px">
										<option value="0"><fmt:message key="view.sylx"/></option>
										<c:forEach items="${ruleType}" var="type"><c:if
										 test="${ruletypeid==type.pkid}"><option value="<c:out
											 value="${type.pkid}"/>" selected><c:out value="${type.typename}"/></option></c:if>
											<c:if test="${ruletypeid!=type.pkid}"><option value="<c:out
											 value="${type.pkid}"/>"><c:out value="${type.typename}"/></option></c:if></c:forEach>
									</select>
							</td>
							<td>
								&nbsp;&nbsp;<fmt:message key="view.sjmx"/>
							</td>
							<td>
								<select sytle="height:100px" name="reportn" onchange="changestocktype(this.value);">
										<option value="0"><fmt:message key="place.select"/><fmt:message key="view.sylx"/></option>
										<c:forEach items="${reportList}" var="rep"><c:if
										 test="${reportid==rep.pkid}"><option value="<c:out
											 value="${rep.pkid}"/>" selected><c:out value="${rep.name}"/></option></c:if>
							     				<c:if test="${reportid!=rep.pkid}"><option value="<c:out
											 value="${rep.pkid}"/>"><c:out value="${rep.name}"/></option></c:if></c:forEach>
									</select>
							</td>
							<td>
								<a href="#" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-search'" style="height:20px;  width:80px;text-decoration: none;" name="ss" onclick="listReportRule();" ><fmt:message key='button.search'/></a>&nbsp;
								<a href="#" class="easyui-linkbutton buttonclass2"  data-options="iconCls:'icon-add'" onclick="addReportRule(); " style="height:20px;  width:80px;text-decoration: none;" ><fmt:message key='button.add'/></a>
							</td>
						
							
						</tr>
					</table>
									
								
									
									<!-- <input type="button" value='<fmt:message key="view.pldc"/>' onClick="importFile('0')"> 
									<a href="reportrule.do?method=exportSystemData&flag=0"><fmt:message key="view.pldc"/></a> -->
					</form>

				
</div>
				   
						<form name="logForm" action="viseAction.do?method=saveLogs" method="post">
						 	<% 
						 	int i = 0;
						 	%>
						<display:table name="reporRule" cellspacing="0" cellpadding="0"  
							    requestURI="" id="rt" width="100%"  pagesize="18"
							    styleClass="list reportsList" >
							    <%-- Table columns --%>
							    <%
							    	i++;
							    %>
							   
							    <display:column sort="true" 
							    	headerClass="sortable" width="15%" 
							    	titleKey="reportrule.rulecode">
							    	<c:out value="${rt.rulecode}"/>
							    </display:column>
							      <display:column sort="true" 
							    	headerClass="sortable" width="30%"   
							    	titleKey="reportrule.modelname">
							    	<c:out value="${rt.reportname}"/>
							    </display:column>
                                <display:column sort="true" 
							    	headerClass="sortable" width="20%" 
							    	titleKey="reportrule.ruleitem">
							    	<c:out value="${rt.targetname}"/>
							    </display:column>
			                   
			                    <display:column sort="true" 
							    	headerClass="sortable" width="15%" 
							    	titleKey="reportrule.typeanme">
							    	<c:out value="${rt.typename}"/>					    	
							    </display:column>
								
								<display:column sort="true" headerClass="sortable" width="5%" 
							    	titleKey="reportrule.status">
							    	<c:if test="${rt.cstatus==0}"> <fmt:message key="contrastrelation.start"/>	</c:if>								
								    <c:if test="${rt.cstatus==1}"><fmt:message key="contrastrelation.stop"/>	</c:if>
								</display:column>
								
								<display:column sort="true" headerClass="sortable" width="15%" 
							    	titleKey="reportrule.edit">	
							    <%-- <a href="reportrule.do?method=editThePriRule&repid=<c:out value="${rt.modelid }"/>&rulecode=<c:out value="${rt.rulecode }"/>&rtarid=<c:out value="${rt.targetid }"/>&targetN=<c:out value="${rt.targetname }"/>&rtype=<c:out value="${rt.rtype }"/>&cflag=1&bflag=0&levelFlag=<c:out value="${levelFlag }"/>&isdetail=0&typename=<c:out value="${rt.typename }"/>&rename=<c:out value="${rt.reportname }"/>" ><fmt:message key="view.ckxx"/></a>&nbsp;&nbsp; --%>							
								<a href="#" style="text-decoration: none;" onclick="window.parent.window.OpenPanel('reportrule.do?method=editThePriRule&repid=<c:out value="${rt.modelid }"/>&rulecode=<c:out value="${rt.rulecode }"/>&rtarid=<c:out value="${rt.targetid }"/>&rtype=<c:out value="${rt.rtype }"/>&cflag=1&bflag=0&levelFlag=<c:out value="${levelFlag }"/>&isdetail=0&typename=<c:out value="${rt.typename }"/>','600','400','<fmt:message key="view.ywzbgzpi"/> >> <fmt:message key="view.yssjgzpz"/> >> <fmt:message key="view.ckxx"/>')" ><fmt:message key="view.ckxx"/></a>&nbsp;&nbsp;
								<a href="#" style="text-decoration: none;" onclick="window.parent.window.OpenPanel('reportrule.do?method=editThePriRule&repid=<c:out value="${rt.modelid }"/>&rtarid=<c:out value="${rt.targetid }"/>&rtype=<c:out value="${rt.rtype }"/>&cflag=1&bflag=0&levelFlag=<c:out value="${levelFlag }"/>&rulecode=<c:out value="${rt.rulecode }"/>&flags=1','600','400','<fmt:message key="view.ywzbgzpi"/> >> <fmt:message key="view.yssjgzpz"/>','600','400','<fmt:message key="view.ywzbgzpi"/> >> <fmt:message key="view.yssjgzpz"/>')" ><fmt:message key="view.pz"/></a>
								 <c:if test="${rt.cstatus==1}"><a style="text-decoration: none;" href="javascript:deleteReportRule('<c:out value="${rt.rulecode }"/>','<c:out value="${rt.targetname}"/>')" ><fmt:message key="button.delete"/></a></c:if>
								</display:column>
							</display:table>
							</form>
				   

</body>
</html>