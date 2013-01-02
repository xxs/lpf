package com.newapp.web.tag;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.nc.bean.UserBean;
import com.nc.bean.UserFunctionBean;
import com.nc.comm.Constant;

public class ChangSkinTag extends BaseTags {
	private static final String defalutSkin = "ext-all";
	private static final String defalutImgPath = "default";
	private String skin  ;
	private String imgpath ;
	private String resources;

	@Override
	public int doStartTag() throws JspException {

		UserBean bean = (UserBean) getBean(Constant.USER_SESSION_KEY, null,"session");
		if (bean != null && StringUtils.isNotEmpty(bean.getSkin())) {
			skin = bean.getSkin();
			if (!bean.getSkin().equals("ext-all")) {
				imgpath = skin.split("-")[1];
			} else {
				imgpath = defalutImgPath;
			}

		}else{
			skin = defalutSkin;
			imgpath = defalutImgPath;
		}
		getResources(bean);
		pageContext.setAttribute("skin", skin);
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		StringBuffer script = new StringBuffer(
				"<script type=\"text/javascript\">");
		script.append("var NEWAPP_RESOURCES="+resources+";");
		script.append("Ext.BLANK_IMAGE_URL=");
		script.append("'" + request.getContextPath()+ "/extjs/resources/images/" + imgpath + "/s.gif';");
		script.append("Ext.util.CSS.swapStyleSheet(");
		script.append("'theme',");

		script.append("'" + request.getContextPath() + "/extjs/resources/css/"
				+ skin + ".css'");
		script.append(");");
		script.append("</script>");
		write(script.toString());
		return EVAL_PAGE;

	}

	private void getResources(UserBean bean) throws JspException {
		if(bean==null)return ;
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String functionId = request.getParameter("functionId");
		if (StringUtils.isEmpty(functionId)) {
			resources = "{}";
		}
		Set functions = bean.getFunctions();
		JSONObject object = new JSONObject();
		for (Iterator it = functions.iterator(); it.hasNext();) {
			UserFunctionBean function = (UserFunctionBean) it.next();
			if(!function.getFunctionId().equals(functionId)){
				continue;
			}
			try {
				object.put("add", function.getAdd());
				object.put("edit", function.getEdit());
				object.put("del", function.getDel());
				object.put("other", function.getOther());
			} catch (Exception ex) {
				throw new JspException("json error" + ex.getMessage());
			}
			resources = object.toString();
		}

	}
	
	@Override
	public void release(){
		skin = null ;
		imgpath  = null;
		resources = null;
		super.release();
	}

}
