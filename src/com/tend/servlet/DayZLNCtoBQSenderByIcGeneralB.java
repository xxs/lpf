package com.tend.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 出入库单辅表增量数据抽取
 * 
 * @author Administrator
 * 
 */
public class DayZLNCtoBQSenderByIcGeneralB extends BaseDao implements Runnable {

	public DayZLNCtoBQSenderByIcGeneralB() {
		System.out.println("出入库单辅表增量数据抽取--无参构造函数");
	}

	/**
	 * 自动执行的run方法
	 */
	public void run() {
		try {
			DeleteDate();// 清空ods表数据
			NCtoBQ();// 数据抽取
			System.out.println("出入库单辅表增量数据抽取完成");
		} catch (Exception e) {
			System.out.println("出入库单辅表抽取数据异常");
			e.printStackTrace();
		}
	}

	/**
	 * 清空表数据
	 * 
	 * @throws Exception
	 */
	public void DeleteDate() throws Exception {
		String sql = "delete ods_ic_general_b";
		boolean result = this.excuteDelete(sql);
		if (!result) {
			System.out.println("操作成功");
		} else {
			System.out.println("操作失败");
		}
	}

	/**
	 * 检测动态的天数 天数和轮询的天数在web.xml中配置
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
		System.out.println("出入库单辅取数据表抽................");
		System.out.println("开始时间为" + new Timestamp(new Date().getTime()));
		try {
			System.out.println("出入库单辅表获取连接");
			conNC = this.getConForNC();
			conBQ = this.getConForBQ();
			System.out.println("出入库单辅表获取连接成功");
			StringBuilder sql = new StringBuilder();
			sql.append("select 				   ");
			sql.append("  BBARCODECLOSE       ,");
			sql.append("  BONROADFLAG         ,");
			sql.append("  BRETURNPROFIT       ,");
			sql.append("  BSAFEPRICE          ,");
			sql.append("  BSOURCELARGESS      ,");
			sql.append("  BSUPPLYFLAG         ,");
			sql.append("  BTOINZGFLAG         ,");
			sql.append("  BTOOUTTOIAFLAG      ,");
			sql.append("  BTOOUTZGFLAG        ,");
			sql.append("  BTOU8RM             ,");
			sql.append("  BZGFLAG             ,");
			sql.append("  BZGYFFLAG           ,");
			sql.append("  CASTUNITID          ,");
			sql.append("  CBODYBILLTYPECODE   ,");
			sql.append("  CBODYWAREHOUSEID    ,");
			sql.append("  CCHECKSTATEID       ,");
			sql.append("  CCORRESPONDBID      ,");
			sql.append("  CCORRESPONDCODE     ,");
			sql.append("  CCORRESPONDHID      ,");
			sql.append("  CCORRESPONDTYPE     ,");
			sql.append("  CCOSTOBJECT         ,");
			sql.append("  CFIRSTBILLBID       ,");
			sql.append("  CFIRSTBILLHID       ,");
			sql.append("  CFIRSTTYPE          ,");
			sql.append("  CFREEZEID           ,");
			sql.append("  CGENERALBID         ,");
			sql.append("  CGENERALHID         ,");
			sql.append("  CINVBASID           ,");
			sql.append("  CINVENTORYID        ,");
			sql.append("  CORDER_BB1ID        ,");
			sql.append("  CPARENTID           ,");
			sql.append("  CPROJECTID          ,");
			sql.append("  CPROJECTPHASEID     ,");
			sql.append("  CQUOTECURRENCY      ,");
			sql.append("  CQUOTEUNITID        ,");
			sql.append("  CRECEIEVEID         ,");
			sql.append("  CRECEIVEAREAID      ,");
			sql.append("  CRECEIVEPOINTID     ,");
			sql.append("  CROWNO              ,");
			sql.append("  CSELASTUNITID       ,");
			sql.append("  CSIGNWASTBID        ,");
			sql.append("  CSIGNWASTHID        ,");
			sql.append("  CSIGNWASTTYPE       ,");
			sql.append("  CSOURCEBILLBID      ,");
			sql.append("  CSOURCEBILLHID      ,");
			sql.append("  CSOURCETYPE         ,");
			sql.append("  CSOURCEWASTBID      ,");
			sql.append("  CSOURCEWASTHID      ,");
			sql.append("  CSOURCEWASTTYPE     ,");
			sql.append("  CSRC2BILLBID        ,");
			sql.append("  CSRC2BILLHID        ,");
			sql.append("  CSRC2BILLTYPE       ,");
			sql.append("  CSUMID              ,");
			sql.append("  CVENDORID           ,");
			sql.append("  CWORKCENTERID       ,");
			sql.append("  CWORKSITEID         ,");
			sql.append("  CWP                 ,");
			sql.append("  DBIZDATE            ,");
			sql.append("  DDELIVERDATE        ,");
			sql.append("  DFIRSTBILLDATE      ,");
			sql.append("  DREQUIREDATE        ,");
			sql.append("  DREQUIRETIME        ,");
			sql.append("  DSTANDBYDATE        ,");
			sql.append("  DVALIDATE           ,");
			sql.append("  DZGDATE             ,");
			sql.append("  FBILLROWFLAG        ,");
			sql.append("  FCHECKED            ,");
			sql.append("  FLARGESS            ,");
			sql.append("  FTOOUTTRANSFLAG     ,");
			sql.append("  HSL                 ,");
			sql.append("  IDESATYPE           ,");
			sql.append("  ISOK                ,");
			sql.append("  NACCUMTONUM         ,");
			sql.append("  NACCUMWASTNUM       ,");
			sql.append("  NBARCODENUM         ,");
			sql.append("  NCORRESPONDASTNUM   ,");
			sql.append("  NCORRESPONDGRSNUM   ,");
			sql.append("  NCORRESPONDNUM      ,");
			sql.append("  NCOUNTNUM           ,");
			sql.append("  NFEESETTLETIMES     ,");
			sql.append("  NINASSISTNUM        ,");
			sql.append("  NINGROSSNUM         ,");
			sql.append("  NINNUM              ,");
			sql.append("  NKDNUM              ,");
			sql.append("  NMNY                ,");
			sql.append("  NNEEDINASSISTNUM    ,");
			sql.append("  NOUTASSISTNUM       ,");
			sql.append("  NOUTGROSSNUM        ,");
			sql.append("  NOUTNUM             ,");
			sql.append("  NPLANNEDMNY         ,");
			sql.append("  NPLANNEDPRICE       ,");
			sql.append("  NPRICE              ,");
			sql.append("  NPRICESETTLEBILL    ,");
			sql.append("  NQUOTEMNY           ,");
			sql.append("  NQUOTENTMNY         ,");
			sql.append("  NQUOTENTPRICE       ,");
			sql.append("  NQUOTEPRICE         ,");
			sql.append("  NQUOTEUNITNUM       ,");
			sql.append("  NQUOTEUNITRATE      ,");
			sql.append("  NREPLENISHEDASTNUM  ,");
			sql.append("  NREPLENISHEDNUM     ,");
			sql.append("  NRETASTNUM          ,");
			sql.append("  NRETGROSSNUM        ,");
			sql.append("  NRETNUM             ,");
			sql.append("  NSALEMNY            ,");
			sql.append("  NSALEPRICE          ,");
			sql.append("  NSHOULDINNUM        ,");
			sql.append("  NSHOULDOUTASSISTNUM ,");
			sql.append("  NSHOULDOUTNUM       ,");
			sql.append("  NTARENUM            ,");
			sql.append("  NTAXMNY             ,");
			sql.append("  NTAXPRICE           ,");
			sql.append("  NTRANOUTASTNUM      ,");
			sql.append("  NTRANOUTNUM         ,");
			sql.append("  PK_BODYCALBODY      ,");
			sql.append("  PK_CORP             ,");
			sql.append("  PK_CREQWAREID       ,");
			sql.append("  PK_CUBASDOC         ,");
			sql.append("  PK_INVOICECORP      ,");
			sql.append("  PK_MEASWARE         ,");
			sql.append("  PK_PACKSORT         ,");
			sql.append("  PK_REQCORP          ,");
			sql.append("  PK_REQSTOORG        ,");
			sql.append("  PK_RETURNREASON     ,");
			sql.append("  TS                  ,");
			sql.append("  VBATCHCODE          ,");
			sql.append("  VBILLTYPEU8RM       ,");
			sql.append("  VBODYNOTE2          ,");
			sql.append("  VCORRESPONDROWNO    ,");
			sql.append("  VFIRSTBILLCODE      ,");
			sql.append("  VFIRSTROWNO         ,");
			sql.append("  VFREE1              ,");
			sql.append("  VFREE10             ,");
			sql.append("  VFREE2              ,");
			sql.append("  VFREE3              ,");
			sql.append("  VFREE4              ,");
			sql.append("  VFREE5              ,");
			sql.append("  VFREE6              ,");
			sql.append("  VFREE7              ,");
			sql.append("  VFREE8              ,");
			sql.append("  VFREE9              ,");
			sql.append("  VNOTEBODY           ,");
			sql.append("  VPRODUCTBATCH       ,");
			sql.append("  VRECEIVEADDRESS     ,");
			sql.append("  VSIGNWASTCODE       ,");
			sql.append("  VSIGNWASTROWNO      ,");
			sql.append("  VSOURCEBILLCODE     ,");
			sql.append("  VSOURCEROWNO        ,");
			sql.append("  VSOURCEWASTCODE     ,");
			sql.append("  VSOURCEWASTROWNO    ,");
			sql.append("  VSRC2BILLCODE       ,");
			sql.append("  VSRC2BILLROWNO      ,");
			sql.append("  VTRANSFERCODE       ,");
			sql.append("  VUSERDEF1           ,");
			sql.append("  VUSERDEF10          ,");
			sql.append("  VUSERDEF11          ,");
			sql.append("  VUSERDEF12          ,");
			sql.append("  VUSERDEF13          ,");
			sql.append("  VUSERDEF14          ,");
			sql.append("  VUSERDEF15          ,");
			sql.append("  VUSERDEF16          ,");
			sql.append("  VUSERDEF17          ,");
			sql.append("  VUSERDEF18          ,");
			sql.append("  VUSERDEF19          ,");
			sql.append("  VUSERDEF2           ,");
			sql.append("  VUSERDEF20          ,");
			sql.append("  VUSERDEF3           ,");
			sql.append("  VUSERDEF4           ,");
			sql.append("  VUSERDEF5           ,");
			sql.append("  VUSERDEF6           ,");
			sql.append("  VUSERDEF7           ,");
			sql.append("  VUSERDEF8           ,");
			sql.append("  VUSERDEF9           ,");
			sql.append("  VVEHICLECODE ,       ");
			sql.append("  DR        ");
			sql.append("  from ic_general_b gb         ");
			sql.append("  where gb.cgeneralhid in (select gh.cgeneralhid from ic_general_h gh");
			sql.append("  where gh.dbilldate>=to_char((sysdate - ").append(
					this.getDays() + "),'yyyy-mm-dd')");
			sql.append("  and substr(gh.ts,1,10)>=to_char((sysdate - ").append(
					this.getBeforedays() + "),'yyyy-mm-dd')");
			sql.append("  and gh.pk_corp != '1020'     ");
			sql.append("  and gh.pk_corp != '1021'     ");
			sql.append("  and gh.pk_corp != '1023'     ");
			sql.append("  and gh.pk_corp != '1024'     ");
			sql.append("  and gh.pk_corp != '1032'     )");

			// System.out.println("查询sql:"+sql);
			pstNC = conNC.prepareStatement(sql.toString());
			restNC = pstNC.executeQuery();
			ResultSetMetaData rsmd = restNC.getMetaData();
			int resultcount = rsmd.getColumnCount();
			int tm = 0;
			while (restNC.next()) {
				StringBuilder insetSql = new StringBuilder();
				insetSql.append("insert into ODS_IC_GENERAL_B (");
				insetSql.append("  BBARCODECLOSE       ,");
				insetSql.append("  BONROADFLAG         ,");
				insetSql.append("  BRETURNPROFIT       ,");
				insetSql.append("  BSAFEPRICE          ,");
				insetSql.append("  BSOURCELARGESS      ,");
				insetSql.append("  BSUPPLYFLAG         ,");
				insetSql.append("  BTOINZGFLAG         ,");
				insetSql.append("  BTOOUTTOIAFLAG      ,");
				insetSql.append("  BTOOUTZGFLAG        ,");
				insetSql.append("  BTOU8RM             ,");
				insetSql.append("  BZGFLAG             ,");
				insetSql.append("  BZGYFFLAG           ,");
				insetSql.append("  CASTUNITID          ,");
				insetSql.append("  CBODYBILLTYPECODE   ,");
				insetSql.append("  CBODYWAREHOUSEID    ,");
				insetSql.append("  CCHECKSTATEID       ,");
				insetSql.append("  CCORRESPONDBID      ,");
				insetSql.append("  CCORRESPONDCODE     ,");
				insetSql.append("  CCORRESPONDHID      ,");
				insetSql.append("  CCORRESPONDTYPE     ,");
				insetSql.append("  CCOSTOBJECT         ,");
				insetSql.append("  CFIRSTBILLBID       ,");
				insetSql.append("  CFIRSTBILLHID       ,");
				insetSql.append("  CFIRSTTYPE          ,");
				insetSql.append("  CFREEZEID           ,");
				insetSql.append("  CGENERALBID         ,");
				insetSql.append("  CGENERALHID         ,");
				insetSql.append("  CINVBASID           ,");
				insetSql.append("  CINVENTORYID        ,");
				insetSql.append("  CORDER_BB1ID        ,");
				insetSql.append("  CPARENTID           ,");
				insetSql.append("  CPROJECTID          ,");
				insetSql.append("  CPROJECTPHASEID     ,");
				insetSql.append("  CQUOTECURRENCY      ,");
				insetSql.append("  CQUOTEUNITID        ,");
				insetSql.append("  CRECEIEVEID         ,");
				insetSql.append("  CRECEIVEAREAID      ,");
				insetSql.append("  CRECEIVEPOINTID     ,");
				insetSql.append("  CROWNO              ,");
				insetSql.append("  CSELASTUNITID       ,");
				insetSql.append("  CSIGNWASTBID        ,");
				insetSql.append("  CSIGNWASTHID        ,");
				insetSql.append("  CSIGNWASTTYPE       ,");
				insetSql.append("  CSOURCEBILLBID      ,");
				insetSql.append("  CSOURCEBILLHID      ,");
				insetSql.append("  CSOURCETYPE         ,");
				insetSql.append("  CSOURCEWASTBID      ,");
				insetSql.append("  CSOURCEWASTHID      ,");
				insetSql.append("  CSOURCEWASTTYPE     ,");
				insetSql.append("  CSRC2BILLBID        ,");
				insetSql.append("  CSRC2BILLHID        ,");
				insetSql.append("  CSRC2BILLTYPE       ,");
				insetSql.append("  CSUMID              ,");
				insetSql.append("  CVENDORID           ,");
				insetSql.append("  CWORKCENTERID       ,");
				insetSql.append("  CWORKSITEID         ,");
				insetSql.append("  CWP                 ,");
				insetSql.append("  DBIZDATE            ,");
				insetSql.append("  DDELIVERDATE        ,");
				insetSql.append("  DFIRSTBILLDATE      ,");
				insetSql.append("  DREQUIREDATE        ,");
				insetSql.append("  DREQUIRETIME        ,");
				insetSql.append("  DSTANDBYDATE        ,");
				insetSql.append("  DVALIDATE           ,");
				insetSql.append("  DZGDATE             ,");
				insetSql.append("  FBILLROWFLAG        ,");
				insetSql.append("  FCHECKED            ,");
				insetSql.append("  FLARGESS            ,");
				insetSql.append("  FTOOUTTRANSFLAG     ,");
				insetSql.append("  HSL                 ,");
				insetSql.append("  IDESATYPE           ,");
				insetSql.append("  ISOK                ,");
				insetSql.append("  NACCUMTONUM         ,");
				insetSql.append("  NACCUMWASTNUM       ,");
				insetSql.append("  NBARCODENUM         ,");
				insetSql.append("  NCORRESPONDASTNUM   ,");
				insetSql.append("  NCORRESPONDGRSNUM   ,");
				insetSql.append("  NCORRESPONDNUM      ,");
				insetSql.append("  NCOUNTNUM           ,");
				insetSql.append("  NFEESETTLETIMES     ,");
				insetSql.append("  NINASSISTNUM        ,");
				insetSql.append("  NINGROSSNUM         ,");
				insetSql.append("  NINNUM              ,");
				insetSql.append("  NKDNUM              ,");
				insetSql.append("  NMNY                ,");
				insetSql.append("  NNEEDINASSISTNUM    ,");
				insetSql.append("  NOUTASSISTNUM       ,");
				insetSql.append("  NOUTGROSSNUM        ,");
				insetSql.append("  NOUTNUM             ,");
				insetSql.append("  NPLANNEDMNY         ,");
				insetSql.append("  NPLANNEDPRICE       ,");
				insetSql.append("  NPRICE              ,");
				insetSql.append("  NPRICESETTLEBILL    ,");
				insetSql.append("  NQUOTEMNY           ,");
				insetSql.append("  NQUOTENTMNY         ,");
				insetSql.append("  NQUOTENTPRICE       ,");
				insetSql.append("  NQUOTEPRICE         ,");
				insetSql.append("  NQUOTEUNITNUM       ,");
				insetSql.append("  NQUOTEUNITRATE      ,");
				insetSql.append("  NREPLENISHEDASTNUM  ,");
				insetSql.append("  NREPLENISHEDNUM     ,");
				insetSql.append("  NRETASTNUM          ,");
				insetSql.append("  NRETGROSSNUM        ,");
				insetSql.append("  NRETNUM             ,");
				insetSql.append("  NSALEMNY            ,");
				insetSql.append("  NSALEPRICE          ,");
				insetSql.append("  NSHOULDINNUM        ,");
				insetSql.append("  NSHOULDOUTASSISTNUM ,");
				insetSql.append("  NSHOULDOUTNUM       ,");
				insetSql.append("  NTARENUM            ,");
				insetSql.append("  NTAXMNY             ,");
				insetSql.append("  NTAXPRICE           ,");
				insetSql.append("  NTRANOUTASTNUM      ,");
				insetSql.append("  NTRANOUTNUM         ,");
				insetSql.append("  PK_BODYCALBODY      ,");
				insetSql.append("  PK_CORP             ,");
				insetSql.append("  PK_CREQWAREID       ,");
				insetSql.append("  PK_CUBASDOC         ,");
				insetSql.append("  PK_INVOICECORP      ,");
				insetSql.append("  PK_MEASWARE         ,");
				insetSql.append("  PK_PACKSORT         ,");
				insetSql.append("  PK_REQCORP          ,");
				insetSql.append("  PK_REQSTOORG        ,");
				insetSql.append("  PK_RETURNREASON     ,");
				insetSql.append("  TS                  ,");
				insetSql.append("  VBATCHCODE          ,");
				insetSql.append("  VBILLTYPEU8RM       ,");
				insetSql.append("  VBODYNOTE2          ,");
				insetSql.append("  VCORRESPONDROWNO    ,");
				insetSql.append("  VFIRSTBILLCODE      ,");
				insetSql.append("  VFIRSTROWNO         ,");
				insetSql.append("  VFREE1              ,");
				insetSql.append("  VFREE10             ,");
				insetSql.append("  VFREE2              ,");
				insetSql.append("  VFREE3              ,");
				insetSql.append("  VFREE4              ,");
				insetSql.append("  VFREE5              ,");
				insetSql.append("  VFREE6              ,");
				insetSql.append("  VFREE7              ,");
				insetSql.append("  VFREE8              ,");
				insetSql.append("  VFREE9              ,");
				insetSql.append("  VNOTEBODY           ,");
				insetSql.append("  VPRODUCTBATCH       ,");
				insetSql.append("  VRECEIVEADDRESS     ,");
				insetSql.append("  VSIGNWASTCODE       ,");
				insetSql.append("  VSIGNWASTROWNO      ,");
				insetSql.append("  VSOURCEBILLCODE     ,");
				insetSql.append("  VSOURCEROWNO        ,");
				insetSql.append("  VSOURCEWASTCODE     ,");
				insetSql.append("  VSOURCEWASTROWNO    ,");
				insetSql.append("  VSRC2BILLCODE       ,");
				insetSql.append("  VSRC2BILLROWNO      ,");
				insetSql.append("  VTRANSFERCODE       ,");
				insetSql.append("  VUSERDEF1           ,");
				insetSql.append("  VUSERDEF10          ,");
				insetSql.append("  VUSERDEF11          ,");
				insetSql.append("  VUSERDEF12          ,");
				insetSql.append("  VUSERDEF13          ,");
				insetSql.append("  VUSERDEF14          ,");
				insetSql.append("  VUSERDEF15          ,");
				insetSql.append("  VUSERDEF16          ,");
				insetSql.append("  VUSERDEF17          ,");
				insetSql.append("  VUSERDEF18          ,");
				insetSql.append("  VUSERDEF19          ,");
				insetSql.append("  VUSERDEF2           ,");
				insetSql.append("  VUSERDEF20          ,");
				insetSql.append("  VUSERDEF3           ,");
				insetSql.append("  VUSERDEF4           ,");
				insetSql.append("  VUSERDEF5           ,");
				insetSql.append("  VUSERDEF6           ,");
				insetSql.append("  VUSERDEF7           ,");
				insetSql.append("  VUSERDEF8           ,");
				insetSql.append("  VUSERDEF9           ,");
				insetSql.append("  VVEHICLECODE       , ");
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
			System.out.println("出入库单辅表增量数据抽取完毕");
			System.out.println("结束时间为" + new Timestamp(new Date().getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("出入库单辅表NC connection close");
			BaseDao.closeAll(pstNC, restNC, conNC);
			System.out.println("出入库单辅表NC connection closed");

			System.out.println("出入库单辅表BQ connection close");
			BaseDao.closeAll(pstBQ, restBQ, conBQ);
			System.out.println("出入库单辅表BQ connection closed");
		}

	}
}
