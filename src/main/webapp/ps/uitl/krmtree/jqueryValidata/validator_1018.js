// jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
//			_1018itemvalue1: / /,  //内部机构号
//			_1018itemvalue2: / /,  //客户类型
//			_1018itemvalue3: / /,  //客户代码
			_1018itemvalue4: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/, //借款人姓名
			_1018itemvalue5: /^[\s]{0,}[0-9]{1,2}$/,  //有效身份证件类型
			_1018itemvalue6:  /.+/,  //证件号码
			_1018itemvalue7: /^[\s]{0,}[0-9]{17}$/, //贷款发放行代码
			_1018itemvalue8: /^[\s]{0,}[0-9]{2,}$/,  //贷款合同号
			_1018itemvalue9: /^[\s]{0,}[0-9]{2,}$/,  //借据号
			_1018itemvalue10: /^[\s]{0,}[1-9]{1}$/, //贷款品种
			_1018itemvalue11: /^[\s]{0,}[1-4]{1}$/,  //担保方式
			_1018itemvalue12: /^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,   //最近一次还款日期
			_1018itemvalue13: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,  //客户住址
			_1018itemvalue14: /^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,  //发放日期
			_1018itemvalue15: /^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,  //到期日期
			_1018itemvalue16: /^[\s]{0,}[0-9]{6}$/,  //住址行政区划代码
			_1018itemvalue17: /.+/,  //违约天数
			_1018itemvalue18: /^[\s]{0,}[1-5]{1}$/,  //五级分类
			_1018itemvalue19: /^[\s]{0,}[1-7]{1}$/  //还款方式
//			_1018itemvalue20: /^[2-12]{1}$/  //客户其他证件类型
//			_1018itemvalue21: / /,  //发放金额
//			_1018itemvalue22: / /,  //贷款余额
//			_1018itemvalue23: / /,  //违约金额
//			_1018itemvalue24: / /,  //最近一次还款金额
//			_1018itemvalue51: / / //客户其他证件号码

		//	isSafe :  "$.fn.safe()"
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
//			_1018itemvalue1Str:"",  //
//			_1018itemvalue2Str:"",  //
//			_1018itemvalue3Str:"",  //
			_1018itemvalue4Str:"不能为空，且大于等于3个字符或2个汉字",  //
			_1018itemvalue5Str:"不能为空，1或2位数字",  //
			_1018itemvalue6Str:"不能为空",  //
			_1018itemvalue7Str:"不能为空,且为17位",  //
			_1018itemvalue8Str:"不能为空,大于等于2个字符",  //
			_1018itemvalue9Str:"不能为空,大于等于2个字符",  //
			_1018itemvalue10Str:"不能为空,且为1-9中的1个字符",  //
			_1018itemvalue11Str:"不能为空,且为1-4中的1个字符", //
			_1018itemvalue12Str:"为8位，月最大为12，日最大为31",  //
			_1018itemvalue13Str:"不能为空,且大于等于3个字符或2个汉字",  //
			_1018itemvalue14Str:"不能为空,且为8位，月最大为12，日最大为31",  //
			_1018itemvalue15Str:"不能为空,且为8位，月最大为12，日最大为31", //
			_1018itemvalue16Str:"不能为空,且为6为数字字符",  //
			_1018itemvalue17Str:"不能为空",  //
			_1018itemvalue18Str:"不能为空,且为1-5位中的1个数字字符",  //
			_1018itemvalue19Str:"不能为空,且为1-7中的一个"//
//			_1018itemvalue20Str:"为2-12中的一个"  //
//			_1018itemvalue21Str:"",  //
//			_1018itemvalue22Str:"",  //
//			_1018itemvalue23Str:"",  //
//			_1018itemvalue24Str:"",  //
//			_1018itemvalue51Str:"若客户其他证件类型为2-11则非空，若为12则空" //
 	
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