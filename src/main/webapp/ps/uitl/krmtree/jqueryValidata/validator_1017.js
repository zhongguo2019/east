// jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
			_1017itemvalue1: /.+/,	//内部机构号
//			_1017itemvalue2: / /,	//个人对公客户类型
//			_1017itemvalue3: / /,	//客户类型
			_1017itemvalue4: /^[\s]{0,}[0-9]{1}$/,	//客户号
			_1017itemvalue5: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,	//借款人姓名
			_1017itemvalue6: /^[\s]{0,}[1-12]{1}$/,	//有效身份证件类型
			_1017itemvalue7: /^[\s]{0,}[0-9A-Za-z]{2,}$/,	//证件号码
			_1017itemvalue8: /^[\s]{0,}[0-9A-Za-z]{2,}$/,	//贷款合同号
			_1017itemvalue9: /^[\s]{0,}[0-9A-Za-z]{2,}$/,	//担保合同号
			_1017itemvalue10: /^[\s]{0,}[1-6]{1}$/,	//担保合同类型
//			_1017itemvalue11: / /,	//押品类型   两个字符       担保合同类型12非空3-6空
//			_1017itemvalue12: / /, 	//押品名称
//			_1017itemvalue13: / /,	//押品代码
			_1017itemvalue14: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,	//押品权属人（或保证人）名称
			_1017itemvalue15: /^[\s]{0,}[1-2]{1}$/,	//押品权属人（或保证人）类型
			_1017itemvalue16: /^[\s]{0,}[1-11]{1}$/,	//押品权属人（或保证人）证件类型
//			_1017itemvalue17: / /,	//押品权属人（或保证人）证件代码
//			_1017itemvalue21: / /,	//押品评估价值（或保证金额）
			_1017itemvalue18: /^[\s]{0,}[1-2]{1}$/	//押品权属人是否第三方
//			_1017itemvalue19: /^[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,	//首次估值日期
//			_1017itemvalue20: /^[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,	//最新估值日期
//			_1017itemvalue51: /^[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/	//估值到期日期
//			_1017itemvalue22: / /,	//保证人保证能力上限
//			_1017itemvalue23: / /,	//审批抵质押率
//			_1017itemvalue24: / /	//实际抵质押率

		//	isSafe :  "$.fn.safe()"
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
			_1017itemvalue1Str: "不能为空" ,	//内部机构号
//			_1017itemvalue2Str: "",	//个人对公客户类型
//			_1017itemvalue3Str:"",	//客户类型
			_1017itemvalue4Str: "不能为空，且为1位数字字符",	//客户号
			_1017itemvalue5Str:"大于等于3个字符或2个汉字",	//借款人姓名
			_1017itemvalue6Str: "为1-12",	//有效身份证件类型
			_1017itemvalue7Str: "大于2个字符",	//证件号码
			_1017itemvalue8Str: "不能为空，且大于等于2个字符",	//贷款合同号
			_1017itemvalue9Str: "不能为空，且大于等于2个字符",	//担保合同号
			_1017itemvalue10Str: "不能为空,且为1-6中的一个",	//担保合同类型
//			_1017itemvalue11Str: "担保合同类型12非空3-6空",	//押品类型
//			_1017itemvalue12Str: "担保合同类型12非空3-6空",	//押品名称
//			_1017itemvalue13Str: "担保合同类型12非空3-6空",	//押品代码
			_1017itemvalue14Str: "不能为空,且大于等于3个字符或2个汉字",	//押品权属人（或保证人）名称
			_1017itemvalue15Str: "不能为空，且为1位数字字符",	//押品权属人（或保证人）类型
			_1017itemvalue16Str: "不能为空，且为1-11中的1个",	//押品权属人（或保证人）证件类型
//			_1017itemvalue17Str: "押品权属人（或保证人）证件类型1-11非空，12空",	//押品权属人（或保证人）证件代码
//			_1017itemvalue21Str: "大于等于0保留2位小数",	//押品评估价值（或保证金额）
			_1017itemvalue18Str: "不能为空，1-是2-否"	//押品权属人是否第三方
//			_1017itemvalue19Str: "担保合同类型为12非空3-6空，且不为空时为8位，月最大为12，日最大为31",	//首次估值日期    担保合同类型为12非空3-6空
//			_1017itemvalue20Str: "担保合同类型为12非空3-6空，且不为空时为8位，为8位，月最大为12，日最大为31",	//最新估值日期
//			_1017itemvalue51Str: "担保合同类型为12非空3-6空，且不为空时为8位，为8位，月最大为12，日最大为31"	//估值到期日期
//			_1017itemvalue22Str: "担保合同类型为12非空或03-6空",	//保证人保证能力上限
//			_1017itemvalue23Str: "担保合同类型为12非空3-6空",	//审批抵质押率
//			_1017itemvalue24Str: "担保合同类型为12非空3-6空"	//实际抵质押率
			
 	
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