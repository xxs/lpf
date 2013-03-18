package com.tend.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 预订单增量抽取
 * @author xxs
 *
 */
public class NCtoBQSenderBySoPreorder extends BaseDao implements Runnable {

	public NCtoBQSenderBySoPreorder() {
		System.out.println("预订单主表增量数 无参构造函数");
	}

	/**
	 * 自动执行的run方法
	 */
	public void run() {
		try {
			DateLoop("2013-03-02", "2013-03-05",3);
			System.out.println("预订单主表增量数据抽取完成");
		} catch (Exception e) {
			System.out.println("预订单主表抽取增量数据异常");
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
		System.out.println("预订单主表时间区间："+sdf.format(date1)+" to "+sdf.format(datetemp));
		NCtoBQ(sdf.format(date1), sdf.format(datetemp));
		while(mm.dateCompare(datetemp , date2)){
			datetemp = mm.getDateAfterDay(datetemp, days);
			if(mm.dateCompare(date2  ,datetemp )){
				System.out.println("预订单主表时间区间："+sdf.format(mm.getDateAfterDay(datetemp, -days))+" to "+sdf.format(date2));
				NCtoBQ(sdf.format(mm.getDateAfterDay(datetemp, -days)), sdf.format(date2));
			}else{
				System.out.println("预订单主表时间区间："+sdf.format(mm.getDateAfterDay(datetemp, -days))+" to "+sdf.format(datetemp));
				NCtoBQ(sdf.format(mm.getDateAfterDay(datetemp, -days)), sdf.format(datetemp));
			}
		}
	}
	
	/**
	 * 抽取增量数据
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
		System.out.println("预订单主表开始抽取增量数据................");
		System.out.println("开始时间为"+new Timestamp(new Date().getTime()));
		try {
			System.out.println("预订单主表获取连接");
			conNC = this.getConForNC();
			conBQ = this.getConForBQ();
			System.out.println("预订单主表获取连接成功");
			StringBuilder sql = new StringBuilder();
			sql.append("select");
			sql.append("  CAPPROVEID,");
			sql.append("  CBIZTYPE,");
			sql.append("  CCURRENCYTYPEID,");
			sql.append("  CCUSTOMERID,");
			sql.append("  CDEPTID,");
			sql.append("  CEMPLOYEEID,");
			sql.append("  COPERATORID,");
			sql.append("  CRECADDRNODE,");
			sql.append("  CRECEIPTAREAID,");
			sql.append("  CRECEIPTCORPID,");
			sql.append("  CRECEIPTCUSTOMERID,");
			sql.append("  CRECEIPTTYPE,");
			sql.append("  CSALECORPID,");
			sql.append("  CTERMPROTOCOLID,");
			sql.append("  CTRANSMODEID,");
			sql.append("  CWEBOPERATORID,");
			sql.append("  DABATEDATE,");
			sql.append("  DAPPROVEDATE,");
			sql.append("  DAUDITTIME,");
			sql.append("  DBILLDATE,");
			sql.append("  DBILLTIME,");
			sql.append("  DMAKEDATE,");
			sql.append("  DMODITIME,");
			sql.append("  FSTATUS,");
			sql.append("  NEXCHANGEOTOBRATE,");
			sql.append("  PK_CORP,");
			sql.append("  PK_PREORDER,");
			sql.append("  TS,");
			sql.append("  VDEF1,");
			sql.append("  VDEF10,");
			sql.append("  VDEF11,");
			sql.append("  VDEF12,");
			sql.append("  VDEF13,");
			sql.append("  VDEF14,");
			sql.append("  VDEF15,");
			sql.append("  VDEF16,");
			sql.append("  VDEF17,");
			sql.append("  VDEF18,");
			sql.append("  VDEF19,");
			sql.append("  VDEF2,");
			sql.append("  VDEF20,");
			sql.append("  VDEF3,");
			sql.append("  VDEF4,");
			sql.append("  VDEF5,");
			sql.append("  VDEF6,");
			sql.append("  VDEF7,");
			sql.append("  VDEF8,");
			sql.append("  VDEF9,");
			sql.append("  VNOTE,");
			sql.append("  VRECEIPTCODE,");
			sql.append("  VRECEIVEADDRESS,");
			sql.append("  DR ");
			sql.append("	from so_preorder p");
			sql.append("	where p.ts >= '").append(beginDate+"'");
			sql.append("	and p.ts <= '").append(endDate+"'");
			
//			System.out.println("查询sql:"+sql);
			pstNC = conNC.prepareStatement(sql.toString());
			restNC = pstNC.executeQuery();
			ResultSetMetaData rsmd = restNC.getMetaData();
			int resultcount = rsmd.getColumnCount();
			int tm = 0;
			while(restNC.next()){
				StringBuilder insetSql = new StringBuilder();
				insetSql.append("insert into ODS_SO_PREORDER (");
				insetSql.append("  CAPPROVEID,");
				insetSql.append("  CBIZTYPE,");
				insetSql.append("  CCURRENCYTYPEID,");
				insetSql.append("  CCUSTOMERID,");
				insetSql.append("  CDEPTID,");
				insetSql.append("  CEMPLOYEEID,");
				insetSql.append("  COPERATORID,");
				insetSql.append("  CRECADDRNODE,");
				insetSql.append("  CRECEIPTAREAID,");
				insetSql.append("  CRECEIPTCORPID,");
				insetSql.append("  CRECEIPTCUSTOMERID,");
				insetSql.append("  CRECEIPTTYPE,");
				insetSql.append("  CSALECORPID,");
				insetSql.append("  CTERMPROTOCOLID,");
				insetSql.append("  CTRANSMODEID,");
				insetSql.append("  CWEBOPERATORID,");
				insetSql.append("  DABATEDATE,");
				insetSql.append("  DAPPROVEDATE,");
				insetSql.append("  DAUDITTIME,");
				insetSql.append("  DBILLDATE,");
				insetSql.append("  DBILLTIME,");
				insetSql.append("  DMAKEDATE,");
				insetSql.append("  DMODITIME,");
				insetSql.append("  FSTATUS,");
				insetSql.append("  NEXCHANGEOTOBRATE,");
				insetSql.append("  PK_CORP,");
				insetSql.append("  PK_PREORDER,");
				insetSql.append("  TS,");
				insetSql.append("  VDEF1,");
				insetSql.append("  VDEF10,");
				insetSql.append("  VDEF11,");
				insetSql.append("  VDEF12,");
				insetSql.append("  VDEF13,");
				insetSql.append("  VDEF14,");
				insetSql.append("  VDEF15,");
				insetSql.append("  VDEF16,");
				insetSql.append("  VDEF17,");
				insetSql.append("  VDEF18,");
				insetSql.append("  VDEF19,");
				insetSql.append("  VDEF2,");
				insetSql.append("  VDEF20,");
				insetSql.append("  VDEF3,");
				insetSql.append("  VDEF4,");
				insetSql.append("  VDEF5,");
				insetSql.append("  VDEF6,");
				insetSql.append("  VDEF7,");
				insetSql.append("  VDEF8,");
				insetSql.append("  VDEF9,");
				insetSql.append("  VNOTE,");
				insetSql.append("  VRECEIPTCODE,");
				insetSql.append("  VRECEIVEADDRESS , ");
				insetSql.append("  DR ) values ( ");
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
						//执行存入增量数据
						pstBQ = conBQ.prepareStatement(insetSql.toString());
						boolean result = pstBQ.execute();
						if(!result){
							System.out.println("第"+tm+"条H保存成功");
						}else{
							System.out.println("第"+tm+"条H保存失败");
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
			System.out.println("预订单主表增量数据抽取完毕");
			System.out.println("结束时间为"+new Timestamp(new Date().getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("预订单主表NC connection close");
			BaseDao.closeAll(pstNC, restNC, conNC);
			System.out.println("预订单主表NC connection closed");
			
			System.out.println("预订单主表BQ connection close");
			BaseDao.closeAll(pstBQ, restBQ, conBQ);
			System.out.println("预订单主表BQ connection closed");
		}

	}
}
