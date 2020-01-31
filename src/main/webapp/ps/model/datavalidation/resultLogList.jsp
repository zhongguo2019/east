<!-- /ps/model/datavalidation/com_logicrule. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>


<html>
    <head>
        <title><fmt:message key="webapp.prefix"/></title>
     <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
     <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
	 <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
    </head>					


<script language=JavaScript>


function  listReportRule(){
	var df=document.reportFormatForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/datavalidation.do" />?method=listResultShellCheckLog';
	df.submit();
}
 
 
	  function dangeCheck(val1,val2,val3,val4,val5){
		  var dataDate= document.getElementById('dataDate').value;
		  	var myDate = new Date();
		  	var year=myDate.getFullYear();
		  	var month = myDate.getMonth() + 1;
		  	month = month < 10 ? ("0" + month) : month;
		  	var dt = myDate.getDate();
		  	dt = dt < 10 ? ("0" + dt) : dt;
		  	var currdate=year+"-"+month+"-"+dt;
			if(dataDate > currdate){
				alert('<fmt:message key="view.xzrryj"/>');
				return;
			}
		  var params={"rulecode": val1,"dataDate":dataDate,"cflag":val2,"targettable":val4,"method":"checkData"};
			$.ajax({ 
				type: "POST", 
				async: false, //ajax
				url: "datavalidation.do", 
				data: jQuery.param(params), 
				success: function(result){
					alert('<fmt:message key="symbol.bracket.left"/>'+val5+'<fmt:message key="symbol.bracket.right"/>'+result);
				}
			});
	  }
	 
	function piliangcheck(){
		zhihui();
		var dataDate= document.getElementById('dataDate').value;
	  	var myDate = new Date();
	  	var year=myDate.getFullYear();
	  	var month = myDate.getMonth() + 1;
	  	month = month < 10 ? ("0" + month) : month;
	  	var dt = myDate.getDate();
	  	dt = dt < 10 ? ("0" + dt) : dt;
	  	var currdate=year+"-"+month+"-"+dt;
		if(dataDate > currdate){
			alert('<fmt:message key="view.xzrryj"/>');
			//document.getElementById('piliang').disabled=false; 
			 $('#piliang').linkbutton('enable');
			return;
		}
		 var arr = document.getElementsByName('yy');
		 var arrayObj="" ;
		 for(var i=0;i<arr.length;i++){
			 if( arr[i].checked ){
			 arrayObj+=arr[i].value+",";
			 }
		 }
		 if(""==arrayObj){
			 alert('<fmt:message key="view.qxzzb"/>');
			 //document.getElementById('piliang').disabled=false; 
			 $('#piliang').linkbutton('enable');
			 return;
		 }
		 $("#loading").show();
			$("#loading").css({
				 "top" : document.body.clientHeight/2 -25 + "px",
				"left" :  document.body.clientWidth/2 -150+ "px", 
				"display" : "block"
			});
			setTimeout("hide()",2000);
		var dataDate=document.getElementById("dataDate").value;
		var params={"mm": arrayObj,"dataDate":dataDate,"method":"checkData"};
		$.ajax({ 
			type: "POST", 
			async: false, //ajax
			url: "datavalidation.do", 
			data: jQuery.param(params), 
			success: function(result){
				 $("#loading").hide();
				alert(result);
				//document.getElementById('piliang').disabled=false;
				$('#piliang').linkbutton('enable');
			}
		});
	}
</script>
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
	
}
</script>
<script type="text/javascript">
		 //window.parent.document.getElementById("ppppp").value= "<fmt:message key="view.sjxy"/>";  
</script>
<body  onload="forbiddon();">

					<form name="reportFormatForm" action="reporFormat.do?method=listReportFormats" method="post" style="margin: 0;">
					<input type="hidden" value="<c:out value="${levelFlag}"/>" name="levelFlag"/>
					<div  name="optionbar" class="navbar2" >
						<table>
							<tr>
								<td>
									<fmt:message key="view.sjmx"/>
								</td>
								<td>
									<select name="reportn">
										<option value="0"><fmt:message key="view.sylx"/></option>
										<c:forEach items="${reportList}" var="rep"><c:if
										 test="${reportid==rep.pkid}"><option value="<c:out
											 value="${rep.pkid}"/>" selected><c:out value="${rep.name}"/></option></c:if>
											<c:if test="${reportid!=rep.pkid}"><option value="<c:out
											 value="${rep.pkid}"/>"><c:out value="${rep.name}"/></option></c:if></c:forEach>
									</select>
								</td>
								<td>
								       <fmt:message key="view.xyrq"/> 
								</td>
								<td>
									  <input id="dataDate" name="dataDate" type="text" value="<c:out value='${dataDate}'/>"  style="width:150;" readonly="true" onClick="WdatePicker()"/>
								</td>
								<td>
								
								    <a href="#" class="easyui-linkbutton buttonclass2 c6"   data-options="iconCls:'icon-checkall'" id="piliang"  onclick="piliangcheck();"><fmt:message key="button.icheck"/></a>&nbsp;&nbsp;
									<a href="#" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-search'"  name="ss" onclick="return listReportRule();" ><fmt:message key='button.search'/></a>
								</td>
							</tr>
						</table>
					</div>
					</form>	
				    <div align="center" style=" width:100%">
					<%-- 	<display:table name="reporRule" cellspacing="0" cellpadding="0"  
							    requestURI="" id="rt" width="100%"  pagesize="500"
							    styleClass="list reportsList" >
							 
							     <display:column width="3%">
											<input type=checkbox name="yy"  
												value="<c:out value="${rt.rulecode}"/>%<c:out value="${rt.targettable}"/>" 
												 />
                                </display:column>
							   
							     <display:column sort="true" 
							    	headerClass="sortable" width="10%" 
							    	titleKey="reportrule.rulecode">
							    	<c:out value="${rt.rulecode}"/>
							    </display:column>
							      <display:column sort="true" 
							    	headerClass="sortable" width="35%"   
							    	titleKey="reportrule.modelname">
							    	<c:out value="${rt.reportname}"/>
							    </display:column>
                                <display:column sort="true" 
							    	headerClass="sortable" width="30%" 
							    	titleKey="reportrule.ruleitem">
							    	<c:out value="${rt.targetname}"/>
							    </display:column>
			                   
			                    <display:column sort="true" 
							    	headerClass="sortable" width="15%" 
							    	titleKey="reportrule.typeanme">
							    	<c:out value="${rt.typename}"/>					    	
							    </display:column>
						
								<display:column sort="true" headerClass="sortable" width="5%" 
							    	titleKey="reportrule.status">
							    	<c:if test="${rt.cstatus==0}"><fmt:message key="contrastrelation.start"/>	</c:if>								
								    <c:if test="${rt.cstatus==1}"><fmt:message key="contrastrelation.stop"/>	</c:if>
								</display:column>
								 
							</display:table>
						 --%>			
				    </div>
 
	     <table style="border:0px solid black;" align="center"><tr><td>
			<c:if test="${message == '1'}"><img src="images/xiaolian.png"><font style="color:#990000;"><b><fmt:message key="view.zxcg"/></b></font></c:if>
 			<c:if test="${message == '2'}"><img src="images/kulian.png"><font style="color:#990000;"><b><fmt:message key="view.zxsb"/> </b></font></c:if>
			</td></tr></table>
			
	    
	    


        <div id="loading"
			style="position: absolute; display: none; color: black; background: #f0f0f0;">
			<table>
			<tr>
			<td>
				<img src="<c:url value='/images/loading1.gif'/>"/>
				</td>
				</tr>
				</table>
		</div>
</body>
</html>