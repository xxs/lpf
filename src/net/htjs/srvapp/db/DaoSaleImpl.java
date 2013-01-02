package net.htjs.srvapp.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author 周朝阳
 * @date 2008-1-17
 */
public class DaoSaleImpl extends BaseDao implements IDaoSale {

	public int add(List list) {
		int i = 0;

		Map baseMap = (Map) list.get(0);
		try {
			this.startBatch();
			Map temp = new HashMap();
			temp.put("SEQ_NAME", "SEQ_XSGL_XSDJ_XSID");
			baseMap.put("XSID", this.queryForObject("selectSEQUENCE", temp));//xml:SEQUENCE.xml
			this.add("insertXSGL_XSDJ", baseMap);// 添加发票信息。对应的xml:XSGL_XSDJ.xml
			List list2 = (List) list.get(1);
			addMx(String.valueOf(baseMap.get("XSID")),list2);
			i=1;
		} catch (Exception e) {
			log.error("添加单据错误", e);
			i=0;
		} finally {
			try{
			i=this.executeBatch();
			}catch (Exception e) {
				log.error("添加单据错误", e);
				i=0;
			} 
		}
		return i;
	}
	
	private void addMx(String xsid,List list){
		int j=0;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {				
			Map map = (Map) iterator.next();
			map.put("XSID", xsid);
			map.put("XH", new Integer(j++));
			this.add("insertXSGL_XSDJ_MX", map);// 添加单据明细。对应的xml:XSGL_XSDJ_MX.xml
		}
	}

	public int update(List list) {
		int i = 0;

		Map baseMap = (Map) list.get(0);
		try {
			this.startBatch();
			this.add("updateXSGL_XSDJ", baseMap);// 添加发票信息。对应的xml:XSGL_XSDJ.xml
			List list2 = (List) list.get(1);
			addMx(String.valueOf(baseMap.get("XSID")),list2);
			i=1;
		} catch (Exception e) {
			log.error("添加单据错误", e);
			i=0;
		} finally {
			try{
				i=this.executeBatch();
				}catch (Exception e) {
					log.error("添加单据错误", e);
					i=0;
				} 
		}
		return i;
	}
	

}
