<!-- /ps/model/datafill/importDataOne. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<html>
<head>
<!-- reportDataEdit.jsp -->
<title><fmt:message key="webapp.prefix" /></title>
<style type="text/css">
table tr:hover
{
    background-color:#edf4fa;

}
</style>

<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='${tableCss}/tablecss.css'/>" />
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.4.1.min.js'/>" type="text/javascript"></script>
<script language="javascript" type="text/javascript" src="<c:url value='/ps/uitl/My97DatePicker/WdatePickerDateOne.js'/>"></script>
<script src="<c:url value='/ps/uitl/krmtree/JSrepfile/validateJson.js'/>" type="text/javascript"></script>
<script language="JavaScript" type="text/JavaScript">
		var $j = jQuery.noConflict();  //jquery别名
</script>
<script src="<c:url value='${filenameUrl} '/>" type="text/javascript"></script>
<head>
<script language="JavaScript">
   jsondata = <c:out value='${jsonObjectFrom}' escapeXml="false"/>;
   init(jsondata);
</script>

<script type="text/javascript">
	function addone(index){
		var dataDate=document.getElementById("dataDate").value;
		var reportId=document.getElementById("reportId").value;
		var organInfocode=document.getElementById("organInfocode").value;
		var organId=document.getElementById("organId").value;
		var aa = document.getElementsByName("databox");
		var aalen = (aa.length-1);
    	var arra = (aa[aalen].value.split(","))[0];
		var clss = arra.split("_")[0];
		clss++;
		index=document.getElementsByName("tr").length;
		index=clss;
		var repDataMap111="";//document.getElementById("repDataMap111").value;
		var table=document.getElementById("budgetTbl");
		var tr=	table.insertRow();//document.createElement("tr");
		var fieldType=document.getElementById("fieldType");
		var td1=tr.insertCell();//document.createElement("td");
		var td2=tr.insertCell();//document.createElement("td");
		var length=document.getElementById("reportTargetListlength").value;
		var aa=0;
		 for(var i=0;i<parseInt(length);i++){
			 var fieldType1=document.getElementsByName("fieldType2222")[i].value;
			 var itemDataKey111=document.getElementsByName("itemDataKey111")[i].value;
			 var ruleMap111=document.getElementsByName("ruleMap1")[i].value;
			 var dtypeMap111=document.getElementsByName("dtypeMap1")[i].value; 
			var td3=tr.insertCell();
			var a="";
			var repDataMap=document.getElementsByName("repDataMap111")[i].value;
			if(parseInt(fieldType1)==0){
				var itemSortkey=document.getElementById("itemSortkey1").value;
				var itemvalueIndex=document.getElementById("itemvalueIndex1").value;
				var reportTargettargetName=document.getElementById("reportTargettargetName1").value;
				var reportTargetdataType=document.getElementsByName("reportTargetdataType")[aa].value;
				td3.innerHTML="<input type='hidden' name='"+index+"_jilu' id='"+index+"_"+itemDataKey111+"_jiluvalue' align='left' value='"+index+"_"+itemDataKey111+":"+repDataMap111+"'/>";
				td3.innerHTML+="<input type='hidden' id='itemSortkey' value='"+itemSortkey+"'/>";
				td3.innerHTML+="<input type='hidden' id='itemvalueIndex' value='"+itemvalueIndex+"'/>";
				td3.innerHTML+="<input type='hidden' id='reportTargettargetName' value='"+reportTargettargetName+"'/>";
				td3.innerHTML+="<input type='hidden' id='reportTargetdataType' value='"+reportTargetdataType+"'/>";
				var aaa="";
				var bbb="";
				if(dtypeMap111!=null&&parseInt(dtypeMap111)!=0){
					 aaa="background-color: #FF6666";
				}
				aaa+="';";
				if(ruleMap111!=null){
					bbb="dataType='"+itemvalueIndex+"' labelText='"+reportTargettargetName+"' ";
				}
				if(parseInt(reportTargetdataType)!=3){
					bbb+="size='7' ";
				}else{
					bbb+="size='12' ";
				}
				td3.innerHTML+="<input type='hidden' name='"+itemDataKey111+"_id' align='left' value='"+itemSortkey+"' />";

				td3.innerHTML+="<input onpaste = 'ieeSet(&quot;"+index+"_"+itemDataKey111+"&quot;);' onfocus='onfocus1(this);'  onblur='onblur1(this);'  onpropertychange='aaa(&quot;"+index+"_"+itemDataKey111+"&quot;);' type='text' id='"+index+"_"+itemDataKey111+"' name='"+index+"_"+itemDataKey111+"' align='left' style='height: 21;' "+aaa+";"+bbb+" />";
				aa++;
			}
			
			if(parseInt(fieldType1)==1||parseInt(fieldType1)==3){
				td3.innerHTML="";
				var itemvalueIndex222=document.getElementById("itemvalueIndex222").value;
				var reporttargetName222=document.getElementById("reporttargetName222").value;
				var itemSortkey222=document.getElementById("itemSortkey222").value;
				var repDataMap222="";  //document.getElementById("repDataMap222").value;
				var aaa="";
				var bbb="";
				if(ruleMap111!=null){
					aaa+="dataType='"+itemvalueIndex222+"' labelText='"+reporttargetName222+"' ";
				}
				aaa+="style='height: 21;width:100px;";
				if(dtypeMap111!=null&&dtypeMap111==1){
					bbb+="background-color: #FF6666";
				}
				bbb+="'";
				
				td3.innerHTML+="<input type='hidden' name='"+itemDataKey111+"_id' align='left' value='"+itemSortkey222+"' />";
				var tt="<select   id='"+index+"_"+itemDataKey111+"' name='"+itemDataKey111+"' "+aaa+" "+bbb+" onpropertychange=aaa(&quot;"+index+"_"+itemDataKey111+"&quot;)>";
				for(var j=0;j<parseInt(document.getElementsByName("vdicName").length);j++){
					 var vdicIds =document.getElementsByName("vdicId")[j].value;
					 var targetid=vdicIds.split("_")[0];
					 var vdicId=vdicIds.split("_")[1];
					 if(itemDataKey111==targetid){
						 tt+="<option value='"+vdicId+"'>"+document.getElementsByName("vdicName")[j].value+"</option>"; 
				      }
	       	 	}
				tt+="</select>"; 
				td3.innerHTML+=tt;
				td3.innerHTML+="<input type='hidden' name='"+index+"_jilu' id='"+index+"_"+itemDataKey111+"_jiluvalue' align='left' value='"+index+"_"+itemDataKey111+":"+document.getElementById(index+"_"+itemDataKey111).value+"' />";
			}
			
			if(parseInt(fieldType1)==2){
				var itemSortkey=document.getElementById("itemSortkey").value;
				var itemvalueIndex333=document.getElementById("itemvalueIndex333").value;
				var reportName333=document.getElementById("reportName333").value;
				var repDataMap333="";//document.getElementById("repDataMap333").value;
				td3.innerHTML="<input type='hidden'  name='"+index+"_jilu' id='"+index+"_"+itemDataKey111+"_jiluvalue' align='left' value='"+index+"_"+itemDataKey111+":"+repDataMap111+"' />";
				var aaa="";
				var bbb="";
				if(ruleMap111!=null){
					aaa+="dataType='"+itemvalueIndex333+"' labelText='"+reportName333+"' align='left'";
				}
				if(dtypeMap111!=null&&parseInt(dtypeMap111)==1){
					bbb+="background-color: #FF6666";
				}
				td3.innerHTML+="<input type='hidden' name='"+itemDataKey111+"_id' align='left' value='"+itemSortkey+"'";
				td3.innerHTML+="<input onfocus='onfocus1(this);'  onblur='onblur1(this);'  onpropertychange=aaa(&quot;"+index+"_"+itemDataKey111+"&quot;) type='text' onclick='WdatePicker();' id='"+index+"_"+itemDataKey111+"' name='"+itemDataKey111+"'"+aaa+""+bbb+"readonly='readonly' size='12' />";
			}
		} 
		var td=tr.insertCell();
		tr.setAttribute("id","tr_"+index);
		var ccc="iconCls:'icon-save'";
		var ddd="iconCls:'icon-add'";
		var hhh="iconCls:'icon-remove'";
		td2.setAttribute("align","center");
		td1.innerHTML="<input type='checkbox' id='databox_"+index+"' name='databox' value='"+index+"_jilu' />"; 
		td2.innerText=index;
		td.setAttribute("style","white-space:nowrap;overflow:hidden;word-break:break-all;");
		td.innerHTML="<a href='#' style='height: 20;' class='easyui-linkbutton l-btn l-btn-small'  data-options="+ccc+"  onclick='singleUpdate(this,1,&quot;"+index+"&quot;,&quot;"+dataDate+"&quot;,&quot;"+reportId+"&quot;,&quot;"+organInfocode+"&quot;,&quot;"+organId+"&quot;);'><span class='l-btn-left l-btn-icon-left' style='margin-top: -3px;'><span class='l-btn-text'><fmt:message key='button.save' /></span><span class='l-btn-icon icon-save'></span></span></a>"+
		"<a href='#' style='height: 20;' class='easyui-linkbutton l-btn l-btn-small' data-options="+ddd+"  onclick='addone("+index+");'><span class='l-btn-left l-btn-icon-left' style='margin-top: -3px;'><span class='l-btn-text'><fmt:message key='select.insert.onecell' /></span><span class='l-btn-icon icon-add'></span></span></a>"+
		 "<a href='#' style='height: 20;' class='easyui-linkbutton l-btn l-btn-small' data-options="+hhh+"  onclick='huoqu("+index+");'><span class='l-btn-left l-btn-icon-left' style='margin-top: -3px;'><span class='l-btn-text'><fmt:message key='select.del.onecell' /></span><span class='l-btn-icon icon-remove'></span></span></a><input type='hidden' name='tr' >";
		var tempflag = $j("#tempflag").val();
		tempflag++;
		$j("#tempflag").val(tempflag);
	}
	
	function huoqu(aa){
		var table=document.getElementById("tr_"+aa).parentNode;
		var tr=document.getElementById("tr_"+aa);
		table.removeChild(tr);
		var tempflag = $j("#tempflag").val();
		tempflag--;
		$j("#tempflag").val(tempflag);
	} 
	function aaa(bb){
		document.getElementById(bb+"_jiluvalue").value=bb+":"+document.getElementById(bb).value;
		
	}


	function onfocus1(obj){
		 obj.style.backgroundColor="#FFFFE0";
	}
	function onblur1(obj){
		obj.style.backgroundColor="white";
	}




