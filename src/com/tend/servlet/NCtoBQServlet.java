package com.tend.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class NCtoBQServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
//	private NCtoBQSenderByIcGeneralH senderByIcGeneralH;
	private NCtoBQSenderByIcGeneralB senderByIcGeneralB;
//	private NCtoBQSenderBySoSale senderBySosale;
//	private NCtoBQSenderBySoSaleorderB senderBySoSaleorderB;
//	private NCtoBQSenderBySoSaleinvoice senderBySoSaleinvoice;
//	private NCtoBQSenderBySoSaleinvoiceB senderBySoSaleinvoiceB;
//	private Thread TIcGeneralH;
	private Thread TIcGeneralB;
//	private Thread TSosale;
//	private Thread TSoSaleorderB;
//	private Thread TSoSaleinvoice;
//	private Thread TSoSaleinvoiceB;
	public NCtoBQServlet() {
	}

	public void destroy() {
		// sender.stop();
		try {
//			TIcGeneralH.join(1000L);
			TIcGeneralB.join(1000L);
//			TSosale.join(1000L);
//			TSoSaleorderB.join(1000L);
//			TSoSaleinvoice.join(1000L);
//			TSoSaleinvoiceB.join(1000L);
//			if (TIcGeneralH.isAlive())
//				System.out.println("抽取NC数据到BQ数据仓库的线程未停止。");
			if (TIcGeneralB.isAlive())
				System.out.println("抽取NC数据到BQ数据仓库的线程未停止。");
//			if (TSosale.isAlive())
//				System.out.println("抽取NC数据到BQ数据仓库的线程未停止。");
//			if (TSoSaleorderB.isAlive())
//				System.out.println("抽取NC数据到BQ数据仓库的线程未停止。");
//			if (TSoSaleinvoice.isAlive())
//				System.out.println("抽取NC数据到BQ数据仓库的线程未停止。");
//			if (TSoSaleinvoiceB.isAlive())
//				System.out.println("抽取NC数据到BQ数据仓库的线程未停止。");
		} catch (Exception e) {
		}
	}

	public void init() throws ServletException {
//		senderByIcGeneralH = new NCtoBQSenderByIcGeneralH();
		senderByIcGeneralB = new NCtoBQSenderByIcGeneralB();

//		senderBySosale = new NCtoBQSenderBySoSale();
//		senderBySoSaleorderB = new NCtoBQSenderBySoSaleorderB();
//		senderBySoSaleinvoice = new NCtoBQSenderBySoSaleinvoice();
//		senderBySoSaleinvoiceB = new NCtoBQSenderBySoSaleinvoiceB();
		
		System.out.println("程序初始化！");
		System.out.println("抽取NC数据到BQ数据仓库启动线程！");
		
//		TIcGeneralH = new Thread(senderByIcGeneralH);
		TIcGeneralB = new Thread(senderByIcGeneralB);
//		TSosale = new Thread(senderBySosale);
//		TSoSaleorderB = new Thread(senderBySoSaleorderB);
//		TSoSaleinvoice = new Thread(senderBySoSaleinvoice);
//		TSoSaleinvoiceB = new Thread(senderBySoSaleinvoiceB);
//		TIcGeneralH.start();
		TIcGeneralB.start();
//		TSosale.start();
//		TSoSaleorderB.start();
//		TSoSaleinvoice.start();
//		TSoSaleinvoiceB.start();
		System.out.println("抽取NC数据到BQ数据仓库启动线程成功！");
	}
}
