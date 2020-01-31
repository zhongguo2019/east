package com.krm.ps.sysmanage.organmanage2.services.impl;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.krm.ps.sysmanage.organmanage2.model.OrganNode;
import com.krm.ps.sysmanage.organmanage2.model.OrganSystem;
import com.krm.ps.sysmanage.organmanage2.model.OrganTree;
 
/**
 * 一个机构系统的机构树缓存类。可能只缓存整个机构系统的机构树，也可能缓存机构系统的多个子树。
 * 如果一个机构树的父机构树加入了缓存，则把此机构树从缓存移除。从缓存中查找机构树的方法是，
 * 先看此树本身有没有缓存，如果没有则查找此树的父树（包括祖先树）有没有缓存， 如果缓存了则从父树中取出此机构树。
 * 使用SoftReference减轻内存压力
 * 
 */
class OrganSystemCache {
	

	private OrganSystem organSystem;

	private Map organTreeMap = new HashMap();

//	OrganIdTagCache otc;

//	OrganSystemCache(OrganSystem organSystem) {
//		this.organSystem = organSystem;
//		if (this.organSystem.getVisibility() != OrganSystem.VISIBILITY_CREATOR) {
//			otc = new OrganIdTagCache();
//		}
//	}
	
	OrganIdTagCache otc = new OrganIdTagCache();

	OrganSystemCache(OrganSystem organSystem) {
		this.organSystem = organSystem;
	}
	
	OrganSystem getOrganSystem() {
		try {
			return (OrganSystem)organSystem.clone();
		}catch(CloneNotSupportedException e) {
		}
		return organSystem;
	}

	void updateOrganSystem(OrganSystem organSystem) {
		try {
			this.organSystem=(OrganSystem)organSystem.clone();
		}catch(CloneNotSupportedException e) {
		}
	}

	/**
	 * 从缓存中取出机构树，如果它本身没有找到，则从它的祖先树（如果存在）找，否则返回null
	 * 
	 * @param topOrganId
	 * @return
	 */
	OrganTree getOrganTree(int topOrganId) {

		OrganTreeCacheKey key = new OrganTreeCacheKey(""+topOrganId);
		Reference ref = (Reference) organTreeMap.get(key);
		if(ref!=null) {
			Object obj = ref.get();
			if (obj != null) {
				return cloneTree((OrganTree)obj);
			}
		}
		if (this.organSystem.getVisibility() == OrganSystem.VISIBILITY_CREATOR) {
			// 如果机构树只是创建者可见的，则缓存此机构树肯定是完整缓存的
			return null;
		}

		/*
		 * 可能此机构树的父机构树已缓存了
		 */
		OrganNode on;
		String tag = otc.getTag(""+topOrganId);
		/*
		 * findOrganByTag()比findOrganByOrganId()更容易查找
		 */
		if (tag != null) {
			on = findOrganByTag(tag);
		} else {
			on = findOrganByOrganId(topOrganId);
			if (on != null) {
				otc.addTag(""+topOrganId, on.getSubTreeTag());
			}
		}
		if (on != null) {
			OrganTree tree = new OrganTree();
			tree.setOrganSystem(organSystem);
			tree.setTopOrgan(on);
			return cloneTree(tree);
		}

		return null;
	}
	
	/*
	 * deep clone
	 */
	private OrganTree cloneTree(OrganTree organTree) {

/*		OrganTree retTree;
		try {
			retTree=(OrganTree)organTree.clone();
		}catch(CloneNotSupportedException e) {
			retTree=organTree;
		}
		return retTree;*/
		
		return organTree;
	}

	/**
	 * 所缓存的机构树的迭代器，对organTreeMap的迭代器进行封装，保证next()不返回null
	 * 
	 */
	private Iterator getOrganTreeIterator() {

		Iterator oti = new Iterator() {

			Iterator it = organTreeMap.keySet().iterator();
			Object nextValue;
			List toRemove=new ArrayList();
			Object o=next();//做初始化
			
			public boolean hasNext() {
				if(nextValue != null) {	
					return true;
				}
				if(toRemove.size()>0) {
					Iterator trm=toRemove.iterator();
					while(trm.hasNext()) {
						organTreeMap.remove(trm.next());
					}
				}
				return false;
			}

			/*
			 * 预先找到下一个（非null）值
			 */
			public Object next() {

				Object thisValue = nextValue;

				boolean found=false;
				while (it.hasNext()) {
					Object key = it.next();
					Reference ref = (Reference) organTreeMap.get(key);
					OrganTree tree = (OrganTree) ref.get();
					if (tree != null) {
						nextValue = tree;
						found=true;
						break;
					} else {
						toRemove.add(key);
					}
				}
				if(!found) {
					nextValue = null;
				}

				return thisValue;
			}

			public void remove() {
			}
			
		};

		return oti;
	}

	private OrganNode findOrganByTag(String subTreeTag) {

		Iterator it = getOrganTreeIterator();

		while (it.hasNext()) {
			OrganTree tree = (OrganTree) it.next();
			//assert tree != null : "迭代器内部错误";
			String topOrganTag = tree.getTopOrgan().getSubTreeTag();
			if (subTreeTag.startsWith(topOrganTag)) {
				// 这个树是所找机构树的父树(祖先树)
				OrganNode on = tree.findNodeBySubTreeTag(subTreeTag);
				return on; // on也有可能为null
			}
		}

		return null;
	}

