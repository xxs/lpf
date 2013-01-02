/**
 * dwr总业务服务类
 */
package com.nc.service;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.nc.comm.AppException;
import com.nc.comm.JsonUtils;
import com.nc.comm.NewAppUtil;
import com.nc.dao.ibatis.IDAO;

public class DWRBasicService implements IDWRBasicService {
	private static final Log logger = LogFactory.getLog(ModuleTreeService.class);
	private IDAO batisDao;

	public int getIntByObject (String statementName, Map<String,String> paramMap) throws AppException{
	 try {
		   return batisDao.getIntByObject(statementName,paramMap); 
		} catch (Exception ex) {
			logger.error(ex);
			throw new AppException(ex);
		}
	}
	/**
	 * 取分页数据
	 */
	public String getListForPage(Map<String,String> queryparam) throws AppException {
		try {
			//String statementName,String statementTotalCount, int start, int limit,String orderBy,Map queryparam
			//System.out.println("=========1======");
			String obj1 = queryparam.get("totalSql");
			String obj2 = queryparam.get("listSql");
			String filter = queryparam.get("filter");
			//System.out.println("========"+obj1+")))))"+obj2+"****");
			String statementTotalCount = null,statementName = null;
			int 	total=0;
			if(obj1!=null){
				statementTotalCount = (String)obj1;
				//System.out.println("wo shi shui");
				total =  batisDao.getIntByObject(statementTotalCount, new HashMap());
				//System.out.println("wo shi shui ***"+total);
			}
			if(obj2!=null){
				statementName =  obj2;
			}
			List pageData = batisDao.getDataByObject(statementName,  queryparam);
			if(StringUtils.isNotEmpty(filter)){
				IDataFilters dataFilter = (IDataFilters)NewAppUtil.applicationInstance(filter);
				dataFilter.setBatisDao(batisDao);
				pageData = dataFilter.filterData(pageData,queryparam);
			}
			String strjson = JsonUtils.encodeData2Json(pageData,total);
			if(logger.isDebugEnabled()){
				logger.info(strjson);
			}
			return (strjson);
		} catch (Exception ex) {
			//System.out.println("wo shi shui *** wrong");
			logger.error(ex);
			throw new AppException(ex);
		}
	}
	/**
	 * 取List列表数据
	 * @param statementName
	 * @param paramMap
	 * @return
	 * @throws AppException
	 */
	public List getListData(String statementName, Map<String,String> paramMap)throws AppException {
		System.out.println("-----------"+statementName);
		System.out.println(paramMap.get("userName"));
		try {
			return batisDao.getDataByObject(statementName, paramMap);
		} catch (Exception ex) {
			logger.error(ex);
			throw new AppException(ex);
		}
	}
/*	
	public List getListData(String statementName, String paramMap)throws AppException {
		try {
			return batisDao.getDataByObject(statementName, paramMap);
		} catch (Exception ex) {
			logger.error(ex);
			throw new AppException(ex);
		}
	}
*/	/*
    /**
     * 保存Map值到数据
     */
	public int saveForMap(String statementName,Map<String,String> formdata)throws AppException {
		try {
			return batisDao.update(statementName, formdata);
		} catch (Exception ex) {
			logger.error(ex);
			throw new AppException(ex);
		}
		
	}
	/**
	 * 批量删除
	 * @param statementName
	 * @param fordata
	 * @throws AppException
	 */
	public void batchDelExecute(String statementName,List<Map> fordata)throws AppException{
		try {
			 batisDao.batchDelExecute(statementName, fordata);
		} catch (Exception ex) {
			logger.error(ex);
			throw new AppException(ex);
		}
	}
	/**
	 * 批量执行SQL
	 * @param sqlName
	 * @param datas
	 * @throws AppException
	 */
  public void batchExecutor(final String sqlName,final  List<Map> datas)throws AppException{
	  try {
		  batisDao.batchExecutor(sqlName, datas);
		  
			// batisDao.batchDelExecute(statementName, fordata);
		} catch (Exception ex) {
			logger.error(ex);
			throw new AppException(ex);
		}
  }
	/**
	 * 保存或更新数据
	 * @param issaveorupdatesql
	 * @param updatestatementName
	 * @param statementName
	 * @param datas
	 * @throws AppException
	 */
   public void batchSaveORUpdate(final String issaveorupdatesql,final String updatestatementName,final String statementName,final List<Map> datas)throws AppException{
	   try {
		   batisDao.ExecuteSql( new SqlMapClientCallback(){
			   public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				   executor.startBatch();
					 for(Map info : datas ){
					     int count = batisDao.getIntByObject(issaveorupdatesql, info);
					   
					     if(count<=0){
					    	 executor.update(statementName,info);
					     }else{
					    	 executor.update(updatestatementName,info); 
					     }
						 
					 }
			  		executor.executeBatch();
			  		 return null;
			  	 }  
		   });
			// batisDao.batchDelExecute(statementName, fordata);
		} catch (Exception ex) {
			logger.error(ex);
			throw new AppException(ex);
		}
   }
   
   
   
   /**
    * 执行存储工具
    * @param sqlProcedureId
    * @throws AppException
    */
   public void excesimpleProcedure(String sqlProcedureId) throws AppException {
		logger.info("procedure start");
		try {
			logger.info("procedure proceing");
			batisDao.simpleProcedure(sqlProcedureId);
			logger.info("procedure end");
		} catch (Exception ex) {
			logger.error(ex);
			throw new AppException(ex);
		}
	}

	public IDAO getBatisDao() {
		return batisDao;
	}

	public void setBatisDao(IDAO batisDao) {
		this.batisDao = batisDao;
	}

}
