<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%
String status = request.getParameter("urs");

if(status!=null&&!status.equals("null"))
{
	if("e01".equals(status)){
%>

<script>

//alert(<%=status%>);

  alert("您没有该系统的权限，请重新登录！");

</script>
<%} }
//response.getWriter().write("username:"+userName);
%>