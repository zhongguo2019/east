<!-- /template/tianjin/model/reportrule/edit_basicrule.jsp -->
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
<title>    </title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
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
	background:url(ps/framework/images/bg-optionBox1-x.gif) repeat-x  top;
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
	background:url(ps/framework/images/border-option.png) no-repeat right center;
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
   </script> 
	<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  		<tr>
    		<td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td> <p style="margin-top: 3"><font class=b><b><fmt:message key="view.ywzbgzpi"/> >> <fmt:message key="view.jbgzpz"/></b></font></p> </td>
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
      			<a href="reportrule.do?method=listBasicRule&levelFlag=1" style="color:#990000;un"><strong><fmt:message key="button.back"/></strong></a>
      			</c:if>
      			<c:if test="${cflag!=1}">
      			<a href="reportrule.do?method=enterBasicRule&levelFlag=1" style="color:#990000;un"><strong><fmt:message key="button.back"/></strong></a>
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
	        				<li><a href="reportrule.do?method=editTheReportRule&repid=<c:out value="${rt.reportId }"/>&rtarid=<c:out value="${rt.targetField }"/>&targetN=<c:out value="${rt.targetName }"/>&rtype=<c:out value="${reportrule.rtype }"/>&bflag=0&levelFlag=<c:out value="${levelFlag }"/>"><c:out value="${rt.targetName }"/></a></li>
	        			</c:forEach>
     			 		</ul>
   					 </div>
  				</div>
  				<!--end optionBox1-->
  					<form action="reportrule.do?method=editTheReportRule" method="post" name="tijiaoForm" id="tijiaoForm">
  				<input type="hidden" name="repid" value="<c:out value="${reportrule.modelid}"/>" />
  				<input type="hidden" name="rtarid" id="rtarid" value="<c:out value="${reportrule.targetid}"/>" />
  				<input type="hidden" name="rulecode" value="<c:out value="${reportrule.rulecode}"/>"/>
  				<input type="hidden" name="targetN1" id="targetN"value="<c:out value="${reportrule.targetname}"/>"/>
  				<input type="hidden" name="cflag" id="cflag"value="2"/>
  				<input type="hidden" name="bflag" id="bflag"value="0"/>
  				<input type="hidden" name="levelFlag" id="levelFlag"value="<c:out value="${levelFlag}"/>"/>
				<div id="formwrapper">
				<label for="rcodee"><b><font style="color:#990000"><fmt:message key="view.zbmc"/><c:out value="${reportrule.targetname}"/></font></b>
				</label>
				<%-- <a href="javascript:openHelp()"><img border="0" src="ps/framework/images/iconInformation.gif" alt='<fmt:message key="view.help"/>' style="float:right;"></a> --%>
				
				</br></br>
				&nbsp;&nbsp;<fmt:message key="view.gzlx"/>
									<select name="rtype" onchange="getReportComRule(this.value)">
										<c:forEach items="${ruleType}" var="type"><c:if
										 test="${reportrule.rtype==type.pkid}"><option value="<c:out value="${type.pkid}"/>" selected><c:out value="${type.typename}"/></option></c:if>
										<c:if test="${reportrule.rtype!=type.pkid}"><option value="<c:out value="${type.pkid}"/>"><c:out value="${type.typename}"/></option></c:if></c:forEach>
									</select>&nbsp;&nbsp;
					</form>
			<form action="reportrule.do?method=saveReportRule" method="post" name="apLogin" id="apLogin">
  				<input type="hidden" name="repid" id="replid" value="<c:out value="${reportrule.modelid}"/>" />
  				<input type="hidden" name="rtarid" id="rtarid" value="<c:out value="${reportrule.targetid}"/>" />
  				<input type="hidden" name="targetN" id="targetN"value="<c:out value="${reportrule.targetname}"/>"/>
  				<input type="hidden" name="rtype1" id="rtype1"value="<c:out value="${reportrule.rtype}"/>"/>
  				<input type="hidden" name="defChar" id="defChar"value="<c:out value="${defChar}"/>"/>
  				<input type="hidden" name="bflag1" id="bflag1"value="0"/>
  				<input type="hidden" name="levelFlag" id="levelFlag" value="<c:out value="${levelFlag}"/>"/>
  				<input type="hidden" name="range" id="range" value="<c:out value="${range}"/>"/>
    				<fieldset id="fieldset1">
    					<legend><fmt:message key="view.gzsz"/></legend>
    				<div>
      					<label for="null0">
      					 <input type="button" id ="null3" name="rulenull" value='<fmt:message key="view.notkong"/>'  onclick="javascript:editrulenull('1');"/>
      					</label>
      					<label for="null1">
      					 <input type="button" id ="null4" name="rulenull" value='<fmt:message key="view.kong"/>' onclick="javascript:editrulenull('2');"/>
      					</label>
      					<br/>
    				</div>
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
	      					 	<fmt:message key="view.dyzb.key"/>
	      					 </td>
	      					  <td>
	      					  <input type="text"  name="keyid" id="keyid" value="<c:out value="${reportrule.keyid }"/>"   />
	      					 </td>
                        </tr>
                         <tr>
	      					 <td>
	      					 	<fmt:message key="view.mbrzb"/>
	      					 </td>
	      					  <td>
	      					    <input type="text"  name="targettable" id="targettable" value="<c:out value="${reportrule.targettable }" />"  style="width:250px;" />
	      					 </td>
                        </tr>
                       <%--  
                        <tr>
	      					 <td>
	      					 	<fmt:message key="view.glgx"/>1:
	      					 </td>
	      					  <td>
	      					   <input type="text"  name="key1" id="key1" value="<c:out value="${reportrule.key1 }" />"  style="width:250px;" />
	      					 </td>
                        </tr>
                         <tr>
	      					 <td>
	      					 	<fmt:message key="view.glgx"/>2:
	      					 </td>
	      					  <td>
	      					   <input type="text"  name="key2" id="key2" value="<c:out value="${reportrule.key2 }" />"  style="width:250px;" />
	      					 </td>
                        </tr>
                         <tr>
	      					 <td>
	      					 	<fmt:message key="view.glgx"/>3:
	      					 </td>
	      					  <td>
	      					   <input type="text"  name="key3" id="key3" value="<c:out value="${reportrule.key3 }" />"  style="width:250px;" />
	      					 </td>
                        </tr>
                        --%>
                         <tr>
	      					 <td>
	      					 	<fmt:message key="view.gzzt"/>
	      					 </td>
	      					  <td>
	      					   <input type="radio" id ="cstatus1" name="cstatus" value="0" <c:if test="${reportrule.cstatus == '0'}"> checked </c:if>  checked><fmt:message key="contrastrelation.start"/></input>
	      					   <input type="radio" id ="cstatus2" name="cstatus" value="1" <c:if test="${reportrule.cstatus == '1'}"> checked </c:if>  ><fmt:message key="contrastrelation.stop"/></input>
	      					 </td>
                        </tr>
    				  	<tr >
    				  		<td>
    				  				<fmt:message key="view.zdmc"/>
    				  		</td>
    				  		<td>
    				  			<select id="ziduan" onchange="setContentVal('ziduan')">
    				  			<c:forEach var="rt" items="${ll}" varStatus="index">
    				  			<option value='<c:out value="${rt.targetField }"/>'><c:out value="${rt.targetName }"/></option>
    				  			</c:forEach>
    				  			</select>	
    				  		</td>
    				  	</tr>
    				   <%-- 
    				  	<tr>
    				  		<td>
    				  				<fmt:message key="view.ys"/>
    				  		</td>
    				  		<td>
    				  				<input type="button" value="+" id="jia" onclick="setContentVal('jia')" />
    				  				<input type="button" value="-"  id="jian" onclick="setContentVal('jian')"/>
    				  				<input type="button" value="*"  id="cheng" onclick="setContentVal('cheng')"/>
    				  				<input type="button" value="/" id="chu" onclick="setContentVal('chu')"/>
    				  		</td>
    				  	</tr>
    				  	<tr>
    				  		<td>
    				  				<fmt:message key="view.bj"/>
    				  		</td>
    				  		<td>
    				  				<input type="button" value="=="  id="deng" onclick="setContentVal('deng')"/>
    				  				<input type="button" value=">" id="dayu" onclick="setContentVal('dayu')"/>
    				  				<input type="button" value=">=" id="dayudeng" onclick="setContentVal('dayudeng')"/>
    				  				<input type="button" value="&lt" id="xiaoyu" onclick="setContentVal('xiaoyu')"/>
    				  				<input type="button" value="<=" id="xiaoyudeng" onclick="setContentVal('xiaoyudeng')"/>
    				  				<input type="button" value="!=" id="budeng" onclick="setContentVal('budeng')"/>
    				  				<input type="button" value="like" id="like" onclick="setContentVal('like')"/>
    				  				<input type="button" value="in" id="in" onclick="setContentVal('in')"/>
    				  				
    				  		</td>
    				  	</tr>
    				  		<tr>
    				  		<td>
    				  				<fmt:message key="view.hs"/>
    				  		</td>
    				  		<td>
    				  				<input type="button" value=" SUM() "  id="sum" onclick="setContentVal('sum')"/>
    				  				<input type="button" value=" COUNT() " id="count" onclick="setContentVal('count')"/>
    				  				
    				  		</td>
    				  	</tr>
    				  	<tr>
				<td><fmt:message key="formuladefine.formula.reportname"/></td>
				<td >
				 <select id="replist" name="hhm" onchange="gettarget(this.value)">
					<c:forEach var="repl" items="${reportlist}" varStatus="index">
    				  	<option value='<c:out value="${repl.pkid }"/>'><c:out value="${repl.name }"/></option>
    				</c:forEach>
    			 </select>
				</td>
			</tr>
			<tr>
				<td> <fmt:message key="directorEnquiries.index"/></td>
				<td>
					<select id="ziduan1" onchange="setContentVal('ziduan1')">
					<c:forEach var="tarl" items="${targetsList}" varStatus="index">
    				  	<option value='<c:out value="${tarl.targetField }"/>'><c:out value="${tarl.targetName }"/></option>
    				</c:forEach>
    			 </select>
			
                             	</td>
                             </tr>
                      --%>
    				  	 <tr>
	      					 <td>
	      					 	<fmt:message key="view.gzjb"/>
	      					 </td>
	      					  <td>
	      					  <textarea rows="10" cols="65" name="content" id="content"><c:out value="${reportrule.content }"/></textarea>
	      					  <input type="button" value='<fmt:message key="view.cs"/>' onclick="testsql()" /> 
	      					 </td>
                        </tr>
                  <%--       <tr>
	      					 <td>
	      					 	<fmt:message key="view.gzsm"/>
	      					 </td>
	      					  <td>
	      					  <textarea rows="6" cols="65" name="contentdes" id="contentdes"><c:out value="${reportrule.contentdes }"/></textarea>
	      					 </td>
                        </tr>
                         <tr>
	      					 <td>
	      					 	<fmt:message key="view.cwts"/>
	      					 </td>
	      					  <td>
	      					  <textarea rows="6" cols="65" name="rulemsg" id="rulemsg"><c:out value="${reportrule.rulemsg }"/></textarea>
	      					 </td>
                        </tr>
                        --%>
                          <tr>
	      					 <td>
	      					 	<fmt:message key="view.xybs"/>
	      					 </td>
	      					  <td>
									<select name="pflag" id="pflag" onchange="getRpcheck(this.value)">
										<option value="1" <c:if test="${reportrule.pflag==1}"> selected </c:if> selected><fmt:message key="view.ymxy"/></option>
									   <%-- 
									    <option value="2" <c:if test="${reportrule.pflag==2}"> selected </c:if> ><fmt:message key="view.hryx"/></option>
										<option value="3" <c:if test="${reportrule.pflag==3}"> selected </c:if> ><fmt:message key="view.ssxy"/></option>
										--%>	
									</select>&nbsp;&nbsp;
	      					 </td>
                        </tr>
                       <tr id="rcontent" style="display:none;">
	      					 <td>
	      					 	<fmt:message key="view.ckzjb"/>
	      					 </td>
	      					  <td>
	      					  <textarea rows="10" cols="65" name="rcontent" ><c:out value="${reportrule.rcontent }"/></textarea>
	      					 </td>
                        </tr>
                         <tr id="pcheck" style="display:none;">
	      					 <td>
	      					 	<fmt:message key="view.ymjbxy"/>
	      					 </td>
	      					 <td>
	      				     <%--
	      				      <input type="checkbox" name="pcheck" id="pchecks1" value="checkNull" ><fmt:message key="view.notkong"/> &nbsp;&nbsp;
	      					  <input type="checkbox" name="pcheck" id="pchecks2" value="checkDengYu" ><fmt:message key="equal"/>&nbsp;&nbsp;
	      					  <input type="checkbox" name="pcheck" id="pchecks3" value="checkXiaoDeng" ><fmt:message key="view.xydy"/>
	      					  --%>
	      					  <table>
			    					<tr>
				    					<c:if test="${enou==1}">
				    					    <td align="center">
				    					        <fmt:message key="button.edit"/>
				    				  		</td>
				    					</c:if>
			    				  		<td align="center">
			    				  				<fmt:message key="view.sgzmc"/>
			    				  		</td>
			    				  		<td align="center">
			    				  				<fmt:message key="view.shsmc"/>
			    				  		</td>
			    				  		<td align="center">
			    				  				<fmt:message key="view.shscs"/>
			    				  		</td>
			    				  		<td align="center">
			    				  				<fmt:message key="view.stsxx"/>
			    				  		</td>
			    				  		<c:if test="${enou==1}">
				    					    <td align="center">
				    					        <fmt:message key="docshare.operate"/>
				    				  		</td>
				    					</c:if>
			    				  	</tr>
			    				  	<c:if test="${enit==2}">
				    				  	<tr>
				    				  	   <c:if test="${enou==1}">
				    				  	     <td></td>
				    				  	   </c:if>  
				    				  		<td>
				    				  				<input type="text" name="gzmc" value="<c:out value="${rulesname}"/>" />
				    				  		</td>
				    				  		<td >
				    				  			<%--	<input type="text" name="hsmc" value="<c:out value="${funname}"/>"  /> --%>
				    				  				<select  name="hsmc" style="width: 99px;">
				    				  				    <option value=""><fmt:message key="place.select"/></option>
														<option value="notnull"  <c:if test="${funname == 'notnull'}">selected = "selected" </c:if>>NotNull</option>
														<option value="reg" <c:if test="${funname == 'reg'}">selected = "selected" </c:if>>Reg</option>
												    </select>
				    				  		</td>
				    				  		<td>
				    				  		       
				    				  				<input type="text" name="hscs" value="<c:out value="${funparam}"/>"  />
				    				  		</td>
				    				  		<td>
				    				  				<input type="text" name="tsxx" value="<c:out value="${prompt}"/>"  />
				    				  		</td>
				    				  		<c:if test="${enou==1}">
				    				  	     <td><fmt:message key="onlineHelpFile.add"/></td>
				    				  	    </c:if>  
				    				  	</tr>
			    				  	</c:if>
			    				  	<c:if test="${enou==1}">
			    				  	 <c:forEach var="fv" items="${fromVlidatList}" varStatus="fvs">
			    				  	   
			    				  	  <tr>
			    				  	        <td>
			    				  	      		 <input type="checkbox" name="pkidstr" id="pkidstr" value="<c:out value="${fv.pkid}"/>"  alt='<fmt:message key="select.alt.checked"/>'>
			    				  	      		 <input type="hidden"   name="pkidobj" id="pkidval" value="" />
			    				  	        </td>
			    				  	    
				    				  		<td>
				    				  				<input type="text" name="<c:out value="${fv.pkid}"/>_gzmc"  id="<c:out value="${fv.pkid}"/>_gzmc"  value="<c:out value="${fv.rulesname}"/>" />
				    				  		</td>
				    				  		<td >
				    				  			<%-- 	<input type="text" name="<c:out value="${fv.pkid}"/>_hsmc"  id="<c:out value="${fv.pkid}"/>_hsmc"  value="<c:out value="${fv.funname}"/>"  /> --%>
				    				  				<select  name="<c:out value="${fv.pkid}"/>_hsmc"  id="<c:out value="${fv.pkid}"/>_hsmc"  style="width: 99px;">
														<option value="notnull"  <c:if test="${fv.funname == 'notnull'}">selected = "selected" </c:if>>NotNull</option>
														<option value="reg" <c:if test="${fv.funname == 'reg'}"> selected = "selected" </c:if>>Reg</option>
												    </select>
				    				  		</td>
				    				  		<td>
				    				  				<input type="text" name="<c:out value="${fv.pkid}"/>_hscs"  id="<c:out value="${fv.pkid}"/>_hscs"  value="<c:out value="${fv.funparam}"/>"  />
				    				  		</td>
				    				  		<td>
				    				  				<input type="text" name="<c:out value="${fv.pkid}"/>_tsxx"  id="<c:out value="${fv.pkid}"/>_tsxx"  value="<c:out value="${fv.prompt}"/>"  />
				    				  		</td>
				    				  		<td>
				    				  				<a href="#" style="color: #2259D7;" onclick="delfv(this,'<c:out value="${fv.pkid}" />'); "><fmt:message key="button.delete" /></a> 
				    				  		</td>
				    				  	</tr>
			    				  	   </c:forEach>
			    				  	</c:if>
			    				  	
    				   			</table>
	      					 </td>
                        </tr>
    				</table>
    				
    				</fieldset>
    				
 					 <br/>
 					 <label for="but" style="margin-left:250px;"><input type="button" value='<fmt:message key="button.ratifypass"/>' onclick="testResult()" style="width:80;" ></label>
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
<script type="text/javascript">
$(function(){
	if($("#pflag").val()==1){
		$("#pcheck").show();
		$("#rcontent").hide();
	}else if($("#pflag").val()==2){
		$("#pcheck").show();
		$("#rcontent").show();
	}else if($("#pflag").val()==3){
		$("#pcheck").hide();
		$("#rcontent").hide();
	}
	var lxhcheck='<c:out value="${reportrule.pcheck }"/>';
	if(lxhcheck.indexOf("checkNull")>=0){
		$("#pchecks1").attr("checked","checked");
	}
	if(lxhcheck.indexOf("checkDengYu")>=0){
		$("#pchecks2").attr("checked","checked");
	}
	if(lxhcheck.indexOf("checkXiaoDeng")>=0){
		$("#pchecks3").attr("checked","checked");
	}
});
function getRpcheck(val){
	if(val==1){
		$("#pcheck").show();
		$("#rcontent").hide();
	}else if(val==2){
		$("#pcheck").show();
		$("#rcontent").show();
	}else{
		$("#pcheck").hide();
		$("#rcontent").hide();
	}
}
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
				$("#ceshi").val("1");
				alert('<fmt:message key="view.cg"/>');
			}else{
				$("#ceshi").val("2");
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
		    mm=mm+"( select          from "+bb[0]+" r1 where )";
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
function testResult(){
	ischeck();
	var content=$("#content").val();
	var params={"content": content,"method":"testSql"};
	$.ajax({ 
		type: "POST", 
		async: false, //ajax
		url: "reportrule.do", 
		data: jQuery.param(params), 
		success: function(result){
			if('true'==result){
				var df=document.apLogin;
				df.action='<c:out value="${hostPrefix}" /><c:url value="/reportrule.do" />?method=saveReportRule';
				df.submit();
			}else{
				alert('<fmt:message key="view.gzjbyw"/>');
			}
		}
	});
}

	function editrulenull(val){
		    var replid=$("#replid").val();
			var keyid=$("#keyid").val();
			var targettable=$("#targettable").val();
			var cc=$("#defChar").val()
			var rtarid=$("#rtarid").val();
			var rtaname=$("#targetN").val();
			var aa="";
		if(val == '1') {
			$("#content").val("");
			//if(trim(rtarid)>=21 && trim(rtarid) < 50){
			aa="select r.ORGAN_ID, r."+keyid+",to_char(r."+rtarid+"),null as a,null as b,null as c from "+cc+" r  where    r."+rtarid+" is  null or  r."+rtarid+" = '' or r."+rtarid+" = 'null'";
			//}else{
			//aa="select r.ORGAN_ID, r."+keyid+",r.ITEMVALUE"+rtarid+" ,null as a,null as b,null as c from  "+cc+" r  where    r.REPORT_ID="+replid+"  and ( r.ITEMVALUE"+rtarid+" is  null or  r.ITEMVALUE"+rtarid+" = '' or r.ITEMVALUE"+rtarid+" = 'null')";
			//}
			$("#qita").hide();
			$("#content").val(aa);
			var con='<fmt:message key="symbol.bracket.left"/>'+rtaname+'<fmt:message key="symbol.bracket.right"/><fmt:message key="common.js.alert.notnull"/>';
			var rulem='<fmt:message key="symbol.bracket.left"/>'+rtaname+'<fmt:message key="symbol.bracket.right"/><fmt:message key="common.js.alert.notnull"/>';
			$("#contentdes").val(con);
			$("#rulemsg").val(rulem);
			$("#pchecks1").attr("checked","checked")
		} else if(val == '2'){
			$("#content").val("");
			//if(rtarid>=21 && rtarid<50){
				aa="select r.ORGAN_ID, r."+keyid+",to_char(r."+rtarid+") ,null as a,null as b,null as c from  "+cc+" r  where     r."+rtarid+" is not null or r."+rtarid+" !='' ";
				//}else{
				//	aa="select r.ORGAN_ID,r."+keyid+",r.ITEMVALUE"+rtarid+"  ,null as a,null as b,null as c from  "+cc+"  r  where   r.REPORT_ID="+replid+"  and ( r.ITEMVALUE"+rtarid+" is not null or r.ITEMVALUE"+rtarid+" !='') ";
			//	}
			$("#content").val(aa);
			var con='<fmt:message key="symbol.bracket.left"/>'+rtaname+'<fmt:message key="symbol.bracket.right"/><fmt:message key="view.bwk"/>' ;
			var rulem='<fmt:message key="symbol.bracket.left"/>'+rtaname+'<fmt:message key="symbol.bracket.right"/><fmt:message key="view.bwk"/>';
			$("#contentdes").val(con);
			$("#rulemsg").val(rulem);
			$("#qita").hide();
		}

	}
	function getReportComRule(val){
		$("#rtype1").val(val);
		var df=document.tijiaoForm;
		df.submit();
	}
	function openHelp() { 
		window.open ("<c:out value="${hostPrefix}" />/ps/model/reportrule/basichelp.jsp", "newwindow", "height=600, width=600, toolbar=no,menubar=no, scrollbars=yes, resizable=no, location=no, status=no") ;
		} 

	function ischeck(){
		 var arr = document.getElementsByName('pkidstr');
		 var arobj="" ;
		 var arrayVal="" ;
		 for(var i=0;i<arr.length;i++){
			 if( arr[i].checked ){
				 	arobj+=arr[i].value+":";
			 }
		 }
		 var arrayObj=arobj.split(":");
		 for(var i=0;i<arrayObj.length;i++){
			    arrayVal+=arrayObj[i]+","+$("#"+arrayObj[i]+"_gzmc").val()+","+$("#"+arrayObj[i]+"_hsmc").val()+","+$("#"+arrayObj[i]+"_hscs").val()+","+$("#"+arrayObj[i]+"_tsxx").val();
			   if(i<=arrayObj.length-2){
			     arrayVal+="@" ;
	      	   }
		 }
		 $("#pkidval").val(arrayVal);
	}
	function delfv(obj,fvpkid){
		if(!confirm('<fmt:message key="view.qrsc" />')){
			return;
		}
		var params={"fvpkid":fvpkid ,"method":"delFromValidator"};
		$.ajax({ 
			type: "POST", 
			async: false, //ajax
			url: "reportrule.do", 
			data: jQuery.param(params), 
			success: function(result){
				if(result==1){
					var tr=obj.parentNode.parentNode;
					var tbody=tr.parentNode;
					tbody.removeChild(tr);
				    alert('<fmt:message key="view.sjsccg" />');
				}else if(result==0){
				    alert('<fmt:message key="view.sjscsb" />');
				}
			}
		});
	}
</script>
