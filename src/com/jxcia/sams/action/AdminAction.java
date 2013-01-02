package com.jxcia.sams.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AdminAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6680016611301011734L;
	private String password;
	private String username;
	private boolean success;
	private String msg;
	public String login() throws Exception {
		   Map<String, Object> session = ActionContext.getContext().getSession();
		   if(username!=null){
		    if(password.equals("ljf")){
		     session.put("password", password);
		     success = true;
		     msg="登陆成功!";
		    }
		    else{
		     success = false;
		     msg="密码错误!";
		    }
		   }
		   else{
		    success = false;
		    msg="账号不存在!";
		   }
		   return SUCCESS;
		}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}


}
