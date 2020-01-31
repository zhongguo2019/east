<!--/ps/model/workinstructions/RightWorkInstructions.jsp-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>

<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/> 


<script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script>
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>

</head>

<body>

  					<div id="divcontext"  align="center">
							<%-- <%@ include file="/ps/model/workinstructions/context.jsp"%>	 --%>
							<p align="center"> <font  color="#000000" size="3"><fmt:message key='workFile.title2'/><c:out value='${title}'/></font></p>
							 <font  color="#000000" size="2"><c:out value='${context}' escapeXml="false" /></font>
						<table >
								<c:forEach items="${list1}" var="list1">
									<tr>
										<td><fmt:message key='fujianming'/>:</td>
										<td><a href="workinstructionsAction.do?method=download&pkid=<c:out value="${list1[0]}"/>"><c:out value="${list1[1]}"/></a></td>
									<tr>
								</c:forEach>	
						</table>
					</div>
		
				<input type="hidden" id="id">
				<input type="hidden" id="name">
				<input type="hidden" id="newname">

</body>


</html>
