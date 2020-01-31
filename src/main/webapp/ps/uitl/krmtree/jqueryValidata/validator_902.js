// 存款帐务信息补录模板jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
			_902itemvalue1:/.+/,	//	数据日期
//			_902itemvalue2:/ /,	//存款账户代码
//			_902itemvalue3:/ /,	//存款账户类型
//			_902itemvalue4:/ /,	//存款状态
//			_902itemvalue5:/ /,	//存款协议代码
//			_902itemvalue6:/ /,	//存款产品类别
//			_902itemvalue7:/ /,	//通知存款提前通知天数
//			_902itemvalue8:/ /,	//缴存存款准备金方式
//			_902itemvalue9:/ /,	//存款协议起始日期
//			_902itemvalue10:/ /,	//存款协议到期日期
//			_902itemvalue11:/ /,	//存款协议实际终止日期
			_902itemvalue12:/.+/,	//存款币种
			_902itemvalue21:/^[\s]{0,}\d*[\.]?\d*[1-9]+\d*$/,	//存款余额	大于0
			_902itemvalue13:/.+/,	//委托资金存款币种
//			_902itemvalue22:/ /,	//委托资金存款余额
//			_902itemvalue14:/ /,	//代理金融交易币种
//			_902itemvalue23:/ /,	//代理金融交易余额
//			_902itemvalue15:/ /,	//客户编号
			_902itemvalue16:/^\RF0[1-2]{1}$/,	//利率是否固定
//			_902itemvalue24:/^[0]{1}\.[0-9]{4}$/,	//利率水平
//			_902itemvalue17:/^[0]{1}\.[0-9]{4}$/,	//计息期限
//			_902itemvalue18:/^[0]{1}\.[0-9]{4}$/,	//计息方式
//			_902itemvalue19:/ /,	//结息方式
			_902itemvalue20:/.+/,	//金融机构编码
//			_902itemvalue51:/.+/,	//所属分支机构
//			_902itemvalue52:/.+/,	//机构名称
//			_902itemvalue53:/.+/,	//客户名称
			_902itemvalue54:/^[\s]{0,}[0-1]{1}$/	//客户种类
//			_902itemvalue55:/ /,	//客户的证件类型
//			_902itemvalue56:/ /	//客户的证件号码
			
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
			_902itemvalue1Str:"不能为空" ,   // 数据日期
//			_902itemvalue2Str: "",	//存款账户代码
//			_902itemvalue3Str:"" ,	//存款账户类型
//			_902itemvalue4Str:"" ,	//存款状态
//			_902itemvalue5Str:"" ,	//存款协议代码
//			_902itemvalue6Str:"" ,	//存款产品类别
//			_902itemvalue7Str:"" ,	// 通知存款提前通知天数
//			_902itemvalue8Str:"" ,	// 缴存存款准备金方式
//			_902itemvalue9Str:"" ,	//存款协议起始日期
//			_902itemvalue10Str:"" ,	//存款协议到期日期
//			_902itemvalue11Str:"" ,	//	存款协议实际终止日期
			_902itemvalue12Str:"不能为空" ,	// 存款币种  
			_902itemvalue13Str:"不能为空" ,	//委托资金存款币种
//			_902itemvalue14Str:"人民币为CNY" ,	//代理金融交易币种
//			_902itemvalue15Str:"" ,	//客户编号
			_902itemvalue16Str:"RF01或RF02" ,	// 利率是否固定
//			_902itemvalue17Str:"" ,	//计息期限
//			_902itemvalue18Str:"" ,	//计息方式
//			_902itemvalue19Str:"" ,	//结息方式   
			_902itemvalue20Str:"不能为空" ,	//金融机构编码 
			_902itemvalue21Str:"必须大于0" ,	//存款余额 
//			_902itemvalue22Str:"委托资金存款余额必须大于0" ,	//委托资金存款余额
//			_902itemvalue23Str:"代理金额交易余额必须大于0" ,	//代理金融交易余额 
//			_902itemvalue24Str:"" ,	//利率水平
//			_902itemvalue51Str:"不能为空" ,	//所属分支机构
//			_902itemvalue52Str:"所属分支机构不能为空" ,	//机构名称   
//			_902itemvalue53Str:"机构名称不能为空" ,	// 客户名称  
			_902itemvalue54Str:"不能为空,为0或1" 	// 客户种类-----701
