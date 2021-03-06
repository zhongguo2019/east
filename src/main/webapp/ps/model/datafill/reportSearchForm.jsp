<!-- /ps/model/datafill/reportSearchForm. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<%
   User user = (User)request.getSession().getAttribute("user");
   String orgId = user.getOrganId();   
 %>

<c:set var="flagOrgan" value="1"/>
<c:set var="flagReport" value="1"/>
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
     
	 <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
	 <script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}

$(document).ready(function(){
	var flagOrgan = <c:out value='${flagOrgan}' /> ;
	var flagReport = <c:out value='${flagReport}' /> ;
	if(flagOrgan==2){
		$("#flagOrgan1").remove();
	}else{
		$("#flagOrgan2").remove();
	} 
	if(flagReport==2){//1单选，2多选
		$("#flagReport1").remove();
	}else{
		$("#flagReport2").remove();
	} 
	  getOrganTreeXML();
	  getrepTreeXML();
});  

 function getOrganTreeXML(){//机构树
	 $.post("treeAction.do?method=getOrganTreeXML",function(data){
			var treeXml = eval(data)[0].treeXml;
			$('#combotreeOrganTree').combotree('loadData', treeXml);
		}); 
 }
 
 function getrepTreeXML(){//报表树
	 $.post("reportView.do?method=repTreeAjax&levelFlag=0",function(data){
			var reportXml = eval(data)[0].reportXml;
			$('#combotreeRepTree').combotree('loadData', reportXml);
		}); 
 }

</script>
	 
