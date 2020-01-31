<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ page
	import="com.krm.ps.util.DateUtil,java.util.Date"%>
<head>
        <title><fmt:message key="webapp.welcome"/></title>
        
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/theme/default/skin_01/style/default.css'/>" />
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css" title="win2k-cold-1'/>">
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
		<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>"
	type="text/javascript"></script>
</head>
 <style type="text/css">

</style>
<script type="text/javascript">
function checkIE(){
	var browser=navigator.appName;
	 var b_version=navigator.appVersion
	 var version=b_version.split(";");
	 var trim_Version=version[1].replace(/[ ]/g,"");
	 if(browser=="Microsoft Internet Explorer" && trim_Version>"MSIE8.0"){   
		 alert('<fmt:message key="view.ie8s"/>'+trim_Version+'<fmt:message key="view.ie8w"/>');
		 return ;
	 }
	 document.forms[0].submit();
}

</script>
<html>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0"    bgcolor="#EDEDED"  >
<%
	String date = (String)request.getAttribute("date");
%>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%">
      <table border="0" cellpadding="0" cellspacing="0" width="486" align=center>
        <tr>
          <td width="100%"><img border="0" src="<c:url value="${imgPath}/a04.gif"/>" width="486" height="88"></td>
        </tr>
        <tr>
          <td width="100%" background="<c:url value="${imgPath}/a05.gif"/>" height="150" valign="top">
              <table cellpadding="0" cellspacing="0" width="100%">
              <html:form action="loginAction.do?method=login" method="post" >
			<!-- 
			<c:set var="pageButtons">
			</c:set>
			-->
                <tr>
                  <td width="100%" height="90">
                    <table cellpadding="0" cellspacing="0" width="100%">
                    
                      <tr>
                        <td width="125" height="28" align="right">
                          <font color="#FFFFFF" ><fmt:message key="logpage.loginname"/></font>
                       </td>
                        <td>
                        	<%-- <html:text property="logonname" style="width:165" value="admin"/> --%>
                        	<html:text property="logonname" style="width:165" value=""/>
                        </td>
                      </tr>
                      <tr>
                        <td width="125" height="28" align="right">
                            <font color="#FFFFFF"><fmt:message key="logpage.password"/></font>
                        </td>
                       <%--  <td><html:password property="password" style="width:165" value="123123"/> --%>
                        <td><html:password property="password" style="width:165"  value="" />
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
                  <td colspan="2" align="center"> </br></br></br><html:button property="method.st" onclick="downlode1();" >
                  		<fmt:message key="atree.downlode1"/>
                  	</html:button>
                  	<html:button property="method.downaxtree" onclick="downlode();" >
                  		<fmt:message key="atree.downlode"/>
                  	</html:button>
                  	
                  	<html:button property="method.sayuser" onclick="sayuser()">
                  		<fmt:message key="button.sysm"/>
                  	</html:button>
                  	</td>
                </tr>
                 <tr align="center">
                  <td  height="30" width="100%"  >
			            <html:button  property="method.save" style="width:70;" onclick="checkIE();">
			            	  <fmt:message key="button.confirm"/>
			            </html:button> &nbsp; &nbsp;&nbsp; &nbsp;
                  </td>
                
                </tr>
                <!-- 
			    <c:out value="${pageButtons}" escapeXml="false" />
			     -->
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

<script type="text/javascript">
	function downlode(){
  		parent.location='<%=request.getContextPath()%>\\krmplugin.exe';
	}
	function sayuser(){
		parent.location='<%=request.getContextPath()%>\\readm.txt';
	}
	function downlode1(){
  		parent.location='<%=request.getContextPath()%>\\showtime.exe';
	}
</script>
