package com.tend.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 途损单辅表增量数据抽取
 * @author Administrator
 *
 */
public class NCtoBQSenderByIcWastagebillB extends BaseDao implements Runnable {

	public NCtoBQSenderByIcWastagebillB() {
		System.out.println("途损单辅表增量数据抽取--无参构造函数");
	}

	/**
	 * 自动执行的run方法
	 */
	public void run() {
		try {
			DateLoop("2013-03-02", "2013-03-05",1);
			System.out.println("途损单辅表增量数据抽取完成");
		} catch (Exception e) {
			System.out.println("途损单辅表抽取数据异常");
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
		System.out.println("途损单辅表时间区间："+sdf.format(date1)+" to "+sdf.format(datetemp));
		NCtoBQ(sdf.format(date1), sdf.format(datetemp));
		while(mm.dateCompare(datetemp , date2)){
			datetemp = mm.getDateAfterDay(datetemp, days);
			if(mm.dateCompare(date2  ,datetemp )){
				System.out.println("途损单辅表时间区间："+sdf.format(mm.getDateAfterDay(datetemp, -days))+" to "+sdf.format(date2));
				NCtoBQ(sdf.format(mm.getDateAfterDay(datetemp, -days)), sdf.format(date2));
			}else{
				System.out.println("途损单辅表时间区间："+sdf.format(mm.getDateAfterDay(datetemp, -days))+" to "+sdf.format(datetemp));
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
		System.out.println("途损单辅取数据表抽................");
		System.out.println("开始时间为"+new Timestamp(new Date().getTime()));
		try {
			System.out.println("途损单辅表获取连接");
			conNC = this.getConForNC();
			conBQ = this.getConForBQ();
			System.out.println("途损单辅表获取连接成功");
			StringBuilder sql = new StringBuilder();
			sql.append("select ");
			sql.append("  CASTUNITID           ,");
			sql.append("  CBILLCUSTBASID       ,");
			sql.append("  CBILLCUSTOMERID      ,");
			sql.append("  CCURRENCYTYPEID      ,");
			sql.append("  CFIRSTBILLBID        ,");
			sql.append("  CFIRSTBILLID         ,");
			sql.append("  CFIRSTTYPECODE       ,");
			sql.append("  CINVBASID            ,");
			sql.append("  CINVENTORYID         ,");
			sql.append("  CQUOTEUNITID         ,");
			sql.append("  CROWNO               ,");
			sql.append("  CSIGNCURRENCYID      ,");
			sql.append("  CSOURCEBILLBID       ,");
			sql.append("  CSOURCEBILLID        ,");
			sql.append("  CSOURCETYPECODE      ,");
			sql.append("  CWASTAGEBILLBID      ,");
			sql.append("  CWASTAGEBILLID       ,");
			sql.append("  CWASTDUTYDEPTID      ,");
			sql.append("  CWASTDUTYOPERID      ,");
			sql.append("  DARRIVEDATE          ,");
			sql.append("  FLARGESS             ,");
			sql.append("  FTOSETTLEFLAG1       ,");
			sql.append("  FTOSETTLEFLAG2       ,");
			sql.append("  FWASTDUTYFLAG        ,");
			sql.append("  HSL                  ,");
			sql.append("  NASSISTCURMNY        ,");
			sql.append("  NASSISTCURPRICE      ,");
			sql.append("  NASSISTCURSUMMNY     ,");
			sql.append("  NASSISTCURTAXPRICE   ,");
			sql.append("  NASSISTNUM           ,");
			sql.append("  NASSISTTAXMNY        ,");
			sql.append("  NASTQUOTECURPRICE    ,");
			sql.append("  NASTQUOTECURTAXPRICE ,");
			sql.append("  NASTSIGNMNY          ,");
			sql.append("  NASTSIGNPRICE        ,");
			sql.append("  NASTSIGNSUMMNY       ,");
			sql.append("  NASTSIGNTAXMNY       ,");
			sql.append("  NASTSIGNTAXPRICE     ,");
			sql.append("  NEXCHANGEOTOARATE    ,");
			sql.append("  NEXCHANGEOTOBRATE    ,");
			sql.append("  NMNY                 ,");
			sql.append("  NNORWASTASTNUM       ,");
			sql.append("  NNORWASTNUM          ,");
			sql.append("  NNUM                 ,");
			sql.append("  NORGQUOTECURPRICE    ,");
			sql.append("  NORGQUOTECURTAXPRICE ,");
			sql.append("  NORIGINALCURMNY      ,");
			sql.append("  NORIGINALCURPRICE    ,");
			sql.append("  NORIGINALCURSUMMNY   ,");
			sql.append("  NORIGINALCURTAXMNY   ,");
			sql.append("  NORIGINALCURTAXPRICE ,");
			sql.append("  NORISIGNMNY          ,");
			sql.append("  NORISIGNPRICE        ,");
			sql.append("  NORISIGNSUMMNY       ,");
			sql.append("  NORISIGNTAXMNY       ,");
			sql.append("  NORISIGNTAXPRICE     ,");
			sql.append("  NOUTASTNUM           ,");
			sql.append("  NOUTBACKASTNUM       ,");
			sql.append("  NOUTBACKNUM          ,");
			sql.append("  NOUTNUM              ,");
			sql.append("  NOUTSIGNASTNUM       ,");
			sql.append("  NOUTSIGNNUM          ,");
			sql.append("  NPRICE               ,");
			sql.append("  NQUOTEPRICE          ,");
			sql.append("  NQUOTETAXPRICE       ,");
			sql.append("  NQUOTEUNITNUM        ,");
			sql.append("  NQUOTEUNITRATE       ,");
			sql.append("  NSALESETTLEMNY       ,");
			sql.append("  NSALESETTLENUM       ,");
			sql.append("  NSIGNEXCHGOTOARATE   ,");
			sql.append("  NSIGNEXCHGOTOBRATE   ,");
			sql.append("  NSIGNMNY             ,");
			sql.append("  NSIGNPRICE           ,");
			sql.append("  NSIGNSUMMNY          ,");
			sql.append("  NSIGNTAXMNY          ,");
			sql.append("  NSIGNTAXPRICE        ,");
			sql.append("  NSIGNTAXRATE         ,");
			sql.append("  NSUMMNY              ,");
			sql.append("  NTAXMNY              ,");
			sql.append("  NTAXPRICE            ,");
			sql.append("  NTAXRATE             ,");
			sql.append("  NTOSETTLEMNY1        ,");
			sql.append("  NTOSETTLEMNY2        ,");
			sql.append("  NTOSETTLENUM1        ,");
			sql.append("  NTOSETTLENUM2        ,");
			sql.append("  PK_CORP              ,");
			sql.append("  TS                   ,");
			sql.append("  VBATCHCODE           ,");
			sql.append("  VBILLTYPEU8RM        ,");
			sql.append("  VBODYDEF1            ,");
			sql.append("  VBODYDEF10           ,");
			sql.append("  VBODYDEF11           ,");
			sql.append("  VBODYDEF12           ,");
			sql.append("  VBODYDEF13           ,");
			sql.append("  VBODYDEF14           ,");
			sql.append("  VBODYDEF15           ,");
			sql.append("  VBODYDEF16           ,");
			sql.append("  VBODYDEF17           ,");
			sql.append("  VBODYDEF18           ,");
			sql.append("  VBODYDEF19           ,");
			sql.append("  VBODYDEF2            ,");
			sql.append("  VBODYDEF20           ,");
			sql.append("  VBODYDEF3            ,");
			sql.append("  VBODYDEF4            ,");
			sql.append("  VBODYDEF5            ,");
			sql.append("  VBODYDEF6            ,");
			sql.append("  VBODYDEF7            ,");
			sql.append("  VBODYDEF8            ,");
			sql.append("  VBODYDEF9            ,");
			sql.append("  VFIRSTBILLCODE       ,");
			sql.append("  VFIRSTROWNO          ,");
			sql.append("  VFREE1               ,");
			sql.append("  VFREE2               ,");
			sql.append("  VFREE3               ,");
			sql.append("  VFREE4               ,");
			sql.append("  VFREE5               ,");
			sql.append("  VMEMO                ,");
			sql.append("  VSOURCEBILLCODE      ,");
			sql.append("  VSOURCEROWNO,         ");
			sql.append("  DR                              ");
			sql.append("  from ic_wastagebill_b wb");
			sql.append("  where wb.cwastagebillid in (select w.cwastagebillid");
			sql.append("      from ic_wastagebill w");
			sql.append("  where w.ts >= '").append(beginDate+"'");
			sql.append("  and w.ts <= '").append(endDate+"'");
			sql.append("  and w.pk_corp != '1020'");
			sql.append("  and w.pk_corp != '1021'");
			sql.append("  and w.pk_corp != '1023'");
			sql.append("  and w.pk_corp != '1024'");
			sql.append("  and w.pk_corp != '1032' )");

//			System.out.println("查询sql:"+sql);
			pstNC = conNC.prepareStatement(sql.toString());
			restNC = pstNC.executeQuery();
			ResultSetMetaData rsmd = restNC.getMetaData();
			int resultcount = rsmd.getColumnCount();
			int tm = 0;
			while(restNC.next()){
				StringBuilder insetSql = new StringBuilder();
				insetSql.append("insert into ODS_IC_WASTAGEBILL_B (  ");
				insetSql.append("  CASTUNITID           ,");
				insetSql.append("  CBILLCUSTBASID       ,");
				insetSql.append("  CBILLCUSTOMERID      ,");
				insetSql.append("  CCURRENCYTYPEID      ,");
				insetSql.append("  CFIRSTBILLBID        ,");
				insetSql.append("  CFIRSTBILLID         ,");
				insetSql.append("  CFIRSTTYPECODE       ,");
				insetSql.append("  CINVBASID            ,");
				insetSql.append("  CINVENTORYID         ,");
				insetSql.append("  CQUOTEUNITID         ,");
				insetSql.append("  CROWNO               ,");
				insetSql.append("  CSIGNCURRENCYID      ,");
				insetSql.append("  CSOURCEBILLBID       ,");
				insetSql.append("  CSOURCEBILLID        ,");
				insetSql.append("  CSOURCETYPECODE      ,");
				insetSql.append("  CWASTAGEBILLBID      ,");
				insetSql.append("  CWASTAGEBILLID       ,");
				insetSql.append("  CWASTDUTYDEPTID      ,");
				insetSql.append("  CWASTDUTYOPERID      ,");
				insetSql.append("  DARRIVEDATE          ,");
				insetSql.append("  FLARGESS             ,");
				insetSql.append("  FTOSETTLEFLAG1       ,");
				insetSql.append("  FTOSETTLEFLAG2       ,");
				insetSql.append("  FWASTDUTYFLAG        ,");
				insetSql.append("  HSL                  ,");
				insetSql.append("  NASSISTCURMNY        ,");
				insetSql.append("  NASSISTCURPRICE      ,");
				insetSql.append("  NASSISTCURSUMMNY     ,");
				insetSql.append("  NASSISTCURTAXPRICE   ,");
				insetSql.append("  NASSISTNUM           ,");
				insetSql.append("  NASSISTTAXMNY        ,");
				insetSql.append("  NASTQUOTECURPRICE    ,");
				insetSql.append("  NASTQUOTECURTAXPRICE ,");
				insetSql.append("  NASTSIGNMNY          ,");
				insetSql.append("  NASTSIGNPRICE        ,");
				insetSql.append("  NASTSIGNSUMMNY       ,");
				insetSql.append("  NASTSIGNTAXMNY       ,");
				insetSql.append("  NASTSIGNTAXPRICE     ,");
				insetSql.append("  NEXCHANGEOTOARATE    ,");
				insetSql.append("  NEXCHANGEOTOBRATE    ,");
				insetSql.append("  NMNY                 ,");
				insetSql.append("  NNORWASTASTNUM       ,");
				insetSql.append("  NNORWASTNUM          ,");
				insetSql.append("  NNUM                 ,");
				insetSql.append("  NORGQUOTECURPRICE    ,");
				insetSql.append("  NORGQUOTECURTAXPRICE ,");
				insetSql.append("  NORIGINALCURMNY      ,");
				insetSql.append("  NORIGINALCURPRICE    ,");
				insetSql.append("  NORIGINALCURSUMMNY   ,");
				insetSql.append("  NORIGINALCURTAXMNY   ,");
				insetSql.append("  NORIGINALCURTAXPRICE ,");
				insetSql.append("  NORISIGNMNY          ,");
				insetSql.append("  NORISIGNPRICE        ,");
				insetSql.append("  NORISIGNSUMMNY       ,");
				insetSql.append("  NORISIGNTAXMNY       ,");
				insetSql.append("  NORISIGNTAXPRICE     ,");
				insetSql.append("  NOUTASTNUM           ,");
				insetSql.append("  NOUTBACKASTNUM       ,");
				insetSql.append("  NOUTBACKNUM          ,");
				insetSql.append("  NOUTNUM              ,");
				insetSql.append("  NOUTSIGNASTNUM       ,");
				insetSql.append("  NOUTSIGNNUM          ,");
				insetSql.append("  NPRICE               ,");
				insetSql.append("  NQUOTEPRICE          ,");
				insetSql.append("  NQUOTETAXPRICE       ,");
				insetSql.append("  NQUOTEUNITNUM        ,");
				insetSql.append("  NQUOTEUNITRATE       ,");
				insetSql.append("  NSALESETTLEMNY       ,");
				insetSql.append("  NSALESETTLENUM       ,");
				insetSql.append("  NSIGNEXCHGOTOARATE   ,");
				insetSql.append("  NSIGNEXCHGOTOBRATE   ,");
				insetSql.append("  NSIGNMNY             ,");
				insetSql.append("  NSIGNPRICE           ,");
				insetSql.append("  NSIGNSUMMNY          ,");
				insetSql.append("  NSIGNTAXMNY          ,");
				insetSql.append("  NSIGNTAXPRICE        ,");
				insetSql.append("  NSIGNTAXRATE         ,");
				insetSql.append("  NSUMMNY              ,");
				insetSql.append("  NTAXMNY              ,");
				insetSql.append("  NTAXPRICE            ,");
				insetSql.append("  NTAXRATE             ,");
				insetSql.append("  NTOSETTLEMNY1        ,");
				insetSql.append("  NTOSETTLEMNY2        ,");
				insetSql.append("  NTOSETTLENUM1        ,");
				insetSql.append("  NTOSETTLENUM2        ,");
				insetSql.append("  PK_CORP              ,");
				insetSql.append("  TS                   ,");
				insetSql.append("  VBATCHCODE           ,");
				insetSql.append("  VBILLTYPEU8RM        ,");
				insetSql.append("  VBODYDEF1            ,");
				insetSql.append("  VBODYDEF10           ,");
				insetSql.append("  VBODYDEF11           ,");
				insetSql.append("  VBODYDEF12           ,");
				insetSql.append("  VBODYDEF13           ,");
				insetSql.append("  VBODYDEF14           ,");
				insetSql.append("  VBODYDEF15           ,");
				insetSql.append("  VBODYDEF16           ,");
				insetSql.append("  VBODYDEF17           ,");
				insetSql.append("  VBODYDEF18           ,");
				insetSql.append("  VBODYDEF19           ,");
				insetSql.append("  VBODYDEF2            ,");
				insetSql.append("  VBODYDEF20           ,");
				insetSql.append("  VBODYDEF3            ,");
				insetSql.append("  VBODYDEF4            ,");
				insetSql.append("  VBODYDEF5            ,");
				insetSql.append("  VBODYDEF6            ,");
				insetSql.append("  VBODYDEF7            ,");
				insetSql.append("  VBODYDEF8            ,");
				insetSql.append("  VBODYDEF9            ,");
				insetSql.append("  VFIRSTBILLCODE       ,");
				insetSql.append("  VFIRSTROWNO          ,");
				insetSql.append("  VFREE1               ,");
				insetSql.append("  VFREE2               ,");
				insetSql.append("  VFREE3               ,");
				insetSql.append("  VFREE4               ,");
				insetSql.append("  VFREE5               ,");
				insetSql.append("  VMEMO                ,");
				insetSql.append("  VSOURCEBILLCODE      ,");
				insetSql.append("  VSOURCEROWNO      ,       ");
				insetSql.append("  DR      ) values (        ");
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
//						System.out.println(insetSql);
					}
					try {
						//执行存入数据
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
			System.out.println("途损单辅表增量数据抽取完毕");
			System.out.println("结束时间为"+new Timestamp(new Date().getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("途损单辅表NC connection close");
			BaseDao.closeAll(pstNC, restNC, conNC);
			System.out.println("途损单辅表NC connection closed");
			
			System.out.println("途损单辅表BQ connection close");
			BaseDao.closeAll(pstBQ, restBQ, conBQ);
			System.out.println("途损单辅表BQ connection closed");
		}

	}
}
