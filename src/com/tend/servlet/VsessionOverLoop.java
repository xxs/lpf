package com.tend.servlet;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;


public class VsessionOverLoop implements Runnable {

	static Connection conn = null;
	static CallableStatement cs = null;
	
	public VsessionOverLoop() {
		System.out.println("视频会议自动结束轮询");
	}

	/**
	 * 自动执行的run方法
	 */
	public void run() {
		System.out.println("执行了轮询");
		Free();
	}
	/**
	 * 自动释放视频账号方法
	 */	
	public static void Free(){
		conn = DBOracleconn.getDBConn();
		try {
			cs = conn.prepareCall("{call xx_autoovervsession_proc}");
			cs.execute();
			System.out.println("存储过程运行成功");
		} catch (SQLException e) {
			System.out.println("程序出现异常，异常信息如下：");
			System.out.println(e.getMessage());
		}
	}
}
