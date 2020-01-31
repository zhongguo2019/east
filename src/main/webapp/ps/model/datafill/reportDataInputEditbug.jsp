<!-- /ps/model/datafill/reportDataInputEditbug. -->
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<html>
<head>
<!-- reportDataEdit.jsp -->
<title><fmt:message key="webapp.prefix" /></title>

<script type="text/javascript">
		 window.parent.document.getElementById("ppppp").value= "<fmt:message key="navigation.dataFill.dataFilledit" />";  
</script>
</head>
<body leftmargin="0" topmargin="0">
	<table border="0" cellpadding="0" cellspacing="0" width="2000"
		height="100%">
		<tr>
			<td width="100%"   valign="top">
			     <c:out	value="${rtable}"/><fmt:message key="view.blbybstbcz" />
			     <c:out value="${requestScope['org.apache.struts.action.EXCEPTION'].message}"/>
		    </td>
		</tr>
	</table>
	
</body>
</html>