</script>
</head>
<body onload="init();" >
 
	<form action="/studentLoan.do?method=save" method="post"
		name="studentLoanForm" enctype="application/x-www-form-urlencoded"
		style="margin: 0">
		<input type="hidden" id="tempflag" value="1" />
		<input type="hidden" id="dataDate" value="<c:out value='${dataDate}'/>"> 
		<input type="hidden" id="reportId" value="<c:out value='${reportId}'/>">
	    <input type="hidden" id="organInfocode" value="<c:out value='${organInfo.code}'/>"> 
	    <input type="hidden" id="organId" value="<c:out value='${organId}'/>">
		<input type="hidden" id="dicMapsize" value="<c:out value='${dicMapsize}'/>"> 
		<input type="hidden" name="dataDate" value="<c:out value='${dataDate}'/>"> 
		<input type="hidden" id="reportTargetListlength" value="<c:out value='${reportTargetListlength}'/>"> 
		<input type="hidden" id="reportId" name="reportId" value="<c:out value='${reportId}'/>"> 
		<input type="hidden" id="organId" name="organId" value="<c:out value='${organId}'/>">
		<input type="hidden" name="filledFlag" value="${filledFlag}" />
        <input type="hidden" name="relateFlag" value="<c:out value='${relateFlag}'/>" /> 
        <input type="hidden" name="levelFlag" value="<c:out value='${levelFlag}'/>" />
		<div class="navbar">
			<a href="#" onclick="history.back();" class="easyui-linkbutton" data-options="iconCls:'icon-back'">
			    <fmt:message key="button.back" /> 
			</a> &nbsp;
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="datasort();">
			     <fmt:message key="standard.dataFill.databox" /> 
			</a>
		</div>
		<div style="display: none;">
				<c:forEach var="reportTarget" items="${reportTargetList}">
					<input type="hidden" name="itemDataKey1" value="<c:out value='${itemDataKey}'/>"> 
						<input type="text" name="repDataMap1" value="<c:out value='${repDataMap[itemDataKey]}'/>"> 
						<input	type="hidden" name="ruleMap1" value="<c:out value='${ruleMap[itemDataKey]}'/>"> 
						<input	type="hidden" name="dtypeMap1"	value="<c:out value='${dtypeMap[itemDataKey]}'/>">
						<input  name="fieldType2222" value="<c:out value='${reportTarget.fieldType}'/>">
				  <c:if test="${reportTarget.fieldType==0}">
							<input  name="fieldType1" type="hidden"	value="<c:out value='${reportTarget.fieldType}'/>"> 
							<input type="hidden" id="itemSortkey1" value="<c:out value="${itemSort.key}"/>">
							<input type="hidden" id="itemvalueIndex1" value="<c:out  value='${itemvalueIndex}' />">
							<input type="hidden" id="reportTargettargetName1" value="<c:out value='${reportTarget.targetName}'/>">
							<input type="hidden" name="reportTargetdataType1" value="<c:out value='${reportTarget.dataType}'/>">
							<input type="hidden" name="<c:out value="${itemDataKey}"/>_id1" align="left" value="<c:out value="${itemSort.key}"/>" />
				 </c:if>
                 <c:if test="${reportTarget.fieldType==2}">
							<input type="hidden" id="itemDataKey333" value="<c:out value='${itemDataKey }'/>">
							<input type="hidden" id="ruleMap333" value="<c:out value='${ ruleMap[itemDataKey]}'/>">
							<input type="hidden" id="itemSortkey" value="<c:out value='${itemSort.key }'/>">
							<input type="hidden" id="itemvalueIndex333" value="<c:out value='${itemvalueIndex }'/>">
							<input type="hidden" id="reportName333" value="<c:out value='${reportTarget.targetName }'/>">
							<input type="hidden" id="dtypeMap333" value="<c:out value='${dtypeMap[itemDataKey] }'/>">
				</c:if>
                <c:if test="${reportTarget.fieldType==1||reportTarget.fieldType==3}">
		              <c:forEach items="${dicMap}" var="d">
								<input type="hidden" name="dkey" value="<c:out value="${d.key}"/>">
								<input type="hidden" name="reportTargetpkid" value="<c:out value="${reportTarget.pkid}"/>">
								<c:if test="${d.key==reportTarget.pkid}">
									<c:forEach items="${d.value}" var="v" begin="0" varStatus="status">
										<input type="hidden" name="vdicId" value="<c:out value="${reportTarget.targetField}" />_<c:out value="${v.dicId}" />">
										<input type="hidden" name="vdicName" value="<c:out value="${v.dicName}" />">
									</c:forEach>
								</c:if>
					   </c:forEach>
							<input type="hidden" id="itemDataKey222" value="<c:out value="${itemDataKey}"/>">
							<input type="hidden" id="ruleMap222" value="<c:out value="${ruleMap[itemDataKey]}"/>">
							<input type="hidden" id="itemvalueIndex222" value="<c:out value="${itemvalueIndex}"/>">
							<input type="hidden" id="reporttargetName222" value="<c:out value="${reportTarget.targetName}"/>">
							<input type="hidden" id="dtypeMap222" value="<c:out value="${dtypeMap[itemDataKey]}"/>">
							<input type="hidden" id="itemSortkey222" value="<c:out value="${itemSort.key}"/>">
							<input type="hidden" id="repDataMap222" value="<c:out value="${repDataMap[itemDataKey]}"/>">
						</c:if> 
				</c:forEach>
			</div>	
		<table id="budgetTbl" width="99%" align="center" cellpadding="1"
			class="table" cellspacing="1" style="overflow-x:scroll; " >
			<thead>
				<tr height="40px">
					<td class="color" align="center" style="width: 20px;">
					     <input id="all" type="checkbox" onclick="databoxsort(this);" />
					</td>
					<td class="color" align="center" style="width: 60px;" nowrap>
					     <fmt:message key="studentloan.report.sortnum" />
					</td>
					<!--填充数据  -->
					<c:forEach var="reportTarget" items="${reportTargetList}">
						<td id="titleName<c:out value='${reportTarget.targetOrder}' />" class="color" align="center" style="white-space:nowrap;overflow:hidden;word-break:break-all; "
							width="<c:out value="${reportTarget.editDate}"/>"><c:out
								value="${reportTarget.targetName}" /> 
						   <input type="hidden" id="reporttargetdatatype_<c:out value="${reportTarget.targetField}" />" value="<c:out value="${reportTarget.dataType}" />"> 
						   <input type="hidden" id="reporttargettargetName_<c:out value="${reportTarget.targetField}" />" value="<c:out value="${reportTarget.targetName}" />"> 
						   <input type="hidden" id="reporttargetrulesize_<c:out value="${reportTarget.targetField}" />" value="<c:out value="${reportTarget.rulesize}" />">
						</td>
					</c:forEach>
					<td class="color" align="center" style="white-space:nowrap;overflow:hidden;word-break:break-all;width: 200px;">
					       <fmt:message key="button.operation" />
					</td>
				</tr>
			</thead>
			<tr id="tr_1" >
				<td align="center" style="width: 20px;"><input type="checkbox" id="databox_1" name="databox" value="1_jilu" />
					<input name="tr111" type="hidden" value="1">
				</td>
				<td align="center">
				   1
				</td>
				<c:forEach var="reportTarget" items="${reportTargetList}">
					<td>
					    <c:set var="itemDataKey"> <c:out value="${reportTarget.targetField}" /></c:set> 
					    <c:set var="targetField"><c:out value="${reportTarget.targetField}" /></c:set> 
					    <input type="hidden" name="itemDataKey111"value="<c:out value='${itemDataKey}'/>"> 
					    <input type="hidden" name="repDataMap111" value="<c:out value='${repDataMap[itemDataKey]}'/>"> 
					    <input type="hidden" name="1_jilu" id="1_<c:out value="${itemDataKey}"/>_jiluvalue" align="left"
						       value="1_<c:out value="${itemDataKey}"/>:<c:out value="${repDataMap[itemDataKey]}"/>" />
						<c:if test="${reportTarget.fieldType==0}">
							<input type="hidden" id="itemSortkey" value="<c:out value="${itemSort.key}"/>">
							<input type="hidden" id="itemvalueIndex" value="<c:out  value='${itemvalueIndex}' />">
							<input type="hidden" id="reportTargettargetName" value="<c:out value='${reportTarget.targetName}'/>">
							<input type="hidden" name="reportTargetdataType" value="<c:out value='${reportTarget.dataType}'/>">
							<input type="hidden" name="<c:out value="${itemDataKey}"/>_id" align="left" value="<c:out value="${itemSort.key}"/>" />
							<input id="1_<c:out value="${itemDataKey}"/>" name="1_<c:out value="${itemDataKey}"/>" align="left"
								   style="height: 21;
								  <c:if test="${dtypeMap[itemDataKey]!=null&&dtypeMap[itemDataKey]!=0}">
								       background-color: #FF6666
								  </c:if>"
								  <c:if test="${ruleMap[itemDataKey]!=null}"> 
								        dataType="<c:out  value='${itemvalueIndex}' />"  labelText="<c:out value='${reportTarget.targetName}'/>" 
								  </c:if>
								       type="text"
								       size="<c:choose><c:when test='${reportTarget.dataType!=3}'>7</c:when><c:otherwise>12</c:otherwise></c:choose>"
								       value="<c:out value="${repDataMap[itemDataKey]}"/>" />
						</c:if>  
						<c:if
							test="${reportTarget.fieldType==1||reportTarget.fieldType==3}">
							   <input type="hidden" name="<c:out value="${itemDataKey}"/>_id" align="left" value="<c:out value="${itemSort.key}"/>" />
						    <select id="1_<c:out value="${itemDataKey}"/>" name="<c:out value="${itemDataKey}"/>"
								<c:if test="${ruleMap[itemDataKey]!=null}">  
								      dataType="<c:out  value='${itemvalueIndex}' />" labelText="<c:out value='${reportTarget.targetName}'/>" 
								</c:if>
								      style="height: 21;width:100px ;
								<c:if test="${dtypeMap[itemDataKey]!=null&&dtypeMap[itemDataKey]==1}">
								      background-color: #FF6666
								</c:if>">
								<c:forEach items="${dicMap}" var="d">
									<c:if test="${d.key==reportTarget.pkid}">
										<c:forEach items="${d.value}" var="v" begin="0"
											varStatus="status">
											<option value="<c:out value="${v.dicId}" />"
												<c:if test="${v.dicId==repDataMap[itemDataKey]}" >selected='selected'</c:if>>
												<c:out value="${v.dicName}" />
											</option>
										</c:forEach>
									</c:if>
								</c:forEach>
							</select>
							<c:if test="${ruleMap[itemDataKey]==null}">
								<input type="hidden" name="<c:out value="${itemDataKey}"/>"
									value="<c:out value="${repDataMap[itemDataKey]}"/>">
							</c:if>
						</c:if> <!--di 3 ge  cif  --> 
						<c:if test="${reportTarget.fieldType==2}">	
							<input type="hidden" id="itemSortkey" value="<c:out value="${itemSort.key}"/>">
							<input type="hidden" name="<c:out value="${itemDataKey}"/>_id" align="left" value="<c:out value="${itemSort.key}"/>" />
							<input type="text" onclick="WdatePicker();" id="1_<c:out value="${itemDataKey}"/>" name="<c:out value="${itemDataKey}"/>"
								<c:if test="${ruleMap[itemDataKey]!=null}">
								     dataType="<c:out  value='${itemvalueIndex}' />"  labelText="<c:out value='${reportTarget.targetName}'/>" 
								 </c:if>
								     align="left"
								 <c:if test="${dtypeMap[itemDataKey]!=null&&dtypeMap[itemDataKey]==1}">
								     background-color: #FF6666
								 </c:if>" 
								     readonly="readonly" type="text" size="12"
								     value="<c:out value="${repDataMap[itemDataKey]}"/>" />
						</c:if>
					</td>
				</c:forEach>
				<td  style="white-space:nowrap;overflow:hidden;word-break:break-all;">
				   <a href="#" style="height: 20;" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="singleUpdate(this,1,'1','<c:out value='${dataDate}'/>','<c:out value='${reportId}'/>','<c:out value='${organInfo.code}'/>','<c:out value='${organId}'/>'); ">
					     <fmt:message key='button.save' /> 
				   </a> 
				   <a href="#" style="height: 20;" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="addone(1);">
					    <fmt:message key='select.insert.onecell' />
				   </a> 
				   <a href="#" style="height: 20;" class="easyui-linkbutton" data-options="iconCls:'icon-remove',disabled:true" onclick="huoqu();">
					    <fmt:message key='select.del.onecell' /> 
				   </a>
				        <input type="hidden" name="tr">
				</td>
			</tr>
		</table>
	</form>
	<c:forEach items="${ruleMap}" var="rule">
		<div id="hint<c:out value='${rule.key}'/>" style="position: absolute; display: none; color: black; background: #f0f0f0; border: 1px solid #979797;">
			<table width="150px" height="50px" cellspacing="0" cellpadding="0"
				border="1px solid #979797;" frame=above>
				<tr>
					<td id="modelThinking" style="align: left; padding-left: 10px; padding-right: 10px:pointer;">
					   <c:out value="${rule.value}" />
					 </td>
				</tr>
			</table>
		</div>
	</c:forEach>
	<div id="tips"
		style="position: absolute; visibility: hidden; top: 0px; left: 0px; width: 150px; height: 60px; background-color: #d2e8ff; border: 1 solid black;">
	</div>
	<form id="pageForm" name="pageForm" method="post" action="">
		<input type="hidden" name="dataDate" value="<c:out value='${dataDate}'/>" id="dataDate" /> 
		<input type="hidden" name="reportId" value="<c:out value='${reportId}'/>" />
		<input type="hidden" name="organId" value="<c:out value='${organId}'/>" /> 
		<input type="hidden" name="refresh" id="refresh" />
	</form>
