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
<script language=JavaScript>
 

 function checkpsd(){
     if(document.userForm.password.value != '' && !document.userForm.password.value.match("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$")) {
	      
	         alert("<fmt:message key="error.invalidpassword"/>");
	         return false;
	      }
	   if(document.userForm.repassword.value != '' && !document.userForm.repassword.value.match("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$")) {
	      
	         alert("<fmt:message key="error.invalidpassword"/>");
	         return false;
	      }
 }

	<c:if test="${mag=='0'}">
	alert("<fmt:message key='logpage.update.password'/><fmt:message key='repfile.statistics.error'/>");
	</c:if>
	<c:if test="${mag=='1'}">
	alert("<fmt:message key='usermanage.sysadmin.refpassword1'/>");
	</c:if>
	<c:if test="${mag=='2'}">
	alert("<fmt:message key='usermanage.sysadmin.refpassword1'/>");
	</c:if>

	function denglogin(){
	   window.location.href='<c:out value="${hostPrefix}" /><c:url value="/loginAction.do" />?method=enterLogin';
	} 
   function validate(){
		if(document.userForm.password.value == "" || document.userForm.password.value.length<6 || document.userForm.password.value.length>10)
		{ 
			alert("<fmt:message key="error.password"/>");
		    return false;
		}
		if(document.userForm.password.value!=document.userForm.repassword.value)
		{
			alert("<fmt:message key="error.repassword"/>");
		    return false;
		}	
     if(document.userForm.password.value != '' && !document.userForm.password.value.match("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$")) {
	         alert("<fmt:message key="error.invalidpassword"/>");
	         return false;
	      }
	   if(document.userForm.repassword.value != '' && !document.userForm.repassword.value.match("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$")) {
	      
	         alert("<fmt:message key="error.invalidpassword"/>");
	         return false;
	      }
		return true;
	}

</script>

<body leftmargin="0" >
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f05.gif"/>" valign="top">
    <p>
    </p>

<table cellSpacing=1 cellPadding=0 width="380" align=center border=0 class="TableBGColor">

<html:form action="userAction?method=updatepassword2" method="post" onsubmit="return validate()">
<%String tt=(String)request.getAttribute("pkid"); 
%>

         <html:hidden property="pkid" value="<%=tt %>" />   
			<c:set var="pageButtons">			   
			 <tr>
				<td width="90" height="22" class="TdBgColor1" align="right"></td>
				<td class="TdBgColor2"><input name="Input" type="submit" value="<fmt:message key="button.confirm"/>" style="width:60;" />
			        	<input   type="button"    value="<fmt:message key='user.loginAgain'/>" onclick="denglogin();" style="width:80;" />
				</td>
			</tr>
			</c:set>
			<br>
			    <tr>
			        <td class="TdBGColor1" width="90" align="right">
			        	<fmt:message key="usermanage.sysadmin.label.ypassword" />
			        </td>
			        <td class="TdBGColor2">
			            <html:password maxlength="15"  property="ypassword" style="width:150;"  value=""  />			            
			        </td>

			    </tr>	
			    <tr>
			        <td class="TdBGColor1" width="90" align="right">
			        	<fmt:message key="usermanage.sysadmin.label.newpassword" />
			        </td>
			        <td class="TdBGColor2">
			            <html:password maxlength="15"  property="password" style="width:150;"  value="" onchange="checkpsd(this);" />			            
			        </td>

			    </tr>			    
			    <tr>
			        <td class="TdBGColor1" width="90" align="right">
			        	<fmt:message key="usermanage.sysadmin.label.cfnewpassword" />
			        </td>
			        <td class="TdBGColor2">
			            <input maxlength="15"  type="password" name="repassword" style="width:150;" value="" onchange="checkpsd(this);" />			            
			        </td>
			    </tr>
			<c:out value="${pageButtons}" escapeXml="false" />
			    <tr>
					<td class="FormBottom"  colspan="2" height="17">
					
					</td>
				</tr>			
			</html:form>
			
</table>
</td>
</tr>
</table>
<script language=JavaScript>

       
		
</script>
</body>
</html>


