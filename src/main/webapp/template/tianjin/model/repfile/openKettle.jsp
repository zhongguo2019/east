<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>


    <head>
        <title><fmt:message key="webapp.prefix"/></title>
        
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
		<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
		
		<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
		
		<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
    </head>					


<script language=JavaScript>

	function updateKtr(){
		window.location.href="repFileAction.do?method=openattach";
	}
	

	function queryktr(){
		document.ktrForm1.action="repFileAction.do?method=queryktr&ktrnamed="+$("#ktrnamed").val()+"&ktrremarked="+$("#ktrremarked").val()+"&ktrtimed="+$("#ktrtimed").val();
		document.ktrForm1.submit();
	}
	
	function fanhui(){
		document.ktrForm1.action="repFileAction.do?method=entercreateFileSerch&levelFlag=2";
		document.ktrForm1.submit();
	}
	
	function delktr(pkid){
		if(confirm("<fmt:message key="dicListPage.cfmdel"/>")){
			param={"pkid":pkid};
			$.post("repFileAction.do?method=delktr",param,function(data){
					var jsondata = eval(data);
				  var flag = jsondata[0].flag;
				  if(flag==4){
						alert("<fmt:message key="kettle.fileData.del"/>");
					}
				  if(flag==5){
					  var message = jsondata[0].message;
						alert(message);
					}
				window.location.href="repFileAction.do?method=openKettle";
			});
			
		}
	}
	
	function activdKtr(pkid,ktrPath){
		if(confirm("<fmt:message key="view.kettle.messa"/>")){
			param={"pkid":pkid,"ktrPath":ktrPath};
			$.post("repFileAction.do?method=activdKtr",param,function(data){
					var jsondata = eval(data);
				  var flag = jsondata[0].flag;
				  if(flag==2){
					  alert("<fmt:message key="kettle.fileData.actived"/>");
					}
				  if(flag==3){
					  var message = jsondata[0].message;
						alert(message);
					}
				window.location.href="repFileAction.do?method=openKettle";
			});
			
		}
		
	}
</script>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
     <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
				<p style="margin-top: 3"><font class=b color="#000000"><b><fmt:message key="view.kettle.list"/> </b></font></p>     
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
		<table border="0" width="98%" align="left" cellSpacing=0 cellPadding=0>
		<input id="flag" value="<c:out value='${flag}'/>" type="hidden"/>
		<input id="message" value="<c:out value='${message}'/>" type="hidden"/>
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value='<%=session.getAttribute("org.apache.struts.action.TOKEN")%>' />
			<tr>
				<td height="30" align="left">
					<form name="ktrForm" action="repFileAction.do?method=openKettle" method="post">
									&nbsp;&nbsp;<fmt:message key="kettle.name"/>
									<input type="text" value="" id="ktrnamed"/>
									&nbsp;&nbsp;
									<fmt:message key="kettle.remark1"/>
									<input type="text" value="" id="ktrremarked"/>
									&nbsp;&nbsp;
									<fmt:message key="view.xyrq"/> <input id="ktrtimed" name="ktrtimed" type="text" value="" 
									style="width:100;" readonly="true" >
									<script type="text/javascript">
											Calendar.setup({
												inputField     :    "ktrtimed",  
												ifFormat       :    "%Y-%m-%d",     
												showsTime      :    false,
												align          :    "tl"
										});
									</script>&nbsp;&nbsp;
									<input type="button" width="200" height="16" value='<fmt:message key="button.search"/>' onClick="queryktr()">&nbsp;&nbsp;
					</form>
				</td>
			</tr>
         <tr>
          <td class="TdBGColor1"><br>&nbsp;
          <input type="button" value="<fmt:message key="button.back"/>" onClick="fanhui();"/>&nbsp;&nbsp;  
         <input type="button" value="<fmt:message key="gdextdata.uploadFile"/>" onClick="updateKtr();"/>&nbsp;&nbsp;  
         </td>
         </tr>
			<form name="ktrForm1" action="#" method="post">
			<tr>
				<td align="center">
				    <div align="center" style=" width:98%">
						<display:table name="kdlist" cellspacing="0" cellpadding="0"  
							    requestURI="" id="kdlist" width="100%" 
							    pagesize="30" styleClass="list reportFormatList" >

							    
							    <display:column sort="true" 
							    	headerClass="sortable"
							    	titleKey="repfile.download.filename">
							    	<c:out value="${kdlist.ktrName}"/>
							    </display:column>
							    
							    <display:column nowrap="no" titleKey="kettle.remark" maxLength="20">
							    		<c:out value="${kdlist.ktrRemark}"/>
							      </display:column>
							    
							     <display:column nowrap="no" titleKey="kettle.user">
							    		<c:out value="${kdlist.userName}"/>
							      </display:column>
							      
							      <display:column nowrap="no" titleKey="kettle.time">
							    		<c:out value="${kdlist.ktrTime}"/>_<c:out value='${kdlist.ktrPath}'/>
							      </display:column>
							      
							      <display:column nowrap="no" titleKey="log.list.status">
							      	<c:if test="${kdlist.attribute1==1}"><fmt:message key="kettle.fileData.successd" /></c:if>
							    	<c:if test="${kdlist.attribute1==2}"><fmt:message key="kettle.fileData.actived" /></c:if>
							    	<c:if test="${kdlist.attribute1==3}"><a title="<c:out value="${kdlist.attribute4}"/>"><fmt:message key="kettle.fileData.failed" /></a></c:if>	
							      </display:column>
							      
							      <display:column nowrap="no" titleKey="kettle.lasttime">
							    		<c:out value="${kdlist.attribute2}"/>
							      </display:column>
							      
							      <display:column nowrap="no" titleKey="kettle.lastuser">
							    		<c:out value="${kdlist.attribute3}"/>
							      </display:column>
							    
							
							  <display:column nowrap="no" titleKey="common.list.operate">
							      <a href="#" onclick="activdKtr('<c:out value='${kdlist.pkid}'/>','<c:out value='${kdlist.ktrPath}'/>')" ><fmt:message key="loanDeviation.btn.file"/></a>
							      &nbsp;&nbsp;&nbsp;&nbsp;
							      <a href="#" onclick="delktr('<c:out value='${kdlist.pkid}'/>')" ><fmt:message key="button.delete"/></a>
							  </display:column>   
							</display:table>
						
				    </div>
				</td>
			</tr>
			</form>
		</table>
				</td>
			</tr>
			<tr height=17><td></td></tr>
			<tr height=17><td></td></tr>
		 <tr><td class="TdBGColor1" colspan="2">
</body>
</html>