package com.tend.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NCtoBQSenderBySoSaleinvoice extends BaseDao implements Runnable {

	public NCtoBQSenderBySoSaleinvoice() {
		System.out.println("NCtoBQSenderBySoSaleinvoice 无参构造函数");
	}

	/**
	 * 自动执行的run方法
	 */
	public void run() {
		try {
			DateLoop("2013-03-02", "2013-03-05",3);
			System.out.println("发票主表数据抽取完成");
		} catch (Exception e) {
			System.out.println("发票主表抽取数据异常");
			e.printStackTrace();
		}
	}
	/**
	 * 循环调用的方法
	 * @throws Exception 
	 */
	public void DateLoop(String begindate,String enddate,int days) throws Exception{
		FormatDate mm = new FormatDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse(begindate);
		Date date2 = sdf.parse(enddate);
		Date datetemp = mm.getDateAfterDay(date1, days);
		System.out.println("发票主表时间区间："+sdf.format(date1)+" to "+sdf.format(datetemp));
		NCtoBQ(sdf.format(date1), sdf.format(datetemp));
		while(mm.dateCompare(datetemp , date2)){
			datetemp = mm.getDateAfterDay(datetemp, days);
			if(mm.dateCompare(date2  ,datetemp )){
				System.out.println("发票主表时间区间："+sdf.format(mm.getDateAfterDay(datetemp, -days))+" to "+sdf.format(date2));
				NCtoBQ(sdf.format(mm.getDateAfterDay(datetemp, -days)), sdf.format(date2));
			}else{
				System.out.println("发票主表时间区间："+sdf.format(mm.getDateAfterDay(datetemp, -days))+" to "+sdf.format(datetemp));
				NCtoBQ(sdf.format(mm.getDateAfterDay(datetemp, -days)), sdf.format(datetemp));
			}
		}
	}
	/**
	 * 抽取数据
	 * 
	 * @throws Exception
	 */
	public void NCtoBQ(String beginDate,String endDate) throws Exception {
		Connection conNC = null;
		PreparedStatement pstNC = null;
		ResultSet restNC = null;
		Connection conBQ = null;
		PreparedStatement pstBQ = null;
		ResultSet restBQ = null;
		System.out.println("发票主表开始抽取数据................");
		System.out.println("开始时间为"+new Timestamp(new Date().getTime()));
		try {
			System.out.println("发票主表获取连接");
			conNC = this.getConForNC();
			conBQ = this.getConForBQ();
			System.out.println("发票主表获取连接成功");
			StringBuilder sql = new StringBuilder();
			sql.append("select ");
			sql.append("  BFREECUSTFLAG      ,");
			sql.append("  BINITFLAG          ,");
			sql.append("  BTOGOLDTAX         ,");
			sql.append("  CAPPROVEID         ,");
			sql.append("  CBIZTYPE           ,");
			sql.append("  CCALBODYID         ,");
			sql.append("  CCREDITNUM         ,");
			sql.append("  CCUSTBANKID        ,");
			sql.append("  CDEPTID            ,");
			sql.append("  CDISPATCHERID      ,");
			sql.append("  CEMPLOYEEID        ,");
			sql.append("  CFREECUSTID        ,");
			sql.append("  CGOLDTAXCODE       ,");
			sql.append("  COPERATORID        ,");
			sql.append("  CRECEIPTCORPID     ,");
			sql.append("  CRECEIPTCUSTOMERID ,");
			sql.append("  CRECEIPTTYPE       ,");
			sql.append("  CSALECORPID        ,");
			sql.append("  CSALEID            ,");
			sql.append("  CTERMPROTOCOLID    ,");
			sql.append("  CTRANSMODEID       ,");
			sql.append("  CWAREHOUSEID       ,");
			sql.append("  DAPPROVEDATE       ,");
			sql.append("  DAUDITTIME         ,");
			sql.append("  DBILLDATE          ,");
			sql.append("  DBILLTIME          ,");
			sql.append("  DMAKEDATE          ,");
			sql.append("  DMODITIME          ,");
			sql.append("  DTOGOLDTAXTIME     ,");
			sql.append("  FCOUNTERACTFLAG    ,");
			sql.append("  FINVOICECLASS      ,");
			sql.append("  FINVOICETYPE       ,");
			sql.append("  FSTATUS            ,");
			sql.append("  IBALANCEFLAG       ,");
			sql.append("  IPRINTCOUNT        ,");
			sql.append("  NDISCOUNTRATE      ,");
			sql.append("  NEVALUATECARRIAGE  ,");
			sql.append("  NNETMNY            ,");
			sql.append("  NSTRIKEMNY         ,");
			sql.append("  NSUBSCRIPTION      ,");
			sql.append("  NTOTALSUMMNY       ,");
			sql.append("  PK_CORP            ,");
			sql.append("  TS                 ,");
			sql.append("  VACCOUNTYEAR       ,");
			sql.append("  VDEF1              ,");
			sql.append("  VDEF10             ,");
			sql.append("  VDEF11             ,");
			sql.append("  VDEF12             ,");
			sql.append("  VDEF13             ,");
			sql.append("  VDEF14             ,");
			sql.append("  VDEF15             ,");
			sql.append("  VDEF16             ,");
			sql.append("  VDEF17             ,");
			sql.append("  VDEF18             ,");
			sql.append("  VDEF19             ,");
			sql.append("  VDEF2              ,");
			sql.append("  VDEF20             ,");
			sql.append("  VDEF3              ,");
			sql.append("  VDEF4              ,");
			sql.append("  VDEF5              ,");
			sql.append("  VDEF6              ,");
			sql.append("  VDEF7              ,");
			sql.append("  VDEF8              ,");
			sql.append("  VDEF9              ,");
			sql.append("  VEDITREASON        ,");
			sql.append("  VNOTE              ,");
			sql.append("  VPRINTCUSTNAME     ,");
			sql.append("  VRECEIPTCODE       ,");
			sql.append("  VRECEIVEADDRESS    ,");
			sql.append("  BISSEND            ,");
			sql.append("  NKPMNY              ,");
			sql.append("  DR                  ");
			sql.append("  from so_saleinvoice sv");
			sql.append("  where sv.ts >= '").append(beginDate+"'");
			sql.append("  and sv.ts <= '").append(endDate+"'");
			sql.append("  and sv.pk_corp != '1020'");
			sql.append("  and sv.pk_corp != '1021'");
			sql.append("  and sv.pk_corp != '1023'");
			sql.append("  and sv.pk_corp != '1024'");
			sql.append("  and sv.pk_corp != '1032'");
			//System.out.println("查询sql:"+sql);
			pstNC = conNC.prepareStatement(sql.toString());
			restNC = pstNC.executeQuery();
			ResultSetMetaData rsmd = restNC.getMetaData();
			int resultcount = rsmd.getColumnCount();
			
			while(restNC.next()){
				StringBuilder insetSql = new StringBuilder();
				insetSql.append("insert into ODS_SO_SALEINVOICE ( ");
				insetSql.append("  BFREECUSTFLAG      ,");
				insetSql.append("  BINITFLAG          ,");
				insetSql.append("  BTOGOLDTAX         ,");
				insetSql.append("  CAPPROVEID         ,");
				insetSql.append("  CBIZTYPE           ,");
				insetSql.append("  CCALBODYID         ,");
				insetSql.append("  CCREDITNUM         ,");
				insetSql.append("  CCUSTBANKID        ,");
				insetSql.append("  CDEPTID            ,");
				insetSql.append("  CDISPATCHERID      ,");
				insetSql.append("  CEMPLOYEEID        ,");
				insetSql.append("  CFREECUSTID        ,");
				insetSql.append("  CGOLDTAXCODE       ,");
				insetSql.append("  COPERATORID        ,");
				insetSql.append("  CRECEIPTCORPID     ,");
				insetSql.append("  CRECEIPTCUSTOMERID ,");
				insetSql.append("  CRECEIPTTYPE       ,");
				insetSql.append("  CSALECORPID        ,");
				insetSql.append("  CSALEID            ,");
				insetSql.append("  CTERMPROTOCOLID    ,");
				insetSql.append("  CTRANSMODEID       ,");
				insetSql.append("  CWAREHOUSEID       ,");
				insetSql.append("  DAPPROVEDATE       ,");
				insetSql.append("  DAUDITTIME         ,");
				insetSql.append("  DBILLDATE          ,");
				insetSql.append("  DBILLTIME          ,");
				insetSql.append("  DMAKEDATE          ,");
				insetSql.append("  DMODITIME          ,");
				insetSql.append("  DTOGOLDTAXTIME     ,");
				insetSql.append("  FCOUNTERACTFLAG    ,");
				insetSql.append("  FINVOICECLASS      ,");
				insetSql.append("  FINVOICETYPE       ,");
				insetSql.append("  FSTATUS            ,");
				insetSql.append("  IBALANCEFLAG       ,");
				insetSql.append("  IPRINTCOUNT        ,");
				insetSql.append("  NDISCOUNTRATE      ,");
				insetSql.append("  NEVALUATECARRIAGE  ,");
				insetSql.append("  NNETMNY            ,");
				insetSql.append("  NSTRIKEMNY         ,");
				insetSql.append("  NSUBSCRIPTION      ,");
				insetSql.append("  NTOTALSUMMNY       ,");
				insetSql.append("  PK_CORP            ,");
				insetSql.append("  TS                 ,");
				insetSql.append("  VACCOUNTYEAR       ,");
				insetSql.append("  VDEF1              ,");
				insetSql.append("  VDEF10             ,");
				insetSql.append("  VDEF11             ,");
				insetSql.append("  VDEF12             ,");
				insetSql.append("  VDEF13             ,");
				insetSql.append("  VDEF14             ,");
				insetSql.append("  VDEF15             ,");
				insetSql.append("  VDEF16             ,");
				insetSql.append("  VDEF17             ,");
				insetSql.append("  VDEF18             ,");
				insetSql.append("  VDEF19             ,");
				insetSql.append("  VDEF2              ,");
				insetSql.append("  VDEF20             ,");
				insetSql.append("  VDEF3              ,");
				insetSql.append("  VDEF4              ,");
				insetSql.append("  VDEF5              ,");
				insetSql.append("  VDEF6              ,");
				insetSql.append("  VDEF7              ,");
				insetSql.append("  VDEF8              ,");
				insetSql.append("  VDEF9              ,");
				insetSql.append("  VEDITREASON        ,");
				insetSql.append("  VNOTE              ,");
				insetSql.append("  VPRINTCUSTNAME     ,");
				insetSql.append("  VRECEIPTCODE       ,");
				insetSql.append("  VRECEIVEADDRESS    ,");
				insetSql.append("  BISSEND            ,");
				insetSql.append("  NKPMNY           ,      ");
				insetSql.append("  DR           ) values (       ");
					for (int i = 1; i <= resultcount; i++) {
						
						if(rsmd.getColumnType(i)==1 ||rsmd.getColumnType(i)==12){
							if(null == restNC.getString(i) || restNC.getString(i).isEmpty()){
								insetSql.append("''");
							}else{
								insetSql.append("'").append(restNC.getString(i)).append("'");
							}
							if(i<resultcount){
								insetSql.append(",");
							}
						}else{
							insetSql.append(restNC.getDouble(i));
							if(i<resultcount){
								insetSql.append(",");
							}
						}
					}
					insetSql.append(")");
					//System.out.println(insetSql);
					try {
						//执行存入数据
						pstBQ = conBQ.prepareStatement(insetSql.toString());
						boolean result = pstBQ.execute();
						if(!result){
							//System.out.println("保存成功");
						}else{
							//System.out.println("保存失败");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}finally {
						try{
							if(pstBQ!=null){
								pstBQ.close();
							}
						}catch(Exception e){
							e.printStackTrace();
						}
					}
			 }                                                         
			System.out.println("发票主表数据抽取完毕");
			System.out.println("结束时间为"+new Timestamp(new Date().getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("发票主表NC connection close");
			BaseDao.closeAll(pstNC, restNC, conNC);
			System.out.println("发票主表NC connection closed");
			
			System.out.println("发票主表BQ connection close");
			BaseDao.closeAll(pstBQ, restBQ, conBQ);
			System.out.println("发票主表BQ connection closed");
		}

	}
}
