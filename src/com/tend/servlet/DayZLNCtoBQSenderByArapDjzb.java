package com.tend.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 单据主表 增量增量数据抽取
 * 
 * @author Administrator
 * 
 */
public class DayZLNCtoBQSenderByArapDjzb extends BaseDao implements
		Runnable {

	public DayZLNCtoBQSenderByArapDjzb() {
		System.out.println("单据主表增量数据抽--无参构造函数");
	}

	public void run() {
		try {
			DeleteDate();// 清空ods表数据
			NCtoBQ();// 数据抽取
			System.out.println("单据主表增量数据抽取完成");
		} catch (Exception e) {
			System.out.println("单据主表抽取增量数据异常");
			e.printStackTrace();
		}
	}

	/**
	 * 清空表数据
	 * 
	 * @throws Exception
	 */
	public void DeleteDate() throws Exception {
		String sql = "delete ods_arap_djzb";
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
		System.out.println("单据主表开始抽取增量数据................");
		System.out.println("开始时间为" + new Timestamp(new Date().getTime()));
		try {
			System.out.println("单据主表获取连接");
			conNC = this.getConForNC();
			conBQ = this.getConForBQ();
			System.out.println("单据主表获取连接成功");
			StringBuilder sql = new StringBuilder();
			sql.append("select");
			sql.append("  BBJE,");
			sql.append("  DDHBBM,");
			sql.append("  DJBH,");
			sql.append("  DJDL,");
			sql.append("  DJKJND,");
			sql.append("  DJKJQJ,");
			sql.append("  DJLXBM,");
			sql.append("  DJRQ,");
			sql.append("  DJZT,");
			sql.append("  DWBM,");
			sql.append("  DYVOUCHID,");
			sql.append("  DZRQ,");
			sql.append("  EFFECTDATE,");
			sql.append("  ENDUSER,");
			sql.append("  FBJE,");
			sql.append("  FJ,");
			sql.append("  FKTJBM,");
			sql.append("  HZBZ,");
			sql.append("  INNER_EFFECT_DATE,");
			sql.append("  ISJSZXZF,");
			sql.append("  ISNETREADY,");
			sql.append("  ISONLINEPAY,");
			sql.append("  ISPAID,");
			sql.append("  ISREDED,");
			sql.append("  ISSELECTEDPAY,");
			sql.append("  JSZXZF,");
			sql.append("  KMBM,");
			sql.append("  KSKHYH,");
			sql.append("  LASTSHR,");
			sql.append("  LASTTZR,");
			sql.append("  LRR,");
			sql.append("  LYBZ,");
			sql.append("  OFFICIALPRINTDATE,");
			sql.append("  OFFICIALPRINTUSER,");
			sql.append("  OUTBUSITYPE,");
			sql.append("  PAYDATE,");
			sql.append("  PAYMAN,");
			sql.append("  PJ_JSFS,");
			sql.append("  PJ_NUM,");
			sql.append("  PJ_OID,");
			sql.append("  PREPAY,");
			sql.append("  PZGLH,");
			sql.append("  QCBZ,");
			sql.append("  QRR,");
			sql.append("  SCOMMENT,");
			sql.append("  SETTLENUM,");
			sql.append("  SFKR,");
			sql.append("  SHKJND,");
			sql.append("  SHKJQJ,");
			sql.append("  SHR,");
			sql.append("  SHRQ,");
			sql.append("  SHZD,");
			sql.append("  SPECFLAG,");
			sql.append("  SPZT,");
			sql.append("  SSBH,");
			sql.append("  SSCAUSE,");
			sql.append("  SXBZ,");
			sql.append("  SXKJND,");
			sql.append("  SXKJQJ,");
			sql.append("  SXR,");
			sql.append("  SXRQ,");
			sql.append("  TS,");
			sql.append("  VOUCHID,");
			sql.append("  XSLXBM,");
			sql.append("  YBJE,");
			sql.append("  YHQRKJND,");
			sql.append("  YHQRKJQJ,");
			sql.append("  YHQRR,");
			sql.append("  YHQRRQ,");
			sql.append("  YWBM,");
			sql.append("  ZDR,");
			sql.append("  ZDRQ,");
			sql.append("  ZGYF,");
			sql.append("  ZYX1,");
			sql.append("  ZYX10,");
			sql.append("  ZYX11,");
			sql.append("  ZYX12,");
			sql.append("  ZYX13,");
			sql.append("  ZYX14,");
			sql.append("  ZYX15,");
			sql.append("  ZYX16,");
			sql.append("  ZYX17,");
			sql.append("  ZYX18,");
			sql.append("  ZYX19,");
			sql.append("  ZYX2,");
			sql.append("  ZYX20,");
			sql.append("  ZYX21,");
			sql.append("  ZYX22,");
			sql.append("  ZYX23,");
			sql.append("  ZYX24,");
			sql.append("  ZYX25,");
			sql.append("  ZYX26,");
			sql.append("  ZYX27,");
			sql.append("  ZYX28,");
			sql.append("  ZYX29,");
			sql.append("  ZYX3,");
			sql.append("  ZYX30,");
			sql.append("  ZYX4,");
			sql.append("  ZYX5,");
			sql.append("  ZYX6,");
			sql.append("  ZYX7,");
			sql.append("  ZYX8,");
			sql.append("  ZYX9,");
			sql.append("  ZZZT,");
			sql.append("  FISKP,");
			sql.append("  FISSK,");
			sql.append("  DR ");
			sql.append(" from arap_djzb z");
			sql.append("  where z.djrq  >=to_char((sysdate - ").append(
					this.getDays() + "),'yyyy-mm-dd')");
			sql.append("  and substr(z.ts,1,10)>=to_char((sysdate - ").append(
					this.getBeforedays() + "),'yyyy-mm-dd')");
			sql.append("  and z.dwbm != '1020'");
			sql.append("  and z.dwbm != '1021'");
			sql.append("  and z.dwbm != '1023'");
			sql.append("  and z.dwbm != '1024'");
			sql.append("  and z.dwbm != '1032'");
			// System.out.println("查询sql:"+sql);
			pstNC = conNC.prepareStatement(sql.toString());
			restNC = pstNC.executeQuery();
			ResultSetMetaData rsmd = restNC.getMetaData();
			int resultcount = rsmd.getColumnCount();
			int tm = 0;
			while (restNC.next()) {
				StringBuilder insetSql = new StringBuilder();
				insetSql.append("insert into ODS_arap_djzb (");
				insetSql.append("  BBJE,");
				insetSql.append("  DDHBBM,");
				insetSql.append("  DJBH,");
				insetSql.append("  DJDL,");
				insetSql.append("  DJKJND,");
				insetSql.append("  DJKJQJ,");
				insetSql.append("  DJLXBM,");
				insetSql.append("  DJRQ,");
				insetSql.append("  DJZT,");
				insetSql.append("  DWBM,");
				insetSql.append("  DYVOUCHID,");
				insetSql.append("  DZRQ,");
				insetSql.append("  EFFECTDATE,");
				insetSql.append("  ENDUSER,");
				insetSql.append("  FBJE,");
				insetSql.append("  FJ,");
				insetSql.append("  FKTJBM,");
				insetSql.append("  HZBZ,");
				insetSql.append("  INNER_EFFECT_DATE,");
				insetSql.append("  ISJSZXZF,");
				insetSql.append("  ISNETREADY,");
				insetSql.append("  ISONLINEPAY,");
				insetSql.append("  ISPAID,");
				insetSql.append("  ISREDED,");
				insetSql.append("  ISSELECTEDPAY,");
				insetSql.append("  JSZXZF,");
				insetSql.append("  KMBM,");
				insetSql.append("  KSKHYH,");
				insetSql.append("  LASTSHR,");
				insetSql.append("  LASTTZR,");
				insetSql.append("  LRR,");
				insetSql.append("  LYBZ,");
				insetSql.append("  OFFICIALPRINTDATE,");
				insetSql.append("  OFFICIALPRINTUSER,");
				insetSql.append("  OUTBUSITYPE,");
				insetSql.append("  PAYDATE,");
				insetSql.append("  PAYMAN,");
				insetSql.append("  PJ_JSFS,");
				insetSql.append("  PJ_NUM,");
				insetSql.append("  PJ_OID,");
				insetSql.append("  PREPAY,");
				insetSql.append("  PZGLH,");
				insetSql.append("  QCBZ,");
				insetSql.append("  QRR,");
				insetSql.append("  SCOMMENT,");
				insetSql.append("  SETTLENUM,");
				insetSql.append("  SFKR,");
				insetSql.append("  SHKJND,");
				insetSql.append("  SHKJQJ,");
				insetSql.append("  SHR,");
				insetSql.append("  SHRQ,");
				insetSql.append("  SHZD,");
				insetSql.append("  SPECFLAG,");
				insetSql.append("  SPZT,");
				insetSql.append("  SSBH,");
				insetSql.append("  SSCAUSE,");
				insetSql.append("  SXBZ,");
				insetSql.append("  SXKJND,");
				insetSql.append("  SXKJQJ,");
				insetSql.append("  SXR,");
				insetSql.append("  SXRQ,");
				insetSql.append("  TS,");
				insetSql.append("  VOUCHID,");
				insetSql.append("  XSLXBM,");
				insetSql.append("  YBJE,");
				insetSql.append("  YHQRKJND,");
				insetSql.append("  YHQRKJQJ,");
				insetSql.append("  YHQRR,");
				insetSql.append("  YHQRRQ,");
				insetSql.append("  YWBM,");
				insetSql.append("  ZDR,");
				insetSql.append("  ZDRQ,");
				insetSql.append("  ZGYF,");
				insetSql.append("  ZYX1,");
				insetSql.append("  ZYX10,");
				insetSql.append("  ZYX11,");
				insetSql.append("  ZYX12,");
				insetSql.append("  ZYX13,");
				insetSql.append("  ZYX14,");
				insetSql.append("  ZYX15,");
				insetSql.append("  ZYX16,");
				insetSql.append("  ZYX17,");
				insetSql.append("  ZYX18,");
				insetSql.append("  ZYX19,");
				insetSql.append("  ZYX2,");
				insetSql.append("  ZYX20,");
				insetSql.append("  ZYX21,");
				insetSql.append("  ZYX22,");
				insetSql.append("  ZYX23,");
				insetSql.append("  ZYX24,");
				insetSql.append("  ZYX25,");
				insetSql.append("  ZYX26,");
				insetSql.append("  ZYX27,");
				insetSql.append("  ZYX28,");
				insetSql.append("  ZYX29,");
				insetSql.append("  ZYX3,");
				insetSql.append("  ZYX30,");
				insetSql.append("  ZYX4,");
				insetSql.append("  ZYX5,");
				insetSql.append("  ZYX6,");
				insetSql.append("  ZYX7,");
				insetSql.append("  ZYX8,");
				insetSql.append("  ZYX9,");
				insetSql.append("  ZZZT,");
				insetSql.append("  FISKP,");
				insetSql.append("  FISSK,");
				insetSql.append("  DR  ) values (");
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
			System.out.println("单据主表增量数据抽取完毕");
			System.out.println("结束时间为" + new Timestamp(new Date().getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("单据主表NC connection close");
			BaseDao.closeAll(pstNC, restNC, conNC);
			System.out.println("单据主表NC connection closed");

			System.out.println("单据主表BQ connection close");
			BaseDao.closeAll(pstBQ, restBQ, conBQ);
			System.out.println("单据主表BQ connection closed");
		}

	}
}
