function getlogin() {
	
		var userId = $("#user_name").val();
		var password = $("#user_password").val();
		var logindate = $("#logindate").val();
		//alert(userId);
		var url = platloginurl;
		if(userId==""||password==""||logindate=="")
			{
			alert("用户名，密码，登录日期都不能为空!");
			return false;
			}
		//用户是否存在标记
		var userexist = false;
		// 异步查询用户信息是否存在
		alert(url);
		$.ajax({
			"async":false,
        	"url": url,
        	"type": "get",
        	"dataType": "json",
        	"data": {"userId":userId, "password": password,"logindate":logindate},
       	 	"cache": false,
        	"error": function () { },
        	"success": function (data) {
        		alert(data.status);
        		if(data.status=="1")
        		{
        		$("#jsesstionId").val(data.jsesstionId);
        		
        		userexist=true;
        	    }
        		
        	}
    	}); 
		// 如果用户信息存在
		if(userexist){
			//getOsData();
			return true;
		}else{
			alert("输入的用户名或密码有误，请重新输入！");
			return false;
		}
	}


	function getOsData() {//显示出当前权限下可以看到的系统
	
		$.getJSON(oslisturl,function(data){
			 //alert(data);
			 $.each(data,function(i,items){
				
				 //if (items.plat) {
					 var url="http://"+items.host+":"+items.port+"/"+items.context+"/"+items.loginurl+"&"+"jsessionIdstr="+$("#jsesstionId").val();
					
					 $("#nav-shadow").append("<li id='plat_"+items.oscode+"'><a href='#'  title='"+items.osname+"' onclick=loginsub('"+url+"') id='"+items.oscode+"' >"+items.osname+"</a></li>");	
					 $("p").attr("'class", "pbox"); 
					 
					 $("#plat_"+items.oscode+"").css("background","url("+items.icon+") no-repeat center");
					 $("ul").each(function() {
					 //alert("1");	 
					 });
				// }
			 });
		});
	}
	
	
	function loginsub(url) {
		
		document.osform.action = url;
		document.osform.submit();
	
      
	}