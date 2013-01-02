/**
 * 用户设置ACTIO
 */
package com.nc.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.nc.bean.UserBean;
import com.nc.comm.Constant;
import com.nc.comm.NewAppUtil;
import com.nc.service.UserService;

public class UserAction extends BaseAction  implements SessionAware {
	
	private Map session;

	public void setSession(Map session) {
		this.session = session;
	}

	public Map getSession() {
		return session;
	}
	
	private String skin;
	
	private UserService service;
	
	private String password;
	private String newpassword;
	
	
	
	 @Override
	public String execute() throws Exception {
		 return this.executeMethod(this.getActions());
	 }
	//换肤操作
    public String changeskin()throws Exception {
    	UserBean user = (UserBean)getSession().get(Constant.USER_SESSION_KEY);
    	user.setSkin(skin);
    	service.updateUserInfo(user);
    	return SUCCESS;
    }
    //修改密码
    public String modifyKey() throws Exception{
    	UserBean user = (UserBean)getSession().get(Constant.USER_SESSION_KEY);
    	String md5 = NewAppUtil.MD5(password);
    	if(user.getPassWord().equals(md5)){
    		user.setPassWord(md5);
    		service.updateUserInfo(user);
    	}else{
    		this.addActionError(this.getText("error.oldpassword"));
    	}
    	return null;
    }

	public  String getSkin() {
		return skin;
	}

	public  void setSkin(String skin) {
		this.skin = skin;
	}

	public  UserService getService() {
		return service;
	}

	public  void setService(UserService service) {
		this.service = service;
	}

	public  String getPassword() {
		return password;
	}

	public  void setPassword(String password) {
		this.password = password;
	}

	public  String getNewpassword() {
		return newpassword;
	}

	public  void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
   
}
