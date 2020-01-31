<!-- /plat/yjh/logpage. -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ page
	import="com.krm.ps.util.DateUtil,java.util.Date"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><fmt:message key="company.name.krm"/></title>
<%-- <script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.4.1.min.js'/>" type="text/javascript"></script> --%>

<script src="<c:url value='/ps/framework/common/jqueryTab/jquery.min.js'/>" type="text/javascript"></script>

<script language="javascript" type="text/javascript" src="<c:url value='${util}/My97DatePicker/WdatePicker.js'/>"></script>
<link href="<c:url value='${plat}/login_files/styles.css'/>" type="text/css" media="screen" rel="stylesheet"/>
<style type="text/css">
img, div { behavior: url(iepngfix.htc) }
</style> 
</head>
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
<body style="background:#9FB6CD" >
<%
	String date = (String)request.getAttribute("date");

%>

	<div style="background:#1ea4d2;height:30px;text-align:left;line-height:30px;padding-left:10px;color:#ffffff" >
   <fmt:message key="east"/>
	</div>
<div  id="login" >
		<div id="wrappertop">
		
		</div>
			<div id="wrapper">
					<div id="content">
						<div id="header" style="height:80px">
							<a href=""><img width=350px src="<c:url value='/ps/framework/images/east_logo_w.png'/>" alt="FreelanceSuite"></a>	</div>
						
						<div id="darkbannerwrap">
						</div>
						<html:form method="post" action="loginAction.do?method=login">
						<fieldset class="form">
                        	                                                                                       <p>
								<label for="user_name"><fmt:message key="usermanage.sysadmin.label.username"/></label>
								<input name="logonname" id="user_name" type="text" value="<c:out value="${usenamee}"/>" style="width: 200;height: 25">
							</p>
							<div id="sho">
								<p>
									<label for="user_password"><fmt:message key="usermanage.sysadmin.label.password"/></label>
									<input name="password" id="user_password" type="password" style="width: 200;height: 25" value="">
								</p>
								<p>
									<label for="user_password"><fmt:message key="logpage.date"/></label>
									<input id="logindate" name="logindate" type="text" 
										value="" 
										style="width:200;height: 25" readonly="true" onClick="WdatePicker()">
								</p>
							
							<button type="submit" class="positive" name="Submit">
							<img src="<c:url value='${plat}/login_files/key.png'/>" alt="Announcement"/>
							<fmt:message key="denglu"/></button>
							</div>
                        </fieldset>
						
						
					</html:form></div>
				</div>
				</div>
				<br>
				<br>
				<br>
				<br>
				<div id="wrapper">
					<div id="content">
						<ul id="s2b_col"> 
						    <li><img border="0" src="<c:url value="/template/default/model/imgs/east.png"/>"></li>
						    <li><img border="0" src="<c:url value="/template/default/model/imgs/east.png"/>"></li>
						    <li><img border="0" src="<c:url value="/template/default/model/imgs/east.png"/>"></li>
						    <li><img border="0" src="<c:url value="/template/default/model/imgs/east.png"/>"></li>
						</ul> 
					</div>
				</div>
</body>
</html>
<script LANGUAGE='JavaScript'>


$(function() {
	$("#s2b_col li").each(function(){
		$(this).bind("mouseover",function() {
			$(this).animate({
           	 	width : "+=" + 50,
               // height: "+=" + 50,
                left : "+=" + 50/2,
                right : "+=" + 50/2,
                top : "=" + 50/2
           });
		});
		
		$(this).bind("mouseout",function() {
			$(this).animate({
           	 	width : "-=" + 50,
               // height: "-=" + 50,
               	left : "-=" + 50/2,
                right : "-=" + 50/2,
                top : "=" + 50/2
           });
		});
		
	});
});


if(<%=request.getAttribute("a")%>!=null){
   alert("<fmt:message key="user.userpassword"/>");
   $("#sho").slideToggle();
   $("#user_name").attr("disabled",true);
}
if(<%=request.getAttribute("loginswitch")%>=='1'){
	alert("<fmt:message key="logpage.witch"/>");
}
//document.forms[0].logonname.focus();
</Script>


