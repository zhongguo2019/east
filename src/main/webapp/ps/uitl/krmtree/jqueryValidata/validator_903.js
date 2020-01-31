//贷款客户补录模板 jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
//			_903itemvalue1:/.+/,	//  客户编号
//			_903itemvalue2:/.+/,	//客户名称
//			_903itemvalue3:/ /,	//金融机构编码
//			_903itemvalue4:/.+/,	//所属分支机构
//			_903itemvalue5:/ /,	//贷款卡号
//			_903itemvalue6:/ /,	//机构名称 
//			_903itemvalue7:/ /,	// 客户办理业务类型 
			_903itemvalue8:/^[\s]{0,}[0-1]{1}$/ ,	// 客户类型  
			_903itemvalue9:/.+/,	//组织机构代码
//			_903itemvalue10:/ /,	//证件类型  
//			_903itemvalue11:/ /,	//证件号码 
//			_903itemvalue12:/ /,	//国民经济部门
//			_903itemvalue13:/ /,	// 贷款主体行业类别 
			_903itemvalue14:/.+/,	//经济成分
			_903itemvalue15:/^\CS0[1-5]{1}$ /,	// 企业规模 
			_903itemvalue16:/.+/	//借款人注册地编码
			
			
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
//			_903itemvalue1Str:"客户编号不能为空" ,
//			_903itemvalue2Str:"客户名称不能为空" ,
//			_903itemvalue3Str:"金融机构编码不能为空" ,
//			_903itemvalue4Str:"所属分支机构不能为空" ,
//			_903itemvalue5Str:"" ,
//			_903itemvalue6Str:"" ,
//			_903itemvalue7Str:"" ,
			_903itemvalue8Str:"为0或1" ,
			_903itemvalue9Str:"不能为空" ,
//			_903itemvalue10Str:"" ,
//			_903itemvalue11Str:"" ,
//			_903itemvalue12Str:"" ,
//			_903itemvalue13Str:"贷款主体行业不能为空" ,
			_903itemvalue14Str:"不能为空" ,
			_903itemvalue15Str:"围殴CS01-CS05" ,
			_903itemvalue16Str:"不能为空"
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