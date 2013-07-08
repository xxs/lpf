package com.tend.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
/**
 * NCtoBQ增量数据抽取
 * @author xxs
 *
 */
public class ZLNCtoBQServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ZLNCtoBQSenderByArapDjzb arapDjzb;
	private ZLNCtoBQSenderByArapDjfb arapDjfb;
	private ZLNCtoBQSenderBySoSale soSale;
	private ZLNCtoBQSenderBySoSaleorderB soSaleorderB; 
	private ZLNCtoBQSenderBySoApply soApply;
	private ZLNCtoBQSenderBySoApplyB soApplyB;
	private ZLNCtoBQSenderBySoPreorder soPreorder;
	private ZLNCtoBQSenderBySoPreorderB soPreorderB;
	private ZLNCtoBQSenderBySoSaleinvoice soSaleinvoice;
	private ZLNCtoBQSenderBySoSaleinvoiceB soSaleinvoiceB;
	private ZLNCtoBQSenderByIcGeneralB icGeneralB;
	private ZLNCtoBQSenderByIcGeneralH icGeneralH;
	private ZLNCtoBQSenderByIcWastagebill wastagebill;
	private ZLNCtoBQSenderByIcWastagebillB wastagebillB;
	private Thread TarapDjzb;
	private Thread TarapDjfb;
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
	public ZLNCtoBQServlet() {
	}

	public void destroy() {
		// sender.stop();
		try {
			TarapDjzb.join(1000L);
			TarapDjfb.join(1000L);
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
			if (TarapDjzb.isAlive())
				System.out.println("抽取单据主表增量NC数据到BQ数据仓库的线程未停止。");
			if (TarapDjfb.isAlive())
				System.out.println("抽取单据辅表增量NC数据到BQ数据仓库的线程未停止。");
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
		arapDjzb = new ZLNCtoBQSenderByArapDjzb();
		arapDjfb = new ZLNCtoBQSenderByArapDjfb();
		soSale = new ZLNCtoBQSenderBySoSale();
		soSaleorderB = new ZLNCtoBQSenderBySoSaleorderB();
		soApply = new ZLNCtoBQSenderBySoApply();
		soApplyB = new ZLNCtoBQSenderBySoApplyB();
		soPreorder = new ZLNCtoBQSenderBySoPreorder();
		soPreorderB = new ZLNCtoBQSenderBySoPreorderB();
		soSaleinvoice = new ZLNCtoBQSenderBySoSaleinvoice();
		soSaleinvoiceB = new ZLNCtoBQSenderBySoSaleinvoiceB();
		icGeneralB = new ZLNCtoBQSenderByIcGeneralB();
		icGeneralH = new ZLNCtoBQSenderByIcGeneralH();
		wastagebill = new ZLNCtoBQSenderByIcWastagebill();
		wastagebillB = new ZLNCtoBQSenderByIcWastagebillB();
		System.out.println("程序初始化！");
		System.out.println("抽取增量NC数据到BQ数据仓库启动线程！");
		
		TarapDjzb = new Thread(arapDjzb);
		TarapDjfb = new Thread(arapDjfb);
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
		
		CommonParam commonParam = new CommonParam();
		String billdate = commonParam.getString("billdate");
		String begindate = commonParam.getString("begindate");
		String enddate = commonParam.getString("enddate");
		Integer zday = Integer.parseInt(commonParam.getString("zday"));
		Integer fday = Integer.parseInt(commonParam.getString("fday"));
		String arap_djzb = commonParam.getString("zlnc_arap_djzb");
		String arap_djfb = commonParam.getString("zlnc_arap_djfb");
		String ic_general_h = commonParam.getString("zlnc_ic_general_h");
		String ic_general_b = commonParam.getString("zlnc_ic_general_b");
		String ic_wastagebill_h = commonParam.getString("zlnc_ic_wastagebill_h");
		String ic_wastagebill_b = commonParam.getString("zlnc_ic_wastagebill_b");
		String so_apply = commonParam.getString("zlnc_so_apply");
		String so_apply_b = commonParam.getString("zlnc_so_apply_b");
		String so_preorder_h = commonParam.getString("zlnc_so_preorder_h");
		String so_preorder_b = commonParam.getString("zlnc_so_preorder_b");
		String so_sale = commonParam.getString("zlnc_so_sale");
		String so_saleorder_b = commonParam.getString("zlnc_so_saleorder_b");
		String so_saleinvoice = commonParam.getString("zlnc_so_saleinvoice");
		String so_saleinvoice_b = commonParam.getString("zlnc_so_saleinvoice_b");
		
		System.out.println("-------------加载配置参数  begin-------------------");
		System.out.println("单据日期参数值 ："+billdate);
		System.out.println("TS开始日期参数值 ："+begindate);
		System.out.println("TS结束日期参数值 ："+enddate);
		System.out.println("主表天数周期参数值 ："+zday);
		System.out.println("辅表天数周期参数值 ："+fday);
		System.out.println("arap_djzb ："+arap_djzb);
		System.out.println("arap_djfb ："+arap_djfb);
		System.out.println("ic_general_h ："+ic_general_h);
		System.out.println("ic_general_b ："+ic_general_b);
		System.out.println("ic_wastagebill_h ："+ic_wastagebill_h);
		System.out.println("ic_wastagebill_b ："+ic_wastagebill_b);
		System.out.println("so_apply ："+so_apply);
		System.out.println("so_apply_b ："+so_apply_b);
		System.out.println("so_preorder_h ："+so_preorder_h);
		System.out.println("so_preorder_b ："+so_preorder_b);
		System.out.println("so_sale ："+so_sale);
		System.out.println("so_saleorder_b ："+so_saleorder_b);
		System.out.println("so_saleinvoice ："+so_saleinvoice);
		System.out.println("so_saleinvoice_b ："+so_saleinvoice_b);
		System.out.println("-------------加载配置参数  end-------------------");
		
		if("true".equals(arap_djzb)){
			TarapDjzb.start();
		}
		if("true".equals(arap_djfb)){
			TarapDjfb.start();
		}
		if("true".equals(so_apply)){
			TsoApply.start();
		}
		if("true".equals(so_apply_b)){
			TsoApplyB.start();
		}
		if("true".equals(so_preorder_h)){
			TsoPreorder.start();
		}
		if("true".equals(so_preorder_b)){
			TsoPreorderB.start();
		}
		if("true".equals(so_saleinvoice)){
			TsoSaleinvoice.start();
		}
		if("true".equals(so_saleinvoice_b)){
			TsoSaleinvoiceB.start();
		}
		if("true".equals(ic_general_b)){
			TicGeneralB.start();
		}
		if("true".equals(ic_general_h)){
			TicGeneralH.start();
		}
		if("true".equals(ic_wastagebill_h)){
			Twastagebill.start();
		}
		if("true".equals(ic_wastagebill_b)){
			TwastagebillB.start();
		}
		if("true".equals(so_sale)){
			TsoSale.start();
		}
		if("true".equals(so_saleorder_b)){
			TsoSaleorderB.start(); 
		}
		System.out.println("抽取增量NC数据到BQ数据仓库启动线程成功！");
	}
}
