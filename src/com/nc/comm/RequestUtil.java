/**
 * request工具操作类
 */
package com.nc.comm;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.BeanUtils;

	public class RequestUtil {
        private RequestUtil(){
        	
        }
        /**
         * 查找pageContext内值
         * @param pageContext
         * @param name
         * @param property
         * @param scope
         * @return
         * @throws IllegalAccessException
         * @throws NoSuchMethodException
         * @throws InvocationTargetException
         */
	    public static Object findObject(PageContext pageContext, String name, String property, String scope) throws IllegalAccessException,NoSuchMethodException, InvocationTargetException {
	        Object bean = findObject(pageContext, name, scope);
	        if(property != null){
	            return BeanUtils.getSimpleProperty(bean, property);
	        }
	        return bean;
	    }

	   /**
	    * 查找pageContext内值
	    * @param pageContext
	    * @param name
	    * @param scope
	    * @return
	    */
	    public static Object findObject(PageContext pageContext, String name, String scope){
	        Object o = null;
	        if(scope == null){
	            o = pageContext.getRequest().getAttribute(name);
	            if(o != null) return o;

	            o = pageContext.getSession().getAttribute(name);
	            if(o != null) return o;

	            o = pageContext.getAttribute(name);
	            if(o != null) return o;

	            o = pageContext.getServletContext().getAttribute(name);
	            if(o != null) return o;

	            return null;
	        }
	        if(scope.equals("request")){
	            return pageContext.getRequest().getAttribute(name);
	        }
	        if(scope.equals("session")){
	            return pageContext.getSession().getAttribute(name);
	        }
	        if(scope.equals("page")){
	            return pageContext.getAttribute(name);
	        }
	        if(scope.equals("application")){
	            return pageContext.getServletContext().getAttribute(name);
	        }

	        return null;
	    }
        /**
         * 保存值--->session,page,application
         * @param pageContext
         * @param name
         * @param scope
         * @param object
         */
	    public static void storeObject(PageContext pageContext, String name, String scope, Object object){
	        if(scope == null || scope.equals("request")){
	            pageContext.getRequest().setAttribute(name, object);
	            return;
	        }
	        if(scope.equals("session")){
	            pageContext.getSession().setAttribute(name, object);
	            return;
	        }
	        if(scope.equals("page")){
	            pageContext.setAttribute(name, object);
	            return;
	        }
	        if(scope.equals("application")){
	            pageContext.getServletContext().setAttribute(name, object);
	        }
	    }
	    /**
         * 删除值--->session,page,application
         * @param pageContext
         * @param name
         * @param scope
         * @param object
         */
	    public static void removeObject(PageContext pageContext, String name, String scope){
	        if(scope == null || scope.equals("request")){
	            pageContext.getRequest().removeAttribute(name);
	            return;
	        }
	        if(scope.equals("session")){
	            pageContext.getSession().removeAttribute(name);
	            return;
	        }
	        if(scope.equals("page")){
	            pageContext.removeAttribute(name);
	            return;
	        }
	        if(scope.equals("application")){
	            pageContext.removeAttribute(name);
	            return;
	        }
	    }
	}

