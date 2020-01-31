<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<c:set var="areaTreeURL">
	<c:out value="${hostPrefix}" /><c:url value="/treeAction.do?method=getAreaTreeXML"/>&areaCode=<c:out value="${rootArea.code}" />&date=<c:out value="${curDate}" />
</c:set>
<c:set var="areaButton">
	<fmt:message key="place.select"/>
</c:set>
<c:set var="colNames">
	<fmt:message key="sysinit.tree.area.name"/>
</c:set>


<html>

<head>
<title><fmt:message key="sysinit.title"/></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<script type="text/javascript" src="<c:url value='/common/ActiveXTree/ActiveXTree.js'/>"></script>
<style>
	body{font-size: 13px}
</style>
<body bgcolor="#EEEEEE">

<form name="sysInitForm" action="sysInitAction.do?method=initMunal" method="post"
 enctype="multipart/form-data" onsubmit="return doSubmit1()">

<table border="0" width="800" align="center">
	<tr>
		<td colspan="3" height="37"><b><fmt:message key="sysinit.t1"/></b></td>
	</tr>
	<tr>
		<td align="right" class="TdBGColor1"><fmt:message key="sysinit.form.loginname"/></td>
		<td width="360" class="TdBGColor2"><input type="text" name="logonName" size="20" style="width:280;"></td>
		<td width="290" class="TdBGColor2"><font color="#808080"><fmt:message key="sysinit.tip.loginname"/></font></td>
	</tr>
	<tr>
		<td align="right" class="TdBGColor1"><fmt:message key="sysinit.form.username"/></td>
		<td class="TdBGColor2"><input type="text" name="userName" size="20" style="width:280;"></td>
		<td class="TdBGColor2">&nbsp;</td>
	</tr>
	<tr>
		<td align="right" class="TdBGColor1"><fmt:message key="sysinit.form.password"/></td>
		<td class="TdBGColor2"><input type="password" name="password" size="20" style="width:280;"></td>
		<td class="TdBGColor2"><font color="#808080"><fmt:message key="sysinit.tip.password"/></font></td>
	</tr>
	<tr>
		<td align="right" class="TdBGColor1"><fmt:message key="sysinit.form.cfpassword"/></td>
		<td class="TdBGColor2"><input type="password" name="cfpassword" size="20" style="width:280;"></td>
		<td class="TdBGColor2">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="3" height="37"><b><fmt:message key="sysinit.t2"/></b></td>
	</tr>
	<tr>
		<td align="right" class="TdBGColor1"><fmt:message key="sysinit.tree.areaname"/></td>
		<td class="TdBGColor2">
		
			
			<input type="hidden" name="areaCode" value=""/>
			<input type="text" name="areaName" value="" size="20" style="width:280;"/>
		</td>
		<td class="TdBGColor2">&nbsp;</td>
	</tr>
	<tr>
		<td align="right" class="TdBGColor1"><fmt:message key="sysinit.form.organcode"/></td>
		<td class="TdBGColor1"><input type="text" name="organCode" size="20" style="width:280;"></td>
		<td class="TdBGColor2">&nbsp;</td>
	</tr>
	<tr>
		<td align="right" class="TdBGColor1"><fmt:message key="sysinit.form.organshortname"/></td>
		<td class="TdBGColor1"><input type="text" name="organShortName" size="20" style="width:280;"></td>
		<td class="TdBGColor2">&nbsp;</td>
	</tr>
	<tr>
		<td align="right" colspan="3" class="TdBGColor1">
		<p align="left"><font color="#808080">
		<fmt:message key="sysinit.tipinfo1"/><br>
		<fmt:message key="sysinit.tipinfo2"/></font></td>
	</tr>
	<tr>
		<td align="right" class="TdBGColor1">&nbsp;</td>
		<td colspan="2" align="left" class="TdBGColor2">
		<input type="submit" value="<fmt:message key="sysinit.button.init"/>"></td>
	</tr>	
</table>

<p>&nbsp;</p>

<table border="0" width="800" align="center">
	<tr>
		<td colspan="3" height="37" class="TdBGColor2">
		<span style="font-size:15"><b><fmt:message key="sysinit.bybackupfile"/></b></span></td>
	</tr>
	<tr>
		<td align="right" colspan="3" class="TdBGColor2">
		<p align="left">
		<font class="TdBGColor2"><fmt:message key="sysinit.form.backupfile"/></font><br>
		<input type="file" name="backupFile" size="50" >
		<input type="button"  value="<fmt:message key="sysinit.button.initbybackup"/>" onclick="doSubmit2()">
		</td>
	</tr>
	<tr>
		<td align="right" colspan="3" class="TdBGColor1">
		<p align="left"><font color="#808080">
		<fmt:message key="sysinit.restore.info"/></font></td>
	</tr>
</table>

</form>


<script>
	var f=document.sysInitForm
	var restore=false
	var formSubmited=false
	
	function doSubmit1(){
		if(restore){
			return false
		}
		if(f.logonName.value==''){
			alert('<fmt:message key="sysinit.validate.logonnameempty"/>')
			return false
		}
		if(f.password.value.length<6){
			alert('<fmt:message key="sysinit.validate.passwordlength"/>')
			return false
		}
		if(f.password.value!=f.cfpassword.value){
			alert('<fmt:message key="sysinit.validate.passwordident"/>')
			return false
		}
		/**
		if(f.areaCode.value==''){
			alert('<fmt:message key="sysinit.validate.area"/>')
			return false
		}
		**/
				/**
		if(f.areaName.value==''){
			alert('<fmt:message key="sysinit.validate.area"/>')
			return false
		}
		**/
		if(f.organCode.value==''){
			alert('<fmt:message key="sysinit.validate.organcode"/>')
			return false
		}
		if(f.organShortName.value===''){
			alert('<fmt:message key="sysinit.validate.organshortname"/>')
			return false
		}

		if(formSubmited){
			return false
		}else{
			formSubmited=true
			return true
		}
	}
	
	function doSubmit2(){
		if(f.backupFile.value==''){
			return
		}
		if(!confirm('<fmt:message key="sysinit.restore.confirm"/>')){
			return
		}
		restore=true
		f.action="sysInitAction.do?method=initBuckupFile"
		//setTimeout(function (){if(!formSubmited){formSubmited=true;f.submit()}},5000)
		if(formSubmited){
			return
		}else{
			formSubmited=true
			f.submit()
		}
	}

	function changeTree1(){
	}
	
</script>

</body>

</html>