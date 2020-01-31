<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<link rel="stylesheet" type="text/css" media="all"
	href="<c:url value='/styles/default.css'/>" />
<link rel="stylesheet" href="images/css.css" type="text/css">
<script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<table border="0" cellpadding="0" cellspacing="0" width="100%"
	height="100%">
	<tr>
		<td width="100%" background="images/f02.gif" height="36">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td width="19"><img border="0" src="images/f01.gif" width="19"
					height="36"></td>
				<td width="42"><img border="0" src="images/f03.gif" width="33"
					height="16"></td>
				<td>
				<p style="margin-top: 3"><font class=b><b><fmt:message
					key="navigation.workfile.inbox" /></b></font></p>
				</td>
				<td></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td width="100%" bgcolor="#EEEEEE" valign="top">
		<table>
			<tr>
				<td align="left"><!-- deleteAll --> 
				<c:if test="${param.status == '9'}">
					<a href="workMail.do?method=delMail&pkId=0&status=9"> <fmt:message
						key="inbox.deleteAll" /> </a>
				</c:if> <c:if test="${empty param.status || param.status != '9'}">
					<a
						href="workMail.do?method=updateMail&pkId=0&status=<c:out value="${param.status}"/>">
					<fmt:message key="inbox.deleteAll" /> </a>

				</c:if> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <!-- refresh --> <a
					href="workMail.do?method=queryMail&status=<c:out value="${param.status }" />"> <fmt:message
					key="inbox.refresh" /> </a></td>
			</tr>
			<tr>
				<!-- examine -->
				<td><fmt:message key="inbox.examine" /> <!-- all --> <a
					href="workMail.do?method=queryMail"> <fmt:message key="inbox.all" />
				</a> &nbsp;-&nbsp; <!-- overRead --> <a
					href="workMail.do?method=queryMail&status=1"> <fmt:message
					key="inbox.overRead" /> </a> &nbsp;-&nbsp; <!-- notRead --> <a
					href="workMail.do?method=queryMail&status=0"> <fmt:message
					key="inbox.notRead" /> </a>  <!-- recycle --> </td>
			</tr>

			<tr>				
				<center>
				<div id="screen" style="width:60%"><display:table name="mailList"
					cellspacing="0" cellpadding="0" requestURI="" id="object"
					width="100%" styleClass="list mailList">

					
					<display:column sort="true" headerClass="sortable" width="5%"
						nowrap="no">
						<img border="0" src="images/xf.gif">
					</display:column>

					<display:column sort="true" headerClass="sortable" width="5%"
						nowrap="no">
						<img border="0" src="images/qbz.gif">
					</display:column>

					<!-- inbox.addresser -->
					<display:column sort="true" headerClass="sortable" width="12%"
						nowrap="no" titleKey="inbox.addresser">
						<c:out value="${object[8]}" />
					</display:column>

					<!-- inbox.title -->
					<display:column sort="true" headerClass="sortable" width="40%"
						nowrap="no" titleKey="inbox.title">
						<a href="#" onclick="window.open('workMail.do?method=viewMails&pkId=<c:out value='${object[0]}' />&fPkId=<c:out value='${object[3]}'/>','','toolbar=no,menubar=no,width=800,height=560,left='+(window.screen.width-800)/2+',top='+(window.screen.height-560)/2+',resizable=yes,scrollbars=yes')">
							<c:out value="${object[6]}" />
						</a>
					</display:column>

					<!-- inbox.date -->
					<display:column sort="true" headerClass="sortable" width="12%"
						nowrap="no" titleKey="inbox.date">
						<c:out value="${object[5]}" />
					</display:column>


					<!-- inbox.delete -->
					<display:column sort="true" headerClass="sortable" width="10%"
						nowrap="no">

						<c:if test="${object[2]=='9'}">
							<a
								href="workMail.do?method=delMail&pkId=<c:out value="${object[0]}" />&status=9">
							<fmt:message key="inbox.delete" /> </a>
						</c:if>

						<c:if test="${object[2]!='9'}">
							<a
								href="workMail.do?method=updateMail&pkId=<c:out value="${object[0]}" />&status=<c:out value="${object[2]}"/>">
							<fmt:message key="inbox.delete" /> </a>
						</c:if>
					</display:column>

				</display:table></div>
				</center>				
			</tr>
		</table>

		<script type="text/javascript">
					<!--
						highlightTableRows("object");
					//-->
				</script>
		</td>
	</tr>
</table>

</body>
</html>
