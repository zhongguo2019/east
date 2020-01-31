<!-- frMain.j -->
<%@ page pageEncoding="UTF-8"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<html>
<head>
     <title><fmt:message key="webapp.welcome"/></title> 

	<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${eaUIPath}/themes/metro/easyui.css'/>" />
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${eaUIPath}/themes/icon.css'/>" />
	 <link rel="stylesheet" type="text/css" href="<c:url value='${eaUIPath}/demo.css'/>"> 
	<script language="javascript" type="text/javascript"
	src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
	<script type="text/javascript" src="<c:url value='${eaUIPath}/jquery.min.js'/>"></script> 
	<script type="text/javascript" src="<c:url value='${eaUIPath}/jquery.easyui.min.js'/>"></script>  
	<script type="text/javascript" src="<c:url value='${eaUIPath}/layoutfullORnot.js'/>"></script> 
<script type="text/javascript">
	function changedate(){
		/* alert(document.getElementById("date971").value);
		alert(document.getElementById("date97").value);
		return false; */
		if(confirm("<fmt:message key='quedingyaoxiugaima'/>")){
			
		  	var req=new XMLHttpRequest();
			 
			req.open('POST', 'loginAction.do?method=updatedate');//struts
			req.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
			req.send("date97="+document.getElementById("date97").value);
			 if (req) {
			  
			      req.onreadystatechange=function() {
			      if (req.readyState == 4) 
			      {
			    	
			       if ( req.status==200) {
			    	  
			    	   
			    	   alert("<fmt:message key='repfile.statistics.suc'/>");
			    	 document.getElementById("date971").value=document.getElementById("date97").value
			     	}
			     }
			             
			  }
			  }  
			} 

			else{ 
				document.getElementById("date97").value=document.getElementById("date971").value;
				return false;
				}
	}
	
	function repws(){
		var userpkid = $("#userpkid").val();
		
		http://localhost:8082/east/userAction.do?method=refpassword&userid=130796
		alert(userpkid);
	}
	
</script>

</head>
<style>
*{font-family:"Microsoft YaHei",微软雅黑,"Microsoft JhengHei",华文细黑,STHeiti,MingLiu}
 .repws{text-decoration:none;color:white;}
</style>
<script type="text/javascript">
$(function() {
    $("#toggle").click(function() {
        $("#content").slideToggle();
    });
});


</script>
<body class="easyui-layout" onload="closepanle();"  id="zm" >


