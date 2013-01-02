package com.nc.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.nc.comm.AppException;
import com.nc.dao.ibatis.IDAO;

public class StoreFilter implements IDataFilters {
	
	private IDAO batisDao;


	public List filterData(List discData, Map param) throws AppException {
		try {
			for (Iterator it = discData.iterator(); it.hasNext();) {
				Map org = (Map) it.next();
				String id = (String) org.get("CARBILLHID");
				param.put("carbillhid", id);
				int count = getBatisDao().getIntByObject("sotemistake", param);
				if (count == 0) {
					it.remove();
				}
			}
			return discData;
		} catch (Exception ex) {
			throw new AppException(ex);
		}
	}


	public IDAO getBatisDao() {
		// TODO Auto-generated method stub
		return batisDao;
	}


	public void setBatisDao(IDAO batisDao) {
		// TODO Auto-generated method stub
		
		this.batisDao = batisDao;

	}

}
