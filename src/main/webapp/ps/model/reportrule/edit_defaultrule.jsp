<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%
   User user = (User)request.getSession().getAttribute("user");
   String orgId = user.getOrganId();   
%>
<c:set var="colName">
	<fmt:message key="common.list.reportname"/>
</c:set>
<c:set var="reportButton">
	<fmt:message key="place.select"/>
</c:set>
<c:set var="colNames">
	<fmt:message key="exportXML.tree.org.name"/>
<c:set var="reportTreeURL">
</c:set>
	<c:out value="${hostPrefix}"/><c:url value='/reportView.do?method=repTreeAjax${params}'/>
</c:set>
<c:set var="targetTreeURL">
	<c:out value="${hostPrefix}" /><c:url value='/integratedQuery.do?method=targetTree' />
</c:set>
<c:set var="targetButton">
	<fmt:message key="integratedQuery.button.targetSelect"/>
</c:set>
<c:set var="displaySumType">
	<%= FuncConfig.getProperty("reportview.showCollectType")%>
</c:set>
<c:set var="studentLoanReportId">
	<%= FuncConfig.getProperty("studentloan.reportid")%>
</c:set>
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" />
<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>    
<script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script>
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value='/ps/scripts/reportrule/edit_reportrule.js'/>"></script>
 
<script type="text/javascript">

function testsql(){
	var content=$("#content").val();
	var params={"content": content,"method":"testSql"};
	$.ajax({ 
		type: "POST", 
		async: false, //ajax
		url: "reportrule.do", 
		data: jQuery.param(params), 
		success: function(result){
			if('true'==result){
				alert('<fmt:message key="view.cg"/>');
			}else{
				alert('<fmt:message key="view.sb"/>');
			}
		}
	});
}

function gettarget(val){
	var params={"repid": val,"method":"getTargetList"};
	$.ajax({ 
		type: "POST", 
		async: false, //ajax
		url: "reportrule.do", 
		data: jQuery.param(params), 
		success: function(result){
			var bb=result.split("@");
			var mm=$("#content").val();
		    mm=mm+"( select          from "+bb[0]+" r1 where r1.REPORT_ID="+val+" )";
			$("#content").val(mm);
			var aa=bb[1].split(",");
			$("#ziduan1").empty();
			for(var i=0;i<aa.length;i++){
				var cc=aa[i].split(":");
				$("#ziduan1").append("<option value="+cc[0]+">"+cc[1]+"</option>");
			}
		}
	});
}
    
	function getReportComRule(val){
		$("#rtype1").val(val);
		var df=document.tijiaoForm;
		df.submit();
	}
	var df=document.reportViewForm;	
	function changeRep(){
		var oDate = document.reportViewForm.dataDate;
		var vDate = oDate.value;
		var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportView.do?method=repTreeAjax&levelFlag=2&paramdate="+vDate;	
		document.all.objTree1.SetXMLPath(url);
	   	document.all.objTree1.Refresh();	
	}

	function changeTree1(){
		changeRep();
		changetarget();
	}
		
	function changetarget(){
		var repid=df.reportId.value;	
		var repname=df.reportName.value;
		var url = "<c:out value="${targetTreeURL}"/>&reportId="+repid+"&reportName="+repname;
		document.all.objTree2.SetXMLPath(url);
	   	document.all.objTree2.Refresh();	
	}
	function changeTree2(){
		changetarget();
	}
	function openHelp() { 
		window.open ("reportrule/logichelp.jsp", "newwindow", "height=600, width=600, toolbar=no,menubar=no, scrollbars=yes, resizable=no, location=no, status=no") 
		} 
