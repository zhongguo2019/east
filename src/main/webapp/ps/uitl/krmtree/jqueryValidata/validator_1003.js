// jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
//			_1003itemvalue1: /.+/,  //内部机构号       没有校验规则
//			_1003itemvalue2: /.+/,	//对公客户的客户代码   没有校验规则
			_1003itemvalue3: /^[\s]{0,}[1-5]{1}/,  //关联类型
//			_1003itemvalue4: /.+/,	//股东/关联企业名称
			_1003itemvalue5: /^[\s]{0,}[1-6]{1}$/,	//股东/关联企业类型
			_1003itemvalue6: /^[\s]{0,}[1-12]{1}$ /,	//股东/关联企业证件类型
//			_1003itemvalue7: /.+/,	//股东/关联企业证件代码	
			_1003itemvalue8: /.+/,	//登记注册代码	
			_1003itemvalue9: /.+/,	//股东/关联企业客户代码
			_1003itemvalue10: /.+/,	//国别代码
			_1003itemvalue21: /.+/,	//持股比例
//			_1003itemvalue11: /^[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,	//股东结构对应日期    若持股比例大于0，则非空
//			_1003itemvalue12: /.+/,	//更新信息日期
			_1003itemvalue13: /^[\s]{0,}[1-2]{1}$/,	//实际控制人标识
			
			_1003itemvalue14: /.+/,	//客户名称
//			_1003itemvalue15: / /,	//组织机构代码
//			_1003itemvalue16: / /,	//组织机构登记/年检/更新日期
//			_1003itemvalue17: / /,	//登记注册代码
//			_1003itemvalue18: / /,	//登记注册/年检/更新日期
			_1003itemvalue19: /.+/,	//注册国家或地区
			_1003itemvalue20: /.+/,	//国别代码
			_1003itemvalue51: /.+/,	//注册地址
			_1003itemvalue52: /^[\s]{0,}[0-9]{5}$/	//行政区划代码
//			_1003itemvalue53: / /,	//上市公司标志-国别代码-上市公司代码
//			_1003itemvalue54: / /,	//风险预警信号
//			_1003itemvalue55: / /	//关注事件
			
			
		//	isSafe :  "$.fn.safe()"
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
			
//			_1003itemvalue1Str: "",  //内部机构号
//			_1003itemvalue2Str: "",	//对公客户的客户代码
			_1003itemvalue3Str: "为1-5其中之1",  //关联类型
			_1003itemvalue4Str: "",	//股东/关联企业名称
			_1003itemvalue5Str: "1-企业2-机关3-事业单位4-社会团体5-其他组织机构6-自然人",	//股东/关联企业类型
			_1003itemvalue6Str: "1-12其中之1",	//股东/关联企业证件类型
//			_1003itemvalue7Str: "",	//股东/关联企业证件代码	
//			_1003itemvalue8Str: "",	//登记注册代码	
//			_1003itemvalue9Str: "",	//股东/关联企业客户代码
			_1003itemvalue10Str: "不能为空",	//国别代码
//			_1003itemvalue11Str: "持股比例大于0，则非空8位字符",	//股东结构对应日期
//			_1003itemvalue12Str: "",	//更新信息日期
			_1003itemvalue13Str: "1-是2-否",	//实际控制人标识
			
			_1003itemvalue14Str: "不能为空",	//客户名称
//			_1003itemvalue15Str: "",	//组织机构代码
//			_1003itemvalue16Str: "",	//组织机构登记/年检/更新日期
//			_1003itemvalue17Str: "",	//登记注册代码
//			_1003itemvalue18Str: "",	//登记注册/年检/更新日期
			_1003itemvalue19Str: "不能为空",	//注册国家或地区
			_1003itemvalue20Str: "不能为空",	//国别代码
			_1003itemvalue51Str: "不能为空",	//注册地址
			_1003itemvalue52Str: "不能为空,5位字符"	//行政区划代码
//			_1003itemvalue53Str: "",	//上市公司标志-国别代码-上市公司代码
//			_1003itemvalue54Str: "",	//风险预警信号
//			_1003itemvalue55Str: ""	//关注事件
//			 
			
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