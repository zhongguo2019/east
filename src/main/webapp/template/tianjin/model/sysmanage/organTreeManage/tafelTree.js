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
		alert("format error");
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
		for(var b = 0; b < document.organTreeAction.organList.options.length; b++){
			if(document.organTreeAction.organList.options[b].selected){
				organId = document.organTreeAction.organList.options[b].value;
				organTxt = document.organTreeAction.organList.options[b].text;
				struct = [{"organId": organId,"txt":organTxt,"onedit":"myEdit","onclick":"myClick","id":"root"}];
				tree = new TafelTree('organTree', struct, {
					'generate' : true,
					'imgBase' : 'common/tafelTree/imgs/',
					'defaultImg' : 'page.gif',
					'defaultImgOpen' : 'folderopen.gif',
					'defaultImgClose' : 'folder.gif',
					'onDrop' : function(){return false;},
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
				break;
			}
		}
		
	}else{
		initOrganIdList("root");
		var branch = tree.getBranchById(_tempId);
		if(branch == null || branch==""){
			alert("need check target Node");
		}else{
			for(var i = 0; i < document.organTreeAction.organList.options.length; i++){
				if(document.organTreeAction.organList.options[i].selected){
					if(!organId_map["a"+document.organTreeAction.organList.options[i].value] == true){
						var item = {
						"id" : "_node"+_arithmometer++,
						"txt" : document.organTreeAction.organList.options[i].text,
						"onclick":"myClick",
						"onedit":"myEdit",
						"organId":document.organTreeAction.organList.options[i].value
						}
						var newBranch = branch.insertIntoLast(item);
						organId_map["a"+document.organTreeAction.organList.options[i].value] = true;
					}else{
						alert("node exist");
					}
				}else{
					alert("not selected node");
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
		iteratorTree(id);
	}
}

//recurrence Tree
function iteratorTree(id){
	var branch = tree.getBranchById(id);
	organId_map["a"+branch.struct.organId] = true;
		if(branch.hasChildren()){
			var children = branch.getChildren();
			for(var i = 0; i < children.length; i++){
				iteratorTree(children[i]);
			}
		}
}

//button name is "removeNode" for onClick event run this function
function removeSelected(){
	tree.removeBranch(_tempId);
	_tempId = null;
}

//button name is "selectAll" for onClick event run this function
function selectAll(){
		for(var i = 0; i < document.organTreeAction.organList.options.length; i++){
			document.organTreeAction.organList.options[i].selected = true;
		}
}

//button name is "unSelectAll" for onClick event run this function
function unSelectAll(){
	for(var i = 0; i < document.organTreeAction.organList.options.length; i++){
		document.organTreeAction.organList.options[i].selected = false;
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
	document.organTreeAction.action = "organTreeAction.do?method=list";
	document.organTreeAction.submit();
}

