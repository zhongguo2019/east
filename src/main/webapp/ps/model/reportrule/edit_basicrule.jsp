<!-- /ps/model/reportrule/edit_basicrule.jsp -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>

<%
 response.setContentType("text/html;charset=UTF-8"); 
 response.setCharacterEncoding("UTF-8");
%>
<html>
<head>
<title>    </title>
 	<link rel="stylesheet" href="<c:url value='/ps/uitl/jmenu/css/styles.css'/>" type="text/css" />
	<link rel="stylesheet" href="<c:url value='/ps/uitl/jmune/css/jquery-tool.css'/>" type="text/css" />

<script type="text/javascript" src="<c:url value='/ps/scripts/reportrule/edit_reportrule.js'/>"></script>
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
       
       function atiao(repid,rtarid,targetN,rtype,bflag,flags,levelFlag){
    	var url="reportrule.do?method=editTheReportRule&repid="+repid+"&rtarid="+rtarid+"&targetN="+targetN+"&rtype="+rtype+"&bflag="+bflag+"&flags="+flags+"&levelFlag="+levelFlag;
    	location.href=url;
      } 
       
       $(document).ready(function(){
    	   if($("#atiaos").val()==1){
    		   var url=$("#urlflag").val();
        	   location.href=url;
    	   }
       });
       
      /*  window.parent.document.getElementById("ppppp").value= "<fmt:message key="view.ywzbgzpi"/> >> <fmt:message key="view.jbgzpz"/>"; */
   </script> 

