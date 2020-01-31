// jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
//			_1016itemvalue1: / /,	//内部机构号
//			_1016itemvalue2: / /,	//集团客户代码
			_1016itemvalue3: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,	//成员单位的客户名称
//			_1016itemvalue4: / /,	//成员单位的客户代码
			_1016itemvalue5: /^[\s]{0,}[0-9A-Za-z]{2,}$/	//授信号码
//			_1016itemvalue21: / /,	//授信额度
//			_1016itemvalue22: / /,	//其中：贷款授信额度
//			_1016itemvalue23: / /,	//贷款余额
//			_1016itemvalue24: / /,	//持有债券余额
//			_1016itemvalue25: / /,	//持有股权余额
//			_1016itemvalue26: / /,	//其他表内信用风险资产余额
//			_1016itemvalue27: / /,	//表外业务余额
//			_1016itemvalue28: / /,	//现有业务余额占用授信额度
//			_1016itemvalue29: / /,	//其中：贷款余额占用贷款授信额度
//			_1016itemvalue30: / /,	//尚可使用授信额度
//			_1016itemvalue31: / /	//其中：尚可使用贷款授信额度

		//	isSafe :  "$.fn.safe()"
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
//			_1016itemvalue1Str: "",	//内部机构号
//			_1016itemvalue2Str: "",	//集团客户代码
			_1016itemvalue3Str: "不能为空，且大于3个字符2个汉字",	//成员单位的客户名称
//			_1016itemvalue4Str: "",	//成员单位的客户代码
			_1016itemvalue5Str: "不能为空，且大于2个字符"	//授信号码
//			_1016itemvalue21Str: "",	//授信额度
//			_1016itemvalue22Str: "",	//其中：贷款授信额度
//			_1016itemvalue23Str: "",	//贷款余额
//			_1016itemvalue24Str: "",	//持有债券余额
//			_1016itemvalue25Str: "",	//持有股权余额
//			_1016itemvalue26Str: "",	//其他表内信用风险资产余额
//			_1016itemvalue27Str: "",	//表外业务余额
//			_1016itemvalue28Str: "",	//现有业务余额占用授信额度
//			_1016itemvalue29Str: "",	//其中：贷款余额占用贷款授信额度
//			_1016itemvalue30Str: "",	//尚可使用授信额度
//			_1016itemvalue31Str: ""	//其中：尚可使用贷款授信额度

			
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