package com.tend.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class NCtoBQServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private NCtoBQSenderByArapDjzb senderByArapDjzb;
	private NCtoBQSenderByArapDjfb senderByArapDjfb;
	private NCtoBQSenderByIcGeneralH senderByIcGeneralH;
	private NCtoBQSenderByIcGeneralB senderByIcGeneralB;
	private NCtoBQSenderByIcWastagebill senderByIcWastagebill;
	private NCtoBQSenderByIcWastagebillB senderByIcWastagebillB;
	private NCtoBQSenderBySoApply senderBySoApply;
	private NCtoBQSenderBySoApplyB senderBySoApplyB; 
	private NCtoBQSenderBySoPreorder senderBySoPreorder;
	private NCtoBQSenderBySoPreorderB senderBySoPreorderB;
	private NCtoBQSenderBySoSale senderBySosale;
	private NCtoBQSenderBySoSaleorderB senderBySoSaleorderB;
	private NCtoBQSenderBySoSaleinvoice senderBySoSaleinvoice;
	private NCtoBQSenderBySoSaleinvoiceB senderBySoSaleinvoiceB;
	private Thread TArapDjzb;
	private Thread TArapDjfb;
	private Thread TIcGeneralH;
	private Thread TIcGeneralB;
	private Thread TIcWastagebill;
	private Thread TIcWastagebillB;
	private Thread TSoApply;
	private Thread TSoApplyB;
	private Thread TSoPreorder;
	private Thread TSoPreorderB;
	private Thread TSosale;
	private Thread TSoSaleorderB;
	private Thread TSoSaleinvoice; 
	private Thread TSoSaleinvoiceB;
	public NCtoBQServlet() {
	}

	public void destroy() {
		// sender.stop();
		try {
			TArapDjzb.join(1000L);
			TArapDjfb.join(1000L);
			TIcGeneralH.join(1000L);
			TIcGeneralB.join(1000L);
			TIcWastagebill.join(1000L);
			TIcWastagebillB.join(1000L);
			TSoApply.join(1000L);
			TSoApplyB.join(1000L);
			TSoPreorder.join(1000L);
			TSoPreorderB.join(1000L);
			TSosale.join(1000L);
			TSoSaleorderB.join(1000L);
			TSoSaleinvoice.join(1000L);
			TSoSaleinvoiceB.join(1000L);
			if (TArapDjzb.isAlive())
				System.out.println("TArapDjzb抽取NC数据到BQ数据仓库的线程未停止。");
			if (TArapDjfb.isAlive())
				System.out.println("TArapDjfb抽取NC数据到BQ数据仓库的线程未停止。");
			if (TIcGeneralH.isAlive())
				System.out.println("TIcGeneralH抽取NC数据到BQ数据仓库的线程未停止。");
			if (TIcGeneralB.isAlive())
				System.out.println("TIcGeneralB抽取NC数据到BQ数据仓库的线程未停止。");
			if (TIcWastagebill.isAlive())
				System.out.println("TIcWastagebill抽取NC数据到BQ数据仓库的线程未停止。");
			if (TIcWastagebillB.isAlive())
				System.out.println("TIcWastagebillB抽取NC数据到BQ数据仓库的线程未停止。");
			if (TSoApply.isAlive())
				System.out.println("TSoApply抽取NC数据到BQ数据仓库的线程未停止。");
			if (TSoApplyB.isAlive())
				System.out.println("TSoApplyB抽取NC数据到BQ数据仓库的线程未停止。");
			if (TSoPreorder.isAlive())
				System.out.println("TSoPreorder抽取NC数据到BQ数据仓库的线程未停止。");
			if (TSoPreorderB.isAlive())
				System.out.println("TSoPreorderB抽取NC数据到BQ数据仓库的线程未停止。");
			if (TSosale.isAlive())
				System.out.println("TSosale抽取NC数据到BQ数据仓库的线程未停止。");
			if (TSoSaleorderB.isAlive())
				System.out.println("TSoSaleorderB抽取NC数据到BQ数据仓库的线程未停止。");
			if (TSoSaleinvoice.isAlive())
				System.out.println("TSoSaleinvoice抽取NC数据到BQ数据仓库的线程未停止。");
			if (TSoSaleinvoiceB.isAlive())
				System.out.println("TSoSaleinvoiceB抽取NC数据到BQ数据仓库的线程未停止。");
		} catch (Exception e) {
		}
	}

	public void init() throws ServletException {
		senderByArapDjzb = new NCtoBQSenderByArapDjzb();
		senderByArapDjfb = new NCtoBQSenderByArapDjfb();
		senderByIcGeneralH = new NCtoBQSenderByIcGeneralH();
		senderByIcGeneralB = new NCtoBQSenderByIcGeneralB();
		senderByIcWastagebill = new NCtoBQSenderByIcWastagebill();
		senderByIcWastagebillB = new NCtoBQSenderByIcWastagebillB();
		senderBySoApply = new NCtoBQSenderBySoApply();
		senderBySoApplyB = new NCtoBQSenderBySoApplyB();
		senderBySoPreorder = new NCtoBQSenderBySoPreorder();
		senderBySoPreorderB = new NCtoBQSenderBySoPreorderB();
		senderBySosale = new NCtoBQSenderBySoSale();
		senderBySoSaleorderB = new NCtoBQSenderBySoSaleorderB();
		senderBySoSaleinvoice = new NCtoBQSenderBySoSaleinvoice();
		senderBySoSaleinvoiceB = new NCtoBQSenderBySoSaleinvoiceB();
		
		System.out.println("程序初始化！");
		System.out.println("抽取NC数据到BQ数据仓库启动线程！");
		
		TArapDjzb = new Thread(senderByArapDjzb);
		TArapDjfb = new Thread(senderByArapDjfb);
		TIcGeneralH = new Thread(senderByIcGeneralH);
		TIcGeneralB = new Thread(senderByIcGeneralB);
		TIcWastagebill = new Thread(senderByIcWastagebill);
		TIcWastagebillB = new Thread(senderByIcWastagebillB);
		TSoApply = new Thread(senderBySoApply);
		TSoApplyB = new Thread(senderBySoApplyB);
		TSoPreorder = new Thread(senderBySoPreorder);
		TSoPreorderB = new Thread(senderBySoPreorderB);
		TSosale = new Thread(senderBySosale);
		TSoSaleorderB = new Thread(senderBySoSaleorderB);
		TSoSaleinvoice = new Thread(senderBySoSaleinvoice);
		TSoSaleinvoiceB = new Thread(senderBySoSaleinvoiceB);
		
		CommonParam commonParam = new CommonParam();
		String billdate = commonParam.getString("billdate");
		String begindate = commonParam.getString("begindate");
		String enddate = commonParam.getString("enddate");
		Integer zday = Integer.parseInt(commonParam.getString("zday"));
		Integer fday = Integer.parseInt(commonParam.getString("fday"));
		String arap_djzb = commonParam.getString("nctobq_arap_djzb");
		String arap_djfb = commonParam.getString("nctobq_arap_djfb");
		String ic_general_h = commonParam.getString("nctobq_ic_general_h");
		String ic_general_b = commonParam.getString("nctobq_ic_general_b");
		String ic_wastagebill_h = commonParam.getString("nctobq_ic_wastagebill_h");
		String ic_wastagebill_b = commonParam.getString("nctobq_ic_wastagebill_b");
		String so_apply = commonParam.getString("nctobq_so_apply");
		String so_apply_b = commonParam.getString("nctobq_so_apply_b");
		String so_preorder_h = commonParam.getString("nctobq_so_preorder_h");
		String so_preorder_b = commonParam.getString("nctobq_so_preorder_b");
		String so_sale = commonParam.getString("nctobq_so_sale");
		String so_saleorder_b = commonParam.getString("nctobq_so_saleorder_b");
		String so_saleinvoice = commonParam.getString("nctobq_so_saleinvoice");
		String so_saleinvoice_b = commonParam.getString("nctobq_so_saleinvoice_b");
		
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
			TArapDjzb.start();
		}
		if("true".equals(arap_djfb)){
			TArapDjfb.start();
		}
		if("true".equals(so_apply)){
			TSoApply.start();
		}
		if("true".equals(so_apply_b)){
			TSoApplyB.start();
		}
		if("true".equals(so_preorder_h)){
			TSoPreorder.start();
		}
		if("true".equals(so_preorder_b)){
			TSoPreorderB.start();
		}
		if("true".equals(so_saleinvoice)){
			TSoSaleinvoice.start();
		}
		if("true".equals(so_saleinvoice_b)){
			TSoSaleinvoiceB.start();
		}
		if("true".equals(ic_general_b)){
			TIcGeneralB.start();
		}
		if("true".equals(ic_general_h)){
			TIcGeneralH.start();
		}
		if("true".equals(ic_wastagebill_h)){
			TIcWastagebill.start();
		}
		if("true".equals(ic_wastagebill_b)){
			TIcWastagebillB.start();
		}
		if("true".equals(so_sale)){
			TSosale.start();
		}
		if("true".equals(so_saleorder_b)){
			TSoSaleorderB.start(); 
		}
		System.out.println("抽取NC数据到BQ数据仓库启动线程成功！");
	}
}
