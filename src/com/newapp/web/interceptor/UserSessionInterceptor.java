package com.newapp.web.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.nc.bean.UserBean;
import com.nc.comm.Constant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class UserSessionInterceptor extends AbstractInterceptor {
	private static final Log logger = LogFactory.getLog(UserSessionInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
		Object action = invocation.getAction();

		if (action instanceof UserSessionAware) {
			//HttpServletRequest request = (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);
			//HttpServletResponse response = (HttpServletResponse) ac.get(ServletActionContext.HTTP_RESPONSE);
			//ServletContext servletContext = (ServletContext) ac.get(ServletActionContext.SERVLET_CONTEXT);
			//WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			  UserBean us = (UserBean) ac.getSession().get(Constant.USER_SESSION_KEY);
			 ((UserSessionAware) action).setUserSession(us);
		
		}

		return invocation.invoke();
	}

}
