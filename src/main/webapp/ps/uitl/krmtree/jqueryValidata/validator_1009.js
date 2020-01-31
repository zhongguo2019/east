// jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
			_1009itemvalue1: /.+/,	//客户类型
			_1009itemvalue2: /.+/,	//客户名称 
			_1009itemvalue3: /.+/,	// 客户代码 
			_1009itemvalue4: /.+/,	// 客户国籍代码
			_1009itemvalue5: /.+/,	//关联方授信标识  
			_1009itemvalue6: /.+/,	//是否纳入授信业务
			_1009itemvalue7: /.+/,	//是否纳入授信业务
			_1009itemvalue8: /.+/	//是否纳入授信业务
			
		//	isSafe :  "$.fn.safe()"
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
			_1009itemvalue1Str:"不能为空",
			_1009itemvalue2Str:"不能为空",
			_1009itemvalue3Str:"不能为空",
			_1009itemvalue4Str:"不能为空",
			_1009itemvalue5Str:"不能为空",
			_1009itemvalue6Str:"不能为空",
			_1009itemvalue7Str:"不能为空",
			_1009itemvalue8Str:"不能为空"

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