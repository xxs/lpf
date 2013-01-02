package com.synear.sale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class ABCBankServlet extends HttpServlet {
	 private ABCBankThread sender;
	 private Thread t;
	public void destroy()
	 {
	     sender.stop();
	     try
	     {
	         t.join(60000L);
	         if(t.isAlive())
	             System.out.println("农行线程未停止。");
	     }
	     catch(Exception e) { }
	 }

	public void init()
	     throws ServletException
	 {
	     System.out.println("农行程序初始化！");
	     sender = new ABCBankThread();
	     System.out.println("农行程序初始化！");
	     String tonc=getInitParameter("abctonc");
	     if(tonc != null)
	         sender.setToncurl(tonc);
	     t = new Thread(sender);
	     t.start();
	     System.out.println("农行启动线程成功！");
	 }
}
