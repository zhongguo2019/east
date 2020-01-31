package com.krm.ps.framework.common.web.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

public class TreeJson implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String sid;
    private String id ; 
    private String pid ; 
    private String text ; 
    private String state ; 
    private JSONObject attributes = new JSONObject() ; 
    private List<TreeJson> children = new ArrayList<TreeJson>() ;
    
    
    public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	

	public JSONObject getAttributes() {
		return attributes;
	}

	public void setAttributes(JSONObject attributes) {
		this.attributes = attributes;
	}

	public List<TreeJson> getChildren() {
		return children;
	}

	public void setChildren(List<TreeJson> children) {
		this.children = children;
	}

	/******** setter and getter **********/

public static List<TreeJson> formatTree(List<TreeJson> list) {

        TreeJson root = new TreeJson();
        TreeJson node = new TreeJson();
        List<TreeJson> treelist = new ArrayList<TreeJson>();// ƴ�պõ�json��ʽ������
        List<TreeJson> parentnodes = new ArrayList<TreeJson>();// parentnodes������еĸ��ڵ�
        
        if (list != null && list.size() > 0) {
            root = list.get(0) ;
            //ѭ������oracle����ѯ�����нڵ�
            for (int i = 1; i < list.size(); i++) {
                node = list.get(i);
                if(node.getPid().equals(root.getId())){
                    //Ϊtree root �����ӽڵ�
                    parentnodes.add(node) ;
                    root.getChildren().add(node) ;
                }else{//��ȡroot�ӽڵ�ĺ��ӽڵ�
                    getChildrenNodes(parentnodes, node);
                    parentnodes.add(node) ;
                }
            }    
        }
        treelist.add(root) ;
        return treelist ;

    }

    private static void getChildrenNodes(List<TreeJson> parentnodes, TreeJson node) {
        //ѭ���������и��ڵ��node����ƥ�䣬ȷ�����ӹ�ϵ
        for (int i = parentnodes.size() - 1; i >= 0; i--) {
            
            TreeJson pnode = parentnodes.get(i);
            //����Ǹ��ӹ�ϵ��Ϊ���ڵ������ӽڵ㣬�˳�forѭ��
            if (pnode.getId().equals(node.getPid())) {
                pnode.setState("closed") ;//�رն�����
                pnode.getChildren().add(node) ;
                return ;
            } else {
                //������Ǹ��ӹ�ϵ��ɾ�����ڵ�ջ�ﵱǰ�Ľڵ㣬
                //�����˴�ѭ����ֱ��ȷ�����ӹ�ϵ�򲻴����˳�forѭ��
                parentnodes.remove(i) ;
            }
        }
    }
    
    public String getJsonTree(List<TreeJson> treelist){
    	
    	StringBuffer sbtree = new StringBuffer();
		sbtree.append("[{");
		sbtree.append("\"id\":\"" + treelist.get(0).getSid() + "\",");
		sbtree.append("\"text\":\"" + treelist.get(0).getText() + "\"");
		
		if(treelist.get(0).getChildren().size()>0){
			
		}
		
    	return null;
    }

}
