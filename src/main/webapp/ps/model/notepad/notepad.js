
function doing(){  //备忘和日程安排设置为不同的日期存存储
	//$("#ormframe").$("#rcdate").hide();
	//$("#ndate").show();
    $("#notetype").val("0");
    //alert($("#notetype").val());
	$(window.frames["formframe"].document).find("#rcdate").hide();
	$(window.frames["formframe"].document).find("#ndate").show();
	$(window.frames["formframe"].document).find("#notetype").val("0");
	window.notePadForm.submit();
    //alert($(window.frames["formframe"].document).find("#notetype").val());

	
}
function done(){  //备忘和日程安排设置为不同的日期存存储
	$(window.frames["formframe"].document).find("#ndate").hide();
	$(window.frames["formframe"].document).find("#rcdate").show();
	$(window.frames["formframe"].document).find("#notetype").val("1");
    //alert($(window.frames["formframe"].document).find("#notetype").val());
	//$("#ndate").hide();
	//$("#rcdate").show();
 	$("#notetype").val("1");
 	window.notePadForm.submit();//在框架页中执行查询
    //alert($("#notetype").val());
}


function editform(){  //备忘和日程安排设置为不同的日期存存储

	$("#ndate").hide();
	$("#rcdate").show();
 	$("#notetype").val("1");
    //alert($("#notetype").val());
}

function getttoday(notedate)
{
	//alert(ptId);
	//var o ={};


	$.ajax({
		   type: "POST",
		   url: "notepadAction.do?method=getTodayNote&from=ajax",
		   data: {notedate:notedate},
		   success: function(response){
	
			 if(response!="")
			 {

					 $("#todaynote").html(response);
				
			 }
	
		   },
		   error : function(response,errText){
			   //alert(response.responseText);
			   //alert('数据获取失败,请登录后重试！');
		   }
		});	


}	

function secBoard(id,css) {
	
	//var secTable = document.getElementById("secTable");
	var chtds = $("#secTable").children().children().children();
	//var chtds = secTable.childNodes[0].childNodes[0].childNodes;
	//alert(chtds.length);
	//alert(n);
	for ( var i = 0; i < chtds.length-1; i++) {
		chtds[i].className="sec1";
		//chtds[i].addClass("sec1");
		//$("").removeClass("class2");
	}
	chtds[id].className="sec2";
	//$("#secTable0").removeClass("sec2");
	//$("#secTable1").removeClass("sec2");
	//alert(id);
	//$(""#secTable"+id+"\"").addClass("sec2");

}

function view(pkid)
{
    var url="";
    url="notepadAction.do?method=getNotePadById&from=view&pkid="+pkid;
	
	dialog(url);	
	
	
}

function viewparent(pkid)  //本页调用无需parent
{
	var url="";
    url="notepadAction.do?method=getNotePadById&from=view&pkid="+pkid;
	//alert(window.parent);
    if(window.parent)
	{window.parent.dialog(url);
	}
	else
	{
		dialog(url);	
	}	
	
	
}

function dialog(url)
{
	 $("#msg").dialog({
			autoOpen : false,
			modal : true,
			stack : true,
			height : 240,
			title : '您的提醒查看',
			buttons : {
				'关闭' : function(){
					$(this).dialog('close');
					
					
				}
	            /**,  设置不在提醒要到查看页面里，不适合在外边单独按钮
				'不在提醒' : function(){
					$(this).dialog('close');
					
				}
				*/
					
			}
		});
		
		var msg = "<iframe frameborder=0 height=100% width=100% src='"+url+"'></iframe";
		if(msg!=""){
			$("#msg").empty();
			$("#msg").html(msg);
		    $("#msg").dialog('open');
		} 	
	 }


		