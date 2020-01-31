<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<html>
    <head>
        <title><fmt:message key="webapp.prefix"/></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/tank.css'/>" />
        <script type="text/javascript" src="<c:url value='/ps/scripts/jquery.js'/>"></script>  
        <script type="text/javascript" src="<c:url value='/ps/scripts/Dialog.js'/>"  > </script> 
<body>
<div>
 <display:table name="subTarsks" cellspacing="0" cellpadding="0"  
							    requestURI="" id="st" width="100%" 
							    pagesize="30" styleClass="list subTarsks" >
							     <display:column sort="true" 
							    	headerClass="sortable"
							    	titleKey="sutarsk.id">
							    	<c:out value="${st.pkid}"/>
							    </display:column>
			 <display:column sort="true" 
							    	headerClass="sortable"
							    	titleKey="tarsk.tid">
							    	<c:out value="${st.tId}"/>
							    </display:column>				    
			<display:column sort="true" 
							    	headerClass="sortable"
							    	titleKey="sutarsk.status">
							    	<c:if test="${st.status=='0'}">
							    	<fmt:message key="st.status.0" />
							    	</c:if>
							    	<c:if test="${st.status=='1'}">
							    	<fmt:message key="st.status.1" />
							    	</c:if>
							    	<c:if test="${st.status=='2'}">
							    	<fmt:message key="st.status.2" />
							    	</c:if>
							    	<c:if test="${st.status=='3'}">
							    	<fmt:message key="st.status.3" />
							    	<a href="javascript:reCreateTarsk('<c:out value="${st.pkid}"/>','<c:out value="${st.tId}" />','<c:out value="${st.organId}"/>','<c:out value="${st.dataDate}"/>','<c:out value="${st.reportId}"/>');"  >
							    	<fmt:message key="st.reset" /></a>
							    	</c:if>
							    	
							    </display:column>
							    <display:column sort="true" 
							    	headerClass="sortable"
							    	titleKey="sutarsk.message">
							    	<c:out value="${st.message}"/>
							    </display:column>
							    
							    <display:column sort="true" 
							    	headerClass="sortable"
							    	titleKey="sutarsk.createtime">
							    	<c:out value="${st.createTime}"/>
							    </display:column>
							    <display:column sort="true" 
							    	headerClass="sortable"
							    	titleKey="subtarsk.reportid">
							    	<c:out value="${st.reportId}"/>
							    </display:column>
							     <display:column sort="true" 
							    	headerClass="sortable"
							    	titleKey="subtarsk.organid">
							    	<c:out value="${st.organId}"/>
							    </display:column>
							     <display:column sort="true" 
							    	headerClass="sortable"
							    	titleKey="subtarsk.datadate">
							    	<c:out value="${st.dataDate}"/>
							    </display:column>
							    <display:column sort="true" 
							    	headerClass="sortable"
							    	titleKey="sutarsk.filepath">
							    	<a href="#">
							    	<c:out value="${st.filePath}"/>
							    	</a>
							    </display:column>
							    </display:table>

</div>
</body>
</html>