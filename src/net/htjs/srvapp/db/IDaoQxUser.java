package net.htjs.srvapp.db;

import java.util.Map;

import net.htjs.srvapp.db.IDao;

/**
 * @author zhouchaoyang
 * @date 2007-11-15
 */
public interface IDaoQxUser extends IDao {

	/**
	 * @param map
	 * @return
	 */
	public int add(Map map);

	/**
	 * @param map
	 * @return
	 */
	public int update(Map map);

	/**
	 * @param map
	 * @return
	 */
	public int delete(Map map);

}