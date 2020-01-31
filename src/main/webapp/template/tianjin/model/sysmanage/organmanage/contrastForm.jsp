<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<c:set var="colName">
	<fmt:message key="organTree.colName"/>
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}"/><c:url value="/treeAction.do?method=getOrganTreeXML${params}"/>
</c:set>
<c:set var="orgButton">
	<fmt:message key="place.select"/>
</c:set>

<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
<script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
<script type="text/javascript" src="<c:url value='/common/ActiveXTree/ActiveXTree.js'/>"></script>
</head>
<%
      String organs[][]    = (String[][]) request.getAttribute("organs");
	  String systems[][]   = (String[][]) request.getAttribute("systems");
	  String contrasts[][] = (String[][]) request.getAttribute("contrasts");

%>

<body leftmargin="0" bgcolor=#eeeeee >

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td colspan="2" width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
            <p style="margin-top: 3"><font class=b><b><fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.organ.menuCode"/></b></font></p>                         
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="3%" bgcolor=#eeeeee></td>
    <td width="97%" bgcolor=#eeeeee valign="top">
    <p>
    </p>
    
<br>
<input name="method.save" type="button" value="<fmt:message key="button.save" />" style="width:80;" onClick="gg()">
<input type="button" value="<fmt:message key="standard.dataFill.exportData" />" id="exportData" onclick="exportContrast()"/>&nbsp;&nbsp; 
<form action="contrastAction.do?method=importContr"  method="post" name="contrastForm1" enctype="multipart/form-data">
		<input type="hidden" name="areaId" id="areaId" value=""/>
		<input type="button" value="<fmt:message key="standard.dataFill.importData" />" id="importData" onclick="importok()"/>
		<input type="file"  name="importXlsFile" id="importXlsFile" /> 
</form>

<form method="POST" name="cform" action="" >
<table  align=left class="TableBGColor" border="0" cellSpacing=1 cellPadding=5>

<tr>
<td colspan="<%=systems.length+1%>" class="TdBGColor2">
	<input type="hidden" name="organId" value="<c:out value='${areaCode}' />"/>
								<input type="hidden" name="organName"/>
      						<slsint:ActiveXTree left="220" top="325" width="210" height="${activeXTreeHeight}"
					      		xml="${orgTreeURL}" 
					      		bgcolor="0xFFD3C0" 
					      		rootid="${rootId}"
					      		columntitle="${colName}" 
					      		columnwidth="210,0,0,0" 
					      		formname="cform" 
					      		idstr="organId" 
					      		namestr="organName" 
					      		checkstyle = "0" 
					      		filllayer="2" 
					      		txtwidth="210"
					      		buttonname="${orgButton}">
    						</slsint:ActiveXTree>
    					</td>
</tr>
<%if(organs.length!=0){ %>

<br>
	<tr>
		<td width=80 class="TdBGColor1" align ="center">&nbsp;</td>
		<%for (int j = 0; j < systems.length; j++){%>
		<td width=80 class="TdBGColor1" align ="center"><%=systems[j][1]%></td>
		<%}%>
	</tr>
	<%for (int i = 0; i < organs.length; i++) {%>
	<tr>
        <td width=80 class="TdBGColor1"><%=organs[i][1]%></td>
		<%for (int k = 0; k< systems.length; k++) {
		  String hh = organs[i][0] + "@" + systems[k][0];
%>
	<input type=hidden name=bb value=<%=hh%>>
	<%String xx = "";
	if(contrasts!=null){ 
	for (int m = 0; m < contrasts.length; m++) {
			if (hh.equals(contrasts[m][0])) {
				xx = contrasts[m][1];
	            break;}}}
     %>
	<td class="TdBGColor1"><input style="width:80" name=aa type=text value=<%=xx%>></td>
     <%}%>
    </tr>
	<%}%>
	 
	  <tr>
<td colspan="<%=systems.length+1%>" class="TdBGColor2">
<div id="organs" style="display:none"></div>

<input name="btn" type="button" value="<fmt:message key="button.save" />" style="width:80;" onClick="gg()">&nbsp;&nbsp;

</td></tr>

</TABLE>
</form>
<html:form action="contrastAction.do?method=save" method="post">
	<html:hidden property="organContrasts" />
	<html:hidden property="areaId" />
</html:form>
</td>
</tr>

</table>


<script> 
function gg()
{ 
  var x=cform.aa.length;
  var str="";
  for(i=0;i<x;i++)
	{  
      if(cform.aa[i].value!="")
      {str=str+cform.bb[i].value+"@"+cform.aa[i].value+"#"};
}
  document.contrastForm.organContrasts.value=str;
  areaId = document.cform.areaId.value;
  document.contrastForm.areaId.value = areaId;
  document.contrastForm.submit();
}
</script>
<%} %>
</BODY>
</HTML>

<script type="text/javascript">
function exportContrast(){
	var areaname = document.cform.areaName.value;
	var areaId = document.cform.areaId.value;
	alert(areaId);
	window.location.href = "contrastAction.do?method=exportContr&areaId="+areaId+"&areaname="+areaname;
}
 
function importok(){
	
	if(document.contrastForm1.importXlsFile.value == null || document.contrastForm1.importXlsFile.value == ""){
		alert("<fmt:message key="dataExtractive.noFile"/>");
		return false;
	}
	
	var filestring = document.contrastForm1.importXlsFile.value;
	var houzui = filestring.substring(filestring.length-3,filestring.length);
	if(houzui != "xls"){
		alert("<fmt:message key="historydata.file.format.errorxls"/>"); 
		return false;
	}
	var areaname = document.cform.areaName.value;
	if(confirm(areaname +":"+ "<fmt:message key="contrast.alert.choose"/>")){
	}else{
		return false;
	}
	
    document.contrastForm1.areaId.value = document.cform.areaId.value;
 	document.contrastForm1.action = "<c:out value="${hostPrefix}" />/<c:out value="${systemName}"/>/contrastAction.do?method=importContr";
	document.contrastForm1.submit();
}


	  
	
function changeTree1(){
areaId = document.cform.areaId.value;
document.cform.action = '<c:out value="${hostPrefix}" /><c:url value="/contrastAction.do" />?method=edit&areaCode='+areaId;
document.cform.submit();
}

<c:if test="${not empty success}">
alert('<fmt:message key="statExport.writeFlag"/>');
</c:if>

</script>