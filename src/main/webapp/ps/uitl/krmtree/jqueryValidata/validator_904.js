//获取帐务信息 jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
//			_904itemvalue1:/.+/,	//	客户编号
//			_904itemvalue2:/.+/,		//	客户名称 
//			_904itemvalue4:/.+/,	//所属分支机构
//			_904itemvalue5:/ /,		//机构名称 
			_904itemvalue6:/.+/,		// 贷款借据编码 
//			_904itemvalue7:/ /,		//资产负债类型
//			_904itemvalue8:/ /,		// 产品类别
//			_904itemvalue9:/ /,		// 贷款实际投向   
//			_904itemvalue10:/ /,		// 贷款财政扶持标识  
//			_904itemvalue11:/ /,		//贷款财政扶持方式 
//			_904itemvalue12:/ /,		// 抵减存款准备金方式
//			_904itemvalue13:/ /,		// 贷款发放日期
//			_904itemvalue14:/ /,		//贷款到期日期  
//			_904itemvalue15:/ /,		//贷款实际终止日期
//			_904itemvalue16:/ /,		//展期起始日期
//			_904itemvalue17:/ /,		// 展期到期日期 
			_904itemvalue18:/^[\s]{0,}\CNY$/,		//币种 
//			_904itemvalue19:/ /,		// 借据状态
//			_904itemvalue20:/ /,		//贷款账号
			_904itemvalue21:/^[\s]{0,}\d*[\.]?\d*[1-9]+\d*$/,		//贷款借据金额 
			_904itemvalue22:/^[\s]{0,}\d*[\.]?\d*[1-9]+\d*$/		//贷款借据余额 
//			_904itemvalue51:/ /,		//还款账号  
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
//			_904itemvalue1Str:"客户编号不能为空",
//			_904itemvalue2Str:"客户名称不能为空",
//			_904itemvalue4Str:"所属分支机构不能为空",
//			_904itemvalue5Str:"",
			_904itemvalue6Str:"不能为空",
//			_904itemvalue7Str:"",
//			_904itemvalue8Str:"",
//			_904itemvalue9Str:"",
//			_904itemvalue10Str:"",
//			_904itemvalue11Str:"",
//			_904itemvalue12Str:"",
//			_904itemvalue13Str:"",
//			_904itemvalue14Str:"",
//			_904itemvalue15Str:"",
//			_904itemvalue16Str:"",
//			_904itemvalue17Str:"",
			_904itemvalue18Str:"为CNY",
//			_904itemvalue19Str:"",
//			_904itemvalue20Str:"",
			_904itemvalue21Str:"不为空且大于0",
			_904itemvalue22Str:"不为空且大于0"
//			_904itemvalue51Str:"",
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