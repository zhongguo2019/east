<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<html>
<head>
        <title><fmt:message key="webapp.welcome"/></title>        
</head>

<c:if test="${topStyle=='sc' }">
<frameset rows="126,*"  frameborder="0" border="0" framespacing="0">
</c:if>
<c:if test="${topStyle=='standard' }">
<frameset rows="96,*"  frameborder="0" border="0" framespacing="0">
</c:if>
<c:if test="${sessionScope.system_id=='1' }">
	<frame src="plat/rhbz/top.jsp?ps=0" name="topFrame" scrolling="NO" noresize  marginwidth="0" marginheight="0" />
</c:if>

<c:if test="${sessionScope.system_id=='3' }">
	<frame src="plat/khfx/top.jsp?ps=1" name="topFrame" scrolling="NO" noresize  marginwidth="0" marginheight="0" />
</c:if>

<c:if test="${sessionScope.system_id=='2' }">
	<frame src="plat/yjh/top.jsp?ps=1" name="topFrame" scrolling="NO" noresize  marginwidth="0" marginheight="0" />
</c:if>
<c:out value="${sessionScope.system_id}"></c:out>

 
  <frameset  cols="180,*" framespacing="1" frameborder="0" border="0" >
    <frame src="menu.do" name="leftFrame" scrolling="auto" target="mainFrame"  noresize="noresize" >
    <frame src="dataFill.do?method=getReportGuide&levelFlag=1" name="mainFrame" marginwidth="90%" marginheight="0" scrolling="auto" noresize="noresize">
  </frameset>
</frameset>
<body onload="forbiddon();">
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}
</script>

</body>
</html>
