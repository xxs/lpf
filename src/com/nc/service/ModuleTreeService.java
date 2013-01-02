/**
 * 系数模块管理类
 */
package com.nc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import com.nc.bean.UserBean;
import com.nc.bean.UserFunctionBean;
import com.nc.comm.AppException;
import com.nc.comm.Constant;
import com.nc.comm.JsonUtils;
import com.nc.comm.TreeDataProvider;
import com.nc.comm.UITreeNode;
import com.nc.dao.ibatis.IDAO;

public class ModuleTreeService {
	private static final String GEY_MODULE_ID = "getModuleByPid";
	private static final String PID_KEY = "0";
	private static final String COUNT_ROW = "countRow";
	private static final Log logger = LogFactory.getLog(ModuleTreeService.class);

	private IDAO treeDao;

	private UserBean userinfo;
    /**
     * 模块树数据查询类
     */
	private TreeDataProvider Provider = new TreeDataProvider() {
		public Object[] getChildren(Object userData) throws Exception {
			try {
				List children = null;
				if (userData == null) {
					children = treeDao.getDataByObject(GEY_MODULE_ID, PID_KEY);
				} else {
					children = treeDao.getDataByObject(GEY_MODULE_ID, userData);
				}
                 //过滤所需
				return filterTree(children).toArray();
			} catch (Exception ex) {
				logger.error(ex);
				throw ex;
			}
		}
       /*
        * 过滤符合权限的用户
        */
		private List filterTree(List treenodes) {
			List children = new ArrayList();
			for (Iterator it = treenodes.iterator(); it.hasNext();) {
				UserFunctionBean function = (UserFunctionBean) it.next();
				
				if (userinfo.getFunctions().contains(function)
					||function.getParentFunctionId().equals("0")) {
					UITreeNode node = new UITreeNode();
					node.setId(function.getFunctionId());
					node.setText(function.getFunctionName());
					if(!function.getFunctionUrl().equals("#"))
					node.setHref("baseaction.action?functionId="+function.getFunctionId()+"&type="+function.getFunctionType()+"&navigationUrl="+ function.getFunctionUrl());
				
					node.setHrefTarget(Constant.MAIN_TARGET);

					children.add(node);
				}

			}
			treenodes.clear();
			return children;
		}

		public String getHref(Object userData) {
			return null;

		}

		public boolean isLeaf(Object userData) throws Exception {

			return (treeDao.getIntByObject("countRow", userData) <= 0);
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

	public String renderForJson(Object parentUserData, boolean loadAllNodes)
			throws AppException {
		JSONArray dataArray = new JSONArray();
		JsonUtils.buildTreeNodeJsonData(Provider, parentUserData, dataArray,
				loadAllNodes);
		return dataArray.toString();
	}

	public IDAO getTreeDao() {
		return treeDao;
	}

	public void setTreeDao(IDAO treeDao) {
		this.treeDao = treeDao;
	}

	public UserBean getUserinfo() {
		return userinfo;
	}

	public synchronized void setUserinfo(UserBean userinfo) {
		this.userinfo = userinfo;
	}

}
