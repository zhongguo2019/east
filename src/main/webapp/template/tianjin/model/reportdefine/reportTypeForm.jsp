<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/meta.jsp" %>
<%@ page import="com.krm.ps.util.FuncConfig"%>
        <title><fmt:message key="webapp.prefix"/></title>
        
        <LINK href="${cssPath}/common.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/helptip.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='${cssPath}/print.css'/>" /> 
        <script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script> 
        <script type="text/javascript" src="<c:url value='/ps/scripts/effects.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/ps/scripts/helptip.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>  
<script type="text/javascript">
window.onload=function(){  
	 
    document.getElementsByTagName("body")[0].onkeydown =function(){  
          
        //获取事件对象  
        var elem = event.relatedTarget || event.srcElement || event.target ||event.currentTarget;   
          
        if(event.keyCode==8){//判断按键为backSpace键  
          
                //获取按键按下时光标做指向的element  
                var elem = event.srcElement || event.currentTarget;   
                  
                //判断是否需要阻止按下键盘的事件默认传递  
                var name = elem.nodeName;  
                  
                if(name!='INPUT' && name!='TEXTAREA'){  
                    return _stopIt(event);  
                }  
                var type_e = elem.type.toUpperCase();  
                if(name=='INPUT' && (type_e!='TEXT' && type_e!='TEXTAREA' && type_e!='PASSWORD' && type_e!='FILE')){  
                        return _stopIt(event);  
                }  
                if(name=='INPUT' && (elem.readOnly==true || elem.disabled ==true)){  
                        return _stopIt(event);  
                }  
            }  
        }  
    
    }  
function _stopIt(e){  
        if(e.returnValue){  
            e.returnValue = false ;  
        }  
        if(e.preventDefault ){  
            e.preventDefault();  
        }                 
  
        return false;   
}  
</script>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" >

<script>

var bCancel = false
function validateForm(f){
if(bCancel==true){
return true
}
if(f.name.value==''){
alert('<fmt:message key="js.validate.typename"/>')
return false
}
return true
}
 <c:set var="auditCollect">
	<%= FuncConfig.getProperty("audit.collect","no")%>
