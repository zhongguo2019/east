<%@ page language="java" isErrorPage="true" %>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!-- I do not integrate this page as a tile, but rather as a standalone-page -->
<html >
<head>
	<title><fmt:message key="webapp.prefix"/></title>
	<script language="JavaScript">
	function hideshow(){
		var form = document.getElementById("texa");
		var flag = document.getElementById("flag").value;
		if(flag=="0"){
			form.style.visibility="visible";
			document.getElementById("flag").value="1";
		}else{
			form.style.visibility="hidden";
			document.getElementById("flag").value="0";
		}
		
	}
	
	</script>
	
</head>

<body bgcolor="#eeeeee">
<div id="screen">
    <div id="content">
    <h1><fmt:message key="errorPage.heading"/></h1>
    <h3><c:out value="${errorMessages}"/></h3>
    
    <input type="button" value="<fmt:message key="view.more"/>" onclick="hideshow();"/>
    <input type="hidden" value="0" id="flag"/>
    </br>
    <textarea id="texa" style="visibility: hidden; color:blue" cols="120" rows="60" >
	<c:out value="${errors}"/>
    </textarea>  
    
    
    
    <%-- Error Messages --%>
    <logic:messagesPresent>
        <logic:present name="error">    
        <div class="error">	
            <html:messages id="error">
                <c:out value="${error}" escapeXml="false"/><br/>
            </html:messages>
        </div>
        </logic:present>
    </logic:messagesPresent>
 <% if (exception != null) { %>
    <pre><% exception.printStackTrace(new java.io.PrintWriter(out)); %></pre>
 <% } else if ((Exception)request.getAttribute("javax.servlet.error.exception") != null) { %>
    <pre><% ((Exception)request.getAttribute("javax.servlet.error.exception"))
                           .printStackTrace(new java.io.PrintWriter(out)); %></pre>
 <% } else if (pageContext.findAttribute("org.apache.struts.action.EXCEPTION") != null) { %>
    <bean:define id="exception2" name="org.apache.struts.action.EXCEPTION"
     type="java.lang.Exception"/>
    <c:if test="${exception2 != null}">
        <pre><% exception2.printStackTrace(new java.io.PrintWriter(out));%></pre>
    </c:if>
    <%-- only show this if no error messages present --%>
    <c:if test="${exception2 == null}">
        <fmt:message key="errors.none"/>
    </c:if>
 <% } %>
    </div>
</body>
</html>
