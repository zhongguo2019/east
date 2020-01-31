<!--/ps/model/funconfig/selectfunconfiglist  -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="/sls/ps/style/comm.css">

 
 
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<link rel="stylesheet" type="text/css" href="/sls/ps/style/comm.css"/>
 <link rel="stylesheet" type="text/css" media="all" href="/sls/ps/style/displaytag.css" />
 <script type="text/javascript">
 	function deleteid(id){
 		if(confirm('<fmt:message key="usermanage.organ.form.confirmdel"/>')){
 			window.location.href="funconfigAction.do?method=deletefunconfig&funkey="+id;
 			} else{ 
 				return false;
 			}
 	}
 	function updateid(){
 		
 		 if(document.getElementById("funckey").value==""){
 			
 			alert('<fmt:message key="peizhimabunengweikong"/>');
 			return ;
 		}
 		var r =/^\d+$/;
 		if(!r.test(document.getElementById("funtype").value)){
 			
 			alert('<fmt:message key="leixingbixuweishuzi"/>');
 			return ;
 		} 
 		
 		
 		document.getElementById("funconfigForm").submit();
 	}
 	
 	function atiao(key){
 		var url="funconfigAction.do?method=editfunconfig&funkey="+key;
 		location.href=url;
 	}
 </script>
</head>
 
<body class="easyui-layout" fit="true">
	
	  <div data-options="region:'north'"  class="navbar">
      		<a href="#" class="easyui-linkbutton "  data-options="iconCls:'icon-add'" onclick="window.parent.window.OpenPanel('funconfigAction.do?method=openwindow','600','400','<fmt:message key="xinzengpeizhi"/>'); " style=" text-decoration:none;"><fmt:message key="fivegraderep.base.button.insert"/></a>
			
	</div>
	<div data-options="region:'west',split:true" style="width:70%">
		<display:table name="list" cellspacing="0" cellpadding="0"  
							    requestURI="" id="list" width="100%"  pagesize="18"
							    styleClass="list reportsList" >
						 <display:column sort="true" 
							    	headerClass="sortable" width="15%" 
							    	titleKey="key">
							    	<c:out value="${list[0]}"/>
						</display:column>
						 <display:column sort="true" 
							    	headerClass="sortable" width="15%" 
							    	titleKey="value">
							    	<c:out value="${list[1]}"/>
						</display:column>
						 <display:column sort="true" 
							    	headerClass="sortable" width="15%" 
							    	titleKey="jieshi">
							    	<c:out value="${list[2]}"/>
						</display:column>
						 <display:column sort="true" 
							    	headerClass="sortable" width="15%" 
							    	titleKey="leixing">
							    	<c:out value="${list[3]}"/>
						</display:column>
						 <display:column sort="true" 
							    	headerClass="sortable" width="15%" 
							    	titleKey="kuozhandiduan">
							    	<c:out value="${list[4]}"/>
						</display:column>
					<display:column sort="true" headerClass="sortable" width="15%" 
							    	titleKey="reportrule.edit">	
						
								<a href="#" onclick="atiao('<c:out value="${list[0]}"/>')" style="text-decoration: none;"><fmt:message key="button.edit"/></a>
								<a href="#" style="text-decoration: none;" onClick="deleteid('<c:out value="${list[0]}"/>')">
							<fmt:message key="button.delete"/>
						</a>
					</display:column>   
		</display:table>
		
	</display>
  	</div>
	 <div data-options="region:'center',split:true">
	 	<form action="funconfigAction.do?method=addfunconfig" class="easyui-form" method="post" name="funconfigForm" id="funconfigForm">
	 		 <input type="hidden" id="flag" name="flag" value="<c:out value="${flag}"/>">
	    	<table cellpadding="5" align="center">
	    		<tr>
	    			<td>key:</td>
	    			
	    			<td><c:if test="${flag==2}"><input type="hidden" name="funkey" id="funckey" value="<c:out value="${funkey}"/>"><c:out value="${funkey}"/></c:if></td>	
	    			
	    		</tr>
	    		<tr>
	    			<td>value:</td>
	    			<td><input  type="text" name="funvalue" value="<c:out value='${funvalue}'/>"></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key="jieshi"/>:</td>
	    			<td><input  type="text" name="fundes" value="<c:out value='${fundes}'/>"></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key="leixing"/>:</td>
	    			<td><input type="text"  id="funtype" name="funtype" value="<c:out value='${funtype}'/>"></input></td>
	    		</tr>
	    		<tr>
	    			<td><fmt:message key="kuozhandiduan"/>:</td>
	    			<td><input  type="text" name="funext1" value="<c:out value='${funext1}'/>"></input></td>
	    		</tr>
	    	</table>
	    	<div align="center">
	 		<c:if test="${flag==2}">
	 			<a href="#" class="easyui-linkbutton buttonclass2"  style="text-decoration: none;" name="ss" onclick="updateid();" ><fmt:message key='button.update'/></a>&nbsp;
	 		</c:if>
	 		</div>
	 	</form>
	 	
	 </div> 	
 
</body>
	
 
</html>