</head>
<body class="easyui-layout" fit="true">
   
   <input id="urlflag" type="hidden" value="<c:out value='${urlflag}'/>"/>
   <input id="atiaos"  type="hidden" value="<c:out value='${atiaos}'/>"/>
   <input id="flags" type="hidden" value="<c:out value='${flags}'/>"/>

   <c:if test="${flags==2}">
   <div data-options="region:'north'" style="height:40px" class="navbar">
      		<a href="reportrule.do?method=listBasicRule&levelFlag=1" class="easyui-linkbutton" data-options="iconCls:'icon-back'" ><fmt:message key="button.back"/></a>
			<fmt:message key="view.ywzbgzpi"/> >> <fmt:message key="view.jbgzpz"/><b>&nbsp;&nbsp;<c:out value="${reportName}"/></b>
	</div>
      </c:if>			
      			
      			<c:if test="${flags==2}">
      			
   				<div data-options="region:'west',split:true" style="width:132px">
    				<div class="menucontent">
      					<ul>
      					<c:forEach var="rt" items="${lxhtargetList}" varStatus="index">
	        				<%-- <div style="width:120px;float:left;border:1px solid #cccccc;padding:3px;margin:2px;background-color:#F2F2F2;color:#000000"><a id="atip"  href="#" onclick="atiao('<c:out value="${rt.reportId }"/>','<c:out value="${rt.targetField }"/>','<c:out value="${rt.targetName }"/>','<c:out value="${reportrule.rtype }"/>','0','<c:out value="${flags }"/>','<c:out value="${levelFlag }"/>')"><c:out value="${rt.targetName }"/></a></div> --%>
	        				<div  style="width:120px;float:left;border:1px solid #cccccc;padding:3px;margin:2px;background-color:#F2F2F2;color:#000000"  onclick="atiao('<c:out value="${rt.reportId }"/>','<c:out value="${rt.targetField }"/>','<c:out value="${rt.targetName }"/>','<c:out value="${reportrule.rtype }"/>','0','<c:out value="${flags }"/>','<c:out value="${levelFlag }"/>')"><a href="#" style="text-decoration: none;"><c:out value="${rt.targetName }"/></a></div>
	        			</c:forEach>
     			 		</ul>
   					 </div>
  				</div>
  				</c:if>
  				<!--end optionBox1-->
  				
  				<div data-options="region:'center',split:true">
  					<form action="reportrule.do?method=editTheReportRule" method="post" name="tijiaoForm" id="tijiaoForm">
		  				<input type="hidden" name="repid" value="<c:out value="${reportrule.modelid}"/>" />
		  				<input type="hidden" name="rtarid" id="rtarid" value="<c:out value="${reportrule.targetid}"/>" />
		  				<input type="hidden" name="rulecode" value="<c:out value="${reportrule.rulecode}"/>"/>
		  				<input type="hidden" name="targetN1" id="targetN"value="<c:out value="${reportrule.targetname}"/>"/>
		  				<input type="hidden" name="cflag" id="cflag"value="2"/>
		  				<input type="hidden" name="bflag" id="bflag"value="0"/>
		  				<input type="hidden" name="levelFlag" id="levelFlag"value="<c:out value="${levelFlag}"/>"/>
	      					 		
					</form>
					
					
			<form action="#" method="post" name="apLogin" id="apLogin">
  				<input type="hidden" name="repid" id="replid" value="<c:out value="${reportrule.modelid}"/>" />
  				<input type="hidden" name="rtarid" id="rtarid" value="<c:out value="${reportrule.targetid}"/>" />
  				<input type="hidden" name="targetN" id="targetN"value="<c:out value="${reportrule.targetname}"/>"/>
  				<input type="hidden" name="rtype1" id="rtype1"value="<c:out value="${reportrule.rtype}"/>"/>
  				<input type="hidden" name="defChar" id="defChar"value="<c:out value="${defChar}"/>"/>
  				<input type="hidden" name="bflag1" id="bflag1"value="0"/>
  				<input type="hidden" name="levelFlag" id="levelFlag" value="<c:out value="${levelFlag}"/>"/>
  				<input type="hidden" name="range" id="range" value="<c:out value="${range}"/>"/>
    				
    				<%-- <div>
      					<label for="null0">
      					<a href="#" class="easyui-linkbutton" style="width:80; height: 20; text-decoration:none;"  onclick="javascript:editrulenull('1');" ><fmt:message key="view.notkong"/></a>
      					</label>
      					<label for="null1">
      					<a href="#" class="easyui-linkbutton" style="width:80; height: 20; text-decoration:none;"  onclick="javascript:editrulenull('2');" ><fmt:message key="view.kong"/></a>
      					<!-- <input type="button" id ="null4" name="rulenull" value='<fmt:message key="view.kong"/>' onclick="javascript:editrulenull('2');"/>-->
      					</label>
      					<br/>
    				</div> --%>
    				<div class="navbar2">
    				</div> 
    				<table cellpadding="10" cellspacing="10">
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
    				<fmt:message key="view.gzlx"/>
    				</td>
	      					  <td>
									<select name="rtype" onchange="getReportComRule(this.value)" style="width: 160">
										<c:forEach items="${ruleType}" var="type"><c:if
										 test="${reportrule.rtype==type.pkid}"><option value="<c:out value="${type.pkid}"/>" selected><c:out value="${type.typename}"/></option></c:if>
										<c:if test="${reportrule.rtype!=type.pkid}"><option value="<c:out value="${type.pkid}"/>"><c:out value="${type.typename}"/></option></c:if></c:forEach>
									</select>
							</td>
    				</tr>
    				
    					<tr>
    				  		<td>
    				  				<fmt:message key="view.gzbm"/>
    				  		</td>
    				  		<td>
    				  				<input type="text" name="rulecode" style="width: 160" value="<c:out value="${reportrule.rulecode}"/>"   />
    				  		</td>
    				  		
    				  	</tr>
    				  	<tr>
	      					 <td>
	      					 	<fmt:message key="view.dyzb.key"/>
	      					 </td>
	      					  <td>
	      					  <input type="text"  name="keyid" id="keyid" style="width: 160" value="<c:out value="${reportrule.keyid }"/>"   />
	      					 </td>
                        </tr>
                         <tr>
	      					 <td>
	      					 	<fmt:message key="view.mbrzb"/>
	      					 </td>
	      					  <td>
	      					    <input type="text"  name="targettable" id="targettable" style="width: 160" value="<c:out value="${reportrule.targettable }" />"  style="width:250px;" />
	      					 </td>
                        </tr>
                         
    				  	<tr >
    				  		<td>
    				  				<fmt:message key="view.zdmc"/>
    				  		</td>
    				  		<td>
    				  			<select id="ziduan" onchange="setContentVal('ziduan')" style="width: 160">
    				  			<c:forEach var="rt" items="${ll}" varStatus="index">
    				  			<option value='<c:out value="${rt.targetField }"/>'><c:out value="${rt.targetName }"/></option>
    				  			</c:forEach>
    				  			</select>	
    				  		</td>
    				  	</tr>
    				  	 <tr>
	      					 <td>
	      					 	<fmt:message key="view.gzjb"/>
	      					 </td>
	      					  <td>
	      					  <textarea rows="5" cols="65" name="content" id="content"><c:out value="${reportrule.content }"/></textarea>
	      					  <%-- <a href="#" class="easyui-linkbutton" style="width:80; height: 20; text-decoration:none;"  onclick="testsql();" ><fmt:message key="view.cs"/></a> --%>
	      					 <!--   <input type="button" value='<fmt:message key="view.cs"/>' onclick="testsql()" /> -->
	      					 <div>
      					<label for="null0">
      					<a href="#" class="easyui-linkbutton" style=" text-decoration:none;"  onclick="javascript:editrulenull('1');" ><fmt:message key="view.notkong"/></a>
      					</label>
      					<label for="null1">
      					<a href="#" class="easyui-linkbutton" style=" text-decoration:none;"  onclick="javascript:editrulenull('2');" ><fmt:message key="view.kong"/></a>
      					<!-- <input type="button" id ="null4" name="rulenull" value='<fmt:message key="view.kong"/>' onclick="javascript:editrulenull('2');"/>-->
      					</label>
      					
      					<a href="#" class="easyui-linkbutton" style=" text-decoration:none;"  onclick="testsql();" ><fmt:message key="view.cs"/></a>
    				</div>
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
                         <tr id="pcheck" >
	      					 <td>
	      					 	<fmt:message key="view.ymjbxy"/>
	      					 </td>
	      					 <td>
	      					  <table cellpadding="10" cellspacing="10">
			    					<tr>
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
			    				  	</tr>
			    				  	<c:if test="${enit==2}">
				    				  	<tr>
				    				  		<td>
				    				  				<input type="text" name="gzmc" value="<c:out value="${rulesname}"/>" />
				    				  		</td>
				    				  		<td >
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
				    				  	</tr>
			    				  	</c:if>
			    				  	<c:if test="${enou==1}">
			    				  	 <c:forEach var="fv" items="${fromVlidatList}" varStatus="fvs">
			    				  	   
			    				  	  <tr>
			    				  	     <input type="hidden"   name="pkidobj" id="pkidval" value="" />
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
    				
 					 <br/>
 					 <label for="but" style="margin-left:250px;">
 					 <a href="#" class="easyui-linkbutton c5"  data-options="iconCls:'icon-save'" style="width:120px; text-decoration:none;"   onclick="testResult();" ><fmt:message key="button.ratifypass"/></a>
 					 </label>
				</div>
 					 </form>
 					 </div>
 					 </div>
			   <div id="users" style="display:none"></div>	
		<!-- 	</td>
		</tr>
	</table> -->
