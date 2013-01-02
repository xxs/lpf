package com.nc.service;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nc.bean.TenderUserBean;
import com.nc.comm.AppException;
import com.nc.dao.ibatis.IDAO;

public class TenderUserService {
	private IDAO baseDao;
	private String FIND_TENDERUSER_BY_USERNAME = "getTenderUserbyUserName";
	private static final Log logger = LogFactory.getLog(ModuleTreeService.class);
	public TenderUserBean findTenderUserByUserName(String name) throws AppException {
		try{
			Object orguser = baseDao.getObjByObject(FIND_TENDERUSER_BY_USERNAME,name,null);
			System.out.println("********"+orguser+"*********");
			if(orguser==null){
				return null;
			}
			TenderUserBean username = (TenderUserBean)orguser;
			return username;
			
		}catch(Exception ex){
			logger.error(ex);
			throw new AppException(ex);
		}
	   
		
	}
}
