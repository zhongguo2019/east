<%@page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page import="com.krm.slsint.workfile.vo.OleFileData"%>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
String date = sdf.format(new Date());
String accessorySize = FuncConfig.getProperty("accessory.size", "10");
%>
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
        <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
	    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
<script type="text/javascript" src="<c:url value='/wordEdit/fckeditor.js' />"></script>
<script type="text/javascript">
<!--
	function checkInput(){
		var strTagType = "";
		for(i = 0; i < document.all.length; i++){
	    if(document.all(i).type == "file"){
			if(strTagType=="")
				strTagType = document.all(i).name;
			else
				strTagType += (","+document.all(i).name);
		}
	  	if(window.workFileForm.title.value.length > 0  ){
	  		if(window.workFileForm.title.value.length < 40){
	  			return true;
	  		}else{
	  			alert("<fmt:message key="sendMail.titleSize" />");
	  			return false;
	  		}
	  	}else{
	  		alert("<fmt:message key="workFile.fileSource.alert" />");
	  		return false;
	  	}	  
	}
}
//-->
</script>
<script type="text/javascript">
<!--
var num = 0;
function addFile(){
	var str = '<input type="file" name="file' + num + '" value="" size="65"><input type="button" name="del' + num + '" value="<fmt:message key='workFile.delete' />" onclick="delFile('+num+');">';
	var div = document.createElement('div');
	div.id = 'div' + num;
	div.innerHTML = str;
	a.appendChild(div);
	num++;
}
function delFile(num){
	var el = document.getElementById('div' + num);
	a.removeChild(el);
}
//-->
</script>
<script type="text/javascript"
	src="<c:url value="/editor/fckeditor.js "/> ">
</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table border="0" cellpadding="0" cellspacing="0" width="100%"
	height="100%">
	<tr>
		<td width="100%" bgcolor="#EEEEEE" valign="top"><br>
		<br>
		<center><html:form action="workFile.do?method=save"
			enctype="multipart/form-data">
			<input type="hidden" name="pkId"
				value="<c:out value="${workFileForm.pkId}" />" />
			<table width="75%" border="0" align="center" cellSpacing="1"
				cellPadding="0" class="TableBGColor">
				<tr>
					<td class="TdBgColor1" width="15%" align="right"><fmt:message
						key="workFile.kind" /></td>
					<td class="TdBgColor1"><select name="kindId">
						<c:forEach items="${jobList}" var="jobType">
							<c:if test="${workFileForm.kindId == jobType.pkid}">
								<option value="<c:out value="${jobType.pkid}"/>" selected><c:out
									value="${jobType.dicname}" /></option>
							</c:if>
							<c:if test="${workFileForm.kindId != jobType.pkid}">
								<option value="<c:out value="${jobType.pkid}"/>" d><c:out
									value="${jobType.dicname}" /></option>
							</c:if>
						</c:forEach>
					</select>&nbsp;&nbsp;&nbsp;&nbsp; <fmt:message
						key="workFile.fileSource" /> <select name="fileSourceId">
						<c:forEach items="${derList}" var="derivationType">
							<c:if test="${workFileForm.fileSourceId == derivationType.pkid}">
								<option value="<c:out value="${derivationType.pkid}"/>" selected><c:out
									value="${derivationType.dicname}" /></option>
							</c:if>
							<c:if test="${workFileForm.fileSourceId != derivationType.pkid}">
								<option value="<c:out value="${derivationType.pkid}"/>"><c:out
									value="${derivationType.dicname}" /></option>
							</c:if>
						</c:forEach>
					</select>&nbsp;&nbsp;&nbsp;&nbsp; <fmt:message
						key="workFile.issData" /> 
						<!-- 20120524By shengping systemdate -->
						<!-- 
						<input type="text" name="issDate"
						style="width:180;" readonly="true"
						value="<c:out value="${workFileForm.issDate}"/>" /> <script
						type="text/javascript">
						 -->
						<input type="text" name="issDate" style="width:180;" readonly="true"
						value="<c:out value="${workFileForm.issDate}"/>" />
						 <script type="text/javascript">
					Calendar.setup({
						inputField     :    "issDate",  
						ifFormat       :    "%Y%m%d",   
						showsTime      :    false
					});
				</script></td>
				</tr>
				<tr>
					<td class="TdBgColor1" align="right"><fmt:message
						key="workFile.title2" /></td>
					<td class="TdBgColor2"><input type="text" name="title" size="86"
						value="<c:out value="${workFileForm.title}" />" /></td>
				</tr>
				<tr>
					<td class="TdBgColor1" align="right" valign="top"><fmt:message
						key="workFile.content" /></td>
					<td class="TdBgColor2">
						<div>
							 <input type="hidden" id="content" name="content" value="<c:out value="${workFileForm.content}" />">
							 <input type="hidden" id="content___Config" value="">
							 <iframe id="content___Frame" src="<c:url value='/wordEdit/editor/fckeditor.html?InstanceName=content&Toolbar=Default' />" width="100%" height="300" frameborder="no" scrolling="no"></iframe>
						</div>
					</td>
				</tr>
				<tr>
					<td class="TdBgColor1" align="right"></td>
					<td class="TdBgColor2"><%if (request.getAttribute("ole") != null) {%>
					<table border="0" width="100%">
						<%ArrayList al = (ArrayList) request.getAttribute("ole");
				Iterator it = al.iterator();
				while (it.hasNext()) {
					OleFileData oleFileData = (OleFileData) it.next();%>
						<tr>
							<input type="hidden" name="filePkId"
								value="<%=oleFileData.getPkId() %>" />
							<td class="TdBgColor1" align="left"><%=oleFileData.getSFileName()%></td>
							<td class="TdBgColor1" align="right"><a
								href="workFile.do?method=delOle&olePkId=<%=oleFileData.getPkId() %>&pkId=<c:out value="${workFileForm.pkId}" />"><fmt:message
								key="workFile.delete" /></a></td>
						</tr>
						<%}%>
					</table>
					<%}%>
		           </td>
				</tr>
				<tr>
					<td class="TdBgColor1"></td>
					<td class="TdBgColor1" id="a"></td>
				</tr>
				<tr>
					<td class="TdBgColor1" align="right"><input type="hidden"
						name="fileNum" value="1" id="fileNum" /> <input type="hidden"
						name="fileName" value="" id="fileName" /></td>
					<td class="TdBgColor1"><a href="#" onClick="addFile();"> <fmt:message
						key="sendMail.addFile" /> </a><fmt:message key="sendMail.fileSize" /><%=accessorySize %>M</td>
				</tr>
				<tr>
					<td colspan="2" align="center" class="FormBottom">
						<c:if test="${workFileForm.pkId == null}">
							<c:set var="btn"><fmt:message key="button.add"/></c:set>
						</c:if>
						<c:if test="${workFileForm.pkId != null}">
							<c:set var="btn"><fmt:message key="button.save"/></c:set>
						</c:if>
						<input type="submit" name="submit" class="easyui-linkbutton buttonclass2" 
							value="<c:out value="${btn}"/>" style="width:100"
							onclick="return checkInput();" />
					</td>
				</tr>
			</table>
		</html:form></center>
		</td>
	</tr>
</table>
</body>
</html>
<c:if test="${fileSize == '9'}" >
<script type="text/javascript">
	alert("<fmt:message key="file.size" />");
</script>
</c:if>
<c:if test="${content == '9'}" >
<script type="text/javascript">
	alert("<fmt:message key="sendMail.contentSize" />");
</script>
</c:if>
