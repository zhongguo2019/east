//存款交易客户信息补录模板 jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
//			_1012itemvalue1:/ /,	//内部机构号
//			_1012itemvalue2:/ /,	//持有类型
			_1012itemvalue3:/^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,	//银行企业名称1
			_1012itemvalue4:/.+/,	//银行企业代码1
			_1012itemvalue5:/^[\s]{0,}[0-9]{17}$/,	//持有行机构代码1
//			_1012itemvalue6:/ /,	//客户代码
			_1012itemvalue7:/^[\s]{0,}[0-9]{2,}$/,	//授信号码1
			_1012itemvalue8:/^[\s]{0,}[0-9]{2,}$/,	//债券代码1
			_1012itemvalue9:/^[\s]{0,}[1-7]{1}$/,	//债券类型1
			_1012itemvalue21:/^[\s]{0,}(\d*[\.]?\d*|[\0.]?\d*[1-9]+\d*)$/,	//债券面值1
			_1012itemvalue22:/^[\s]{0,}(\d*[\.]?\d*|[\0.]?\d*[1-9]+\d*)$/,	//账面余额1
			_1012itemvalue10:/^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,	//发行日期1
			_1012itemvalue11:/^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,	//到期兑付日期1
			_1012itemvalue12:/^[\s]{0,}[1-2]{1}$/	//账户类型1
//			_1012itemvalue13:/ /,	//内部评级
//			_1012itemvalue14:/ /	//外部评级
	
			
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
		
			_1012itemvalue1Str:"",	//内部机构号
			_1012itemvalue2Str:"",	//持有类型
			_1012itemvalue3Str:"不能为空,且大于等于3个字符或2个汉字", 	//银行企业名称1
			_1012itemvalue4Str:"不能为空" ,	//银行企业代码1
			_1012itemvalue5Str:"不能为空,为17位代码",	//持有行机构代码1
//			_1012itemvalue6Str:"",	//客户代码
			_1012itemvalue7Str:"不能为空,且大于2个字符",	//授信号码1
			_1012itemvalue8Str:"不能为空,且大于2个字符", //债券代码1
			_1012itemvalue9Str:"不能为空,且为1-7之1",	//债券类型
			_1012itemvalue21Str:"不能为空,且大于0",	//债券面值1
			_1012itemvalue22Str:"不能为空,且大于等于0",	//账面余额1
			_1012itemvalue10Str:"不能为空，且为8位，月最大为12，日最大为31",	//发行日期1
			_1012itemvalue11Str:"不能为空，且为8位，月最大为12，日最大为31",	//到期兑付日期1
			_1012itemvalue12Str:"不能为空,1-银行账户2-交易账户，选其1"	//账户类型
//			_1012itemvalue13Str:"",	//内部评级
//			_1012itemvalue14Str:""	//外部评级

		},
		msg_en : {
			RequireStr : " cannot be empty",
			IPStr : " is not a valid IP address",
			EmailStr : " is not a valid email address",
			_1012itemvalue3Str:"cannot be empty" 
	
		},
		/** 添加其他语言提示信息
		msg_** : {
			RequireStr : " cannot be empty",
			IPStr : " is not a valid IP address",
			EmailStr : " is not a valid email address"
		},
		*/
		
			validator : function (formName,mode,isSubmit,single){
				debugger;
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