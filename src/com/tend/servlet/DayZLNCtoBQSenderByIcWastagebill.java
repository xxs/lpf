package com.tend.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 途损单主表增量数据抽取
 * 
 * @author Administrator
 * 
 */
public class DayZLNCtoBQSenderByIcWastagebill extends BaseDao implements
		Runnable {

	public DayZLNCtoBQSenderByIcWastagebill() {
		System.out.println("途损单主表增量数据抽取--无参构造函数");
	}

	public void run() {
		try {
			DeleteDate();// 清空ods表数据
			NCtoBQ();// 数据抽取
			System.out.println("途损单主表增量数据抽取完成");
		} catch (Exception e) {
			System.out.println("途损单主表抽取数据异常");
			e.printStackTrace();
		}
	}

	/**
	 * 清空表数据
	 * 
	 * @throws Exception
	 */
	public void DeleteDate() throws Exception {
		String sql = "delete ods_ic_wastagebill";
		boolean result = this.excuteDelete(sql);
		if (!result) {
			System.out.println("操作成功");
		} else {
			System.out.println("操作失败");
		}
	}

	/**
	 * 抽取数据
	 * 
	 * @throws Exception
	 */
	public void NCtoBQ() throws Exception {
		Connection conNC = null;
		PreparedStatement pstNC = null;
		ResultSet restNC = null;
		Connection conBQ = null;
		PreparedStatement pstBQ = null;
		ResultSet restBQ = null;
		System.out.println("途损单辅取数据表抽................");
		System.out.println("开始时间为" + new Timestamp(new Date().getTime()));
		try {
			System.out.println("途损单主表获取连接");
			conNC = this.getConForNC();
			conBQ = this.getConForBQ();
			System.out.println("途损单主表获取连接成功");
			StringBuilder sql = new StringBuilder();
			sql.append("select ");
			sql.append("  CAPPROVERID       ,");
			sql.append("  CBILLTYPECODE     ,");
			sql.append("  CCALBODYID        ,");
			sql.append("  CCURRENCYTYPEID   ,");
			sql.append("  CCUSTBASID        ,");
			sql.append("  CCUSTOMERID       ,");
			sql.append("  CDEPTID           ,");
			sql.append("  CDILIVERTYPEID    ,");
			sql.append("  CDISPATCHERID     ,");
			sql.append("  CEMPLOYEEID       ,");
			sql.append("  CINCALBODYID      ,");
			sql.append("  CINCORPID         ,");
			sql.append("  CINWHID           ,");
			sql.append("  CLASTOPERATORID   ,");
			sql.append("  COPERATORID       ,");
			sql.append("  COUTCALBODYID     ,");
			sql.append("  COUTCORPID        ,");
			sql.append("  COUTWHID          ,");
			sql.append("  CSIGNCURRENCYID   ,");
			sql.append("  CWASTAGEBILLID    ,");
			sql.append("  CWHID             ,");
			sql.append("  DAPPROVEDATE      ,");
			sql.append("  DBILLDATE         ,");
			sql.append("  DLASTMAKETIME     ,");
			sql.append("  FSTATUSFLAG       ,");
			sql.append("  FWASTDUTYFLAG     ,");
			sql.append("  FWASTPARTFLAG     ,");
			sql.append("  IPRINTCOUNT       ,");
			sql.append("  NEXCHANGEOTOARATE ,");
			sql.append("  NEXCHANGEOTOBRATE ,");
			sql.append("  PK_CORP           ,");
			sql.append("  TACCOUNTTIME      ,");
			sql.append("  TMAKETIME         ,");
			sql.append("  TS                ,");
			sql.append("  VBILLCODE         ,");
			sql.append("  VDEF1             ,");
			sql.append("  VDEF10            ,");
			sql.append("  VDEF11            ,");
			sql.append("  VDEF12            ,");
			sql.append("  VDEF13            ,");
			sql.append("  VDEF14            ,");
			sql.append("  VDEF15            ,");
			sql.append("  VDEF16            ,");
			sql.append("  VDEF17            ,");
			sql.append("  VDEF18            ,");
			sql.append("  VDEF19            ,");
			sql.append("  VDEF2             ,");
			sql.append("  VDEF20            ,");
			sql.append("  VDEF3             ,");
			sql.append("  VDEF4             ,");
			sql.append("  VDEF5             ,");
			sql.append("  VDEF6             ,");
			sql.append("  VDEF7             ,");
			sql.append("  VDEF8             ,");
			sql.append("  VDEF9             ,");
			sql.append("  VMEMO             ,");
			sql.append("  DR                ");
			sql.append("  from ic_wastagebill w");
			sql.append("  where w.dbilldate >=to_char((sysdate - ").append(
					this.getDays() + "),'yyyy-mm-dd')");
			sql.append("  and substr(w.ts,1,10)>=to_char((sysdate - ").append(
					this.getBeforedays() + "),'yyyy-mm-dd')");
			sql.append("  and w.pk_corp != '1020'");
			sql.append("  and w.pk_corp != '1021'");
			sql.append("  and w.pk_corp != '1023'");
			sql.append("  and w.pk_corp != '1024'");
			sql.append("  and w.pk_corp != '1032'");

			// System.out.println("查询sql:"+sql);
			pstNC = conNC.prepareStatement(sql.toString());
			restNC = pstNC.executeQuery();
			ResultSetMetaData rsmd = restNC.getMetaData();
			int resultcount = rsmd.getColumnCount();
			int tm = 0;
			while (restNC.next()) {
				StringBuilder insetSql = new StringBuilder();
				insetSql.append("insert into ODS_IC_WASTAGEBILL (  ");
				insetSql.append("  CAPPROVERID       ,");
				insetSql.append("  CBILLTYPECODE     ,");
				insetSql.append("  CCALBODYID        ,");
				insetSql.append("  CCURRENCYTYPEID   ,");
				insetSql.append("  CCUSTBASID        ,");
				insetSql.append("  CCUSTOMERID       ,");
				insetSql.append("  CDEPTID           ,");
				insetSql.append("  CDILIVERTYPEID    ,");
				insetSql.append("  CDISPATCHERID     ,");
				insetSql.append("  CEMPLOYEEID       ,");
				insetSql.append("  CINCALBODYID      ,");
				insetSql.append("  CINCORPID         ,");
				insetSql.append("  CINWHID           ,");
				insetSql.append("  CLASTOPERATORID   ,");
				insetSql.append("  COPERATORID       ,");
				insetSql.append("  COUTCALBODYID     ,");
				insetSql.append("  COUTCORPID        ,");
				insetSql.append("  COUTWHID          ,");
				insetSql.append("  CSIGNCURRENCYID   ,");
				insetSql.append("  CWASTAGEBILLID    ,");
				insetSql.append("  CWHID             ,");
				insetSql.append("  DAPPROVEDATE      ,");
				insetSql.append("  DBILLDATE         ,");
				insetSql.append("  DLASTMAKETIME     ,");
				insetSql.append("  FSTATUSFLAG       ,");
				insetSql.append("  FWASTDUTYFLAG     ,");
				insetSql.append("  FWASTPARTFLAG     ,");
				insetSql.append("  IPRINTCOUNT       ,");
				insetSql.append("  NEXCHANGEOTOARATE ,");
				insetSql.append("  NEXCHANGEOTOBRATE ,");
				insetSql.append("  PK_CORP           ,");
				insetSql.append("  TACCOUNTTIME      ,");
				insetSql.append("  TMAKETIME         ,");
				insetSql.append("  TS                ,");
				insetSql.append("  VBILLCODE         ,");
				insetSql.append("  VDEF1             ,");
				insetSql.append("  VDEF10            ,");
				insetSql.append("  VDEF11            ,");
				insetSql.append("  VDEF12            ,");
				insetSql.append("  VDEF13            ,");
				insetSql.append("  VDEF14            ,");
				insetSql.append("  VDEF15            ,");
				insetSql.append("  VDEF16            ,");
				insetSql.append("  VDEF17            ,");
				insetSql.append("  VDEF18            ,");
				insetSql.append("  VDEF19            ,");
				insetSql.append("  VDEF2             ,");
				insetSql.append("  VDEF20            ,");
				insetSql.append("  VDEF3             ,");
				insetSql.append("  VDEF4             ,");
				insetSql.append("  VDEF5             ,");
				insetSql.append("  VDEF6             ,");
				insetSql.append("  VDEF7             ,");
				insetSql.append("  VDEF8             ,");
				insetSql.append("  VDEF9             ,");
				insetSql.append("  VMEMO     ,        ");
				insetSql.append("  DR     ) values (        ");
				for (int i = 1; i <= resultcount; i++) {

					if (rsmd.getColumnType(i) == 1
							|| rsmd.getColumnType(i) == 12) {
						if (null == restNC.getString(i)
								|| restNC.getString(i).isEmpty()) {
							insetSql.append("''");
						} else {
							insetSql.append("'").append(restNC.getString(i))
									.append("'");
						}
						if (i < resultcount) {
							insetSql.append(",");
						}
					} else {
						insetSql.append(restNC.getDouble(i));
						if (i < resultcount) {
							insetSql.append(",");
						}
					}
				}
				insetSql.append(")");
				if (tm == 0) {
					// System.out.println(insetSql);
				}
				try {
					// 执行存入数据
					pstBQ = conBQ.prepareStatement(insetSql.toString());
					boolean result = pstBQ.execute();
					if (!result) {
						System.out.println("第" + tm + "条H保存成功");
					} else {
						System.out.println("第" + tm + "条H保存失败");
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (pstBQ != null) {
							pstBQ.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				tm++;
			}
			System.out.println("途损单主表增量数据抽取完毕");
			System.out.println("结束时间为" + new Timestamp(new Date().getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("途损单主表NC connection close");
			BaseDao.closeAll(pstNC, restNC, conNC);
			System.out.println("途损单主表NC connection closed");

			System.out.println("途损单主表BQ connection close");
			BaseDao.closeAll(pstBQ, restBQ, conBQ);
			System.out.println("途损单主表BQ connection closed");
		}

	}
}
