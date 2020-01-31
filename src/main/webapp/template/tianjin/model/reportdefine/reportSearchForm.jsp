<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/meta.jsp"%>
<html>
<title><fmt:message key="webapp.prefix" /></title>

<LINK href="/theme/default/skin_01/style/common.css" rel="stylesheet" type="text/css">

<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/helptip.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='${cssPath}/print.css'/>" /> 
        <script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script> 
        <script type="text/javascript" src="<c:url value='/ps/scripts/effects.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/ps/scripts/helptip.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>  
        
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/ps/scripts/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script>  
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>


<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<script type="text/javascript">  
       function keydown(e) {  
           var isie = (document.all) ? true : false;  
           var key;  
           var ev;  
           if (isie){ 
               key = window.event.keyCode;  
               ev = window.event;  
           } else {  
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
<table border="0" cellpadding="0" cellspacing="0" width="100%"
	height="100%">
	<tr>
		<td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
				<p style="margin-top: 3"><font class=b><b><fmt:message
					key="navigation.reportdefine.search" /></b></font></p>
				</td>
				<td></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td width="100%" bgcolor="#EEEEEE" valign="top"><br>

		<html:form action="reportAction" method="post">
			<table cellSpacing=1 cellPadding=0 width="380" align=center border=0
				class="TableBGColor">
				<html:hidden property="pkid" />
				<c:set var="pageButtons">
					<tr align="center" class="BtnBgColor">
						<td class="TdBGColor1" width=80 align="left"></td>
						<td class="buttonBar" align="left"><html:submit
							property="method.search" style="width:60;"
							onclick="bCancel=false">
							<fmt:message key="button.search" />
						</html:submit></td>
					</tr>
				</c:set>

				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.name" /></td>
					<td class="TdBGColor2"><html:text property="name"
						style="width:240;" /></td>
				</tr>
				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.code" /></td>
					<td class="TdBGColor2"><html:text property="code"
						style="width:240;" /></td>
				</tr>

				<!--<tr>
			        5<td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.report.isSum"/>
			        </td>
			        <td class="TdBGColor2"> 2 
			        	<html:text property="isSum"/>
			        </td>
			    </tr>
				-->
				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.reportType" /></td>
					<td class="TdBGColor2"><html:select property="reportType" size="1"
						style="width:240;">
						<html:option value="">
							<fmt:message key="reportdefine.reportType.all" />
						</html:option>
						<c:forEach var="type" items="${types}">
							<html-el:option value="${type.pkid}">
								<c:out value="${type.name}" />
							</html-el:option>
						</c:forEach>
					</html:select></td>
				</tr>
				
				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.frequency" /></td>
					<td class="TdBGColor2"><html:select property="frequency" size="1"
						style="width:240;">
						<html:option value="">
							<fmt:message key="reportdefine.reportItem.allfrequency" />
						</html:option>
						<c:forEach var="frequency" items="${frequencys}">
							<html-el:option value="${frequency.dicid}">
								<c:out value="${frequency.dicname}" />
							</html-el:option>
						</c:forEach>
					</html:select></td>
				</tr>								
				<tr>
				    <td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.beginDate" /></td>
					<td class="TdBGColor2"><html:text property="beginDate"
						style="width:240;" readonly="true" /></td>											
			    </tr>
			    <script type="text/javascript">
				Calendar.setup({
					inputField     :    "beginDate",  
					ifFormat       :    "%Y-%m-%d",   
					showsTime      :    false
				});
			    </script>
			    <tr>
				    <td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.endDate" /></td>
					<td class="TdBGColor2"><html:text property="endDate"
						style="width:240;" readonly="true" /></td>											
			    </tr>
			    <script type="text/javascript">
				Calendar.setup({
					inputField     :    "endDate",  
					ifFormat       :    "%Y-%m-%d",   
					showsTime      :    false
				});
			    </script>
				<!--
			    <tr>
			        5<td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.report.rol"/>
			        </td>
			        <td class="TdBGColor2"> 2 
			        	<html:text property="rol"/>
			            
			        </td>
			    </tr>
			    <tr>
			        5<td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.report.moneyunit"/>
			        </td>
			        <td class="TdBGColor2"> 2 
			        	<html:text property="moneyunit"/>
			        </td>
			    </tr>
			   <tr>
			        5<td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.report.beginDate"/>
			        </td>
			        <td class="TdBGColor2"> 2 
			        	<html:text property="beginDate" styleId = "beginDate" readonly = "true"/>
			        </td>
			    </tr>
			    <tr>
			        5<td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.report.endDate"/>
			        </td>
			        <td class="TdBGColor2"> 2 
			        	<html:text property="endDate" styleId = "endDate" readonly = "true"/>
			        </td>
			    </tr>
			    <tr>
			        5<td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.report.description"/>
			        </td>
			        <td class="TdBGColor2"> 2 
			        	<html:text property="description"/>
			        </td>
			    </tr>
			    -->
				<c:out value="${pageButtons}" escapeXml="false" />
				<tr>
					<td class="FormBottom" colspan="2" height="17"></td>
				</tr>
			</table>

		</html:form>
		</td>
	</tr>
</table>

</body>
</html>
