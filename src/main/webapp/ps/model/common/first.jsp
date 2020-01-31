<!--first.jsp-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8"%>

<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<style>
<!--
html{height:100%;margin:0px;overflow:auto}
body{height:100%;margin:0px;overflow:auto}
-->
</style>
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
 
</head>


<body>

		<div style="width:99%;height:98%;float:left;padding:4px">
	        <div style="width:99%;height:98%;  title="<fmt:message key='buluchaxun'/>" 
	                data-options="collapsible:true,maximizable:true,closable:true" style="width:60%;height:500px;float:left;padding:4px">
	          <iframe frameborder=0 width=100% height=100% src="<c:url value='/dataFill.do?method=getReportGuide&levelFlag=1&flagtap=1'/>" frameborder="0px" scrolling="auto" noresize="noresize"></iframe>
	        </div>
	    </div>
 	   
	   <%--  <div style="width:38%;height:90%;float:left ;padding:4px">
	        <div class="easyui-panel" fit="true" title="<fmt:message key='welcome.fileListTitle'/>" 
	                data-options="collapsible:true,maximizable:true,closable:true">
	          <iframe frameborder=0 width=100% height=100% src="<c:url value='/workinstructionsAction.do?method=getRightdefaultwork'/>" frameborder="0px" scrolling="auto" noresize="noresize"></iframe>
	        </div>
	    </div>
	 --%>

</body>
</html>
