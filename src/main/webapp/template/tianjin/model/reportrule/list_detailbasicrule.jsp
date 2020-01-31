<%@ page pageEncoding="UTF-8" %>
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
       //屏蔽页面中不可编辑的文本框中的backspace按钮事件  
       function keydown(e) {  
           var isie = (document.all) ? true : false;  
           var key;  
           var ev;  
           if (isie){ //IE和谷歌浏览器  
               key = window.event.keyCode;  
               ev = window.event;  
           } else {//火狐浏览器  
               key = e.which;  
               ev = e;  
           }  
  
           if (key == 8) {//IE和谷歌浏览器  
               if (isie) {  
                   if (document.activeElement.readOnly == undefined || document.activeElement.readOnly == true) {  
                       return event.returnValue = false;  
                   }   
               } else {//火狐浏览器  
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
          				<td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" width="33" height="16"></td>
          				<td><p style="margin-top: 3"><font class=b><b>业务指标规则配置 >> 指标逻辑规则配置 >> 查看详细信息</b></font></p> </td>
		  				<td>
		  				</td> 
        		   </tr>
      			</table>
   			 </td>
 		 </tr>
 		 <tr>
    		<td width="100%" bgcolor="#EEEEEE"  height="36">
      			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!-- <a href="javascript:history.back();" style="color:#990000;un"><strong>返回</strong></a> -->
   			<c:if test="${bflag==0}">
   				<a href="reportrule.do?method=listBasicRule&levelFlag=1" style="color:#990000;un"><strong>返回</strong></a>
   			</c:if>
   			<c:if test="${bflag==1}">
   				<a href="reportrule.do?method=listLogicRule&levelFlag=2" style="color:#990000;un"><strong>返回</strong></a>
   			</c:if>
   			<c:if test="${bflag==2}">
   				<a href="javascript:history.back();" style="color:#990000;un"><strong>返回</strong></a>
   			</c:if>
   			 </td>
 		 </tr>
  		 <tr>
    		 <td width="100%" bgcolor="#EEEEEE" valign="top" align="center">
    			<br>
   				<br>
   				<div id="formwrapper">
  				<!--end optionBox1-->
    				<fieldset id="fieldset1">
    					<legend>
	      					 	规则详细信息
    					</legend>
    				<br><br>
    				<table>
    				<tr>
    				  		<td style="width:100px;text-align:right;">
    				  				<strong>
	      					 	模型名称：
    				  				</strong><br><br>
    				  		</td>
    				  		<td>
    				  				<c:out value="${rename}"/><br><br>
    				  		</td>
    				  		
    				  	</tr>
    				  	<tr>
    				  		<td style="width:100px;text-align:right;">
    				  				<strong>
	      					 	规则类型：
    				  				</strong><br><br>
    				  		</td>
    				  		<td>
    				  				<c:out value="${typename}"/><br><br>
    				  		</td>
    				  		
    				  	</tr>
    				<tr>
    				  		<td style="width:100px;text-align:right;">
    				  				<strong>
	      					 	指标名称：
    				  				</strong><br><br>
    				  		</td>
    				  		<td>
    				  				<c:out value="${reportrule.targetname}"/><br><br>
    				  		</td>
    				  		
    				  	</tr>
    					<tr>
    				  		<td style="width:100px;text-align:right;">
    				  				<strong>
	      					 	规则编码：
    				  				</strong><br><br>
    				  		</td>
    				  		<td>
    				  				<c:out value="${reportrule.rulecode}"/><br><br>
    				  		</td>
    				  		
    				  	</tr>
    				  	<tr>
	      					 <td style="width:100px;text-align:right;">
	      					 	<strong>指标对应key列：</strong><br><br>
	      					 </td>
	      					  <td>
	      					  <c:out value="${reportrule.keyid }"/><br><br>
	      					 </td>
                        </tr>
                         <tr>
	      					 <td style="width:100px;text-align:right;">
	      					 	<strong>目标日志表：</strong><br><br>
	      					 </td>
	      					  <td>
	      					    <c:out value="${reportrule.targettable }" /><br><br>
	      					 </td>
                        </tr>
                         <tr>
	      					 <td style="width:100px;text-align:right;">
	      					 	<strong>
	      					 	规则状态：
	      					 	</strong><br><br>
	      					 </td>
	      					  <td>
	      					    <c:if test="${reportrule.cstatus == '0'}"> 启用 </c:if>  
	      					    <c:if test="${reportrule.cstatus == '1'}"> 停用 </c:if><br><br>
	      					 </td>
                        </tr>
                        	
    				  	 <tr>
	      					 <td style="width:100px;text-align:right;">
	      					 	<strong>
	      					 	规则脚本：
	      					 	</strong><br><br>
	      					 </td>
	      					  <td>
	      					  <c:out value="${reportrule.content }"/> <br><br>
	      					 </td>
                        </tr>
                         <tr>
	      					 <td style="width:100px;text-align:right;">
	      					 	<strong>
	      					 	规则说明：
	      					 	</strong><br><br>
	      					 </td>
	      					  <td>
	      					  <c:out value="${reportrule.contentdes }"/><br><br>
	      					 </td>
                        </tr>
                         <tr>
	      					 <td style="width:100px;text-align:right;">
	      					 	<strong>
	      					 	错误提示：
	      					 	</strong><br><br>
	      					 </td>
	      					  <td>
	      					  <c:out value="${reportrule.rulemsg }"/><br><br>
	      					 </td>
                        </tr>
    				</table>
    				
    				</fieldset>
				</div>
			   <div id="users" style="display:none"></div>	
			</td>
		</tr>
	</table>
</body>
</html>
