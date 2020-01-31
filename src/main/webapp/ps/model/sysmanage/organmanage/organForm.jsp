<!-- /ps/model/sysmanage/organmanage/organForm. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/meta.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<c:url value='${eaUIPath}/jquery.min.js'/>"></script> 


	 <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
	 
	 <script type="text/javascript">  
	 			function quxiao(){
	 				document.organForm.action="organAction.do?method=list";
	 				document.organForm.submit();
	 			}
	 	
	 </script>
</head>
<body>

<table  align="center"  width="400" border="0" cellSpacing=1 cellPadding=5 height="200">    
<html:form action="organAction" method="post" styleId="organForm">
		<html:hidden property="pkid" />
		<html:hidden property="super_id" />
		<c:set var="pageButtons">
			<tr align="center" class="BtnBgColor" height="18">
				<td  align="right"></td>
			    <td  align="left">	
			     <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="return validate();"><fmt:message key='button.save' /></a>
			     &nbsp;&nbsp;&nbsp;&nbsp;
			     	<a href="#" class="easyui-linkbutton"  iconCls="icon-cancel" onclick="quxiao();" ><fmt:message key='button.cancel'/></a> 		         
			     </td>
			</tr>
		</c:set>
		<br>
        <br>
		
		 <tr style="height:18">
			<td  align="right" ><fmt:message
				key="usermanage.organ.form.code" /></td>
			<td  style="width:280;height:20" width="300"><html:text  maxlength="20" property="code" styleId="code" style="width:160;height:20" /><span style="color: red" id="codeSpan" ><fmt:message
				key="usermanage.organ.form.notnull" /></span></td>
		</tr>

		<tr height="18">
			<td  align="right"><fmt:message
				key="usermanage.organ.form.name" /></td>
			<td ><html:text maxlength="50" style="width:160;height:22" property="full_name" styleId="full_name"/><span style="color: red"><fmt:message
				key="usermanage.organ.form.notnull" /></span></td>
		</tr>

		<tr height="18">
			<td  align="right"><fmt:message
				key="usermanage.organ.form.short" /></td>
			<td ><html:text  maxlength="25" property="short_name" style="width:160;height:20" styleId="short_name"/><span style="color: red"><fmt:message
				key="usermanage.organ.form.notnull"/></span></td>
		</tr>

		<tr height="18">
			<td  align="right"><fmt:message
				key="usermanage.organ.form.organtype" /></td>
			<td >
			    <html:select property="organ_type" style ="width:160;height:20"  styleId="organ_type">
			    
                <html:options collection="typeList" property="dicid"
                  labelProperty="dicname" /></html:select><span style="color: red"><fmt:message
				key="usermanage.organ.form.selectnotnull"/></span></td>			
		</tr>
        
        <tr height="18">
			<td  align="right"><fmt:message
				key="usermanage.organ.form.businesstype" /></td>
            <td >
                <html:select property="business_type" style ="width:160;height:20" styleId="business_type">

                <html:options collection="btypeList" property="dicid"
                  labelProperty="dicname" /></html:select><span style="color: red"><fmt:message
				key="usermanage.organ.form.selectnotnull"/></span></td>
		</tr>
		

		<tr height="18">
			<td  align="right"><fmt:message
				key="usermanage.organ.form.zipcode" /></td>
			<td ><html:text  property="postalcode" maxlength="20" style ="width:160;height:20" styleId="postalcode"/></td>
		</tr>


		<tr height="18">
			<td  align="right"><fmt:message
				key="usermanage.organ.form.tel" /></td>
			<td ><html:text  property="phone" style ="width:160;height:20" maxlength="30"/></td>
		</tr>


		<tr height="18">
			<td  align="right"><fmt:message
				key="usermanage.organ.form.addr" /></td>
			<td ><html:text  property="adder" maxlength="100"  style ="width:160;height:20"/></td>
		</tr>


		<tr height="18">
			<td  align="right"><fmt:message
				key="usermanage.organ.form.delegate" /></td>
			<td ><html:text maxlength="50" property="delegate" style ="width:160;height:20" styleId="delegate"/><span style="color: red"><fmt:message
				key="usermanage.organ.form.notnull"/></span></td>
		</tr>		


		<tr height="18">
			<td  align="right"><fmt:message
				key="usermanage.organ.form.delmobile" /></td>
			<td ><html:text  property="del_mobile" maxlength="14" style ="width:160;height:20" styleId="del_mobile"/></td>
		</tr>


		<tr height="18">
			<td  align="right"><fmt:message
				key="usermanage.organ.form.delphone" /></td>
			<td ><html:text  property="del_phone" maxlength="17" style ="width:160;height:20"/></td>
		</tr>


		<tr height="18">
			<td   align="right"><fmt:message
				key="usermanage.organ.form.delother" /></td>
			<td ><html:text  property="del_other" maxlength="50" style ="width:160;height:20" styleId="del_other"/></td>
		</tr>

		<tr height="18">
			<td  align="right"><fmt:message
				key="usermanage.organ.form.begindate" /></td>
			<%-- <html:text  property="begin_date" style ="width:160;height:20" styleId = "beginDate" readonly = "true"/> --%>
			<td ><input id="begin_date" name="begin_date" type="text" 
									value="<c:out value='${organForm.begin_date}'/>" 
									style="width:160;" readonly="true" onClick="WdatePicker()"/>
									</td>
		</tr>
		<tr height="18">
			<td  align="right"><fmt:message
				key="usermanage.organ.form.enddate" /></td>
			<td >
			<%-- <html:text  property="end_date" style ="width:160;height:20" styleId = "endDate" readonly = "true"/> --%>
			<input id="end_date" name="end_date" type="text" 
									value="<c:out value='${organForm.end_date}'/>" 
									style="width:160;" readonly="true" onClick="WdatePicker()"/>
			</td>
		</tr>

		<c:out value="${pageButtons}" escapeXml="false" />
