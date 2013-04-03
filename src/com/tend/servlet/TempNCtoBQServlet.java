package com.tend.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * NCtoBQ增量数据抽取
 * 
 * @author xxs
 * 
 */
public class TempNCtoBQServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private TempNCtoBQSenderByArapDjzb arapDjzb;
	private TempNCtoBQSenderByArapDjfb arapDjfb;
	private Thread TarapDjzb;
	private Thread TarapDjfb;
	
	public TempNCtoBQServlet() {
	}

	public void destroy() {
		// sender.stop();
		try {
			TarapDjzb.join(1000L);
			TarapDjfb.join(1000L);
			if (TarapDjzb.isAlive())
				System.out.println("抽取单据主表增量NC数据到BQ数据仓库的线程未停止。");
			if (TarapDjfb.isAlive())
				System.out.println("抽取单据辅表增量NC数据到BQ数据仓库的线程未停止。");
		} catch (Exception e) {
		}
	}

	public void init() throws ServletException {
		arapDjzb = new TempNCtoBQSenderByArapDjzb();
		arapDjfb = new TempNCtoBQSenderByArapDjfb();
		System.out.println("程序初始化！");
		TarapDjzb = new Thread(arapDjzb); 
		TarapDjfb = new Thread(arapDjfb);
		TarapDjzb.start();
		TarapDjfb.start();
	}
}
