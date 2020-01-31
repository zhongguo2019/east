<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/meta.jsp"%>
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
        
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
		<link rel="stylesheet" href="<c:url value='/ps/framework/images/css.css" type="text/css'/>"/>
</head>
<body leftmargin="0" bgcolor=#eeeeee  onload="backspaceForbiddon();">
<script type="text/javascript">  
       //屏蔽页面中不可编辑的文本框中的backspace按钮事件  
       function keydown(e) {  
           var isie = (document.all) ? true : false;  
           var key;  
           var ev;  
           if (isie){ //IE和谷歌浏览器  
               key = window.event.keyCode;  
               ev = window.event;  
           } else {//火狐浏览器  
               key = e.which;  
               ev = e;  
           }  
  
           if (key == 8) {//IE和谷歌浏览器  
               if (isie) {  
                   if (document.activeElement.readOnly == undefined || document.activeElement.readOnly == true) {  
                       return event.returnValue = false;  
                   }   
               } else {//火狐浏览器  
                   if (document.activeElement.readOnly == undefined || document.activeElement.readOnly == true) {  
                       ev.which = 0;  
                       ev.preventDefault();  
                   }  
               }  
           }  
       }  
  
       document.onkeydown = keydown;  
   </script> 
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
            <p style="margin-top: 3"><font class=b><b><fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.organ.menu"/></b></font></p>                         
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f05.gif"/>" valign="top">
    <p>
    </p>

<table  align="center" class="TableBGColor" width="400" border="0" cellSpacing=1 cellPadding=5 height="200">    
<html:form action="organAction" method="post" styleId="organForm">
		<html:hidden property="pkid" />
		<html:hidden property="super_id" />
		<c:set var="pageButtons">
			<tr align="center" class="BtnBgColor" height="18">
				<td class="TdBGColor1" align="right"></td>
			    <td class="buttonBar" align="left">			         
			          <input name="method.save" type="submit" value="<fmt:message key="button.save" />" style="width:60;" onClick="return validate();">			          
			          <input name="method.list" type="submit" value="<fmt:message key="button.cancel" />" style="width:60;" onClick="bCancel=true">
			     </td>
			</tr>
		</c:set>
		<br>
        <br>
		
		 <tr style="height:18">
			<td class="TdBGColor1" align="right" ><fmt:message
				key="usermanage.organ.form.code" /></td>
			<td class="TdBGColor2" style="width:280;height:20" width="300"><html:text  maxlength="20" property="code" style="height:20"  /><fmt:message
				key="usermanage.organ.form.notnull" /></td>
		</tr>

		<tr height="18">
			<td class="TdBGColor1" align="right"><fmt:message
				key="usermanage.organ.form.name" /></td>
			<td class="TdBGColor2"><html:text maxlength="50" style="height:22" property="full_name"/><fmt:message
				key="usermanage.organ.form.notnull" /></td>
		</tr>

		<tr height="18">
			<td class="TdBGColor1" align="right"><fmt:message
				key="usermanage.organ.form.short" /></td>
			<td class="TdBGColor2"><html:text  maxlength="25" property="short_name" style="height:20"/><fmt:message
				key="usermanage.organ.form.notnull"/></td>
		</tr>

		<tr height="18">
			<td class="TdBGColor1" align="right"><fmt:message
				key="usermanage.organ.form.organtype" /></td>
			<td class="TdBGColor2">
			    <html:select property="organ_type" style ="width:160;height:20"  >
			    
                <html:options collection="typeList" property="dicid"
                  labelProperty="dicname" /></html:select><fmt:message
				key="usermanage.organ.form.selectnotnull"/></td>			
		</tr>
        
        <tr height="18">
			<td class="TdBGColor1" align="right"><fmt:message
				key="usermanage.organ.form.businesstype" /></td>
            <td class="TdBGColor2">
                <html:select property="business_type" style ="width:160;height:20" >
