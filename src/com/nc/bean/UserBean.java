/**
 * 用户BEAN
 */
package com.nc.bean;

import java.util.List;
import java.util.Set;

public class UserBean {
	

	private String userId;
	private String name;
	private String passWord;
	private String userName;
	
	private String skin;
	

	
	private List<RoleBean> roles;
    
	private Set<UserFunctionBean> functions;

    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<RoleBean> getRoles() {
		return roles;
	}
	public void setRoles(List<RoleBean> roles) {
		this.roles = roles;
	}
	public Set<UserFunctionBean> getFunctions() {
		return functions;
	}
	public void setFunctions(Set<UserFunctionBean> functions) {
		this.functions = functions;
	}
	public  String getSkin() {
		return skin;
	}
	public  void setSkin(String skin) {
		this.skin = skin;
	}


}
