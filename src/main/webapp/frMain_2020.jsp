<!-- frMain.j -->
<%@ page pageEncoding="UTF-8"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<html>
<head>
     <title><fmt:message key="webapp.welcome"/></title> 

	<link rel="stylesheet" href="<c:url value='/ps/framework/menu/css/default.css'/>" type="text/css" />
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${eaUIPath}/themes/metro/easyui.css'/>" />
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${eaUIPath}/themes/icon.css'/>" />
	 <link rel="stylesheet" type="text/css" href="<c:url value='${eaUIPath}/demo.css'/>"> 
	<script language="javascript" type="text/javascript"
	src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
	<script type="text/javascript" src="<c:url value='${eaUIPath}/jquery.min.js'/>"></script> 
	<script type="text/javascript" src="<c:url value='${eaUIPath}/jquery.easyui.min.js'/>"></script>  
	<script type="text/javascript" src="<c:url value='${eaUIPath}/layoutfullORnot.js'/>"></script> 
	<script type="text/javascript" src="<c:url value='/ps/framework/menu/js/outlook.js'/>"></script>
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
<script type="text/javascript">

var _menus = {
	basic : [ {
		"menuid" : "10",
		"icon" : "icon-sys",
		"menuname" : "基础数据",
		"menus" : [ {
			"menuid" : "111",
			"menuname" : "基础数据1",
			"icon" : "icon-nav",
			"url" : "#"
		}, {
			"menuid" : "113",
			"menuname" : "基础数据12",
			"icon" : "icon-nav",
			"url" : "#"
		}, {
			"menuid" : "115",
			"menuname" : "基础数据13",
			"icon" : "icon-nav",
			"url" : "#"
		}, {
			"menuid" : "117",
			"menuname" : "基础数据14",
			"icon" : "icon-nav",
			"url" : "#"
		}, {
			"menuid" : "119",
			"menuname" : "基础数据15",
			"icon" : "icon-nav",
			"url" : "em/enterpriseChannelObtend.action"
		} ]
	}, {
		"menuid" : "20",
		"icon" : "icon-sys",
		"menuname" : "测试一",
		"menus" : [ {
			"menuid" : "211",
			"menuname" : "测试一11",
			"icon" : "icon-nav",
			"url" : "#"
		}, {
			"menuid" : "213",
			"menuname" : "测试一22",
			"icon" : "icon-nav",
			"url" : "#"
		} ]
	} ],
	point : [{
		"menuid" : "20",
		"icon" : "icon-sys",
		"menuname" : "积分管理",
		"menus" : [ {
			"menuid" : "211",
			"menuname" : "积分用途",
			"icon" : "icon-nav",
			"url" : "#"
		}, {
			"menuid" : "213",
			"menuname" : "积分调整",
			"icon" : "icon-nav",
			"url" : "#"
		} ]

	}]
};

//设置登录窗口
function openPwd() {
    $('#w').window({
        title: '修改密码',
        width: 300,
        modal: true,
        shadow: true,
        closed: true,
        height: 160,
        resizable:false
    });
}
//关闭登录窗口
function closePwd() {
    $('#w').window('close');
}



//修改密码
function serverLogin() {
    var $newpass = $('#txtNewPass');
    var $rePass = $('#txtRePass');

    if ($newpass.val() == '') {
        msgShow('系统提示', '请输入密码！', 'warning');
        return false;
    }
    if ($rePass.val() == '') {
        msgShow('系统提示', '请在一次输入密码！', 'warning');
        return false;
    }

    if ($newpass.val() != $rePass.val()) {
        msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
        return false;
    }

    $.post('/ajax/editpassword.ashx?newpass=' + $newpass.val(), function(msg) {
        msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
        $newpass.val('');
        $rePass.val('');
        close();
    })
    
}

$(function() {

    openPwd();

    $('#editpass').click(function() {
        $('#w').window('open');
    });

    $('#btnEp').click(function() {
        serverLogin();
    })

	$('#btnCancel').click(function(){closePwd();})

    $('#loginOut').click(function() {
        $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {

            if (r) {
                location.href = '/ajax/loginout.ashx';
            }
        });
    })
});
</script>

