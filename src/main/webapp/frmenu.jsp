<!DOCTYPE html>
<html>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%String loginDate = (String)request.getSession().getAttribute("logindate");
 
  String jessionid =(String)request.getSession(false).getAttribute("jsesstionId");
  String userId =(String)request.getSession(false).getAttribute("userId");
  String userName =(String)request.getSession(false).getAttribute("userName");
  String userPass =(String)request.getSession(false).getAttribute("userPss");
  String logindate =(String)request.getSession(false).getAttribute("logindate");
%>
<head>
<title></title>

	<link rel="stylesheet" href="<c:url value='/ps/uitl/jmenu/css/styles.css'/>" type="text/css" />
	<link rel="stylesheet" href="<c:url value='/ps/uitl/jmune/css/jquery-tool.css'/>" type="text/css" />
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${eaUIPath}/themes/metro-orange/easyui.css'/>" />
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${eaUIPath}/themes/icon.css'/>" /> 

	<script type="text/javascript" src="<c:url value='/ps/uitl/jmenu/js/jquery.tools.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/ps/uitl/jmenu/js/main.js'/>"></script>
	
	<script type="text/javascript" src="<c:url value='${eaUIPath}/jquery.min.js'/>"></script> 
	<script type="text/javascript" src="<c:url value='${eaUIPath}/jquery.easyui.min.js'/>"></script>  

<script type="text/javascript">
/* window.parent.document.getElementById("ppppp").value= "<fmt:message key="navigation.dataFill.dataFilledit" />"; */

function addPanel(tname,url){
	var url2=url+"&jsessionIdstr=<%=jessionid%>&logonname=<%=userId%>&password=<%=userPass%>";
//	alert(url2);
	window.parent.window.addPaneltab(tname,url2);
}
</script>
<style>html body{overflow:auto;scrolling:auto;font-family:"Microsoft YaHei",微软雅黑,"Microsoft JhengHei",华文细黑,STHeiti,MingLiu}
* {
font-family: "Microsoft YaHei" ! important;
}

</style>

</head>
<body>
	
	<div id="menucontent" class="menucontent">
	
		<ul id="expmenu-freebie">
			<li>
				<ul class="expmenu" style="width:180px">
    			<c:forEach var="m" items="${menus}">
					<li>
						<div class="jmenuheader">
						<span class="arrow up"></span>
						<span class="label" style="background-image: url(<c:url value='/ps/uitl/jmenu/images/book-orange.png'/>);"><c:out value="${m.name}" /></span> 
							
						</div>
						<ul class="jmenumenu">
						<c:forEach var="sm" items="${m.subMenus}">
							<li class="submenu" onclick="addPanel('<c:out value="${sm.name}" />','<c:out value="${sm.resource}" />')"><a target="mainFrame"><c:out value="${sm.name}" /></a></li>
						</c:forEach>
						</ul>
					</li>
				</c:forEach>			
				</ul>
			</li>
		</ul>
	</div>
</body>
</html>
