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
    </head>
<BODY style="padding-top:0px;margin-top:0px;" >
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
     <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
				<p style="margin-top: 3"><font class=b><b><fmt:message key="navigation.reportfilemanage.createfile"/></b></font></p>     
          </td>
		  <td></td> 
        </tr> 
      </table>
    </td>
  </tr>

 <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
		<table border="0" width="98%" align="left" cellSpacing=0 cellPadding=0>
			
			<tr>
				<td align="center">
				    <div align="center" style=" width:98%">
						<display:table name="repList" cellspacing="0" cellpadding="0"  
							    requestURI="" id="format" width="100%" 
							    pagesize="30" styleClass="list reportFormatList" >
							    <display:column sort="true" 
							    	headerClass="sortable"
							    	titleKey="reportformat.list.repformat">
							    	<c:out value="${format.repname}"/>
							    </display:column>
							     <display:column nowrap="no" titleKey="repfile.rep.result">
							     		<c:out value="${format.successinfo}" />
							      </display:column>

							</display:table>
							<script type="text/javascript">
								<!--
									highlightTableRows("format");
								//-->
							</script>
				    </div>
				</td>
			</tr>
			<tr>
			  <td align="center"  bgcolor="#EEEEEE"><input type="button" value="<fmt:message key="button.back" />"     onClick="back();" /></td>
			  <script type="text/javascript">  
	  function back()
	  {
		 window.location='repFileAction.do?method=entercreateFileSerch&levelFlag=2';
		
	  }
    </script>
			  </tr>
		</table>
		</td>
  </tr>
  </table>

</BODY>
		
</HTML>

