// jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
//			_50itemvalue1:/.+/,	// 数据日期
			_50itemvalue2:/.+/, // 金融机构编码 2-713
			_50itemvalue3:/^[\s]{0,}[0-1]{1}$/,//客户类型(单位个人客户标示)  
//			_50itemvalue4:/ /, //借款人代码（组织机构代码或个人证件号码）
//			_50itemvalue5:/.+/, //贷款主体行业类别 
//			_50itemvalue6:/.+/, //借款人注册地编码 
//			_50itemvalue7:/ /, //企业出资人经济成分
//			_50itemvalue8:/^\CS0[1-5]{1}$/,//企业规模
			_50itemvalue9:/.+/,// 贷款借据编码1-712
//			_50itemvalue10:/ /,// 产品类别 1-712
//			_50itemvalue11:/ /,//贷款实际投向 1-712
//			_50itemvalue12:/ /,//贷款发放日期1-712
//			_50itemvalue13:/ /,//贷款到期日期  1-712
//			_50itemvalue14:/ /,//贷款实际终止日期1-712
			_50itemvalue15:/^[\s]{0,}\CNY$/,// 贷款币种 1-712
			_50itemvalue16:/^(\RF01|\RF02)$/,//利率是否固定 2-713
//			_50itemvalue17:/ /,// 贷款担保方式2-713
			_50itemvalue18:/^(\FS01|\FS04|\FS05|\FS06)$/,// 贷款状态2-713
			_50itemvalue19:/^[\s]{0,}[0-1]{1}$/,//贷款发放收回标志 2-713
//			_50itemvalue21:/ /,//贷款发生金额
			_50itemvalue22:/^[\s]{0,}\d*[\.]?\d*[1-9]+\d*$/  //利率水平 2-713

			
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
//			_50itemvalue1Str:"数据日期不能为空",
			_50itemvalue2Str:"不能为空",
			_50itemvalue3Str:"为0或1",
//			_50itemvalue4Str:"",
//			_50itemvalue5Str:"",
//			_50itemvalue6Str:"借款人注册地编码不能为空",
//			_50itemvalue7Str:"",
//			_50itemvalue8Str:"企业规模有误",
			_50itemvalue9Str:"不能为空",
//			_50itemvalue10Str:"",
//			_50itemvalue11Str:"",
//			_50itemvalue12Str:"",
//			_50itemvalue13Str:"",
//			_50itemvalue14Str:"",
			_50itemvalue15Str:"为CNY",
			_50itemvalue16Str:"为RF01或RF02",
//			_50itemvalue17Str:"",
			_50itemvalue18Str:"为FS01.FS04.FS05.FS06其中之1",
			_50itemvalue19Str:"为0或1",
//			_50itemvalue21Str:"",
			_50itemvalue22Str:"必须大于0"

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