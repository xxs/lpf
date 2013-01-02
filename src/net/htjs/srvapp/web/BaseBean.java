package net.htjs.srvapp.web;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import net.htjs.srvapp.db.BaseDao;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zcy
 * @date 2007-5-6
 * 
 */
public class BaseBean extends BaseAttribute {

	protected Logger log = Logger.getLogger(BaseBean.class);

	private int rowsCount = 0;

	protected BaseDao dao;

	/**
	 * srping context;
	 */
	public static ClassPathXmlApplicationContext ctx;
	static {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

	}

	/**
	 * 
	 */
	public BaseBean() {
		super();
		dao = (BaseDao) ctx.getBean("baseDao");
	}

	/**
	 * @param daoType
	 * 
	 */
	public BaseBean(String daoType) {
		super();
		dao = (BaseDao) ctx.getBean("baseDao" + daoType);
	}

	/**
	 * @param daoType
	 */
	public void initDao(String daoType) {
		dao = (BaseDao) ctx.getBean("baseDao" + daoType);
	}

	/**
	 * @param sid
	 * @param map
	 * @return
	 */
	protected int addNew(String sid, Map map) {
		return dao.add(sid, map);
	}

	/**
	 * 填加一条新数据，根据SID调用
	 * 
	 * @param request
	 * @return 1成功、0失败
	 */
	protected int addNew(HttpServletRequest request) {
		int i = 0;
		this.parserRequest(request);
		if (this.hasAttribute("SID")) {
			i = addNew((String) this.getAttribute("SID"), this.getAttributes());
		}
		return i;
	}

	/**
	 * 修改一条数据，根据SID调用
	 * 
	 * @param request
	 * @return 1成功、0失败
	 */
	protected int update(HttpServletRequest request) {
		int i = 0;
		this.parserRequest(request);
		if (this.hasAttribute("SID")) {
			try {
				i = dao.update((String) this.getAttribute("SID"),
						this.getAttributes());
			} catch (Exception e) {
				log.error(
						"Failed to update new data;SID=["
								+ this.getAttribute("SID") + "];data=["
								+ this.getAttributes() + "]", e);
			}

		}
		return i;
	}

