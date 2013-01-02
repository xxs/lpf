package com.tend.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class AutoRequestHttpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private AutoRequestHttpSender sender;
	private AutoKPIHistorySave senderkpi;
	private AutoWriteOrderSender senderMakeOrder;
	private Thread t1;//定时同步基础数据的线程
	private Thread t2;//定时传送KPI历史销售的线程
	private Thread t3;//定时生成订单，返回订单状态的线程
	
	public AutoRequestHttpServlet() {
	}

	public void destroy() {
		// sender.stop();
		try {
			t1.join(1000L);
			t2.join(1000L);
			t3.join(1000L);
			if (t1.isAlive())
				System.out.println("t1线程未停止。");
			if (t2.isAlive())
				System.out.println("t2线程未停止。");
			if (t3.isAlive())
				System.out.println("t3线程未停止。");
		} catch (Exception e) {
		}
	}

	public void init() throws ServletException {
		sender = new AutoRequestHttpSender();
		senderkpi = new AutoKPIHistorySave();
		senderMakeOrder = new AutoWriteOrderSender();
		
		System.out.println("程序初始化！");

		ServletConfig config = getServletConfig();
		System.out.println(config.getInitParameter("username"));
		System.out.println(config.getInitParameter("pwd"));
		System.out.println(config.getInitParameter("time"));
		System.out.println(config.getInitParameter("kpitime"));
		System.out.println(config.getInitParameter("loadurl"));
		System.out.println(config.getInitParameter("requesturl"));
		System.out.println(config.getInitParameter("requesttime"));
		System.out.println(config.getInitParameter("ncserver"));
		if (config.getInitParameter("username") != null) {
			sender.setUsername(config.getInitParameter("username"));
			senderkpi.setUsername(config.getInitParameter("username"));
			senderMakeOrder.setUsername(config.getInitParameter("username"));
			
		}
		if (config.getInitParameter("pwd") != null) {
			sender.setPwd(config.getInitParameter("pwd"));
			senderkpi.setPwd(config.getInitParameter("pwd"));
			senderMakeOrder.setPwd(config.getInitParameter("pwd"));
		}
		if (config.getInitParameter("time") != null) {
			sender.setTime(Integer.parseInt(config.getInitParameter("time")));
		}
		if (config.getInitParameter("kpitime") != null) {
			senderkpi.setKpitime(Integer.parseInt(config.getInitParameter("kpitime")));
		}
		if (config.getInitParameter("monthnum") != null) {
			senderkpi.setMonthnum(Integer.parseInt(config.getInitParameter("monthnum")));
		}
		if (config.getInitParameter("loadurl") != null) {
			sender.setLoadurl(config.getInitParameter("loadurl"));
			senderkpi.setLoadurl(config.getInitParameter("loadurl"));
			senderMakeOrder.setLoadurl(config.getInitParameter("loadurl"));
		}
		if (config.getInitParameter("requesturl") != null) {
			senderMakeOrder.setRequesturl(config.getInitParameter("requesturl"));
		}
		if (config.getInitParameter("requesttime") != null) {
			senderMakeOrder.setRequesttime(Integer.parseInt(config.getInitParameter("requesttime")));
		}
		if (config.getInitParameter("ncserver") != null) {
			senderMakeOrder.setNcserver(config.getInitParameter("ncserver"));
		}
		System.out.println("移动商务数据检测启动线程！");
		t1 = new Thread(sender);
		t1.start();
		t2 = new Thread(senderkpi);
		t2.start();
		t3 = new Thread(senderMakeOrder);
		t3.start();
		System.out.println("移动商务数据检测启动线程成功！");
	}
}