</body>
<script type="text/javascript">

	  //为每个text控件定义“获得输入焦点”和“失去焦点”时的样式     
     $j("input[type='text']").focus(function(){
    	 $j(this).css({"background-color":"#FFFFE0"});
        }).blur(function(){     
        	$j(this).css({"background-color":"white"});
                
        });      
    //jquery中未对onpaste事件(即粘贴事件)进行封装，只好采用js原有的方式为每个text控件绑定onpaste事件
    $j.each($j("input[type='text']"),function(obj,index){
         this.onpaste = ieSet;     
      });   

    
    
    function databoxsort(cb){
    	
    	
    	  if(cb.checked==true){
      		var options = document.getElementsByName("databox");
      		for(var i=0;i<options.length;i++){
      			var tr = options[i].parentNode.parentNode.id;//当前行的tr id
      	  		var trid = document.getElementById(tr);
      	  		var nexttrid = trid.id;//下一行的 tr id
      			if(document.getElementById(nexttrid).style.display!="none"){
      				options[i].checked=true;	
      			}
      			
      		}
      }else{
    	  var options = document.getElementsByName("databox");
      		for(var i=0;i<options.length;i++){
      			options[i].checked=false;
      		}
      }  
    	
    }

    function datasort(){
    	
    	var aa = document.getElementsByName("databox");
    	var ids = "";
    	var valueurl="";
    	var valueurllist = "";
    	var valuestr="";
    	for(var i=0;i<aa.length;i++){
    		if(aa[i].checked==true){
    			var arra = aa[i].value.split(",");
    			ids += arra[0]+",";
    			
    		}
    	}
    	if(ids==""){
        	alert('<fmt:message key="js.please.select"/><fmt:message key="jxchart.wizard.step4.title1"/>');
        		return false;
        }


    	for(var j=0;j<ids.split(",").length-1;j++){
    		var fk = ids.split(","); //  1jilu   2jilu
    	valuestr =document.getElementsByName(fk[j]);
			var k=1;

			for(i=0;i<valuestr.length;i++){

			}

     	 for(i=0;i<valuestr.length;i++){
    	   // alert(valuestr[i].value+"di 2ci =="+j);  	
     		var ab=valuestr[i].value.split("_")[1];
     		
     		//alert(ab);

    	      	var arr=ab.split(":");
    	      	if(!doRule(arr[0],arr[1])){
    				alert('<fmt:message key="view.tips0"/>'+(j+1)+'<fmt:message key="view.tips1"/>'+getMessages());
    				return false;
    			} 
    	      	if(arr[0]=="CJRQ"){
    	      		if(arr[1]!=""){
    	      			valueurl+=arr[0]+":"+arr[1]+"@";
    	      		}else{
    	      			valueurl+=arr[0]+":"+($("#dataDate").val()).replace("-","").substr(0,6)+"00@";
    	      		}
    	      	}else{
    	      		valueurl+=arr[0]+":"+arr[1]+"@";
    	      	}
    	    }  
     	valueurllist+=valueurl+"#";
     	valueurl="";
     	k++;
    	}

    	valueurllist = valueurllist.substring(0, valueurllist.lastIndexOf("#"));
    	var params={"stflag": 1,"reportDate":$j("#dataDate").val(),"reportId":<c:out value='${reportId}'/>,"organCode":<c:out value='${organInfo.code}'/>,"organId":<c:out value='${organId}'/>,
   			 "valueurllist":valueurllist,"method":"singleSaveOne11"};
    	$j.ajax({
			type: "POST", 
			async: false, //ajax
			url: "dataFill.do", 
			data: $j.param(params),
			success: function(result){
				if(result==1){
					alert('<fmt:message key="view.sjtjsb"/>');
				}else{
					for(var i=0;i<ids.split(",").length-1;i++){
						var abc = ids.split(",");
						var val = abc[i].split("_")[0];
						var options =document.getElementById("databox_"+val);
			      			var tr = options.parentNode.parentNode;//当前行的tr id
			      			var tbody=tr.parentNode;
			      			tbody.removeChild(tr);
						}
					alert('<fmt:message key="view.sjtjcg"/>');
				}
			}
		});
    }   
    
    function ieeSet(strs){
    	var tmp;
    	var aa = document.getElementsByName("itemDataKey111");
    	var ids = "";//获取所有的字段
    	for(var i=0;i<aa.length;i++){
    			var arra = aa[i].value.split(",");
    			ids += arra+",";
    			
    	}
    	var xindex = strs.split("_")[0];//获取行号
    	var xfield = strs.split("_")[1];//获取当前字段
    	ids = ","+ids;
    	var fieldarr = xfield+","+(ids.split(","+xfield+",")[1]);
    	var fieldarrlen = fieldarr.split(",");

        var str = window.clipboardData.getData("text"); //获取剪切板数据

        var reportTargetListlength = $j("#reportTargetListlength").val();
        var len = str.split("\n");//获取剪切板数据行数     

        var trStr;   
        	var tempflag = $j("#tempflag").val();//获取当前最大行号

        	
        	var tflen = tempflag-xindex+1;//当前所在行
        	if(tflen<len.length){
        		var fleng = len.length-tflen;
        		for(var j=2;j<=fleng;j++){
        			addone(j);
        		}
        	}
        
       //从excle表格中复制的数据，最后一行为空行，因此无需对len数组中最后的元素进行处理     
      for(var i=0;i<len.length-1;i++){
               //excel表格同一行的多个cell是以空格 分割的，此处以空格为单位对字符串做 拆分操作。。     
                trStr = len[i].split(/\s+/); 
           
               var temleng = (fieldarrlen.length-1);
               if(trStr.length>(fieldarrlen.length-1)){
            	   temleng = (fieldarrlen.length-1);
               }else if(trStr.length<(fieldarrlen.length-1)){
            	   temleng = trStr.length;
               }
               for(var j=0;j<temleng;j++){//将excel中的一行数据存放在table中的一行cell中    
            	   $j("#"+xindex+"_"+fieldarrlen[j]).val(trStr[j]);
            	   $j("#"+xindex+"_"+fieldarrlen[j]+"_jiluvalue").val(xindex+"_"+fieldarrlen[j]+":"+trStr[j]);
            	   if(j==0){
            		   var tmp = xindex+"_"+fieldarrlen[j]+"@"+trStr[j];
            	   }
               }
               xindex++;
            }   
         return false; //防止onpaste事件起泡 
      }
    
    
	

