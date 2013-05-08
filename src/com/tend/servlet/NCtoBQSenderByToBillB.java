package com.tend.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NCtoBQSenderByToBillB extends BaseDao implements Runnable {
	public NCtoBQSenderByToBillB() {
		System.out.println("NCtoBQSenderByToBillB 无参构造函数");
	}

	public void run() {
		try {
			DateLoop("2010-07-01", "2010-08-01", 1);
			System.out.println("内部交易辅表数据抽取完成");
		} catch (Exception e) {
			System.out.println("内部交易辅表抽取数据异常");
			e.printStackTrace();
		}
	}

	public void DateLoop(String begindate, String enddate, int days)
			throws Exception {
		FormatDate mm = new FormatDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = sdf.parse(begindate);
		Date date2 = sdf.parse(enddate);
		Date datetemp = FormatDate.getDateAfterDay(date1, days);
		System.out.println("内部交易辅表时间区间：" + sdf.format(date1) + " to "
				+ sdf.format(datetemp));
		NCtoBQ(sdf.format(date1), sdf.format(datetemp));
		while (FormatDate.dateCompare(datetemp, date2)) {
			datetemp = FormatDate.getDateAfterDay(datetemp, days);
			if (FormatDate.dateCompare(date2, datetemp)) {
				System.out.println("内部交易辅表时间区间："
						+ sdf.format(FormatDate
								.getDateAfterDay(datetemp, -days)) + " to "
						+ sdf.format(date2));
				NCtoBQ(sdf.format(FormatDate.getDateAfterDay(datetemp, -days)),
						sdf.format(date2));
			} else {
				System.out.println("内部交易辅表时间区间："
						+ sdf.format(FormatDate
								.getDateAfterDay(datetemp, -days)) + " to "
						+ sdf.format(datetemp));
				NCtoBQ(sdf.format(FormatDate.getDateAfterDay(datetemp, -days)),
						sdf.format(datetemp));
			}
		}
	}

	public void NCtoBQ(String beginDate, String endDate) throws Exception {
		Connection conNC = null;
		PreparedStatement pstNC = null;
		ResultSet restNC = null;
		Connection conBQ = null;
		PreparedStatement pstBQ = null;
		ResultSet restBQ = null;
		System.out.println("内部交易辅表开始抽取数据................");
		System.out.println("开始时间为" + new Timestamp(new Date().getTime()));
		try {
			System.out.println("内部交易辅表获取连接");
			conNC = getConForNC();
			conBQ = getConForBQ();
			System.out.println("内部交易辅表获取连接成功");
			StringBuilder sql = new StringBuilder();
			sql.append("select BARRANGEDFLAG,");
			sql.append("  BOUTENDFLAG,");
			sql.append("  BRETRACTFLAG,");
			sql.append("  BSENDENDFLAG,");
			sql.append("  BSETTLEENDFLAG,");
			sql.append("  BTAKESETTLEENDFLAG,");
			sql.append("  CARRANGEPERSONID,");
			sql.append("  CASTUNITID,");
			sql.append("  CBILL_BID,");
			sql.append("  CBILLID,");
			sql.append("  CBILLTYPE,");
			sql.append("  CBIZTYPEID,");
			sql.append("  CCLOSEMAKERID,");
			sql.append("  CCORRECTBILL_BID,");
			sql.append("  CDELETEMAKERID,");
			sql.append("  CFIRSTBID,");
			sql.append("  CFIRSTID,");
			sql.append("  CFIRSTTYPECODE,");
			sql.append("  CINCBID,");
			sql.append("  CINCORPID,");
			sql.append("  CINDEPTID,");
			sql.append("  CININVID,");
			sql.append("  CINPSNID,");
			sql.append("  CINSPACEID,");
			sql.append("  CINVBASID,");
			sql.append("  CINWHID,");
			sql.append("  COUTCBID,");
			sql.append("  COUTCORPID,");
			sql.append("  COUTDEPTID,");
			sql.append("  COUTENDMAKERID,");
			sql.append("  COUTINVID,");
			sql.append("  COUTPSNID,");
			sql.append("  COUTSPACEID,");
			sql.append("  COUTWHID,");
			sql.append("  CPROJECTID,");
			sql.append("  CPROJECTPHASE,");
			sql.append("  CQUOTEUNITID,");
			sql.append("  CRECEIEVEID,");
			sql.append("  CRECEIVERBASID,");
			sql.append("  CRELATION_BID,");
			sql.append("  CRELATIONID,");
			sql.append("  CROWNO,");
			sql.append("  CSENDENDMAKERID,");
			sql.append("  CSOURCEBID,");
			sql.append("  CSOURCEID,");
			sql.append("  CSOURCETYPECODE,");
			sql.append("  CTAKEOUTCBID,");
			sql.append("  CTAKEOUTCORPID,");
			sql.append("  CTAKEOUTDEPTID,");
			sql.append("  CTAKEOUTINVID,");
			sql.append("  CTAKEOUTPSNID,");
			sql.append("  CTAKEOUTSPACEID,");
			sql.append("  CTAKEOUTWHID,");
			sql.append("  CTAKERELATION_BID,");
			sql.append("  CTAKERELATIONID,");
			sql.append("  CTYPECODE,");
			sql.append("  CVENDORBASID,");
			sql.append("  CVENDORID,");
			sql.append("  CWORKCENTERID,");
			sql.append("  DPLANARRIVEDATE,");
			sql.append("  DPLANOUTDATE,");
			sql.append("  DPRODUCEDATE,");
			sql.append("  DR,");
			sql.append("  DREQUIREDATE,");
			sql.append("  DVALIDATE,");
			sql.append("  FALLOCFLAG,");
			sql.append("  FLARGESS,");
			sql.append("  FROWSTATUFLAG,");
			sql.append("  FTAXTYPEFLAG,");
			sql.append("  NADDPRICERATE,");
			sql.append("  NARRANGEASSNUM,");
			sql.append("  NARRANGEMONUM,");
			sql.append("  NARRANGENUM,");
			sql.append("  NARRANGEPOAPPLYNUM,");
			sql.append("  NARRANGEPOORDERNUM,");
			sql.append("  NARRANGESCORNUM,");
			sql.append("  NARRANGETOORNUM,");
			sql.append("  NASKNOTAXPRICE,");
			sql.append("  NASKPRICE,");
			sql.append("  NASSISTNUM,");
			sql.append("  NCHANGERATE,");
			sql.append("  NFEEDBACKNUM,");
			sql.append("  NMNY,");
			sql.append("  NNOTAXMNY,");
			sql.append("  NNOTAXPRICE,");
			sql.append("  NNUM,");
			sql.append("  NORDERBACKASSNUM,");
			sql.append("  NORDERBACKNUM,");
			sql.append("  NORDERINASSNUM,");
			sql.append("  NORDERINNUM,");
			sql.append("  NORDERINOUTMNY,");
			sql.append("  NORDERINOUTNUM,");
			sql.append("  NORDEROUTASSNUM,");
			sql.append("  NORDEROUTNUM,");
			sql.append("  NORDERSENDASSNUM,");
			sql.append("  NORDERSENDNUM,");
			sql.append("  NORDERSHOULDINNUM,");
			sql.append("  NORDERSHOULDOUTNUM,");
			sql.append("  NORDERSIGNASSNUM,");
			sql.append("  NORDERSIGNNUM,");
			sql.append("  NORDERTAKEMNY,");
			sql.append("  NORDERTAKENUM,");
			sql.append("  NORDERWAYLOSSASSNUM,");
			sql.append("  NORDERWAYLOSSNUM,");
			sql.append("  NORGQTNETPRC,");
			sql.append("  NORGQTTAXNETPRC,");
			sql.append("  NOUTSUMNUM,");
			sql.append("  NPRICE,");
			sql.append("  NQUOTEUNITNUM,");
			sql.append("  NQUOTEUNITRATE,");
			sql.append("  NSAFETYSTOCKNUM,");
			sql.append("  NTAXRATE,");
			sql.append("  NTRANPOASSNUM,");
			sql.append("  NTRANPONUM,");
			sql.append("  NTRANSTOPRAYNUM,");
			sql.append("  PK_AREACL,");
			sql.append("  PK_ARRIVEAREA,");
			sql.append("  PK_DEFDOC1,");
			sql.append("  PK_DEFDOC10,");
			sql.append("  PK_DEFDOC11,");
			sql.append("  PK_DEFDOC12,");
			sql.append("  PK_DEFDOC13,");
			sql.append("  PK_DEFDOC14,");
			sql.append("  PK_DEFDOC15,");
			sql.append("  PK_DEFDOC16,");
			sql.append("  PK_DEFDOC17,");
			sql.append("  PK_DEFDOC18,");
			sql.append("  PK_DEFDOC19,");
			sql.append("  PK_DEFDOC2,");
			sql.append("  PK_DEFDOC20,");
			sql.append("  PK_DEFDOC3,");
			sql.append("  PK_DEFDOC4,");
			sql.append("  PK_DEFDOC5,");
			sql.append("  PK_DEFDOC6,");
			sql.append("  PK_DEFDOC7,");
			sql.append("  PK_DEFDOC8,");
			sql.append("  PK_DEFDOC9,");
			sql.append("  PK_RT_BID,");
			sql.append("  PK_SENDTYPE,");
			sql.append("  PK_TLDID,");
			sql.append("  TLASTARRANGETIME,");
			sql.append("  TPLANARRIVETIME,");
			sql.append("  TPLANOUTTIME,");
			sql.append("  TREQUIRETIME,");
			sql.append("  TS,");
			sql.append("  TSCLOSE,");
			sql.append("  TSDELETE,");
			sql.append("  TSOUTEND,");
			sql.append("  TSSENDEND,");
			sql.append("  VBATCH,");
			sql.append("  VBDEF1,");
			sql.append("  VBDEF10,");
			sql.append("  VBDEF11,");
			sql.append("  VBDEF12,");
			sql.append("  VBDEF13,");
			sql.append("  VBDEF14,");
			sql.append("  VBDEF15,");
			sql.append("  VBDEF16,");
			sql.append("  VBDEF17,");
			sql.append("  VBDEF18,");
			sql.append("  VBDEF19,");
			sql.append("  VBDEF2,");
			sql.append("  VBDEF20,");
			sql.append("  VBDEF3,");
			sql.append("  VBDEF4,");
			sql.append("  VBDEF5,");
			sql.append("  VBDEF6,");
			sql.append("  VBDEF7,");
			sql.append("  VBDEF8,");
			sql.append("  VBDEF9,");
			sql.append("  VBOMCODE,");
			sql.append("  VBREVISEREASON,");
			sql.append("  VCODE,");
			sql.append("  VFIRSTCODE,");
			sql.append("  VFIRSTROWNO,");
			sql.append("  VFREE1,");
			sql.append("  VFREE2,");
			sql.append("  VFREE3,");
			sql.append("  VFREE4,");
			sql.append("  VFREE5,");
			sql.append("  VNOTE,");
			sql.append("  VPRODUCEBATCH,");
			sql.append("  VRECEIVEADDRESS,");
			sql.append("  VSOURCECODE,");
			sql.append("  VSOURCEROWNO");
			sql.append("  from to_bill_b gb         ");
			sql.append("  where gb.CBILLID in (select gh.CBILLID from to_bill gh");
			sql.append("  where gh.ts >= '").append(beginDate + "'");
			sql.append("  and gh.ts < '").append(endDate + "'");
			sql.append("  and gh.coutcorpid = '1002' )");
			System.out.println("查询sql:" + sql);
			pstNC = conNC.prepareStatement(sql.toString());
			restNC = pstNC.executeQuery();
			ResultSetMetaData rsmd = restNC.getMetaData();
			int resultcount = rsmd.getColumnCount();

			while (restNC.next()) {
				StringBuilder insetSql = new StringBuilder();
				insetSql.append("insert into ods_to_bill_b ( BARRANGEDFLAG,");
				insetSql.append("  BOUTENDFLAG,");
				insetSql.append("  BRETRACTFLAG,");
				insetSql.append("  BSENDENDFLAG,");
				insetSql.append("  BSETTLEENDFLAG,");
				insetSql.append("  BTAKESETTLEENDFLAG,");
				insetSql.append("  CARRANGEPERSONID,");
				insetSql.append("  CASTUNITID,");
				insetSql.append("  CBILL_BID,");
				insetSql.append("  CBILLID,");
				insetSql.append("  CBILLTYPE,");
				insetSql.append("  CBIZTYPEID,");
				insetSql.append("  CCLOSEMAKERID,");
				insetSql.append("  CCORRECTBILL_BID,");
				insetSql.append("  CDELETEMAKERID,");
				insetSql.append("  CFIRSTBID,");
				insetSql.append("  CFIRSTID,");
				insetSql.append("  CFIRSTTYPECODE,");
				insetSql.append("  CINCBID,");
				insetSql.append("  CINCORPID,");
				insetSql.append("  CINDEPTID,");
				insetSql.append("  CININVID,");
				insetSql.append("  CINPSNID,");
				insetSql.append("  CINSPACEID,");
				insetSql.append("  CINVBASID,");
				insetSql.append("  CINWHID,");
				insetSql.append("  COUTCBID,");
				insetSql.append("  COUTCORPID,");
				insetSql.append("  COUTDEPTID,");
				insetSql.append("  COUTENDMAKERID,");
				insetSql.append("  COUTINVID,");
				insetSql.append("  COUTPSNID,");
				insetSql.append("  COUTSPACEID,");
				insetSql.append("  COUTWHID,");
				insetSql.append("  CPROJECTID,");
				insetSql.append("  CPROJECTPHASE,");
				insetSql.append("  CQUOTEUNITID,");
				insetSql.append("  CRECEIEVEID,");
				insetSql.append("  CRECEIVERBASID,");
				insetSql.append("  CRELATION_BID,");
				insetSql.append("  CRELATIONID,");
				insetSql.append("  CROWNO,");
				insetSql.append("  CSENDENDMAKERID,");
				insetSql.append("  CSOURCEBID,");
				insetSql.append("  CSOURCEID,");
				insetSql.append("  CSOURCETYPECODE,");
				insetSql.append("  CTAKEOUTCBID,");
				insetSql.append("  CTAKEOUTCORPID,");
				insetSql.append("  CTAKEOUTDEPTID,");
				insetSql.append("  CTAKEOUTINVID,");
				insetSql.append("  CTAKEOUTPSNID,");
				insetSql.append("  CTAKEOUTSPACEID,");
				insetSql.append("  CTAKEOUTWHID,");
				insetSql.append("  CTAKERELATION_BID,");
				insetSql.append("  CTAKERELATIONID,");
				insetSql.append("  CTYPECODE,");
				insetSql.append("  CVENDORBASID,");
				insetSql.append("  CVENDORID,");
				insetSql.append("  CWORKCENTERID,");
				insetSql.append("  DPLANARRIVEDATE,");
				insetSql.append("  DPLANOUTDATE,");
				insetSql.append("  DPRODUCEDATE,");
				insetSql.append("  DR,");
				insetSql.append("  DREQUIREDATE,");
				insetSql.append("  DVALIDATE,");
				insetSql.append("  FALLOCFLAG,");
				insetSql.append("  FLARGESS,");
				insetSql.append("  FROWSTATUFLAG,");
				insetSql.append("  FTAXTYPEFLAG,");
				insetSql.append("  NADDPRICERATE,");
				insetSql.append("  NARRANGEASSNUM,");
				insetSql.append("  NARRANGEMONUM,");
				insetSql.append("  NARRANGENUM,");
				insetSql.append("  NARRANGEPOAPPLYNUM,");
				insetSql.append("  NARRANGEPOORDERNUM,");
				insetSql.append("  NARRANGESCORNUM,");
				insetSql.append("  NARRANGETOORNUM,");
				insetSql.append("  NASKNOTAXPRICE,");
				insetSql.append("  NASKPRICE,");
				insetSql.append("  NASSISTNUM,");
				insetSql.append("  NCHANGERATE,");
				insetSql.append("  NFEEDBACKNUM,");
				insetSql.append("  NMNY,");
				insetSql.append("  NNOTAXMNY,");
				insetSql.append("  NNOTAXPRICE,");
				insetSql.append("  NNUM,");
				insetSql.append("  NORDERBACKASSNUM,");
				insetSql.append("  NORDERBACKNUM,");
				insetSql.append("  NORDERINASSNUM,");
				insetSql.append("  NORDERINNUM,");
				insetSql.append("  NORDERINOUTMNY,");
				insetSql.append("  NORDERINOUTNUM,");
				insetSql.append("  NORDEROUTASSNUM,");
				insetSql.append("  NORDEROUTNUM,");
				insetSql.append("  NORDERSENDASSNUM,");
				insetSql.append("  NORDERSENDNUM,");
				insetSql.append("  NORDERSHOULDINNUM,");
				insetSql.append("  NORDERSHOULDOUTNUM,");
				insetSql.append("  NORDERSIGNASSNUM,");
				insetSql.append("  NORDERSIGNNUM,");
				insetSql.append("  NORDERTAKEMNY,");
				insetSql.append("  NORDERTAKENUM,");
				insetSql.append("  NORDERWAYLOSSASSNUM,");
				insetSql.append("  NORDERWAYLOSSNUM,");
				insetSql.append("  NORGQTNETPRC,");
				insetSql.append("  NORGQTTAXNETPRC,");
				insetSql.append("  NOUTSUMNUM,");
				insetSql.append("  NPRICE,");
				insetSql.append("  NQUOTEUNITNUM,");
				insetSql.append("  NQUOTEUNITRATE,");
				insetSql.append("  NSAFETYSTOCKNUM,");
				insetSql.append("  NTAXRATE,");
				insetSql.append("  NTRANPOASSNUM,");
				insetSql.append("  NTRANPONUM,");
				insetSql.append("  NTRANSTOPRAYNUM,");
				insetSql.append("  PK_AREACL,");
				insetSql.append("  PK_ARRIVEAREA,");
				insetSql.append("  PK_DEFDOC1,");
				insetSql.append("  PK_DEFDOC10,");
				insetSql.append("  PK_DEFDOC11,");
				insetSql.append("  PK_DEFDOC12,");
				insetSql.append("  PK_DEFDOC13,");
				insetSql.append("  PK_DEFDOC14,");
				insetSql.append("  PK_DEFDOC15,");
				insetSql.append("  PK_DEFDOC16,");
				insetSql.append("  PK_DEFDOC17,");
				insetSql.append("  PK_DEFDOC18,");
				insetSql.append("  PK_DEFDOC19,");
				insetSql.append("  PK_DEFDOC2,");
				insetSql.append("  PK_DEFDOC20,");
				insetSql.append("  PK_DEFDOC3,");
				insetSql.append("  PK_DEFDOC4,");
				insetSql.append("  PK_DEFDOC5,");
				insetSql.append("  PK_DEFDOC6,");
				insetSql.append("  PK_DEFDOC7,");
				insetSql.append("  PK_DEFDOC8,");
				insetSql.append("  PK_DEFDOC9,");
				insetSql.append("  PK_RT_BID,");
				insetSql.append("  PK_SENDTYPE,");
				insetSql.append("  PK_TLDID,");
				insetSql.append("  TLASTARRANGETIME,");
				insetSql.append("  TPLANARRIVETIME,");
				insetSql.append("  TPLANOUTTIME,");
				insetSql.append("  TREQUIRETIME,");
				insetSql.append("  TS,");
				insetSql.append("  TSCLOSE,");
				insetSql.append("  TSDELETE,");
				insetSql.append("  TSOUTEND,");
				insetSql.append("  TSSENDEND,");
				insetSql.append("  VBATCH,");
				insetSql.append("  VBDEF1,");
				insetSql.append("  VBDEF10,");
				insetSql.append("  VBDEF11,");
				insetSql.append("  VBDEF12,");
				insetSql.append("  VBDEF13,");
				insetSql.append("  VBDEF14,");
				insetSql.append("  VBDEF15,");
				insetSql.append("  VBDEF16,");
				insetSql.append("  VBDEF17,");
				insetSql.append("  VBDEF18,");
				insetSql.append("  VBDEF19,");
				insetSql.append("  VBDEF2,");
				insetSql.append("  VBDEF20,");
				insetSql.append("  VBDEF3,");
				insetSql.append("  VBDEF4,");
				insetSql.append("  VBDEF5,");
				insetSql.append("  VBDEF6,");
				insetSql.append("  VBDEF7,");
				insetSql.append("  VBDEF8,");
				insetSql.append("  VBDEF9,");
				insetSql.append("  VBOMCODE,");
				insetSql.append("  VBREVISEREASON,");
				insetSql.append("  VCODE,");
				insetSql.append("  VFIRSTCODE,");
				insetSql.append("  VFIRSTROWNO,");
				insetSql.append("  VFREE1,");
				insetSql.append("  VFREE2,");
				insetSql.append("  VFREE3,");
				insetSql.append("  VFREE4,");
				insetSql.append("  VFREE5,");
				insetSql.append("  VNOTE,");
				insetSql.append("  VPRODUCEBATCH,");
				insetSql.append("  VRECEIVEADDRESS,");
				insetSql.append("  VSOURCECODE,");
				insetSql.append("  VSOURCEROWNO  ) values (  ");
				for (int i = 1; i <= resultcount; i++) {
					if ((rsmd.getColumnType(i) == 1)
							|| (rsmd.getColumnType(i) == 12)) {
						if ((restNC.getString(i) == null)
								|| (restNC.getString(i).isEmpty()))
							insetSql.append("''");
						else {
							insetSql.append("'").append(restNC.getString(i))
									.append("'");
						}
						if (i < resultcount)
							insetSql.append(",");
					} else {
						insetSql.append(restNC.getDouble(i));
						if (i < resultcount) {
							insetSql.append(",");
						}
					}
				}
				insetSql.append(")");
				try {
					pstBQ = conBQ.prepareStatement(insetSql.toString());
					boolean result = pstBQ.execute();
					if (!result)
						System.out.println("保存成功");
					else
						System.out.println("保存失败");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (pstBQ != null)
							pstBQ.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("内部交易辅表数据抽取完毕");
			System.out.println("结束时间为" + new Timestamp(new Date().getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("内部交易辅表NC connection close");
			BaseDao.closeAll(pstNC, restNC, conNC);
			System.out.println("内部交易辅表NC connection closed");

			System.out.println("内部交易辅表BQ connection close");
			BaseDao.closeAll(pstBQ, restBQ, conBQ);
			System.out.println("内部交易辅表BQ connection closed");
		}
	}
}