// jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
			_1011itemvalue1: / /,	//内部机构号
			_1011itemvalue2: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,	//贷款客户名称
			_1011itemvalue3: /.+/,	//贷款客户代码
			_1011itemvalue4: /.+/,	//贷款发放行代码
			_1011itemvalue5: /^[\s]{0,}[0-9]{2,}$/,	//授信号码
			_1011itemvalue6: /.+/,	//贷款合同号
			_1011itemvalue7: /.+/,	//借据号
			_1011itemvalue8: /^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,	//发放日期
			_1011itemvalue9: /^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,	//到期日期
			_1011itemvalue21: /.+/,	//发放金额
			_1011itemvalue22: / /,	//贷款余额
			_1011itemvalue10: /^[\s]{0,}[1-5]{1}$/,	//五级分类   若货款余额大于0，则不能为空
			_1011itemvalue11: /^[\s]{0,}[1-6]{1}$/,	//贷款类型
			_1011itemvalue12: /^[\s]{0,}[1-4]{1}$/,	//贷款业务种类
			_1011itemvalue13: /.+/,	//投向行业
			_1011itemvalue14: /.+/,	//币种代码
			_1011itemvalue15: /^[\s]{0,}[1-4]{1}$/,	//担保方式
//			_1011itemvalue23: / /,	//欠本余额
//			_1011itemvalue16: / /,	//欠本天数
//			_1011itemvalue24: / /,	//欠息余额
//			_1011itemvalue17: / /,	//欠息天数
//			_1011itemvalue25: / /,	//本期还款
			_1011itemvalue18: /^[\s]{0,}[1-7]{1}$/,	//还本方式
			_1011itemvalue19: /^[\s]{0,}[1-6]{1}$/,	//还息方式
			_1011itemvalue20: /^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,	//下期还本日期
			_1011itemvalue51: /^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,	//下期还息日期
			_1011itemvalue52: /^[\s]{0,}[1-4]{1}$/,	//贷款发放类型
			_1011itemvalue53: /^[\s]{0,}[1-3]{1}$/,	//产业结构调整类型
			_1011itemvalue54: /^[\s]{0,}[1-2]{1}$/,	//工业转型升级标识
			_1011itemvalue55: /^[\s]{0,}[1-7]{1}$/,	//战略新兴产业类型
			_1011itemvalue56: /^[\s]{0,}[1-2]{1}$/,	//银团贷款标识
			_1011itemvalue57: /^[\s]{0,}[1-2]{1}$/	//支付方式
//			_1011itemvalue26: / /,	//下期还本金额
//			_1011itemvalue27: / /	//下期还息金额

		//	isSafe :  "$.fn.safe()"
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
			
			_1011itemvalue1Str: "",	//内部机构号
			_1011itemvalue2Str: "大于等于3个字符或2个汉字",	//贷款客户名称
			_1011itemvalue3Str: "不能为空",	//贷款客户代码
			_1011itemvalue4Str: "不能为空",	//贷款发放行代码
			_1011itemvalue5Str: "不能为空，大于等于两个字符",	//授信号码
			_1011itemvalue6Str: "不能为空",	//贷款合同号
			_1011itemvalue7Str: "不能为空",	//借据号
			_1011itemvalue8Str: "不能为空，为8位，月最大为12，日最大为31",	//发放日期
			_1011itemvalue9Str: "不能为空，为8位，月最大为12，日最大为31",	//到期日期
			_1011itemvalue21Str: "不能为空",	//发放金额
			_1011itemvalue22Str: "",	//贷款余额
			_1011itemvalue10Str: "1-正常2-关注3-次级4-可疑5-损单",	//五级分类
			_1011itemvalue11Str: "不能为空，1-贷款2-贴现3-贸易融资4-垫款5-法人账户投资",	//贷款类型
			_1011itemvalue12Str: "不能为空，1-流动资金贷款2-项目贷款3-一般固定资产贷款(除项目贷款外的固定资产贷款)4-其他贷款，之1",	//贷款业务种类
			_1011itemvalue13Str: "不能为空",	//投向行业
			_1011itemvalue14Str: "不能为空",	//币种代码
			_1011itemvalue15Str: "不能为空，为1-保证2-抵押3-质押4-信用，之1",	//担保方式
//			_1011itemvalue23Str: "",	//欠本余额
//			_1011itemvalue16Str: "",	//欠本天数
//			_1011itemvalue24Str: "",	//欠息余额
//			_1011itemvalue17Str: "",	//欠息天数
//			_1011itemvalue25Str: "",	//本期还款
			_1011itemvalue18Str: "不能为空，1-按月2-按季3-按半年4-按年5-到期一次还本6-按进度还款,,之一",	//还本方式
			_1011itemvalue19Str: "不能为空，1-按月2-按季3-按半年4-按年5-利随本清6-其他",	//还息方式
			_1011itemvalue20Str: "为8位，月最大为12，日最大为31",	//下期还本日期
			_1011itemvalue51Str: "为8位，月最大为12，日最大为31",	//下期还息日期
			_1011itemvalue52Str: "不能为空,且为1-新增2-展期3-借新还旧4-重组，之1",	//贷款发放类型
			_1011itemvalue53Str: "1-鼓励2-限制3-淘汰",	//产业结构调整类型
			_1011itemvalue54Str: "1-是2-否",	//工业转型升级标识
			_1011itemvalue55Str: "1-节能环保2-新一代信息技术3-生物医药4-高端装备制造5-新能源6-新材料7-新能源汽车",	//战略新兴产业类型
			_1011itemvalue56Str: "1-牵头行2-参与行",	//银团贷款标识
			_1011itemvalue57Str: "1-受托支付2-自主支付"	//支付方式
//			_1011itemvalue26Str: "",	//下期还本金额
//			_1011itemvalue27Str: ""	//下期还息金额
 
			
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