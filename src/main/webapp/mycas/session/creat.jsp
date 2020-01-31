<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.krmsoft.mycas.bean.Loginbean"%>
<%
String userId = request.getParameter("userId");
String userName = "系统管理元";
String passWord = request.getParameter("password");
String logindate = request.getParameter("logindate");
System.out.println(userId+";"+userName+";"+passWord);
Loginbean lb = new Loginbean(request, response,userId,userName, passWord, logindate, null);
lb.setStatus("1");
response.getWriter().write(lb.jsonStr());
 %>