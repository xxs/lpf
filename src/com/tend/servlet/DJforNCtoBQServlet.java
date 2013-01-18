package com.tend.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
/**
 * NCtoBQ
 * @author xxs
 *
 */
public class DJforNCtoBQServlet extends HttpServlet {

	
	private static final long serialVersionUID = -4002071122199170800L;
	
	private DjzbNCtoBQSender djzbNCtoBQSender;
	private DjfbNCtoBQSender djfbNCtoBQSender;
	private Thread Tdjzb;
	private Thread Tdjfb;
	public DJforNCtoBQServlet() {
	}

	public void destroy() {
		// sender.stop();
		try {
			Tdjzb.join(1000L);
			Tdjfb.join(1000L);
			if (Tdjzb.isAlive())
				System.out.println("抽取单据主表NC数据到BQ数据仓库的线程未停止。");
			if (Tdjfb.isAlive())
				System.out.println("抽取单据辅表NC数据到BQ数据仓库的线程未停止。");
		} catch (Exception e) {
		}
	}

	public void init() throws ServletException {
		djzbNCtoBQSender = new DjzbNCtoBQSender();
		djfbNCtoBQSender = new DjfbNCtoBQSender();
		System.out.println("程序初始化！");
		System.out.println("抽取NC数据到BQ数据仓库启动线程！");
		Tdjzb = new Thread(djzbNCtoBQSender);
		Tdjfb = new Thread(djfbNCtoBQSender);
		Tdjzb.start();
		Tdjfb.start(); 
		System.out.println("抽取NC数据到BQ数据仓库启动线程成功！");
	}
}
