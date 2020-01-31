<!-- /ps/model/xlsinit/xlsinit. -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<c:if test="${error1=='-1'}" >
<script type="text/javascript">
 alert("<fmt:message key="usermanage.sysadmin.xlsinit.success"/>");
</script>
</c:if>
<c:if test="${error2=='-1'}" >
<script type="text/javascript">
 alert("<fmt:message key="usermanage.sysadmin.xlsinit.fail"/>");
</script>
</c:if>
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/common/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" />
<script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
<script type="text/javascript" src="<c:url value='/common/jscalendar/calendar.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/common/jscalendar/lang/zh-cn.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/common/jscalendar/calendar-setup.js'/>"></script>

</head>
<script language="JavaScript">
    function showMessage()
	{
	  var detailtd = null;
		var st = null
	    detailtd = document.getElementById("check");
	    st = detailtd.style;
	    st["visibility"]="";
	
	}
    
  //  window.parent.document.getElementById("ppppp").value= "<fmt:message key="reportdefine"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.sysadmin.xlsinit"/>";

</script>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<div id="check" style="filter: alpha(opacity=80); visibility:hidden; position: absolute; top: 100; left: 260; z-index: 10; width: 200; height: 74">
<table WIDTH='100%' BORDER='0' CELLSPACING='0' CELLPADDING='0' > <tr > <td width='50%' > <table WIDTH='100%' height='70' BORDER='0' CELLSPACING='2' CELLPADDING='0'> <tr> <td align='center'> <nobr>&nbsp;&nbsp;&nbsp;<fmt:message key="sysinit.button.init"/>&nbsp;&nbsp;&nbsp; </td> </tr> </table> </td> </tr> </table>
</div>

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
            <p style="margin-top: 3"><font class=b><b>
            <fmt:message key="reportdefine"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.sysadmin.xlsinit"/>
            </b></font></p>                         
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" background="<c:url value="/images/f05.gif"/>" valign="top"><br>
   	<br>
   	
	<html:form action="xlsInit" method="post"	enctype="multipart/form-data" styleId="xlsInitForm" >
		<table width="550" border="0" align="center" cellSpacing="1" cellPadding="0" class="TableBGColor">						
			<tr>
				<td class="TdBGColor1" width="100" align="right" >
					<fmt:message key="usermanage.sysadmin.xls.name" />
				</td>
				<td class="TdBGColor2" align="left">
					<html:file styleClass="file" property="dataFile" style="width:400" />
				</td>
			</tr>
			<tr>
				<td class="TdBGColor1" width="100" align="right" >
					<fmt:message key="usermanage.sysadmin.xml.name" />
				</td>
				<td class="TdBGColor2" align="left">
					<html:file styleClass="file" property="xmlFile" style="width:400" />
				</td>
			</tr>
						
			<tr align="center">
					<td class="TdBgColor1" colspan="2" align="center">
					<input name="method.xlsInit" type="button" value="<fmt:message key="usermanage.sysadmin.xlsinit"/>" style="width:110;" onclick="xlsInit();"></td>
			</tr>					
			<tr>
				<td width="200" height="16" colspan="2" class="FormBottom"></td>
			</tr>
		</table>
	</html:form>
</td>
</tr>
</table>

<script type="text/javascript">

function validateFile(fileExc,fileObj)
	{
		if(fileObj.value.length==0)
		{
			alert("<fmt:message key="importexport.file.invalid"/>"+fileExc);
		    return false;
		}
		else
		{
			var importFile = fileObj.value;
			var idx = importFile.lastIndexOf(".");
			if(idx < 0)
			{
				alert("<fmt:message key="importexport.file.invalid"/>"+fileExc);
				return false;
			}
			else
			{
				var importFileExc =  importFile.substr(idx+1);
				if(importFileExc != fileExc)
				{
					alert("<fmt:message key="importexport.file.invalid"/>"+fileExc);
					return false;
				}
			}
			return true;
		}
	}
	
function xlsInit(){
  var oData = document.xlsInitForm.dataFile;
  var oConfig = document.xlsInitForm.xmlFile;
  if (validateFile('xls',oData)) {
  	if (validateFile('xml',oConfig)) {
	 showMessage();
     document.xlsInitForm.action = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/xlsInit.do?method=xlsInit";
     document.xlsInitForm.submit();
     }
  }
  
}
</script>

</body>
</html>
