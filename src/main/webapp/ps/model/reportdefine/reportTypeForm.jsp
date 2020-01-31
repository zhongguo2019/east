<!-- reportTypeForm -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/meta.jsp" %>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
        <title><fmt:message key="webapp.prefix"/></title>
        
 
<script type="text/javascript">
window.onload=function(){  
	 
    document.getElementsByTagName("body")[0].onkeydown =function(){  
          
        //è·åäºä»¶å¯¹è±¡  
        var elem = event.relatedTarget || event.srcElement || event.target ||event.currentTarget;   
          
        if(event.keyCode==8){//å¤æ­æé®ä¸ºbackSpaceé®  
          
                //è·åæé®æä¸æ¶åæ åæåçelement  
                var elem = event.srcElement || event.currentTarget;   
                  
                //å¤æ­æ¯å¦éè¦é»æ­¢æä¸é®ççäºä»¶é»è®¤ä¼ é  
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
function save(){
	var bCancel = false;
	
	if(bCancel==true){
	return true;
	}
	if(document.getElementById("aaa").value==''){
	alert('<fmt:message key="js.validate.typename"/>')
	return false;
	}
	return true;
	
	 <c:set var="auditCollect">
		<%= FuncConfig.getProperty("audit.collect","no")%>
	</c:set>
	document.reportTypeForm.action="reportTypeAction.do?method=save";
	document.reportTypeForm.submit();
}

</script>
 <script type="text/javascript">
	        //window.parent.document.getElementById("ppppp").value= "<fmt:message key="navigation.reportmanage.reporttype"/>";  
</script> 
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%" valign="top">
    <br>
    <br>


			<html:form action="reportTypeAction" method="post" onsubmit="return validateForm(this)">
			<table cellSpacing=1 cellPadding=0 width="380" align=center border=0 >
			<html:hidden property="pkid"/>
			<c:set var="pageButtons">
			    <tr align="center" class="BtnBgColor">
			    
			       <td  width=80 align="right"></td>
			    	<td   align="left">
			    	<a href="#" class="easyui-linkbutton"  style="width:80;height: 20;text-decoration:none;" data-options="iconCls:'icon-add'" onclick="save(this);"  ><fmt:message key='button.confirm'/></a>
			    	<a href="#" class="easyui-linkbutton"  style="width:80;height: 20;" iconCls="icon-cancel" onclick="history.back()" ><fmt:message key='button.cancel'/></a>
			           <!--  <html:submit  property="method.save"  style="width:60;" onclick="bCancel=false">
			            	  <fmt:message key="button.confirm"/>
			            </html:submit>
			            <html:cancel  onclick="bCancel=true" style="width:60;">
			                <fmt:message key="button.cancel"/>
			            </html:cancel> -->
			        </td>
			    </tr>
			</c:set>
			
				
				
			    <tr>
			        <!--5--><td  width=80 align="right">
			            <fmt:message key="reportdefine.reportType.name"/>
			        </td>
			        <td ><!-- 2 -->
			        	<html:text maxlength="20" styleId="aaa"  property="name" style="width:240;"/>
			            
			        </td>
			    </tr>
			    
			    <tr>
			        <!--5--><td  width=80 align="right">
			            <fmt:message key="reportdefine.reportType.systemCode"/>
			        </td>
			        <td ><!-- 2 -->
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
			        <!--5--><td  width=80 align="right">
			        	<fmt:message key="reportdefine.reportType.dataSource"/>
			        </td>
			        <td ><!-- 2 -->
			             <html:select property="dataSource" size="1" style="width:240;"> 
	        				<html:option value="1"><fmt:message key="reportdefine.reportType.dataSource.caiji"/></html:option>
	        				<html:option value="2"><fmt:message key="reportdefine.reportType.dataSource.shengcheng"/></html:option>
	        				<html:option value="3"><fmt:message key="reportdefine.reportType.dataSource.luru"/></html:option>
	        			 </html:select>
			        </td>
			    </tr>

			    <tr>
			        <!--5--><td  width=80 align="right">
			        	<fmt:message key="reportdefine.reportType.isBalance"/>
			        </td>
			        <td ><!-- 2 -->
			            <html:select property="isBalance" size="1" style="width:240;"> 
	        				<html:option value="1"><fmt:message key="select.yes"/></html:option>
	        				<html:option value="0"><fmt:message key="select.no"/></html:option>
	        			</html:select>
			            
			        </td>
			    </tr>
			    <tr>
			        <td  width=80 align="right">
			        	<fmt:message key="reportdefine.reportType.isShowRep"/>
			        </td>
			        <td >
			            <html:select property="isShowRep" size="1" style="width:240;"> 
	        				<html:option value="1"><fmt:message key="select.yes"/></html:option>
	        				<html:option value="0"><fmt:message key="select.no"/></html:option>
	        			</html:select>
			            
			        </td>
			    </tr>
			    <tr>
			        <!--5--><td  width=80 align="right">
			        	<fmt:message key="reportdefine.reportType.isCollect"/>
			        </td>
			        <td ><!-- 2 -->
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
			        <!--5--><td  width=80 align="right">
			        	<fmt:message key="reportdefine.reportType.isInput"/>
			        </td>
			        <td ><!-- 2 -->
			            <html:select property="isInput" size="1" style="width:240;"> 
	        				<html:option value="1"><fmt:message key="select.yes"/></html:option>
	        				<html:option value="0"><fmt:message key="select.no"/></html:option>
	        			</html:select>
			        </td>
			    </tr>
			     <tr>
			        <!--5--><td  width=80 align="right">
			        	<fmt:message key="reportdefine.reportType.isUpdate"/>
			        </td>
			        <td><!-- 2 -->
			            <html:select property="isUpdate" size="1" style="width:240;"> 
			                <html:option value="1"><fmt:message key="select.yes"/></html:option>
	        		 		<html:option value="0"><fmt:message key="select.no"/></html:option>
	        			</html:select>
			        </td>
			    </tr>
			    <tr>
			        <!--5--><td  width=80 align="right">
			        	<fmt:message key="reportdefine.reportType.upReport"/>
			        </td>
			        <td ><!-- 2 -->
			            <html:select property="upReport" size="1" style="width:240;">
	        				<html:option value="1"><fmt:message key="select.yes"/></html:option>
	        				<html:option value="0"><fmt:message key="select.no"/></html:option>
	        			</html:select>
			        </td>
			    </tr>
			    <tr>
			        <!--5--><td  width=80 align="right">
			        	<fmt:message key="reportdefine.reportType.showLevel"/>
			        </td>
			        <td ><!-- 2 -->
			         <html:select property="showlevel" size="1" style="width:240;">
				        <c:forEach var="test" items="${showlevel}">
				        		<html-el:option value="${test.description }"><c:out value="${test.dicname}"></c:out> </html-el:option>
				        </c:forEach>
				      </html:select>	
			        </td>
			    </tr>
			    
			    <c:out value="${pageButtons}" escapeXml="false" />
			    <tr>
					<td  colspan="2" height="17"></td>
				</tr>
			</table>
			
			</html:form>
