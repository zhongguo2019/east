// jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
			_1008itemvalue1: /.+/,	//内部机构号
			_1008itemvalue2: /.+/,	//对公客户的客户代码
			_1008itemvalue3: /^[\s]{0,}[0-9A-Za-z]{2,}$/,	//关联集团代码
			_1008itemvalue4: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,	//关联集团名称
			_1008itemvalue5: /^[\s]{0,}[1-6]{1}$/,	//关联关系类型
			
			_1008itemvalue6: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$ /,//客户名称
//			_1008itemvalue7: / /,	//工商注册编号
			_1008itemvalue8: /.+/,	//集团成员数
			_1008itemvalue9: /^[\s]{0,}[1-2]{1}$ /,	//授信类型
//			_1008itemvalue21: / /,	//资产总额
//			_1008itemvalue22: / /,	//负债总额	
//			_1008itemvalue10: / /,	//资产负债表类型
			_1008itemvalue11: /^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/	//资产负债表日期
//			_1008itemvalue12: / /,	//风险预警信号
//			_1008itemvalue13: / /	//关注事件
				
		//	isSafe :  "$.fn.safe()"
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
			_1008itemvalue1Str:"不能为空",	//内部机构号
			_1008itemvalue2Str:"不能为空",	//对公客户的客户代码
			_1008itemvalue3Str:"大于等于2个字符",	//关联集团代码
			_1008itemvalue4Str:"大于等于3个字符或2个汉字",	//关联集团名称
			_1008itemvalue5Str:"123456中的一个",	//关联关系类型
			
			_1008itemvalue6Str:"大于等于3个字符或2个汉字", //客户名称
//			_1008itemvalue7Str: "",	//工商注册编号
			_1008itemvalue8Str: "不能为空",	//集团成员数
			_1008itemvalue9Str: "为1或2",	//授信类型
//			_1008itemvalue21Str: "",	//资产总额
//			_1008itemvalue22Str: "",	//负债总额	
//			_1008itemvalue10Str: "",	//资产负债表类型
			_1008itemvalue11Str: "为8位，月最大为12，日最大为31"	//资产负债表日期
//			_1008itemvalue12Str: "",	//风险预警信号
//			_1008itemvalue13Str: ""	//关注事件
		
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