package com.tend.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class BaseDao {
	//public Integer nexttime = 120000;

	//数据跨时间段抽取需要的参数
	CommonParam commonParam = new CommonParam();
	String billdate = commonParam.getString("billdate");
	String begindate = commonParam.getString("begindate");
	String enddate = commonParam.getString("enddate");
	Integer zday = Integer.parseInt(commonParam.getString("zday"));
	Integer fday = Integer.parseInt(commonParam.getString("fday"));
	
	//每天抽取增量需要的参数
	Integer days = Integer.parseInt(commonParam.getString("days"));  
	Integer beforedays = Integer.parseInt(commonParam.getString("beforedays"));  
	//数据初始抽取需要的参数
	String nctobq_begindate = commonParam.getString("nctobq_begindate");  
	String nctobq_enddate = commonParam.getString("nctobq_enddate");  
	Integer nctobq_zday = Integer.parseInt(commonParam.getString("nctobq_zday"));  
	Integer nctobq_fday = Integer.parseInt(commonParam.getString("nctobq_fday"));  

	public BaseDao() {
		super();
	}

	public BaseDao(Integer n,Integer d,Integer b ) {
		super();
		System.out.println("-------------加载配置参数  begin-------------------");
		System.out.println("单据日期参数值 ："+billdate);
		System.out.println("TS开始日期参数值 ："+begindate);
		System.out.println("TS结束日期参数值 ："+enddate);
		System.out.println("主表天数周期参数值 ："+zday);
		System.out.println("辅表天数周期参数值 ："+fday);
		System.out.println("days参数值 ："+days);
		System.out.println("beforedays参数值 ："+beforedays);
		System.out.println("-------------加载配置参数  end-------------------");
		//nexttime = n;
		days = d;
		beforedays = b;

	}

//	public Integer getNexttime() {
//		return nexttime;
//	}

	public Integer getDays() {
		return days;
	}

	public CommonParam getCommonParam() {
		return commonParam;
	}

	public String getBilldate() {
		return billdate;
	}

	public String getBegindate() {
		return begindate;
	}

	public String getEnddate() {
		return enddate;
	}

	public Integer getZday() {
		return zday;
	}

	public Integer getNctobq_zday() {
		return nctobq_zday;
	}

	public Integer getNctobq_fday() {
		return nctobq_fday;
	}

	public Integer getFday() {
		return fday;
	}

	public Integer getBeforedays() {
		return beforedays;
	}

	public String getNctobq_begindate() {
		return nctobq_begindate;
	}

	public String getNctobq_enddate() {
		return nctobq_enddate;
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
	//获取NC测试数据库连接
	public Connection getConForNCTest(){
		System.out.println("启动测试库连接");
		Connection con = null;
		String dirver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@100.100.1.71:1521:nctp";
		try{
			Class.forName(dirver);
			con = DriverManager.getConnection(url,"snzs02","snzs02");
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	//获取BQ数据库连接
	public Connection getConForBQ(){
		Connection con = null;
		String dirver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@100.100.1.27:1521:bidb";
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