</head>
<body onclick="suggestclose()" onload=" forbiddon()" class="easyui-layout">
<div data-options="region:'center',border:false,title:' <fmt:message key="navigation.dataFill.dataFill"/> '" style="width:100%;padding:0px;overflow-y:hidden ">
<div class="pbox">
<fmt:message key="report.tips1"/>
</div>
 

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%"  valign="top">
   <br>
	<html:form action="reportView.do?method=inputExtraCondition" method="post" onsubmit="return reportCheck()" >
		<table width="90%" border="0"  cellSpacing="1" cellPadding="3" class="TableBGColor">
		<input type="hidden" name="reportId" id="reportId" value="10000"/>
		<input type="hidden" name="reportName"/>
		<input type="hidden" name="organId" value="<c:out value='${organId}' />"/>
		<input type="hidden" name="organName"/>
		<input type="hidden" name="unitCode" value="<c:out value='${unitCode}' />"/>
		<c:if test="${param.method=='enterViewSearch'}">
            <input type="hidden" name="mode" value="display"/>
		</c:if>
       	<c:if test="${param.method=='enterInputSearch'}">
	        <input type="hidden" name="mode" value="edit"/>
		</c:if>
		<tr>
				<td width="120" align="right" nowrap >
					<fmt:message key="reportview.form.day"/><fmt:message key="reportview.form.qi"/>
				</td>
				<td>
					<input type="hidden" name="showret" value="yes">
					  <input id="dataDate" name="dataDate" type="text" 
									value="<c:out value='${dataDate}'/>" 
									style="width:150;" readonly="true" onClick="WdatePicker()"/>
				</td>
		  </tr>
			<tr>
				<td   align="right"><fmt:message key="select.organ"/></td>
				<td>
				<div id="flagOrgan1">
					<input id="combotreeOrganTree" class="easyui-combotree"  value="<%=orgId %>"  style="width:380px"/>
				</div>
				<div id="flagOrgan2">
					<input id="combotreeOrganTree" class="easyui-combotree" multiple value="<%=orgId %>"  style="width:380px"/>
				</div>
				</td>
			</tr>	
			<tr>
				<td align="right" nowrap ><fmt:message key="standard.dataFill.templateName"/></td>
				<td >
				<div id="flagReport1">
					<input id="combotreeRepTree" class="easyui-combotree"  value="10000"   style="width:380px"/>
				</div>
				<div id="flagReport2">
					<input id="combotreeRepTree" class="easyui-combotree" value="10000" multiple style="width:380px">
				</div>	
				</td>
			</tr>
	<c:if test="${darkSearchButton == 'yes'}">		
		<tr>
			<td align="right" nowrap ></td>
			<td >
			<a href="#" class="easyui-linkbutton"  onclick="darkSearch();"><fmt:message key="reportview.darksearch"/></a>
				<!--  <input type="button" value=<fmt:message key="reportview.darksearch"/>  onclick="darkSearch();"/>-->
			</td>
		</tr>
	</c:if>	
	<tr id="darkSearch" style="display:none">
			<td  colspan="2">
		<div  name="darkSearch" >
			<table width="100%" border="0" cellSpacing="1" cellPadding="2" >
			   <c:if test="${darkSearchButton == 'yes'}">	
			    <tr>
			        <td  align="right" width="10%"> <fmt:message key="databuild.form.reporttpe"/>
			        </td>
			        <td >
			        	 <html:select property="reportType" size="1" onchange="changeType();" style="width:380px;">
			            	<c:forEach var="type" items="${reportTypes}">
        						<html-el:option value="${type.pkid}">
       								<c:out value="${type.name}"/>
								</html-el:option>
							</c:forEach>
			            </html:select>
			        </td>
			    </tr>				
	    		</c:if>	
			    <tr>
			  	  <td   width="25%" align="right"><fmt:message key="reportdefine.report.frequency"/></td>
			      <td >
			    	<div id="freq">
						<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency1' value='1' /><fmt:message key="common.list.frequency1"/>		    	
					</div>
			      </td>
			    </tr>
			    
			    <tr>
			  	  <td    align="right"><fmt:message key="reportdefine.report.name"/></td>
			      <td   >
			      
			      
					<input name="txt1" type="text" id="txt1" onFocus="select()" value="" size="58"  autocomplete="off"  
					  onkeyup="catchKeyBoard(event,'txt1','suggestName');"
					  />
			      </td>
			    </tr>

			    <tr >
					<td  align="right"></td>
                    <td align="left" ><div id="suggestName"></div></td>
                    <td></td>
                  </tr>	
                  
			    <tr>
			  	  <td    align="right"><fmt:message key="reportdefine.report.code"/></td>
			      <td  >
					<input name="txt2" type="text" id="txt2" onFocus="select()" value="" size="58"  autocomplete="off"  
					  onkeyup="catchKeyBoard(event,'txt2','suggestCode');"
					  />
			      </td>
			    </tr>
		    
                  <tr>
					<td   align="right"></td>
                    <td align="left"><div id="suggestCode"></div></td>
                    <td></td>
                  </tr>	
              </table>	 
              </div>                 		    
		</td>
	</tr>
		
			
			<c:if test="${unitCodeFlag == 1}">
			<tr>
				<td align="right" nowrap ><fmt:message key="select.unit"/></td>
				<td >
				<select id="selUnit" style="width:380px">
					<c:forEach var="unitInfo" items="${viewUnitList}">
						<c:if test="${unitInfo.code == unitCode}">
							<option value="<c:out value='${unitInfo.code}'/>" selected><c:out value="${unitInfo.name}"/></option>
						</c:if>
						<c:if test="${unitInfo.code != unitCode}">
							<option value="<c:out value='${unitInfo.code}'/>"><c:out value="${unitInfo.name}"/></option>
						</c:if>
					</c:forEach>
				</select>
				</td>
			</tr>
			</c:if>
			<tr>
				<td  align="right" nowrap ></td>
				<c:if test="${canEdit == '1'}">
					<td >
					<a href="#" class="easyui-linkbutton c6" data-options="iconCls:'icon-view'"  title="<fmt:message key="standard.dataEdit.editData"/>"  onclick="viewReport('_self','yes')"><fmt:message key="standard.dataEdit.editData"/></a>
					<a href="#" class="easyui-linkbutton c6" data-options="iconCls:'icon-exp'"   title="<fmt:message key="standard.dataFill.exportData1"/>" onclick="exportData();"><fmt:message key="standard.dataFill.exportData1"/></a>
					<!-- <input type="button" value="<fmt:message key="standard.dataEdit.editData"/>" style="width:80;" onclick="viewReport('_self','yes')">
					<input type="button" value=<fmt:message key="standard.dataFill.exportData1"/>  onclick="exportData();"/>
					 -->	
					</td>
				</c:if>
				<c:if test="${canEdit == '0'}">
					<td >
					<a href="#" class="easyui-linkbutton c6"  onclick="viewReport('_self','yes');"><fmt:message key="reportview.button.look"/></a>
					<!-- <input type="button" value="<fmt:message key="reportview.button.look"/>" style="width:80;" onclick="viewReport('_self','yes')"> --></td>
				</c:if>
			</tr>
			<tr>
				<td width="50" height="16" colspan="2" nowrap ></td>
			</tr>			
		</table>
	</html:form>
	<div id="users" style="display:none"></div>	
