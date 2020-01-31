<%@ include file="/ps/framework/common/taglibs.jsp"%>

<html>
<head>
<%
	response.setHeader("P3P","CP=CAO PSA OUR");
%>
        <title><fmt:message key="webapp.welcome"/></title>        
</head>

<c:if test="${topStyle=='sc' }">
<frameset rows="126,*"  frameborder="0" border="0" framespacing="0">
</c:if>
<c:if test="${topStyle=='standard' }">
<frameset rows="96,*"  frameborder="0" border="0" framespacing="0">
</c:if>
  <frame src="<c:url value='/loginAction.do?method=skipTopPage'/>" name="topFrame" scrolling="NO" noresize  marginwidth="0" marginheight="0">
  <frameset  cols="190,*" framespacing="0" frameborder="0" border="0">
    <frame src="<c:url value='/menu.do'/>" name="leftFrame" scrolling="auto" style="overflow-x: hidden" noresize  target="mainFrame">
    <frame src="<c:url value = '/loginAction.do?method=queryNewFile' />" name="mainFrame" marginwidth="0" marginheight="0" scrolling="yes" noresize>
    <!-- <frame src="br.jsp" name="mainFrame" marginwidth="0" marginheight="0" scrolling="yes" noresize> -->
  </frameset>

</frameset>

</html>
