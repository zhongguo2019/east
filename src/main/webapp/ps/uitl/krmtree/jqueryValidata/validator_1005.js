// jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4] \d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
//			_1005itemvalue1:/.+/,	//内部机构号
//			_1005itemvalue2:/.+/,	//对公客户的客户代码	
//			_1005itemvalue3:/.+/,	//关系人的客户代码
			_1005itemvalue4:/.+/,	//关系人类型
			_1005itemvalue5:/^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,	//姓名
			_1005itemvalue6:/.+/,	//国别代码
//			_1005itemvalue7:/.+/,	//身份证号码
			_1005itemvalue8:/^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,	//更新信息日期
//			_1005itemvalue9:/.+/,	//实际控制人标识
			_1005itemvalue10: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,//客户名称
//			_1005itemvalue11: / /,	//工商注册编号
			_1005itemvalue12: /.+/,	//集团成员数
			_1005itemvalue13: /^[\s]{0,}[1-2]{1}$/,	//授信类型
//			_1005itemvalue21: / /,	//资产总额
//			_1005itemvalue22: / /,	//负债总额	
//			_1005itemvalue14: / /,	//资产负债表类型
			_1005itemvalue15: /^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/	//资产负债表日期
//			_1005itemvalue16: / /,	//风险预警信号
//			_1005itemvalue17: / /	//关注事件
			
			
		//	isSafe :  "$.fn.safe()"
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
//			_1005itemvalue1Str:"不能为空",  //内部机构号
//			_1005itemvalue2Str:"不能为空",  //对公客户的客户代码
//			_1005itemvalue3Str:"不能为空",  //关系人的客户代码
			_1005itemvalue4Str:"不能为空",  //关系人类型
			_1005itemvalue5Str:"大于等于3个字符或2个汉字",  //姓名
			_1005itemvalue6Str:"不能为空",  //国别代码
//			_1005itemvalue7Str:"不能为空",  //身份证号码
			_1005itemvalue8Str:"为8位，月最大为12，日最大为31",  //更新信息日期
//			_1005itemvalue9Str:"不能为空",  //实际控制人标识
			
			_1005itemvalue10Str:"大于等于3个字符或2个汉字",  //客户名称
//			_1005itemvalue11: / /,	//工商注册编号
			_1005itemvalue12: "不能为空",	//集团成员数
			_1005itemvalue13: "为1或2",	//授信类型
//			_1005itemvalue21: / /,	//资产总额
//			_1005itemvalue22: / /,	//负债总额	
//			_1005itemvalue14: / /,	//资产负债表类型
			_1005itemvalue15: "为8位，月最大为12，日最大为31"	//资产负债表日期
//			_1005itemvalue16: / /,	//风险预警信号
//			_1005itemvalue17: / /	//关注事件
		
		 
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