</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<script type="text/javascript">  
//ping bi ye mian zhong bu ke biam ji de wen ben kuang zhong de [backspace]an jiu shi jian  
       function keydown(e) {  
           var isie = (document.all) ? true : false;  
           var key;  
           var ev;  
           if (isie){ //IE he gu ge liu lan qi
               key = window.event.keyCode;  
               ev = window.event;  
           } else {//hu hu liu lan qi 
               key = e.which;  
               ev = e;  
           }  
  
           if (key == 8) {//IE he gu ge liu lan qi
               if (isie) {  
                   if (document.activeElement.readOnly == undefined || document.activeElement.readOnly == true) {  
                       return event.returnValue = false;  
                   }   
               } else {//hu hu liu lan qi  
                   if (document.activeElement.readOnly == undefined || document.activeElement.readOnly == true) {  
                       ev.which = 0;  
                       ev.preventDefault();  
                   }  
               }  
           }  
       }  
  
       document.onkeydown = keydown;  
       
       window.parent.document.getElementById("ppppp").value= "<fmt:message key="view.ywzbgzpi"/> >> <fmt:message key="view.mrgzsz"/>";
   </script> 
	<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  		
 		  <tr>
    		<td width="100%" height="36">
      			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			<c:if test="${cflag==1}">
      			<a href="reportrule.do?method=listDefRule&levelFlag=2" style="color:#990000;un"><strong><fmt:message key="button.back"/></strong></a>
      			</c:if>
      			<c:if test="${cflag!=1}">
      			<a href="reportrule.do?method=enterDefRule&levelFlag=2" style="color:#990000;un"><strong><fmt:message key="button.back"/></strong></a>
      			</c:if>
   			 </td>
 		 </tr>
  		 <tr>
    		 <td width="100%" valign="top" align="center">
    			<br>
   				<br>
   				<div class="optionBox1">
    				<div class="cont">
      					<ul>
      					<c:forEach var="rt" items="${lxhtargetList}" varStatus="index">
	        				<li><a href="reportrule.do?method=editTheDefRule&repid=<c:out value="${rt.reportId }"/>&rtarid=<c:out value="${rt.targetField }"/>&targetN=<c:out value="${rt.targetName }"/>&rtype=<c:out value="${reportrule.rtype }"/>&levelFlag=<c:out value="${levelFlag }"/>"><c:out value="${rt.targetName }"/></a></li>
	        			</c:forEach>
     			 		</ul>
   					 </div>
  				</div>
  				<!--end optionBox1-->
  				
				<div id="formwrapper">
				<label for="rcodee"><b><font style="color:#990000"><fmt:message key="view.zbmc"/><c:out value="${reportrule.targetname}"/></font></b></label>
				<a href="javascript:openHelp()"><img border="0" src="${imgPath}/iconInformation.gif" alt='<fmt:message key="view.help"/>' style="float:right;"></a>
				</br></br>
				<input type="hidden" name="rtype"  value="3"/>
			<form action="reportrule.do?method=saveYjhReportRule" method="post" name="reportViewForm" id="reportViewForm">
  				<input type="hidden" name="repid" value="<c:out value="${reportrule.modelid}"/>" />
  				<input type="hidden" name="rtarid" id="rtarid" value="<c:out value="${reportrule.targetid}"/>" />
  				<input type="hidden" name="targetN" id="targetN"value="<c:out value="${reportrule.targetname}"/>"/>
  				<input type="hidden" name="rtype1" id="rtype1"value="<c:out value="${reportrule.rtype}"/>"/>
  				<input type="hidden" name="bflag1" id="bflag1"value="4"/>
  				<input type="hidden" name="levelFlag" id="levelFlag"value="<c:out value="${levelFlag}"/>"/>
    				<fieldset id="fieldset1">
    					<legend><fmt:message key="view.gzsz"/></legend>
    				
    				<table>
    					<tr>
    				  		<td>
    				  				<fmt:message key="view.gzbm"/>
    				  		</td>
    				  		<td>
    				  				<input type="text" name="rulecode" value="<c:out value="${reportrule.rulecode}"/>"   />
    				  		</td>
    				  		
    				  	</tr>
                         <tr>
	      					 <td>
	      					 	<fmt:message key="view.gzzt"/>
	      					 </td>
	      					  <td>
	      					   <input type="radio" id ="cstatus1" name="cstatus" value="0" <c:if test="${reportrule.cstatus == '0'}"> checked </c:if>  checked><fmt:message key="contrastrelation.start"/></input>
	      					   <input type="radio" id ="cstatus2" name="cstatus" value="1" <c:if test="${reportrule.cstatus == '1'}"> checked </c:if>  ><fmt:message key="contrastrelation.stop"/></input>
	      					 </td>
                        </tr>
    				  	
    				  	 <tr>
	      					 <td>
	      					 	<fmt:message key="view.gzjb"/>
	      					 </td>
	      					  <td>
	      					  <textarea rows="10" cols="65" name="content" id="content"><c:out value="${reportrule.content }"/></textarea>	
	      					 </td>
                        </tr>
                         <tr>
	      					 <td>
	      					 	<fmt:message key="view.gzsm"/>
	      					 </td>
	      					  <td>
	      					  <textarea rows="6" cols="65" name="contentdes" id="contentdes"><c:out value="${reportrule.contentdes }"/></textarea>
	      					 </td>
                        </tr>
    				</table>
    				
    				</fieldset>
    				
 					 <br/>
 					 <label for="but" style="margin-left:250px;"><input type="submit" value='<fmt:message key="button.ratifypass"/>'  style="width:80;" /></label>
 					</br> <label for="but" style="margin-left:250px;"><c:if test="${message == '1'}"><img src="ps/framework/images/xiaolian.png"><font style="color:#990000;"><b><fmt:message key="view.bccg"/></b></font></c:if>
 					 <c:if test="${message == '2'}"><img src="ps/framework/images/kulian.png"><font style="color:#990000;"><b><fmt:message key="view.bcsb"/></b></font></c:if>
 					 </label>
				</div>
 					 </form>
			   <div id="users" style="display:none"></div>	
			</td>
		</tr>
	</table>
</body>
</html>
