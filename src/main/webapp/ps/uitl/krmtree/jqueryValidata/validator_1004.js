// jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
//			_1004itemvalue1: /.+/,	//内部机构号
			_1004itemvalue2: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,	//客户名称	
			_1004itemvalue3: /.+/,	//客户代码/供应链编码
//			_1004itemvalue4: /.+/,	//工商注册编号
			_1004itemvalue5: /.+/,	//集团成员数
			_1004itemvalue6: /^[\s]{0,}[1-2]{1}$/,	//授信类型
//			_1004itemvalue21: /.+/,	//资产总额
//			_1004itemvalue22: /.+/,	//负债总额
//			_1004itemvalue7: /.+/,	//资产负债表类型
			_1004itemvalue8: /^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,	//资产负债表日期
//			_1004itemvalue9: /.+/,	//风险预警信号--
//			_1004itemvalue10: /.+/,	//关注事件--
//			_1004itemvalue11: /.+/,	//信用评级结果--
			_1004itemvalue12: /.+/,	//注册国家或地区--
			_1004itemvalue13: /.+/,	//国别代码--
			_1004itemvalue14:  /^[\s]{0,}([A-Za-z0-9 ]{3,}|[\u4e00-\u9fa5]{2,})$ /,	//国内注册地址--
			_1004itemvalue15: /^[\s]{0,}[0-9]{6}$/,	//行政区划代码--
			_1004itemvalue16:  /^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,	//更新注册信息日期--
			_1004itemvalue17: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,	//办公地址--
			_1004itemvalue18: /^[\s]{0,}[0-9]{6}$/,	//行政区划代码--
			_1004itemvalue19: /^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/	//更新办公地址日期--
			
		
		//	isSafe :  "$.fn.safe()"
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
			_1004itemvalue2Str:"大于等于3个字符或2个汉字",    //客户名称
			_1004itemvalue3Str:"不能为空",	//客户代码/供应链编码
			_1004itemvalue5Str:"不能为空",	//集团成员数
			_1004itemvalue8Str:"为8位，月最大为12，日最大为31", //资产负债表日期
			_1004itemvalue6Str:"为1或2", //授信类型
			_1004itemvalue14Str:"大于等于3个字符或2个汉字", //国内注册地址
			_1004itemvalue18Str:"6位数字字符", //行政区划代码
			_1004itemvalue16Str:"为8位，月最大为12，日最大为31", //更新注册信息日期
			_1004itemvalue17Str:"大于等于3个字符或2个汉字", //办公地址
			_1004itemvalue19Str:"为8位，月最大为12，日最大为31", //更新办公地址日期
			_1004itemvalue15Str:"6位数字字符", //行政区划代码
			_1004itemvalue13Str:"不能为空" //国别代码
			
//			_1004itemvalue1Str:"", //内部机构号
//			_1004itemvalue4Str:"", //工商注册编号
//			_1004itemvalue21Str:"", //资产总额
//			_1004itemvalue22Str:"", //负债总额
//			_1004itemvalue7Str:"", //资产负债表类型
//			_1004itemvalue9Str:"", //风险预警信号
//			_1004itemvalue10Str:"", //关注事件
//			_1004itemvalue11Str:"", //信用评级结果
//			_1004itemvalue12Str:"", //注册国家或地区
	
		 
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