<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ page
	import="com.krm.ps.util.DateUtil,java.util.Date"%>
<head>
        <title><fmt:message key="webapp.welcome"/></title>
        
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/style/default.css'/>" /> 
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/common/jscalendar/calendar-win2k-1.css" title="win2k-cold-1'/>">
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/common/jscalendar/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/common/jscalendar/lang/zh-cn.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/common/jscalendar/calendar-setup.js'/>"></script>
</head>
 <style type="text/css">
/* body {
	background-repeat: no-repeat;
	background-color: #9CDCF9;
	background-position: 0px 0px;
} */
</style>
<html>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0"   bgcolor="#EDEDED" >
<%
   //String date = DateUtil.getDateTime("yyyy-MM-dd",new Date());
	String date = (String)request.getAttribute("date");
	//System.out.println("login===="+request.getAttribute("loginswitch"));
	//System.out.println("date=["+date+"]");
%>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%">
      <table border="0" cellpadding="0" cellspacing="0" width="486" align=center>
        <tr>
          <td width="100%"><img border="0" src="<c:url value="/plat/rhbz/img/a04.gif"/>" width="486" height="88"></td>
        </tr>
        <tr>
          <td width="100%" background="<c:url value="/plat/rhbz/img/a05.gif"/>" height="150" valign="top">
              <table border="0" cellpadding="0" cellspacing="0" width="100%">
              <html:form action="loginAction.do?method=login" method="post" >
			<c:set var="pageButtons">
                <tr align="center">
                  <td  height="60" width="100%">
			            <html:submit  property="method.save" style="width:70;" onclick="return true">
			            	  <fmt:message key="button.confirm"/>
			            </html:submit> &nbsp; &nbsp;&nbsp; &nbsp;
                  </td>
                </tr>
			</c:set>
                <tr>
                  <td width="100%">
                    <table border="0" cellpadding="0" cellspacing="0" width="100%">
                     <tr>
                    	<td height="5"></td>
                    </tr>
                      <tr>
                        <td width="125" height="28" align="right">
                          <font color="#FFFFFF"><fmt:message key="logpage.loginname"/></font>
                       </td>
                        <td>
                        	<html:text property="logonname" style="width:165" value="admin"/>
                        </td>
                      </tr>
                      <tr>
                        <td width="125" height="28" align="right">
                            <font color="#FFFFFF"><fmt:message key="logpage.password"/></font>
                        </td>
                        <td><html:password property="password" style="width:165" value="123123"/>
                        </td>
                      </tr>
                      <tr>
                        <td width="125" height="28" align="right">
                          <font color="#FFFFFF"><fmt:message key="logpage.date"/></font></td>
                        <td>
			        	<html:text property="logindate" value="<%=date%>"  style="width:165" readonly = "true"/>		            
			        </td>
                      </tr>
						<script type="text/javascript">
							Calendar.setup({
									inputField     :    "logindate",  
									ifFormat       :    "%Y-%m-%d",     
									showsTime      :    false
								});
						</script>
                    </table>
                  </td>
                </tr>
                
			    <c:out value="${pageButtons}" escapeXml="false" />
               </html:form>
              </table>
              </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

</body>
</html>
<script LANGUAGE='JavaScript'><!--

if(<%=request.getAttribute("a")%>!=null){
   alert("<fmt:message key="user.userpassword"/>");
}
if(<%=request.getAttribute("loginswitch")%>=='1'){
	alert("<fmt:message key="logpage.witch"/>");
}
document.forms[0].logonname.focus();
--></Script>
