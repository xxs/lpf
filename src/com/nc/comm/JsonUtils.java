/**
 * json工具类
 */
package com.nc.comm;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nc.bean.UserBean;
import com.nc.service.ModuleTreeService;

public class JsonUtils {

	private static final Log logger = LogFactory
			.getLog(ModuleTreeService.class);

	private JsonUtils() {

	}

	public static final String JSON_ROOT = "records";
	public static final String JSON_TOTAL = "totalcount";
    /**
     * LISt数据转EXTJS GRID JSON数据格式!
     * @param datas 表一页数
     * @param total 表总行数
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws JSONException
     */
	public static String encodeData2Json(List datas, int total)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, JSONException {
		if (datas == null || datas.size() == 0) {
			return "{" + JSON_ROOT + ":[]}";
		}
		JSONObject json = new JSONObject();
		JSONArray jsonData = new JSONArray();
		for (Object orig : datas) {
			JSONObject rowData = new JSONObject();

			if (orig instanceof Map) {
				Iterator names = ((Map) orig).keySet().iterator();
				while (names.hasNext()) {
					String name = (String) names.next();
					Object value = ((Map) orig).get(name);
					if(value==null)value="";
					rowData.put(name, value);
				}
			} else {

				PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(orig);
				for (PropertyDescriptor aPd : origDescriptors) {
					if (aPd.getReadMethod() == null) {
						continue;
					}
					String name = aPd.getName();

					if ("class".equals(name)) {
						continue;
					}
					Object value = null;

					if (orig == null) {
						throw new IllegalArgumentException("No bean specified");
					}
					if (name == null) {
						throw new IllegalArgumentException("No name specified");
					}
					Method readMethod = aPd.getReadMethod();

					if (readMethod == null) {
						throw new NoSuchMethodException("Property '" + name
								+ "' has no getter method");
					}
					value = readMethod.invoke(orig, new Object[0]);

					rowData.put(name, value);

				}
				
			}
			jsonData.put(rowData);
		}
		json.put(JSON_ROOT, jsonData);
		json.put(JSON_TOTAL, total);
		return json.toString();

	}
   /**
    * 生成EXTJS TREE JSON数据
    * @param provider 数据源
    * @param parentUserData 递归父类数据
    * @param dataArray 当前数据
    * @param loadAllNodes 是否一次性读取全部
    * @throws AppException
    */
	public static void buildTreeNodeJsonData(TreeDataProvider provider,
			Object parentUserData, JSONArray dataArray, boolean loadAllNodes)
			throws AppException {
		try {

			for (Object child : provider.getChildren(parentUserData)) {
				UITreeNode newTreeNode = (UITreeNode) child;
				if (loadAllNodes) {
					newTreeNode.setExpanded(false);
				}
				JSONObject object = new JSONObject();

				object.put("id", newTreeNode.getId());

				if (newTreeNode.getText() != null)
					object.put("text", newTreeNode.getText());
				if (newTreeNode.getIcon() != null) {
					object.put("icon", newTreeNode.getIcon());
				} else {
					object.put("iconCls", "");
				}
				if (newTreeNode.getExpanded() != null)
					object.put("expanded", provider.isExpanded(newTreeNode));
				if (newTreeNode.getHref() != null){
					String strhref = provider.getHref(newTreeNode);
					object.put("href", strhref==null?newTreeNode.getHref():strhref);
				}
				if (newTreeNode.getHrefTarget() != null)
					object.put("hrefTarget", newTreeNode.getHrefTarget());
				object.put("leaf", provider.isLeaf(newTreeNode.getId()));
				if (newTreeNode.getQtip() != null)
					object.put("qtip", newTreeNode.getQtip());
				if (newTreeNode.getChecked() != null) {
					if (Boolean.TRUE.equals(newTreeNode.getChecked())) {
						object.put("check", "checked");
					} else {
						object.put("check", "unchecked");
					}

					if (Boolean.TRUE.equals(newTreeNode.getCascade())) {
						object.put("uiProvider", "Ext.tree.CheckboxNodeUI");
					} else {
						object.put("uiProvider","Ext.tree.SimpleCheckboxNodeUI");
					}
				}
				if (loadAllNodes) {
					JSONArray childrenArray = new JSONArray();
					buildTreeNodeJsonData(provider, ((UITreeNode) child)
							.getId(), childrenArray, loadAllNodes);
					object.put("children", childrenArray);
				}
				dataArray.put(object);
			}
		} catch (Exception ex) {
			logger.error(ex);
			throw new AppException(ex);
		}
	}

	public static void main(String[] args) {
		List list = new ArrayList();
		UserBean b1 = new UserBean();
		b1.setName("sdfasd");
		b1.setPassWord("sdf");
		b1.setUserName("we");
		UserBean b2 = new UserBean();
		b2.setName("sdfawerqwersd");
		b2.setPassWord("serqwerqdf");
		b2.setUserId("sdfeerwasdf");
		list.add(b2);
		list.add(b1);
		try {
			System.out.print(JsonUtils.encodeData2Json(list, 1000));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
