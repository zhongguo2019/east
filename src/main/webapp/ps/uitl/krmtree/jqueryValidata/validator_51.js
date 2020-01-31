// jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
			_51itemvalue1:/.+/,   //数据日期
			_51itemvalue2:/.+/,	//金融机构编码902 
//			_51itemvalue3:/^[0-1]{1}$/,//客户类型2
//			_51itemvalue4:/.+/,	//借款人代码2
			_51itemvalue5:/.+/,	//贷款主体行业类型2
//			_51itemvalue6:/.+/,	//借款人注册地编码2
//			_51itemvalue7:/ /,	//企业出资人经济成分2
//			_51itemvalue8:/^\CS0[1-5]{1}$/,//企业规模2
//			_51itemvalue9:/.+/,	//贷款借据编码1
			_51itemvalue10:/.+/,	//产品类别	1
//			_51itemvalue11:/ /,	//贷款实际投向1
//			_51itemvalue12:/ /,	//贷款发放日期1
//			_51itemvalue13:/ /,	//贷款到期日期1
//			_51itemvalue14:/ /,	//展期到期日期1
			_51itemvalue15:/^[\s]{0,}\CNY$/,	//贷款币种1
			_51itemvalue16:/^\RF0[1-2]{1}$/,	//利率是否固定2
//			_51itemvalue17:/ /,	//贷款担保方式2
			_51itemvalue18:/^\FQ0[1-5]{1}$/,	//贷款质量2--
			_51itemvalue19:/^(\FS01|\FS04|\FS0[5-6]{1})$/,	//贷款状态2
//			_51itemvalue21:/.+/,	//贷款余额
			_51itemvalue22:/^[\s]{0,}\d*[\.]?\d*[1-9]+\d*$/	//利率水平2--
			
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
			_51itemvalue1Str:"不能为空",
			_51itemvalue2Str:"不能为空",
//			_51itemvalue3Str:"客户类型有误",
//			_51itemvalue4Str:"借款人代码不能为空",
			_51itemvalue5Str:"不能为空",
//			_51itemvalue6Str:"借款人注册编码不能为空",
//			_51itemvalue7Str:"",
//			_51itemvalue8Str:"企业规模有误",
//			_51itemvalue9Str:"贷款借据编码不能为空",
			_51itemvalue10Str:"不能为空",
//			_51itemvalue11Str:"",
//			_51itemvalue12Str:"",
//			_51itemvalue13Str:"",
//			_51itemvalue14Str:"",
			_51itemvalue15Str:"为CNY",
			_51itemvalue16Str:"为RF01或RF02",
//			_51itemvalue17Str:""
			_51itemvalue18Str:"为FQ01-FQ05之1",
			_51itemvalue19Str:"为FS01,FS04,FS05,FS06其1",
//			_51itemvalue21Str:"贷款余额不能为空",
			_51itemvalue22Str:"大于0"
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