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

<style type="text/css">
.table {
	width: 100%;
	padding: 0px;
	margin: 0px;
	font-family: Arial, Tahoma, Verdana, Sans-Serif;
	border-left: 1px solid #ADD8E6;
	border-collapse: collapse;
	background-color: #EEEEEE
}
/*table top to type*/
.table th {
	font-size: 12px;
	font-weight: 600;
	color: #303030;
	border-right: 1px solid #ADD8E6;
	border-bottom: 1px solid #ADD8E6;
	border-top: 1px solid #ADD8E6;
	letter-spacing: 2px;
	text-align: left;
	padding: 10px 0px 10px 0px;
	background: url(../images/tablehdbg.png);
	white-space: nowrap;
	text-align: center;
	overflow: hidden;
	
}
/*dan yuan ge yang shi*/
.table td {
	border-right: 1px solid #ADD8E6;
	border-bottom: 1px solid #ADD8E6;
	background: #EEEEEE;
	font-size: 12px;
	padding: 3px 3px 3px 6px;
	color: #303030;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
	
}
/*lan se dan yuan ge yang shi , zhu yao yong yu ge hang bian se*/
.table td.color {
	background: #edf7f9;
}
/*biao ge zhong chao lian jie yang shi*/
.table td a:link {
	font-weight: 400;
	color: #2259D7;
	text-decoration: none;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}

.table td a:visited {
	font-weight: 400;
	color: #2259D7;
	text-decoration: none;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}

.table td a:hover {
	font-weight: 400;
	text-decoration: underline;
	color: #303030;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}

.table td a:active {
	font-weight: 400;
	text-decoration: none;
	color: #2259D7;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}

.btn {
	BORDER-RIGHT: #7b9ebd 1px solid;
	PADDING-RIGHT: 2px;
	BORDER-TOP: #7b9ebd 1px solid;
	PADDING-LEFT: 2px;
	FONT-SIZE: 12px;
	FILTER: progid :   DXImageTransform.Microsoft.Gradient (   GradientType
		=   0, StartColorStr =   #ffffff, EndColorStr =   #cecfde );
	BORDER-LEFT: #7b9ebd 1px solid;
	CURSOR: hand;
	COLOR: #0D82AE;
	PADDING-TOP: 2px;
	BORDER-BOTTOM: #7b9ebd 1px solid;
	background-color: #FFFFFF
}
.InputStyle{
background-color:#FF2525
}
</style>

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
				<td width='50%' bgcolor='#104A7B'>
					<table WIDTH='100%' height='70' BORDER='0' CELLSPACING='2'
						CELLPADDING='0'>
						<tr>
							<td bgcolor='#EEEEEE' align='center'><nobr>
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
			 <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
							<p style="margin-top: 3">
								<font class=b><b><fmt:message key="view.drblsj"/></b></font>
							</p>
						</td>
						<td></td>
					</tr>
				</table>
			</td>
		</tr>
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