</head>
<style>
*{font-family:"Microsoft YaHei",微软雅黑,"Microsoft JhengHei",华文细黑,STHeiti,MingLiu}
 .repws{text-decoration:none;color:white;}
 
#css3menu li{ float:left; list-style-type:none;}
#css3menu li a{	color:#fff; padding-right:20px;}
#css3menu li a.active{color:yellow;}
</style>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<noscript>
		<div style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
			<img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
	
	<div region="north" split="true" border="false"
		style="overflow: hidden; height: 30px; background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%; line-height: 20px; color: #fff; font-family: Verdana, 微软雅黑, 黑体">
		<span style="float: right; padding-right: 20px;" class="head">
			欢迎 疯狂秀才
			<a href="#" id="editpass">修改密码</a> 
			<a href="#" id="loginOut">安全退出</a>
		</span> 
		<span style="padding-left: 10px; font-size: 16px; float: left;">
			<img src="images/blocks.gif" width="20" height="20" align="absmiddle"/> 
			我的帐本
		</span>
		<ul id="css3menu" style="padding: 0px; margin: 0px; list-type: none; float: left; margin-left: 40px;">
			<li><a class="active" name="basic" href="javascript:;" title="基础数据">基础数据</a></li>
			<li><a name="point" href="javascript:;" title="积分管理">积分管理</a></li>

		</ul>
	</div>
	
	<div region="south" split="true" style="height: 30px; background: #D2E0F2;">
		<div class="footer">By 疯狂秀才 Email:bjhxl@59ibox.cn</div>
	</div>
	
	<div region="west" hide="true" split="true" title="导航菜单" style="width: 180px;" id="west">
		<div id='wnav' class="easyui-accordion" fit="true" border="false">
			<!--  导航内容 -->

		</div>
	</div>
	
	<div id="mainPanle" region="center" style="background: #eee; overflow-y: hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="欢迎使用" style="padding: 20px; overflow: hidden;" id="home">
				<h1>Welcome to using The jQuery EasyUI!</h1>
			</div>
		</div>
	</div>

	<!--修改密码窗口-->
	<div id="w" class="easyui-window" title="修改密码" collapsible="false" minimizable="false" maximizable="false"
		icon="icon-save" style="width: 300px; height: 150px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
				<table cellpadding=3>
					<tr>
						<td>新密码：</td>
						<td><input id="txtNewPass" type="Password" class="txt01" /></td>
					</tr>
					<tr>
						<td>确认密码：</td>
						<td><input id="txtRePass" type="Password" class="txt01" /></td>
					</tr>
				</table>
			</div>
			<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
				<a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)"> 确定</a> <a id="btnCancel"
					class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)"
				>取消</a>
			</div>
		</div>
	</div>

	<div id="mm" class="easyui-menu" style="width: 150px;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>


</body>

