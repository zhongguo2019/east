<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/scripts/trimpath-template-1.0.38.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/scripts/jquery-1.2.6.js'/>"></script>
<script type="text/javascript" src="<c:url value='/chart/scripts/moduldlg.js'/>"></script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
				<p style="margin-top: 3"><font class=b><b><fmt:message
				 key="navigation.reportdefine.config"/></b></font></p>
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
    <br>
    <br>
   
    <form name="reportConfigForm" id="reportConfigForm" action="" method="post">
    <input type="hidden" name="repId" id="repId" value="<c:out value="${reportId}"/>"/>
    <input type="hidden" name="organs" id="organs" value="<c:out value="${organs}"/>"/>
   
    	<table cellSpacing="1" cellPadding="0" width="600" align="center" border="0" class="TableBGColor">
    		<tr>
		        <td class="TdBgColor1" align="right">
		            <fmt:message key="reportDefine.funConfigs"/>
		        </td>
		        <td class="TdBGColor2">			        	
		            <select name="funId" id="funId" size="1" style="width:360;">
			        		<c:forEach var="fi" items="${funIdList}">
        						<option value="<c:out value="${fi.dicid}"/>">
       								<c:out value="${fi.dicname}"/>
								</option>
							</c:forEach>
					</select>
		        </td>
		    </tr>
		    <tr>
		    	<td class="TdBgColor1" align="right">
		            <fmt:message key="datagather.label.report"/>
		        </td>
		        <td class="TdBGColor2">
		        	<input type="text" name="reportName" id="reportName" value="<c:out value="${reportName}"/>" style="width:360;" readonly="readonly">
		        </td>
		    </tr>
		    <tr>
		    <td height="21" width="25%" class="TdBgColor1" align="right">
		    	<fmt:message key='batch.checkdata.form.organmode'/>
		    </td>
		    <td class="TdBGColor2">
		    	<div id="mode">
				    <input type="radio" name="organMode" id="organMode" value="1" checked="checked" onclick="javascript:checkMode();"/>
				    <fmt:message key='batch.checkdata.form.organmode1'/>
				    <input type="radio" name="organMode" id="organMode" value="2" onclick="javascript:checkMode();"/>
				    <fmt:message key='batch.checkdata.form.organmode2'/></td>
			    </div>
		    </tr>
		    <tr>
		    	<td class="TdBgColor1" align="right">
		    		<fmt:message key="usermanage.organId" />
		    	</td>
		    	<td class="TdBGColor2">
		    		<input type="button" name="orgBtn" id="orgBtn" value="<fmt:message key="chartpage.button.org.name"/>" onclick="javascript:openOrganWindow();">
		    		<input type="text" name="organNames" id="organNames" value="" style="width:277;" readonly="readonly">
		    	</td>
		    </tr>
		    <tr id="organLevel">
		    	<td width="25%" class="TdBgColor1" align="right"><fmt:message key="batch.checkdata.form.organlevel"/></td>
	    		<td class="TdBgColor2" valign="top">
		    		<select name="organLevel" style="width:360;height:240;">
		    			<c:forEach	var="level" items="${organLevel}">
		    				<option value="<c:out value='${level.dicid}'/>"><c:out value="${level.dicname}"/></option>
		    			</c:forEach>
		    		</select>
		    	</td>
		    </tr>
		    <tr>
		    	<td class="TdBgColor1" align="right">
		    		<fmt:message key="usermanage.sysadmin.label.rule"/>
		    	</td>
		    	<td class="TdBGColor2">
		    		<select name="role" id="role" multiple="multiple" style="width:360;">
		    			<c:forEach var="role" items="${roleList}" >
		    				<option value="<c:out value="${role.roleType}"/>"><c:out value="${role.name}"/></option>
						</c:forEach>
		    		</select>
		    	</td>
		    </tr>
		    <tr>
		    	<td class="TdBgColor1" align="center" colspan="2">
		    		<input type="button" name="saveBtn" id="saveBtn" value="<fmt:message key="button.confirm"/>" onclick="javascript:saveConfig();">
		    		<input type="button" name="reBtn" id="reBtn" value="<fmt:message key="button.cancel"/>" onclick="javascript:resetConfig();">
		    	</td>
		    </tr>
    	</table>

    </form>
    </td>
    </tr>
    </table>
    </body>
</html>
<script type="text/javascript">
$(document).ready(function(){
/**
	var roleArray = new Array();
	<c:forEach var="role" items="${roleList}" varStatus="status">
		roleArray[<c:out value='${status.index}'/>] = new Array("<c:out value='${role.roleType}'/>",
		"<c:out value='${role.name}'/>");
	</c:forEach>
**/
	var selectedRole = "<c:out value="${roles}"/>";
	var reportName = "<c:out value="${reportName}"/>";
	if(selectedRole.length > 0)
	{
		var roles = selectedRole.split(",");
		for(var i = 0; i < document.reportConfigForm.role.options.length; i++)
		{
			for(var j = 0; j < roles.length; j++)
			{
				if(document.reportConfigForm.role.options[i].value == roles[j])
				{
					document.reportConfigForm.role.options[i].selected = true;
					break;
				}
			}
		}
	}
	//jQuery('#main').html(TrimPath.processDOMTemplate('main_jst',{roleArray:roleArray,funArray:funArray,selectedRole:selectedRole,reportName:reportName}).toString());
});
function openOrganWindow(){
	var mode = '1';
	$("#mode > input").each(function(i){
		if(this.checked){
			mode = this.value;
		}
	});
	//alert(mode);
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportConfigAction.do?method=showOrgan&style="+mode;
	OpenModalWin(url,"<fmt:message key='chartpage.wizard.modal.title'/>",400,320,"2,4",1,0);
}
function saveConfig()
{
	if($("#organNames").attr("value").length == 0)
	{
		alert("<fmt:message key="riskwarning.organ.null"/>");
		return;
	}
	var roleFlag = true;
	for(var i = 0; i < document.reportConfigForm.role.options.length; i++)
	{
		if(document.reportConfigForm.role.options[i].selected == true)
		{
			roleFlag = false;
			break;
		}
	}
	if(roleFlag)
	{
		alert("<fmt:message key="riskwarning.role.null"/>");
		return;
	}
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportConfigAction.do?method=saveRiskConfig";
	document.reportConfigForm.action = url;
	document.reportConfigForm.submit();
}
function resetConfig()
{
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportConfigAction.do?method=enter";
	document.reportConfigForm.action = url;
	document.reportConfigForm.submit();
}
function checkMode()
{
	$("#mode > input").each(function(i){
		if(this.checked)
		{
			
			if(this.value == '1')
				$("#organLevel").show();
			else
				$("#organLevel").hide();
		}
	});
}
</script>