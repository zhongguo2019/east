<%@ page language="java" isErrorPage="true" %>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>

<!-- I do not integrate this page as a tile, but rather as a standalone-page -->
<html >
<head>
	<title><fmt:message key="webapp.prefix"/></title>
	<script language="JavaScript">
	function hideshow(){
		var form = document.getElementById("texa");
		var flag = document.getElementById("flag").value;
		if(flag=="0"){
			form.style.display="block";
			document.getElementById("flag").value="1";
		}else{
			form.style.display="none";
			document.getElementById("flag").value="0";
		}
		
	}
	
	</script>
<link rel="stylesheet" href="<c:url value='/ps/uitl/jmenu/css/styles.css'/>" type="text/css" />
	<link rel="stylesheet" href="<c:url value='/ps/uitl/jmune/css/jquery-tool.css'/>" type="text/css" />
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${eaUIPath}/themes/bootstrap/easyui.css'/>" />
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${eaUIPath}/themes/icon.css'/>" /> 
	<script type="text/javascript" src="<c:url value='${eaUIPath}/jquery.min.js'/>"></script> 
	<script type="text/javascript" src="<c:url value='${eaUIPath}/jquery.easyui.min.js'/>"></script> 	
</head>
<style>
html body{overflow:auto;scrolling:auto}

</style>
<body bgcolor="#eeeeee">

	<div class="easyui-panel" style="padding: 10px; height: 100%">

		<div>
			<div id="content">
				<div
					style="background: #FFCCCC url('ps/framework/images/error.png') no-repeat left center; padding: 10px; padding-left: 40px; margin:5px;border: 1px dashed #FF6666">
					<fmt:message key="errorPage.heading" />
					<a href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-search'" style="width: 80px"
						onclick="hideshow();"><fmt:message key="view.more" /></a> <a
						href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-reload'" style="width: 80px">Reload</a>
					<a href="#" class="easyui-linkbutton"
						data-options="iconCls:'icon-help'" style="width: 80px">Help</a>
				</div>
				<div
					style="color: #000000; background: #FFFFFF url('') no-repeat left center; padding: 10px;margin:5px; border: 1px dashed #FF6666;">
				<b>messages:</b>
				<c:out value="${errorMessages}" />
				</div>
				<input type="hidden" value="0" id="flag" />

				<div id="texa"
					style="color: #000000; background: #FFFFFF url('') no-repeat left center; display: none; padding: 10px;margin:5px;border: 1px dashed #FF6666;">
					<b>Exception:</b><br><c:out value="${errors}" />
				</div>



				<%-- Error Messages --%>
				<logic:messagesPresent>
					<logic:present name="error">
						<div class="error">
							<html:messages id="error">
								<c:out value="${error}" escapeXml="false" />
								<br />
							</html:messages>
						</div>
					</logic:present>
				</logic:messagesPresent>
				<%
					if (exception != null) {
				%>
				<pre>
					<%
						exception.printStackTrace(new java.io.PrintWriter(out));
					%>
				</pre>
				<%
					} else if ((Exception) request
							.getAttribute("javax.servlet.error.exception") != null) {
				%>
				<pre>
					<%
						((Exception) request
									.getAttribute("javax.servlet.error.exception"))
									.printStackTrace(new java.io.PrintWriter(out));
					%>
				</pre>
				<%
					} else if (pageContext
							.findAttribute("org.apache.struts.action.EXCEPTION") != null) {
				%>
				<bean:define id="exception2"
					name="org.apache.struts.action.EXCEPTION"
					type="java.lang.Exception" />
				<c:if test="${exception2 != null}">
					<pre>
						<%
							exception2.printStackTrace(new java.io.PrintWriter(out));
						%>
					</pre>
				</c:if>
				<%-- only show this if no error messages present --%>
				<c:if test="${exception2 == null}">
					<fmt:message key="errors.none" />
				</c:if>
				<%
					}
				%>
			</div>
		</div>
</body>
</html>
