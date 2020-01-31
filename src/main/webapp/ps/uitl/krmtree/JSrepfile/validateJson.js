var resoureces=true;
var messages="";
var objs;
var res="";

	function init(jsondata){
		objs = eval(jsondata);
	}

	/**
	 * ����У��
	 * @param column_key ����
	 * @param price ��������ֵ
	 * @returns {Boolean} ���� true false; 
	 */
	function doRule(column_key,price){//�У�ֵ
		
		messages="";
		resoureces=true;
		var obj_keys = objs[column_key];//����
		 for(var values_key in obj_keys){
		 	var obj_key = obj_keys[values_key];//��õڼ��еľ������
			var funname = obj_key.funname;//ִ�е�ʲô����--����,�ǿ�
			var funparam = obj_key.funparam;//����
			var prompt = obj_key.prompt;//��ʾ��Ϣ
			
			try
			   { 
				  if(funname == "reg"){
	            	   res = doRuleSimaple(price,funname,funparam,prompt);
	              }else{
	          	       res = notnull(funparam,price,prompt);
	              }
			   }
			   catch (e){
				alert(e.message);
			} 
			if(!res){
				resoureces=false;
			}
		}
		return resoureces;
		
	}
	
	function getMessages(){
		return messages;
		
	}
	/**
	 * ��������
	 * @param price ֵ
	 * @param funname ִ�е�ʲô����--����,�ǿ�
	 * @param funparam ����
	 * @param prompt ��ʾ��Ϣ
	 * @returns 
	 */
	function doRuleSimaple(price,funname,funparam,prompt){
		var res = eval(funname+"("+funparam+",'"+price+"','"+prompt+"')");
		return res;
	}
	
	//����
	function reg(funparam,price,prompt){
		if(!funparam.exec(price)){
			messages+=prompt+"\n";
			return false;
		}else{
			return true;
		}
	}
	
	//�ǿ�
	function notnull(funparam,price,prompt){
		if(price==""){
			messages+=prompt+"\n"; 
			return false;
		}else{
			return true;
		}
	}

	
	