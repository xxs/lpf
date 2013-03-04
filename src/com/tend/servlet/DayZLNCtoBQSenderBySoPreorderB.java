package com.tend.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 预订单增量抽取
 * 
 * @author xxs
 * 
 */
public class DayZLNCtoBQSenderBySoPreorderB extends BaseDao implements Runnable {

	public DayZLNCtoBQSenderBySoPreorderB() {
		System.out.println("预订单增量抽取-- 无参构造函数");
	}

	public void run() {
		try {
			DeleteDate();// 清空ods表数据
			NCtoBQ();// 数据抽取
			System.out.println("预订单辅表增量数据抽取完成");
		} catch (Exception e) {
			System.out.println("预订单辅表抽取增量数据异常");
			e.printStackTrace();
		}
	}

	/**
	 * 清空表数据
	 * 
	 * @throws Exception
	 */
	public void DeleteDate() throws Exception {
		String sql = "delete ods_so_preorder_b";
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
		System.out.println("预订单辅表开始抽取增量数据................");
		System.out.println("开始时间为" + new Timestamp(new Date().getTime()));
		try {
			System.out.println("预订单辅表获取连接");
			conNC = this.getConForNC();
			conBQ = this.getConForBQ();
			System.out.println("预订单辅表获取连接成功");
			StringBuilder sql = new StringBuilder();
			sql.append("select ");
			sql.append("  BLARGESSFLAG       ,");
			sql.append("  BLINECLOSE         ,");
			sql.append("  BRETURNPROFIT      ,");
			sql.append("  BSAFEPRICE         ,");
			sql.append("  CARRANGEID         ,");
			sql.append("  CBATCHID           ,");
			sql.append("  CBODYWAREHOUSEID   ,");
			sql.append("  CCALBODYID         ,");
			sql.append("  CCURRENCYTYPEID    ,");
			sql.append("  CINVBASDOCID       ,");
			sql.append("  CINVENTORYID       ,");
			sql.append("  CORDERCORPID       ,");
			sql.append("  CPACKUNITID        ,");
			sql.append("  CPORJECTID         ,");
			sql.append("  CPRICEID           ,");
			sql.append("  CPRICEITEMTABLE    ,");
			sql.append("  CPRICEITEMTABLEID  ,");
			sql.append("  CQUOTEUNITID       ,");
			sql.append("  CRECADDRNODEID     ,");
			sql.append("  CRECEIPTAREAID     ,");
			sql.append("  CRECEIPTCORPID     ,");
			sql.append("  CROWNO             ,");
			sql.append("  CSINVENTORYID      ,");
			sql.append("  CSOURCEBILLBODYID  ,");
			sql.append("  CSOURCEBILLID      ,");
			sql.append("  CSOURCEBILLTYPE    ,");
			sql.append("  CUNITID            ,");
			sql.append("  DARRDATE           ,");
			sql.append("  DRECEIVEDATE       ,");
			sql.append("  DRECEIVETIME       ,");
			sql.append("  DSENDDATE          ,");
			sql.append("  DSENDTIME          ,");
			sql.append("  FROWNOTE           ,");
			sql.append("  NARRNUM            ,");
			sql.append("  NDISCOUNTMNY       ,");
			sql.append("  NDISCOUNTRATE      ,");
			sql.append("  NEXCHANGEOTOBRATE  ,");
			sql.append("  NITEMDISCOUNTRATE  ,");
			sql.append("  NMNY               ,");
			sql.append("  NNETPRICE          ,");
			sql.append("  NNUMBER            ,");
			sql.append("  NORGNLCURDISCNTMNY ,");
			sql.append("  NORGNLCURMNY       ,");
			sql.append("  NORGNLCURNETPRICE  ,");
			sql.append("  NORGNLCURSUMMNY    ,");
			sql.append("  NORGNLCURTAXMNY    ,");
			sql.append("  NORGNLCURTAXNETPRC ,");
			sql.append("  NORGNLCURTAXPRICE  ,");
			sql.append("  NORGQTNETPRC       ,");
			sql.append("  NORGQTPRC          ,");
			sql.append("  NORGQTTAXNETPRC    ,");
			sql.append("  NORGQTTAXPRC       ,");
			sql.append("  NORIGINALCURPRICE  ,");
			sql.append("  NPACKNUMBER        ,");
			sql.append("  NPRICE             ,");
			sql.append("  NQTNETPRC          ,");
			sql.append("  NQTORGNETPRC       ,");
			sql.append("  NQTORGPRC          ,");
			sql.append("  NQTORGTAXNETPRC    ,");
			sql.append("  NQTORGTAXPRC       ,");
			sql.append("  NQTPRC             ,");
			sql.append("  NQTTAXNETPRC       ,");
			sql.append("  NQTTAXPRC          ,");
			sql.append("  NQUOTEUNITNUM      ,");
			sql.append("  NQUOTEUNITRATE     ,");
			sql.append("  NSUMMNY            ,");
			sql.append("  NTAXMNY            ,");
			sql.append("  NTAXNETPRICE       ,");
			sql.append("  NTAXPRICE          ,");
			sql.append("  NTAXRATE           ,");
			sql.append("  PK_CORP            ,");
			sql.append("  PK_PREORDER        ,");
			sql.append("  PK_PREORDER_B      ,");
			sql.append("  PK_RETURNREASON    ,");
			sql.append("  TS                 ,");
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
			sql.append("  VFREE1             ,");
			sql.append("  VFREE2             ,");
			sql.append("  VFREE3             ,");
			sql.append("  VFREE4             ,");
			sql.append("  VFREE5             ,");
			sql.append("  VPRICECALPROC      ,");
			sql.append("  VRECEIVEADDRESS    ,");
			sql.append("  VRETURNMODE        ,");
			sql.append("  VSOURCECODE        ,");
			sql.append("  DR        ");
			sql.append("  FROM  so_preorder_b pb");
			sql.append("  where pb.pk_preorder in (select p.pk_preorder");
			sql.append("  from so_preorder p");
			sql.append("  where p.dbilldate >=to_char((sysdate - ").append(
					this.getDays() + "),'yyyy-mm-dd')");
			sql.append("  and substr(p.ts,1,10)>=to_char((sysdate - ").append(
					this.getBeforedays() + "),'yyyy-mm-dd')");
			sql.append("  ) ");

			// System.out.println("查询sql:"+sql);
			pstNC = conNC.prepareStatement(sql.toString());
			restNC = pstNC.executeQuery();
			ResultSetMetaData rsmd = restNC.getMetaData();
			int resultcount = rsmd.getColumnCount();
			int tm = 0;
			while (restNC.next()) {
				StringBuilder insetSql = new StringBuilder();
				insetSql.append("insert into ODS_SO_PREORDER_B ( ");
				insetSql.append("  BLARGESSFLAG       ,");
				insetSql.append("  BLINECLOSE         ,");
				insetSql.append("  BRETURNPROFIT      ,");
				insetSql.append("  BSAFEPRICE         ,");
				insetSql.append("  CARRANGEID         ,");
				insetSql.append("  CBATCHID           ,");
				insetSql.append("  CBODYWAREHOUSEID   ,");
				insetSql.append("  CCALBODYID         ,");
				insetSql.append("  CCURRENCYTYPEID    ,");
				insetSql.append("  CINVBASDOCID       ,");
				insetSql.append("  CINVENTORYID       ,");
				insetSql.append("  CORDERCORPID       ,");
				insetSql.append("  CPACKUNITID        ,");
				insetSql.append("  CPORJECTID         ,");
				insetSql.append("  CPRICEID           ,");
				insetSql.append("  CPRICEITEMTABLE    ,");
				insetSql.append("  CPRICEITEMTABLEID  ,");
				insetSql.append("  CQUOTEUNITID       ,");
				insetSql.append("  CRECADDRNODEID     ,");
				insetSql.append("  CRECEIPTAREAID     ,");
				insetSql.append("  CRECEIPTCORPID     ,");
				insetSql.append("  CROWNO             ,");
				insetSql.append("  CSINVENTORYID      ,");
				insetSql.append("  CSOURCEBILLBODYID  ,");
				insetSql.append("  CSOURCEBILLID      ,");
				insetSql.append("  CSOURCEBILLTYPE    ,");
				insetSql.append("  CUNITID            ,");
				insetSql.append("  DARRDATE           ,");
				insetSql.append("  DRECEIVEDATE       ,");
				insetSql.append("  DRECEIVETIME       ,");
				insetSql.append("  DSENDDATE          ,");
				insetSql.append("  DSENDTIME          ,");
				insetSql.append("  FROWNOTE           ,");
				insetSql.append("  NARRNUM            ,");
				insetSql.append("  NDISCOUNTMNY       ,");
				insetSql.append("  NDISCOUNTRATE      ,");
				insetSql.append("  NEXCHANGEOTOBRATE  ,");
				insetSql.append("  NITEMDISCOUNTRATE  ,");
				insetSql.append("  NMNY               ,");
				insetSql.append("  NNETPRICE          ,");
				insetSql.append("  NNUMBER            ,");
				insetSql.append("  NORGNLCURDISCNTMNY ,");
				insetSql.append("  NORGNLCURMNY       ,");
				insetSql.append("  NORGNLCURNETPRICE  ,");
				insetSql.append("  NORGNLCURSUMMNY    ,");
				insetSql.append("  NORGNLCURTAXMNY    ,");
				insetSql.append("  NORGNLCURTAXNETPRC ,");
				insetSql.append("  NORGNLCURTAXPRICE  ,");
				insetSql.append("  NORGQTNETPRC       ,");
				insetSql.append("  NORGQTPRC          ,");
				insetSql.append("  NORGQTTAXNETPRC    ,");
				insetSql.append("  NORGQTTAXPRC       ,");
				insetSql.append("  NORIGINALCURPRICE  ,");
				insetSql.append("  NPACKNUMBER        ,");
				insetSql.append("  NPRICE             ,");
				insetSql.append("  NQTNETPRC          ,");
				insetSql.append("  NQTORGNETPRC       ,");
				insetSql.append("  NQTORGPRC          ,");
				insetSql.append("  NQTORGTAXNETPRC    ,");
				insetSql.append("  NQTORGTAXPRC       ,");
				insetSql.append("  NQTPRC             ,");
				insetSql.append("  NQTTAXNETPRC       ,");
				insetSql.append("  NQTTAXPRC          ,");
				insetSql.append("  NQUOTEUNITNUM      ,");
				insetSql.append("  NQUOTEUNITRATE     ,");
				insetSql.append("  NSUMMNY            ,");
				insetSql.append("  NTAXMNY            ,");
				insetSql.append("  NTAXNETPRICE       ,");
				insetSql.append("  NTAXPRICE          ,");
				insetSql.append("  NTAXRATE           ,");
				insetSql.append("  PK_CORP            ,");
				insetSql.append("  PK_PREORDER        ,");
				insetSql.append("  PK_PREORDER_B      ,");
				insetSql.append("  PK_RETURNREASON    ,");
				insetSql.append("  TS                 ,");
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
				insetSql.append("  VFREE1             ,");
				insetSql.append("  VFREE2             ,");
				insetSql.append("  VFREE3             ,");
				insetSql.append("  VFREE4             ,");
				insetSql.append("  VFREE5             ,");
				insetSql.append("  VPRICECALPROC      ,");
				insetSql.append("  VRECEIVEADDRESS    ,");
				insetSql.append("  VRETURNMODE        ,");
				insetSql.append("  VSOURCECODE       , ");
				insetSql.append("  DR       ) values ( ");
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
			System.out.println("预订单辅表增量数据抽取完毕");
			System.out.println("结束时间为" + new Timestamp(new Date().getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("预订单辅表NC connection close");
			BaseDao.closeAll(pstNC, restNC, conNC);
			System.out.println("预订单辅表NC connection closed");

			System.out.println("预订单辅表BQ connection close");
			BaseDao.closeAll(pstBQ, restBQ, conBQ);
			System.out.println("预订单辅表BQ connection closed");
		}

	}
}
