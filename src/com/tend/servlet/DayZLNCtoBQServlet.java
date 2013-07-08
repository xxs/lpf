package com.tend.servlet;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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
	private DayZLNCtoBQSenderByArapDjzb arapDjzb;
	private DayZLNCtoBQSenderByArapDjfb arapDjfb; 
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

	public DayZLNCtoBQServlet() {
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
		arapDjzb = new DayZLNCtoBQSenderByArapDjzb();
		arapDjfb = new DayZLNCtoBQSenderByArapDjfb();
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
		
		
		
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1); // 控制时      12
        calendar.set(Calendar.MINUTE, 0);       // 控制分   0
        calendar.set(Calendar.SECOND, 0);       // 控制秒      0
        Date time = calendar.getTime();         // 得出执行任务的时间,此处为今天的12：00：00
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	try {
            		//读取参数配置文件
            		CommonParam commonParam = new CommonParam();
            		Integer days = Integer.parseInt(commonParam.getString("days"));
            		Integer beforedays = Integer.parseInt(commonParam.getString("beforedays"));
            		String arap_djzb = commonParam.getString("dayzlnc_arap_djzb");
            		String arap_djfb = commonParam.getString("dayzlnc_arap_djfb");
            		String ic_general_h = commonParam.getString("dayzlnc_ic_general_h");
            		String ic_general_b = commonParam.getString("dayzlnc_ic_general_b");
            		String ic_wastagebill_h = commonParam.getString("dayzlnc_ic_wastagebill_h");
            		String ic_wastagebill_b = commonParam.getString("dayzlnc_ic_wastagebill_b");
            		String so_apply = commonParam.getString("dayzlnc_so_apply");
            		String so_apply_b = commonParam.getString("dayzlnc_so_apply_b");
            		String so_preorder_h = commonParam.getString("dayzlnc_so_preorder_h");
            		String so_preorder_b = commonParam.getString("dayzlnc_so_preorder_b");
            		String so_sale = commonParam.getString("dayzlnc_so_sale");
            		String so_saleorder_b = commonParam.getString("dayzlnc_so_saleorder_b");
            		String so_saleinvoice = commonParam.getString("dayzlnc_so_saleinvoice");
            		String so_saleinvoice_b = commonParam.getString("dayzlnc_so_saleinvoice_b");
            		
            		System.out.println("-------------加载配置参数  begin-------------------");
            		System.out.println("days参数值 ："+days);
            		System.out.println("beforedays参数值 ："+beforedays);
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
            		
					System.out.println("抽取增量NC数据到BQ数据仓库启动线程！"); 
					System.out.println(TarapDjzb.isAlive());
					System.out.println(TarapDjfb.isAlive());
					System.out.println(TsoSale.isAlive());
					System.out.println(TsoSaleorderB.isAlive());
					System.out.println(TsoApply.isAlive());
					System.out.println(TsoApplyB.isAlive());
					System.out.println(TsoPreorder.isAlive());
					System.out.println(TsoPreorderB.isAlive());
					System.out.println(TsoSaleinvoice.isAlive());
					System.out.println(TsoSaleinvoiceB.isAlive());
					System.out.println(TicGeneralB.isAlive());
					System.out.println(TicGeneralH.isAlive());
					System.out.println(Twastagebill.isAlive());
					System.out.println(TwastagebillB.isAlive());
					if(!TarapDjzb.isAlive()&&"true".equals(arap_djzb)){
						TarapDjzb = new Thread(arapDjzb);
						TarapDjzb.start();
					}
					if(!TarapDjfb.isAlive()&&"true".equals(arap_djfb)){
						TarapDjfb = new Thread(arapDjfb);
						TarapDjfb.start();
					}
					if(!TsoSale.isAlive()&&"true".equals(so_sale)){
						TsoSale = new Thread(soSale);
						TsoSale.start();
					}
					if (!TsoSaleorderB.isAlive()&&"true".equals(so_saleorder_b)){
						TsoSaleorderB = new Thread(soSaleorderB);
						TsoSaleorderB.start();
					}
					if (!TsoApply.isAlive()&&"true".equals(so_apply)){
						TsoApply = new Thread(soApply);
						TsoApply.start();
					}
					if (!TsoApplyB.isAlive()&&"true".equals(so_apply_b)){
						TsoApplyB = new Thread(soApplyB);
						TsoApplyB.start();
					}
					if (!TsoPreorder.isAlive()&&"true".equals(so_preorder_h)){
						TsoPreorder = new Thread(soPreorder);
						TsoPreorder.start();
					}
					if (!TsoPreorderB.isAlive()&&"true".equals(so_preorder_b)){
						TsoPreorderB = new Thread(soPreorderB);
						TsoPreorderB.start();
					}
					if (!TsoSaleinvoice.isAlive()&&"true".equals(so_saleinvoice)){
						TsoSaleinvoice = new Thread(soSaleinvoice);
						TsoSaleinvoice.start();
					}
					if (!TsoSaleinvoiceB.isAlive()&&"true".equals(so_saleinvoice_b)){
						TsoSaleinvoiceB = new Thread(soSaleinvoiceB);
						TsoSaleinvoiceB.start();
					}
					if (!TicGeneralB.isAlive()&&"true".equals(ic_general_b)){ 
						TicGeneralB = new Thread(icGeneralB);
						TicGeneralB.start();
					}
					if (!TicGeneralH.isAlive()&&"true".equals(ic_general_h)){
						TicGeneralH = new Thread(icGeneralH);
						TicGeneralH.start();
					}
					if (!Twastagebill.isAlive()&&"true".equals(ic_wastagebill_h)){
						Twastagebill = new Thread(wastagebill);
						Twastagebill.start();
					}
					if (!TwastagebillB.isAlive()&&"true".equals(ic_wastagebill_b)){
						TwastagebillB = new Thread(wastagebillB);
						TwastagebillB.start();
					}
					System.out.println("线程启动成功");
				} catch (Exception e) {
					System.out.println("线程启动异常");
					e.printStackTrace();
				}
            }
        }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
	}
}
