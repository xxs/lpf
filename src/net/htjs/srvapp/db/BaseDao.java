package net.htjs.srvapp.db;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.dao.client.DaoException;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 时间: 2006-10-10 15:59:02 SqlMapClient是线程安全的，所以把它设计成单实例，并做为数据对象操作的基类,为它的子
 * 类提供这个对象；同时提供日志工具对象
 * 
 * @author <a href="mailto:zhouchaoyang@htjs.net">zhouchaoyang</a>
 */



public class BaseDao extends SqlMapClientDaoSupport implements IDao {
	protected static Logger log = Logger.getLogger(BaseDao.class);
	private static ClassPathXmlApplicationContext ctx;
	private static BaseDao baseDao=null; 
	static{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		baseDao = (BaseDao)ctx.getBean("baseDao");
	}

	/*
	 * static { log.debug("在静态块中开始初始化SqlMapClient为单一对象"); try {
	 * PropertyConfigurator.configure(BaseDao.class
	 * .getResource("/log4j.properties")); log.debug("加载属性文件:db.properties");
	 * Properties config = new Properties();
	 * config.load(BaseDao.class.getResourceAsStream("/db.properties")); String
	 * mapType = config.getProperty("sqlMapConfigType");
	 * log.debug("从属性文件中获取SqlMapConfig的类型:" + mapType); String resource =
	 * config.getProperty(mapType); log.debug("加载SqlMapConfig文档名称：" + resource);
	 * Reader reader = Resources.getResourceAsReader(resource); //
	 * XmlSqlMapClientBuilder sqlMapClientBuilder = new //
	 * XmlSqlMapClientBuilder(); // sqlMap =
	 * sqlMapClientBuilder.buildSqlMap(reader); sqlMap =
	 * SqlMapClientBuilder.buildSqlMapClient(reader); } catch (IOException e) {
	 * e.printStackTrace(); // log.error(e.getMessage());
	 * log.error(e.getMessage()); throw new RuntimeException("不能够初始化BaseDao类:" +
	 * e); } catch (Exception e) { e.printStackTrace();
	 * log.debug(e.getMessage()); // throw new
	 * RuntimeException("不能够初始化BaseDao类:" + e); } log.debug("SqlMapClient初始完毕"); }
	 */
	
	
	
	/**
	 * @param sid
	 * @param param
	 * @return ths list of result
	 */
	public List queryForList(String sid, Object param) {
		// param = ParamUtil.initResultSetParam(param);
		try {
			Map tmp = new HashMap();
			tmp.putAll((Map)param);
			tmp.put("CUR_SQLID", sid);
	
			param = this.getQxxkSjfwgz(tmp);
			//if(sid.equals("selectKHGL_JBXX_BYDH")){
			//}
			return getSqlMapClient().queryForList(sid, param);
		} catch (SQLException e) {
			Map map = new HashMap();
			//writeLog(sid, e.getMessage(), 0, "查询失败", map);
			log.error("查询失败", e);
		}
		return null;
	}

	/**
	 * @param sid
	 * @param ids
	 * @return 执行批量删除
	 */
	public int deleteBatch(String sid, String ids[]) {
		int rtn = 0;
		try {
			startBatch();
			String idStr = "";
			for (int i = 0; i < ids.length; i++) {
				idStr = idStr + ids[i] + ",";
				getSqlMapClient().delete(sid, ids[i]);
			}
			executeBatch();
			rtn = 1;
			Map map = new HashMap();
			map.put("ids", idStr);
			//writeLog(sid, "", 1, "批量删除", map);
		} catch (Exception e) {
			Map map = new HashMap();
			map.put("ids", ids);
			//writeLog(sid, e.getMessage(), 0, "批量删除", map);
			throw new DaoException("startBatch failed caused by:" + e, e);
		}

		return rtn;
	}

