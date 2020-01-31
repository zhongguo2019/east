<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>


<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
<script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/prototype.js'/>"></script>
	
<script language=JavaScript>
	function switchs(value)
	{
			window.loginForm.action = "<c:out value="${hostPrefix}" />" + 
			"<c:url value='/loginAction.do?method=updateLoginSwitch&switchvalue='/>"+value;
			
			window.loginForm.submit();
	}
</script>	
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<form name="loginForm" method="post" action="loginAction.do?method=updateLoginSwitch">
<center>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
	<tr>
		<td width="100%" background="${imgPath}/f02.gif" height="36">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td width="19"><img border="0" src="${imgPath}/f01.gif" width="19"
					height="36"></td>
				<td width="42"><img border="0" src="${imgPath}/f03.gif" width="33"
					height="16"></td>
				<td>
				<p style="margin-top: 3">
					<font class=b>
						<b>
							<fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.rightarrow"/><fmt:message key="login.switch"/>
				 		</b>
				 	</font>
				 </p>
				</td>
				<td></td>
			</tr>
			<tr>
			</tr>
			
		</table>
		</td>
	</tr>
	  <tr>
		<td width="100%" bgcolor="#EEEEEE" valign="top">
		<br>
		<br>
		<br>
		<br>
			<table width="90%" border="0" align="center" cellSpacing="0" cellPadding="0">
			<tr>
					<td width="200" align="right"  >
					<br>
						
					<br>
					</td>
					<td width="200" align="right"  >
					<br>
						<fmt:message key='login.switch.state'/>
					<br>
					</td>
					<td >
					<br>
						<c:if test="${switch=='yes'}">
						<fmt:message key='login.switch.close'/>
						</c:if>
						<c:if test="${switch=='no'}">
						<fmt:message key='login.switch.open'/>
						</c:if>
					</td>
			  	</tr>
			  	<tr>
			  		<td align="right"  >
					<br>
						
					<br>
					</td>
					<td width="200" align="right">
					<br>
						<fmt:message key='login.switch.user.state'/>
					</td>
					<td >
					<br>
						<c:if test="${switch=='yes'}">
						<fmt:message key='login.switch.open.message'/>
						</c:if>
						<c:if test="${switch=='no'}">
						<fmt:message key='login.switch.close.message'/>
						</c:if>
					</td>
			  	</tr>
				<tr>
					<td height="22" align="right" ><br><br></td>
					<td >
					</td>
				</tr>
			  	<tr>
					<td height="22" align="right" ><br><br></td>
					<td align="right"  >
					<input type="button" value="<fmt:message key='login.open'/>" style="width:80;" onclick="switchs('no');">
					</td>
					<td align="lift"  >&nbsp;&nbsp;
						<input type="button" value="<fmt:message key='login.close'/>" style="width:80;" onclick="switchs('yes');">
					</td>
				</tr>
			  	<tr>
					<td width="50" height="16" colspan="2" ></td>
				</tr>	
			</table>
		</td>
	  </tr>
</table>
</center>
</body>
</html>