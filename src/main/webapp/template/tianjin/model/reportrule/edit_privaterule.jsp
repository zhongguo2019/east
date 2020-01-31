<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%
   User user = (User)request.getSession().getAttribute("user");
   String orgId = user.getOrganId();   
%>

<html>
<head>
<title></title>
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" />
<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>    
<script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script>
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value='/ps/scripts/reportrule/edit_reportrule.js'/>"></script>
 
 <style type="text/css">
	body {
		font-family: Arial, Helvetica, sans-serif;
		font-size:12px;
		color:#666666;
		background:#fff;
		text-align:center;
		}
	* {
	margin:0;
	padding:0;
		}

	a {
	color:#000;
	text-decoration:none; 
	}
	a:hover {
	color:#1E7ACE;
	text-decoration:underline;
	}
	h3 {
	font-size:14px;
	font-weight:bold;
	}

input, select,textarea {
padding:1px;
margin:2px;
font-size:11px;
}
.buttom{
padding:1px 10px;
font-size:12px;
border:1px #1E7ACE solid;
background:#D0F0FF;
}
#formwrapper {
width:80%;
margin:15px auto;
padding:20px;
text-align:left;
border:1px solid #A4CDF2;
}
fieldset {
padding:10px;
margin-top:5px;
border:1px solid #A4CDF2;
background:none;
}
fieldset legend {
color:#1E7ACE;
font-weight:bold;
padding:3px 20px 3px 20px;
border:1px solid #A4CDF2; 
background:none;
}
fieldset label {
float:left;
text-align:right;
padding:4px;
margin:1px;
}
fieldset div {
clear:left;
margin-bottom:2px;
}
.enter{ text-align:center;}
.clear {
clear:both;
}

.optionBox1{
	background:url(/ps/framework/images/bg-optionBox1-x.gif) repeat-x  top;
	overflow:hidden;
	width:80%;
}
/*
.optionBox1 .sign{
	float:left;
	padding:8px;
}
*/
.optionBox1 .cont{
	/*
	margin-left:75px!important;
	margin-left:73px;
	*/
	padding:8px;
}
.optionBox1 ul{overflow:hidden;}
.optionBox1 ul li{
	float:left;
	padding:0 4px;
	background:url(/ps/framework/images/border-option.png) no-repeat right center;
	white-space:nowrap;
}
.optionBox1 ul li a{
	display:block;
	width:82;
	height:28px;
	line-height:28px;
	text-align:center;	
}
.optionBox1 ul li a:visited{font-weight:bold;color:#990000;}

</style>
<script type="text/javascript">
	
	function getReportComRule(val){
		$("#rtype1").val(val);
		var df=document.tijiaoForm;
		df.submit();
	}
	function openHelp() { 
		window.open ("reportrule/basichelp.jsp", "newwindow", "height=600, width=600, toolbar=no,menubar=no, scrollbars=yes, resizable=no, location=no, status=no") 
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
  
           if (key == 8) { //IE he gu ge liu lan qi
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
   </script> 
	<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  		<tr>
    		<td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td> 
          <p style="margin-top: 3"><font class=b><b><fmt:message key="view.ywzbgzpi"/> >> <fmt:message key="view.yssjgzpz"/></b></font></p> </td>
		  				<td></td> 
        			</tr>
      			</table>
   			 </td>
 		 </tr>
 		  <tr>
    		<td width="100%" bgcolor="#EEEEEE"  height="36">
      			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      			<c:if test="${cflag==1}">
      			<!-- <a href="javascript:history.back();" style="color:#990000;un"><strong><fmt:message key="button.back"/></strong></a> -->
      			<a href="reportrule.do?method=listPriRule&levelFlag=1" style="color:#990000;un"><strong><fmt:message key="button.back"/></strong></a>
      			</c:if>
      			<c:if test="${cflag!=1}">
      			<a href="reportrule.do?method=enterPriRule&levelFlag=1" style="color:#990000;un"><strong><fmt:message key="button.back"/></strong></a>
      			</c:if>
   			 </td>
 		 </tr>
  		 <tr>
    		 <td width="100%" bgcolor="#EEEEEE" valign="top" align="center">
    			<br>
   				<br>
   				<div class="optionBox1">
    				<div class="cont">
      					<ul>
      					<c:forEach var="rt" items="${lxhtargetList}" varStatus="index">
	        				<li><a href="reportrule.do?method=editThePriRule&repid=<c:out value="${rt.reportId }"/>&rtarid=<c:out value="${rt.targetField }"/>&targetN=<c:out value="${rt.targetName }"/>&rtype=<c:out value="${reportrule.rtype }"/>&bflag=0&levelFlag=<c:out value="${levelFlag }"/>"><c:out value="${rt.targetName }"/></a></li>
	        			</c:forEach>
     			 		</ul>
   					 </div>
  				</div>
  				<!--end optionBox1-->
  					
				<div id="formwrapper">
				<label for="rcodee"><b><font style="color:#990000"><fmt:message key="view.zbmc"/><c:out value="${reportrule.targetname}"/></font></b>
				</label>
				<a href="javascript:openHelp()"><img border="0" src="ps/framework/images/iconInformation.gif" alt='<fmt:message key="view.help"/>' style="float:right;"></a>
				
				</br></br>
					<input type="hidden" name="rtype"  value="3"/>
			<form action="reportrule.do?method=saveYjhReportRule" method="post" name="apLogin" id="apLogin">
  				<input type="hidden" name="repid" id="replid" value="<c:out value="${reportrule.modelid}"/>" />
  				<input type="hidden" name="rtarid" id="rtarid" value="<c:out value="${reportrule.targetid}"/>" />
  				<input type="hidden" name="targetN" id="targetN"value="<c:out value="${reportrule.targetname}"/>"/>
  				<input type="hidden" name="rtype1" id="rtype1"value="<c:out value="${reportrule.rtype}"/>"/>
  				<input type="hidden" name="defChar" id="defChar"value="<c:out value="${defChar}"/>"/>
  				<input type="hidden" name="bflag1" id="bflag1"value="3"/>
  				<input type="hidden" name="levelFlag" id="levelFlag" value="<c:out value="${levelFlag}"/>"/>
  				<input type="hidden" name="range" id="range" value="<c:out value="${range}"/>"/>
    				<fieldset id="fieldset1">
    					<legend><fmt:message key="view.gzsz"/></legend>
    				<br><br>
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
	      					  <select id="content" name="content" >
    				  			<option value='checkName' <c:if test="${reportrule.content=='checkName' }"> selected </c:if> selected><fmt:message key="view.xmtm"/></option>
    				  			<option value='checkIDcard' <c:if test="${reportrule.content=='checkIDcard' }"> selected </c:if> ><fmt:message key="view.sftm"/></option>
    				  			</select>	
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
 					 <label for="but" style="margin-left:250px;"><input type="submit" value='<fmt:message key="button.ratifypass"/>' style="width:80;" ></label>
 					</br> <label for="but" style="margin-left:250px;"><c:if test="${message == '1'}"><img src="ps/framework/images/xiaolian.png"><font style="color:#990000;"><b><fmt:message key="view.bccg"/></b></font></c:if>
 					 <c:if test="${message == '2'}"><img src="ps/framework/images/kulian.png"><font style="color:#990000;"><b> <fmt:message key="view.bcsb"/></b></font></c:if>
 					 </label>
				</div>
 					 </form>
			   <div id="users" style="display:none"></div>	
			</td>
		</tr>
	</table>
</body>
</html>
