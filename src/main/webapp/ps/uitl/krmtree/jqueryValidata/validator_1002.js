// jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
			_1002itemvalue8: /.+/,  //内部机构号
//			_1002itemvalue1: / /,  //对公客户的客户代码
			_1002itemvalue2: /^[\s]{0,}[1-7]{1}$/,  //关系人类型
			_1002itemvalue3: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,  //姓名
			_1002itemvalue5:/.+/,   //国别代码
			_1002itemvalue4: /.+/,   //身份证号码
			_1002itemvalue6:/^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,         //更新信息日期
			_1002itemvalue7:/^[\s]{0,}[1-2]{1}$/,	//实际控制人标识
			
			_1002itemvalue9: /.+/,	//客户名称
//			_1002itemvalue10: / /,	//组织机构代码
//			_1002itemvalue11: / /,	//组织机构登记/年检/更新日期
//			_1002itemvalue12: / /,	//登记注册代码
//			_1002itemvalue13: / /,	//登记注册/年检/更新日期
			_1002itemvalue14: /.+/,	//注册国家或地区
			_1002itemvalue15: /.+/,	//国别代码
			_1002itemvalue16: /.+/,	//注册地址
			_1002itemvalue17: /^[\s]{0,}[0-9]{5}$/	//行政区划代码
//			_1002itemvalue18: / /,	//上市公司标志-国别代码-上市公司代码
//			_1002itemvalue19: / /,	//风险预警信号
//			_1002itemvalue20: / /	//关注事件
		
		//	isSafe :  "$.fn.safe()"
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
			_1002itemvalue8Str:"不能为空",
//			_1002itemvalue1Str:"",
			_1002itemvalue2Str:"为1-7,1-法定代表人2-董事长3-监事长4-总经理5-财务主管6-个人股东7-其他",
			_1002itemvalue3Str:"不能为空，且大于等于3个字符或2个汉字",
			_1002itemvalue5Str:"不能为空",
			_1002itemvalue4Str:"不能为空",
			_1002itemvalue6Str:"为8位，月最大为12，日最大为31",
			_1002itemvalue7Str:"不能为空,且只能是：1-是，2-否。",
			
			_1002itemvalue9Str: "不能为空",	//客户名称
//			_1002itemvalue10Str: "",	//组织机构代码
//			_1002itemvalue11tr: "",	//组织机构登记/年检/更新日期
//			_1002itemvalue12Str: "",	//登记注册代码
//			_1002itemvalue13Str: "",	//登记注册/年检/更新日期
			_1002itemvalue14Str: "不能为空",	//注册国家或地区
			_1002itemvalue15Str: "不能为空",	//国别代码
			_1002itemvalue161Str: "不能为空",	//注册地址
			_1002itemvalue17Str: "不能为空,5位字符"	//行政区划代码
//			_1002itemvalue18Str: "",	//上市公司标志-国别代码-上市公司代码
//			_1002itemvalue19Str: "",	//风险预警信号
//			_1002itemvalue20Str: ""	//关注事件
			 
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