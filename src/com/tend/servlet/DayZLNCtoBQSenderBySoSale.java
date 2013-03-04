package com.tend.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 订单增量抽取
 * 
 * @author xxs
 * 
 */
public class DayZLNCtoBQSenderBySoSale extends BaseDao implements Runnable {

	public DayZLNCtoBQSenderBySoSale() {
		System.out.println("订单主表增量数 无参构造函数");
	}

	public void run() {
		try {
			DeleteDate();// 清空ods表数据
			NCtoBQ();// 数据抽取
			System.out.println("订单主表增量数据抽取完成");
		} catch (Exception e) {
			System.out.println("订单主表抽取增量数据异常");
			e.printStackTrace();
		}
	}

	/**
	 * 清空表数据
	 * 
	 * @throws Exception
	 */
	public void DeleteDate() throws Exception {
		String sql = "delete ods_so_sale";
		boolean result = this.excuteDelete(sql);
		if (!result) {
			System.out.println("操作成功");
		} else {
			System.out.println("操作失败");
		}
	}

	/**
	 * 抽取增量数据
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
		System.out.println("订单主表开始抽取增量数据................");
		System.out.println("开始时间为" + new Timestamp(new Date().getTime()));
		try {
			System.out.println("订单主表获取连接");
			conNC = this.getConForNC();
			conBQ = this.getConForBQ();
			System.out.println("订单主表获取连接成功");
			StringBuilder sql = new StringBuilder();
			sql.append("select 				  ");
			sql.append("  BCOOPTOPO          ,");
			sql.append("  BFREECUSTFLAG      ,");
			sql.append("  BINITFLAG          ,");
			sql.append("  BINVOICENDFLAG     ,");
			sql.append("  BOUTENDFLAG        ,");
			sql.append("  BOVERDATE          ,");
			sql.append("  BPAYENDFLAG        ,");
			sql.append("  BPOCOOPTOME        ,");
			sql.append("  BRECEIPTENDFLAG    ,");
			sql.append("  BRETINVFLAG        ,");
			sql.append("  BTRANSENDFLAG      ,");
			sql.append("  CAPPROVEID         ,");
			sql.append("  CBALTYPEID         ,");
			sql.append("  CBIZTYPE           ,");
			sql.append("  CCALBODYID         ,");
			sql.append("  CCOOPPOHID         ,");
			sql.append("  CCREDITNUM         ,");
			sql.append("  CCUSTBANKID        ,");
			sql.append("  CCUSTOMERID        ,");
			sql.append("  CDEPTID            ,");
			sql.append("  CEMPLOYEEID        ,");
			sql.append("  CFREECUSTID        ,");
			sql.append("  COPERATORID        ,");
			sql.append("  CRECEIPTAREAID     ,");
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
			sql.append("  EDITAUTHOR         ,");
			sql.append("  EDITDATE           ,");
			sql.append("  EDITIONNUM         ,");
			sql.append("  FINVOICECLASS      ,");
			sql.append("  FINVOICETYPE       ,");
			sql.append("  FSTATUS            ,");
			sql.append("  IBALANCEFLAG       ,");
			sql.append("  IPRINTCOUNT        ,");
			sql.append("  NACCOUNTPERIOD     ,");
			sql.append("  NDISCOUNTRATE      ,");
			sql.append("  NEVALUATECARRIAGE  ,");
			sql.append("  NHEADSUMMNY        ,");
			sql.append("  NPRECEIVEMNY       ,");
			sql.append("  NPRECEIVERATE      ,");
			sql.append("  NSUBSCRIPTION      ,");
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
			sql.append("  VRECEIPTCODE       ,");
			sql.append("  VRECEIVEADDRESS    ,");
			sql.append("  BISSEND            ,");
			sql.append("  DR            ");
			sql.append("  from so_sale s");
			sql.append("  where s.dbilldate >=to_char((sysdate - ").append(
					this.getDays() + "),'yyyy-mm-dd')");
			sql.append("  and substr(s.ts,1,10)>=to_char((sysdate - ").append(
					this.getBeforedays() + "),'yyyy-mm-dd')");
			sql.append("  and s.pk_corp != '1020'	");
			sql.append("  and s.pk_corp != '1021'	");
			sql.append("  and s.pk_corp != '1023'	");
			sql.append("  and s.pk_corp != '1024'	");
			sql.append("  and s.pk_corp != '1032'	");

			// System.out.println("查询sql:"+sql);
			pstNC = conNC.prepareStatement(sql.toString());
			restNC = pstNC.executeQuery();
			ResultSetMetaData rsmd = restNC.getMetaData();
			int resultcount = rsmd.getColumnCount();
			int tm = 0;
			while (restNC.next()) {
				StringBuilder insetSql = new StringBuilder();
				insetSql.append("insert into ODS_SO_SALE ( 				  ");
				insetSql.append("  BCOOPTOPO          ,");
				insetSql.append("  BFREECUSTFLAG      ,");
				insetSql.append("  BINITFLAG          ,");
				insetSql.append("  BINVOICENDFLAG     ,");
				insetSql.append("  BOUTENDFLAG        ,");
				insetSql.append("  BOVERDATE          ,");
				insetSql.append("  BPAYENDFLAG        ,");
				insetSql.append("  BPOCOOPTOME        ,");
				insetSql.append("  BRECEIPTENDFLAG    ,");
				insetSql.append("  BRETINVFLAG        ,");
				insetSql.append("  BTRANSENDFLAG      ,");
				insetSql.append("  CAPPROVEID         ,");
				insetSql.append("  CBALTYPEID         ,");
				insetSql.append("  CBIZTYPE           ,");
				insetSql.append("  CCALBODYID         ,");
				insetSql.append("  CCOOPPOHID         ,");
				insetSql.append("  CCREDITNUM         ,");
				insetSql.append("  CCUSTBANKID        ,");
				insetSql.append("  CCUSTOMERID        ,");
				insetSql.append("  CDEPTID            ,");
				insetSql.append("  CEMPLOYEEID        ,");
				insetSql.append("  CFREECUSTID        ,");
				insetSql.append("  COPERATORID        ,");
				insetSql.append("  CRECEIPTAREAID     ,");
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
				insetSql.append("  EDITAUTHOR         ,");
				insetSql.append("  EDITDATE           ,");
				insetSql.append("  EDITIONNUM         ,");
				insetSql.append("  FINVOICECLASS      ,");
				insetSql.append("  FINVOICETYPE       ,");
				insetSql.append("  FSTATUS            ,");
				insetSql.append("  IBALANCEFLAG       ,");
				insetSql.append("  IPRINTCOUNT        ,");
				insetSql.append("  NACCOUNTPERIOD     ,");
				insetSql.append("  NDISCOUNTRATE      ,");
				insetSql.append("  NEVALUATECARRIAGE  ,");
				insetSql.append("  NHEADSUMMNY        ,");
				insetSql.append("  NPRECEIVEMNY       ,");
				insetSql.append("  NPRECEIVERATE      ,");
				insetSql.append("  NSUBSCRIPTION      ,");
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
				insetSql.append("  VRECEIPTCODE       ,");
				insetSql.append("  VRECEIVEADDRESS    ,");
				insetSql.append("  BISSEND        , ");
				insetSql.append("  DR        ) values (    ");
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
					// 执行存入增量数据
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
			System.out.println("订单主表增量数据抽取完毕");
			System.out.println("结束时间为" + new Timestamp(new Date().getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("订单主表NC connection close");
			BaseDao.closeAll(pstNC, restNC, conNC);
			System.out.println("订单主表NC connection closed");

			System.out.println("订单主表BQ connection close");
			BaseDao.closeAll(pstBQ, restBQ, conBQ);
			System.out.println("订单主表BQ connection closed");
		}

	}
}
