package com.nc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nc.bean.RoleBean;
import com.nc.bean.UserBean;
import com.nc.bean.UserFunctionBean;
import com.nc.comm.AppException;
import com.nc.comm.NewAppUtil;
import com.nc.dao.ibatis.IDAO;

public class UserService {
	private IDAO baseDao;
	private String FIND_USER_BY_USERNAME = "getUserbyUserName";
	private String FIND_USER_BY_USERID = "getUserbyUserId";
	private String UPDATE_USER_BY = "updateUser";
	private String FIND_ROLE_BY_USERID = "getRoleByUserId";
	private String FIND_FUNCTION_BY_ROLEID = "getFunctionbyRoleId";
	private String ADD_USER = "addUser";
	private String FIND_USER_BY_USERNAMECORP = "getUserbyUserNameBycorp";
	private String FIND_ROLE_BY_USERIDCORP = "getRoleByUserCorpId";
	private static final Log logger = LogFactory.getLog(ModuleTreeService.class);
	
	public UserBean findUserByUserName(String name) throws AppException {
		try{
			Object orguser = baseDao.getObjByObject(FIND_USER_BY_USERNAME,name,null);
			if(orguser==null){
				return null;
			}
			 UserBean username = (UserBean)orguser;
			 Map param = new HashMap();
			 param.put("userid", username.getUserId());
			 List<RoleBean> roles = baseDao.getDataByObject(FIND_ROLE_BY_USERID, param);
			 username.setRoles(roles);
			 Set<UserFunctionBean> functions = new HashSet<UserFunctionBean>();
			 for(RoleBean role : roles){

				 List<UserFunctionBean> function = baseDao.getDataByObject(FIND_FUNCTION_BY_ROLEID,role.getRoleId() ); 

				 if(function!=null&&!function.isEmpty()){
					 functions.addAll(function);
				 } 
			 }
			 for(Iterator it = functions.iterator();it.hasNext();){
				 UserFunctionBean function =  (UserFunctionBean)it.next();
				 if(function.isRight()){
					 it.remove();
				 }
			 }
			 username.setFunctions(functions);
			return username;
			
		}catch(Exception ex){
			logger.error(ex);
			throw new AppException(ex);
		}
	   
		
	}
	
	public UserBean findUserByUserNameCorp(String name,String corpid) throws AppException {
		try{
			Map tmp = new HashMap();
			tmp.put("userId", name);
			tmp.put("corpid", corpid);
			//List orguser = baseDao.getDataByObject(FIND_USER_BY_USERNAMECORP,tmp);
			List<UserBean> orguser = baseDao.getDataByObject(FIND_USER_BY_USERNAMECORP,tmp);
			//List orguser = sdao.queryForList(FIND_USER_BY_USERNAMECORP,tmp);
			
			if(orguser.size() ==0){
				return null;
			}
			 UserBean username = null;
			 for(int i = 0 ; i < orguser.size(); i ++){
				 username = orguser.get(i);
			 }
			 Map param = new HashMap();
			 param.put("userid", username.getUserId());
			 param.put("corpid",corpid);
			 List<RoleBean> roles = baseDao.getDataByObject(FIND_ROLE_BY_USERIDCORP, param);
			 username.setRoles(roles);
			 Set<UserFunctionBean> functions = new HashSet<UserFunctionBean>();
			 for(RoleBean role : roles){
				 List<UserFunctionBean> function = baseDao.getDataByObject(FIND_FUNCTION_BY_ROLEID,role.getRoleId() ); 
				 if(function!=null&&!function.isEmpty()){
					 functions.addAll(function);
				 } 
			 }
			 for(Iterator it = functions.iterator();it.hasNext();){
				 UserFunctionBean function =  (UserFunctionBean)it.next();
				 if(function.isRight()){
					 it.remove();
				 }
			 }
			 username.setFunctions(functions);
			return username;
			
		}catch(Exception ex){
			logger.error(ex);
			throw new AppException(ex);
		}
	   
		
	}
	
	public UserBean findUserByUserId(String userid)throws AppException {
		try{
			return (UserBean)baseDao.getObjByObject(FIND_USER_BY_USERID,userid,null);
		}catch(Exception ex){
			logger.error(ex);
			throw new AppException(ex);
		}
	}
	
	public void updateUserInfo(UserBean user)throws AppException{
		try{
			baseDao.update(UPDATE_USER_BY, user);
		}catch(Exception ex){
			logger.error(ex);
			throw new AppException(ex);
		}
	}
	
	public void saveUser(UserBean user)throws AppException{
		try{
			user.setSkin("ext-all");
			user.setPassWord(NewAppUtil.MD5(user.getUserName()));
			baseDao.update(ADD_USER, user);
		}catch(Exception ex){
			logger.error(ex);
			throw new AppException(ex);
		}
	}
	
	public int updatePassword(Map<String,String> user)throws AppException{
		try{
			UserBean ouser = findUserByUserId(user.get("userId"));
			if(ouser.getPassWord().equals(NewAppUtil.MD5(user.get("oldpassword")))){
				ouser.setPassWord(NewAppUtil.MD5(user.get("newpassword")));
				updateUserInfo(ouser);
			    return 1;
		    }else{
		    	return 0;
		    }
		}catch(Exception ex){
			logger.error(ex);
			throw new AppException(ex);
		}
		
	}
	
	public IDAO getBaseDao() {
		return baseDao;
	}
	public void setBaseDao(IDAO baseDao) {
		this.baseDao = baseDao;
	}

}
