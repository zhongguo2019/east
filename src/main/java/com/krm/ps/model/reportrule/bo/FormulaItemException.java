package com.krm.ps.model.reportrule.bo;

/**
 * <p>Title: </p>
 *
 * <p>Description: 公式表达式中的每一项的异常</p>
 *
 * <p>Copyright: Copyright krmsoft.com(c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author
 */
public class FormulaItemException extends Exception
{
	private static final long serialVersionUID = 1L;

	/**
	 * <p>FormulaItemException构造器，默认使用</p>
     * @param exceptionMessage 异常原因或说明
	 */
    public FormulaItemException(String exceptionMessage)
    {
		super(exceptionMessage);
    }
    
}
