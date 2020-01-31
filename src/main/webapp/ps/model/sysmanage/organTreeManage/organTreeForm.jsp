<!-- /organTreeManage/organTreeForm.j -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<%@ page import="java.lang.String" %>
<c:set var="colName">
	<fmt:message key="organTree.colName"/>
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}"/><c:url value='/organTreeAction.do?method=tree'/>
</c:set>
<c:set var="orgButton">
	<fmt:message key="organTree.area"/>
</c:set>

<html>
<head>
	<title><fmt:message key="webapp.prefix"/></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/style/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
 <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>  
	<link rel="stylesheet" type="text/css" href="<c:url value='/ps/uitl/krmtree/tafelTree/css/tree.css' />" />
	<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/tafelTree/js/prototype.js' />"></script>
	<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/tafelTree/js/scriptaculous.js' />"></script>
	<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/tafelTree/Tree-optimized.js' />"></script>
	<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>
	
	<script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>

	<script language="JavaScript" type="text/JavaScript">  
		var $j = jQuery.noConflict();
	</script>
	<script type="text/javascript">

		$j(function () {
			backspaceForbiddon();
		})

		function backspaceForbiddon(){
			$j.post("organTreeAction.do?method=tree",function(data){
				var treeXml = eval(data)[0].treeXml;
				$j('#orgTree').combotree('loadData', treeXml);
			});
		}
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

	 
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" >
	<div class="navbar">
		<table>
		<tr>
			<td>
			<a href="javascript:backOrganList();" class="easyui-linkbutton buttonclass2"  ><fmt:message key="organTree.back"/></a>
				
			</td>
			<td>
				<a href="javascript:saveOrganTree();" class="easyui-linkbutton buttonclass2"   data-options="iconCls:'icon-save'"  ><fmt:message key='organTree.saveOrganTree'/></a>
			</td>
		</tr>
	</table>
	</div>
		<br><br/>
			<form name="organTreeAction" action="" method="post">
				<table width="699px" border="0" align="center" style="margin: 0 auto;"  cellSpacing="1" cellPadding="0" class="TableBGColor">
    				<tr>
      					<td height="34" colspan="2" class="TdBgColor1">
      					<fmt:message key="organTree.organTreeName"/>
							<input type="text" type="hidden" name="name" size="25" value="<c:out value="${organTreeForm.name }"/>"/>
						</td>
      					<td width="9"  class="TdBgColor1">&nbsp;</td>
      					<td width="324" class="TdBgColor1">
      						 <input  type="hidden" name="areaId" value="<c:out value="${areaCode }"/>"/>
      						<input  type="hidden" name="areaName"/> 
    						<input id="orgTree" class="easyui-combotree"  value="<c:out value='${areaCode}'/>" onchange="changeTree1();"  style="width:260"/>
    					</td>
    				</tr>
    				<tr>
      					<td width="193" height="31" class="TdBgColor1">
      						<fmt:message key="organTree.beginDate"/>
							<%-- <input type="text" name="beginDate" value="<c:out value="${organTreeForm.beginDate }"/>" readonly="true" size="8"/> --%>
							<input  name="beginDate" type="text" 
									value="<c:out value='${organTreeForm.beginDate}'/>" 
									style="width:150;" readonly="true" onClick="WdatePicker()"/>
							
						</td>
      					<td width="168" class="TdBgColor1">
      						<fmt:message key="organTree.endDate"/>
							<%-- <input type="text" name="endDate" value="<c:out value="${organTreeForm.endDate }"/>" readonly="true" size="8"/> --%>
							<input  name="endDate" type="text" 
									value="<c:out value='${organTreeForm.endDate}'/>" 
									style="width:150;" readonly="true" onClick="WdatePicker()"/>
						</td>
      					<td class="TdBgColor1">&nbsp;</td>
      					<td class="TdBgColor1">
      						<div id="organTypes1"></div>
      					</td>
    				</tr>
    				<tr>
      					<td height="24" class="TdBgColor1" width="193">
      						<fmt:message key="organTree.group1"/>
      						&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="organTree.group2"/>
      						<div id="groups">
      						<select name="group" id="group" style="width:85px">
								<option value="0"><fmt:message key="organTree.defaultGroup"/></option>
							</select>
							</div>
						</td>
      					<td class="TdBgColor1">
      						<fmt:message key="organTree.isPublic"/>
							<select name="isPublic" id="isPublic" style="width:85px">
								<c:if test="${ isAdmin == '1'}">
									<c:if test="${organTreeForm.isPublic == '0'}">
										<option value="0" selected><fmt:message key="organTree.public"/></option>
									</c:if>
									<c:if test="${organTreeForm.isPublic != '0'}">
										<option value="0"><fmt:message key="organTree.public"/></option>
									</c:if>
								</c:if>
								<c:if test="${organTreeForm.isPublic == '1'}">
									<option value="1" selected><fmt:message key="organTree.groupPublic"/></option>
								</c:if>
								<c:if test="${organTreeForm.isPublic != '1'}">
									<option value="1"><fmt:message key="organTree.groupPublic"/></option>
								</c:if>
								<c:if test="${organTreeForm.isPublic == '2'}">
									<option value="2" selected><fmt:message key="organTree.onlyMinePublic"/></option>
								</c:if>
								<c:if test="${organTreeForm.isPublic != '2'}">
									<option value="2"><fmt:message key="organTree.onlyMinePublic"/></option>
								</c:if>
							</select>
						</td>
      					<td class="TdBgColor1">&nbsp;</td>
      					<td class="TdBgColor1">
      						<div id="organTypes2"></div>
      					</td>
    				</tr>
    				<tr>
      					<td height="29" colspan="2" class="TdBgColor1" align="center">
      						<a href="#" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-remove'" onClick="removeSelected();"><fmt:message key='organTree.removeNode'/></a>
      						<a href="#" class="easyui-linkbutton buttonclass2"  onClick="hasMoveUp();"><fmt:message key="organTree.moveUp"/></a>
      						<a href="#" class="easyui-linkbutton buttonclass2"  onClick="hasMoveDown();"><fmt:message key="organTree.moveDown"/></a>
      						<!--  <input type="button" name="removeNode" value="<fmt:message key='organTree.removeNode'/>" onClick="removeSelected();" />&nbsp;
							<input type="button" name="moveUp" value="<fmt:message key='organTree.moveUp'/>" onClick="hasMoveUp();" />&nbsp;
	      					<input type="button" name="moveDown" value="<fmt:message key='organTree.moveDown'/>" onClick="hasMoveDown();" />-->
						</td>
      					<td class="TdBgColor1">&nbsp;</td>
      					<td class="TdBgColor1" align="center">
      						<a href="#" class="easyui-linkbutton buttonclass2"  onClick="addSelectNode();" data-options="iconCls:'icon-add'"><fmt:message key='organTree.addNode'/></a>
      						<a href="#" class="easyui-linkbutton buttonclass2"  onClick="selectAll();"><fmt:message key="organTree.selectAll"/></a>
      						<a href="#" class="easyui-linkbutton buttonclass2"  onClick="unSelectAll();"><fmt:message key="organTree.unSelectAll"/></a>
      						<!-- 				
      						<input type="button" name="addNode" value="<fmt:message key='organTree.addNode'/>" onClick="addSelectNode();" />&nbsp;
							<input type="button" name="select" value="<fmt:message key='organTree.selectAll'/>" onClick="selectAll();" />&nbsp;
			      			<input type="button" name="unSelect" value="<fmt:message key='organTree.unSelectAll'/>" onClick="unSelectAll();" />-->
						</td>
    				</tr>
    					<tr>
      					<td bgcolor="#FFFFFF" colspan="2" align="center" valign="top">
      						<div id="organTree"></div>
      					</td>
      					<td class="TdBgColor1">&nbsp;</td>
      					<td class="TdBgColor1" valign="top" >
      						<div id="org">
      						<select name="organList" id="organList" size="18" style="width:320px" multiple>
      						</select>
	      					</div>
	      					<div id="organs" style="display:none"></div>
	      				</td>
    				</tr>
    				<tr>
					    <td height="19" class="TdBgColor1" colspan="2">&nbsp;</td>
					    <td class="TdBgColor1">&nbsp;</td>
					    <td class="TdBgColor1">&nbsp;</td>
					</tr>
  				</table>
  				<input type="hidden" name="id" value="<c:out value="${organTreeForm.id}"/>"/>
  				<input type="hidden" name="creatorId" value="<c:out value="${organTreeForm.creatorId}"/>"/>
  				<input type="hidden" name="json" value="<c:out value="${organTreeForm.json}"/>"/>
  				<input type="hidden" name="organSystemId" value="<c:out value="${organSystemId}"/>"/>
  				<input type="hidden" name="organsId" value="<c:out value="${organTreeForm.organsId}"/>"/>
			</form>

