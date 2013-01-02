package com.nc.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nc.bean.UserBean;
import com.nc.comm.AppException;

public class CorpUserService {
	private String FIND_USER_BY_USERNAMECORP = "getUserbyUserNameBycorp";
	private static final Log logger = LogFactory.getLog(ModuleTreeService.class);
	public UserBean findUserByUserNameCorp(String name,String corpid) throws AppException {
		System.out.println("aa");
		UserBean userbean = null;
		System.out.println("aa");
		try{
			
		}catch(Exception ex){
			logger.error(ex);
			throw new AppException(ex);
		}
	    return userbean;
	}
}
