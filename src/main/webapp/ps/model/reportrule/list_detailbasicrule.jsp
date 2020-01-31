<!-- /ps/model/reportrule/list_detailbasicrule. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>

<html>
<head>
<title></title>
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" />
<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>

<script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script>
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value='/ps/scripts/reportrule/edit_reportrule.js'/>"></script>
 

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
   
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
  		
 		
  		 <tr>
    		 <td width="100%" valign="top" align="center">
    			<br>
   				<br>
   				<div id="formwrapper">
  				<!--end optionBox1-->
    				<fieldset id="fieldset1">
    					<legend>
	      					 	<fmt:message key="guizexiangxixinxi"/>
    					</legend>
    				<br><br>
    				<table>
    				<tr>
    				  		<td style="width:100px;text-align:right;">
    				  				<strong>
	      					 <fmt:message key="moxingmingcheng"/>	
    				  				</strong><br><br>
    				  		</td>
    				  		<td>
    				  				<c:out value="${rename}"/><br><br>
    				  		</td>
    				  		
    				  	</tr>
    				  	<tr>
    				  		<td style="width:100px;text-align:right;">
    				  				<strong>
	      					 <fmt:message key="guize"/>	
    				  				</strong><br><br>
    				  		</td>
    				  		<td>
    				  				<c:out value="${typename}"/><br><br>
    				  		</td>
    				  		
    				  	</tr>
    				<tr>
    				  		<td style="width:100px;text-align:right;">
    				  				<strong>
	      					 	<fmt:message key="zhibiaomingcheng"/>
    				  				</strong><br><br>
    				  		</td>
    				  		<td>
    				  				<c:out value="${reportrule.targetname}"/><br><br>
    				  		</td>
    				  		
    				  	</tr>
    					<tr>
    				  		<td style="width:100px;text-align:right;">
    				  				<strong>
	      					 <fmt:message key="view.gzbm"/>	
    				  				</strong><br><br>
    				  		</td>
    				  		<td>
    				  				<c:out value="${reportrule.rulecode}"/><br><br>
    				  		</td>
    				  		
    				  	</tr>
    				  	<tr>
	      					 <td style="width:100px;text-align:right;" nowrap>
	      					 	<strong><fmt:message key="zhibiaoduiyinglie"/></strong><br><br>
	      					 </td>
	      					  <td>
	      					  <c:out value="${reportrule.keyid }"/><br><br>
	      					 </td>
                        </tr>
                         <tr>
	      					 <td style="width:100px;text-align:right;">
	      					 	<strong><fmt:message key="view.mbrzb"/></strong><br><br>
	      					 </td>
	      					  <td>
	      					    <c:out value="${reportrule.targettable }" /><br><br>
	      					 </td>
                        </tr>
                         <tr>
	      					 <td style="width:100px;text-align:right;">
	      					 	<strong>
	      					 	<fmt:message key="view.gzzt"/>
	      					 	</strong><br><br>
	      					 </td>
	      					  <td>
	      					    <c:if test="${reportrule.cstatus == '0'}"> <fmt:message key="contrastrelation.start"/></c:if>  
	      					    <c:if test="${reportrule.cstatus == '1'}"> <fmt:message key="contrastrelation.stop"/> </c:if><br><br>
	      					 </td>
                        </tr>
                        	
    				  	 <tr>
	      					 <td style="width:100px;text-align:right;">
	      					 	<strong>
	      					 	<fmt:message key="view.gzjb"/>
	      					 	</strong><br><br>
	      					 </td>
	      					  <td>
	      					  <c:out value="${reportrule.content }"/> <br><br>
	      					 </td>
                        </tr>
                         <tr>
	      					 <td style="width:100px;text-align:right;">
	      					 	<strong>
	      					 <fmt:message key="view.gzsm"/>	
	      					 	</strong><br><br>
	      					 </td>
	      					  <td>
	      					  <c:out value="${reportrule.contentdes }"/><br><br>
	      					 </td>
                        </tr>
                         <tr>
	      					 <td style="width:100px;text-align:right;">
	      					 	<strong>
	      					 	<fmt:message key="view.cwtss"/>
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