</body>
</html>
<script type="text/javascript">
	function TafelTreeInit () {
		if(newOrEdit == '1'){
			struct = <c:out value="${myJson}" escapeXml="false" />;
			initializeTree ('organTree',struct);
			var branch = tree.getBranchById("root");
			//alert(branch.getId()+"---"+branch.getText());
			if(branch.hasChildren()){
				//alert("hasChildren");
				var children = branch.getChildren();
				for(var i = 0; i < children.length; i++){
					children[i].closeSiblings();
				}
			}
		}
	}

	function initializeTree (name,treeStruct) {
		tree = new TafelTree(name, treeStruct, {
			'generate' : true,
			'imgBase' : 'ps/uitl/krmtree/tafelTree/imgs/',
			'defaultImg' : 'page.gif',
			'defaultImgOpen' : 'folderopen.gif',
			'defaultImgClose' : 'folder.gif',
			//'onDrop' : function(){return false;},
			'copyDrag' : true,
			'behaviourDrop' : 'sibling',
			'dropALT':false,
			'openAtLoad':true,
			'cookies':true,
			'multiline':true,
			'rtlMode':false,
			'behaviourDrop':'child',
			'dropCTRL' : false
		});
	}
//全局变量
var organTreeForm=document.organTreeAction;
var organSelect=organTreeForm.organList;
var areaId;
var allAreaOrgans = new Array();
var areaOrgansOfLength = 0;
var struct = "";
	//if newOrEdit == 0 is new Tree or edit Tree
