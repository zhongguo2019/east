<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
        <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
	    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table border="0" cellpadding="0" cellspacing="0" width="100%"
	height="100%">
	<tr>
		<td width="100%" bgcolor="#EEEEEE" valign="top"><br><br>
		<center><html:form action="workFile.do?method=searchFile">
			<table width="75%" border="0" align="center" cellSpacing="1" cellPadding="0" class="TableBGColor">
				<tr>
					<td class="TdBgColor1" width="15%" align="right"><fmt:message key="workFile.kind" /></td>
					<td class="TdBgColor1"><select name="kindId" style="width:180;">
						<option value="" selected><fmt:message key="workFile.all" /></option>
						<c:forEach items="${jobList}" var="jobType">
							<option value="<c:out value="${jobType.pkid}"/>"><c:out
								value="${jobType.dicname}" /></option>
						</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td class="TdBgColor1" width="15%" align="right"><fmt:message key="workFile.fileSource" /></td>
					<td class="TdBgColor1">
					<select name="fileSourceId" style="width:180;">
						<option value="" selected><fmt:message key="workFile.all" /></option>
						<c:forEach items="${derList}" var="derivationType">
							<option value="<c:out value="${derivationType.pkid}"/>"><c:out
								value="${derivationType.dicname}" /></option>
						</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td class="TdBgColor1" width="15%" align="right"><fmt:message key="workFile.issData" /></td>
					<td class="TdBgColor1">
					<input type="text" name="issDate" style="width:180;"
						readonly="true" value="<c:out value="${workFileForm.issDate}"/>" /> <script type="text/javascript">
				</script>
				</td>
				</tr>
				<tr>
					<td class="TdBgColor1" align="right"><fmt:message key="workFile.title2" /></td>
					<td class="TdBgColor2"><input type="text" name="title" size="50" value="" /></td>
				</tr>
				<tr>
					<td class="TdBgColor1" align="right" valign="top"><fmt:message key="workFile.content" /></td>
					<td class="TdBgColor2"><input type="text" name="content" id="content" size="50" value="" /></td>
				</tr>
				<tr>
					<td colspan="2" align="center" >
					     <input type="submit" class="easyui-linkbutton buttonclass2" name="submit" value="<fmt:message key="button.search"/>" style="width:100" />
					</td>
				</tr>
			</table>
		</html:form></center>
		</td>
	</tr>
</table>
</body>
</html>


