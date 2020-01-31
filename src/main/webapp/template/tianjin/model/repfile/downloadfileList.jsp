<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<html>
    <head>
        <title><fmt:message key="webapp.prefix"/></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
        <script type="text/javascript" src="<c:url value='/ps/scripts/Dialog.js'/>"  > </script>        
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
	 
	<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.4.1.min.js'/>" type="text/javascript"></script>
    </head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" onload=" forbiddon()">

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
     <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
          <c:if test="${systemid==2}"> 
				<p style="margin-top: 3"><font class=b><b><fmt:message key="navigation.repifle.downloadfile"/></b></font></p>     
          		 </c:if>
          		 <c:if test="${systemid==1}"> 
          		 <p style="margin-top: 3"><font class=b><b><fmt:message key="view.blbyxz"/></b></font></p>
          		 </c:if>
          </td>
		  <td></td> 
        </tr>
        
      </table>
    </td>
  </tr>
  <c:if test="${systemid==2}"> 
	  <tr>
	          <td class="TdBGColor1">&nbsp;
	         <input type="button" value="<fmt:message key="button.selectall"/>" onClick="check_all();return false;"/>&nbsp;&nbsp;   
	          <input type="button" value="<fmt:message key="button.releaseall"/>" onClick="rel_all();return false"/>&nbsp;&nbsp;
	          <input type="button" value='<fmt:message key="view.plxz"/>' id="piliangdown"  onclick="piliangDown();"/>&nbsp;&nbsp;
	         <input type="button" value='<fmt:message key="view.plsc"/>' id="piliangdel"  onclick="piliangDel('<c:out value="${systemid}" />');"/>&nbsp;&nbsp;
	          </td>
	   </tr>
   </c:if>
  <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
		<table border="0" width="98%" align="left" cellSpacing=0 cellPadding=0>
			
			<tr>
				<td align="center">
				    <div align="center" style=" width:98%">
						<display:table name="repList" cellspacing="0" cellpadding="0"  
							    requestURI="" id="format" width="100%" 
							    pagesize="500" styleClass="list reportFormatList" >
							    <%-- Table columns --%>
							     <display:column width="3%">
									<input type=checkbox name="yy"  
										value="<c:out value="${format.pkid}" />" 
									/>
                                </display:column>
							    <display:column sort="true" 
							    	headerClass="sortable"
							    	titleKey="repfile.attr.repname">
							    	<c:out value="${format.name}"/>
							    </display:column>
							    <display:column sort="true" 
							    	headerClass="sortable"
							    	titleKey="repfile.download.filename">
							    	<c:out value="${format.repfilename}"/>
							    </display:column>
							    
							     <display:column nowrap="no" titleKey="repfile.download.fileurl">
							    		<c:out value="${format.filepath}"/>
							      </display:column>
							      <display:column nowrap="no" titleKey="reporttask.status">
							      	 <c:if test="${format.downloadnum==0}" >
							    		 <fmt:message key="repfile.downfile.status1" />
							    	</c:if>
							    	 <c:if test="${format.downloadnum!=0}" >
							    		 <fmt:message key="repfile.downfile.status2" />
							    	 </c:if>
							      </display:column>
							    
							<display:column nowrap="no" titleKey="common.list.operate">
							     <a href="<c:url value="/repFileAction.do?method=downloadfile"/>&pkid=<c:out value="${format.pkid}" />&systemid=<c:out value="${systemid}" />" ><fmt:message key="button.download"/></a>
							  </display:column>    
							  <display:column nowrap="no" titleKey="common.list.operate">
							      <a href="javascript:dfn('<c:out value="${format.pkid}" />','<c:out value="${systemid}" />')" ><fmt:message key="button.delete"/></a>
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
		</table>
		</td>
  </tr>
</table>
<form action="" name="reportFormatForm" method="post">

</form>


<script type="text/javascript">
<c:if test="${ not empty sucess}">
alert('<c:out value="${sucess}" />');
</c:if>
var f=document.reportFormatForm;
function piliangDown(){
	 var arr = document.getElementsByName('yy');
	 var arrayObj="" ;
	 for(var i=0;i<arr.length;i++){
		 if( arr[i].checked ){
		 arrayObj+=arr[i].value+",";
		 }
	 }
	 if(""==arrayObj){
		 alert('<fmt:message key="view.szbw"/>');
		 return;
	 }
	 f.action = "repFileAction.do?method=piLiangdownloadfile&mm="+arrayObj;
	 f.submit();
}

function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
	
}
function check_all(){
	   arr = document.getElementsByName('yy');
	   for(i=0;i<arr.length;i++){
	      arr[i].checked = true;
	   }
	  }
	  function rel_all(){
	   arr = document.getElementsByName('yy');
	   for(i=0;i<arr.length;i++){
	      arr[i].checked = false;
	   }
	  }
//页面加载完成  
$(document).ready(function(){  
    document.onkeypress = banBackSpace;  
    document.onkeydown = banBackSpace;  
}); 
function dfn(pkid,systemid){
	if(!confirm('<fmt:message key="view.qrsc"/>')){
		return;
	}
		var params={"pkid": pkid,"systemid":systemid,"method":"deldownloadfile"};
		$.ajax({ 
			type: "POST", 
			async: false, //ajax
			url: "repFileAction.do", 
			data: jQuery.param(params), 
			success: function(result){
				window.location.href = "repFileAction.do?method=downloadFile&systemid="+systemid;
				alert(result);
			}
		});
}
function piliangDel(systemid){
	var arr = document.getElementsByName('yy');
	 var arrayObj="" ;
	 for(var i=0;i<arr.length;i++){
		 if( arr[i].checked ){
		 arrayObj+=arr[i].value+",";
		 }
	 }
	 if(""==arrayObj){
		 alert('<fmt:message key="view.szbw"/>');
		 return;
	 }
	if(!confirm('<fmt:message key="view.qrsc"/>')){
		return;
	}
	var params={"mm": arrayObj,"method":"piLiangdelfile"};
	$.ajax({ 
		type: "POST", 
		async: false, //ajax
		url: "repFileAction.do", 
		data: jQuery.param(params), 
		success: function(result){
			window.location.href = "repFileAction.do?method=downloadFile&systemid="+systemid;
			alert(result);
		}
	});
}
</script>


</body>
</html>