	/**
	 * 修改一条数据，根据SID调用
	 * 
	 * @param sid
	 * @param para
	 * 
	 * @param request
	 * @return 1成功、0失败
	 */
	protected int update(String sid, Object para) {
		int i = 0;
		try {
			i = dao.update(sid, para);
		} catch (Exception e) {
			log.error(
					"Failed to update new data;SID=["
							+ this.getAttribute("SID") + "];data=["
							+ this.getAttributes() + "]", e);
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 修改一条数据，根据SID调用
	 * 
	 * @param sid
	 * @param para
	 * 
	 * @param request
	 * @return 1成功、0失败
	 */
	protected int batch(String sid, Object para) throws Exception {
		int i = 0;
		dao.batch(sid, para);
		return i;
	}

	/**
	 * 删除一条数据，根据SID调用
	 * 
	 * @param request
	 * @return 1成功、0失败
	 */
	protected int delete(HttpServletRequest request) {
		int i = 0;
		this.parserRequest(request);
		if (this.hasAttribute("SID")) {
			try {
				i = 0;
				i = dao.delete((String) this.getAttribute("SID"),
						this.getAttributes());

			} catch (Exception e) {
				log.error(
						"Failed to delete new data;SID=["
								+ this.getAttribute("SID") + "];data=["
								+ this.getAttributes() + "]", e);
			}
		}
		return i;
	}

	/**
	 * 删除一条数据，根据SID调用
	 * 
	 * @param sid
	 * @param param
	 * 
	 * @param request
	 * @return 1成功、0失败
	 */
	protected int delete(String sid, Map param) {
		int i = 0;
		try {
			i = dao.delete(sid, param);

		} catch (Exception e) {
			log.error("Failed to delete data;SID=[" + sid + "];data=[" + param
					+ "]", e);
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 修改一条数据，根据SID调用
	 * 
	 * @param request
	 * @return 1成功、0失败
	 */
	protected int deleteBatch(HttpServletRequest request) {
		int i = 0;
		if (this.hasAttribute("SID")) {
			try {
				i = dao.deleteBatch((String) this.getAttribute("SID"),
						request.getParameterValues("ID"));
			} catch (Exception e) {
				log.error(
						"Failed to delete new data;SID=["
								+ this.getAttribute("SID") + "];data=["
								+ this.getAttributes() + "]", e);
			}
		}
		return i;
	}

	/**
	 * 成批事务处理sql
	 * 
	 * @param request
	 * @return 1成功、0失败
	 */
	public int execteBatch(Vector vsqls, Map inMap) throws SQLException {
		int k = 0;
		// IDao dao = new BaseDao();
		dao.startBatch();
		for (int i = 0; i < vsqls.size(); i++) {
			int j = dao.update(vsqls.elementAt(i).toString(), inMap);
			// dao.writeLog(vsqls.elementAt(i).toString(), "", j, "UNKNOW",
			// inMap);
		}
		k = dao.executeBatch();
		return k;
	}

	/**
	 * @return
	 */
	public int getRowsCount() {
		return rowsCount;
	}

	/**
	 * @param rowsCount
	 */
	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}

	/**
	 * 在进行查询返回列表时，每条记录中含有total字段表示满足条件的记录总数，将其取处理作为为rowsCount
	 * 
	 * @param list
	 */
	public void setRowsCountFromResult(List list) {
		if (list != null && !list.isEmpty()) {
			Object obj = list.get(0);
			try {
				if (this.getRowsCount() == 0) {
					String tmp = org.apache.commons.beanutils.BeanUtils
							.getProperty(obj, "TOTAL");
					this.setRowsCount(Integer.parseInt(tmp));
				}
			} catch (IllegalAccessException e) {
				log.info(e.getMessage());
			} catch (InvocationTargetException e) {
				log.info(e.getMessage());
			} catch (NoSuchMethodException e) {
				log.info(e.getMessage());
			} catch (NumberFormatException e) {
				log.info(e.getMessage());
			}
		}
	}

	public void parserRequest(HttpServletRequest request, int psize) {
		this.setAttribute("pageSize", new Integer(psize));
		parserRequest(request);
	}

	public void parserRequest(HttpServletRequest request) {
		super.parserRequest(request);
		this.setAttribute("LOG_USERID",
				request.getSession().getAttribute("USERID"));
		this.setAttribute("LOG_CZRY_DM",
				request.getSession().getAttribute("CZRY_DM"));
		this.setAttribute("LOG_CZRY_MC",
				request.getSession().getAttribute("CZRY_MC"));
		this.setAttribute("LOG_ZZJG_DM",
				request.getSession().getAttribute("ZZJG_DM"));
		if (this.hasAttribute("recCount")) {
			try {
				this.setRowsCount(Integer.parseInt((String) this
						.getAttribute("recCount")));
			} catch (Exception e) {
			}
		}
		int pageSize = 20;
		if (this.hasAttribute("pageSize")) {
			try {
				pageSize = Integer.parseInt((String) this
						.getAttribute("pageSize"));
			} catch (Exception e) {
			}
		}

		if (this.hasAttribute("page") && !this.getAttribute("page").equals("")) {
			int tmp = Integer.parseInt((String) this.getAttribute("page"));
			tmp = tmp * pageSize;
			this.setAttribute("START", new Integer(tmp - pageSize + 1));
			this.setAttribute("END", new Integer(tmp));
		} else {
			if (!this.hasAttribute("START"))
				this.setAttribute("START", "0");
			if (!this.hasAttribute("END"))
				this.setAttribute("END", String.valueOf(pageSize));
		}
		if (this.hasAttribute("queryItem")) {
			this.setAttribute("queryItem",
					request.getParameterValues("queryItem"));
			/*
			 * String[] s = request.getParameterValues("queryItem");
			 * StringBuffer strBuffer=new StringBuffer(); for(int
			 * i=0;i<s.length;i++){ strBuffer.append(s[i]); if(i!=s.length-1){
			 * strBuffer.append(","); } }
			 */
		}
	}

	/**
	 * 根据条件和sid，查询满足条件的列表
	 * 
	 * @param sid
	 * @param request
	 * @return
	 */
	public List getList(String sid, HttpServletRequest request) {
		this.parserRequest(request);
		return getList(sid, this.getAttributes());
	}

	/**
	 * 根据条件和sid，查询满足条件的列表
	 * 
	 * @param sid
	 * @param map
	 * @return
	 */
	public List getList(String sid, Map map) {
		return dao.queryForList(sid, map);
	}

	/**
	 * 根据条件和sid，查询满足条件的单条信息
	 * 
	 * @param sid
	 * @param map
	 * @return
	 */
	public Object getObject(String sid, Map map) {
		return dao.queryForObject(sid, map);
	}

	/**
	 * 根据条件和sid，查询满足条件的单条信息
	 * 
	 * @param sid
	 * @param request
	 * @return
	 */
	public Object getObject(String sid, HttpServletRequest request) {
		this.parserRequest(request);
		return getObject(sid, this.getAttributes());
	}

	/**
	 * @return
	 */
	public BaseDao getDao() {
		return dao;
	}

	/**
	 * @param dao
	 */
	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

}
