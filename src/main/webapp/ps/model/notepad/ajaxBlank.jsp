<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
response.setContentType("text/plain");
response.setCharacterEncoding("UTF-8");
String msg =(String) request.getAttribute("message");
response.getWriter().write((msg==null||msg.length()<=0)?"无消息":msg);
%>