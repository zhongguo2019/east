// jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
			_1015itemvalue1: /.+/,	//内部机构号
			_1015itemvalue2: /^[\s]{0,}[1-3]{1}$/,	//客户类型
			_1015itemvalue3: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,	//客户名称
			_1015itemvalue4: /.+/,	//客户代码
			_1015itemvalue5: /.+/,	//客户国籍代码
			_1015itemvalue6: /.+/,	//授信银行代码
//			_1015itemvalue7: / /,	//授信银行名称
			_1015itemvalue8: /^[\s]{0,}[0-9]{2,}$/,	//授信号码
//			_1015itemvalue21: / /,	//授信额度
//			_1015itemvalue22: / /,	//其中：贷款授信额度    客户类型为，同业客户则为空或0
//			_1015itemvalue9: / /,	//授信起始日期
//			_1015itemvalue10: / /,	//授信到期日期
//			_1015itemvalue23: / /,	//贷款余额
//			_1015itemvalue24: / /,	//持有债券余额
//			_1015itemvalue25: / /,	//持有股权余额
//			_1015itemvalue26: / /,	//其他表内信用风险资产余额
//			_1015itemvalue27: / /,	//表外业务余额
//			_1015itemvalue28: / /,	//现有业务余额占用授信额度
//			_1015itemvalue29: / /,	//其中：贷款余额占用贷款授信额度
//			_1015itemvalue30: / /,	//尚可使用授信额度
//			_1015itemvalue31: / /,	//其中：尚可使用贷款授信额度
			_1015itemvalue11: /^[\s]{0,}[1-2]{1}$/	//关联方授信标识
//			_1015itemvalue12: / /,	//是否纳入授信业务
//			_1015itemvalue13: / /	//是否集团客户

		//	isSafe :  "$.fn.safe()"
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
	
			_1015itemvalue1Str: "",	//内部机构号
			_1015itemvalue2Str: "不能为空，1-集团客户2-单一法人客户3-同业客户",	//客户类型
			_1015itemvalue3Str: "不能为空，大于等于3个字符或2个汉字",	//客户名称
			_1015itemvalue4Str: "不能为空",	//客户代码
			_1015itemvalue5Str: "不能为空",	//客户国籍代码
			_1015itemvalue6Str: "不能为空",	//授信银行代码
//			_1015itemvalue7Str:  "",	//授信银行名称
			_1015itemvalue8Str:  "不能为空,且大于等于两个字符",	//授信号码
//			_1015itemvalue21Str: "",	//授信额度
//			_1015itemvalue22Str:  "客户类型为3时，为空或0",	//其中：贷款授信额度
//			_1015itemvalue9Str:  "除授信号码为WNRSXYW外，不能为空",	//授信起始日期
//			_1015itemvalue10Str:  "除授信号码为WNRSXYW外，不能为空",	//授信到期日期
//			_1015itemvalue23Str:  "除客户类型为3-同业客户时，为空或0，其他不能为空",	//贷款余额
//			_1015itemvalue24Str:  "除客户类型为3-同业客户时，为空或0，其他不能为空",	//持有债券余额
//			_1015itemvalue25Str:  "除客户类型为3-同业客户时，为空或0，其他不能为空",	//持有股权余额
//			_1015itemvalue26Str:  "除客户类型为3-同业客户时，为空或0，其他不能为空",	//其他表内信用风险资产余额
//			_1015itemvalue27Str:  "除客户类型为3-同业客户时，为空或0，其他不能为空",	//表外业务余额
//			_1015itemvalue28Str:  "除客户类型为3-同业客户时，为空或0，其他不能为空",	//现有业务余额占用授信额度
//			_1015itemvalue29Str:  "除客户类型为3-同业客户时，为空或0，其他不能为空",	//其中：贷款余额占用贷款授信额度
//			_1015itemvalue30Str:  "除客户类型为3-同业客户时，为空或0，其他不能为空",	//尚可使用授信额度
//			_1015itemvalue31Str:  "除客户类型为3-同业客户时，为空或0，其他不能为空",	//其中：尚可使用贷款授信额度
			_1015itemvalue11Str:  "不能为空，且为1-是2-否，之1"	//关联方授信标识
//			_1015itemvalue12Str:  "",	//是否纳入授信业务
//			_1015itemvalue13Str:  ""	//是否集团客户
	
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