package com.nc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;

import com.nc.bean.RoleBean;
import com.nc.comm.AppException;
import com.nc.comm.JsonUtils;
import com.nc.comm.TreeDataProvider;
import com.nc.comm.UITreeNode;
import com.nc.dao.ibatis.IDAO;

public class RoleService {
	private IDAO baseDao;
	
	
	
	private static final String GET_ROLE = "getAllRole";
	
	private static final String SAVE_ROLE_FOR_USER = "";
	
	private static final String UPDATE_ROLE_ROR_USER = "";
	
	
	private static final Log logger = LogFactory.getLog(ModuleTreeService.class);
	
	private TreeDataProvider Provider = new TreeDataProvider(){
    	public Object[] getChildren(Object userData)throws Exception {
    	  try {
    			List<RoleBean> children = baseDao.getDataByObject(GET_ROLE, null);
    			List treeNode = new ArrayList();
    			for(RoleBean obj : children){    			
    				UITreeNode node =  new UITreeNode();
    				node.setId(obj.getRoleId());
    				node.setText(obj.getRoleName());
    				treeNode.add(node);
    			}
    			
    			return treeNode.toArray();
    		} catch (Exception ex) {
    			logger.error(ex);
    			throw ex;
    		}
		}
    	

		public String getHref(Object userData) {
			return "";
		}
		
		public boolean isLeaf(Object userData)throws Exception{
			
			return true;
		}

		public String getHrefTarget(Object userData) {
			return null;
		}

		public String getIcon(Object userData) {
			return "";
		}
		


		public String getText(Object userData) {
			return "";
		}

		public boolean isCascade(Object userData) {
			return false;
		}

		public Boolean isChecked(Object userData) {
			return null;
		}

		public boolean isExpanded(Object userData) {
			return false;
		}
	};
	
	public void saveRoleForUser(Map<String,String> data) throws AppException{
		try {
			List<Map> datas = prcData(data);
			baseDao.batchExecutor("SAVE_ROLE_FOR_USER", datas);
		} catch (Exception ex) {
			logger.error(ex);
			throw new AppException(ex);
		}

	}
	


	private List<Map> prcData(Map<String, String> data) {
		Object userId = data.get("USERID");
		Object strroleId = data.get("roleId");
		if (strroleId == null) {
			throw new NullPointerException("roleId not null");
		}
		if (userId == null) {
			throw new NullPointerException("roleId not null");
		}
		String[] arrRoleId = ((String) strroleId).split(",");
		List<Map> datas = new ArrayList<Map>();
		for (String keyId : arrRoleId) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("userId", (String)userId);
			map.put("roleId", keyId);
			datas.add(map);
		}
		return datas;
	}
	
	public String renderForJson(Object parentUserData, boolean loadAllNodes)throws AppException {
		JSONArray dataArray = new JSONArray();
		JsonUtils.buildTreeNodeJsonData(Provider,parentUserData, dataArray, loadAllNodes);
		return dataArray.toString();
	}

	public IDAO getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(IDAO baseDao) {
		this.baseDao = baseDao;
	}

}
