<!-- xlinputdata0.j -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<c:if test="${error1=='-1'}">
	<script type="text/javascript">
		alert("<fmt:message key="usermanage.sysadmin.xlsInit0.success"/>");
	</script>
</c:if>
<c:if test="${error2=='-1'}">
	<script type="text/javascript">
		alert("<fmt:message key="usermanage.sysadmin.xlsInit0.fail"/>");
	</script>
</c:if>
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />


<script type="text/javascript">
		 window.parent.document.getElementById("ppppp").value= "<fmt:message key="view.drblsj"/>";  
</script>
</head>
<script language="JavaScript">
	function showMessage() {
		var detailtd = null;
		var st = null
		detailtd = document.getElementById("check");
		st = detailtd.style;
		st["visibility"] = "";

	}
	
</script>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
	<div id="check"
		style="filter: alpha(opacity = 80); visibility: hidden; position: absolute; top: 100; left: 260; z-index: 10; width: 200; height: 74">
		<table WIDTH='100%' BORDER='0' CELLSPACING='0' CELLPADDING='0'>
			<tr>
				<td width='50%'  >
					<table WIDTH='100%' height='70' BORDER='0' CELLSPACING='2'
						CELLPADDING='0'>
						<tr>
							<td  align='center'><nobr>
									&nbsp;&nbsp;&nbsp;
									<fmt:message key="sysinit.button.init" />
									&nbsp;&nbsp;&nbsp; </td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>

	<table border="0" cellpadding="0" cellspacing="0" width="100%"
		height="100%">
		<tr>
			<td width="100%" background="<c:url value="${imgPath}/f05.gif"/>"
				valign="top"><br> <br> 
				<html:form action="xlsInit0"
					method="post" enctype="multipart/form-data" styleId="xlsInitForm0">
					<table width="550" border="0" align="center" cellSpacing="1"
						cellPadding="0" class="TableBGColor">
							<tr>
								<td class="TdBgColor1" colspan="2" align="lift">
									<input type="hidden" name="organId" value="<c:out value='${organId}'/>" />
									<input type="hidden" name="reportId" value="<c:out value='${reportId}'/>" />
									<input type="hidden" name="dataDate" value="<c:out value='${dataDate}'/>" />
									<%-- &nbsp;<a href="dataFill.do?method=exportTemplate&organId=<c:out value='${organId}'/>&reportId=<c:out value='${reportId}'/>&dataDate=<c:out value='${dataDate}'/>"><fmt:message key="repFile.exp"/></a>
								 --%></td>
							</tr>
						<tr>
							<td class="TdBGColor1" width="100" align="right"><fmt:message
									key="usermanage.sysadmin.xls.name" /></td>
							<td class="TdBGColor2" align="left">
							<html:file
									styleClass="file" property="dataFile" style="width:400" /></td>
						</tr>
						<tr align="center">
							<td class="TdBgColor1" colspan="2" align="center"><input
								name="method.xlsInit0" type="button"
								value='<fmt:message key="standard.dataFill.importData"/>'
								 onclick="xlsInit0();"></td>
						</tr>
						<tr>
							<td width="200" height="16" colspan="2" class="FormBottom"></td>
						</tr>
					</table>
					<c:if test="${message == '0'}">
						<table border='0' align="center"><tr><td border='0'><font style="color:#990000;"><b><fmt:message key="view.rkcg"/></b></font></td></tr></table>
					</c:if>
				</html:form></td>
		</tr>
	</table>

	<script type="text/javascript">
		function validateFile(fileExc, fileObj) {
			if (fileObj.value.length == 0) {
				alert("<fmt:message key="importexport.file.invalid"/>"
						+ fileExc);
				return false;
			} else {
				var importFile = fileObj.value;
				var idx = importFile.lastIndexOf(".");
				if (idx < 0) {
					alert("<fmt:message key="importexport.file.invalid"/>"
							+ fileExc);
					return false;
				} else {
					var importFileExc = importFile.substr(idx + 1);
					if (importFileExc != fileExc) {
						alert("<fmt:message key="importexport.file.invalid"/>"
								+ fileExc);
						return false;
					}
				}
				return true;
			}
		}

		function xlsInit0() {
			var oData = document.xlsInitForm0.dataFile;
			var xlsfilename=oData.value;
					document.xlsInitForm0.action = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/xlsInit0.do?method=uploadExcelYJH&xlsfilename="+xlsfilename+"&resultablename1=<c:out value='${resultablename1}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>";
					document.xlsInitForm0.submit();
		}
	</script>

</body>
</html>
