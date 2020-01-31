<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<html>
    <head>
        <title><fmt:message key="webapp.prefix"/></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/tank.css'/>" />
<script type="text/javascript" src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.4.1.min.js'/>"></script>  
<script type="text/javascript" src="<c:url value='/ps/scripts/Dialog.js'/>"  > </script> 
 <script type="text/javascript">
		// window.parent.document.getElementById("ppppp").value= "<fmt:message key="navigation.reportfilemanage.createfile"/>";  

function reCreateTarsk(stId,tId,organId,dataDate,reportId)
{
	var batch='txt';
	var url="<c:out value='${hostPrefix}' /><c:url value='/repFileAction.do?method=createFileSerch&levelFlag=2' />"+"&reportId="+reportId+"&organId="+organId+"&begindate="+dataDate+"&enddate="+dataDate+"&batch="+batch+"&condates="+dataDate
			+"&stId="+stId+"&tId="+tId;
	//alert(url);
window.location.href=url;
}
function showSubList(tid){
	var url="<c:out value='${hostPrefix}' /><c:url value='/repFileAction.do?method=showSubTarsks&tId='/>"+tid;
	alert(url);
	//document.mainTarskForm.action=url;
	//document.mainTarskForm.submit();
	//window.frames["subDiv"].src=url;
	$("#subDiv").attr("src",url);
	$("#subDiv").reload();
   // window.frames["subDiv"].location=url;
}
<c:if test="${sessionScope.createFileSchedule != null}">
   window.location.href="ps/model/repfile/createfileProgress.jsp?_=" + (new Date()).getMilliseconds();
</c:if>

</script>              
</head>
<BODY style="padding-top:0px;margin-top:0px;" >
<form action="" method="post" id="mainTarskForm" name="mainTarskForm">
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
<%-- 
 <tr>
    <td width="100%"  valign="top">
       
    <input type="hidden"  name="processid" id="processid" value="<c:out value='${processid}' />" />
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
								//	highlightTableRows("format");
								//-->
							</script>
				    </div> 
				</td>
			</tr>
			<tr>
			  <td align="center" ><input type="button" value="<fmt:message key="button.back" />"     onClick="back();" /></td>
			  <script type="text/javascript">  
	  function back()
	  {
		 window.location='repFileAction.do?method=entercreateFileSerch&levelFlag=2';
		
	  }
    </script>
			</tr> --%>
		</table>
		</td>
		<tr>
		<td align="center">
		 <input type="hidden"  name="processid" id="processid" value="<c:out value='${processid}' />" />
				    <div align="center" style=" width:98%">
							    
			<table border="1" width="98%" align="left" cellSpacing=0 cellPadding=0>
			<tr>
			<td><fmt:message key="tarsk.tid" /></td>
			<td><fmt:message key="tarsk.status" /></td>
			<td><fmt:message key="tarsk.message" /></td>
			<td><fmt:message key="tarsk.createTime" /></td>
			<td><fmt:message key="tarsk.dirpath" /></td>
			</tr>
			<tr id="content" >
			</tr>
			
			</table>				    
		</div>
		</td>
		</tr>
		<tr>
		<td>
		
		
		</td>
		</tr>
  </tr>
  </table>
</form>
	

</BODY>
<script type="text/javascript">
$(document).ready(function(){
	//alert(123);
	setTimeout(function() {
		getTarsks();
	  },
	200);
	setInterval(function() {
		getTarsks();
	},
	30000);
});



function getTarsks(){
	//alert(1);
	var processid=$("#processid").val();
	var params={"processid":processid,"method":"showMainTarsks"};
	$.ajax({
		url:"repFileAction.do",
		data:jQuery.param(params),
		type:"post",
		async:true,
		dataType: "json",
		success:function(data){
			//alert(data[0].tId);
			var status;
			 if(data[0].status=='0') {status='<fmt:message key="st.status.0" />';}
			if(data[0].status=='1') {status='<fmt:message key="st.status.1" />';}
			if(data[0].status=='2') {status='<fmt:message key="st.status.2" />';}
			if(data[0].status=='3') {status='<fmt:message key="st.status.3" />';}
		$("#content").html("<td><a href='javascript:showSubTarsks("+data[0].tId+")'>"+data[0].tId+"</a></td><td>"+status+"</td><td>"+data[0].message+"</td><td>"+data[0].createTime+"</td><td>"+data[0].dirPath+"</td>");
			
		}
	});
}


function showSubTarsks(tid){
	var url="<c:out value='${hostPrefix}' /><c:url value='/repFileAction.do?method=showSubTarsks&tId='/>"+tid;
	 var win=null;
	    winState = "top=" + ((window.screen.height - 600) / 2) + ",left=" + ((window.screen.width - 800) / 2) + ",";
		winState += "height=400,width=600,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
		 win=  window.open(url,"showWindow",winState); 
		 win.focus();
}

</script>		
</HTML>

