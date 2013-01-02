package com.nc.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.nc.comm.AppException;
import com.nc.comm.JsonUtils;

public class TMDSqlService extends JdbcDaoSupport {
	private static final Log logger = LogFactory
			.getLog(ModuleTreeService.class);

	public String getListForPage(Map<String, String> queryparam)
			throws AppException {
		try {
			String statementTotalCount = null, statementName = null;
			String strfield = queryparam.get("field");
			String strgroup = queryparam.get("group");
			
			if(strfield==null||strfield.length()<0){
				return "";
			}
			String datasource = "datasource1";
	
			final String[] fields = strfield.split(",");
			String  sql = SBSqlFactory.MakeSql(fields,queryparam,strgroup);
			int total = 0;
			List param = new ArrayList() ;
			
			///
			param.add(new Integer(queryparam.get("start")));
			param.add(new Integer(queryparam.get("limit")));
			Object[] objArray2 = param.toArray(new Object[param.size()]);
	
			List listdata = this.getJdbcTemplate().query(sql,objArray2 ,new RowMapper() {
				public Object mapRow(java.sql.ResultSet rs, int num) {
					Map map = new HashMap();
					try {
						for (int i = 0; i < fields.length; i++) {
							int index = fields[i].indexOf(" ");
							String field = null;
							if(index!=-1){
								field = fields[i].substring(index,fields[i].length());
							}else{
								field = fields[i];
							}
							field = StringUtils.trim(field);
							map.put(field, rs.getString(field));
							
						}
						map.put("total", rs.getString("total")) ;
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					return map;
				}
			});
			if(listdata.size()>0){
				Map map = (Map)listdata.get(0);
				String strtotal = (String)map.get("total");
				total = Integer.parseInt(strtotal) ;
			}
		
			String strjson = JsonUtils.encodeData2Json(listdata, total);
			if (logger.isDebugEnabled()) {
				logger.info(strjson);
			}
			return (strjson);
		} catch (Exception ex) {
			logger.error(ex);
			throw new AppException(ex);
		}

	}

}
