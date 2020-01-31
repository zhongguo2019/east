package com.krmsoft.mycas.clogin;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO (2015-3-11 涓嬪崍2:09:55) 璇锋坊鍔?Login 绫荤殑娉ㄩ噴銆?
 * @author xxx
 * @version $Revision: 1.1 $
 */
public interface Login {
public String doLogin(HttpServletRequest request, HttpServletResponse res) throws Exception;
public boolean checkLogin(HttpServletRequest request, HttpServletResponse res);
public String logout(HttpServletRequest request, HttpServletResponse res) throws Exception;
public boolean checkLogout(HttpServletRequest request, HttpServletResponse res);
}
