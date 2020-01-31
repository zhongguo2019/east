<!-- /ps/model/reportrule/edit_privaterule.jsp -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>




<html>
<head>
<title></title>

<link rel="stylesheet" href="<c:url value='/ps/uitl/jmenu/css/styles.css'/>" type="text/css" />
<link rel="stylesheet" href="<c:url value='/ps/uitl/jmune/css/jquery-tool.css'/>" type="text/css" />
<script type="text/javascript"
	src="<c:url value='/ps/scripts/reportrule/edit_reportrule.js'/>"></script>

<script type="text/javascript">
	
	/* window.parent.document.getElementById("ppppp").value= "<fmt:message key="view.ywzbgzpi"/> >> <fmt:message key="view.yssjgzpz"/>"; */
 
	 /* $(document).ready(function(){
		   if($("#atiaos").val()==1){
			  alert("<fmt:message key="statExport.writeFlag"/>");
		   }
	}); */

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
	      
	       function atiao(repid,rtarid,rtype,bflag,levelFlag,flags){
	    	var url="reportrule.do?method=editThePriRule&repid="+repid+"&rtarid="+rtarid+"&rtype="+rtype+"&bflag="+bflag+"&levelFlag="+levelFlag+"&flags="+flags;
	       	location.href=url;
	      } 
	       
	       $(document).ready(function(){
		       if("<c:out value='${message}'/>"==1){
		           alert("<fmt:message key="statExport.writeFlag"/>");
		           if(<c:out value='${flags==2}'/>){
		        	   location.href="reportrule.do?method=listPriRule&levelFlag=1";
		           }else{
		        	   window.parent.window.ClosePanel();
		           }
		       }else if("<c:out value='${message}'/>"==2){
		           alert("<fmt:message key="statExport.noContrast"/>");
		       }
	    	   if($("#atiaos").val()==0){
	    		   var url=$("#urlflag").val();
	        	   location.href=url;
	    	   }
	       }); 
</script>
</head>

<body  class="easyui-layout" fit="true">

