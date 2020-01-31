<%@ page language="java"
 pageEncoding="UTF-8" contentType="text/html;charset=gbk" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean-el" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html-el" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic-el" %>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.krmsoft.com/tags-slsint" prefix="slsint" %>

<%-- Set all pages that include this page (particularly tiles) to use XHTML --%>
<html:xhtml />
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<c:set var="colName">
	<fmt:message key="organTree.colName" />
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}" />
	<c:url value='/organTreeAction.do?method=tree' />
</c:set>
<c:set var="orgButton">
	<fmt:message key="organTree.area" />
</c:set>

<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/ps/style/default.css'/>" />
<link rel="stylesheet" type="text/css" href="images/css.css">
<link rel="STYLESHEET" type="text/css"
	href="<%=request.getContextPath() %>/organTreeManage/dhtmlxtree/dhtmlxtree.css">

<script
	src="<%=request.getContextPath() %>/organTreeManage/dhtmlxtree/dhtmlxcommon.js"></script>
<script
	src="<%=request.getContextPath() %>/organTreeManage/dhtmlxtree/dhtmlxtree.js"></script>
<script language="javascript">
	var xml_url = gl_hostPrefix+"/"+gl_systemName+"/organTreeAction.do?method=getDhtmlxTree&treeId="+<%=session.getAttribute("min_tree_id")%>+"&viewtree=viewtree";
</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" style="overflow-x:hidden;overflow-y:hidden">
		<form name="organTreeAction" action="" method="post">
		<div id="treeboxbox_tree" style="border :0px solid Silver; overflow:auto;"></div>
		<script>    
       		tree=new dhtmlXTreeObject("treeboxbox_tree","100%","100%",0);
       		tree.setImagePath("<%=request.getContextPath() %>/organTreeManage/dhtmlxtree/imgs/csh_bluebooks/");
			//tree.enableSmartXMLParsing(true);
			tree.setXMLAutoLoading(xml_url);
			tree.loadXML(xml_url);
			//tree.enableSmartXMLParsing(true);
			tree.setOnClickHandler(onNodeSelect);//set function object to call on node select
	        function onNodeSelect(nodeId){
	        	document.all.organ_id.value=nodeId;
	        }
			
       		</script>
       	<input type="hidden" id="organ_id" name="organ_id" value=""/> 
		</form>
</body>
</html>