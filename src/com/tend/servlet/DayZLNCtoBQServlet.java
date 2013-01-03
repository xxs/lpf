package com.tend.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * NCtoBQ增量数据抽取
 * 
 * @author xxs
 * 
 */
public class DayZLNCtoBQServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	//BaseDao baseDao ;
	private DayZLNCtoBQSenderBySoSale soSale;
	private DayZLNCtoBQSenderBySoSaleorderB soSaleorderB;
	private DayZLNCtoBQSenderBySoApply soApply;
	private DayZLNCtoBQSenderBySoApplyB soApplyB;
	private DayZLNCtoBQSenderBySoPreorder soPreorder;
	private DayZLNCtoBQSenderBySoPreorderB soPreorderB;
	private DayZLNCtoBQSenderBySoSaleinvoice soSaleinvoice;
	private DayZLNCtoBQSenderBySoSaleinvoiceB soSaleinvoiceB;
	private DayZLNCtoBQSenderByIcGeneralB icGeneralB;
	private DayZLNCtoBQSenderByIcGeneralH icGeneralH;
	private DayZLNCtoBQSenderByIcWastagebill wastagebill;
	private DayZLNCtoBQSenderByIcWastagebillB wastagebillB;
	private Thread TsoSale;
	private Thread TsoSaleorderB;
	private Thread TsoApply;
	private Thread TsoApplyB;
	private Thread TsoPreorder;
	private Thread TsoPreorderB;
	private Thread TsoSaleinvoice;
	private Thread TsoSaleinvoiceB;
	private Thread TicGeneralB;
	private Thread TicGeneralH;
	private Thread Twastagebill;
	private Thread TwastagebillB;

	public DayZLNCtoBQServlet() {
	}

	public void destroy() {
		// sender.stop();
		try {
			TsoSale.join(1000L);
			TsoSaleorderB.join(1000L);
			TsoApply.join(1000L);
			TsoApplyB.join(1000L);
			TsoPreorder.join(1000L);
			TsoPreorderB.join(1000L);
			TsoSaleinvoice.join(1000L);
			TsoSaleinvoiceB.join(1000L);
			TicGeneralB.join(1000L);
			TicGeneralH.join(1000L);
			Twastagebill.join(1000L);
			TwastagebillB.join(1000L);
			if (TsoSale.isAlive())
				System.out.println("抽取订单主表增量NC数据到BQ数据仓库的线程未停止。");
			if (TsoSaleorderB.isAlive())
				System.out.println("抽取订单辅表增量NC数据到BQ数据仓库的线程未停止。");
			if (TsoApply.isAlive())
				System.out.println("抽取退货申请单主表增量NC数据到BQ数据仓库的线程未停止。");
			if (TsoApplyB.isAlive())
				System.out.println("抽取退货申请单辅表增量NC数据到BQ数据仓库的线程未停止。");
			if (TsoPreorder.isAlive())
				System.out.println("抽取预订单主表增量NC数据到BQ数据仓库的线程未停止。");
			if (TsoPreorderB.isAlive())
				System.out.println("抽取预订单辅表增量NC数据到BQ数据仓库的线程未停止。");
			if (TsoSaleinvoice.isAlive())
				System.out.println("抽取发票主表增量NC数据到BQ数据仓库的线程未停止。");
			if (TsoSaleinvoiceB.isAlive())
				System.out.println("抽取发票辅表增量NC数据到BQ数据仓库的线程未停止。");
			if (TicGeneralB.isAlive())
				System.out.println("抽取出库单主表增量NC数据到BQ数据仓库的线程未停止。");
			if (TicGeneralH.isAlive())
				System.out.println("抽取出库单辅表增量NC数据到BQ数据仓库的线程未停止。");
			if (Twastagebill.isAlive())
				System.out.println("抽取途损单主表增量NC数据到BQ数据仓库的线程未停止。");
			if (TwastagebillB.isAlive())
				System.out.println("抽取途损单辅表增量NC数据到BQ数据仓库的线程未停止。");
		} catch (Exception e) {
		}
	}

	public void init() throws ServletException {
		soSale = new DayZLNCtoBQSenderBySoSale();
		soSaleorderB = new DayZLNCtoBQSenderBySoSaleorderB();
		soApply = new DayZLNCtoBQSenderBySoApply();
		soApplyB = new DayZLNCtoBQSenderBySoApplyB();
		soPreorder = new DayZLNCtoBQSenderBySoPreorder();
		soPreorderB = new DayZLNCtoBQSenderBySoPreorderB();
		soSaleinvoice = new DayZLNCtoBQSenderBySoSaleinvoice();
		soSaleinvoiceB = new DayZLNCtoBQSenderBySoSaleinvoiceB();
		icGeneralB = new DayZLNCtoBQSenderByIcGeneralB();
		icGeneralH = new DayZLNCtoBQSenderByIcGeneralH();
		wastagebill = new DayZLNCtoBQSenderByIcWastagebill();
		wastagebillB = new DayZLNCtoBQSenderByIcWastagebillB();
		System.out.println("程序初始化！");
		/*
		ServletConfig config = getServletConfig();
		System.out.println(config.getInitParameter("nexttime"));
		System.out.println(config.getInitParameter("beforedays"));
		System.out.println(config.getInitParameter("days"));
		if (config.getInitParameter("nexttime") != null
				&& config.getInitParameter("beforedays") != null
				&& config.getInitParameter("days") != null) {
			baseDao = new BaseDao(Integer.parseInt(config
					.getInitParameter("nexttime")), Integer.parseInt(config
					.getInitParameter("beforedays")), Integer.parseInt(config
					.getInitParameter("days")));
		}
		*/
		System.out.println("抽取增量NC数据到BQ数据仓库启动线程！");

		TsoSale = new Thread(soSale);
		TsoSaleorderB = new Thread(soSaleorderB);
		TsoApply = new Thread(soApply);
		TsoApplyB = new Thread(soApplyB);
		TsoPreorder = new Thread(soPreorder);
		TsoPreorderB = new Thread(soPreorderB);
		TsoSaleinvoice = new Thread(soSaleinvoice);
		TsoSaleinvoiceB = new Thread(soSaleinvoiceB);
		TicGeneralB = new Thread(icGeneralB);
		TicGeneralH = new Thread(icGeneralH);
		Twastagebill = new Thread(wastagebill);
		TwastagebillB = new Thread(wastagebillB);

		TsoSale.start();
		TsoSaleorderB.start();
		TsoApply.start();
		TsoApplyB.start();
		TsoPreorder.start();
		TsoPreorderB.start();
		TsoSaleinvoice.start();
		TsoSaleinvoiceB.start();
		TicGeneralB.start();
		TicGeneralH.start();
		Twastagebill.start();
		TwastagebillB.start();
		System.out.println("抽取增量NC数据到BQ数据仓库启动线程成功！");
	}
}