<input id="ppppp" type="hidden" value="" onpropertychange="demaxiya(this.value)"/>

	<div data-options="region:'north',border:false " style="height:40px;padding:0px;overflow-x:hidden;overflow-y:hidden;  ">
		<div style="height:40px;overflow-y:hidden;overflow-x:hidden;background-image: linear-gradient(to bottom,#E7F9FD, #75B2E1,#E7F9FD)">
	<div style="float:left;padding-left:10px;width:800px;">
	<div style="float:left;width:180px;color:#ffffff;height:40px;line-height:40px;">
	<img style="float:left;color:#ffffff;height:40px;" src="<c:url value='/ps/framework/images/east_logo_w.png'/>" >
	</div>
	<!-- <div style="float:left;font-size:12px;width:200px;color:#ffffff;height:40xp;line-height:40px;" scrolling="no" >
	
	</div> -->
	</div>
	<div  style="font-size:12px;width:40px;color:#ffffff;height:40xp;line-height:40px;float:right;padding:8px">
	<a  class="easyui-linkbutton" data-options="iconCls:'icon-logout'" title="<fmt:message key="top.logout"/>" href="<c:url value='/loginAction.do?method=logout'/>"></a>
	
	
	</div>
	<div style="font-size:12px;color:#ffffff;height:40xp;line-height:40px;padding-left:1px;float:right;padding:10px">
	 <input type="hidden" onclick="WdatePicker();" class="Wdate" id="date971" value="<c:out value='${date }'/>">
      <input type="text" onclick="WdatePicker();" class="Wdate" id="date97" value="<c:out value='${date }'/>" onchange="changedate();">
    </div>
	<div  style="font-size:12px;color:#ffffff;height:40xp;line-height:40px;padding-left:2px;float:right;" >
	
	 <a class="repws" href="userAction.do?method=refpassword2&userid=<c:out value='${user.pkid}'/>"  ><fmt:message key="logpage.loginname"/><c:out value="${user.name}"/></a>
	 <input type="hidden"  id="userpkid" value="<c:out value='${user.pkid}'/>">
	
	</div>
	
	
	</div>
	</div>
	
		<div data-options="region:'west',split:true,iconCls:'icon-menu',title:' &nbsp;&nbsp;系统菜单 '" style="width:180px;padding:0px;background-color:#ffffff" class="panel-body panel-body-noheader layout-body panel-noscroll">
		 	<iframe src="menu.do"  frameborder="no" border="0" marginwidth="0" marginheight="0"  name="leftFrame"  style="width:100%;height:100%" scrolling="auto" target="mainFrame"  noresize="noresize"></iframe>
		</div>
	
		<div data-options="region:'center'" id="righttitle" style="background-color:#ffffff">
			<div data-options="region:'center',tools:'#tab-tools'" name="optionbar"  style="background-color:#ffffff" id="centertab" class="easyui-tabs " fit="true"  class="easyui-tabs tabs-container easyui-fluid" border="false" style="padding:0px;">
			</div>
			 <div id="tab-tools" style="border-left: 0px;border-top: 0px;border-right: 0px;">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'" onclick="$('body').layout('full');"></a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="$('body').layout('unFull');"></a>
    </div>
		</div>
	
	<!-- <div data-options="region:'east',split:true,collapsed:true,title:'用户信息 ',iconCls:'icon-man'" style="width:10%;padding:0px;background-color:#ffffff" class="panel-body-noheader panel-noscroll">
	<iframe src="notepadAction.do?method=goTestPage" frameborder="no" border="0" marginwidth="0" marginheight="0"  style="width:100%;height:100%" scrolling="auto" ></iframe>
	</div> -->


<div id="w" class="easyui-window" closed="true"></div>

<div id='Loading' style="position: absolute; z-index: 1000; top: 48%;text-algin:center;
    width: 100%; height: 60px;  text-align: center;">
    <div style="position: relative;margin:auto;width:200px;line-height:60px;background: #7f8384;">
        <font color="#ffffff">loading···</font>
    </div>
</div>

<div id="rcmenu" class="easyui-menu">
    <div id="closecur">
        关闭
    </div>
    <div id="closeall">
        关闭全部
    </div>
    <div id="closeother">
        关闭其他
    </div>
    <div class="menu-sep"></div>
    <div id="closeright">
        关闭右侧标签页
    </div>
    <div id="closeleft">
        关闭左侧标签页
    </div>
    <div class="menu-sep"></div>
</div>



    


</body>

