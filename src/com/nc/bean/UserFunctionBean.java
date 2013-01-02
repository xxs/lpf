/**
 * 用户关联功能BEAN
 */
package com.nc.bean;

public class UserFunctionBean {
	private int hashCode = Integer.MIN_VALUE;

    //角色ID
	private String roleId;
	//功能ID
	private String functionId;
	//功能NAME
	private String functionName;
	//功能URL
	private String functionUrl;
    //功能模块URL
	private String functionType;
	//父模块ID
	private String parentFunctionId;
	//增加标识
	private String add;
	//其它标识
	private String other;
	//增加标识
	private String edit;
	//增加标识
	private String del;
	
	public boolean isRight(){
		return "0".equals(add)&&"0".equals(other)&&"0".equals(edit)&&"0".equals(del);
	}
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getFunctionId() {
		return functionId;
	}
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
	public String getAdd() {
		return add;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	public String getEdit() {
		return edit;
	}
	public void setEdit(String edit) {
		this.edit = edit;
	}
	public String getDel() {
		return del;
	}
	public void setDel(String del) {
		this.del = del;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof UserFunctionBean))
			return false;
		UserFunctionBean bean = (UserFunctionBean) obj;
		if (null == this.getFunctionId() || null == bean.getFunctionId())
			return false;
		return this.getFunctionId().equals(bean.getFunctionId());
	}
	
	@Override
	public int hashCode() {
		 if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getFunctionId())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":"+ this.getFunctionId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;

	}
	public synchronized String getFunctionName() {
		return functionName;
	}
	public synchronized void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public synchronized String getFunctionUrl() {
		return functionUrl;
	}
	public synchronized void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}
	public synchronized String getFunctionType() {
		return functionType;
	}
	public synchronized void setFunctionType(String functionType) {
		this.functionType = functionType;
	}
	public synchronized String getParentFunctionId() {
		return parentFunctionId;
	}
	public synchronized void setParentFunctionId(String parentFunctionId) {
		this.parentFunctionId = parentFunctionId;
	}

}