function ieSet(){
	
	var aa = document.getElementsByName("itemDataKey111");
	var ids = "";//获取所有的字段
	for(var i=0;i<aa.length;i++){
			var arra = aa[i].value.split(",");
			ids += arra+",";
			
	}
	//(ids.split(",GH,")[1]);//从特定的字符开始截取之后的
	var strs = $j(this).attr("name").split("_");
	var xindex = strs[0];//获取行号
	var xfield = strs[1];//获取当前字段
	ids = ","+ids;
	var fieldarr = xfield+","+(ids.split(","+xfield+",")[1]);
	//alert(fieldarr);
	var fieldarrlen = fieldarr.split(",");
	
	
	
    var str = window.clipboardData.getData("text"); //获取剪切板数据
    var reportTargetListlength = $j("#reportTargetListlength").val();
    var len = str.split("\n");//获取剪切板数据行数     
    var trStr;   
    	var tempflag = $j("#tempflag").val();//获取当前最大行号
    	if(tempflag<len.length){
    		var fleng = len.length-tempflag;
    		for(var j=2;j<=fleng;j++){
    			addone(j);
    		}
    		
    	}
    
   //从excle表格中复制的数据，最后一行为空行，因此无需对len数组中最后的元素进行处理     

  for(var i=0;i<len.length-1;i++){
           //excel表格同一行的多个cell是以空格 分割的，此处以空格为单位对字符串做 拆分操作。。     
            trStr = len[i].split(/\s+/); 
       
           var temleng = (fieldarrlen.length-1);
           if(trStr.length>(fieldarrlen.length-1)){
        	   temleng = fieldarrlen.length-1;
           }else if(trStr.length<(fieldarrlen.length-1)){
        	   temleng = trStr.length;
           }
           
           for(var j=0;j<temleng;j++){//将excel中的一行数据存放在table中的一行cell中    
        	   $j("#"+xindex+"_"+fieldarrlen[j]).val(trStr[j]);
        	   $j("#"+xindex+"_"+fieldarrlen[j]+"_jiluvalue").val(xindex+"_"+fieldarrlen[j]+":"+trStr[j]);
           }
           xindex++;
        }     
      return false; //防止onpaste事件起泡 

}