</html>
	<script language=JavaScript>
	
    function closes() {
        $("#Loading").fadeOut("normal", function () {
            $(this).remove();
        });
    }

    var pc;
    $.parser.onComplete = function () {
        if (pc) clearTimeout(pc);
        pc = setTimeout(closes, 1000);
    }
	
	 function closepanle(){
		/* $('#zm').layout('collapse','east');   */
		var turl = "ps/model/common/first.jsp";
        $('#centertab').tabs('add',{
            title: "首页",
            iconCls:"icon-home",
            content: '<div style="padding:0px;overflow-x:hidden;overflow-y:hidden;"><iframe frameborder=0 width=100% height=100% src='+turl+' frameborder="0px" scrolling="auto" noresize="noresize"></iframe></div>',
            closable: false
        });
	} 
	
	   function demaxiya(obj){
		 // $("#righttitle").panel({title:obj});
	}  
    function addPaneltab(tname,turl){//不重复打开已打开的标签
    	 if($("#centertab").tabs("exists",tname)){//是否存在特定的 panel
    	       $("#centertab").tabs("select",tname); //选择一个 tab panel
    	      current_tab = $('#centertab').tabs('getSelected');
    	       $('#centertab').tabs('update', {
    	    	   tab: current_tab,
    	    	   options:{
    	    		   content: '<div style="padding:0px"><iframe frameborder=0 width=100% height=100% src='+turl+' frameborder="0px" scrolling="auto" noresize="noresize"></iframe></div>' 
    	    	   }
    	});
    	    }else{
    	       $("#centertab").tabs("add",{
    	    	   title: tname,
    	            content: '<div style="padding:0px"><iframe frameborder=0 width=100% height=100% src='+turl+' frameborder="0px" scrolling="auto" noresize="noresize"></iframe></div>',
    	            closable: true
    	       });
    	    }
    }
    
    
    function OpenPaneltab(turl){//父窗口打开标签
   	      current_tab = $('#centertab').tabs('getSelected');
   	       $('#centertab').tabs('update', {
   	    	   tab: current_tab,
   	    	   options:{
   	    		   content: '<div style="padding:0px"><iframe frameborder=0 width=100% height=100% src='+turl+' frameborder="0px" scrolling="auto" noresize="noresize"></iframe></div>' 
   	    	   }
   		});
   }
    
    function OpenPanel(openUrl,openWidth,openHeight,openName){//iframe URL,宽度,高度,iframe Name
    	var lenwidth = document.body.clientWidth;//网页可见区域宽
    	var lenheight = document.body.clientHeight;//网页可见区域高
    	

    	$('#w').window({
    		title:openName,
    		width:openWidth,
    		height:openHeight,
    		left:(lenwidth-openWidth)*0.5,
    		top:(lenheight-openHeight)*0.5,
    		shadow:false,//如果设置为true，窗口的阴影也将显示
    		draggable:true,//定义窗口是否可被拖动
    		maximizable:true,//定义是否显示最大化按钮
    		closable:true,//定义是否显示关闭按钮
    		collapsible:false,//定义是否显示可折叠定义按钮
    		modal:true//定义窗口是否是一个模式窗口
    	});
    	var con = '<iframe style="width:100%;height:100%;border:0;" src='+openUrl+' frameborder="0px" scrolling="auto" noresize="noresize"></iframe>';
    	$('#w').html(con);
    	$('#w').window('open'); 
    }
    
    function ClosePanel(){
    	$('#w').window('close');
    }
    
    $(function(){
        
        $(".tabs-header").bind('contextmenu',function(e){
            e.preventDefault();
            $('#rcmenu').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        });
    
        //关闭当前标签页
        $("#closecur").bind("click",function(){
            var tab = $('#centertab').tabs('getSelected');
            var index = $('#centertab').tabs('getTabIndex',tab);
            $('#centertab').tabs('close',index);
        });
        //关闭所有标签页
        $("#closeall").bind("click",function(){
            var tablist = $('#centertab').tabs('tabs');
            for(var i=tablist.length-1;i>=0;i--){
                $('#centertab').tabs('close',i);
            }
        });
        //关闭非当前标签页（先关闭右侧，再关闭左侧）
        $("#closeother").bind("click",function(){
            var tablist = $('#centertab').tabs('tabs');
            var tab = $('#centertab').tabs('getSelected');
            var index = $('#centertab').tabs('getTabIndex',tab);
            for(var i=tablist.length-1;i>index;i--){
                $('#centertab').tabs('close',i);
            }
            var num = index-1;
            for(var i=num;i>=0;i--){
                $('#centertab').tabs('close',0);
            }
        });
        //关闭当前标签页右侧标签页
        $("#closeright").bind("click",function(){
            var tablist = $('#centertab').tabs('tabs');
            var tab = $('#centertab').tabs('getSelected');
            var index = $('#centertab').tabs('getTabIndex',tab);
            for(var i=tablist.length-1;i>index;i--){
                $('#centertab').tabs('close',i);
            }
            $("#centertab").tabs("select",tab);//当前标签页
        });
        //关闭当前标签页左侧标签页
        $("#closeleft").bind("click",function(){
            var tab = $('#centertab').tabs('getSelected');
            var index = $('#centertab').tabs('getTabIndex',tab);
            var num = index-1;
            for(var i=0;i<=num;i++){
                $('#centertab').tabs('close',0);
            }
            $("#centertab").tabs("select",tab);//当前标签页
        });
             
    });

</script>
