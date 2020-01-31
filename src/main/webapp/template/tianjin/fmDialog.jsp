<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/style/default.css'/>" />    
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
        
		<link rel="stylesheet" href="<c:url value='ps/framework/images/css.css" type="text/css'/>"/>
</head>
<body leftmargin="0" bgcolor=#eeeeee>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
            <p style="margin-top: 3"><font class=b><b><fmt:message key="logpage.update.password"/></b></font></p>                         
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f05.gif"/>" valign="top">
    <p>
    </p>

<table cellSpacing=1 cellPadding=0 width="380" align=center border=0 class="TableBGColor">
<html:form action="userAction?method=updatepassword" method="post" onsubmit="return validate()">
           
			<c:set var="pageButtons">
			   
			 <tr>
				<td width="60" height="22" class="TdBgColor1" align="right"></td>
				<td class="TdBgColor2"><input name="Input" type="submit" value="<fmt:message key="button.confirm"/>" style="width:60;">
			     <td class="TdBGColor1" width=20 align="right">			        	
			        </td>
			</tr>
			</c:set>
			<br>
			    <tr>
			        <td class="TdBGColor1" width=60 align="right">
			        	<fmt:message key="usermanage.sysadmin.label.newpassword" />
			        </td>
			        <td class="TdBGColor2">
			            <html:password property="password" style="width:150;"  value="" />
			            
			        </td>
			        <td class="TdBGColor1" width=20 align="right">			        	
			        </td>
			    </tr>
			    
			    <tr>
			        <td class="TdBGColor1" width=60 align="right">
			        	<fmt:message key="usermanage.sysadmin.label.cfnewpassword" />
			        </td>
			        <td class="TdBGColor2">
			            <input type="password" name="repassword" style="width:150;" />
			            
			        </td>
			         <td class="TdBGColor1" width=20 align="right">			        	
			        </td>
			    </tr>
			<c:out value="${pageButtons}" escapeXml="false" />
			    <tr>
					<td class="FormBottom" colspan="3" height="17"></td>
				</tr>			
			</html:form>
</table>
</td>
</tr>
</table>
<script LANGUAGE='JavaScript'>
var  a="<c:out value="${ok}"/>";
if(a=="1")
{
     alert("<fmt:message key='usermanage.sysadmin.refpassword1'/>");

}
<!--
	function validate()
			{
				
				if(document.userForm.password.value == "" || document.userForm.password.value.length<6 || document.userForm.password.value.length>15)
				{ 
					alert("<fmt:message key="error.password"/>");
				    return false;
				}
				if(document.userForm.password.value!=document.userForm.repassword.value)
				{
					alert("<fmt:message key="error.repassword"/>");
				    return false;
				}	
				return true;
			}
	
-->
</script>
</body>
</html>


