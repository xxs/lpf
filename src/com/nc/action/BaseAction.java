/**
 * Struts工具父类
 */
package com.nc.action;

import java.lang.reflect.Method;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	// 导航链接
	private String navigationUrl;
	// 基本action参数
	private String actions;
	// 链接类型
	private String type;
	// 链接指向功能ID
	private String functionId;

	/**
	 * 映射调用子类ACTION业务方法
	 * 
	 * @param method
	 * @return
	 * @throws Exception
	 */
	protected String executeMethod(String method) throws Exception {
		Class[] c = null;
		Method m = this.getClass().getMethod(method, c);
		Object[] o = null;
		String result = (String) m.invoke(this, o);
		return result;
	}

	/**
	 * 默认链接导向
	 */
	@Override
	public String execute() throws Exception {
		if ("1".startsWith(type)) {
			return "goUrl";
		} else {
			String[] urlparam = navigationUrl.split("_");
			String url = urlparam[0];
			for (int i = 1; i < urlparam.length - 1; i++) {
				url += "/" + urlparam[i];
			}
			setNavigationUrl(url + "." + urlparam[urlparam.length - 1]+"?functionId="+functionId);
			return SUCCESS;
		}
	}
///////////////////////get/set////////////////////////////////
	public String getNavigationUrl() {

		return navigationUrl;
	}

	public void setNavigationUrl(String navigationUrl) {
		this.navigationUrl = navigationUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

}
