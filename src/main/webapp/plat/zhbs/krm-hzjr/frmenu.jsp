<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%String loginDate = (String)request.getSession().getAttribute("logindate"); %>
<html>
<head>
<title>aaa</title>
<script type="text/javascript" src="<c:url value='/plat/zhbs/pngfix.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/prototype.js'/>"></script>
<link href="<c:url value='/plat/zhbs/ty.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/plat/zhbs/krm-hzjr.css'/>" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" style= "overflow-x:hidden" height="180">
<script language="javascript">
    var la=null;
	function expand(tb){
	    var e=document.getElementById(tb)
		if (e.style.display=="none"){
				e.style.display="block";
			if(la!=null){
				la.style.display="none";
			}
			la=e;
		}else{
			e.style.display="none";
			la=null;
		}
	}
	
	var lm=null;
	function cc(m){
		if(lm!=null){
			lm.className="";
		}
		m.className="cruuentLink";
		lm=m;
	}
</script>
<form name="menuForm" action="" method="post"> 
              <tr>
                <td width="3" valign="top"></td>
                <td bgcolor="#407CCA"  valign="top">         
		          <select style="width:158;font:b" name="organTree" id="organTree" onChange="javascript:changeOrganTree(this);">
		          </select>
                </td>
              </tr>
<div class=hzjr_sj>
  <div class="hzjr_sj_l">
    <% 
    	int i=1;
    	String tdid = "tb"+i;
    %>
    <c:forEach var="m" items="${menus}">
    <div class="tree"><span class="tree_b" onclick="expand(<%="'"+tdid+"'" %>);"><a href="#" id="hz<c:out value="${m.id}"/>"><c:out value="${m.name}" /></a></span>
    <div class="tree2" id=<%="'"+tdid+"'" %> style="display:none">
    <ul>
    <c:forEach var="sm" items="${m.subMenus}">
      <li><a href="<c:out value="${sm.resource}" />" target="mainFrame"><c:out value="${sm.name}" /></a></li>
    </c:forEach>
     </ul>
     <% 
	     i++;
	     tdid = "tb"+i;
     %>
    </div>
    </div>
    </c:forEach>
  </div>
</div>
<div id="organs" style="display:none"></div>
</form>
</body>
</html>
<script type="text/javascript">
//observePro();
<!--
function observePro(){
	var options={onSuccess:processRequest}
	var url='<c:out value="${hostPrefix}" /><c:url value="/organTreeAction.do" />?method=getOrganTree';
	//pu=new Ajax.PeriodicalUpdater($("res"),url,options)
	pu = new Ajax.Updater($("organs"),url,options);
}
function processRequest(request) {
	var organSystemJson = request.responseText;
	var systemJson;
	eval('systemJson='+organSystemJson);
	var organTree = document.menuForm.organTree;
	organTree.options.length = systemJson.length;
	for(var i = 0; i < systemJson.length; i++){
		organTree.options[i] = new Option(systemJson[i].name,systemJson[i].id);
		organTree.options[i].availability = systemJson[i].use;
		if(defineTreeId == ""){
			if(systemJson[i].use == "yes") {
				organTree.options[i].selected = true;
				defSelectedIndex = organTree.options[i].index;
				document.menuForm.organTree.selectedIndex = defSelectedIndex;
			}
		}else{
			if(systemJson[i].id == defineTreeId && systemJson[i].use == "yes") {
				organTree.options[i].selected = true;
				defSelectedIndex = organTree.options[i].index;
				document.menuForm.organTree.selectedIndex = defSelectedIndex;
			}
		}
	}
	if(systemJson.length == 1){
		var organTreeSelect = document.getElementById("organTree");
		organTreeSelect.style.display = "none";
	}else if(systemJson.length > 1){
		var organTreeSelect = document.getElementById("organTree");
		organTreeSelect.style.display = "block";
	}
}

var defSelectedIndex;
var systemJson = <c:out value="${json}" escapeXml="false"/>;
var defineTreeId = '<c:out value="${defineTreeId}"/>';
var organTree = document.menuForm.organTree;
	organTree.options.length = systemJson.length;
	if(systemJson.length == 1 || systemJson.length == 0){
		var organTreeSelect = document.getElementById("organTree");
		organTreeSelect.style.display = "none";
	}
	for(var i = 0; i < systemJson.length; i++){
		organTree.options[i] = new Option(systemJson[i].name,systemJson[i].id);
		organTree.options[i].availability = systemJson[i].use;
		if(organTree.options[i].availability == "no") {
			organTree.options[i].disabled = true;
			organTree.options[i].style.color = "#999";
		}else{
			organTree.options[i].disabled = false;
		}
		if(defineTreeId == ""){
			if(systemJson[i].use == "yes") {
				organTree.options[i].selected = true;
				defSelectedIndex = organTree.options[i].index;
			}
		}else{
			if(systemJson[i].id == defineTreeId && systemJson[i].use == "yes") {
				organTree.options[i].selected = true;
				defSelectedIndex = organTree.options[i].index;
			}
		}
	}

//alert(defSelectedIndex);
function changeOrganTree(opt){
	 var isOptionDisabled = opt.options[opt.selectedIndex].disabled;
	 if(isOptionDisabled) {
      		opt.selectedIndex = defSelectedIndex;//opt.defaultSelectedIndex;    
      		return;
    	}else{
    		opt.defaultSelectedIndex = opt.selectedIndex; 
    		var treeId = document.menuForm.organTree.value;
			var mainFrame = window.top.frames['mainFrame'];
			document.menuForm.organTree.blur();
			observePro1();
			mainFrame.changeOrganTree(treeId);
 		}
	
	//var treeId = document.menuForm.organTree.value;
	//var mainFrame = window.top.frames['mainFrame'];
	//document.menuForm.organTree.blur();
	//alert(document.menuForm.organTree.availability);
	//if(document.menuForm.organTree.availability == "yes") {
		//mainFrame.changeOrganTree(treeId);
		//observePro1();
	//}else{
		//mainFrame.notTreeNode();
	//}
	//observePro2();
	//observePro1();
}
/**
function observePro2(){
	var options={onSuccess:processRequest2}
	var treeId = document.menuForm.organTree.value;
	var url='<c:out value="${hostPrefix}" /><c:url value="/treeAction.do" />?method=isTreeNode&treeId='+treeId;
	//pu=new Ajax.PeriodicalUpdater($("res"),url,options)
	pu = new Ajax.Updater($("organs"),url,options);
}
function processRequest2(request) {
	var isTreeNode = request.responseText;
	if(isTreeNode == "yes") {
		observePro1();
	}else if(isTreeNode == "no") {
		var mainFrame = window.top.frames['mainFrame'];
		mainFrame.notTreeNode();
	}
}
**/
function observePro1(){
	var options={onSuccess:processRequest1}
	var treeId = document.menuForm.organTree.value;
	//alert("treeId="+treeId);
	var url='<c:out value="${hostPrefix}" /><c:url value="/organTreeAction.do" />?method=changeOrganTree&treeId='+treeId;
	//pu=new Ajax.PeriodicalUpdater($("res"),url,options)
	pu = new Ajax.Updater($("organs"),url,options);
}
function processRequest1(request) {
	var flag = request.responseText;
}

//observePro2();
//-->
</script>