</td>
</tr>

</table>
</div>
</body>

<script type="text/javascript">

var df=document.reportViewForm

<c:if test="${reportBalance!=null}">
	alert("<c:out value='${reportBalance}'/>");
</c:if>

<c:if test="${viewResult == '1'}">
	alert("<fmt:message key="reportview.alert.noformat"/>");
</c:if>


function viewReport(tg,sr){
	var vReportId = $("#combotreeRepTree").combotree("getValue");
	var organCode = $("#combotreeOrganTree").combotree("getValue");
	 if(vReportId==10000||vReportId==""||vReportId == null)
     {
      alert('<fmt:message key="js.validate.modeltarget"/>!');
      return false;
     }
    else if(vReportId>10000)
     {
     alert('<fmt:message key="place.select"/>'+document.getElementById('reportName').value+'<fmt:message key="view.xzjtmb"/>!');
     return false;
     }
	
	
	df.action="dataFill.do?method=editReportDataEntryForDataInput&openmode="+tg+"&canEdit=<c:out value='${canEdit}'/>&levelFlag=0&reportId="+vReportId+"&organCode="+organCode;
	<c:if test="${unitCodeFlag == 1}">
	setUnitCode();
	</c:if>
	if(tg=="_blank"){
		var viewOrganId = $("#combotreeOrganTree").combotree("getValue");
		var viewDataDate = df.dataDate.value;
		var viewReportId = $("#combotreeRepTree").combotree("getValue");
		tg = viewReportId+viewDataDate+viewDataDate+"_window";
	}
	df.target=tg;
	df.showret.value=sr;
	//alert("dataFill.do?method=editReportDataEntryForDataInput&openmode="+tg+"&canEdit=<c:out value='${canEdit}'/>&levelFlag=0&reportId="+vReportId+"&organCode="+organCode);
	df.submit();
}

function exportData(){
	/* oReportId = df.reportId;
	vReportId = oReportId.value; */
	var vReportId = $("#combotreeRepTree").combotree("getValue");
	 if(vReportId==10000||vReportId==""||vReportId == null)
     {
      alert('<fmt:message key="js.validate.modeltarget"/>!');
      return false;
     }
    else if(vReportId>10000)
     {
     alert('<fmt:message key="place.select"/>'+document.getElementById('reportName').value+'<fmt:message key="view.xzjtmb"/>!');
     return false;
     }
	 	/* var viewOrganId = df.organId.value; */
	 	var viewOrganId = $("#combotreeOrganTree").combotree("getValue");
		var viewDataDate = df.dataDate.value;
		/* var viewReportId = df.reportId.value; */
		var viewReportId = $("#combotreeRepTree").combotree("getValue");
	df.action = "dataFill.do?method=exportTemplate&dataDate="+viewDataDate+"&reportId="+vReportId+"&organId="+viewOrganId;
	df.submit();
	//url= "dataFill.do?method=exportTemplate&dataDate="+viewDataDate+"&reportId="+vReportId+"&organId="+viewOrganId;
	//alert(url);
}