<!--                 <script type="text/javascript">
function forbiddon(){
	document.getElementByName("business_type").onkeydown=function(){if(event.keyCode==8)return false;};
	document.getElementByName("organ_type").onkeydown=function(){if(event.keyCode==8)return false;};
}
</script> -->
                <html:options collection="btypeList" property="dicid"
                  labelProperty="dicname" /></html:select><fmt:message
				key="usermanage.organ.form.selectnotnull"/></td>
		</tr>
		

		<tr height="18">
			<td class="TdBGColor1" align="right"><fmt:message
				key="usermanage.organ.form.zipcode" /></td>
			<td class="TdBGColor2"><html:text  property="postalcode" style ="width:160;height:20"/></td>
		</tr>


		<tr height="18">
			<td class="TdBGColor1" align="right"><fmt:message
				key="usermanage.organ.form.tel" /></td>
			<td class="TdBGColor2"><html:text  property="phone" style ="width:160;height:20"/></td>
		</tr>


		<tr height="18">
			<td class="TdBGColor1" align="right"><fmt:message
				key="usermanage.organ.form.addr" /></td>
			<td class="TdBGColor2"><html:text  property="adder" style ="width:160;height:20"/></td>
		</tr>


		<tr height="18">
			<td class="TdBGColor1" align="right"><fmt:message
				key="usermanage.organ.form.delegate" /></td>
			<td class="TdBGColor2"><html:text maxlength="50" property="delegate" style ="width:160;height:20"/><fmt:message
				key="usermanage.organ.form.notnull"/></td>
		</tr>		


		<tr height="18">
			<td class="TdBGColor1" align="right"><fmt:message
				key="usermanage.organ.form.delmobile" /></td>
			<td class="TdBGColor2"><html:text  property="del_mobile" style ="width:160;height:20"/></td>
		</tr>


		<tr height="18">
			<td class="TdBGColor1" align="right"><fmt:message
				key="usermanage.organ.form.delphone" /></td>
			<td class="TdBGColor2"><html:text  property="del_phone" style ="width:160;height:20"/></td>
		</tr>


		<tr height="18">
			<td class="TdBGColor1"  align="right"><fmt:message
				key="usermanage.organ.form.delother" /></td>
			<td class="TdBGColor2"><html:text  property="del_other" style ="width:160;height:20"/></td>
		</tr>

		<tr height="18">
			<td class="TdBGColor1" align="right"><fmt:message
				key="usermanage.organ.form.begindate" /></td>
			<td class="TdBGColor2"><html:text  property="begin_date" style ="width:160;height:20" styleId = "beginDate" readonly = "true"/></td>
		</tr>
		<tr height="18">
			<td class="TdBGColor1" align="right"><fmt:message
				key="usermanage.organ.form.enddate" /></td>
			<td class="TdBGColor2"><html:text  property="end_date" style ="width:160;height:20" styleId = "endDate" readonly = "true"/></td>
		</tr>

		<c:out value="${pageButtons}" escapeXml="false" />
</html:form>
</table>
</td>
</tr>
</table>
<script LANGUAGE='JavaScript'><!--
	  Calendar.setup({
	    inputField     :    "beginDate",  
     	ifFormat       :    "%Y-%m-%d",     
    		showsTime      :    false
        	});
      Calendar.setup({
	    inputField     :    "endDate",  
     	ifFormat       :    "%Y-%m-%d",     
    		showsTime      :    false
        	});
	function validate()
			{
				if(document.organForm.code.value == "")
				{
					alert("<fmt:message key="error.code"/>");
					document.organForm.code.focus();
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
				if(document.organForm.full_name.value == "")
				{
					alert("<fmt:message key="error.full"/>");
					document.organForm.full_name.focus();
				    return false;
				}
				if(document.organForm.short_name.value == "")
				{
					alert("<fmt:message key="error.short"/>");
					document.organForm.short_name.focus();
				    return false;
				}
				if(document.organForm.postalcode.value != "")
				{
					if(document.organForm.postalcode.value.length!=6)
				    {
					   alert("<fmt:message key="error.postalcodelength"/>");
					   document.organForm.postalcode.focus();
				       return false;
				    }				    
				    if(isNaN(document.organForm.postalcode.value))
				    {
					    alert("<fmt:message key="error.postalcodecode"/>");
					    document.organForm.postalcode.focus();
				        return false;
					  
				    }
				}
				if(document.organForm.delegate.value == "")
				{
					alert("<fmt:message key="error.delegate"/>");
				    return false;
				}
				/*
				if(document.organForm.code.value.length != 8)
				{
					alert("<fmt:message key="error.codenotenough"/>");
				    return false;
				}
				*/
				if(document.organForm.begin_date.value>document.organForm.end_date.value)
				{
					alert("<fmt:message key="error.bdateerror"/>");
				    return false;
				}
				
				if(document.organForm.code.value == "")
				{ 
						alert("<fmt:message key="error.code"/>"); document.organForm.code.focus(); return false; 
			}else {
				if(!document.organForm.code.value.match(/^\d+$/)){ alert("<fmt:message key="error.code"/>"); 
					document.organForm.code.focus(); return false; }
				}
				
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
