package com.newapp.web.interceptor;

import com.nc.bean.UserBean;

public interface UserSessionAware {
	public void setUserSession(UserBean userSession);
}
