package com.tend.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class BaseDao {
	private Integer nexttime;
	private Integer days;
	private Integer beforedays;
	
	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}


	public Integer getNexttime() {
		return nexttime;
	}

	public void setNexttime(Integer nexttime) {
		this.nexttime = nexttime;
	}

	public Integer getBeforedays() {
		return beforedays;
	}

	public void setBeforedays(Integer beforedays) {
		this.beforedays = beforedays;
	}
	//获取NC数据库连接
	public Connection getConForNC(){
		Connection con = null;
		String dirver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@100.100.1.11:1521:sndb";
		try{
			Class.forName(dirver);
			con = DriverManager.getConnection(url,"snzs","snzs");
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	//获取BQ数据库连接
	public Connection getConForBQ(){
		Connection con = null;
		String dirver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@100.100.1.172:1521:test";
		try{
			Class.forName(dirver);
			con = DriverManager.getConnection(url,"bius01","bius_123");
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	public static void closeAll(PreparedStatement pst,ResultSet rest,Connection con){
		try{
			if(pst!=null){
				pst.close();
			}
			if(rest!=null){
				rest.close();
			}
			if(con!=null){
				con.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public boolean excuteDelete(String sql){
		Connection conn = getConForBQ();
		PreparedStatement ps = null;
		boolean result = false;
		try {
			ps = conn.prepareStatement(sql);
			result = ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeAll(ps, null, conn);
		}
		return result;
	}
}
