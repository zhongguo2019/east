//贷款交易信息 jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
//			_905itemvalue1:/.+/,	//客户编号
//			_905itemvalue2:/.+/,//客户名称 
			_905itemvalue3:/.+/, //金融机构编码
//			_905itemvalue4:/.+/,// 所属分支机构
//			_905itemvalue5:/ /,//机构名称
//			_905itemvalue6:/ /,// 贷款合同号
			_905itemvalue7:/.+/, //贷款借据编码
			_905itemvalue8:/^[\s]{0,}\RF0[1-2]{1}$/,//利率是否固定 
//			_905itemvalue9:/ /,//贷款利率重新定价日
//			_905itemvalue10:/ /,//贷款担保方式
			_905itemvalue11:/^[\s]{0,}\FQ0[1-5]{1}$/,//贷款质量
			_905itemvalue12:/^[\s]{0,}(\FS01|\FS04|\FS05|\FS06)$/,// 贷款状态   FS01 FS04 FS05 FS06
//			_905itemvalue13:/ /,//贷款迁徙方式
			_905itemvalue14:/^[\s]{0,}[0-1]{1}$/,//贷款回收标志
//			_905itemvalue15:/ /,// 还款方式 
//			_905itemvalue16:/ /,// 贷款账号  
//			_905itemvalue17:/ /,// 还款账号 
			_905itemvalue21:/^[\s]{0,}\d*[\.]?\d*[1-9]+\d*$/,//利率水平
			_905itemvalue22:/^[\s]{0,}\d*[\.]?\d*[1-9]+\d*$/,   //贷款借据金额
			_905itemvalue23:/^[\s]{0,}\d*[\.]?\d*[1-9]+\d*$/   //贷款借据余额
			
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
//			_905itemvalue1Str:"客户编号不能为空", 
//			_905itemvalue2Str:"客户名称不能为空", 
			_905itemvalue3Str:"不能为空", 
//			_905itemvalue4Str:"所属分支机构不能为空", 
//			_905itemvalue5Str:"", 
//			_905itemvalue6Str:"", 
			_905itemvalue7Str:"不能为空", 
			_905itemvalue8Str:"为RF01或RF02", 
//			_905itemvalue9Str:"", 
//			_905itemvalue10Str:"", 
			_905itemvalue11Str:"为FQ01-FQ05之1", 
//			_905itemvalue12Str:"该状态不存在", 
//			_905itemvalue13Str:"", 
			_905itemvalue14Str:"为0或1", 
//			_905itemvalue15Str:"", 
//			_905itemvalue16Str:"", 
//			_905itemvalue17Str:"", 
			_905itemvalue21Str:"必须大于0", 
			_905itemvalue22Str:"必须大于0", 
			_905itemvalue23tr:"必须大于0" 
		
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