/* $j(document).ready(function(){
	for(var i=2;i<51;i++){
		(document.getElementById("tr_"+i)).style.display="none";
	}
	
}); */
	

/* function addOnecell(){
	var a = document.getElementsByTagName("tr");
	alert(a[1].value);
} */





	         function singleUpdate(obj,val,x,reportda,repid,organC,organI) {
				var valueurl="";
	        	var valuestr=document.getElementsByName(x+"_jilu");
	        	for(i=0;i<valuestr.length;i++){
	      	      	var aa=valuestr[i].value.split("_")[1];
	      	      	var arr=aa.split(":");
	      	      	if(!doRule(arr[0],arr[1])){					
							alert(getMessages());
							return false;
						}
	      	      	if(arr[0]=="CJRQ"){
	      	      		if(arr[1]!=""){
	      	      			valueurl+=arr[0]+":"+arr[1]+"@";
	      	      		}else{
	      	      			valueurl+=arr[0]+":"+($j("#dataDate").val()).replace("-","").substr(0,6)+"00@";
	      	      		}
	      	      	}else{
	      	      		valueurl+=arr[0]+":"+arr[1]+"@";
	      	      	}
	      	    }
	      	    
	        	
	        	var params={"stflag": val,"reportDate":reportda,"reportId":repid,"organCode":organC,"organId":organI,
	        			 "valueurl":valueurl,"method":"singleSaveOne1"};
	        	$j.ajax({
					type: "POST", 
					async: false, //ajax
					url: "dataFill.do", 
					data: $j.param(params),
					success: function(result){
						if(result==1){
							alert('<fmt:message key="view.sjtjsb"/>');
						}else{
						
							var tr=obj.parentNode.parentNode;
							var tbody=tr.parentNode;
							tbody.removeChild(tr);
							alert('<fmt:message key="view.sjtjcg"/>');
							
						}
					}
				});
	         }
	        
	      
	       
						 function showDiv(targetField) {
							 $j("#hint" + "<c:out value='${reportId}'/>_"+targetField).show();
							 $j("#hint" + "<c:out value='${reportId}'/>_"+targetField).css({
								"top" : rMenu.position.y + "px",
								"left" : rMenu.position.x + "px",
								"display" : "block"
							});
						} 
						
						var rMenu = {
								position : {
									x : "",
									y : ""
								}
						}; 
						



	         $j(function () {
				 //全局失去焦点  把值赋到隐藏域
				 $("body").on("blur","input[type='text']",function(){
					 var id=$j(this).attr("id");
					 var val=$j(this).val();
					 $j("#"+id+"_jiluvalue").val(id+":"+val);
				 })

			 })
						$j(function() {
							$j("input[readonly!='readonly']").each(function(){
		
								$j(this).unbind("mouseover");
								$j(this).bind("mouseover",function(event) {
											//alert("1");
												var e = event || window.event;
									            var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
									            var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
									            var x = e.pageX || e.clientX + scrollX;
									            var y = e.pageY || e.clientY + scrollY;
												rMenu.position.x = x;
												rMenu.position.y =  y;
												var strs = $j(this).attr("name").split("_");
												var targetField = strs[1];
												//showDiv(targetField);
										});
									
								$j(this).unbind("mouseout");
								$j(this).bind("mouseout",function(e) {


											var strs = $j(this).attr("name").split("_");
											var targetField = strs[0];
											
											$j("#hint" +"<c:out value='${reportId}'/>_"+ targetField).hide();
										});
								});
							$j("#refresh").unbind("click");
							$j("#refresh").bind("click",function(){
										window.location.href= "dataFill.do?method=editReportDataEntryForDataInput&openmode=_self&levelFlag=<c:out value='${levelFlag}'/>&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&page=<c:out value='${page.pageNo}'/>";
									});




							$j("select").each(function(){
								$j(this).unbind("mouseover");
								$j(this).bind("mouseover",function(event) {
												var e = event || window.event;
									            var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
									            var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
									            var x = e.pageX || e.clientX + scrollX;
									            var y = e.pageY || e.clientY + scrollY;
												rMenu.position.x = x;
												rMenu.position.y =  y;
												var strs = $j(this).attr("name").split("_");
												var targetField = strs[1];
												//showDiv(targetField);
											});
								$j(this).unbind("mouseout");
								$j(this).bind("mouseout",function(e) {
												var strs = $j(this).attr("name").split("_");
												var targetField = strs[1];
												$j("#hint" +"<c:out value='${reportId}'/>_"+ targetField).hide();
											});
								$j(this).blur(function(e){
												var id=$j(this).attr("id");
												var val=$j(this).val();
												$j("#"+id+"_jiluvalue").val(id+":"+val);


									/*

									这段代码  pflag pcheck这些字段找不到没起作用 注释
                                    var strs=$j(this).attr("id");
                                        var pflag=$j("#"+strs+"_pflag").val();
                                        var pcheck=$j("#"+strs+"_pcheck").val();  //
                                        var rcontent=$j("#"+strs+"_rvalue").val();//
                                        var rvalue=$j("#"+strs+"_value").val();  //
                                        if(pflag.indexOf("2")>=0){
                                            getrvalue(this,pcheck,newvalue,rcontent,"<c:out value='${dataDate}'/>" );  //
												}else{
													var rvalue=$("#"+strs+"_value").val();  //
													var bflag=checkAll(pcheck,newvalue,rvalue); //
													if(bflag ==true){
														$j("#"+strs+"_datatype").val(0);
														$j("#"+strs+"_jiludatatypes").val(strs+"_datatype:"+0);
														$j("#"+strs+"_jiludatas").val(0);
														$j(this).css("background", "#fff");
													}
												}*/
												
											});
									}); 
							
							$j("#exportData").unbind("click");
							$j("#exportData").bind("click",function() {
										<c:if test="${ empty repDataMap}">
										alert('<fmt:message key="view.myydcsj"/>');
							        	return false;
							        	</c:if>
										//window.location.href = "dataFill.do?method=exportReportData&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>";
										var url = "dataFill.do?method=exportReportData&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>";
										$j.post(url,'',function(data){
											if(data=="ok") {
												window.location.href = "dataFill.do?method=downloadExcel&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>";
											}
										});
									});
									
							$j("#importData").unbind("click");
									$("#importData").bind("click",function() {
									
										//window.location.href = "dataFill.do?method=exportReportData&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>";
										//var url = "standard/datafill/dataUpload.jsp";
										var url="dataFill.do?method=enteryRepUpLoad&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&levelFlag=<c:out value='${levelFlag}'/>";
										window.location.href = url;
									/* 	$.post(url,'',function(data){
											if(data=="ok") {
												window.location.href = "dataFill.do?method=downloadExcel&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>";
											}
										}); */
									});
								});
	
						 window.onload=function(){   
							    /****************************  
							     * ：q821424508@sina.com   *  
							     * ：2012-08-20            *  
							     * version：2.1              *  
							     *                          *  
							     ****************************/  
							    document.getElementsByTagName("body")[0].onkeydown =function(){   
							           
							        //huo de shi jian dui xiang   
							        var elem = event.relatedTarget || event.srcElement || event.target ||event.currentTarget;    
							           
							        if(event.keyCode==8){//pan duan an jian wei [backSpace]jian   
							           
							                //huo qu an jian an xia shi fuang biao zuo zhi xiang de [element]   
							                var elem = event.srcElement || event.currentTarget;    
							                   
							                //pan duan shi fou xu yao zu zhi an xia jian pan de shi jian mo ren chuan di   
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
							        };   
							    } ;  
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

</html>
