package com.krm.ps.framework.common.dictionary.web.action;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.krm.ps.framework.common.dictionary.services.DictionaryManager;
import com.krm.ps.framework.common.dictionary.vo.Dictionary;
import com.krm.ps.framework.common.dictionary.web.form.DictionaryForm;
import com.krm.ps.framework.common.dictionary.web.util.OrderCode;
import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.util.DateUtil;
/**
 * <p>
 * <a href="DictionaryAction.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author
 *
 * @struts.action name="dictionaryForm" path="/dictionary" scope="request" 
 *  validate="false" parameter="method" input="dicPage"
 *  
 * @struts.action-forward name="dicListPage" path="/common/dictionary/dicList.jsp"
 * @struts.action-forward name="dicPage" path="/common/dictionary/dicForm.jsp"
 * @struts.action-forward name="listDictionary" path="/dictionary.do?method=getroots"
 * @struts.action-forward name="tree" path="/common/tree.jsp"
 */

public class DictionaryAction extends BaseAction
{

	/**
	 * 将添加操作定向到添加页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addParent(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		request.setAttribute("dotype", "1");
		return mapping.findForward("dicPage");
	}

	/**
	 * 将添加操作定向到添加子节点的页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addChild(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		request.setAttribute("dotype", "2");
		request.setAttribute("CurrentItem", request
			.getParameter("GetCurrentItem"));
		DictionaryManager mgr = getDictionaryManager();
		List list = mgr.getDictionaryById(request.getParameter("GetCurrentItem"));
		Iterator it=list.iterator();
		Dictionary dictionary = (Dictionary)it.next();
		request.setAttribute("CurrentItemName", dictionary.getDicName());
		return mapping.findForward("dicPage");
	}

	/**
	 * 将修改操作定向到修改的页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		request.setAttribute("dotype", "3");
		request.setAttribute("CurrentItem", request
			.getParameter("GetCurrentItem"));
		DictionaryManager mgr = getDictionaryManager();
		List list = mgr.getDictionaryById(request.getParameter("GetCurrentItem"));
		Iterator It=list.iterator();
		Dictionary dictionary = (Dictionary)It.next();
		request.setAttribute("parentItem", dictionary.getDicName());
		List Rlist = mgr.getDictionaryById(request
			.getParameter("GetCurrentItem"));
		Iterator it = Rlist.iterator();
		Dictionary dic = (Dictionary) it.next();
		request.setAttribute("dicdesc", dic.getDescription());
		if (dic.getParentId().intValue() == 0)
			request.setAttribute("parentName", "");
		else
		{
			List Rlist2 = mgr.getDictionaryById(dic.getParentId().toString());
			Iterator it2 = Rlist2.iterator();
			Dictionary dic2 = (Dictionary) it2.next();
			request.setAttribute("parentName", dic2.getDicName());
		}

		return mapping.findForward("dicPage");
	}

	/**
	 * 保存数据字典的根结点、子节点及修改节点
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		ActionMessages errors = form.validate(mapping, request);
		DictionaryManager mgr = getDictionaryManager();
		DictionaryForm dicForm;
		if (!errors.isEmpty())
		{
			saveErrors(request, errors);
			return mapping.findForward("dicPage");
		}
		if (request.getParameter("dotype").equals("3")) //修改节点信息
		{
			mgr = getDictionaryManager();
			dicForm = (DictionaryForm) form;
			List Rlist = mgr.getDictionaryById(request
				.getParameter("CurrentItem"));
			Iterator it = Rlist.iterator();
			Dictionary dic = (Dictionary) it.next();
			dic.setDicName(dicForm.getDicName());
			dic.setDescription(dicForm.getDisc());
			mgr.saveupdateDictionary(dic);
			return mapping.findForward("listDictionary");
		}
		dicForm = (DictionaryForm) form; //保存根结点及子节点
		Dictionary dictionary = new Dictionary();
		//由ActionForm构件VO
		dictionary.setDicName(dicForm.getDicName());
		dictionary.setDescription(dicForm.getDisc());
		dictionary.setLayer(Integer.valueOf("0"));
		if (request.getParameter("CurrentItem") == null)
			dictionary.setParentId(Long.valueOf("0"));
		else
		{
			dictionary.setParentId(Long.valueOf(request
				.getParameter("CurrentItem")));
			List plist = mgr.getDictionaryById(request
				.getParameter("CurrentItem"));
			Iterator pIt = plist.iterator();
			if (pIt.hasNext() != false)
			{
				for (; pIt.hasNext();)
				{
					Dictionary pdic = (Dictionary) pIt.next();
					pdic.setIsLeaf("0");
					mgr.saveDictionary(pdic);
				}
			}

		}

		dictionary.setDispOrder("999999");
		dictionary.setIsLeaf("1");
		dictionary.setModifyTime(DateUtil.convertDateToString(new Date()));
		dictionary.setStatus(Integer.valueOf("1"));
		mgr.saveDictionary(dictionary);
		request.setAttribute("CurrentItem",request.getParameter("CurrentItem"));
		return mapping.findForward("listDictionary");
	}

	/**
	 * 设置节点的状态位
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setstatus(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		DictionaryManager mgr = getDictionaryManager();
		String dicItem = request.getParameter("GetCurrentItem");
		if (request.getParameter("dotype").equals("4")) //删除节点给状态位置9
		{
			//处理dicItem父节点的isLeaf
			//首先获取当前的节点信息
			List clist = mgr.getDictionaryById(dicItem);
			Iterator cIt = clist.iterator();
			if (cIt.hasNext() != false)
			{
				for (; cIt.hasNext();)
				{
					Dictionary cdic = (Dictionary) cIt.next();
					if (cdic.getParentId().longValue() == 0)
					{
						cdic.setIsLeaf("1");
						mgr.saveDictionary(cdic);
					}
					else
					{
						List plist = mgr.getDictionaryById(cdic.getParentId()
							.toString());
						Iterator pIt = plist.iterator();
						if (pIt.hasNext() != false)
						{
							for (; pIt.hasNext();)
							{
								Dictionary pdic = (Dictionary) pIt.next();
								if (pdic.getParentId().longValue() == 0)
								{
									pdic.setIsLeaf("1");
									mgr.saveDictionary(pdic);
								}
							}
						}
					}
				}
			}

			List Rlist = mgr.getDictionargBeginWitchId(dicItem);
			for (Iterator it = Rlist.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				if (request.getParameter("Leaf").equals("false"))
				{
					List diclist = mgr.getDictionaryById(object[0].toString());
					Iterator dicIt = diclist.iterator();
					if (dicIt.hasNext() != false)
					{
						for (; dicIt.hasNext();)
						{
							Dictionary dictionary = (Dictionary) dicIt.next();
							dictionary.setStatus(Integer.valueOf("9"));
							mgr.saveDictionary(dictionary);
						}
					}
				}
				else
				{
					if (request.getParameter("GetCurrentItem").equals(
						object[0].toString()))
					{
						continue;
					}
					List diclist = mgr.getDictionaryById(object[0].toString());
					Iterator dicIt = diclist.iterator();
					if (dicIt.hasNext() != false)
					{
						for (; dicIt.hasNext();)
						{
							Dictionary dictionary = (Dictionary) dicIt.next();
							dictionary.setStatus(Integer.valueOf("9"));
							mgr.saveDictionary(dictionary);
						}
					}
				}
			}
			request.setAttribute("CurrentItem",request.getParameter("GetCurrentItem"));
			return mapping.findForward("listDictionary");

		}
		if (request.getParameter("dotype").equals("5")) //冻结节点给状态位置2
		{
			List Rlist = mgr.getDictionargBeginWitchId(dicItem);
			for (Iterator it = Rlist.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				if (request.getParameter("Leaf").equals("false"))
				{
					List diclist = mgr.getDictionaryById(object[0].toString());
					Iterator dicIt = diclist.iterator();
					if (dicIt.hasNext() != false)
					{
						for (; dicIt.hasNext();)
						{
							Dictionary dictionary = (Dictionary) dicIt.next();
							dictionary.setStatus(Integer.valueOf("2"));
							mgr.saveDictionary(dictionary);
						}
					}
				}
				else
				{
					if (request.getParameter("GetCurrentItem").equals(
						object[0].toString()))
					{
						continue;
					}
					List diclist = mgr.getDictionaryById(object[0].toString());
					Iterator dicIt = diclist.iterator();
					if (dicIt.hasNext() != false)
					{
						for (; dicIt.hasNext();)
						{
							Dictionary dictionary = (Dictionary) dicIt.next();
							dictionary.setStatus(Integer.valueOf("2"));
							mgr.saveDictionary(dictionary);
						}
					}
				}
			}
			request.setAttribute("CurrentItem",request.getParameter("GetCurrentItem"));
			return mapping.findForward("listDictionary");
		}
		if (request.getParameter("dotype").equals("6")) //解冻节点给状态位置1
		{
			List Rlist = mgr.getDictionargBeginWitchId(dicItem);
			for (Iterator it = Rlist.iterator(); it.hasNext();)
			{
				Object[] object = (Object[]) it.next();
				if (request.getParameter("Leaf").equals("false"))
				{
					List diclist = mgr.getDictionaryById(object[0].toString());
					Iterator dicIt = diclist.iterator();
					if (dicIt.hasNext() != false)
					{
						for (; dicIt.hasNext();)
						{
							Dictionary dictionary = (Dictionary) dicIt.next();
							dictionary.setStatus(Integer.valueOf("1"));
							mgr.saveDictionary(dictionary);
						}
					}
				}
				else
				{
					if (request.getParameter("GetCurrentItem").equals(
						object[0].toString()))
					{
						continue;
					}
					List diclist = mgr.getDictionaryById(object[0].toString());
					Iterator dicIt = diclist.iterator();
					if (dicIt.hasNext() != false)
					{
						for (; dicIt.hasNext();)
						{
							Dictionary dictionary = (Dictionary) dicIt.next();
							dictionary.setStatus(Integer.valueOf("1"));
							mgr.saveDictionary(dictionary);
						}
					}
				}
			}
			request.setAttribute("CurrentItem",request.getParameter("GetCurrentItem"));
			return mapping.findForward("listDictionary");
		}
		request.setAttribute("CurrentItem",request.getParameter("GetCurrentItem"));
		return mapping.findForward("listDictionary");

	}

	/**
	 * 获取字典相中的所有根
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getroots(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled())
		{
			log.debug("Entering 'Dictionary getroots' method");
		}
		DictionaryManager mgr = getDictionaryManager();
		List Rlist = mgr.getDictionaryRoots();
		String roots = "";
		Iterator it = Rlist.iterator();
		if (it.hasNext() != false)
		{
			for (; it.hasNext();)
			{
				Dictionary dic = (Dictionary) it.next();
				roots = roots + dic.getDicId() + ",";
			}
			roots = roots.substring(0, roots.length() - 1);
		}
		else
		{
			roots = "0";
		}
		request.setAttribute("roots", roots);
		if(request.getParameter("CurrentItem")!=null||request.getAttribute("CurrentItem")!=null)
		{
			request.setAttribute("CurrentItem",request.getParameter("CurrentItem"));
		}
		return mapping.findForward("dicListPage");
	}

	public ActionForward setdisorder(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled())
		{
			log.debug("Entering 'Dictionary setdicorder' method");
		}
		DictionaryManager mgr = getDictionaryManager();
		List Rlist;
		Iterator it;
		Dictionary dic;
		OrderCode ordercode = new OrderCode();
		String Items = request.getParameter("MovedItems");
		String ItemArray[] = Items.split(";");
		for (int i = 0; i < ItemArray.length; i++)
		{
			String item = ItemArray[i].substring(ItemArray[i].indexOf(":") + 1,
				ItemArray[i].length());
			String IArray[] = item.split(",");
			for (int j = 0; j < IArray.length; j++)
			{
				Rlist = mgr.getDictionaryById(IArray[j]);
				it = Rlist.iterator();
				dic = (Dictionary) it.next();
				dic.setDispOrder(ordercode.getOrdercode(String.valueOf(j + 1)));
				mgr.saveupdateDictionary(dic);
			}
		}
		request.setAttribute("CurrentItem",request.getParameter("GetCurrentItem"));
		return mapping.findForward("listDictionary");
	}
	
	public ActionForward getTree(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		request.setAttribute("treeXml", getDictionaryManager().getDictionaryTreeXML());
		return mapping.findForward("tree");
	}
}
