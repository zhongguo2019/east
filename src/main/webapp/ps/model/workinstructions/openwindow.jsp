
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>

<%
   User user = (User)request.getSession().getAttribute("user");
   String orgId = user.getOrganId();   
%>
<c:set var="flagReport" value="1"/>
<c:set var="flagAdo" value="2"/>


<html>
<head>
<title><fmt:message key="view.ywzbgzpi"/></title>

<script type="text/javascript">
	function addid(){
 		
		 if(document.getElementById("funckey").value==""){
			
			alert('<fmt:message key="peizhimabunengweikong"/>');
			return ;
		}
		var r =/^\d+$/;
		if(!r.test(document.getElementById("funtype").value)){
			
			alert('<fmt:message key="leixingbixuweishuzi"/>');
			return ;
		} 
		
		
		document.getElementById("funconfigForm").submit();
	}
</script>
</head>

<body >

<form action="funconfigAction.do?method=addfunconfig" class="easyui-form" method="post" name="funconfigForm" id="funconfigForm">
	 		 <input type="hidden" id="flag" name="flag" value="<c:out value="${flag}"/>">
	    	<table cellpadding="5" align="center" class="TableBGColor">
	    		<tr>
	    			<td>key:</td>
	    			
	    			<td><input type="text" name="funkey" id="funckey" value="<c:out value="${funkey}"/>"></td>	
	    			
	    		</tr>
	    		<tr>
	    			<td>value:</td>
	    			<td><input  type="text" name="funvalue" value="<c:out value='${funvalue}'/>"></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key="jieshi"/>:</td>
	    			<td><input  type="text" name="fundes" value="<c:out value='${fundes}'/>"></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key="leixing"/>:</td>
	    			<td><input type="text"  id="funtype" name="funtype" value="<c:out value='${funtype}'/>"></input></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key="kuozhandiduan"/>:</td>
	    			<td><input  type="text" name="funext1" value="<c:out value='${funext1}'/>"></input></td>
	    		</tr>
	    	</table>
	    	<div align="center">
	 		
	 			<a href="#" class="easyui-linkbutton buttonclass2"  style="text-decoration: none;" name="ss" onclick="addid();" ><fmt:message key='button.confirm'/></a>&nbsp;
	 		
	 		</div>
	 	</form>
</body>


</html>