</html:form>
</table>


<script LANGUAGE='JavaScript'>
	window.onload=function(){
		if($('#code').val()!=""){
			$('#code').prop("readOnly",true);
			document.getElementById("codeSpan").style.display="none";
		}
	};  
	function validate()
			{
				if($('#code').val() == "")
				{
					alert("<fmt:message key="error.code"/>");
					$('#code').focus();
				    return false;
				}
				/*
				if(document.organForm.code.value.length!=8)
				{
					alert("<fmt:message key="error.codelength"/>");
					document.organForm.code.focus();
				    return false;
				}
				*/
				if($('#full_name').val() == "")
				{
					alert("<fmt:message key="error.full"/>");
					$('#full_name').focus();
				    return false;
				}
				if($('#short_name').val() == "")
				{
					alert("<fmt:message key="error.short"/>");
					$('#short_name').focus();
				    return false;
				}
				if($('#postalcode').val() != "")
				{
					if($('#postalcode').val().length!=6)
				    {
					   alert("<fmt:message key="error.postalcodelength"/>");
					   $('#postalcode').focus();
				       return false;
				    }				    
				    if(isNaN($('#postalcode').val()))
				    {
					    alert("<fmt:message key="error.postalcodecode"/>");
					    $('#postalcode').focus();
				        return false;
					  
				    }
				}
				if($('#delegate').val() == "")
				{
					alert("<fmt:message key="error.delegate"/>");
				    return false;
				}
				if($('#del_mobile').val() != "")
				{
					if(!(/^1[3456789]\d{9}$/.test($('#del_mobile').val())))
					{
						alert("<fmt:message key='personal.credit.identity.mpn'/><fmt:message key='fmate.error'/>");
					    return false;
					}
				}
				/*
				if($('#code').val().length != 8)
				{
					alert("<fmt:message key="error.codenotenough"/>");
				    return false;
				}
				*/
				if($('#begin_date').val()>$('#end_date').val())
				{
					alert("<fmt:message key="error.bdateerror"/>");
				    return false;
				}
				
			if($('#code').val() == "")
			{
						alert("<fmt:message key="error.code"/>"); $('#code').focus(); return false; 
			}else {
				if(!$('#code').val().match(/^\d+$/)){ alert("<fmt:message key="error.codeNum"/>");
					$('#code').focus(); return false; }
				}
				$('#organForm').prop('action',"organAction.do?method=save");
				$('#organForm').submit();
			}
						
		<c:if test="${error=='-1'}" >
		 alert("<fmt:message key="error.coderepeat"/>");
		</c:if>
		<c:if test="${error=='-2'}" >		
		 alert("<fmt:message key="error.organused.modify"/>");
		</c:if>	
-->
</script>

<p>&nbsp;</p>
</body>
</html>
