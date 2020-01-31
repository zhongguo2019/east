// jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
//			_1014itemvalue1: / /,	//内部机构号
			_1014itemvalue2: /.+/,	//客户名称
			_1014itemvalue3: /.+/,	//客户代码
			_1014itemvalue4: /.+/,	//国别代码
			_1014itemvalue5: /.+/,	//非现场监管统计机构编码
			_1014itemvalue6: /.+/,	//组织机构代码
			_1014itemvalue7: /^[1-27]{1}$/	//客户类别
//			_1014itemvalue8: / /,	//内部评级
//			_1014itemvalue9: / /,	//外部评级
//			_1014itemvalue21: / /,	//拆放同业
//			_1014itemvalue22: / /,	//存放同业
//			_1014itemvalue23: / /,	//债券回购
//			_1014itemvalue24: / /,	//股票质押贷款
//			_1014itemvalue25: / /,	//买入返售资产
//			_1014itemvalue26: / /,	//买断式转贴现
//			_1014itemvalue27: / /,	//持有债券
//			_1014itemvalue28: / /,	//股权投资
//			_1014itemvalue29: / /,	//同业代付
//			_1014itemvalue30: / /,	//其他表内业务
//			_1014itemvalue31: / /,	//卖出回购资产
//			_1014itemvalue32: / /,	//不可撤销的承诺及或有负债
//			_1014itemvalue33: / /	//其他表外业务

		//	isSafe :  "$.fn.safe()"
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
//			_1014itemvalue1Str: "",	//内部机构号
			_1014itemvalue2Str: "不能为空",	//客户名称
			_1014itemvalue3Str: "不能为空",	//客户代码
			_1014itemvalue4Str: "不能为空",	//国别代码
			_1014itemvalue5Str: "不能为空",	//非现场监管统计机构编码
			_1014itemvalue6Str: "不能为空",	//组织机构代码
			_1014itemvalue7Str: "为1-27中的一个"	//客户类别
//			_1014itemvalue8Str: "",	//内部评级
//			_1014itemvalue9Str: "",	//外部评级
//			_1014itemvalue21Str: "", //拆放同业
//			_1014itemvalue22Str: "", //存放同业
//			_1014itemvalue23Str: "", //债券回购
//			_1014itemvalue24Str: "", //股票质押贷款
//			_1014itemvalue25Str: "", //买入返售资产
//			_1014itemvalue26Str: "", //买断式转贴现
//			_1014itemvalue27Str: "", //持有债券
//			_1014itemvalue28Str: "", //股权投资
//			_1014itemvalue29Str: "", //同业代付
//			_1014itemvalue30Str: "", //其他表内业务
//			_1014itemvalue31Str: "", //卖出回购资产
//			_1014itemvalue32Str: "", //不可撤销的承诺及或有负债
//			_1014itemvalue33Str: "" //其他表外业务
	
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