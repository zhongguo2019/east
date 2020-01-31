<!-- /ps/model/datafill/xlinputdata.jsp -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<c:if test="${error1=='-1'}">
	<script type="text/javascript">
		alert("<fmt:message key="usermanage.sysadmin.xlsinit.success"/>");
	</script>
</c:if>
<c:if test="${error2=='-1'}">
	<script type="text/javascript">
		alert("<fmt:message key="usermanage.sysadmin.xlsinit.fail"/>");
	</script>
</c:if>
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>

<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
<%-- <script type="text/javascript" src="<c:url value='${tableCss}/jqueryTab/jquery-1.4.1.min.js'/>"></script> --%>
<!-- <script type="text/javascript">
		 window.parent.document.getElementById("ppppp").value= "<fmt:message key="view.drblsj"/>";  
</script> -->
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
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" style="overflow-y:hidden ">
	 <div id="check"
		style="filter: alpha(opacity = 0); visibility: hidden; position: absolute; top: 100; left: 260; z-index: 10; width: 200; height: 74">
		<table WIDTH='100%' BORDER='0' CELLSPACING='0' CELLPADDING='0'>
			<tr>
				<td width='50%' >
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

	<table border="0" cellpadding="0" cellspacing="0" width="100%" height="70%">
				<html:form action="xlsInit" method="post" enctype="multipart/form-data" styleId="xlsInitForm">
							<tr>
								<td>
									<input type="hidden" name="organId" value="<c:out value='${organId}'/>" />
									
									<input type="hidden" name="reportId" value="<c:out value='${reportId}'/>" />
									<input type="hidden" name="dataDate" value="<c:out value='${dataDate}'/>" />
								</td>
							</tr>
						<tr align="center" height="30%">
							<td align="center" nowrap>
							<fmt:message key="usermanage.sysadmin.xls.name" />
								<input type="file" name="dataFile"  style="width:80%" id="dataFile"/>
							</td>
						</tr>
						
						<tr height="30%">
							<td align="center">
						
						 	<a href="javascript:void(0);"   onclick="xlsInit();" class="easyui-linkbutton" style="width:80;height: 20;text-decoration: none;" ><fmt:message key="standard.dataFill.importData"/></a>
							</td>
							
						</tr>
					
				</html:form>
		
	</table>

	<script type="text/javascript">
		function validateFile(fileExc, fileObj) {
			if (fileObj=="") {
				alert("<fmt:message key="importexport.file.invalid"/>"
						+ fileExc);
				return false;
			} else {
				if (fileExc!=fileObj) {
					alert("<fmt:message key="importexport.file.invalid"/>"
							+ fileExc);
					return false;
				} 
				return true;
			}
		}

		function xlsInit() {
			 var oData = document.xlsInitForm.dataFile;
			var xlsfilename=oData.value;
			 if(xlsfilename==""){
				alert("<fmt:message key="importexport.file.nothing"/>");
				return false;
			}
			if(validateFile("xls",xlsfilename.substring(xlsfilename.lastIndexOf(".")+1))){
			 	document.getElementById("xlsInitForm").action = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/xlsInit.do?method=uploadExcelJHGZ";
				document.getElementById("xlsInitForm").submit();
			}
		}  
	</script>

</body>
</html>
