package com.nc.dao.ibatis;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;

public interface IDAO {
	
	List getDataByObject(String statementName,Object obj) throws SQLException;
	
	int getIntByObject(String statementName,Object obj) throws SQLException;
	
	public Object getObjByObject(String statementName,Object parameterObject,Object retulsObject) throws SQLException;
	
	public int update(String statementName,Map<String,String> infos)throws SQLException;
	
	public int update(String statementName,Object infos)throws SQLException;
	
	public void simpleProcedure(final String SqlProcedure)throws SQLException;
	
	public void batchDelExecute(final String statementName,final List<Map> infos)throws SQLException;
	
    public Object ExecuteSql(SqlMapClientCallback callback)throws SQLException;
    
    public void batchExecutor(final String sqlName,final  List<Map> datas)throws SQLException;

}