var newOrEdit = "<c:out value="${newOrEdit}"/>";
	//var loginDate = "<c:out value="${logindate}"/>";



$j(function () {
	//尽量使用局部变量
	var optionStr = "";
	var optionStr2 = "";
	var typeId = "<c:out value="${typeId}"/>";
	var typeName = "<c:out value="${typeName}"/>";
	var id = typeId.split(",");
//不能直接使用name 做变量  因为“name”是当前窗体：window对象的属性 直接使用会有奇怪的效果
	var nameS = typeName.split(",");
	var cnt = Math.round(nameS.length/2);
	for(var i = 0; i < cnt; i++){
		if(optionStr == ""){
			optionStr = '<input type="checkbox" name=\''+id[i]+'\' value=\''+id[i]+'\' checked onClick="filterOrganByType();">'+ nameS[i];
		}else{
			optionStr += '<input type="checkbox" name=\''+id[i]+'\' value=\''+id[i]+'\' checked onClick="filterOrganByType();">'+ nameS[i];
		}
	}
	for(var i = cnt; i < id.length; i++){
		if(optionStr2 == ""){
			optionStr2 = '<input type="checkbox" name=\''+id[i]+'\' value=\''+id[i]+'\' checked onClick="filterOrganByType();">'+ nameS[i];
		}else{
			optionStr2 += '<input type="checkbox" name=\''+id[i]+'\' value=\''+id[i]+'\' checked onClick="filterOrganByType();">'+ nameS[i];
		}
	}

	document.all.organTypes1.innerHTML = optionStr;
	document.all.organTypes2.innerHTML = optionStr2;
	var groupId = "<c:out value="${groupId}"/>";
	var groupName = "<c:out value="${groupName}"/>";
	var gId = groupId.split(",");
	var gName = groupName.split(",");
	var _groupSelectHead = '<select name="group" id="group"><option value="0" selected><fmt:message key="organTree.defaultGroup"/></option>';
	var _groupSelectLast = '</select>'
	var groupStr = "";
	for(var i = 0; i < gId.length; i++){
		groupStr += '<option value=\''+gId[i]+'\'>'+gName[i]+'</option>';
	}
	document.all.groups.innerHTML = _groupSelectHead+groupStr+_groupSelectLast;
	var selectGroup = "<c:out value="${organTreeForm.group}"/>";

	if(selectGroup == ""){
		document.organTreeAction.group.value = "0";
	}else{
		document.organTreeAction.group.value = selectGroup;
	}

	//初始化可选择机构
	fetchOrganList();

})