function setUnitCode(){
	var listObj = document.all.selUnit;
	document.all.unitCode.value = listObj.options[listObj.selectedIndex].value;
}

function repTree(){
    var oDate = document.reportViewForm.dataDate;
	var vDate = oDate.value;
	var oOrganId = document.reportViewForm.organId;
	var vOrganId = oOrganId.value;	
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId+"&levelFlag=0";
	
	if (window.XMLHttpRequest){
		req = new XMLHttpRequest();
	}else if (window.ActiveXObject){
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (req){
		req.open("GET", url, true);
		req.send();
	}	
}

function changeOrg(){
    var oDate = document.reportViewForm.dataDate;
	var vDate = oDate.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/treeAction.do?method=getOrganTreeXML&date="+vDate;

	//1104 system use url:slsint...
	//var url = "<c:out value="${hostPrefix}"/>/slsint/reportView.do?method=orgTree&orgDate="+vDate;
	//bill exchange system use url: slspj...
	//var url = "<c:out value="${hostPrefix}"/>/slspj/reportView.do?method=orgTree&orgDate="+vDate;

	document.all.objTree1.SetXMLPath(url);
   	document.all.objTree1.Refresh();	
}


function changeRep(){
    var oDate = document.reportViewForm.dataDate;
	var vDate = oDate.value;
	var oOrganId = document.reportViewForm.organId;
	var vOrganId = oOrganId.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId+"&levelFlag=0";	
	<c:if test="${systemName=='1104'}">
		var url = "<c:out value="${hostPrefix}"/>/slsint/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId+"&levelFlag=0";
	</c:if>
	<c:if test="${systemName=='billExchange'}">
		var url = "<c:out value="${hostPrefix}"/>/slspj/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId+"&levelFlag=0";
	</c:if>
	document.all.objTree2.SetXMLPath(url);
   	document.all.objTree2.Refresh();	
}

function reportCheck(){
	/* oReportId = df.reportId;
    vReportId = oReportId.value; */
    var vReportId = $("#combotreeRepTree").combotree("getValue");
	if (vReportId==""||vReportId == null){
       alert("<fmt:message key="js.validate.report"/>");
       return false;
	}
	if (vReportId >= 10000){
		return false;
	}
}

function change(){
	changeOrg();
	changeRep();
	setReportList();
}

function changeTree1(){
	changeRep();
}

function changeTree2(){
}

function setReportUnit(){
	<c:if test="${unitCodeFlag == 1}">
	var ajaxOption = {method:'get',onSuccess:setReportUnitResponse};
	var url = "<c:out value='${hostPrefix}'/><c:url value='/reportView.do?method=getReportUnit&reportID='/>" + document.all.objTree2.getCurrentItem();
	var doAjax = new Ajax.Request(url,ajaxOption);
	</c:if>
}

function setReportUnitResponse(ajaxRequest){
	var unitCode = ajaxRequest.responseText;
	if (unitCode == "0"){
		return;
	}
	var selObj = document.all.selUnit;
	for (var i = 0 ; i < selObj.options.length ; i++){
		if (selObj.options[i].value == unitCode){
			selObj.selectedIndex = i;
			break;
		}
	}
}

/* var oldHide=Calendar.prototype.hide;
Calendar.prototype.hide = function () {oldHide.apply(this);	change()} */
</script>

<script type="text/javascript">
	changeDate();

function changeDate(){
	var jdate = document.getElementById("dataDate").value;
	jdate = jdate.substring(5,7);
	setCheckbox(jdate);
}

function setCheckbox(month){
	var str = "";
	if(month == "03" || month == "09"){
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency1' value='1' ><fmt:message key='common.list.frequency1'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency2' value='2' ><fmt:message key='common.list.frequency2'/>";
	}else if(month == "06"){
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency1' value='1' ><fmt:message key='common.list.frequency1'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency2' value='2' ><fmt:message key='common.list.frequency2'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency3' value='3' ><fmt:message key='common.list.frequency3'/>";
	}else if(month == "12"){
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency1' value='1' ><fmt:message key='common.list.frequency1'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency2' value='2' ><fmt:message key='common.list.frequency2'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency3' value='3' ><fmt:message key='common.list.frequency3'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency4' value='4' ><fmt:message key='common.list.frequency4'/>";
	}else{
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency1' value='1' ><fmt:message key='common.list.frequency1'/>";
	}

	str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency5' value='5' ><fmt:message key='common.list.frequency5'/>";
	
	var div = document.getElementById("freq");
	div.innerHTML = str;
}

</script>

<script language='javascript'><!--

	var arrreport = new Array();
	<c:forEach var="report" items="${reportList}" varStatus="status">
		arrreport[<c:out value='${status.index}'/>] = new Array("<c:out value='${report.pkid}'/>",
		"<c:out value='${report.name}'/>",
		"<c:out value='${report.code}'/>",
		"<c:out value='${report.reportType}'/>",		
		"<c:out value='${report.frequency}'/>")
	</c:forEach>	

	var arr1 = new Array();
	<c:forEach var="report" items="${reportList}" varStatus="status">
		arr1[<c:out value='${status.index}'/>] = new Array("<c:out value='${report.pkid}'/>",
		"<c:out value='${report.name}'/>",
		"<c:out value='${report.code}'/>",
		"<c:out value='${report.reportType}'/>",		
		"<c:out value='${report.frequency}'/>")
	</c:forEach>		
	

 function getPosition( obj )

     { 

     var top = 0,left = 0;

      do 

{

    top += obj.offsetTop;

        left += obj.offsetLeft;

     }

while ( obj = obj.offsetParent );      

      var arr = new Array();      

      arr[0] = top;

      arr[1] = left;       

      return arr;    

   }


function createMenu( textBox, menuid ){ 

       if( document.getElementById( menuid ) == null )

       {

         var left_new=getPosition( textBox )[1];

         var top_new=getPosition( textBox )[0];    

         var newControl = document.createElement("div"); //???      

         newControl.id = menuid;        

         document.body.appendChild( newControl );       

         newControl.style.position = "absolute"; 

         newControl.style.border = "solid 1px #000000";

         newControl.style.backgroundColor = "#FFFFFF";

        // newControl.style.width = textBox.clientWidth + "px"; //????

        // newControl.style.left = left_new + "px";           //??

         //newControl.style.top = (top_new + textBox.clientHeight + 2) + "px";  //????????2?????JS?????????    
         newControl.style.width = "360px"; //????

         newControl.style.left = "40px";           //??

         newControl.style.top =  "35px";  //????????2?????JS?????????   

//		var res = createMenuBody( document.getElementById(textBox).value);
		var res = createMenuBody(textBox);

		var newNode = document.createElement("p");

		newNode.innerHTML = res;

		newControl.appendChild(newNode);



        return newControl;

       } else {

		//document.all.suggest.style.display="block";

		document.getElementById(menuid).style.display="block";
		
		document.getElementById(menuid).innerHTML="";

//		var res = createMenuBody( document.getElementById(textBox).getAttribute("value"));
		var res = createMenuBody(textBox);

		var newNode1 = document.createElement("p");

		newNode1.innerHTML = res;

		document.getElementById(menuid).appendChild(newNode1);


			return document.getElementById( menuid );
	   }
    }

  function getSearchResult( textBox )
    {
    var key = document.getElementById(textBox).value;
	var arr = new Array();
    var k = 0;
    if(textBox == "txt1"){
    	for(i = 0; i < arr1.length; i++){
		if(arr1[i][1].indexOf(key) > -1 || arr1[i][1].indexOf(key.toUpperCase()) > -1  || arr1[i][1].indexOf(key.toLowerCase()) > -1){
			arr[k] = arr1[i];			
			k++;
		} else{
			continue;
		}	
	}
    }else{
    	for(i = 0; i < arr1.length; i++){
		if(arr1[i][2].indexOf(key) > -1 || arr1[i][2].indexOf(key.toUpperCase()) > -1  || arr1[i][2].indexOf(key.toLowerCase()) > -1){
			arr[k] = arr1[i];			
			k++;
		} else{
			continue;
		}	
	}
    }

 return arr;

}


  function createMenuBody( textBox )

    {

      var result = "";

      var j = 0;

      arr = getSearchResult( textBox ); //???????

       //????????

      if(arr.length > 10)

      {

        j = 10;

      }

      else

      {

        j = arr.length;

      }

	if(textBox == 'txt1'){
		for ( var i = 0; i < j; i++ ) {
			result += "<table border=\"0\" cellpadding=\"2\" cellspacing=\"0\" id=\"menuItem"+(i+1)+"\" onmouseover=\"forceMenuItem( "+(i+1)+");\" width=\"100%\" onclick=\"givNumberForMouse("+i+")\"><tr><td align=\"left\">" + arr[i][1] + "</td><td align=\"right\">" + (i+1) + " </td></tr></table>";
		}
	}else{
		for ( var i = 0; i < j; i++ ) {
			result += "<table border=\"0\" cellpadding=\"2\" cellspacing=\"0\" id=\"menuItem"+(i+1)+"\" onmouseover=\"forceMenuItem( "+(i+1)+");\" width=\"100%\" onclick=\"givNumberForMouse("+i+")\"><tr><td align=\"left\">" + arr[i][2] + "--" + arr[i][1] + "</td><td align=\"right\">" + (i+1) + " </td></tr></table>";
		}
	}
    return result;
 	}

var menuFocusIndex = 0;
function forceMenuItem(index){

//var menuFocusIndex = 0;

lastMenuItem = document.getElementById( "menuItem" + menuFocusIndex )

       if ( lastMenuItem != null )

       {

         //??????

         lastMenuItem.style.backgroundColor="white";       

         lastMenuItem.style.color="#000000";

       }

       var menuItem = document.getElementById( "menuItem" + index );

       if ( menuItem == null )

        {

          document.getElementById("txt1").focus(); 

        }

        else 

        {

         menuItem.style.backgroundColor = "#5555CC";

         menuItem.style.color = "#FFFFFF";

         menuFocusIndex = index;

      }

}


function forceMenuItem(index,textBox){

//var menuFocusIndex = 0;

lastMenuItem = document.getElementById( "menuItem" + menuFocusIndex )

       if ( lastMenuItem != null )

       {

         //??????

         lastMenuItem.style.backgroundColor="white";       

         lastMenuItem.style.color="#000000";

       }

       var menuItem = document.getElementById( "menuItem" + index );

       if ( menuItem == null )

        {
		if(textBox == "tet1"){
			document.getElementById("txt1").focus(); 
		}else{
			document.getElementById("txt2").focus(); 
		}

        }

        else 

        {

         menuItem.style.backgroundColor = "#5555CC";

         menuItem.style.color = "#FFFFFF";

         menuFocusIndex = index;

      }

}


function catchKeyBoard(e,textBox, menuid){

	var keyNumber = event.keyCode;
	var txtcontent = document.getElementById(textBox).value;
	if(keyNumber =='40'){ 
       if(menuFocusIndex == 10){
         return true;
       }else if (menuFocusIndex == null){     
           forceMenuItem( 1, textBox);
           givNumber( 0 );
       } else{
          forceMenuItem( menuFocusIndex+1, textBox); 
          givNumber(menuFocusIndex-1);
		}
	} else if(keyNumber == '38'){  
        if(menuFocusIndex == 1){
           forceMenuItem(menuFocusIndex-1, textBox); 
         } else{
          forceMenuItem(menuFocusIndex-1, textBox); 
          givNumber(menuFocusIndex-1);
        } 
   } else if(txtcontent == null || txtcontent == "" || keyNumber == '13' || keyNumber == '27'){
   		suggestclose();
   }else {
		createMenu(textBox, menuid); 
   } 
}

 function givNumber( index )

     {

       document.getElementById("txt1").value = arr[index][1];
       
       document.getElementById("txt2").value = arr[index][2];  
       
       document.getElementById("reportId").value = arr[index][0];

       document.getElementById("txtTree2").value = arr[index][1];       

       document.getElementById("txt1").focus();     
       
       changeRep();  
	   
	   //document.all.suggest.style.display="none";

     }

 function givNumberForMouse( index )

     {
     
       document.getElementById("txt1").value = arr[index][1];

       document.getElementById("txt2").value = arr[index][2];
       
       document.getElementById("reportId").value = arr[index][0];
       
       document.getElementById("txtTree2").value = arr[index][1];       

       document.getElementById("txt1").focus(); 

	   document.all.suggestName.style.display="none";
	   
	   document.all.suggestCode.style.display="none";	   
	   
	   
	   changeRep();
	   
//		document.getElementById("suggest").innerHTML="";

     }
     
    var typerep = new Array();
    
//	changeType();

	function changeType(){

		var type = document.forms[0].reportType.value;
	
		typerep = new Array();
		var j = 0;
		for(i=0; i< arrreport.length; i++){
			if(arrreport[i][3] == type){
				typerep[j] = arrreport[i];
				j++;
			}else if(type == 0){
				typerep[j] = arrreport[i];
				j++;
			}else {
				continue;
			}
		}
		checkfeq(this);
	}    

 var freqArr = new Array();
 function checkfeq(freq){
 	var freqs = $("#freq > input");
	var cnt = 0;
	freqArr = new Array();
	freqs.each(function(i){
		if(this.checked){
			freqArr[i] = this.value;
			cnt++;
		}
	});
	if(cnt == 0){
		alert("<fmt:message key="batch.checkdata.form.checkfreq"/>");
		freq.checked = true;
		freqArr[0] = freq.value;
 	}
	filterReport(freqArr);
 }	 
 
function filterReport(freqArr_){
	arr2 = new Array();
	var k = 0;
	for(i = 0; i < freqArr_.length; i++){
		for(j = 0; j < typerep.length; j++){
			if(freqArr_[i] == typerep[j][4]){
				arr2[k] = typerep[j];
				k++;
			}else{
				continue;
			}
		}
	
	}
	var m = 0;
	arr1 = new Array();
	for(i = 0; i < arr2.length; i++){
			arr1[m] = arr2[i];
			m++;
	}
	return arr1;
} 
 
 function setReportList(){
    var oDate = document.reportViewForm.dataDate;
	var vDate = oDate.value;
    var oOrganId = document.reportViewForm.organId;
	var vOrganId = oOrganId.value;	
	var options={onSuccess:setReportUnitResponse}
	var url='<c:out value="${hostPrefix}" /><c:url value="/reportView.do" />?method=getReportListAjax&paramDate='+vDate+"&paramOrgan=" + vOrganId;
	pu = new Ajax.Updater($("users"),url,options);
}

var allRep = new Array();
var repLen = 0;
function setReportUnitResponse(ajaxRequest){
	var struts = ajaxRequest.responseText;
	var reports = eval(struts);
	arr1 = new Array();
	arrreport = new Array();
	for(i = 0; i < reports.length; i++){
		arr1[i] = new Array(reports[i].id,reports[i].name,reports[i].code,reports[i].reportType,reports[i].frequency);
		arrreport[i] = new Array(reports[i].id,reports[i].name,reports[i].code,reports[i].reportType,reports[i].frequency);
	}
	changeType();
}

 function darkSearch(){
 var displayStyle = document.all.darkSearch.style.display;
 if(displayStyle == 'none'){
 	document.all.darkSearch.style.display = "block";
 }else{
 	document.all.darkSearch.style.display = "none";
 }
 }

 function suggestclose(){
  var suggestNameStyle = document.all.suggestName.style.display;
  var suggestCodeStyle = document.all.suggestCode.style.display;  
  if(suggestName != 'none' || suggestCodeStyle != 'none'){
 	document.all.suggestName.style.display = "none";
 	document.all.suggestCode.style.display = "none"; 
 	}
 }


</script>



</html>