</html>
	<script language=JavaScript>
		function closes() {
			$("#Loading").fadeOut("normal", function() {
				$(this).remove();
			});
		}

		var pc;
		$.parser.onComplete = function() {
			if (pc)
				clearTimeout(pc);
			pc = setTimeout(closes, 1000);
		}

		function closepanle() {
			/* $('#zm').layout('collapse','east');   */
			var turl = "ps/model/common/first.jsp";
			$('#centertab')
					.tabs(
							'add',
							{
								title : "首页",
								iconCls : "icon-home",
								content : '<div style="padding:0px;overflow-x:hidden;overflow-y:hidden;"><iframe frameborder=0 width=100% height=100% src='
										+ turl
										+ ' frameborder="0px" scrolling="auto" noresize="noresize"></iframe></div>',
								closable : false
							});
		}

		function demaxiya(obj) {
			// $("#righttitle").panel({title:obj});
		}
		function addPaneltab(tname, turl) {//不重复打开已打开的标签
			if ($("#centertab").tabs("exists", tname)) {//是否存在特定的 panel
				$("#centertab").tabs("select", tname); //选择一个 tab panel
				current_tab = $('#centertab').tabs('getSelected');
				$('#centertab')
						.tabs(
								'update',
								{
									tab : current_tab,
									options : {
										content : '<div style="padding:0px"><iframe frameborder=0 width=100% height=100% src='
												+ turl
												+ ' frameborder="0px" scrolling="auto" noresize="noresize"></iframe></div>'
									}
								});
			} else {
				$("#centertab")
						.tabs(
								"add",
								{
									title : tname,
									content : '<div style="padding:0px"><iframe frameborder=0 width=100% height=100% src='
											+ turl
											+ ' frameborder="0px" scrolling="auto" noresize="noresize"></iframe></div>',
									closable : true
								});
			}
		}

		function OpenPaneltab(turl) {//父窗口打开标签
			current_tab = $('#centertab').tabs('getSelected');
			$('#centertab')
					.tabs(
							'update',
							{
								tab : current_tab,
								options : {
									content : '<div style="padding:0px"><iframe frameborder=0 width=100% height=100% src='
											+ turl
											+ ' frameborder="0px" scrolling="auto" noresize="noresize"></iframe></div>'
								}
							});
		}

		function OpenPanel(openUrl, openWidth, openHeight, openName) {//iframe URL,宽度,高度,iframe Name
			var lenwidth = document.body.clientWidth;//网页可见区域宽
			var lenheight = document.body.clientHeight;//网页可见区域高

			$('#w').window({
				title : openName,
				width : openWidth,
				height : openHeight,
				left : (lenwidth - openWidth) * 0.5,
				top : (lenheight - openHeight) * 0.5,
				shadow : false,//如果设置为true，窗口的阴影也将显示
				draggable : true,//定义窗口是否可被拖动
				maximizable : true,//定义是否显示最大化按钮
				closable : true,//定义是否显示关闭按钮
				collapsible : false,//定义是否显示可折叠定义按钮
				modal : true
			//定义窗口是否是一个模式窗口
			});
			var con = '<iframe style="width:100%;height:100%;border:0;" src='
					+ openUrl
					+ ' frameborder="0px" scrolling="auto" noresize="noresize"></iframe>';
			$('#w').html(con);
			$('#w').window('open');
		}

		function ClosePanel() {
			$('#w').window('close');
		}

		$(function() {

			$(".tabs-header").bind('contextmenu', function(e) {
				e.preventDefault();
				$('#rcmenu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			});

			//关闭当前标签页
			$("#closecur").bind("click", function() {
				var tab = $('#centertab').tabs('getSelected');
				var index = $('#centertab').tabs('getTabIndex', tab);
				$('#centertab').tabs('close', index);
			});
			//关闭所有标签页
			$("#closeall").bind("click", function() {
				var tablist = $('#centertab').tabs('tabs');
				for (var i = tablist.length - 1; i >= 0; i--) {
					$('#centertab').tabs('close', i);
				}
			});
			//关闭非当前标签页（先关闭右侧，再关闭左侧）
			$("#closeother").bind("click", function() {
				var tablist = $('#centertab').tabs('tabs');
				var tab = $('#centertab').tabs('getSelected');
				var index = $('#centertab').tabs('getTabIndex', tab);
				for (var i = tablist.length - 1; i > index; i--) {
					$('#centertab').tabs('close', i);
				}
				var num = index - 1;
				for (var i = num; i >= 0; i--) {
					$('#centertab').tabs('close', 0);
				}
			});
			//关闭当前标签页右侧标签页
			$("#closeright").bind("click", function() {
				var tablist = $('#centertab').tabs('tabs');
				var tab = $('#centertab').tabs('getSelected');
				var index = $('#centertab').tabs('getTabIndex', tab);
				for (var i = tablist.length - 1; i > index; i--) {
					$('#centertab').tabs('close', i);
				}
				$("#centertab").tabs("select", tab);//当前标签页
			});
			//关闭当前标签页左侧标签页
			$("#closeleft").bind("click", function() {
				var tab = $('#centertab').tabs('getSelected');
				var index = $('#centertab').tabs('getTabIndex', tab);
				var num = index - 1;
				for (var i = 0; i <= num; i++) {
					$('#centertab').tabs('close', 0);
				}
				$("#centertab").tabs("select", tab);//当前标签页
			});

		});
	</script>
