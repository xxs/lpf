package net.htjs.srvapp.db;

import java.util.List;

/**
 * @author 周朝阳
 * @date 2008-1-17
 */
public interface IDaoSale extends IDao {
	/**
	 * @param list
	 * @return
	 */
	public int add(List list);
	
	/**
	 * @param list
	 * @return
	 */
	public int update(List list);
}