</c:set>
</script>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
   <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
				<p style="margin-top: 3"><font class=b><b><fmt:message
				 key="navigation.reportmanage.reporttype"/></b></font></p>
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
    <br>
    <br>


			<html:form action="reportTypeAction" method="post" onsubmit="return validateForm(this)">
			<table cellSpacing=1 cellPadding=0 width="380" align=center border=0 class="TableBGColor">
			<html:hidden property="pkid"/>
			<c:set var="pageButtons">
			    <tr align="center" class="BtnBgColor">
			    
			       <td class="TdBGColor1" width=80 align="right"></td>
			    	<td class="buttonBar"  align="left">
			            <html:submit  property="method.save"  style="width:60;" onclick="bCancel=false">
			            	  <fmt:message key="button.confirm"/>
			            </html:submit>
			            <html:cancel  onclick="bCancel=true" style="width:60;">
			                <fmt:message key="button.cancel"/>
			            </html:cancel>
			        </td>
			    </tr>
			</c:set>
			
				
				
			    <tr>
			        <!--5--><td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.reportType.name"/>
			        </td>
			        <td class="TdBGColor2"><!-- 2 -->
			        	<html:text maxlength="20"   property="name" style="width:240;"/>
			            
			        </td>
			    </tr>
			    
			    <tr>
			        <!--5--><td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.reportType.systemCode"/>
			        </td>
			        <td class="TdBGColor2"><!-- 2 -->
			        	<html:select property="systemCode" size="1" style="width:240;">
			        		<c:forEach var="systemCode" items="${systemCodes}">
        						<html-el:option value="${systemCode.pkid}">
       								<c:out value="${systemCode.name}"/>
								</html-el:option>
        					</c:forEach>
			            </html:select>
			            
			        </td>
			    </tr>

			    <tr>
			        <!--5--><td class="TdBGColor1" width=80 align="right">
			        	<fmt:message key="reportdefine.reportType.dataSource"/>
			        </td>
			        <td class="TdBGColor2"><!-- 2 -->
			             <html:select property="dataSource" size="1" style="width:240;"> 
	        				<html:option value="1"><fmt:message key="reportdefine.reportType.dataSource.caiji"/></html:option>
	        				<html:option value="2"><fmt:message key="reportdefine.reportType.dataSource.shengcheng"/></html:option>
	        				<html:option value="3"><fmt:message key="reportdefine.reportType.dataSource.luru"/></html:option>
	        			 </html:select>
			        </td>
			    </tr>

			    <tr>
			        <!--5--><td class="TdBGColor1" width=80 align="right">
			        	<fmt:message key="reportdefine.reportType.isBalance"/>
			        </td>
			        <td class="TdBGColor2"><!-- 2 -->
			            <html:select property="isBalance" size="1" style="width:240;"> 
	        				<html:option value="1"><fmt:message key="select.yes"/></html:option>
	        				<html:option value="0"><fmt:message key="select.no"/></html:option>
	        			</html:select>
			            
			        </td>
			    </tr>
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			        	<fmt:message key="reportdefine.reportType.isShowRep"/>
			        </td>
			        <td class="TdBGColor2">
			            <html:select property="isShowRep" size="1" style="width:240;"> 
	        				<html:option value="1"><fmt:message key="select.yes"/></html:option>
	        				<html:option value="0"><fmt:message key="select.no"/></html:option>
	        			</html:select>
			            
			        </td>
			    </tr>
			    <tr>
			        <!--5--><td class="TdBGColor1" width=80 align="right">
			        	<fmt:message key="reportdefine.reportType.isCollect"/>
			        </td>
			        <td class="TdBGColor2"><!-- 2 -->
			            <html:select property="isCollect" size="1" style="width:240;"> 
	        				<c:if test="${auditCollect =='yes' }">
	        				<html:option value="1"><fmt:message key="common.collect"/></html:option>
	        				<html:option value="2"><fmt:message key="audit.collect"/></html:option>
	        				</c:if>
	        				<c:if test="${auditCollect =='no'}">
	        				<html:option value="1"><fmt:message key="select.yes"/></html:option>
	        				</c:if>
	        				<html:option value="0"><fmt:message key="select.no"/></html:option>
	        			</html:select>
			        </td>
			    </tr>
			     <tr>
			        <!--5--><td class="TdBGColor1" width=80 align="right">
			        	<fmt:message key="reportdefine.reportType.isInput"/>
			        </td>
			        <td class="TdBGColor2"><!-- 2 -->
			            <html:select property="isInput" size="1" style="width:240;"> 
	        				<html:option value="1"><fmt:message key="select.yes"/></html:option>
	        				<html:option value="0"><fmt:message key="select.no"/></html:option>
	        			</html:select>
			        </td>
			    </tr>
			     <tr>
			        <!--5--><td class="TdBGColor1" width=80 align="right">
			        	<fmt:message key="reportdefine.reportType.isUpdate"/>
			        </td>
			        <td class="TdBGColor2"><!-- 2 -->
			            <html:select property="isUpdate" size="1" style="width:240;"> 
			                <html:option value="1"><fmt:message key="select.yes"/></html:option>
	        		 		<html:option value="0"><fmt:message key="select.no"/></html:option>
	        			</html:select>
			        </td>
			    </tr>
			    <tr>
			        <!--5--><td class="TdBGColor1" width=80 align="right">
			        	<fmt:message key="reportdefine.reportType.upReport"/>
			        </td>
			        <td class="TdBGColor2"><!-- 2 -->
			            <html:select property="upReport" size="1" style="width:240;">
	        				<html:option value="1"><fmt:message key="select.yes"/></html:option>
	        				<html:option value="0"><fmt:message key="select.no"/></html:option>
	        			</html:select>
			        </td>
			    </tr>
			    <tr>
			        <!--5--><td class="TdBGColor1" width=80 align="right">
			        	<fmt:message key="reportdefine.reportType.showLevel"/>
			        </td>
			        <td class="TdBGColor2"><!-- 2 -->
			         <html:select property="showlevel" size="1" style="width:240;">
				        <c:forEach var="test" items="${showlevel}">
				        		<html-el:option value="${test.description }"><c:out value="${test.dicname}"></c:out> </html-el:option>
				        </c:forEach>
				      </html:select>	
			        </td>
			    </tr>
			    
			    <c:out value="${pageButtons}" escapeXml="false" />
			    <tr>
					<td class="FormBottom" colspan="2" height="17"></td>
				</tr>
			</table>
			
			</html:form>
