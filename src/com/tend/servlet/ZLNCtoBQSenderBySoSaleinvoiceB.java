package com.tend.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 发票辅表增量数据抽取
 * @author Administrator
 *
 */
public class ZLNCtoBQSenderBySoSaleinvoiceB extends BaseDao implements Runnable {

	public ZLNCtoBQSenderBySoSaleinvoiceB() {
		System.out.println("发票辅表增量数据抽取--无参构造函数");
	}

	/**
	 * 自动执行的run方法
	 */
	public void run() {
		try {
			DateLoop("2012-10-15", "2013-01-04","2013-01-07",1);
			System.out.println("发票辅表增量数据抽取完成");
		} catch (Exception e) {
			System.out.println("发票辅表抽取增量数据异常");
			e.printStackTrace();
		}
	}
	/**
	 * 循环调用的方法
	 * @throws Exception 
	 */
	public void DateLoop(String dbilldate,String begindate,String enddate,int days) throws Exception{
		FormatDate mm = new FormatDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse(begindate);
		Date date2 = sdf.parse(enddate);
		Date datetemp = mm.getDateAfterDay(date1, days);
		System.out.println("发票辅表时间区间："+sdf.format(date1)+" to "+sdf.format(datetemp));
		NCtoBQ(dbilldate,sdf.format(date1), sdf.format(datetemp));
		while(mm.dateCompare(datetemp , date2)){
			datetemp = mm.getDateAfterDay(datetemp, days);
			if(mm.dateCompare(date2  ,datetemp )){
				System.out.println("发票辅表时间区间："+sdf.format(mm.getDateAfterDay(datetemp, -days))+" to "+sdf.format(date2));
				NCtoBQ(dbilldate,sdf.format(mm.getDateAfterDay(datetemp, -days)), sdf.format(date2));
			}else{
				System.out.println("发票辅表时间区间："+sdf.format(mm.getDateAfterDay(datetemp, -days))+" to "+sdf.format(datetemp));
				NCtoBQ(dbilldate,sdf.format(mm.getDateAfterDay(datetemp, -days)), sdf.format(datetemp));
			}
		}
	}
	/**
	 * 抽取增量数据
	 * 
	 * @throws Exception
	 */
	public void NCtoBQ(String dbilldate,String ts,String ts2) throws Exception {
		Connection conNC = null;
		PreparedStatement pstNC = null;
		ResultSet restNC = null;
		Connection conBQ = null;
		PreparedStatement pstBQ = null;
		ResultSet restBQ = null;
		System.out.println("发票辅表开始抽取增量数据................");
		System.out.println("开始时间为"+new Timestamp(new Date().getTime()));
		try {
			System.out.println("发票辅表获取连接");
			conNC = this.getConForNC();
			conBQ = this.getConForBQ();
			System.out.println("发票辅表获取连接成功");
			StringBuilder sql = new StringBuilder();
			sql.append("select ");
			sql.append("  BLARGESSFLAG            ,");
			sql.append("  CBATCHID                ,");
			sql.append("  CBODYWAREHOUSEID        ,");
			sql.append("  CCALBODYID              ,");
			sql.append("  CCURRENCYTYPEID         ,");
			sql.append("  CCUSTOMERID             ,");
			sql.append("  CFREEZEID               ,");
			sql.append("  CINVBASDOCID            ,");
			sql.append("  CINVENTORYID            ,");
			sql.append("  CINVOICE_BID            ,");
			sql.append("  CORIGINALBILLCODE       ,");
			sql.append("  COSTSUBJID              ,");
			sql.append("  CPACKUNITID             ,");
			sql.append("  CPROLINEID              ,");
			sql.append("  CQUOTEUNITID            ,");
			sql.append("  CRECEIPTCORPID          ,");
			sql.append("  CRECEIPTTYPE            ,");
			sql.append("  CROWNO                  ,");
			sql.append("  CSALEID                 ,");
			sql.append("  CSOURCEBILLBODYID       ,");
			sql.append("  CSOURCEBILLID           ,");
			sql.append("  CT_MANAGEID             ,");
			sql.append("  CUNITID                 ,");
			sql.append("  CUPINVOICEBILLBODYID    ,");
			sql.append("  CUPINVOICEBILLCODE      ,");
			sql.append("  CUPINVOICEBILLID        ,");
			sql.append("  CUPRECEIPTTYPE          ,");
			sql.append("  CUPSOURCEBILLBODYID     ,");
			sql.append("  CUPSOURCEBILLCODE       ,");
			sql.append("  CUPSOURCEBILLID         ,");
			sql.append("  DDELIVERDATE            ,");
			sql.append("  FBATCHSTATUS            ,");
			sql.append("  FROWNOTE                ,");
			sql.append("  FROWSTATUS              ,");
			sql.append("  NCOSTMNY                ,");
			sql.append("  NDISCOUNTMNY            ,");
			sql.append("  NDISCOUNTRATE           ,");
			sql.append("  NEXCHANGEOTOBRATE       ,");
			sql.append("  NINVOICEDISCOUNTRATE    ,");
			sql.append("  NITEMDISCOUNTRATE       ,");
			sql.append("  NMNY                    ,");
			sql.append("  NNETPRICE               ,");
			sql.append("  NNUMBER                 ,");
			sql.append("  NORIGINALCURDISCOUNTMNY ,");
			sql.append("  NORIGINALCURMNY         ,");
			sql.append("  NORIGINALCURNETPRICE    ,");
			sql.append("  NORIGINALCURPRICE       ,");
			sql.append("  NORIGINALCURSUMMNY      ,");
			sql.append("  NORIGINALCURTAXMNY      ,");
			sql.append("  NORIGINALCURTAXNETPRICE ,");
			sql.append("  NORIGINALCURTAXPRICE    ,");
			sql.append("  NPACKNUMBER             ,");
			sql.append("  NPRICE                  ,");
			sql.append("  NQOCURTAXNETPRI         ,");
			sql.append("  NQUNETPRICE             ,");
			sql.append("  NQUORICURNETPRI         ,");
			sql.append("  NQUORICURPRI            ,");
			sql.append("  NQUORICURTAXPRI         ,");
			sql.append("  NQUOTENUMBER            ,");
			sql.append("  NQUOTEUNITRATE          ,");
			sql.append("  NQUPRICE                ,");
			sql.append("  NQUTAXNETPRICE          ,");
			sql.append("  NQUTAXPRICE             ,");
			sql.append("  NSIMULATECOSTMNY        ,");
			sql.append("  NSUBCURSUMMNY           ,");
			sql.append("  NSUBQUNETPRI            ,");
			sql.append("  NSUBQUPRI               ,");
			sql.append("  NSUBQUTAXNETPRI         ,");
			sql.append("  NSUBQUTAXPRI            ,");
			sql.append("  NSUBSUMMNY              ,");
			sql.append("  NSUBTAXNETPRICE         ,");
			sql.append("  NSUMMNY                 ,");
			sql.append("  NTAXMNY                 ,");
			sql.append("  NTAXNETPRICE            ,");
			sql.append("  NTAXPRICE               ,");
			sql.append("  NTAXRATE                ,");
			sql.append("  PK_CORP                 ,");
			sql.append("  TS                       ");
			sql.append("  from so_saleinvoice_b svb");
			sql.append("  where svb.dr=0 and svb.csaleid in (select sv.csaleid from so_saleinvoice sv ");
			sql.append("  where sv.dbilldate >= '").append(dbilldate+"'");
			sql.append("  and sv.ts >= '").append(ts+"'");
			sql.append("  and sv.ts <= '").append(ts2+"'");
			sql.append("  and sv.dr=0");
			sql.append("  and sv.pk_corp != '1020'");
			sql.append("  and sv.pk_corp != '1021'");
			sql.append("  and sv.pk_corp != '1023'");
			sql.append("  and sv.pk_corp != '1024'");
			sql.append("  and sv.pk_corp != '1032' )");
			
			System.out.println("查询sql:"+sql);
			pstNC = conNC.prepareStatement(sql.toString());
			restNC = pstNC.executeQuery();
			ResultSetMetaData rsmd = restNC.getMetaData();
			int resultcount = rsmd.getColumnCount();
			int tm = 0;
			while(restNC.next()){
				StringBuilder insetSql = new StringBuilder();
				insetSql.append("insert into ODS_SO_SALEINVOICE_B ( ");
				insetSql.append("  BLARGESSFLAG            ,");
				insetSql.append("  CBATCHID                ,");
				insetSql.append("  CBODYWAREHOUSEID        ,");
				insetSql.append("  CCALBODYID              ,");
				insetSql.append("  CCURRENCYTYPEID         ,");
				insetSql.append("  CCUSTOMERID             ,");
				insetSql.append("  CFREEZEID               ,");
				insetSql.append("  CINVBASDOCID            ,");
				insetSql.append("  CINVENTORYID            ,");
				insetSql.append("  CINVOICE_BID            ,");
				insetSql.append("  CORIGINALBILLCODE       ,");
				insetSql.append("  COSTSUBJID              ,");
				insetSql.append("  CPACKUNITID             ,");
				insetSql.append("  CPROLINEID              ,");
				insetSql.append("  CQUOTEUNITID            ,");
				insetSql.append("  CRECEIPTCORPID          ,");
				insetSql.append("  CRECEIPTTYPE            ,");
				insetSql.append("  CROWNO                  ,");
				insetSql.append("  CSALEID                 ,");
				insetSql.append("  CSOURCEBILLBODYID       ,");
				insetSql.append("  CSOURCEBILLID           ,");
				insetSql.append("  CT_MANAGEID             ,");
				insetSql.append("  CUNITID                 ,");
				insetSql.append("  CUPINVOICEBILLBODYID    ,");
				insetSql.append("  CUPINVOICEBILLCODE      ,");
				insetSql.append("  CUPINVOICEBILLID        ,");
				insetSql.append("  CUPRECEIPTTYPE          ,");
				insetSql.append("  CUPSOURCEBILLBODYID     ,");
				insetSql.append("  CUPSOURCEBILLCODE       ,");
				insetSql.append("  CUPSOURCEBILLID         ,");
				insetSql.append("  DDELIVERDATE            ,");
				insetSql.append("  FBATCHSTATUS            ,");
				insetSql.append("  FROWNOTE                ,");
				insetSql.append("  FROWSTATUS              ,");
				insetSql.append("  NCOSTMNY                ,");
				insetSql.append("  NDISCOUNTMNY            ,");
				insetSql.append("  NDISCOUNTRATE           ,");
				insetSql.append("  NEXCHANGEOTOBRATE       ,");
				insetSql.append("  NINVOICEDISCOUNTRATE    ,");
				insetSql.append("  NITEMDISCOUNTRATE       ,");
				insetSql.append("  NMNY                    ,");
				insetSql.append("  NNETPRICE               ,");
				insetSql.append("  NNUMBER                 ,");
				insetSql.append("  NORIGINALCURDISCOUNTMNY ,");
				insetSql.append("  NORIGINALCURMNY         ,");
				insetSql.append("  NORIGINALCURNETPRICE    ,");
				insetSql.append("  NORIGINALCURPRICE       ,");
				insetSql.append("  NORIGINALCURSUMMNY      ,");
				insetSql.append("  NORIGINALCURTAXMNY      ,");
				insetSql.append("  NORIGINALCURTAXNETPRICE ,");
				insetSql.append("  NORIGINALCURTAXPRICE    ,");
				insetSql.append("  NPACKNUMBER             ,");
				insetSql.append("  NPRICE                  ,");
				insetSql.append("  NQOCURTAXNETPRI         ,");
				insetSql.append("  NQUNETPRICE             ,");
				insetSql.append("  NQUORICURNETPRI         ,");
				insetSql.append("  NQUORICURPRI            ,");
				insetSql.append("  NQUORICURTAXPRI         ,");
				insetSql.append("  NQUOTENUMBER            ,");
				insetSql.append("  NQUOTEUNITRATE          ,");
				insetSql.append("  NQUPRICE                ,");
				insetSql.append("  NQUTAXNETPRICE          ,");
				insetSql.append("  NQUTAXPRICE             ,");
				insetSql.append("  NSIMULATECOSTMNY        ,");
				insetSql.append("  NSUBCURSUMMNY           ,");
				insetSql.append("  NSUBQUNETPRI            ,");
				insetSql.append("  NSUBQUPRI               ,");
				insetSql.append("  NSUBQUTAXNETPRI         ,");
				insetSql.append("  NSUBQUTAXPRI            ,");
				insetSql.append("  NSUBSUMMNY              ,");
				insetSql.append("  NSUBTAXNETPRICE         ,");
				insetSql.append("  NSUMMNY                 ,");
				insetSql.append("  NTAXMNY                 ,");
				insetSql.append("  NTAXNETPRICE            ,");
				insetSql.append("  NTAXPRICE               ,");
				insetSql.append("  NTAXRATE                ,");
				insetSql.append("  PK_CORP                 ,");
				insetSql.append("  TS                 ) values (        ");
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
					if(tm==0){
						System.out.println(insetSql);
					}
					try {
						//执行存入增量数据
						pstBQ = conBQ.prepareStatement(insetSql.toString());
						boolean result = pstBQ.execute();
						if(!result){
							System.out.println("第"+tm+"条B保存成功");
						}else{
							System.out.println("第"+tm+"条B保存失败");
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
					tm++;
			 }                                                         
			System.out.println("发票辅表增量数据抽取完毕");
			System.out.println("结束时间为"+new Timestamp(new Date().getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("发票辅表NC connection close");
			BaseDao.closeAll(pstNC, restNC, conNC);
			System.out.println("发票辅表NC connection closed");
			
			System.out.println("发票辅表BQ connection close");
			BaseDao.closeAll(pstBQ, restBQ, conBQ);
			System.out.println("发票辅表BQ connection closed");
		}

	}
}