//			_902itemvalue55Str:"客户类型不能为空" ,	//证件类型 
//			_902itemvalue56Str:"客户类型客户类型有误" 	//证件号码
			
		},
		msg_en : {
			RequireStr : " cannot be empty",
			IPStr : " is not a valid IP address",
			EmailStr : " is not a valid email address"
		},
		/** 添加其他语言提示信息
		msg_** : {
			RequireStr : " cannot be empty",
			IPStr : " is not a valid IP address",
			EmailStr : " is not a valid email address"
		},
		*/
		validator : function (formName,mode,isSubmit,single){
			
			var validatorResult = true;
			
			var form = $("form[name='"+formName+"']");
			
			if(mode == null || mode === "")
				mode = 1;
			if(isSubmit == null || isSubmit === "")
				isSubmit = false;
			// 非空判断 当为单条数据保存的时候single不为空。
			var elements;
			if(single == null || single ===""){
				elements = form.find("input[dataType]");
			} else {
				var tep = $(single).parent().parent();
				elements = tep.find("input[dataType]");
			}
			
			var msgs = "";
			$.fn.delErrorMsg();
			$.each(elements, function(){
				msg = $.fn.match(this);
				if (mode == 0 && msg != ""){
					msgs += msg + "\n";
					validatorResult = false;
				}
				if (mode == 1 && msg !=""){
					$.fn.addElement(this, msg);
					isSubmit = false;
					validatorResult = false;
				}
			});
			
			if(mode == 0){
				alert(msgs);
				return false;
			}
			if(isSubmit){
				form.submit();
			}
			return validatorResult;
		}
	});
	
	
	$.extend($.fn, {
		getReg : function(regKey){
			return (eval("$.reg."+regKey));
		},
		getMsg : function(msgKey){
			var language = navigator.language || navigator.browserLanguage;
			language = language.substring(0,language.indexOf("-"));
			return (eval("$.msg_"+language+"."+msgKey+"Str"));
		},
		match : function (element) {
			var regKeys = $(element).attr("dataType");
			var labelText = $(element).attr("labelText");
			var regArray = regKeys.split(",");
			var value = $(element).val();
			
			var msg = "";
			
			$.each(regArray, function(i){
				var regKey = $.fn.getReg(regArray[i]);
				if(regKey == null || regKey === ""){
					return false;
				}
				if(!regKey.test(value)){
					msg = labelText + $.fn.getMsg(regArray[i]);
					return false;
				}
			});
			
			return msg;
		},
		addElement : function(element, error_msg){
			$(element).after("<font name='error_msg' style='color:red;font-size:13px;'>"+error_msg+"</font>");
		},
		delErrorMsg : function(){
			$("font[name='error_msg']").remove();
		}
	});
})(jQuery);

function validation_902(fieldType,itemvalue_idname,itemvalue_value){
	 	alert("进入902");
	 	if(fieldType == 0){
	 		var itemvaluearray = new Array();
	 		itemvaluearray = itemvalue_idname.split("_");
	 		var itemvalueid = itemvaluearray[1];
	 		itemvalue6 = itemvaluearray[0] + "6";
	 		
	 		if(itemvalueid == "24"){
	 			if(document.getElementById(itemvalue6).value !="D02"){
	 				itemvalue_value == "";
	 				alert("该产品类别非‘定活两便存款’，利率水平不能为空");
	 			}
	 		}
	 		
	 		itemvalue1 = itemvaluearray[0] + "1";
	 		if(itemvalueid == "9"){
	 			if(document.getElementById(itemvalue6).value !="D03" || document.getElementById(itemvalue6).value !="D04"
	 				||document.getElementById(itemvalue6).value !="D012"||document.getElementById(itemvalue6).value !="D014"
	 				||document.getElementById(itemvalue6).value !="D016"){
	 				
	 				if(itemvalue_value >= document.getElementById(itemvalue1).value){
	 					alert("该产品类型存款协议起始日期应小于数据日期");
	 				}
	 			}
	 		}
	 		
	 		if(itemvalueid == "10"){
	 			if(document.getElementById(itemvalue6).value !="D04"||document.getElementById(itemvalue6).value !="D012"
	 				||document.getElementById(itemvalue6).value !="D014"){
	 				
	 				if(itemvalue_value <= document.getElementById(itemvalue1).value){
	 					alert("该产品类型存款协议到期日期应大于数据日期");
	 				}
	 			}
	 		}
	 		
	 		if(itemvalueid == "5"){
	 			if(document.getElementById(itemvalue6).value !="D011" || document.getElementById(itemvalue6).value !="D013"){
	 				if(itemvalue_value == ""){
	 					alert("该产品类别不能为空");
	 				}else if(itemvalue_value.length >= 60){
	 					alert("存款协议代码长度应小于60");
	 				}
	 			}
	 		}
	 		if(itemvalueid == "2"){
	 			if(document.getElementById(itemvalue6).value !="D011" || document.getElementById(itemvalue6).value !="D013"){
	 				if(itemvalue_value == ""){
	 					alert("该产品类别不能为空");
	 				}else if(itemvalue_value.length >= 60){
	 					alert("存款账户代码长度应小于60");
	 				}
	 			}
	 		}
		}
	 	
	
		if(fieldType == 1){
			itemvalueid = itemvalue_idname.split("_")[1];
			
			
		}
		if(fieldType == 2){
			itemvalueid = itemvalue_idname.split("_")[1];
			
			
		}
} 
