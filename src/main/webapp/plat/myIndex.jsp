<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
  	<frameset cols="20%,80%" >
		<frame src="<%=basePath %>plat/report/reportTree.jsp" name="reporttree">
		<frameset rows="30%,70%">
			<frame src="<%=basePath %>plat/report/reportSearchCondition.jsp" name="condition" >
			<frame src="<%=basePath %>plat/report/reportView.jsp" name="view" id="h">
		</frameset>
	</frameset>
