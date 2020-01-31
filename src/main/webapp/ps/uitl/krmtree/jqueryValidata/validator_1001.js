// jquery表单校验代码
(function($){
	
	$.extend({
		// 定义校验规则
		reg : {
			Require: /.+/,
			IP : /^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,
			Email : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
			illegal:/(^[\u4e00-\u9fa5]+$)|(^[A-Z][a-zA-Z\s\d]$)/,
//			_1001itemvalue1: / /,//内部机构号
			_1001itemvalue2: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$ /,//客户名称
			_1001itemvalue3: /^[\s]{0,}[0-9A-Za-z]{1,}$ /,//客户代码
			_1001itemvalue4: /^[\s]{0,}[0-9A-Z]{9}$/,        //组织机构代码
			_1001itemvalue5: /^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/, //组织机构登记/年检/更新日期
//			_1001itemvalue6: / /,//登记注册代码
			_1001itemvalue7:  /^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/, //登记注册/年检/更新日期
			_1001itemvalue8: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,              //注册国家或地区-------------
			_1001itemvalue9: /.+/,      //----国别代码-------
			_1001itemvalue10: /^[\s]{0,}([0-9A-Za-z]{3,}|[\u4e00-\u9fa5]{2,})$/,         //---注册地址
			_1001itemvalue11:  /^[\s]{0,}[0-9]{6}$/,       //行政区划代码-----------
			_1001itemvalue12: /^[\s]{0,}[A-D]{1}$/,     //上市公司标志-国别代码-上市公司代码-
//			_1001itemvalue13: / /,//风险预警信号
			_1001itemvalue14: /^[\s]{0,}[0-9A-Za-z]{3}$/,      //关注事件---------------0k
			_1001itemvalue21: /^[\s]{0,}([0]\.\d{4}|[1]\.[0]{4})$/,//违约概率ok------------------------
//			_1001itemvalue15: / /,//信用评级结果
			_1001itemvalue22: /^[\s]{0,}[0-9]{1,}\.[0-9]{2}$/,//资产总额
			_1001itemvalue23: /^[\s]{0,}[0-9]{1,}\.[0-9]{2}$/,//负债总额
//			_1001itemvalue24: / /,//税前利润
			_1001itemvalue25: /^[\s]{0,}[0-9]{1,}\.[0-9]{2}$/,//主营业务收入
			_1001itemvalue26: /^[\s]{0,}[0-9]{1,}\.[0-9]{2}$/,//存货
//			_1001itemvalue27: / /,//应收账款
			_1001itemvalue28: /^[\s]{0,}[0-9]{1,}\.[0-9]{2}$/,//其他应收款
			_1001itemvalue29: /^[\s]{0,}[0-9]{1,}\.[0-9]{2}$/,//流动资产合计
			_1001itemvalue30: /^[\s]{0,}[0-9]{1,}\.[0-9]{2}$/,//流动负债合计
			_1001itemvalue16: /^[\s]{0,}[1-4]{1}$/,      //财务报表类型     p+q+|R|+s大于0则非空
			_1001itemvalue17: /^[\s]{0,}[1-9]{1}[0-9]{3}[0-1]{1}[1-9]{1}[0-2]{1}[0-9]{1}$/,      //--------财务报表日期 p+q+|R|+s大于0则非空
			_1001itemvalue18: /^[\s]{0,}([B-F]{1}|\A[1-4]{1})$/,       //客户类型   1或2位字符------------
			_1001itemvalue19: /^[\s]{0,}[0-9A-Za-z]{5}$/,      //客户所属行业代码---------------
			_1001itemvalue20: /^[\s]{0,}([0-9]{16}|[0-9]{18})$/     //贷款卡号------------
//			_1001itemvalue51: / /   //环境和社会风险分类
				
				
				
				
		//	isSafe :  "$.fn.safe()"
		},
		// 定义错误信息，可支持国际化
		msg_zh : {
			RequireStr: "不能为空",
			IPStr : "不是有效的IP地址",
			EmailStr : "不是有效的邮箱地址",
			illegalStr:"包含非法字符",
		
//			_1001itemvalue1Str: "",//内部机构号
			_1001itemvalue2Str: "不能为空，大于等于3个字符或2个汉字",//客户名称
			_1001itemvalue3Str: "不能为空，长度大于一个字符",//客户代码
			_1001itemvalue4Str: "为9位字符，数字或大写字母",       //组织机构代码   若国别代码为CHN，则非空
			_1001itemvalue5Str: "为8位，月最大为12，日最大为",       //组织机构登记/年检/更新日期    若组织机构代码非空则非空
//			_1001itemvalue6Str: "",//登记注册代码
			_1001itemvalue7Str: "为8位，月最大为12，日最大为",       //登记注册/年检/更新日期    登记注册代码非空则非空
			_1001itemvalue8Str: "不能为空，大于等于3个字符或2个汉字",            //注册国家或地区
			_1001itemvalue9Str: "不能为空",     //国别代码
			_1001itemvalue10Str: "不能为空，大于等于3个字符或2个汉字",         //注册地址
			_1001itemvalue11Str: "不能为空，为6位数字字符",      //行政区划代码
			_1001itemvalue12Str: "A-A股上市，B-B股上市，C-H股上市，D-F股海外上市",     //上市公司标志-国别代码-上市公司代码      不小于两个字符
//			_1001itemvalue13Str: "",//风险预警信号
			_1001itemvalue14Str: "为3个字符",      //关注事件
			_1001itemvalue21Str: "大于0小于1，保留4位小数",//违约概率
//			_1001itemvalue15Str: "",//信用评级结果
			_1001itemvalue22Str: "大于等于0，保留两位小数",//资产总额
			_1001itemvalue23Str: "大于等于0，保留两位小数",//负债总额
//			_1001itemvalue24Str: "",//税前利润
			_1001itemvalue25Str: "大于等于0，保留两位小数",//主营业务收入
			_1001itemvalue26Str: "大于等于0，保留两位小数",//存货
			_1001itemvalue27Str: "大于等于0，保留两位小数",//应收账款
			_1001itemvalue28Str: "大于等于0，保留两位小数",//其他应收款
			_1001itemvalue29Str: "大于等于0，保留两位小数",//流动资产合计
			_1001itemvalue30Str: "大于等于0，保留两位小数",//流动负债合计
			_1001itemvalue16Str: "类型应该为1-年报,2-半年报,3-季报,4-月报中的一个",      //财务报表类型
			_1001itemvalue17Str: "为8位，月最大为12，日最大为31",      //财务报表日期
			_1001itemvalue18Str: "A1-大型企业,A2-中型企业,A3-小型企业,A4-微型企业,B-机关,C-事业单位,D-社会团体,E-其他,F-境外",      //客户类型
			_1001itemvalue19Str: "不能为空，且5个字符",     //客户所属行业代码
			_1001itemvalue20Str: "为16或18为数字"     //贷款卡号
//			_1001itemvalue51Str: ""  //环境和社会风险分类
				
				
				
				
				
	
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