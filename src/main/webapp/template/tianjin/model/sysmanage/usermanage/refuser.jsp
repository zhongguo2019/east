<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
        
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
            <p style="margin-top: 3"><font class=b><b><fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.sysadmin.menu.resetpassword"/></b></font></p>                         
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

<html:form action="userAction?method=refupdatepassword" method="post" onsubmit="return validate()">
<%String tt=(String)request.getAttribute("pkid"); 
%>

<html:hidden property="pkid" value="<%=tt %>" />           
			<c:set var="pageButtons">			   
			 <tr>
				<td width="90" height="22" class="TdBgColor1" align="right"></td>
				<td class="TdBgColor2"><input name="Input" type="submit" value="<fmt:message key="button.confirm"/>" style="width:60;">
			</tr>
			</c:set>
			<br>
			    <tr>
			        <td class="TdBGColor1" width="90" align="right">
			        	<fmt:message key="usermanage.sysadmin.label.newpassword" />
			        </td>
			        <td class="TdBGColor2">
			            <html:password maxlength="10"  property="password" style="width:150;"  value="" onchange="checkpsd(this);" />			            
			        </td>

			    </tr>			    
			    <tr>
			        <td class="TdBGColor1" width="90" align="right">
			        	<fmt:message key="usermanage.sysadmin.label.cfnewpassword" />
			        </td>
			        <td class="TdBGColor2">
			            <input maxlength="10"  type="password" name="repassword" style="width:150;" onchange="checkpsd(this);" />			            
			        </td>
			    </tr>
			<c:out value="${pageButtons}" escapeXml="false" />
			    <tr>
					<td class="FormBottom" colspan="2" height="17"></td>
				</tr>			
			</html:form>
</table>
</td>
</tr>
</table>
<script LANGUAGE='JavaScript'>
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
	           if(document.userForm.password.value != '' && !document.userForm.password.value.match(/^[\x21-\x7e]{1,14}$/ig)) {
			      
			         alert("<fmt:message key="error.invalidpassword"/>");
			         return false;
			      }
			   if(document.userForm.repassword.value != '' && !document.userForm.repassword.value.match(/^[\x21-\x7e]{1,14}$/ig)) {
			      
			         alert("<fmt:message key="error.invalidpassword"/>");
			         return false;
			      }
				return true;
			}
		function checkpsd()
	       {
	           if(document.userForm.password.value != '' && !document.userForm.password.value.match(/^[\x21-\x7e]{1,14}$/ig)) {
			      
			         alert("<fmt:message key="error.invalidpassword"/>");
			         return false;
			      }
			   if(document.userForm.repassword.value != '' && !document.userForm.repassword.value.match(/^[\x21-\x7e]{1,14}$/ig)) {
			      
			         alert("<fmt:message key="error.invalidpassword"/>");
			         return false;
			      }
	       }
-->
</script>
</body>
</html>


