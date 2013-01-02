package com.nc.dao.ibatis;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.nc.bean.UserFunctionBean;

public class TestDao extends SqlMapClientDaoSupport {
 
	public void test(String statementName,Map parameterObject){
		Map map = this.getSqlMapClientTemplate().queryForMap(statementName, parameterObject, "pk", "value");
		System.out.println(map);
	}
	
	
	public  static void  main(String[] arg ){
		UserFunctionBean bean = new UserFunctionBean();
		bean.setFunctionId("M0005");
		UserFunctionBean bean2 = new UserFunctionBean();
		bean2.setFunctionId("M0005");
		Set set  = new HashSet();
		set.add(bean2);
		System.out.println(bean2.hashCode());
		System.out.println(bean.hashCode());
		System.out.println(bean.equals(bean2));
		System.out.println(set.contains(bean));
		System.out.println(bean == bean2);
		
	}

}
