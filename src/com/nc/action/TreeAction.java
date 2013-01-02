/**
 * 树数据ACTION
 */
package com.nc.action;




import com.nc.bean.UserBean;
import com.nc.service.AreaTreeService;
import com.nc.service.ModuleTreeService;
import com.nc.service.ProduceClassService;
import com.nc.service.RoleService;
import com.newapp.web.interceptor.UserSessionAware;

public class TreeAction extends BaseAction implements UserSessionAware{
	
	private UserBean userSession;
	
	public void setUserSession(UserBean userSession){
		this.userSession = userSession;
	}
	
	private  String jsonData;
	
	private  ModuleTreeService moduleService;
	
	private RoleService  roleService;
	
	private ProduceClassService produceclassservice;
	
	private String roleId;
	
	private String node;
	
	private boolean loadAllNodes;
	private AreaTreeService  areaTreeService;
	
	
	 @Override
	public String execute() throws Exception {
		 return this.executeMethod(this.getActions());
	 }
     
     //导航树
	 public String  forMenu()throws Exception{
		  moduleService.setUserinfo(userSession);
		  jsonData = moduleService.renderForJson("0", true);
		  System.out.println(jsonData);
		 return SUCCESS;
		 
	 }
	 //角色列表树
	 public String forRole() throws Exception{
		 jsonData = roleService.renderForJson("", false);
		  System.out.println(jsonData);
		 return SUCCESS; 
	 }
	 //产品列表树
	 public String forProductclass()throws Exception{
		 if(node==null||node.equals("00")){
			 jsonData = "[{id:'03','text':'产成品'},{id:'09','text':'出口产品'}]";
		 }else{
			  jsonData = produceclassservice.renderForJson(node, false);
		 }
		 return SUCCESS; 
	 }
	 
	 public String forareaTree()throws Exception{
		 jsonData = areaTreeService.renderForJson(node, false);
		 return SUCCESS; 
	 }


	public ModuleTreeService getService()throws Exception {
		
		return moduleService;
	}







	public void setService(ModuleTreeService service) {
		this.moduleService = service;
	}







	public String getJsonData() {
		return jsonData;
	}







	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}



	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}


	public RoleService getRoleService() {
		return roleService;
	}


	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}


	public ModuleTreeService getModuleService() {
		return moduleService;
	}


	public void setModuleService(ModuleTreeService moduleService) {
		this.moduleService = moduleService;
	}


	public String getNode() {
		return node;
	}


	public void setNode(String node) {
		this.node = node;
	}


	public boolean isLoadAllNodes() {
		return loadAllNodes;
	}


	public void setLoadAllNodes(boolean loadAllNodes) {
		this.loadAllNodes = loadAllNodes;
	}


	public  ProduceClassService getProduceclassservice() {
		return produceclassservice;
	}


	public  void setProduceclassservice(
			ProduceClassService produceclassservice) {
		this.produceclassservice = produceclassservice;
	}

	public AreaTreeService getAreaTreeService() {
		return areaTreeService;
	}

	public void setAreaTreeService(AreaTreeService areaTreeService) {
		this.areaTreeService = areaTreeService;
	}

	



	
}
