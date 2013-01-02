package com.newapp.web.tag;



import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.nc.comm.RequestUtil;




public class BaseTags extends TagSupport {
	private static final long serialVersionUID = 3690183899614654765L;
	ApplicationContext context = null;
	protected String id;
	protected void write(String text) throws JspException {
		try {
			pageContext.getOut().print(text);
		} catch (IOException e) {
			throw new JspException("Could not write to JspWriter", e);
		}
	}

	protected Object getBeanProperty(Object object, String property)
			throws JspException {
		try {
			return BeanUtils.getSimpleProperty(object, property);
		} catch (IllegalAccessException e) {
			throw new JspException("Error getting bean: ", e);
		} catch (InvocationTargetException e) {
			throw new JspException("Error getting bean: ", e);
		}catch (NoSuchMethodException e){
			throw new JspException("Error getting bean: ", e);
		}
	}

	protected Object getBean(String name, String property, String scope)
			throws JspException {
		try {
			return RequestUtil.findObject(pageContext, name, property, scope);
		} catch (IllegalAccessException e) {
			throw new JspException("Error finding bean: ", e);
		} catch (InvocationTargetException e) {
			throw new JspException("Error finding bean: ", e);
		}catch (NoSuchMethodException e){
			throw new JspException("Error finding bean: ", e);
		}
	}
	
	protected Object getBean(String name){
		if(null==context){
			context  = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext());
		}
		return context.getBean(name);
	}

	protected void setBean(String name, String scope, String defaultScope,
			Object bean) throws JspException {
		if (bean == null)
			return;

		if (name == null) {
			throw new JspException(
					"Cannot store bean in any scope. Name was null");
		}

		String theScope = scope;
		if (theScope == null) {
			theScope = defaultScope;
		}
		if (theScope == null) {
			theScope = "request";
		}

		if ("request".equals(theScope)) {
			pageContext.getRequest().setAttribute(name, bean);
		} else if ("session".equals(theScope)) {
			pageContext.getSession().setAttribute(name, bean);
		} else if ("page".equals(theScope)) {
			pageContext.setAttribute(name, bean);
		} else if ("application".equals(theScope)) {
			pageContext.getServletContext().setAttribute(name, bean);
		} else {
			throw new JspException(
					"Could not set bean in any scope. Invalid scope: "
							+ theScope
							+ ". Valid scopes are 'request', 'session', 'page', and 'application'.");
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

}

