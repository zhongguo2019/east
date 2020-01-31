// jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
//			_1013itemvalue1: / /,	//内部机构号
			_1013itemvalue2: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,	//表外业务客户名称
			_1013itemvalue3: /.+/,	//表外业务客户代码
			_1013itemvalue4: /.+/,	//承办银行机构代码
			_1013itemvalue5: /^[\s]{0,}[1-8]{1}$/,	//表外业务类型
			_1013itemvalue6: /^[\s]{0,}[0-9]{2,}$/,	//授信号码
//			_1013itemvalue7: / /,	//合同号     表外业务类型为1-6时非空，7时为空，8时必空
//			_1013itemvalue8: / /,	//业务号码  表外业务类型为123456的一个时，不能为空，7可为空，8-必为空
			_1013itemvalue21: /.+/,	//业务余额
			_1013itemvalue9: /.+/,	//币种代码
			_1013itemvalue10: /^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,	//发生日期
			_1013itemvalue11: /^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,	//到期日期
//			_1013itemvalue22: / /,	//保证金金额
			_1013itemvalue12: /^[\s]{0,}[1-3]{1}$/,	//产业结构调整类型    空
			_1013itemvalue13: /^[\s]{0,}[1-2]{1}$/,	//工业转型升级标识
			_1013itemvalue14: /^[\s]{0,}[1-7]{1}$/	//战略新兴产业类型

		//	isSafe :  "$.fn.safe()"
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
			
//			_1013itemvalue1Str: "",	//内部机构号
			_1013itemvalue2Str: "不能为空，且大于等于3个字符或2个汉字",	//表外业务客户名称
			_1013itemvalue3Str: "不能为空",	//表外业务客户代码
			_1013itemvalue4Str: "不能为空",	//承办银行机构代码
			_1013itemvalue5Str: "不能为空，且为其中之一1-银行承兑汇票2-信用证3-保函4-承诺5-委托贷款6-委托投资7-信用风险仍在银行的销售与购买协议8-金融衍生品",	//表外业务类型
			_1013itemvalue6Str: "不能为空，且长度大于等于2个字符",	//授信号码
			_1013itemvalue7Str: "表外业务类型为123456的一个时，不能为空，7可为空，8-必为空",	//合同号
			_1013itemvalue8Str: "表外业务类型为123456的一个时，不能为空，7可为空，8-必为空",	//业务号码
			_1013itemvalue21Str: "不能为空",	//业务余额
			_1013itemvalue9Str: "不能为空",	//币种代码
			_1013itemvalue10Str: "不能为空，且为8位，月最大为12，日最大为31",	//发生日期
			_1013itemvalue11Str: "不能为空，且为8位，月最大为12，日最大为31",	//到期日期
//			_1013itemvalue22Str: "",	//保证金金额
			_1013itemvalue12Str: "123其中之1",	//产业结构调整类型
			_1013itemvalue13Str: "1-是2-否",	//工业转型升级标识
			_1013itemvalue14Str: "1-节能环保2-新一代技术信息3-生物医药4-高端装备制造5-新能源6-新材料7-新能源汽车"	//战略新兴产业类型
 	
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