	private OrganNode findOrganByOrganId(int organId) {

		Iterator it = getOrganTreeIterator();
		while (it.hasNext()) {
			OrganTree tree = (OrganTree) it.next();
			OrganNode on = tree.findNodeByOrganId(organId);
			if (on != null) {
				return on;
			}
		}

		return null;
	}
	
	/**
	 * 从缓存取出完整机构树.判断是否完整树的顶级节点的方法是看机构节点的subTreeTag是否为两位
	 * 
	 * @return
	 */
	OrganTree getCompleteOrganTree(){

		Iterator it = getOrganTreeIterator();
		while (it.hasNext()) {
			OrganTree tree = (OrganTree) it.next();
			String topOrganTag = tree.getTopOrgan().getSubTreeTag();
			if (topOrganTag.length()==2) {
				// 这个树是整棵树缓存的
				return tree;
			}
		}
		return null;
	}

	/**
	 * 缓存机构树，如果已有祖先树缓存，更新对应的子树，如果已有此树的子树的缓存，移除这些子树
	 * 
	 * @param treeToCache
	 */
	public void cacheOrganTree(OrganTree treeToCache) {
		
		//should clone?(needn't)

		OrganNode on = treeToCache.getTopOrgan();
		OrganTreeCacheKey key = new OrganTreeCacheKey(""+on.getOrganId());

		if (this.organSystem.getVisibility() == OrganSystem.VISIBILITY_CREATOR) {
			// 如果机构树只是创建者可见的，则缓存此机构树肯定是完整缓存的
			Reference ref = new SoftReference(treeToCache);
			organTreeMap.put(key, ref);
			return;
		}

		boolean putNew = true;
		String tag = on.getSubTreeTag();

		List toRemove=new ArrayList(0);
		Iterator it = getOrganTreeIterator();

		while (it.hasNext()) {
			OrganTree treeCached = (OrganTree) it.next();

			OrganNode onc = treeCached.getTopOrgan();
			String tagc = onc.getSubTreeTag();

			if (tag.equals(tagc)) {
				// treeCached就是要更新的树
				break;
			} else if (tag.startsWith(tagc)) {
				// treeToCache是treeCached的子树
				OrganNode organToUpdate = treeCached.findNodeBySubTreeTag(tag);
				OrganNode parentNode = organToUpdate.getParent();
				parentNode.updateChild(organToUpdate);
				putNew = false;
				break;
			} else if (tagc.startsWith(tag)) {
				// treeCached是treeToCache的子树
				OrganTreeCacheKey keyc = new OrganTreeCacheKey(""+onc.getOrganId());
				toRemove.add(keyc);
				continue;
			}
		}
		if(toRemove.size()>0) {
			Iterator trm=toRemove.iterator();
			while(trm.hasNext()) {
				organTreeMap.remove(trm.next());//移除子树
			}
		}

		if (putNew) {
			Reference ref = new SoftReference(treeToCache);
			organTreeMap.put(key, ref);
		}
		
		
		ArrayList itp = otc.idTagPairs;
		for(int i=itp.size()-1;i>=0;i--){
			String[] idTag=(String[])itp.get(i);
			OrganNode o=treeToCache.findNodeBySubTreeTag(idTag[1]);
			if(o!=null&&!(""+o.getOrganId()).equals(idTag[0])){
				itp.remove(i);
			}
		}
		otc.addTag(""+on.getOrganId(), on.getSubTreeTag());		
	}

	void free() {
		organSystem=null;
		organTreeMap=null;
		otc=null;
	}

	static class OrganTreeCacheKey {

		String organId;

		public OrganTreeCacheKey(String organId) {
			this.organId = organId;
		}

		public boolean equals(Object obj) {
			OrganTreeCacheKey ck = (OrganTreeCacheKey) obj;
			return organId.equals(ck.organId);
		}

		public int hashCode() {
			return (organId).hashCode();
		}
		
		public String toString() {
			return "key:"+organId;
		}

	}

	/**
	 * 因为提供的取机构树的方法OrganSystemCache#getOrganTree(String
	 * topOrganId)是以机构id为参数的，而在内部使用子树编码subTreeTag才是高效的做法，所以提供两者映射的缓存
	 * 
	 */
	private static class OrganIdTagCache {

		static int MAX_PAIRS = 10;

		ArrayList idTagPairs = new ArrayList();

		void addTag(String organId, String subTreeTag) {
			idTagPairs.add(0, new String[] { organId, subTreeTag });
			if (idTagPairs.size() > MAX_PAIRS) {
				for(int i=idTagPairs.size()-1;i>=MAX_PAIRS;i--) {
					idTagPairs.remove(i);
				}
				Iterator it = idTagPairs.iterator();
				it.next();
				while (it.hasNext()) {
					String[] pair = (String[]) it.next();
					if (pair[0].equals(organId)) {
						it.remove();
						return;
					}
				}
				it.remove();
			}
		}

		String getTag(String organId) {
			for (int i = 0; i < idTagPairs.size(); i++) {
				String[] pair = (String[]) idTagPairs.get(i);
				if (pair[0].equals(organId)) {
					idTagPairs.set(i, idTagPairs.get(0));
					idTagPairs.set(0, pair);
					return pair[1];
				}
			}
			return null;
		}

		void updateTag(String organId, String newSubTreeTag) {
		}

		void removeTag(String organId) {
			Iterator itp=idTagPairs.iterator();
			while(itp.hasNext()){
				String[] idTag=(String[])itp.next();
				if(organId.equals(idTag[0])){
					itp.remove();
					return;
				}
			}
		}
	}

}
