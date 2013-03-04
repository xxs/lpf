package com.tend.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 退货申请单辅表增量数据抽取
 * 
 * @author Administrator
 * 
 */
public class DayZLNCtoBQSenderBySoApplyB extends BaseDao implements Runnable {

	public DayZLNCtoBQSenderBySoApplyB() {
		System.out.println("退货申请单辅表增量数据抽取--无参构造函数");
	}

	public void run() {
		try {
			DeleteDate();// 清空ods表数据
			NCtoBQ();// 数据抽取
			System.out.println("退货申请单辅表增量数据抽取完成");
		} catch (Exception e) {
			System.out.println("退货申请单辅表抽取数据异常");
			e.printStackTrace();
		}
	}

	/**
	 * 清空表数据
	 * 
	 * @throws Exception
	 */
	public void DeleteDate() throws Exception {
		String sql = "delete ods_so_apply_b";
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
		System.out.println("退货申请单辅取数据表抽................");
		System.out.println("开始时间为" + new Timestamp(new Date().getTime()));
		try {
			System.out.println("退货申请单辅表获取连接");
			conNC = this.getConForNC();
			conBQ = this.getConForBQ();
			System.out.println("退货申请单辅表获取连接成功");
			StringBuilder sql = new StringBuilder();
			sql.append("select ");
			sql.append("  BCONFIRM                ,");
			sql.append("  BDERICTTRANS            ,");
			sql.append("  BIFINVFINISH            ,");
			sql.append("  BIFTAKEFINISH           ,");
			sql.append("  BLARGESSFLAG            ,");
			sql.append("  BRETURNPROFIT           ,");
			sql.append("  BROWFINISH              ,");
			sql.append("  BSAFEPRICE              ,");
			sql.append("  CBATCHID                ,");
			sql.append("  CBODYWAREHOUSEID        ,");
			sql.append("  CCALBODYID              ,");
			sql.append("  CCHANTYPEID             ,");
			sql.append("  CCONSIGNCORPID          ,");
			sql.append("  CCURRENCYTYPEID         ,");
			sql.append("  CFIRSTBILLBID           ,");
			sql.append("  CFIRSTBILLHID           ,");
			sql.append("  CFIRSTTYPE              ,");
			sql.append("  CINVBASDOCID            ,");
			sql.append("  CINVENTORYID            ,");
			sql.append("  CPACKUNITID             ,");
			sql.append("  CPRICECALPROC           ,");
			sql.append("  CPRICEITEMID            ,");
			sql.append("  CPRICEITEMTABLE         ,");
			sql.append("  CPRICEPOLICYID          ,");
			sql.append("  CPRICETYPEID            ,");
			sql.append("  CQUOTEUNITID            ,");
			sql.append("  CRECCALBODYID           ,");
			sql.append("  CRECEIPTAREAID          ,");
			sql.append("  CRECEIPTCORPID          ,");
			sql.append("  CRECWAREID              ,");
			sql.append("  CROWNO                  ,");
			sql.append("  CSOURCEBILLBODYID       ,");
			sql.append("  CSOURCEBILLID           ,");
			sql.append("  CSOURCEBILLTYPE         ,");
			sql.append("  CUNITID                 ,");
			sql.append("  FROWNOTE                ,");
			sql.append("  FROWSTATUS              ,");
			sql.append("  NDISCOUNTMNY            ,");
			sql.append("  NDISCOUNTRATE           ,");
			sql.append("  NEXCHANGEOTOBRATE       ,");
			sql.append("  NFEEDBACKNUM            ,");
			sql.append("  NINNUMBER               ,");
			sql.append("  NINPACKNUMBER           ,");
			sql.append("  NITEMDISCOUNTRATE       ,");
			sql.append("  NMNY                    ,");
			sql.append("  NNETPRICE               ,");
			sql.append("  NNUMBER                 ,");
			sql.append("  NORGQTNETPRC            ,");
			sql.append("  NORGQTPRC               ,");
			sql.append("  NORGQTTAXNETPRC         ,");
			sql.append("  NORGQTTAXPRC            ,");
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
			sql.append("  NQTNETPRC               ,");
			sql.append("  NQTORGPRC               ,");
			sql.append("  NQTORGTAXPRC            ,");
			sql.append("  NQTPRC                  ,");
			sql.append("  NQTTAXNETPRC            ,");
			sql.append("  NQTTAXPRC               ,");
			sql.append("  NQUOTEUNITNUM           ,");
			sql.append("  NQUOTEUNITRATE          ,");
			sql.append("  NREFUSENUMBER           ,");
			sql.append("  NREFUSEPACKNUM          ,");
			sql.append("  NRUSHNUM                ,");
			sql.append("  NSUMMNY                 ,");
			sql.append("  NTAKENUMBER             ,");
			sql.append("  NTAKEPACKNUMBER         ,");
			sql.append("  NTALBALANCEMNY          ,");
			sql.append("  NTAXMNY                 ,");
			sql.append("  NTAXNETPRICE            ,");
			sql.append("  NTAXPRICE               ,");
			sql.append("  NTAXRATE                ,");
			sql.append("  NTOTALBALANCENUMBER     ,");
			sql.append("  NTOTALSHOULDINNUM       ,");
			sql.append("  NTOTLBALCOSTNUM         ,");
			sql.append("  PK_APPLY                ,");
			sql.append("  PK_APPLY_B              ,");
			sql.append("  PK_CORP                 ,");
			sql.append("  PK_INVCLS               ,");
			sql.append("  PK_PRODUCTLINE          ,");
			sql.append("  PK_RETURNPOLICY         ,");
			sql.append("  PK_RETURNREASON         ,");
			sql.append("  TS                      ,");
			sql.append("  VDEF1                   ,");
			sql.append("  VDEF10                  ,");
			sql.append("  VDEF11                  ,");
			sql.append("  VDEF12                  ,");
			sql.append("  VDEF13                  ,");
			sql.append("  VDEF14                  ,");
			sql.append("  VDEF15                  ,");
			sql.append("  VDEF16                  ,");
			sql.append("  VDEF17                  ,");
			sql.append("  VDEF18                  ,");
			sql.append("  VDEF19                  ,");
			sql.append("  VDEF2                   ,");
			sql.append("  VDEF20                  ,");
			sql.append("  VDEF3                   ,");
			sql.append("  VDEF4                   ,");
			sql.append("  VDEF5                   ,");
			sql.append("  VDEF6                   ,");
			sql.append("  VDEF7                   ,");
			sql.append("  VDEF8                   ,");
			sql.append("  VDEF9                   ,");
			sql.append("  VFIRSTCODE              ,");
			sql.append("  VFREE1                  ,");
			sql.append("  VFREE2                  ,");
			sql.append("  VFREE3                  ,");
			sql.append("  VFREE4                  ,");
			sql.append("  VFREE5                  ,");
			sql.append("  VRECEIVEADDRESS         ,");
			sql.append("  VRETURNMODE             ,");
			sql.append("  VSERIALCODE             ,");
			sql.append("  VSOURCECODE             ,");
			sql.append("  DR                       ");
			sql.append("  from so_apply_b ayb");
			sql.append("  where ayb.pk_apply in (select ay.pk_apply");
			sql.append("  from so_apply ay");
			sql.append("  where ay.dbilldate >=to_char((sysdate - ").append(
					this.getDays() + "),'yyyy-mm-dd')");
			sql.append("  and substr(ay.ts,1,10)>=to_char((sysdate - ").append(
					this.getBeforedays() + "),'yyyy-mm-dd')");
			sql.append("  and ay.pk_corp != '1020'");
			sql.append("  and ay.pk_corp != '1021'");
			sql.append("  and ay.pk_corp != '1023'");
			sql.append("  and ay.pk_corp != '1024'");
			sql.append("  and ay.pk_corp != '1032' )");

			// System.out.println("查询sql:"+sql);
			pstNC = conNC.prepareStatement(sql.toString());
			restNC = pstNC.executeQuery();
			ResultSetMetaData rsmd = restNC.getMetaData();
			int resultcount = rsmd.getColumnCount();
			int tm = 0;
			while (restNC.next()) {
				StringBuilder insetSql = new StringBuilder();
				insetSql.append("insert into ODS_SO_APPLY_B ( ");
				insetSql.append("  BCONFIRM                ,");
				insetSql.append("  BDERICTTRANS            ,");
				insetSql.append("  BIFINVFINISH            ,");
				insetSql.append("  BIFTAKEFINISH           ,");
				insetSql.append("  BLARGESSFLAG            ,");
				insetSql.append("  BRETURNPROFIT           ,");
				insetSql.append("  BROWFINISH              ,");
				insetSql.append("  BSAFEPRICE              ,");
				insetSql.append("  CBATCHID                ,");
				insetSql.append("  CBODYWAREHOUSEID        ,");
				insetSql.append("  CCALBODYID              ,");
				insetSql.append("  CCHANTYPEID             ,");
				insetSql.append("  CCONSIGNCORPID          ,");
				insetSql.append("  CCURRENCYTYPEID         ,");
				insetSql.append("  CFIRSTBILLBID           ,");
				insetSql.append("  CFIRSTBILLHID           ,");
				insetSql.append("  CFIRSTTYPE              ,");
				insetSql.append("  CINVBASDOCID            ,");
				insetSql.append("  CINVENTORYID            ,");
				insetSql.append("  CPACKUNITID             ,");
				insetSql.append("  CPRICECALPROC           ,");
				insetSql.append("  CPRICEITEMID            ,");
				insetSql.append("  CPRICEITEMTABLE         ,");
				insetSql.append("  CPRICEPOLICYID          ,");
				insetSql.append("  CPRICETYPEID            ,");
				insetSql.append("  CQUOTEUNITID            ,");
				insetSql.append("  CRECCALBODYID           ,");
				insetSql.append("  CRECEIPTAREAID          ,");
				insetSql.append("  CRECEIPTCORPID          ,");
				insetSql.append("  CRECWAREID              ,");
				insetSql.append("  CROWNO                  ,");
				insetSql.append("  CSOURCEBILLBODYID       ,");
				insetSql.append("  CSOURCEBILLID           ,");
				insetSql.append("  CSOURCEBILLTYPE         ,");
				insetSql.append("  CUNITID                 ,");
				insetSql.append("  FROWNOTE                ,");
				insetSql.append("  FROWSTATUS              ,");
				insetSql.append("  NDISCOUNTMNY            ,");
				insetSql.append("  NDISCOUNTRATE           ,");
				insetSql.append("  NEXCHANGEOTOBRATE       ,");
				insetSql.append("  NFEEDBACKNUM            ,");
				insetSql.append("  NINNUMBER               ,");
				insetSql.append("  NINPACKNUMBER           ,");
				insetSql.append("  NITEMDISCOUNTRATE       ,");
				insetSql.append("  NMNY                    ,");
				insetSql.append("  NNETPRICE               ,");
				insetSql.append("  NNUMBER                 ,");
				insetSql.append("  NORGQTNETPRC            ,");
				insetSql.append("  NORGQTPRC               ,");
				insetSql.append("  NORGQTTAXNETPRC         ,");
				insetSql.append("  NORGQTTAXPRC            ,");
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
				insetSql.append("  NQTNETPRC               ,");
				insetSql.append("  NQTORGPRC               ,");
				insetSql.append("  NQTORGTAXPRC            ,");
				insetSql.append("  NQTPRC                  ,");
				insetSql.append("  NQTTAXNETPRC            ,");
				insetSql.append("  NQTTAXPRC               ,");
				insetSql.append("  NQUOTEUNITNUM           ,");
				insetSql.append("  NQUOTEUNITRATE          ,");
				insetSql.append("  NREFUSENUMBER           ,");
				insetSql.append("  NREFUSEPACKNUM          ,");
				insetSql.append("  NRUSHNUM                ,");
				insetSql.append("  NSUMMNY                 ,");
				insetSql.append("  NTAKENUMBER             ,");
				insetSql.append("  NTAKEPACKNUMBER         ,");
				insetSql.append("  NTALBALANCEMNY          ,");
				insetSql.append("  NTAXMNY                 ,");
				insetSql.append("  NTAXNETPRICE            ,");
				insetSql.append("  NTAXPRICE               ,");
				insetSql.append("  NTAXRATE                ,");
				insetSql.append("  NTOTALBALANCENUMBER     ,");
				insetSql.append("  NTOTALSHOULDINNUM       ,");
				insetSql.append("  NTOTLBALCOSTNUM         ,");
				insetSql.append("  PK_APPLY                ,");
				insetSql.append("  PK_APPLY_B              ,");
				insetSql.append("  PK_CORP                 ,");
				insetSql.append("  PK_INVCLS               ,");
				insetSql.append("  PK_PRODUCTLINE          ,");
				insetSql.append("  PK_RETURNPOLICY         ,");
				insetSql.append("  PK_RETURNREASON         ,");
				insetSql.append("  TS                      ,");
				insetSql.append("  VDEF1                   ,");
				insetSql.append("  VDEF10                  ,");
				insetSql.append("  VDEF11                  ,");
				insetSql.append("  VDEF12                  ,");
				insetSql.append("  VDEF13                  ,");
				insetSql.append("  VDEF14                  ,");
				insetSql.append("  VDEF15                  ,");
				insetSql.append("  VDEF16                  ,");
				insetSql.append("  VDEF17                  ,");
				insetSql.append("  VDEF18                  ,");
				insetSql.append("  VDEF19                  ,");
				insetSql.append("  VDEF2                   ,");
				insetSql.append("  VDEF20                  ,");
				insetSql.append("  VDEF3                   ,");
				insetSql.append("  VDEF4                   ,");
				insetSql.append("  VDEF5                   ,");
				insetSql.append("  VDEF6                   ,");
				insetSql.append("  VDEF7                   ,");
				insetSql.append("  VDEF8                   ,");
				insetSql.append("  VDEF9                   ,");
				insetSql.append("  VFIRSTCODE              ,");
				insetSql.append("  VFREE1                  ,");
				insetSql.append("  VFREE2                  ,");
				insetSql.append("  VFREE3                  ,");
				insetSql.append("  VFREE4                  ,");
				insetSql.append("  VFREE5                  ,");
				insetSql.append("  VRECEIVEADDRESS         ,");
				insetSql.append("  VRETURNMODE             ,");
				insetSql.append("  VSERIALCODE             ,");
				insetSql.append("  VSOURCECODE          , ");
				insetSql.append("  DR          ) values ( ");
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
						System.out.println("第" + tm + "条B保存成功");
					} else {
						System.out.println("第" + tm + "条B保存失败");
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
			System.out.println("退货申请单辅表增量数据抽取完毕");
			System.out.println("结束时间为" + new Timestamp(new Date().getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("退货申请单辅表NC connection close");
			BaseDao.closeAll(pstNC, restNC, conNC);
			System.out.println("退货申请单辅表NC connection closed");

			System.out.println("退货申请单辅表BQ connection close");
			BaseDao.closeAll(pstBQ, restBQ, conBQ);
			System.out.println("退货申请单辅表BQ connection closed");
		}

	}
}
