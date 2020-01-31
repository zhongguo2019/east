<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>


<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>

<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.4.1.min.js'/>" type="text/javascript"></script>


</head>
<script language="JavaScript">
function fanhui(){
	location.href="repFileAction.do?method=openKettle";
}
$(document).ready(function(){
	var flag = $("#flag").val();
	var message = $("#message").val();
	if(flag==1){
		alert("<fmt:message key="kettle.fileData.successd"/>");
		$("#flag").val("");
		$("#message").val("");
	}
	if(flag==6){
		alert(message);
		$("#flag").val("");
		$("#message").val("");
	}
	
});
</script>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">


	<table border="0" cellpadding="0" cellspacing="0" width="100%" height="80%">
	<tr>
	<td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
				<p style="margin-top: 3">
								<font class=b><b><fmt:message key="view.kettle.upload"/></b></font>
							</p>
						</td>
						<td></td>
					</tr>
				</table>
			</td>
	</tr>
		<tr>
			<td width="100%" background="<c:url value="${imgPath}/f05.gif"/>"
				valign="top"><br> 
				 
				<br> 
				
				<html:form action="repFileAction" method="post" enctype="multipart/form-data" styleId="repFlFomatForm" target="_self">
					<table width="500" border="0" align="center" cellSpacing="1" cellPadding="0" class="TableBGColor">
		<input id="flag" value="<c:out value='${flag}'/>" type="hidden" />
		<input id="message" value="<c:out value='${message}'/>" type="hidden" />
							<tr>
								<td class="TdBgColor1" colspan="2" align="left">
								&nbsp;
								</td>
							</tr>
							<tr>
							<td class="TdBGColor1" width="100" align="right" nowrap="nowrap"><fmt:message
									key="kettle.name" /></td>
							<td class="TdBGColor2" align="left">
							<input 	type="text" style="width:200" name="ktrName" id="ktrName"/></td>
						</tr>
							
						<tr>
							<td class="TdBGColor1" width="100" align="right"><fmt:message
									key="usermanage.sysadmin.xls.name" /></td>
							<td class="TdBGColor2" align="left">
							<!-- <input 	type="file" style="width:400"  id="dataFile"/> -->
							<html:file styleClass="file" property="dataFile" style="width:400" />
							</td>
						</tr>
						
						<tr>
							<td class="TdBGColor1" width="100" align="right"><fmt:message
									key="kettle.remark1" /></td>
							<td class="TdBGColor2" align="left">
							<textarea cols="46" rows="5" name="ktrRemark" id="ktrRemark"></textarea></td>
						</tr>
						
						<tr align="center">
							<td class="TdBgColor1" colspan="2" align="center">
							<input type="button" value="<fmt:message key="button.back"/>" onClick="fanhui();" style="width: 60"/>&nbsp;
							<input type="button"
								value='<fmt:message key="standard.dataFill.importData"/>'
								 onclick="xlsInit();"></td>
						</tr>
						
					</table>
				</html:form></td>
		</tr>
	</table>

	<script type="text/javascript">
	
	
	
		function xlsInit() {
			var oData = document.repFlFomatForm.dataFile.value;
			var ktrName = document.getElementById("ktrName").value;
			var ktrRemark = document.getElementById("ktrRemark").value;
			
			if(ktrName==""){
				alert("<fmt:message key="view.kettle.upload.notnull"/>");
				return false;
			}
			if(oData==""){
				alert("<fmt:message key="usermanage.sysadmin.xls.info"/>");
				return false;
			}
			if(ktrRemark==""){
				alert("<fmt:message key="view.kettle.remark.notnull"/>");
				return false;
			}
			var lengthed = huolength(ktrRemark);
			if(lengthed>2048){
				alert("<fmt:message key="view.kettle.remark.lengthed"/>");
				return false;
			}
			
			
			var fileData = oData.substring(oData.lastIndexOf('.') + 1,oData.length);
			if(fileData!="ktr"){
				alert("<fmt:message key="kettle.fileData"/>");
				return false;
			} 

			document.repFlFomatForm.action = "repFileAction.do?method=uploadKtr&oData="+oData;
			document.repFlFomatForm.submit();
		}
		
		
		function huolength(str){
			
			var realLength = 0, len = str.length, charCode = -1;
		    for (var i = 0; i < len; i++) {
		        charCode = str.charCodeAt(i);
		        if (charCode >= 0 && charCode <= 128) realLength += 1;
		        else realLength += 2;
		    }
		    return realLength;
		}
	</script>

</body>
</html>
