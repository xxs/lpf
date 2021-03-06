package com.tend.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NCtoBQSenderByToBill extends BaseDao implements Runnable {
	public NCtoBQSenderByToBill() {
		System.out.println("NCtoBQSenderByToBill 无参构造函数");
	}

	public void run() {
		try {
			DateLoop(this.getNctobq_begindate(), this.getEnddate(),this.getNctobq_zday());
			System.out.println("内部交易主表数据抽取完成");
		} catch (Exception e) {
			System.out.println("内部交易主表抽取数据异常");
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
		System.out.println("内部交易主表时间区间：" + sdf.format(date1) + " to "
				+ sdf.format(datetemp));
		NCtoBQ(sdf.format(date1), sdf.format(datetemp));
		while (FormatDate.dateCompare(datetemp, date2)) {
			datetemp = FormatDate.getDateAfterDay(datetemp, days);
			if (FormatDate.dateCompare(date2, datetemp)) {
				System.out.println("内部交易主表时间区间："
						+ sdf.format(FormatDate
								.getDateAfterDay(datetemp, -days)) + " to "
						+ sdf.format(date2));
				NCtoBQ(sdf.format(FormatDate.getDateAfterDay(datetemp, -days)),
						sdf.format(date2));
			} else {
				System.out.println("内部交易主表时间区间："
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
		System.out.println("内部交易主表开始抽取数据................");
		System.out.println("开始时间为" + new Timestamp(new Date().getTime()));
		try {
			System.out.println("内部交易主表获取连接");
			conNC = getConForNC();
			conBQ = getConForBQ();
			System.out.println("内部交易主表获取连接成功");
			StringBuilder sql = new StringBuilder();
			sql.append("select BADDPLANFLAG,");
			sql.append("  BDRICTFLAG,");
			sql.append("  BLATEST,");
			sql.append("  BPUSHBYSOFLAG,");
			sql.append("  CAUDITORID,");
			sql.append("  CBILLID,");
			sql.append("  CBILLTYPE,");
			sql.append("  CBIZTYPEID,");
			sql.append("  CCORRECTBILLID,");
			sql.append("  CEMPLOYEEID,");
			sql.append("  CINCBID,");
			sql.append("  CINCORPID,");
			sql.append("  CINWHID,");
			sql.append("  CLASTMODIFIERID,");
			sql.append("  COPERATORID,");
			sql.append("  COUTCBID,");
			sql.append("  COUTCORPID,");
			sql.append("  COUTCURRTYPE,");
			sql.append("  COUTWHID,");
			sql.append("  CPROJECTID,");
			sql.append("  CREVISER,");
			sql.append("  CSETTLEPATHID,");
			sql.append("  CSOURCEMODULENAME,");
			sql.append("  CTAKEOUTCBID,");
			sql.append("  CTAKEOUTCORPID,");
			sql.append("  CTYPECODE,");
			sql.append("  DAUDITDATE,");
			sql.append("  DBILLDATE,");
			sql.append("  DPLANBINDATE,");
			sql.append("  DPLANENDDATE,");
			sql.append("  DR,");
			sql.append("  DREVISETIME,");
			sql.append("  FALLOCFLAG,");
			sql.append("  FOIWASTPARTFLAG,");
			sql.append("  FOTWASTPARTFLAG,");
			sql.append("  FSTATUSFLAG,");
			sql.append("  IPRINTCOUNT,");
			sql.append("  IVERSION,");
			sql.append("  NEXCHANGEOTOARATE,");
			sql.append("  NEXCHANGEOTOBRATE,");
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
			sql.append("  TAUDITTIME,");
			sql.append("  TLASTMODIFYTIME,");
			sql.append("  TMAKETIME,");
			sql.append("  TS,");
			sql.append("  VCODE,");
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
			sql.append("  VREVISEREASON_H,");
			sql.append("  BISSEND");
			sql.append("  from to_bill gh          ");
			sql.append("  where gh.ts >= '").append(beginDate + "'");
			sql.append("  and gh.ts < '").append(endDate + "'");
			sql.append("  and gh.coutcorpid = '1002'       ");
			System.out.println("查询sql:" + sql);
			pstNC = conNC.prepareStatement(sql.toString());
			restNC = pstNC.executeQuery();
			ResultSetMetaData rsmd = restNC.getMetaData();
			int resultcount = rsmd.getColumnCount();

			while (restNC.next()) {
				StringBuilder insetSql = new StringBuilder();
				insetSql.append("insert into ods_to_bill (  BADDPLANFLAG,");
				insetSql.append("  BDRICTFLAG,");
				insetSql.append("  BLATEST,");
				insetSql.append("  BPUSHBYSOFLAG,");
				insetSql.append("  CAUDITORID,");
				insetSql.append("  CBILLID,");
				insetSql.append("  CBILLTYPE,");
				insetSql.append("  CBIZTYPEID,");
				insetSql.append("  CCORRECTBILLID,");
				insetSql.append("  CEMPLOYEEID,");
				insetSql.append("  CINCBID,");
				insetSql.append("  CINCORPID,");
				insetSql.append("  CINWHID,");
				insetSql.append("  CLASTMODIFIERID,");
				insetSql.append("  COPERATORID,");
				insetSql.append("  COUTCBID,");
				insetSql.append("  COUTCORPID,");
				insetSql.append("  COUTCURRTYPE,");
				insetSql.append("  COUTWHID,");
				insetSql.append("  CPROJECTID,");
				insetSql.append("  CREVISER,");
				insetSql.append("  CSETTLEPATHID,");
				insetSql.append("  CSOURCEMODULENAME,");
				insetSql.append("  CTAKEOUTCBID,");
				insetSql.append("  CTAKEOUTCORPID,");
				insetSql.append("  CTYPECODE,");
				insetSql.append("  DAUDITDATE,");
				insetSql.append("  DBILLDATE,");
				insetSql.append("  DPLANBINDATE,");
				insetSql.append("  DPLANENDDATE,");
				insetSql.append("  DR,");
				insetSql.append("  DREVISETIME,");
				insetSql.append("  FALLOCFLAG,");
				insetSql.append("  FOIWASTPARTFLAG,");
				insetSql.append("  FOTWASTPARTFLAG,");
				insetSql.append("  FSTATUSFLAG,");
				insetSql.append("  IPRINTCOUNT,");
				insetSql.append("  IVERSION,");
				insetSql.append("  NEXCHANGEOTOARATE,");
				insetSql.append("  NEXCHANGEOTOBRATE,");
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
				insetSql.append("  TAUDITTIME,");
				insetSql.append("  TLASTMODIFYTIME,");
				insetSql.append("  TMAKETIME,");
				insetSql.append("  TS,");
				insetSql.append("  VCODE,");
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
				insetSql.append("  VREVISEREASON_H,");
				insetSql.append("  BISSEND  ) values ( ");
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
			System.out.println("内部交易主表数据抽取完毕");
			System.out.println("结束时间为" + new Timestamp(new Date().getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("内部交易主表NC connection close");
			BaseDao.closeAll(pstNC, restNC, conNC);
			System.out.println("内部交易主表NC connection closed");

			System.out.println("内部交易主表BQ connection close");
			BaseDao.closeAll(pstBQ, restBQ, conBQ);
			System.out.println("内部交易主表BQ connection closed");
		}
	}
}