	/**
	 * 
	 */
	public void startBatch() {
		try {
			// sqlMap.endTransaction();
			getSqlMapClient().startTransaction();
			getSqlMapClient().startBatch();
		} catch (SQLException e) {
			try {
				getSqlMapClient().endTransaction();
			} catch (SQLException e2) {
				log.error("启动事务失败", e2);
			}
			throw new DaoException("startBatch failed caused by:" + e, e);
		}
	}


	/**
	 * @return
	 */
	public int executeBatch() throws SQLException{
		int i = 0;
		getSqlMapClient().executeBatch();
		getSqlMapClient().commitTransaction();
		getSqlMapClient().endTransaction();
		i = 1;
		return i;
	}

	public Object queryForObject(String sid, Object obj) {
		try {
			Map tmp = new HashMap();
			tmp.putAll((Map)obj);
			tmp.put("CUR_SQLID", sid);
			obj = this.getQxxkSjfwgz(tmp);
			return getSqlMapClient().queryForObject(sid, obj);
		} catch (SQLException e) {
			log.error("查询失败", e);
			Map map = new HashMap();
			//writeLog(sid, e.getMessage(), 0, "查询对象", map);
			//throw new DaoException("queryForObject failed caused by:" + e, e);
			return null;
		}
	}

	/**
	 * @param sid
	 * @param obj
	 * @return 1、成功 0失败 -1 主键冲突
	 */
	public int insert(String sid, Object obj) {
		int i = 0;
		try {
			getSqlMapClient().insert(sid, obj);
			i = 1;
			Map map = new HashMap();
			//writeLog(sid, "", 1, "添加", map);
		} catch (SQLException e) {
			log.error("添加保存失败", e);
			if (e.getMessage().indexOf("ORA-00001") > -1) {
				i = -1;
			}
			Map map = new HashMap();
			//writeLog(sid, e.getMessage(), 0, "添加", map);
		}
		return i;
	}