$j("#orgTree").combotree({
    onChange:function(){
    	changeTree1(); 
    }
});
//change areaTree run this function
function changeTree1(){
	var areaId = $j("#orgTree").combotree("getValue");
	if(allAreaOrgans[areaId]){
		filterOrganByType();
	}else{
		fetchOrganList1(areaId);
	}
}


function fetchOrganList1(areaId){
	//var options={onSuccess:recieveOrganList};
	var url='<c:out value="${hostPrefix}" /><c:url value="/organTreeAction.do" />?method=getOrganList&areaId='+areaId;
	$j.ajax({
		url : url,
		type : "post",
		success : function(data){
			recieveOrganList(data);
		},
		error : function(e){
			alert(e);
		}
	})



	//var url='<c:out value="${hostPrefix}" /><c:url value="/organTreeAction.do" />?method=getOrganList&areaId='+areaId;
	//pu = new Ajax.Updater($j("organs"),url,options);
}

function fetchOrganList(){

	var areaId = $j("#orgTree").val();
	//var options={onSuccess:recieveOrganList};
	var url='<c:out value="${hostPrefix}" /><c:url value="/organTreeAction.do" />?method=getOrganList&areaId='+areaId;
	$j.ajax({
		url : url,
		type : "post",
		success : function(data){
			recieveOrganList(data);
		},
		error : function(e){
			alert(e);
		}
	})
	//pu = new Ajax.Updater($j("organs"),url,options);
}

//function processRequest(request) {
function recieveOrganList(data) {
  var organInfo = data;
  var organsOfArea;
  eval('organsOfArea='+organInfo);

  allAreaOrgans[areaOrgansOfLength++] = allAreaOrgans[areaId] = organsOfArea;
  filterOrganByType();
}

//onClick "checkbox" is running this function filter organs
function filterOrganByType(){
	organSelect.options.length = 0;
	var inputTag = document.getElementsByTagName("input");
	var organsOfArea = allAreaOrgans[areaId];

	var optionsLen=0;
	//organList.options.length = organsOfArea.length;
	for(var i = 0; i < organsOfArea.length; i++){
		var organInfo = organsOfArea[i];

		for(var j = 0; j < inputTag.length; j++){
			var input = inputTag[j];

			if(input.getAttribute("type") == "checkbox" && $j(input).is(":checked")){
				if(input.getAttribute("value") == organInfo.type){
					organSelect.options.length = optionsLen;
					organSelect.options[optionsLen++] = new Option(organInfo.name,organInfo.id);
				}
			}
		}
	}
}

