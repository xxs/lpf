package com.nc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;

import com.nc.comm.AppException;
import com.nc.comm.JsonUtils;
import com.nc.comm.TreeDataProvider;
import com.nc.comm.UITreeNode;
import com.nc.dao.ibatis.IDAO;

public class AreaTreeService {
	private IDAO baseDao;
	private static final String GET_AREA_TREE = "getareatree";
	private static final String IS_LEAF = "getareatreeisLeaf";
	private static  Log logger = LogFactory.getLog(AreaTreeService.class);
	private TreeDataProvider Provider = new TreeDataProvider(){
    	public Object[] getChildren(Object userData)throws Exception {
    	  try {
    			List<Map> children = baseDao.getDataByObject(GET_AREA_TREE, userData);
    			List treeNode = new ArrayList();
    			
    			for(Map<String,String> obj : children){    			
    				UITreeNode node =  new UITreeNode();
    				node.setId(obj.get("PK_DEPTDOC"));
    				node.setText(obj.get("DEPTNAME"));
    				node.setQtip(obj.get("DEPTCODE"));
    				node.setLeaf(false);
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
			int count = baseDao.getIntByObject(IS_LEAF, userData);
			return count<=1;
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
	
	public String renderForJson(Object parentUserData, boolean loadAllNodes)throws AppException {
		JSONArray dataArray = new JSONArray();
		JsonUtils.buildTreeNodeJsonData(Provider,parentUserData, dataArray, loadAllNodes);
		return dataArray.toString();
	}
	
	

	public  IDAO getBaseDao() {
		return baseDao;
	}

	public  void setBaseDao(IDAO baseDao) {
		this.baseDao = baseDao;
	}

}
