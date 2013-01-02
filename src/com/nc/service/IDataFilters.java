/**
 * 数据过滤接口
 */
package com.nc.service;

import java.util.List;
import java.util.Map;

import com.nc.comm.AppException;
import com.nc.dao.ibatis.IDAO;

public interface IDataFilters {
	
	IDAO getBatisDao();
	
	void setBatisDao(IDAO batisDao);
	
	List filterData(List discData,Map param)throws AppException;

}
