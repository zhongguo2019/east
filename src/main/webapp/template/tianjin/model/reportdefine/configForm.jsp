<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/meta.jsp"%>
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
        
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
		<%
		   String areaCode  = request.getAttribute("areaCode").toString();
		%>
</head>
<body leftmargin="0" bgcolor=#eeeeee >
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
     <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
            <p style="margin-top: 3"><font class=b><b><fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.organ.config.add"/></b></font></p>                         
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" background="<c:url value="/theme/default/skin_01/imgs/f05.gif"/>" valign="top">
    <p>
    </p>

<table  align="center" class="TableBGColor" width="400" border="0" cellSpacing=1 cellPadding=5 height="200">    
<html:form action="reportConfigAction" method="post" styleId="reportConfigForm">
		<input id = "areaCode" type="hidden" value="<%=areaCode %>" />
		<html:hidden property="pkid" />
		<c:set var="pageButtons">
			<tr align="center" class="BtnBgColor" height="18">
				<td class="TdBGColor1" align="right"></td>
			    <td class="buttonBar" align="left">			         
			          <input name="method.save" type="submit" value="<fmt:message key="button.save" />" style="width:60;" onClick="return  validatesave();">			          
			          <input name="method.list" type="submit" value="<fmt:message key="button.cancel" />" style="width:60;" onClick="return  validates();">
			     </td>
			</tr>
		</c:set>
		<br>
        <br>
		
		 <tr style="height:18">
			<td class="TdBGColor1" align="right" ><fmt:message
				key="endyeardatacarry.reportid" /></td>
			<td class="TdBGColor2" style="width:280;height:20" width="300"><html:text  maxlength="20" property="reportId" style="height:20"  /><fmt:message
				key="usermanage.organ.form.notnull" /></td>
		</tr>

		<tr height="18">
			<td class="TdBGColor1" align="right"><fmt:message
				key="balanreport.list.reportname" /></td>
			<td class="TdBGColor2"><html:text maxlength="50" style="height:22" property="description"/><fmt:message
				key="usermanage.organ.form.notnull" /></td>
		</tr>

		<tr height="18">
			<td class="TdBGColor1" align="right"><fmt:message
				key="config.databasename" /></td>
			<td class="TdBGColor2"><html:text  maxlength="25" property="defChar" style="height:20"/><fmt:message
				key="usermanage.organ.form.notnull"/></td>
		</tr>

		<tr height="18">
			<td class="TdBGColor1" align="right"><fmt:message
				key="config.type" /></td>
			<td class="TdBGColor2">
	                      <html:select property="funId" style ="width:160;height:20">
			                <html:options collection="btypeList" property="dicid"
			                  labelProperty="dicname" /></html:select><fmt:message
							key="usermanage.organ.form.selectnotnull"/>
	        </td>			
		</tr>


		<c:out value="${pageButtons}" escapeXml="false" />
</html:form>
</table>
</td>
</tr>
</table>
<script type="text/javascript">
	function validatesave()
			{
				if(document.reportConfigForm.reportId.value == "")
				{
					alert("<fmt:message key="error.reportId"/>");
					document.reportConfigForm.reportId.focus();
				    return false;
				}
				
				if(document.reportConfigForm.description.value == "")
				{
					alert("<fmt:message key="error.description"/>");
					document.reportConfigForm.description.focus();
				    return false;
				}
				if(document.reportConfigForm.defChar.value == "")
				{
					alert("<fmt:message key="error.defChar"/>");
					document.reportConfigForm.defChar.focus();
				    return false;
				}
				
			//	var funid     = document.reportConfigForm.funid.value;
				var areaCode  = document.reportConfigForm.areaCode.value;
				var pkid      = document.reportConfigForm.pkid.value;
				document.reportConfigForm.action='<c:out value="${hostPrefix}" /><c:url value="/reportConfigAction.do" />?method=configsave&areaCode='+areaCode+'&pkid='+pkid;
				document.reportConfigForm.submit();
			}
		function validates(){
				document.reportConfigForm.action='<c:out value="${hostPrefix}" /><c:url value="/reportConfigAction.do" />?method=configlist';
				document.reportConfigForm.submit();
			
		}		

</script>

<p>&nbsp;</p>
</body>
</html>
