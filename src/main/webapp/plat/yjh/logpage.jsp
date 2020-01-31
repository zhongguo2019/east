<!DOCTYPE HTML>
<!-- /plat/yjh/logpage. -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ page
	import="com.krm.ps.util.DateUtil,java.util.Date"%>
<html>	
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="company.name.krm"/></title>
<script language="javascript" type="text/javascript" src="<c:url value='${util}/My97DatePicker/WdatePicker.js'/>"></script>
<link href="<c:url value='${plat}/login_files/styles.css'/>" type="text/css" media="screen" rel="stylesheet"/>
<style type="text/css">
HTML{height:100%}
body{height:100%}
img, div { behavior: url(iepngfix.htc) }

</style>
<script LANGUAGE='JavaScript'><!--

if(<%=request.getAttribute("a")%>!=null){
   alert("<fmt:message key="user.userpassword"/>");
   //window.location.href="http://127.0.0.1:8080/platsso/mycas/login.jsp";
   //return;
}
if(<%=request.getAttribute("loginswitch")%>=='1'){
	alert("<fmt:message key="logpage.witch"/>");
}



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
</head>



<body style="background:#9FB6CD" >
<%
	String date = (String)request.getAttribute("date");

%>

	<div style="background:#75B2E1;height:30px;text-align:left;line-height:30px;padding-left:10px;color:#ffffff" >
   <fmt:message key="east"/>
	</div>
<div  id="login" >
		<div id="wrappertop">
		
		</div>
			<div id="wrapper">
					<div id="content">
						<div id="header" style="height:80px;background-image: linear-gradient(to bottom,#E7F9FD, #75B2E1,#E7F9FD);">
							<a href=""><img width=350px src="<c:url value='/ps/framework/images/east_logo_w.png'/>" alt="北京零壹智慧科技有限公司"></a>	</div>
						
						<div id="darkbannerwrap">
						</div>
						<html:form method="post" action="loginAction.do?method=login">
						<fieldset class="form">
                        	                                                                                       <p>
								<label for="user_name"><fmt:message key="usermanage.sysadmin.label.username"/></label>
								<input name="logonname" id="user_name" type="text"  style="width: 200;height: 25">
							</p>
							<p>
								<label for="user_password"><fmt:message key="usermanage.sysadmin.label.password"/></label>
								<input name="password" id="user_password" type="password" style="width: 200;height: 25">
							</p>
							<p>
								<label for="user_password"><fmt:message key="logpage.date"/></label>
								<input id="logindate" name="logindate" type="text" value="<%=date %>" 
									style="width:200;height: 25" readonly="true" onClick="WdatePicker()">
							</p>
							
							<button type="submit" class="positive" name="Submit">
							<img src="<c:url value='${plat}/login_files/key.png'/>" alt="Announcement"/>
							<fmt:message key="denglu"/></button>
							
                        </fieldset>
						
						
					</html:form></div>
				</div>
				</div>
</body>
<script LANGUAGE='JavaScript'><!--


	document.forms[0].logonname.focus();

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
</html>


