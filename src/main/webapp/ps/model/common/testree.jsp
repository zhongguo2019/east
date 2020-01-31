<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />



<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>

<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/> 
<script type="text/javascript" src="<c:url value='${eaUIPath}/searchTree.js'/>"></script> 

<script type="text/javascript">

function onButtonClick(menuitemId,type){
    var id = tree.contextID;
	tree.setItemColor(id,menuitemId.split("_")[1]);
}


/**
 * 异步机构树处理
 * @param oid 传过来的放置机构id隐藏域的id
 * @param oname 传过来的放置机构name域的id 
 * @returns oid,oname;
 */


var tid;
var tname;
  function openOrganTree(oid,oname){//打开异步机构树
	tid=oid;
	tname=oname;
 	$("#dlg").dialog("open");
 	$('#searchbox').searchbox('setValue', null);//设置searchbox为空
 	$('#treeOrganXml').find('.tree-node-selected').removeClass('tree-node-selected');//移除上次选中的节点
 	
 	$("#treeOrganXml li:eq(0)").find("div").addClass("tree-node-selected");//设置第一个节点高亮  
        var node = $("#treeOrganXml").tree("getSelected");//获取第一个节点
 	$("#treeOrganXml").tree("select",node.target);//默认第一个节点选中
 	
 	$('#treeOrganXml').tree('collapseAll', node.target);//折叠所有节点
 	 } 
 	 
 	 $(function(){//异步加载下级机构树 
 			 $('#treeOrganXml').tree({
 		    		onBeforeExpand:function(node,param){
 		    			//treeState机构树是0,同步，1，异步，2，异步，机构树获取下级，3，同步，直接获取下级所有的数据，但是不把数据 put进json
 		                $('#treeOrganXml').tree('options').url = "<c:out value='${contextPath}'/>/treeAction.do?method=getOrganTreeXML&treeState=2&nodeid=" + node.id;
 		            }
 		    	});
 	     });

 	 function SetVale(){//获取当前选中节点
 		 var node =$("#treeOrganXml").tree("getSelected"); //当前选中节点         
 		 $("#"+tid+"").val(node.id);
		 $("#"+tname+"").val(node.text);
		 $("#dlg").dialog("close");
 	 }
  $(function(){
 	 $('#treeOrganXml').tree({//单击选中的时候，去掉其他选中的高亮显示
 		    onClick: function(node){
 		    	 $('#treeOrganXml').find('.tree-node-selected').removeClass('tree-node-selected');//移除上次选中的节点
 		    	 $("#treeOrganXml").tree("select",node.target);//选中当前节点
 		    }
 		});
 	});
 	 
 $(function(){ 	 
 	 $("#searchbox").searchbox({//搜索
 		  searcher: function (value) {
 			  if(value==""){
 				  alert("Please Write");
 				  return false;
 			  }
 			  $("#treeOrganXml li:eq(0)").find("div").addClass("tree-node-selected");//设置第一个节点高亮  
 		      var roots = $("#treeOrganXml").tree("getSelected");//获取根节点
 			  var nodes = $('#treeOrganXml').tree('getChildren',roots.target);
 			  var childNodeList = [];
 			  var nochildNodeList = [];
 			  if(value!=""){
 				 $('#treeOrganXml').find('.tree-node-selected').removeClass('tree-node-selected');//移除上次选中的节点
 				  
 				  for ( var i=0; i<nodes.length; i++){
 					  var node = nodes[i];
 					  if(isMatch(value,roots.text)){
 						  childNodeList.push(roots);
 					  }
 					  if(isMatch(value,node.text)){
 						  childNodeList.push(node);
 					  }else if(!isMatch(value,node.text)){
 						  nochildNodeList.push(node); 
 					  }
 				  }
 			  }
 			  if(childNodeList.length<=0){
 				 alert("No Search");
 				  return false;
 			  }
 			
 			  for ( var i=0; i<nochildNodeList.length; i++){//循环关闭不匹配的节点层级
 				  $('#treeOrganXml').tree('collapse', nochildNodeList[i].target);//折叠所有节点
 			  }
 			  if(childNodeList.length==1){//只有一个匹配的节点，默认选中
 				  $('#treeOrganXml').tree('expandTo',childNodeList[0].target);//打开节点到当前节点
 				  $('#treeOrganXml').tree('select',childNodeList[0].target);//打开节点到当前节点
 			  }else{//有多个匹配的节点，加上高亮
 				  for ( var i=0; i<childNodeList.length; i++){//循环打开到匹配的节点层级
 					  $('#treeOrganXml').tree('expandTo',childNodeList[i].target);//打开节点到当前节点
 					  $(".tree-title", childNodeList[i].target).addClass("tree-node-selected"); 
 				  }
 			  }
 	  }
 	});
 });
 	 function isMatch(searchText, targetText) {//模糊查询
 	        return $.trim(targetText)!="" && targetText.indexOf(searchText)!=-1;  
 	    } 
</script>

</head>

<body>



<!-- 异步机构树-->

<div id="dlg" class="easyui-dialog" closed="true" title="机构树" style="width:380px;height:380px;padding:10px"
            data-options="toolbar: '#dlg-toolbar',buttons: '#dlg-buttons'">
        	<ul class="easyui-tree" id="treeOrganXml" data-options="url:'<c:out value="${contextPath}"/>/treeAction.do?method=getOrganTreeXML&treeState=1'"></ul>
    		<!-- treeState机构树是0,同步，1，异步，2，异步，机构树获取下级，3，同步，直接获取下级所有的数据，但是不把数据 put进json -->
</div>
    <div id="dlg-toolbar" style="padding:2px 0">
        <table cellpadding="0" cellspacing="0" style="width:100%">
            <tr>
                <td style="text-align:center;padding-right:2px;width: 100%">
                    <input class="easyui-searchbox" id="searchbox" data-options="prompt:'查找机构'" style="width:350px"></input>
                </td>
            </tr>
        </table>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:SetVale();">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')">Close</a>
    </div> 
    
    
    

  			  		异步机构树
  			  		<input value="opener" onclick="openOrganTree('oid','oname');" type="button">
					<input id="oid" type="text">---
					<input id="oname" type="text">
  
</body>


</html>
