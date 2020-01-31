package com.krm.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.axis.utils.StringUtils;

import com.bonc.sso.client.IAuthHandle;

public class AuthHandleImpl implements IAuthHandle {

	@Override
	public boolean onSuccess(HttpServletRequest request,
			HttpServletResponse response, String loginId) {
		if (null != request && null != response
				&& !StringUtils.isEmpty(loginId)) {
			String login_id = (String) request.getSession().getAttribute(
					"loginId");
			if (!StringUtils.isEmpty(login_id)) {
				// 如果 当前session中的用户为登录用户，则无需任何操作
				return true;
			}
			HttpSession session = request.getSession();
			// 写入session信息
			session.setAttribute("loginId", loginId);
			return true;
		}
		return false;
	}

}
