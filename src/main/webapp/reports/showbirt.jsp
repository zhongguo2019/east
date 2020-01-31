<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ taglib uri="/birt.tld" prefix="birt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/tank.css'/>" />
<script type="text/javascript" src="<c:url value='/ps/scripts/jquery.js'/>"></script>  
<script type="text/javascript" src="<c:url value='/ps/scripts/Dialog.js'/>"  > </script>
</head>
<body>
	<table>
		<tr style="width: 100%; height: 95%" valign="top">
			<td style="width: 100%; height: 95%">
			<birt:viewer id="birtViewer1"
					reportDesign='<%=request.getAttribute("prtName").toString()%>'
					baseURL="<%=request.getContextPath()%>" pattern="frameset"
					title="" showTitle="false" height="600" width="1500"
					format="html" frameborder="false" isHostPage="false"
					 showParameterPage="false">
				<%-- <birt:param name="vdate" value='<%=request.getAttribute("vdate").toString()%>'/>
				
				<birt:param name="organId" value='<%=request.getAttribute("organId").toString()%>'/> --%>
				</birt:viewer>
				<input type="hidden" id="test"  name="test" value="1234"/>
				
				</td>
		</tr>
	</table>
</body>
<script type="text/javascript">

</script>
</html>