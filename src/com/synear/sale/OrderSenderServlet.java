package com.synear.sale;

//Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
//Decompiler options: packimports(3) fieldsfirst ansi 
//Source File Name:   OrderSenderServlet.java
import java.io.File;
import java.io.PrintStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

//Referenced classes of package com.sunny.sms:
//         OrderSender

public class OrderSenderServlet extends HttpServlet {

	private File root;
	private OrderSender sender;
	private Thread t;

	public OrderSenderServlet() {
		root = null;
	}

	public void destroy() {
		sender.stop();
		try {
			t.join(1000L);
			if (t.isAlive())
				System.out.println("线程未停止。");
		} catch (Exception e) {
		}
	}

	public void init() throws ServletException {
		System.out.println("程序初始化开会！");
		sender = new OrderSender();
		/*
		 * String server = getInitParameter("server"); String username =
		 * getInitParameter("username"); String password =
		 * getInitParameter("password"); if(server == null || username == null
		 * || password == null) { System.out.println("系统文件web.xml错误：程序初始化失败！");
		 * return; } sender.setServer(server); sender.setUsername(username);
		 * sender.setPassword(password);
		 */
		System.out.println("程序初始化！");

		String tonc = getInitParameter("tonc");
		if (tonc != null)
			sender.setToncurl(tonc);
		String time = getInitParameter("time");
		if (time != null)
			sender.setTime(Integer.parseInt(time));
		String validate = getInitParameter("validate");
		System.out.println(validate);
		if (validate != null)
			sender.setValidate(Boolean.valueOf(validate).booleanValue());
		System.out.println("启动线程！");
		t = new Thread(sender);
		t.start();
		System.out.println("启动线程成功！");
	}
}
