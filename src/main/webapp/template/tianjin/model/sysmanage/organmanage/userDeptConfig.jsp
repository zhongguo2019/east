<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<html>
<c:set var="selectOrganButton">
	<fmt:message key="docshare.select.organanddept" />
</c:set>
<c:set var="selectUserButton">
	<fmt:message key="docshare.select.user" />
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}" /><c:url value="/treeAction.do?method=getOrganTreeXML${orgparam}" />
</c:set>
<c:set var="organAndDeptTreeURL">
	<c:out value="${hostPrefix}" /><c:url value='/treeAction.do?method=getOrgAndDepartmentXML' />
</c:set>
<head>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
<script type="text/javascript"
		src="<c:url value='/common/ActiveXTree/ActiveXTree.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/scripts/prototype.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/scripts/log4javascript.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/scripts/formcheckkrm.js'/>"></script>
</head>
<body leftmargin="0" bgcolor=#eeeeee>
	<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
		  <tr>
		    <td colspan="2" width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
		      <table border="0" cellpadding="0" cellspacing="0" width="100%">
		        <tr>
		          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
		          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
		          <td>
		            <p style="margin-top: 3"><font class=b><b><fmt:message
		             key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="department.manage"/></b></font></p>                         
		          </td>
				  <td></td> 
		        </tr>
		      </table>
		    </td>
		  </tr>
		  <tr>
		  	<td valign="top">
		  		<br/>
		  		<br/>
				<form name="departmentManageForm" action="departmentManageAction.do?method=saveUserDeptInfo" method="post">
					<input type="hidden" name="deptId" />
					<input type="hidden" name="deptName" />
					<input type="hidden" name="userIdStr" con="!=''" msg="<fmt:message key="js.alert.select.user"/>"/>
					<input type="hidden" name="userNameStr" />
					<input type="hidden" name="organCode"/>
					<input type="hidden" name="organName" />
					<table align="center" class="TableBGColor">
						<tr>
							<td colspan=2 class="TdBgColor1">
								<input type="radio" name="operFlag" value="1" checked/><fmt:message key="button.save"></fmt:message>
								&nbsp;&nbsp;
								<input type="radio" name="operFlag" value="0" /><fmt:message key="button.delete"></fmt:message>		
							</td>
						</tr>
						<tr>
							<td colspan="2" class="TdBgColor1">
								<!-- users select -->
								<slsint:ActiveXTree
										left="220" 
										top="325" 
										width="380" 
										height="${activeXTreeHeight }"
										xml="${orgTreeURL}"
										bgcolor="0xFFD3C0"
										rootid="" 
										columntitle=""
										columnwidth="380,0,0,0" 
										idstr="organCode" 
										formname="departmentManageForm"
										namestr="organName"
										checkstyle="0" 
										filllayer="2" 
										txtwidth="250"
										buttonname="${selectUserButton}">
								</slsint:ActiveXTree>
							</td>
						</tr>
						<tr>
							<td colspan="2" class="TdBgColor1">
								<select name="users" size="10" multiple="multiple" onchange="saveSelectedUser()" style="width:100%"></select>
							</td>
							<td></td>
						</tr>
						<tr>
							<td colspan=2 class="TdBgColor1">
								<fmt:message key="docshare.right.dept.name"/><fmt:message key="docshare.right.select"/><fmt:message key="symbol.colon.fullwidth"></fmt:message>
								<select name="dept" style="width:120" onchange="changeDept()" con="!=''" msg="<fmt:message key="js.alert.select.dept"/>">
									<c:forEach items="${ deptList }" var="dept">
										<option value="<c:out value="${dept.pkid}"/>"><c:out value="${dept.deptName}"/></option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td colspan=2 align="center" class="TdBgColor1">
								<input type="button" value="<fmt:message key="button.confirm" />" onclick="closeSelf()"></input>
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
	</table>
</body>
<script type="text/javascript">

	init();

	function getUsersByAjax()
	{
		var ajaxOptions = {method:"get",onSuccess:displayValues};
		var url = "<c:out value="${hostPrefix}" /><c:url value="/docShareManageAction.do?method=getUsersByAjax"/>"
			 + "&organId=" + document.departmentManageForm.organCode.value;
		var doAjax = new Ajax.Request(url, ajaxOptions);
	}
	
	function displayValues(response)
	{
		// alert(response.responseText);
		var repStr = response.responseText;
		if (repStr == "")
			return;
		var options = repStr.split(";");
		var size = options.length;
		var selectObj = document.departmentManageForm.dept;
		if (repStr.indexOf("O:") > -1)
			selectObj = document.departmentManageForm.organs;
		if (repStr.indexOf("U:") > -1)
			selectObj = document.departmentManageForm.users;
		selectObj.options.length = 0;
		var optionParams;
		selectObj.add(new Option("<fmt:message key='place.select' />...", "0"));
		for (var i = 0; i < size; i++)
		{
			optionParams = options[i].split(",");
			if (optionParams[2] != undefined) // has dept info
				selectObj.add(new Option(appendSpaceToStr(optionParams[1], 20) + optionParams[2], optionParams[0]));
			else
				selectObj.add(new Option(optionParams[1], optionParams[0]));
		}
	}
	
	function appendSpaceToStr(str, length)
	{
		while (str.length < length)
		{
			str = str + " ";
		}
		return str;
	}
	
	function saveSelectedUser()
	{
		var options = document.departmentManageForm.users.options;
		var size = options.length;
		var tmpStr = "";
		var tmpNames = "";
		for (var i = 0; i < size; i++)
		{
			if (options[i].selected && options[i].value != "0")
			{
				tmpStr = tmpStr + "," + options[i].value.split(":")[1];
				tmpNames = tmpNames + "," + options[i].text.replace(/\s+\w*$/, "");
			}
		}
		// alert(tmpStr.replace(/^,/, ""));
		// alert(tmpNames.replace(/^,/, ""));
		document.departmentManageForm.userIdStr.value = tmpStr.replace(/^,/, "");
		document.departmentManageForm.userNameStr.value = tmpNames.replace(/^,/, "");
	}
	var saveButton = false;
	function closeSelf()
	{
		var form = document.departmentManageForm;
		if (!FormCheckKRM.checkSubmit())
		{
			return;
		}
		form.submit();
	}
	
	function init()
	{
		changeDept();
	}
	
	// tree change
	function changeTree1()
	{
		getUsersByAjax();
	}
	
	function changeTree2()
	{
		getUsersByAjax();
	}
	
	function changeDept()
	{
		var form = document.departmentManageForm;
		form.deptId.value = form.dept.value;
		form.deptName.value = form.dept.options[form.dept.selectedIndex].text;
	}
</script>
</html>