//onClick "save tree" button is running this function build JSON
function saveOrganTree(){
	var doc = organTreeForm;
	if(tree != null){
		var branch = tree.getBranchById("root");
		var organId = branch.struct.organId;
		var isSave = false;
		var found = false;
		for(var i = 0; i < areaOrgansOfLength; i++){
			var organsOfArea = allAreaOrgans[i];
				for(var j = 0; j < organsOfArea.length; j++){
				if(organId == organsOfArea[j].id){
					if(doc.beginDate.value >= organsOfArea[j].beginDate && doc.endDate.value <= organsOfArea[j].endDate){
						isSave = true;
					}
					found = true;
					break;
				}
			}
			if(found)break;
		}
		
		if(isSave){
			getChildren("root");
			getSpreadTree("root");
			//alert(organsId);
			organTreeForm.organsId.value = organsId;
			organsId = "";
			var organTree = document.getElementById("organTree");
			//eval('var v='+json);
			//alert(json);
			organTreeForm.json.value = json;
			if(organTreeForm.name.value == "" || organTreeForm.json.value == ""){
				alert("<fmt:message key="organTree.nameIsNull"/>");
				return;
			}else{
				doSave();
			}
		}else{
			alert("<fmt:message key="organTree.topNodeTimeRangs"/>");
			return;
		}
	}else{
		alert("<fmt:message key="organTree.treeIsNull"/>");
		return;
	}
}

var organsId = "";
function getSpreadTree(id){
	var branch = tree.getBranchById(id);
	if(branch.hasChildren()){
		var children = branch.getChildren();
		for(var i = 0; i < children.length; i++){
			if(children[i].getId() == "delete"){
				var parent = children[i].getParent();
				if(organsId == "")
					organsId += parent.struct.organId;
				else
					organsId += ","+parent.struct.organId;
			}else{
				getSpreadTree(children[i]);
			}
		}
	}
}

function getChildren(id){
	var branch = tree.getBranchById(id);
	json += "{'id':'"+branch.getId()+"','txt':'"+branch.getText()+"','organId':'"+branch.struct.organId+"','nodeId':'"+branch.struct.nodeId+"'";
		if(branch.hasChildren()){
			var children = branch.getChildren();
			json += ",'items':[";
			for(var i = 0; i < children.length; i++){
				getChildren(children[i]);
				if(i < children.length-1){
					json += ",";
				}
			}
			json += "]";
		}
	json += '}';
}

function doSave(){
	var organSystemId = organTreeForm.organSystemId.value;
  	organTreeForm.action = "organTreeAction.do?method=saveTree&organSystemId="+organSystemId;
	organTreeForm.submit();
}






// JavaScript Document
var tree = null;
var _tempId = null;
var organId_map = null;
var _arithmometer = 0;
var json = "";
var _leftParentheses = "{";
var _rightParentheses = "}";
var count = 0;

