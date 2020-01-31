<!-- /ps/model/repfile/downloadfileList. -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<%@ include file="/ps/framework/common/easyUItag.jsp"%>

<html>
    <head>
        <title><fmt:message key="webapp.prefix"/></title>

	 <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
	 <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />


	 
	 
	 
 <script type="text/javascript">
	    <c:if test="${systemid==2}"> 
	        window.parent.document.getElementById("ppppp").value= "<fmt:message key="navigation.repifle.downloadfile"/>";  
		 </c:if>
		 <c:if test="${systemid==1}"> 
		    window.parent.document.getElementById("ppppp").value= "<fmt:message key="view.blbyxz"/>";
		 </c:if>
		   
</script>
    </head>
<body  onload=" forbiddon()">
 <c:if test="${systemid==2}"> 
	          <div class="navbar">
	          <table>
	          		<tr>
	          			<td>
	          				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-selectall'" onclick="check_all();return false;"><fmt:message key="button.selectall"/></a>
	          			</td>
	          			<td>
	          				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-unselectall'" onclick="rel_all();return false"><fmt:message key="button.releaseall"/></a>
	          			</td>
	          			<td>
	          				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-download'" onclick="piliangDown();"><fmt:message key="view.plxz"/></a>
	          			</td>
	          			<td>
	          				  <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="piliangDel('<c:out value="${systemid}" />');"><fmt:message key="view.plsc"/></a>
	          			</td>

	          		</tr>
	          </table>

	          </div>
   </c:if>
<table border="0" cellpadding="0" cellspacing="0" width="100%" >
 
  <tr>
    <td width="100%"  valign="top">
		<table border="0" width="98%" align="left" cellSpacing=0 cellPadding=0>
			
			<tr>
				<td >
				    <div align="center" style=" width:98%">
						<display:table name="repList" cellspacing="0" cellpadding="0"  
							    requestURI="" id="format" width="100%" 
							    pagesize="30" styleClass="list reportFormatList" >
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
							 <!--  <a href="<c:url value="/repFileAction.do?method=downloadfile"/>&pkid=<c:out value="${format.pkid}" />&systemid=<c:out value="${systemid}" />" class="easyui-linkbutton" style="width:80; height: 20;" onclick="piliangDel('<c:out value="${systemid}" />');"><fmt:message key="button.download"/></a>-->
							    <a href="<c:url value="/repFileAction.do?method=downloadfile"/>&pkid=<c:out value="${format.pkid}" />&systemid=<c:out value="${systemid}" />" ><fmt:message key="button.download"/></a> 
							  </display:column>    
							  <display:column nowrap="no" titleKey="common.list.operate">
							      <a href="javascript:dfn('<c:out value="${format.pkid}" />','<c:out value="${systemid}" />')" ><fmt:message key="button.delete"/></a>
							  </display:column>   
							</display:table>
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
/* $(document).ready(function(){  
    document.onkeypress = banBackSpace;  
    document.onkeydown = banBackSpace;  
});  */
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