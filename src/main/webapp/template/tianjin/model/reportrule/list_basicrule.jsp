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
<c:set var="reportButton">
	<fmt:message key="place.select"/>
</c:set>
<c:set var="colNames">
	<fmt:message key="exportXML.tree.org.name"/>
</c:set>
<c:set var="reportTreeURL">
	<c:out value="${hostPrefix}"/><c:url value='/reportView.do?method=repTreeAjax${params}'/>
</c:set>
<c:set var="targetTreeURL">
	<c:out value="${hostPrefix}" /><c:url value='/integratedQuery.do?method=targetTree' />
</c:set>
<c:set var="targetButton">
	<fmt:message key="integratedQuery.button.targetSelect"/>
</c:set>
<c:set var="displaySumType">
	<%= FuncConfig.getProperty("reportview.showCollectType")%>
</c:set>
<c:set var="studentLoanReportId">
	<%= FuncConfig.getProperty("studentloan.reportid")%>
</c:set>
<html>
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
	
}
</script>
    <head>
        <title><fmt:message key="webapp.prefix"/></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" />
<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>    
<script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script>
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value='/ps/scripts/reportrule/edit_reportrule.js'/>"></script>
 </head>					


<script language=JavaScript>
function  listReportRule(){
	var df=document.reportFormatForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/reportrule.do" />?method=listBasicRule';
	df.submit();
}
function addReportRule(){
	var df=document.reportFormatForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/reportrule.do" />?method=enterBasicRule';
	df.submit();
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
				window.location.href="reportrule.do?method=listBasicRule&levelFlag=1";
				alert('<fmt:message key="symbol.bracket.left"/>'+val2+'<fmt:message key="symbol.bracket.right"/>'+result);
			}
		});
}
/*
function importFile(val1){
	  var params={"flag": val1,"method":"exportSystemData"};
		$.ajax({ 
			type: "POST", 
			async: false, //ajax
			url: "reportrule.do", 
			data: jQuery.param(params), 
			success: function(result){
				
			}
		});
}
*/
function changestocktype(val){
	if('812'==val){
		$('#guquan').show();
	}else{
		$('#guquan').hide();
	}
}
</script>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0"  onload="forbiddon();">

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td> 
				<p style="margin-top: 3"><font class=b color="#000000"><b><fmt:message key="view.blgzpz"/>>><fmt:message key="view.jblgzpz"/> </b></font></p>     
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
		<table border="0" width="98%" align="left" cellSpacing=0 cellPadding=0>
			<tr>
				<td height="30" align="left">
					<form name="reportFormatForm" action="reporFormat.do?method=listReportFormats" method="post">
					<input type="hidden" value="<c:out value="${levelFlag}"/>" name="levelFlag"/>
									&nbsp;&nbsp;<fmt:message key="view.gzlx"/>
									<select name="repruletype">
										<option value="0"><fmt:message key="view.sylx"/></option>
										<c:forEach items="${ruleType}" var="type"><c:if
										 test="${ruletypeid==type.pkid}"><option value="<c:out
											 value="${type.pkid}"/>" selected><c:out value="${type.typename}"/></option></c:if>
											<c:if test="${ruletypeid!=type.pkid}"><option value="<c:out
											 value="${type.pkid}"/>"><c:out value="${type.typename}"/></option></c:if></c:forEach>
									</select>&nbsp;&nbsp;
									<fmt:message key="view.sjmx"/>
									<select name="reportn" onchange="changestocktype(this.value);">
										<option value="0"><fmt:message key="place.select"/><fmt:message key="view.sylx"/></option>
										<c:forEach items="${reportList}" var="rep"><c:if
										 test="${reportid==rep.pkid}"><option value="<c:out
											 value="${rep.pkid}"/>" selected><c:out value="${rep.name}"/></option></c:if>
							     				<c:if test="${reportid!=rep.pkid}"><option value="<c:out
											 value="${rep.pkid}"/>"><c:out value="${rep.name}"/></option></c:if></c:forEach>
									</select>&nbsp;&nbsp;
									<input type="button" value='<fmt:message key="button.search"/>' onClick="listReportRule()">&nbsp;&nbsp;
									<input type="button" value='<fmt:message key="button.add"/>' onClick="addReportRule()">
									<!-- <input type="button" value='<fmt:message key="view.pldc"/>' onClick="importFile('0')"> 
									<a href="reportrule.do?method=exportSystemData&flag=0"><fmt:message key="view.pldc"/></a> -->
					</form>
				</td>
			</tr>
          
			<tr>
				<td align="center">				
				    <div align="center" style=" width:98%">
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
							    	<c:if test="${rt.cstatus==0}"><fmt:message key="contrastrelation.start"/></c:if>								
								    <c:if test="${rt.cstatus==1}"><fmt:message key="contrastrelation.stop"/></c:if>
								</display:column>
								
								<display:column sort="true" headerClass="sortable" width="15%" 
							    	titleKey="reportrule.edit">	
							    <a href="reportrule.do?method=editTheReportRule&repid=<c:out value="${rt.modelid }"/>&rulecode=<c:out value="${rt.rulecode }"/>&rtarid=<c:out value="${rt.targetid }"/>&targetN=<c:out value="${rt.targetname }"/>&rtype=<c:out value="${rt.rtype }"/>&cflag=1&bflag=0&levelFlag=<c:out value="${levelFlag }"/>&isdetail=0&typename=<c:out value="${rt.typename }"/>&rename=<c:out value="${rt.reportname }"/>" ><fmt:message key="view.ckxx"/></a>&nbsp;&nbsp;							
								<a href="reportrule.do?method=editTheReportRule&repid=<c:out value="${rt.modelid }"/>&rtarid=<c:out value="${rt.targetid }"/>&targetN=<c:out value="${rt.targetname }"/>&rtype=<c:out value="${rt.rtype }"/>&cflag=1&bflag=0&levelFlag=<c:out value="${levelFlag }"/>&rulecode=<c:out value="${rt.rulecode }"/>" ><fmt:message key="view.pz"/></a>
								 <c:if test="${rt.cstatus==1}"><a href="javascript:deleteReportRule('<c:out value="${rt.rulecode }"/>','<c:out value="${rt.targetname}"/>')" ><fmt:message key="button.delete"/></a></c:if>
								</display:column>
							</display:table>
							<script type="text/javascript">
								<!--
									highlightTableRows("logs");
								//-->
							</script>
							</form>
				    </div>
				</td>
			</tr>
			<tr height=17><td></td></tr>
		
	    
	    
</table> 
</body>
</html>