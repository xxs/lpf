package net.htjs.srvapp.db;

import java.util.Map;

import net.htjs.srvapp.db.BaseDao;

/**
 * @author zhouchaoyang
 * @date 2007-11-15
 */
public class DaoQxUserImpl extends BaseDao implements IDaoQxUser {

	public int add(Map map) {
		try {
			this.startBatch();
			this.insert("insertQX_USER", map);
			this.update("insertQX_USER_GW", map);
			this.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	public int update(Map map) {
		try {
			this.startBatch();
			this.update("updateQX_USER", map);
			this.update("insertQX_USER_GW", map);
			this.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	public int delete(Map map) {
		try {
			this.startBatch();
			this.delete("deleteQX_USER", map);
			this.update("deleteQX_USER_GW", map);
			this.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

}