<input id="urlflag"   value="<c:out value='${urlflag}'/>"/>
   
   <input id="atiaos" type="hidden" value="<c:out value='${atiaos}'/>"/>


   <c:if test="${flags==2}">
    <div data-options="region:'north'" style="height:40px" class="navbar">
    
      		<a href="reportrule.do?method=listPriRule&levelFlag=1" class="easyui-linkbutton" data-options="iconCls:'icon-back'"><fmt:message key="button.back"/></a>
      		
      		<fmt:message key="view.ywzbgzpi"/> >> <fmt:message key="view.yssjgzpz"/><b>&nbsp;&nbsp;<c:out value="${reportName}"/></b>
	</div>
	</c:if>
      			
      			<c:if test="${flags==2}">
   				<div data-options="region:'west',split:true"  style="width:132px">
    				<div class="menucontent">

      					
      			     	<c:forEach var="rt" items="${lxhtargetList}" varStatus="index">
	        				<%-- <div style="width:120px;float:left;border:1px solid #cccccc;padding:3px;margin:2px;background-color:#F2F2F2;color:#000000"><a  href="reportrule.do?method=editThePriRule&repid=<c:out value="${rt.reportId }"/>&rtarid=<c:out value="${rt.targetField }"/>&targetN=<c:out value="${rt.targetName }"/>&rtype=<c:out value="${reportrule.rtype }"/>&bflag=0&levelFlag=<c:out value="${levelFlag }"/>&flags=<c:out value="${flags}"/>"><c:out value="${rt.targetName }"/></a></div> --%>
	        				<div style="width:120px;float:left;border:1px solid #cccccc;padding:3px;margin:2px;background-color:#F2F2F2;color:#000000" ><a href="#" onclick="atiao('<c:out value="${rt.reportId }"/>','<c:out value="${rt.targetField }"/>','<c:out value="${reportrule.rtype }"/>','0','<c:out value="${levelFlag }"/>','<c:out value="${flags}"/>')"><c:out value="${rt.targetName }"/></a></div>
	        			</c:forEach>  
	        			 
   					 </div>
  				</div>
  				</c:if>
  				<!--end optionBox1-->
  					
				<div data-options="region:'center',split:false" >
				<input type="hidden" name="rtype"  value="3"/>
				<form action="#" method="post" name="apLogin" id="apLogin">
  				<input type="hidden" name="repid" id="replid" value="<c:out value="${reportrule.modelid}"/>" />
  				<input type="hidden" name="rtarid" id="rtarid" value="<c:out value="${reportrule.targetid}"/>" />
  				<input type="hidden" name="targetN" id="targetN"value="<c:out value="${reportrule.targetname}"/>"/>
  				<input type="hidden" name="rtype1" id="rtype1"value="<c:out value="${reportrule.rtype}"/>"/>
  				<input type="hidden" name="defChar" id="defChar"value="<c:out value="${defChar}"/>"/>
  				<input type="hidden" name="bflag1" id="bflag1"value="3"/>
  				<input type="hidden" name="levelFlag" id="levelFlag" value="<c:out value="${levelFlag}"/>"/>
  				<input type="hidden" name="range" id="range" value="<c:out value="${range}"/>"/>
    			
    				<div class="navbar2">
    				</div> 
    				<table  cellpadding="10" cellspacing="10">
    				
    				<tr class="navbar2">
	      					 <td>
	      					 	<fmt:message key="da.reportname"/>
	      					 </td>
	      					  <td style="color: blue">
								<c:out value="${reportName}"/>
	      					 </td>
                        </tr>
    				<tr>
	      					 <td>
    						<fmt:message key="view.zbmc"/>
    				 		</td>
	      					  <td style="color: blue">
	      					  <c:out value="${reportrule.targetname}"/>
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
	      					  <select id="content" name="content" style="width: 160">
    				  			<option value='checkName' <c:if test="${reportrule.content=='checkName' }"> selected </c:if> selected><fmt:message key="view.xmtm"/></option>
    				  			<option value='checkIDcard' <c:if test="${reportrule.content=='checkIDcard' }"> selected </c:if> ><fmt:message key="view.sftm"/></option>
    				  			<option value='checkCode' <c:if test="${reportrule.content=='checkCode' }"> selected </c:if> ><fmt:message key="view.zjhm"/></option>
    				  			<option value='checkNull' <c:if test="${reportrule.content=='checkNull' }"> selected </c:if> ><fmt:message key="view.zhikong"/></option>
    				  			</select>	
	      					 </td>
                        </tr>
    					<tr>
    				  		<td>
    				  				<fmt:message key="view.gzbm"/>
    				  		</td>
    				  		<td>
    				  				<input type="text" name="rulecode" value="<c:out value="${reportrule.rulecode}"/>" style="width: 160"  />
    				  		</td>
    				  		
    				  	</tr>
    				  	 
                         <tr>
	      					 <td>
	      					 	<fmt:message key="view.gzsm"/>
	      					 </td>
	      					  <td>
	      					  <textarea rows="5" cols="65" name="contentdes" id="contentdes"><c:out value="${reportrule.contentdes }"/></textarea>
	      					 </td>
                        </tr>
                       </table>
 					 <br/>

 					 <label for="but" style="margin-left:100px;">
 					  <a href="#" class="easyui-linkbutton c5"  data-options="iconCls:'icon-save'" style="width:120px; text-decoration:none;"  onclick="testResult();" ><fmt:message key="button.ratifypass"/></a>
 					</label>

				</div>
 					 </form>
 					
 					 
			   <div id="users" style="display:none"></div>	
</body>
</html>
<script type="text/javascript">
function testResult(){
	//alert("flags:<c:out value='${flags}'/>");
	var url="reportrule.do?method=saveYjhReportRule&flags=<c:out value='${flags}'/>";
	//var url="reportrule.do?method=saveYjhReportRule&flags="+$("#flags").val();
	document.getElementById("apLogin").action=url;
	document.getElementById("apLogin").submit(); 
}
</script>