<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

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
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value='/ps/uitl/krmtree/tafelTree/css/tree.css' />" />
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" />
	<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/tafelTree/js/prototype.js' />"></script>
	<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/tafelTree/js/scriptaculous.js' />"></script>
	<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/tafelTree/Tree-optimized.js' />"></script>
	<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
	<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
	<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0"
	bgcolor="EEEEEE" onload="backspaceForbiddon();">
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
<table border="0" cellpadding="0" cellspacing="0" width="100%"
	height="100%">
	<tr>
		<td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19"
					height="36"></td>
				<td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" width="33"
					height="16"></td>
				<td>
				<p style="margin-top: 3"><font class=b><b><fmt:message
					key="organTree.title" /></b></font></p>
				</td>
				<td></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td width="100%" bgcolor="#EEEEEE" valign="top">
		<br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:saveOrganTree();">
		<fmt:message key="organTree.saveOrganTree"/></a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="javascript:backOrganList();"><fmt:message key="organTree.back"/></a>
		<br><br/>
			<form name="organTreeAction" action="" method="post">
				<table width="699" border="0" align="center" cellSpacing="1" cellPadding="0" class="TableBGColor">
    				<tr>
      					<td height="34" colspan="2" class="TdBgColor1">
      					<fmt:message key="organTree.organTreeName"/>
							<input type="text" name="name" size="25" value="<c:out value="${organTreeForm.name }"/>"/>
						</td>
      					<td width="9" class="TdBgColor1">&nbsp;</td>
      					<td width="324" class="TdBgColor1">
      						<input type="hidden" name="areaId" value="<c:out value="${areaCode }"/>"/>
      						<input type="hidden" name="areaName"/>
      						<slsint:ActiveXTree left="220" top="325" width="260" height="200" 
					      		xml="${orgTreeURL}" 
					      		bgcolor="0xFFD3C0" 
					      		rootid="${areaCode}"
					      		columntitle="${colName}" 
					      		columnwidth="260,0,0,0" 
					      		formname="organTreeAction" 
					      		idstr="areaId" 
					      		namestr="areaName" 
					      		checkstyle = "0" 
					      		filllayer="2" 
					      		txtwidth="260"
					      		buttonname="${orgButton}">
    						</slsint:ActiveXTree>
    					</td>
    				</tr>
    				<tr>
      					<td width="193" height="31" class="TdBgColor1">
      						<fmt:message key="organTree.beginDate"/>
							<input type="text" name="beginDate" value="<c:out value="${organTreeForm.beginDate }"/>" readonly="true" size="8"/>
							<script type="text/javascript">
								Calendar.setup({
									inputField     :    "beginDate",  
									ifFormat       :    "%Y-%m-%d",     
									showsTime      :    false,
									align          :    "tl"
								});
							</script>
						</td>
      					<td width="168" class="TdBgColor1">
      						<fmt:message key="organTree.endDate"/>
							<input type="text" name="endDate" value="<c:out value="${organTreeForm.endDate }"/>" readonly="true" size="8"/>
							<script type="text/javascript">
								Calendar.setup({
									inputField     :    "endDate",  
									ifFormat       :    "%Y-%m-%d",     
									showsTime      :    false,
									align          :    "tl"
								});
							</script>
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
      						<input type="button" name="removeNode" value="<fmt:message key='organTree.removeNode'/>" onClick="removeSelected();" />&nbsp;
							<input type="button" name="moveUp" value="<fmt:message key='organTree.moveUp'/>" onClick="hasMoveUp();" />&nbsp;
	      					<input type="button" name="moveDown" value="<fmt:message key='organTree.moveDown'/>" onClick="hasMoveDown();" />
						</td>
      					<td class="TdBgColor1">&nbsp;</td>
      					<td class="TdBgColor1" align="center">
      						<input type="button" name="addNode" value="<fmt:message key='organTree.addNode'/>" onClick="addSelectNode();" />&nbsp;
							<input type="button" name="select" value="<fmt:message key='organTree.selectAll'/>" onClick="selectAll();" />&nbsp;
			      			<input type="button" name="unSelect" value="<fmt:message key='organTree.unSelectAll'/>" onClick="unSelectAll();" />
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
		</td>
	</tr>
</table>
</body>
</html>
<script type="text/javascript">

var organTreeForm=document.organTreeAction
var organSelect=organTreeForm.organList;

var struct = "";
var newOrEdit = "<c:out value="${newOrEdit}"/>";
//if newOrEdit == 0 is new Tree or edit Tree
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


//page onload build organType option
var allAreaOrgans = new Array();
var areaOrgansOfLength = 0;
var areaId;
//observePro();
fetchOrganList();
var loginDate = "<c:out value="${logindate}"/>";

var optionStr = "";
var optionStr2 = "";
var typeId = "<c:out value="${typeId}"/>";
var typeName = "<c:out value="${typeName}"/>";
var id = typeId.split(",");
var name = typeName.split(",");
var cnt = Math.round(name.length/2);
for(var i = 0; i < cnt; i++){
	if(optionStr == ""){
		optionStr = '<input type="checkbox" name=\''+id[i]+'\' value=\''+id[i]+'\' checked onClick="filterOrganByType();">'+ name[i];
	}else{
		optionStr += '<input type="checkbox" name=\''+id[i]+'\' value=\''+id[i]+'\' checked onClick="filterOrganByType();">'+ name[i];
	}
}
for(var i = cnt; i < id.length; i++){
	if(optionStr2 == ""){
		optionStr2 = '<input type="checkbox" name=\''+id[i]+'\' value=\''+id[i]+'\' checked onClick="filterOrganByType();">'+ name[i];
	}else{
		optionStr2 += '<input type="checkbox" name=\''+id[i]+'\' value=\''+id[i]+'\' checked onClick="filterOrganByType();">'+ name[i];
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

//change areaTree run this function
function changeTree1(){
	areaId = organTreeForm.areaId.value;
	if(allAreaOrgans[areaId]){
		filterOrganByType();
	}else{
		fetchOrganList();
	}
}
function fetchOrganList(){
	areaId = organTreeForm.areaId.value;
	var options={onSuccess:recieveOrganList};
	var url='<c:out value="${hostPrefix}" /><c:url value="/organTreeAction.do" />?method=getOrganList&areaId='+areaId;
	//pu=new Ajax.PeriodicalUpdater($("res"),url,options)
	pu = new Ajax.Updater($("organs"),url,options);
}

//function processRequest(request) {
function recieveOrganList(request) {
  var organInfo = request.responseText;
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
			if(inputTag[j].getAttribute("type") == "checkbox" && inputTag[j].getAttribute("checked") == true){
				if(inputTag[j].getAttribute("value") == organInfo.type){
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
	/*
	var branch = tree.getBranchById("root");
	var organId = branch.struct.organId;
	var options={onSuccess:processRequest1}
	var url='<c:out value="${hostPrefix}" /><c:url value="/organTreeAction.do" />?method=getAreaId&organId='+organId;
	//pu=new Ajax.PeriodicalUpdater($("res"),url,options)
	pu = new Ajax.Updater($("organs"),url,options);
	*/
}

/*
function processRequest1(request) {
	var areaId = request.responseText;
  	if(areaId != ""){
  		organTreeForm.areaId.value = areaId;
  		organTreeForm.action = "organTreeAction.do?method=saveTree";
		organTreeForm.submit();
  	}else if(areaId == "no"){
  		alert("error");
  	}
}
*/






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
	pu = new Ajax.Updater($("organs"),url,options);
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
