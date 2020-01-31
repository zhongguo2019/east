<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.User"%>

<c:set var="organColNames">
	<fmt:message key="chartpage.tree.org.name"/>
</c:set>
<c:set var="orgButton">
	<fmt:message key="chartpage.button.org.name"/>
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}" /><c:url value="/chart.do?method=getOrganTree"/>&treeId=<c:out value="treeId"/>
</c:set>

<%
   User user = (User)request.getSession().getAttribute("user");
   String orgId = user.getOrganId();
%>

<html >
	<head>
		<%-- <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/chart/scripts/wizard.css'/>" /> --%>
		<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>
	</head>
	<script type="text/javascript">
		function changeTree1(){}
		
		function setSelect(){
			parent.reportConfigForm.organNames.value=window.configForm.organname.value;
			parent.reportConfigForm.organs.value=window.configForm.organid.value;
			parent.CloseModalWin();
		}

	</script>
	
	<body leftmargin="0" topmargin="0" bgcolor="#E8E8E8">
		<form name="configForm" id="configForm">
		<table width="100%"  height="80">
		  <tr>
		    <td>
				<div id="organDiv">
					<input type="hidden" name="organid" value="">
					<input type="hidden" name="organname" value="">
					<c:if test="${mode == '1' }">
					<slsint:ActiveXTree left="220" top="325" width="300"
						height="${activeXTreeHeight }" xml="${orgTreeURL}"
						bgcolor="0xFFD3C0"
						rootid="<%=orgId %>"
						columntitle="${organColNames}"
						columnwidth="300,0,0,0"
						formname="configForm"
						idstr="organid" namestr="organname"
						checkstyle="0" 
						filllayer="2"
						txtwidth="300" buttonname="${orgButton}">
					</slsint:ActiveXTree>
					</c:if>
					<c:if test="${mode == '2' }">
					<slsint:ActiveXTree left="220" top="325" width="300"
						height="${activeXTreeHeight }" xml="${orgTreeURL}"
						bgcolor="0xFFD3C0"
						rootid="<%=orgId %>"
						columntitle="${organColNames}"
						columnwidth="300,0,0,0"
						formname="configForm"
						idstr="organid" namestr="organname"
						checkstyle="5" 
						filllayer="2"
						txtwidth="300" buttonname="${orgButton}">
					</slsint:ActiveXTree>
					</c:if>
		    	</div>
		    </td>
		  </tr>
		</table>

		<table width="100%" >
		  <tr>
		    <td align="center" valign="bottom" height="220"><input name="" type="button" style="width:70;height=30" onClick="setSelect()" value="<fmt:message key="button.submit"/>">&nbsp;&nbsp;&nbsp;<input name="" type="button" style="width:70;height=30" onClick="parent.CloseModalWin();" value="<fmt:message key="button.close"/>"></td>
		  </tr>
		</table>
		</form>
	</body>
</html>
