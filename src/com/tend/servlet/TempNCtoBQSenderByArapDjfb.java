package com.tend.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;




/**
 * 单据辅表增量数据抽取
 * @author Administrator
 *
 */
public class TempNCtoBQSenderByArapDjfb extends BaseDao implements Runnable {

	public TempNCtoBQSenderByArapDjfb() {
		System.out.println("单据辅表增量数据抽取--无参构造函数");
	}
	public void run() {
		try {
			DeleteDate();//清空ods表数据
			DateLoop("2013-03-28", "2013-04-03",1);
			System.out.println("单据辅表增量数据抽取完成");
		} catch (Exception e) {
			System.out.println("单据辅表抽取增量数据异常");
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
		System.out.println("单据主表时间区间："+sdf.format(date1)+" to "+sdf.format(datetemp));
		NCtoBQ(sdf.format(date1), sdf.format(datetemp));
		while(mm.dateCompare(datetemp , date2)){
			datetemp = mm.getDateAfterDay(datetemp, days);
			if(mm.dateCompare(date2  ,datetemp )){
				System.out.println("单据主表时间区间："+sdf.format(mm.getDateAfterDay(datetemp, -days+1))+" to "+sdf.format(date2));
				NCtoBQ(sdf.format(mm.getDateAfterDay(datetemp, -days+1)), sdf.format(date2));
			}else{
				System.out.println("单据主表时间区间："+sdf.format(mm.getDateAfterDay(datetemp, -days+1))+" to "+sdf.format(datetemp));
				NCtoBQ(sdf.format(mm.getDateAfterDay(datetemp, -days+1)), sdf.format(datetemp));
			}
		}
	}
	/**
	 * 清空表数据
	 * @throws Exception
	 */
	public void DeleteDate() throws Exception {
		String sql = "delete ods_arap_djfb";
		boolean result = this.excuteDelete(sql);
		if(!result){
			System.out.println("操作成功");
		}else{
			System.out.println("操作失败");
		}
	}
	/**
	 * 抽取增量数据
	 * 
	 * @throws Exception
	 */
	public void NCtoBQ(String start,String end) throws Exception {
		Connection conNC = null;
		PreparedStatement pstNC = null;
		ResultSet restNC = null;
		Connection conBQ = null;
		PreparedStatement pstBQ = null;
		ResultSet restBQ = null;
		System.out.println("单据辅表开始抽取增量数据................");
		System.out.println("开始时间为"+new Timestamp(new Date().getTime()));
		try {
			System.out.println("单据辅表获取连接");
			conNC = this.getConForNC();
			conBQ = this.getConForBQ();
			System.out.println("单据辅表获取连接成功");
			StringBuilder sql = new StringBuilder();
			sql.append("select ");
			sql.append("  ACCOUNTID,");
			sql.append("  ASSETPACTNO,");
			sql.append("  BANKROLLPROJET,");
			sql.append("  BBHL,");
			sql.append("  BBPJLX,");
			sql.append("  BBTXFY,");
			sql.append("  BBYE,");
			sql.append("  BFYHZH,");
			sql.append("  BILLDATE,");
			sql.append("  BJDWHSDJ,");
			sql.append("  BJDWSL,");
			sql.append("  BJDWWSDJ,");
			sql.append("  BJJLDW,");
			sql.append("  BZ_DATE,");
			sql.append("  BZ_KJND,");
			sql.append("  BZ_KJQJ,");
			sql.append("  BZBM,");
			sql.append("  CASHITEM,");
			sql.append("  CHBM_CL,");
			sql.append("  CHECKFLAG,");
			sql.append("  CHMC,");
			sql.append("  CINVENTORYID,");
			sql.append("  CKBM,");
			sql.append("  CKDH,");
			sql.append("  CKDID,");
			sql.append("  CKSQSH,");
			sql.append("  CLBH,");
			sql.append("  COMMONFLAG,");
			sql.append("  CONTRACTNO,");
			sql.append("  CTZHTLX_PK,");
			sql.append("  DDH,");
			sql.append("  DDHH,");
			sql.append("  DDHID,");
			sql.append("  DDLX,");
			sql.append("  DEPTID,");
			sql.append("  DFBBJE,");
			sql.append("  DFBBSJ,");
			sql.append("  DFBBWSJE,");
			sql.append("  DFFBJE,");
			sql.append("  DFFBSJ,");
			sql.append("  DFJS,");
			sql.append("  DFSHL,");
			sql.append("  DFYBJE,");
			sql.append("  DFYBSJ,");
			sql.append("  DFYBWSJE,");
			sql.append("  DFYHZH,");
			sql.append("  DJ,");
			sql.append("  DJBH,");
			sql.append("  DJDL,");
			sql.append("  DJLXBM,");
			sql.append("  DJXTFLAG,");
			sql.append("  DSTLSUBCS,");
			sql.append("  DWBM,");
			sql.append("  ENCODE,");
			sql.append("  EQUIPMENTCODE,");
			sql.append("  FACARDBH,");
			sql.append("  FB_OID,");
			sql.append("  FBHL,");
			sql.append("  FBPJLX,");
			sql.append("  FBTXFY,");
			sql.append("  FBYE,");
			sql.append("  FJLDW,");
			sql.append("  FKYHDZ,");
			sql.append("  FKYHMC,");
			sql.append("  FLBH,");
			sql.append("  FPH,");
			sql.append("  FPHID,");
			sql.append("  FREEITEMID,");
			sql.append("  FX,");
			sql.append("  GGXH,");
			sql.append("  GROUPNUM,");
			sql.append("  HBBM,");
			sql.append("  HSDJ,");
			sql.append("  HSL,");
			sql.append("  HTBH,");
			sql.append("  HTMC,");
			sql.append("  INNERORDERNO,");
			sql.append("  ISSFKXYCHANGED,");
			sql.append("  ISVERIFYFINISHED,");
			sql.append("  ITEM_BILL_PK,");
			sql.append("  ITEMSTYLE,");
			sql.append("  JFBBJE,");
			sql.append("  JFBBSJ,");
			sql.append("  JFFBJE,");
			sql.append("  JFFBSJ,");
			sql.append("  JFJS,");
			sql.append("  JFSHL,");
			sql.append("  JFYBJE,");
			sql.append("  JFYBSJ,");
			sql.append("  JFYBWSJE,");
			sql.append("  JFZKFBJE,");
			sql.append("  JFZKYBJE,");
			sql.append("  JOBID,");
			sql.append("  JOBPHASEID,");
			sql.append("  JSFSBM,");
			sql.append("  JSHJ,");
			sql.append("  KMBM,");
			sql.append("  KPRQ,");
			sql.append("  KSBM_CL,");
			sql.append("  KSLB,");
			sql.append("  KXYT,");
			sql.append("  NOTETYPE,");
			sql.append("  OCCUPATIONMNY,");
			sql.append("  OLD_FLAG,");
			sql.append("  OLD_SYS_FLAG,");
			sql.append("  ORDERCUSMANDOC,");
			sql.append("  OTHERSYSFLAG,");
			sql.append("  PAUSETRANSACT,");
			sql.append("  PAYDATE,");
			sql.append("  PAYFLAG,");
			sql.append("  PAYMAN,");
			sql.append("  PCH,");
			sql.append("  PH,");
			sql.append("  PJ_JSFS,");
			sql.append("  PJDIRECTION,");
			sql.append("  PJH,");
			sql.append("  PK_JOBOBJPHA,");
			sql.append("  PRODUCEORDER,");
			sql.append("  PRODUCTLINE,");
			sql.append("  PZFLH,");
			sql.append("  QXRQ,");
			sql.append("  SANHU,");
			sql.append("  SEQNUM,");
			sql.append("  SFBZ,");
			sql.append("  SFKXYH,");
			sql.append("  SHLYE,");
			sql.append("  SKYHDZ,");
			sql.append("  SKYHMC,");
			sql.append("  SL,");
			sql.append("  SPZT,");
			sql.append("  SRBZ,");
			sql.append("  SZXMID,");
			sql.append("  TASK,");
			sql.append("  TAX_NUM,");
			sql.append("  TBBH,");
			sql.append("  TS,");
			sql.append("  TXLX_BBJE,");
			sql.append("  TXLX_FBJE,");
			sql.append("  TXLX_YBJE,");
			sql.append("  USEDEPT,");
			sql.append("  VERIFYFINISHEDDATE,");
			sql.append("  VOUCHID,");
			sql.append("  WBFBBJE,");
			sql.append("  WBFFBJE,");
			sql.append("  WBFYBJE,");
			sql.append("  WLDX,");
			sql.append("  XBBM3,");
			sql.append("  XGBH,");
			sql.append("  XM,");
			sql.append("  XMBM2,");
			sql.append("  XMBM4,");
			sql.append("  XMYS,");
			sql.append("  XYZH,");
			sql.append("  YBPJLX,");
			sql.append("  YBTXFY,");
			sql.append("  YBYE,");
			sql.append("  YCSKRQ,");
			sql.append("  YSBBYE,");
			sql.append("  YSFBYE,");
			sql.append("  YSYBYE,");
			sql.append("  YWBM,");
			sql.append("  YWXZ,");
			sql.append("  YWYBM,");
			sql.append("  ZJLDW,");
			sql.append("  ZKL,");
			sql.append("  ZRDEPTID,");
			sql.append("  ZY,");
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
			sql.append("  ZYX9, ");
			sql.append("  DR ");
			sql.append(" from arap_djfb f");
			sql.append("  where f.vouchid in (select z.vouchid from arap_djzb z ");
			sql.append("  where z.djrq >=to_char((sysdate - 180),'yyyy-mm-dd')");
			sql.append("  and substr(z.ts,1,10)>='").append(start+"'");
			sql.append("  and substr(z.ts,1,10)<='").append(start+"'");
			sql.append("  and z.dwbm != '1020'");
			sql.append("  and z.dwbm != '1021'");
			sql.append("  and z.dwbm != '1023'");
			sql.append("  and z.dwbm != '1024'"); 
			sql.append("  and z.dwbm != '1032' )");
			
			System.out.println("查询sql:"+sql);
			pstNC = conNC.prepareStatement(sql.toString());
			restNC = pstNC.executeQuery();
			ResultSetMetaData rsmd = restNC.getMetaData();
			int resultcount = rsmd.getColumnCount();
			int tm = 0;
			while(restNC.next()){
				StringBuilder insetSql = new StringBuilder();
				insetSql.append("  insert into ODS_arap_djfb (");
				insetSql.append("  ACCOUNTID,");
				insetSql.append("  ASSETPACTNO,");
				insetSql.append("  BANKROLLPROJET,");
				insetSql.append("  BBHL,");
				insetSql.append("  BBPJLX,");
				insetSql.append("  BBTXFY,");
				insetSql.append("  BBYE,");
				insetSql.append("  BFYHZH,");
				insetSql.append("  BILLDATE,");
				insetSql.append("  BJDWHSDJ,");
				insetSql.append("  BJDWSL,");
				insetSql.append("  BJDWWSDJ,");
				insetSql.append("  BJJLDW,");
				insetSql.append("  BZ_DATE,");
				insetSql.append("  BZ_KJND,");
				insetSql.append("  BZ_KJQJ,");
				insetSql.append("  BZBM,");
				insetSql.append("  CASHITEM,");
				insetSql.append("  CHBM_CL,");
				insetSql.append("  CHECKFLAG,");
				insetSql.append("  CHMC,");
				insetSql.append("  CINVENTORYID,");
				insetSql.append("  CKBM,");
				insetSql.append("  CKDH,");
				insetSql.append("  CKDID,");
				insetSql.append("  CKSQSH,");
				insetSql.append("  CLBH,");
				insetSql.append("  COMMONFLAG,");
				insetSql.append("  CONTRACTNO,");
				insetSql.append("  CTZHTLX_PK,");
				insetSql.append("  DDH,");
				insetSql.append("  DDHH,");
				insetSql.append("  DDHID,");
				insetSql.append("  DDLX,");
				insetSql.append("  DEPTID,");
				insetSql.append("  DFBBJE,");
				insetSql.append("  DFBBSJ,");
				insetSql.append("  DFBBWSJE,");
				insetSql.append("  DFFBJE,");
				insetSql.append("  DFFBSJ,");
				insetSql.append("  DFJS,");
				insetSql.append("  DFSHL,");
				insetSql.append("  DFYBJE,");
				insetSql.append("  DFYBSJ,");
				insetSql.append("  DFYBWSJE,");
				insetSql.append("  DFYHZH,");
				insetSql.append("  DJ,");
				insetSql.append("  DJBH,");
				insetSql.append("  DJDL,");
				insetSql.append("  DJLXBM,");
				insetSql.append("  DJXTFLAG,");
				insetSql.append("  DSTLSUBCS,");
				insetSql.append("  DWBM,");
				insetSql.append("  ENCODE,");
				insetSql.append("  EQUIPMENTCODE,");
				insetSql.append("  FACARDBH,");
				insetSql.append("  FB_OID,");
				insetSql.append("  FBHL,");
				insetSql.append("  FBPJLX,");
				insetSql.append("  FBTXFY,");
				insetSql.append("  FBYE,");
				insetSql.append("  FJLDW,");
				insetSql.append("  FKYHDZ,");
				insetSql.append("  FKYHMC,");
				insetSql.append("  FLBH,");
				insetSql.append("  FPH,");
				insetSql.append("  FPHID,");
				insetSql.append("  FREEITEMID,");
				insetSql.append("  FX,");
				insetSql.append("  GGXH,");
				insetSql.append("  GROUPNUM,");
				insetSql.append("  HBBM,");
				insetSql.append("  HSDJ,");
				insetSql.append("  HSL,");
				insetSql.append("  HTBH,");
				insetSql.append("  HTMC,");
				insetSql.append("  INNERORDERNO,");
				insetSql.append("  ISSFKXYCHANGED,");
				insetSql.append("  ISVERIFYFINISHED,");
				insetSql.append("  ITEM_BILL_PK,");
				insetSql.append("  ITEMSTYLE,");
				insetSql.append("  JFBBJE,");
				insetSql.append("  JFBBSJ,");
				insetSql.append("  JFFBJE,");
				insetSql.append("  JFFBSJ,");
				insetSql.append("  JFJS,");
				insetSql.append("  JFSHL,");
				insetSql.append("  JFYBJE,");
				insetSql.append("  JFYBSJ,");
				insetSql.append("  JFYBWSJE,");
				insetSql.append("  JFZKFBJE,");
				insetSql.append("  JFZKYBJE,");
				insetSql.append("  JOBID,");
				insetSql.append("  JOBPHASEID,");
				insetSql.append("  JSFSBM,");
				insetSql.append("  JSHJ,");
				insetSql.append("  KMBM,");
				insetSql.append("  KPRQ,");
				insetSql.append("  KSBM_CL,");
				insetSql.append("  KSLB,");
				insetSql.append("  KXYT,");
				insetSql.append("  NOTETYPE,");
				insetSql.append("  OCCUPATIONMNY,");
				insetSql.append("  OLD_FLAG,");
				insetSql.append("  OLD_SYS_FLAG,");
				insetSql.append("  ORDERCUSMANDOC,");
				insetSql.append("  OTHERSYSFLAG,");
				insetSql.append("  PAUSETRANSACT,");
				insetSql.append("  PAYDATE,");
				insetSql.append("  PAYFLAG,");
				insetSql.append("  PAYMAN,");
				insetSql.append("  PCH,");
				insetSql.append("  PH,");
				insetSql.append("  PJ_JSFS,");
				insetSql.append("  PJDIRECTION,");
				insetSql.append("  PJH,");
				insetSql.append("  PK_JOBOBJPHA,");
				insetSql.append("  PRODUCEORDER,");
				insetSql.append("  PRODUCTLINE,");
				insetSql.append("  PZFLH,");
				insetSql.append("  QXRQ,");
				insetSql.append("  SANHU,");
				insetSql.append("  SEQNUM,");
				insetSql.append("  SFBZ,");
				insetSql.append("  SFKXYH,");
				insetSql.append("  SHLYE,");
				insetSql.append("  SKYHDZ,");
				insetSql.append("  SKYHMC,");
				insetSql.append("  SL,");
				insetSql.append("  SPZT,");
				insetSql.append("  SRBZ,");
				insetSql.append("  SZXMID,");
				insetSql.append("  TASK,");
				insetSql.append("  TAX_NUM,");
				insetSql.append("  TBBH,");
				insetSql.append("  TS,");
				insetSql.append("  TXLX_BBJE,");
				insetSql.append("  TXLX_FBJE,");
				insetSql.append("  TXLX_YBJE,");
				insetSql.append("  USEDEPT,");
				insetSql.append("  VERIFYFINISHEDDATE,");
				insetSql.append("  VOUCHID,");
				insetSql.append("  WBFBBJE,");
				insetSql.append("  WBFFBJE,");
				insetSql.append("  WBFYBJE,");
				insetSql.append("  WLDX,");
				insetSql.append("  XBBM3,");
				insetSql.append("  XGBH,");
				insetSql.append("  XM,");
				insetSql.append("  XMBM2,");
				insetSql.append("  XMBM4,");
				insetSql.append("  XMYS,");
				insetSql.append("  XYZH,");
				insetSql.append("  YBPJLX,");
				insetSql.append("  YBTXFY,");
				insetSql.append("  YBYE,");
				insetSql.append("  YCSKRQ,");
				insetSql.append("  YSBBYE,");
				insetSql.append("  YSFBYE,");
				insetSql.append("  YSYBYE,");
				insetSql.append("  YWBM,");
				insetSql.append("  YWXZ,");
				insetSql.append("  YWYBM,");
				insetSql.append("  ZJLDW,");
				insetSql.append("  ZKL,");
				insetSql.append("  ZRDEPTID,");
				insetSql.append("  ZY,");
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
						//System.out.println(insetSql);
					}
					try {
						//执行存入增量数据
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
			System.out.println("单据辅表增量数据抽取完毕");
			System.out.println("结束时间为"+new Timestamp(new Date().getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("单据辅表NC connection close");
			BaseDao.closeAll(pstNC, restNC, conNC);
			System.out.println("单据辅表NC connection closed");
			
			System.out.println("单据辅表BQ connection close");
			BaseDao.closeAll(pstBQ, restBQ, conBQ);
			System.out.println("单据辅表BQ connection closed");
		}

	}
}
