package com.newapp.web.interceptor;

import com.nc.bean.UserBean;
import com.nc.comm.Constant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class UserLoginInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
		UserBean us = (UserBean) ac.getSession().get(Constant.USER_SESSION_KEY);
		if (us == null) {
			return "nologinerror";
		}
		return invocation.invoke();
	}

}
