package com.nc.dao.ibatis;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;

public class BatisDaoImpl extends SqlMapClientDaoSupport implements IDAO {
	
	public List getDataByObject(String statementName,Object parameterObject)  throws SQLException{

		return this.getSqlMapClientTemplate().queryForList(statementName, parameterObject);
	}
	
	public Object getObjByObject(String statementName,Object parameterObject,Object retulsObject) throws SQLException{
		if(retulsObject == null){
		   return this.getSqlMapClientTemplate().queryForObject(statementName, parameterObject);
		}else{
			return this.getSqlMapClientTemplate().queryForObject(statementName, parameterObject,retulsObject);	
		}
	}
	
	public int getIntByObject(String statementName,Object parameterObject) throws SQLException{
		 Integer num = new Integer(0);
		 num = (Integer)this.getSqlMapClientTemplate().queryForObject(statementName,parameterObject,num);
		 if(num==null) return 0;
		 return num.intValue();
	}
	
	public int update(String statementName,Map<String,String> infos)throws SQLException{
	   return this.getSqlMapClientTemplate().update(statementName, infos);
	}
	
	public int update(String statementName,Object infos)throws SQLException{
		
		  return this.getSqlMapClientTemplate().update(statementName, infos);
	}
	

	
	public void batchDelExecute(final String statementName,final List<Map> infos){
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
	  	 public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
			 executor.startBatch();
			 for(Map info : infos ){
				 executor.delete(statementName,info);
			 }
	  		 executor.executeBatch();
	  		 return null;
	  	 }
	  });
	}
	 public Object ExecuteSql( SqlMapClientCallback callback){
		return this.getSqlMapClientTemplate().execute(callback);
	 }
	 
	 public void simpleProcedure(final String SqlProcedure)throws SQLException{
		 this.getSqlMapClientTemplate().execute(new SqlMapClientCallback(){
			  public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				  executor.update(SqlProcedure, null);
				  return null;
			  }
			 
		 });
	 }
	 
	 public void batchExecutor(final String sqlName,final  List<Map> datas)throws SQLException{
		 ExecuteSql( new SqlMapClientCallback(){
			   public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				   executor.startBatch();
					 for(Map<String,String> info : datas ){
						 executor.update(sqlName, info);
					 }
			  		 executor.executeBatch();
			  		 return null;
			  	 }  
		   });
	 }

}