	public int update(String sid, Object obj) {
		Map map = new HashMap();
		if(obj instanceof Map){
			map = (Map)obj;
		}
		try {
			getSqlMapClient().update(sid, obj);
			
			//writeLog(sid, "", 1, "修改", map);
		} catch (SQLException e) {
			//writeLog(sid, e.getMessage(), 0, "修改", map);
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	
	public int batch(String sid, Object obj) throws Exception{
		Map map = new HashMap();
		if(obj instanceof Map){
			map = (Map)obj;
		}
			getSqlMapClient().update(sid, obj);
			
			//writeLog(sid, "", 1, "修改", map);
		return 1;
	}

	public int delete(String sid, Object obj) {
		int i = 0;
		try {
			getSqlMapClient().delete(sid, obj);
			Map map = new HashMap();
			//writeLog(sid, "", 1, "删除", map);
			i = 1;
		} catch (SQLException e) {
			Map map = new HashMap();
			//writeLog(sid, e.getMessage(), 0, "删除失败", map);
			if (e.getMessage().indexOf("ORA-02292") > -1) {
				i = -1;
			}
			log.error("删除失败", e);
		}
		return i;
	}
	
	
	

	public int add(String sid, Object param) {
		int i = 0;
		i = insert(sid, param);
		return i;
	}

	
	/**
	 * 记录操作日志
	 * 
	 * @param sid
	 * @param bz
	 * @param cgbz
	 * @param czmc
	 * @param obj
	 * @return
	 */
	public int writeLog(String sid, String bz, int cgbz, String czmc, Object obj) {
		if (sid.equals("insertSY_LOG")) {
			return 1;
		}
		Map tmp = new HashMap();
		Map map = new HashMap();
		if (obj instanceof Map) {
			tmp = (Map) obj;
		}
		map.put("SIDS", sid);
		map.put("CZY", tmp.get("LOG_USERID") == null ? "-1" : tmp.get("LOG_USERID"));
		map.put("BZ", bz);
		map.put("CGBZ", new Integer(cgbz));
		map.put("CZMC", czmc);
		String param = obj.toString();
		if (param.length() > 4000) {
			param = param.substring(0, 4000);
		}
		map.put("PARAM", param);
		map.put("RESOURCE_DM", tmp.get("qxxkdm") == null ? "0" : tmp
				.get("qxxkdm"));
		try {
			baseDao.getSqlMapClient().insert("insertSY_LOG", map);
		} catch (Exception e) {
			log.error("记录日志失败", e);
			return 0;
		}

		return 1;
	}
	private Object getQxxkSjfwgz(Map param) {
		try {
			if(param.containsKey("xtqxgz")){
				param.remove("xtqxgz");
			}
			//2009－04－27注释
			if(param.containsKey("qxxkdm")&&!param.get("qxxkdm").equals("") &&!param.get("qxxkdm").equals("null")){
				//qx_gwx_sjqxgz.xml
				//增加对当前用户和当前组织机构的支持
			    String qxgzStr = String.valueOf(baseDao.getSqlMapClient().queryForObject("selectUSER_QXXK_SJQXGZ", param));
			    if(param.containsKey("LOG_CZRY_DM")&&qxgzStr.indexOf("c_user")!=-1){
			    	qxgzStr=qxgzStr.replaceAll("#c_user#", param.get("LOG_CZRY_DM").toString());
			    }
			    if(param.containsKey("LOG_ZZJG_DM")&&qxgzStr.indexOf("#c_zzjg#")!=-1)
			    	qxgzStr=qxgzStr.replaceAll("#c_zzjg#", param.get("LOG_ZZJG_DM").toString());
			    if(!qxgzStr.equals("null")&&!qxgzStr.equals("")){
			        param.put("xtqxgz", qxgzStr);
			    }else{
			    	param.put("xtqxgz", "");
			    }
			}
		} catch (SQLException e) {
			log.error("获取数据权限失败", e);
		}
		return param;
		// Map tmp = (Map) obj;
		// if(!tmp.containsKey("qxxkdm")||tmp.get("qxxkdm").equals("")){
		// return tmp;
		// }
		// StringBuffer strBuffer = new StringBuffer("");
		// try {
		// //qx_user.xml取得用户的当前操作的所有数据权限规则
		// List list = getSqlMapClient().queryForList("selectUSER_QXXK_SJQXGZ",
		// obj);
		// //qx_user_gw.xml取得用户的所属岗位
		// List list2 = getSqlMapClient().queryForList("selectQX_USER_GW",obj);
		// for(int i=0;i<list2.size();i++){//去掉岗位中的Map层，list直接保存岗位代码
		// Map map = (Map)list2.get(i);
		// list2.remove(i);
		// list2.add(i,map.get("GW_DM"));
		// }
		//			
		// if (list != null && !list.isEmpty()) {
		// strBuffer.append("(");
		// Map map = null;
		// Map old = null;
		// for (Iterator iterator = list.iterator(); iterator.hasNext();) {
		// map = (Map) iterator.next();
		// if (old != null) {//非第一个元素，需要添加AND 或者 OR
		// if (old.get("GW_DM").equals(map.get("GW_DM"))) {//同岗位，只添加AND
		// strBuffer.append(" AND ");
		// } else {//不同岗位
		// strBuffer.append(") OR (");
		// }
		//						
		// }
		// strBuffer.append(map.get("LQX_VALUE"));
		// //过滤权限规则中的岗位，如果将出现的岗位，在岗位列表中排除
		// if((list2.indexOf((String)map.get("GW_DM"))!=-1)){
		// list2.remove((String)map.get("GW_DM"));
		// }
		// old = map;
		// }
		// strBuffer.append(")");
		// }
		// //如果岗位列表不为空，则表示当前用户所有岗位中有至少一个岗位没有设定权限，
		// //鉴于数据规则取岗位中的最大值，因此相当于对该用户没有设置数据权限
		// if(!list2.isEmpty()){
		// return tmp;
		// }
		//			
		// // org.apache.commons.beanutils.BeanUtils.setProperty(tmp, "xtqxgz",
		// // strBuffer.toString());
		// ((Map) tmp).put("xtqxgz", strBuffer.toString());
		// } catch (SQLException e) {
		// log.error("查询失败", e);
		// }
		// return tmp;
		// return obj;
	}

}
