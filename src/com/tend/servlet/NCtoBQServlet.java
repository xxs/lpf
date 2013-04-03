package com.tend.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class NCtoBQServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
//	private NCtoBQSenderByArapDjzb senderByArapDjzb;
	private NCtoBQSenderByArapDjfb senderByArapDjfb;
//	private NCtoBQSenderByIcGeneralH senderByIcGeneralH;
//	private NCtoBQSenderByIcGeneralB senderByIcGeneralB;
//	private NCtoBQSenderByIcWastagebill senderByIcWastagebill;
//	private NCtoBQSenderByIcWastagebillB senderByIcWastagebillB;
//	private NCtoBQSenderBySoApply senderBySoApply;
//	private NCtoBQSenderBySoApplyB senderBySoApplyB; 
//	private NCtoBQSenderBySoPreorder senderBySoPreorder;
//	private NCtoBQSenderBySoPreorderB senderBySoPreorderB;
//	private NCtoBQSenderBySoSale senderBySosale;
//	private NCtoBQSenderBySoSaleorderB senderBySoSaleorderB;
//	private NCtoBQSenderBySoSaleinvoice senderBySoSaleinvoice;
//	private NCtoBQSenderBySoSaleinvoiceB senderBySoSaleinvoiceB;
//	private Thread TArapDjzb;
	private Thread TArapDjfb;
//	private Thread TIcGeneralH;
//	private Thread TIcGeneralB;
//	private Thread TIcWastagebill;
//	private Thread TIcWastagebillB;
//	private Thread TSoApply;
//	private Thread TSoApplyB;
//	private Thread TSoPreorder;
//	private Thread TSoPreorderB;
//	private Thread TSosale;
//	private Thread TSoSaleorderB;
//	private Thread TSoSaleinvoice; 
//	private Thread TSoSaleinvoiceB;
	public NCtoBQServlet() {
	}

	public void destroy() {
		// sender.stop();
		try {
//			TArapDjzb.join(1000L);
			TArapDjfb.join(1000L);
//			TIcGeneralH.join(1000L);
//			TIcGeneralB.join(1000L);
//			TIcWastagebill.join(1000L);
//			TIcWastagebillB.join(1000L);
//			TSoApply.join(1000L);
//			TSoApplyB.join(1000L);
//			TSoPreorder.join(1000L);
//			TSoPreorderB.join(1000L);
//			TSosale.join(1000L);
//			TSoSaleorderB.join(1000L);
//			TSoSaleinvoice.join(1000L);
//			TSoSaleinvoiceB.join(1000L);
//			if (TArapDjzb.isAlive())
//				System.out.println("TArapDjzb抽取NC数据到BQ数据仓库的线程未停止。");
			if (TArapDjfb.isAlive())
				System.out.println("TArapDjfb抽取NC数据到BQ数据仓库的线程未停止。");
//			if (TIcGeneralH.isAlive())
//				System.out.println("TIcGeneralH抽取NC数据到BQ数据仓库的线程未停止。");
//			if (TIcGeneralB.isAlive())
//				System.out.println("TIcGeneralB抽取NC数据到BQ数据仓库的线程未停止。");
//			if (TIcWastagebill.isAlive())
//				System.out.println("TIcWastagebill抽取NC数据到BQ数据仓库的线程未停止。");
//			if (TIcWastagebillB.isAlive())
//				System.out.println("TIcWastagebillB抽取NC数据到BQ数据仓库的线程未停止。");
//			if (TSoApply.isAlive())
//				System.out.println("TSoApply抽取NC数据到BQ数据仓库的线程未停止。");
//			if (TSoApplyB.isAlive())
//				System.out.println("TSoApplyB抽取NC数据到BQ数据仓库的线程未停止。");
//			if (TSoPreorder.isAlive())
//				System.out.println("TSoPreorder抽取NC数据到BQ数据仓库的线程未停止。");
//			if (TSoPreorderB.isAlive())
//				System.out.println("TSoPreorderB抽取NC数据到BQ数据仓库的线程未停止。");
//			if (TSosale.isAlive())
//				System.out.println("TSosale抽取NC数据到BQ数据仓库的线程未停止。");
//			if (TSoSaleorderB.isAlive())
//				System.out.println("TSoSaleorderB抽取NC数据到BQ数据仓库的线程未停止。");
//			if (TSoSaleinvoice.isAlive())
//				System.out.println("TSoSaleinvoice抽取NC数据到BQ数据仓库的线程未停止。");
//			if (TSoSaleinvoiceB.isAlive())
//				System.out.println("TSoSaleinvoiceB抽取NC数据到BQ数据仓库的线程未停止。");
		} catch (Exception e) {
		}
	}

	public void init() throws ServletException {
//		senderByArapDjzb = new NCtoBQSenderByArapDjzb();
		senderByArapDjfb = new NCtoBQSenderByArapDjfb();
//		senderByIcGeneralH = new NCtoBQSenderByIcGeneralH();
//		senderByIcGeneralB = new NCtoBQSenderByIcGeneralB();
//		senderByIcWastagebill = new NCtoBQSenderByIcWastagebill();
//		senderByIcWastagebillB = new NCtoBQSenderByIcWastagebillB();
//		senderBySoApply = new NCtoBQSenderBySoApply();
//		senderBySoApplyB = new NCtoBQSenderBySoApplyB();
//		senderBySoPreorder = new NCtoBQSenderBySoPreorder();
//		senderBySoPreorderB = new NCtoBQSenderBySoPreorderB();
//		senderBySosale = new NCtoBQSenderBySoSale();
//		senderBySoSaleorderB = new NCtoBQSenderBySoSaleorderB();
//		senderBySoSaleinvoice = new NCtoBQSenderBySoSaleinvoice();
//		senderBySoSaleinvoiceB = new NCtoBQSenderBySoSaleinvoiceB();
		
		System.out.println("程序初始化！");
		System.out.println("抽取NC数据到BQ数据仓库启动线程！");
		
//		TArapDjzb = new Thread(senderByArapDjzb);
		TArapDjfb = new Thread(senderByArapDjfb);
//		TIcGeneralH = new Thread(senderByIcGeneralH);
//		TIcGeneralB = new Thread(senderByIcGeneralB);
//		TIcWastagebill = new Thread(senderByIcWastagebill);
//		TIcWastagebillB = new Thread(senderByIcWastagebillB);
//		TSoApply = new Thread(senderBySoApply);
//		TSoApplyB = new Thread(senderBySoApplyB);
//		TSoPreorder = new Thread(senderBySoPreorder);
//		TSoPreorderB = new Thread(senderBySoPreorderB);
//		TSosale = new Thread(senderBySosale);
//		TSoSaleorderB = new Thread(senderBySoSaleorderB);
//		TSoSaleinvoice = new Thread(senderBySoSaleinvoice);
//		TSoSaleinvoiceB = new Thread(senderBySoSaleinvoiceB);
		
//		TArapDjzb.start();
		TArapDjfb.start();
//		TIcGeneralH.start();
//		TIcGeneralB.start();
//		TIcWastagebill.start();
//		TIcWastagebillB.start();
//		TSoApply.start();
//		TSoApplyB.start();
//		TSoPreorder.start();
//		TSoPreorderB.start();
//		TSosale.start();
//		TSoSaleorderB.start();
//		TSoSaleinvoice.start();
//		TSoSaleinvoiceB.start();
		System.out.println("抽取NC数据到BQ数据仓库启动线程成功！");
	}
}
