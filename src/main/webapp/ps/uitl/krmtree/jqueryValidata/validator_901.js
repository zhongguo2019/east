//存款交易客户信息补录模板 jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
//			_901itemvalue1:/.+/,	// 客户编号
//			_901itemvalue2:/.+/, //客户名称
//			_901itemvalue3:/.+/, // 金融机构编码  
//			_901itemvalue4:/.+/, //所属分支机构
//			_901itemvalue5:/.+/,// 机构名称
//			_901itemvalue6:/ /, // 客户办理业务类型
			_901itemvalue7:/^[\s]{0,}[0-1]{1}$/ //客户种类
//			_901itemvalue8:/ /, //组织机构代码
//			_901itemvalue9:/ /, // 证件类型
//			_901itemvalue10:/ /, //证件号码
//			_901itemvalue11:/ /, //国民经济部门
//			_901itemvalue12:/ /, // 行业 
//			_901itemvalue13:/ /, // 经济成分
//			_901itemvalue14:/^\CS0[1-5]{1}$ /, //企业规模
//			_901itemvalue15:/.+/ // 存款人注册地编码 
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
//			_901itemvalue1Str:"不能为空" ,
//			_901itemvalue2Str:"不能为空",
//			_901itemvalue3Str:"不能为空",
//			_901itemvalue4Str:"不能为空",
//			_901itemvalue5Str:"不能为空",
//			_901itemvalue6Str:/ /,
			_901itemvalue7Str:"为0或1"
//			_901itemvalue8Str:/ /,
//			_901itemvalue9Str:/ /,
//			_901itemvalue10Str:/ /,
//			_901itemvalue11Str:/ /,
//			_901itemvalue12Str:/ /,
//			_901itemvalue13Str:/ /,
//			_901itemvalue14Str:"为CS01-CS05",
//			_901itemvalue15Str:"不能为空"
		},
		msg_en : {
			RequireStr : " cannot be empty",
			IPStr : " is not a valid IP address",
			EmailStr : " is not a valid email address",
			_901itemvalue1Str:"cannot be empty" 

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