</body>
</html>
<script type="text/javascript">
  <c:if test="${yes == 'yes'}"><img src="ps/framework/images/xiaolian.png"><font style="color:#990000;"><b><fmt:message key="view.bccg"/></b></font></c:if>
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
	var flags = $("#flags").val();
	$.ajax({ 
		type: "POST", 
		async: false, //ajax
		url: "reportrule.do", 
		data: jQuery.param(params), 
		success: function(result){
			if('true'==result){
				/* document.getElementById("apLogin").action='<c:out value="${hostPrefix}" /><c:url value="/reportrule.do" />?method=saveReportRule';
				document.getElementById("apLogin").submit(); */
				var url1 = '<c:out value="${hostPrefix}" /><c:url value="/reportrule.do" />?method=saveReportRule&flags='+flags;
				$("#apLogin").form("submit",{url:url1});
				location.href=$("#urlflag").val();
				  alert('<fmt:message key="view.bccg" />'); 
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
			aa="select r.NBJGH, r."+keyid+",to_char(r."+rtarid+"),null as a,null as b,null as c from "+cc+" r  where    r."+rtarid+" is  null or  r."+rtarid+" = '' or r."+rtarid+" = 'null'";
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
				aa="select r.NBJGH, r."+keyid+",to_char(r."+rtarid+") ,null as a,null as b,null as c from  "+cc+" r  where     r."+rtarid+" is not null or r."+rtarid+" !='' ";
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
		//var df=document.tijiaoForm;
		//df.submit();
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
