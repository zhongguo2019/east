// jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
//			_1020itemvalue1: /.+/,	//客户代码
			_1020itemvalue2: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,	//学校名称
			_1020itemvalue3: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,	//学校地址
			_1020itemvalue4: /^[\s]{0,}[0-9]{6}$/,	//学校行政区划代码
//			_1020itemvalue5: / /,	//学生证号     若助学贷款类型12非空，
			_1020itemvalue6: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,	//贷款时家庭住址  若助学贷款类型12非空，
			_1020itemvalue7: /^[\s]{0,}[0-9]{6}$/,	//贷款时家庭住址行政区划代码
			_1020itemvalue8: /^[\s]{0,}[1-6]{1}$/	//助学贷款类型
//			_1020itemvalue9: / /	//内部机构号

		//	isSafe :  "$.fn.safe()"
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
//			_1020itemvalue1Str:"不能为空",//客户代码
			_1020itemvalue2Str:"大于等于3个字符或2个汉字",//学校名称
			_1020itemvalue3Str:"大于等于3个字符或2个汉字",//学校地址
			_1020itemvalue4Str:"6位数字字符",//学校行政区划代码
//			_1020itemvalue5Str:"",//学生证号     若助学贷款类型12非空，
			_1020itemvalue6Str:"大于等于3个字符或2个汉字",//贷款时家庭住址
			_1020itemvalue7Str:"6位数字字符",//贷款时家庭住址行政区划代码
			_1020itemvalue8Str:"为1-6中1个"  //助学贷款类型
//			_1020itemvalue9Str:""  //内部机构号
 				
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