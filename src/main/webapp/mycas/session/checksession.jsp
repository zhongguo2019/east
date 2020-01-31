<%@ page import="java.util.*"%>
<%
String jessionid =(String)request.getSession(false).getAttribute("jsesstionId");
String userId =(String)request.getSession(false).getAttribute("userId");
String userName =(String)request.getSession(false).getAttribute("userName");
String userPass =(String)request.getSession(false).getAttribute("userPss");
String logindate =(String)request.getSession(false).getAttribute("logindate");



boolean login= false;
if(jessionid!=null&&!jessionid.equals("")&&userName!=null&&!userName.equals(""))
login = true;
if(login){
%>

<script>
<%-- $("#jsesstionId").val("<%=jessionid%>");
$("#userid").val("<%=userId%>");
$("#username").val("<%=userName%>");
$("#userpass").val("<%=userPass%>");
$("#logindate").val("<%=logindate%>");
$("#platlog").slideUp();
$("#platchoose").slideDown();
$("#platchoose").attr({
	"style" : "visibility:visible"
});
$("#usernamespan").text("<%=userName%>");
$("#logindatespan").text("<%=logindate%>");

getOsData();

//alert("<%=jessionid%>"); --%>
</script>
<%}else{
	response.sendRedirect("login.jsp");
}
//response.getWriter().write("username:"+userName);
%>