//ondblclick node event run this function
function myEdit (struct, newValue, oldValue) {
	var pattern = /.*[!|~|`|@|#|$|%|\^|&|\*|\(|\)|\-|\+|\=|\_|\'|\"|\?|\/|\.|,|\<|\>].*/;
	if(pattern.test(newValue)){
		alert("<fmt:message key="organTree.formatError"/>");
		return oldValue;
	}else{
		return newValue;
	}
}

//the treeNode onClick run this function
function myClick (struct, checked) {
	_tempId = struct.getId();
	
}

//button name is "addNode" for onClick event run this function
function addSelectNode(){
	if(tree == null){
		var organId = "";
		var organTxt = "";
		for(var b = 0; b < organSelect.options.length; b++){
			if(organSelect.options[b].selected){
				organId = organSelect.options[b].value;
				organTxt = organSelect.options[b].text;
				struct = [{"organId": organId,"txt":organTxt,"onedit":"myEdit","onclick":"myClick","id":"root","onopen":"myOpen"}];				
				initializeTree ('organTree',struct);
				break;
			}
		}
	}else{
		initOrganIdList("root");
		var branch = tree.getBranchById(_tempId);
		if(branch == null || branch==""){
			alert("<fmt:message key="organTree.needCheckTargetNode"/>");
		}else{
			for(var i = 0; i < organSelect.options.length; i++){
				if(organSelect.options[i].selected){
					if(!organId_map["a"+organSelect.options[i].value] == true){
						var item = {
						"id" : "_node"+_arithmometer++,
						"txt" : organSelect.options[i].text,
						"onclick":"myClick",
						"onedit":"myEdit",
						"organId":organSelect.options[i].value,
						"onopen":"myOpen"
						}
						var newBranch = branch.insertIntoLast(item);
						organId_map["a"+organSelect.options[i].value] = true;
					}else{
						alert("<fmt:message key="organTree.nodeExist"/>");
					}
				}
			}
		}
	}
	_tempId = null;
}

//addSelectNode function first running is run this function
function initOrganIdList(id){
	if(organId_map == null){
		organId_map = new Object();
		iterateTreeForInitTree(id);
	}
}

//recurrence Tree
function iterateTreeForInitTree(id){
	var branch = tree.getBranchById(id);
	organId_map["a"+branch.struct.organId] = true;
	if(branch.hasChildren()){
		var children = branch.getChildren();
		for(var i = 0; i < children.length; i++){
			iterateTreeForInitTree(children[i]);
		}
	}
}
function iterateTreeForRemoveNode(id){
	var branch = tree.getBranchById(id);
	organId_map["a"+branch.struct.organId] = null;
	if(branch.hasChildren()){
		var children = branch.getChildren();
		for(var i = 0; i < children.length; i++){
			iterateTreeForRemoveNode(children[i]);
		}
	}
}
//button name is "removeNode" for onClick event run this function
function removeSelected(){
	//alert(_tempId);
	if(organId_map != null){
		iterateTreeForRemoveNode(_tempId);
	}
	tree.removeBranch(_tempId);
	if(_tempId == "root"){
		tree = null;
		organId_map = null;
	}
	_tempId = null;
}

//button name is "selectAll" for onClick event run this function
function selectAll(){
	for(var i = 0; i < organSelect.options.length; i++){
		if(i>99){
			break;
		}
		organSelect.options[i].selected = true;
	}
}

//button name is "unSelectAll" for onClick event run this function
function unSelectAll(){
	for(var i = 0; i < organSelect.options.length; i++){
		organSelect.options[i].selected = false;
	}
}

//button name is "moveUp" for onClick event run this function
function hasMoveUp(){
	var branch = tree.getBranchById(_tempId);
	var sibling = branch.getPreviousSibling();
	if(sibling != null){
		branch.moveBefore(sibling);
	}
	_tempId = null;
}

//button name is "moveDown" for onClick event run this function
function hasMoveDown(){
	var branch = tree.getBranchById(_tempId);
	var sibling = branch.getNextSibling();
	if(sibling != null){
		branch.moveAfter(sibling);
	}
	_tempId = null;
}

//onClick back button run this function
function backOrganList(){
	organTreeForm.action = "organTreeAction.do?method=list";
	organTreeForm.submit();
}

function myOpen(branch, opened){
	//alert(branch.getId());
	//alert(branch.isOpened());
	var organSystemId = organTreeForm.organSystemId.value;
	var children = branch.getChildren();
	for(var i = 0; i < children.length; i++){
		children[i].closeSiblings();
	}
	if(branch.isOpened()){
		var branchId = branch.struct.organId;
		_tempId = branch.getId();
		getSubChild(organSystemId,branchId);
	}else{
		
	}
}
function getSubChild(treeId,organId){
	var options={onSuccess:processRequest1}
	var url='<c:out value="${hostPrefix}" /><c:url value="/organTreeAction.do" />?method=getSubChild&treeId='+treeId+'&organId='+organId;
	pu = new Ajax.Updater($j("organs"),url,options);
}
function processRequest1(request) {
	var subBranch = request.responseText;
	var json;
	eval('json='+subBranch);
	var branch = tree.getBranchById(_tempId);
	var children = branch.getChildren();
	if(children.length == 1){
		if(children[0].getId() == "delete"){
			branch.removeChildren();
			for(var i = 0; i < json.length; i++){
				var newBranch = branch.insertIntoLast(json[i]);
			}
		}
	}
}


</script>
