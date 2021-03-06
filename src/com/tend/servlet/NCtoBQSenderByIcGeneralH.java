package com.tend.servlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NCtoBQSenderByIcGeneralH extends BaseDao implements Runnable {

	public NCtoBQSenderByIcGeneralH() {
		System.out.println("NCtoBQSenderIcGeneralH 无参构造函数");
	}

	/**
	 * 自动执行的run方法
	 */
	public void run() {
		try {
			DateLoop(this.getNctobq_begindate(), this.getEnddate(),this.getNctobq_zday());
			System.out.println("出入库单主表数据抽取完成");
		} catch (Exception e) {
			System.out.println("出入库单主表抽取数据异常");
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
		System.out.println("出入库单主表时间区间："+sdf.format(date1)+" to "+sdf.format(datetemp));
		NCtoBQ(sdf.format(date1), sdf.format(datetemp));
		while(mm.dateCompare(datetemp , date2)){
			datetemp = mm.getDateAfterDay(datetemp, days);
			if(mm.dateCompare(date2  ,datetemp )){
				System.out.println("出入库单主表时间区间："+sdf.format(mm.getDateAfterDay(datetemp, -days))+" to "+sdf.format(date2));
				NCtoBQ(sdf.format(mm.getDateAfterDay(datetemp, -days)), sdf.format(date2));
			}else{
				System.out.println("出入库单主表时间区间："+sdf.format(mm.getDateAfterDay(datetemp, -days))+" to "+sdf.format(datetemp));
				NCtoBQ(sdf.format(mm.getDateAfterDay(datetemp, -days)), sdf.format(datetemp));
			}
		}
	}
	/**
	 * 抽取数据
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
		System.out.println("出入库单主表开始抽取数据................");
		System.out.println("开始时间为"+new Timestamp(new Date().getTime()));
		try {
			System.out.println("出入库单主表获取连接");
			conNC = this.getConForNC();
			conBQ = this.getConForBQ();
			System.out.println("出入库单主表获取连接成功");
			StringBuilder sql = new StringBuilder();
			sql.append("select                              ");
			sql.append("		  BASSETCARD        ,           ");
			sql.append("		  BDELIVEDTORM      ,           ");
			sql.append("		  BDIRECTTRANFLAG   ,           ");
			sql.append("		  BOUTRETFLAG       ,           ");
			sql.append("		  BSALECOOPPUR      ,           ");
			sql.append("		  CAUDITORID        ,           ");
			sql.append("		  CBILLTYPECODE     ,           ");
			sql.append("		  CBIZID            ,           ");
			sql.append("		  CBIZTYPE          ,           ");
			sql.append("		  CCUSTOMERID       ,           ");
			sql.append("		  CDILIVERTYPEID    ,           ");
			sql.append("		  CDISPATCHERID     ,           ");
			sql.append("		  CDPTID            ,           ");
			sql.append("		  CENDREPORTID      ,           ");
			sql.append("		  CGENERALHID       ,           ");
			sql.append("		  CINVENTORYID      ,           ");
			sql.append("		  CLASTMODIID       ,           ");
			sql.append("		  COPERATORID       ,           ");
			sql.append("		  COTHERCALBODYID   ,           ");
			sql.append("		  COTHERCORPID      ,           ");
			sql.append("		  COTHERWHID        ,           ");
			sql.append("		  COUTCALBODYID     ,           ");
			sql.append("		  COUTCORPID        ,           ");
			sql.append("		  CPROVIDERID       ,           ");
			sql.append("		  CREGISTER         ,           ");
			sql.append("		  CSETTLEPATHID     ,           ");
			sql.append("		  CTRANCUSTID       ,           ");
			sql.append("		  CWAREHOUSEID      ,           ");
			sql.append("		  CWASTEWAREHOUSEID ,           ");
			sql.append("		  CWHSMANAGERID     ,           ");
			sql.append("		  DACCOUNTDATE      ,           ");
			sql.append("		  DAUDITDATE        ,           ");
			sql.append("		  DBILLDATE         ,           ");
			sql.append("		  FALLOCFLAG        ,           ");
			sql.append("		  FBILLFLAG         ,           ");
			sql.append("		  FREPLENISHFLAG    ,           ");
			sql.append("		  FSPECIALFLAG      ,           ");
			sql.append("		  IPRINTCOUNT       ,           ");
			sql.append("		  NDISCOUNTMNY      ,           ");
			sql.append("		  NNETMNY           ,           ");
			sql.append("		  PK_CALBODY        ,           ");
			sql.append("		  PK_CORP           ,           ");
			sql.append("		  PK_CUBASDOC       ,           ");
			sql.append("		  PK_CUBASDOCC      ,           ");
			sql.append("		  PK_MEASWARE       ,           ");
			sql.append("		  PK_PURCORP        ,           ");
			sql.append("		  TACCOUNTTIME      ,           ");
			sql.append("		  TLASTMODITIME     ,           ");
			sql.append("		  TMAKETIME         ,           ");
			sql.append("		  TS                ,           ");
			sql.append("		  VBILLCODE         ,           ");
			sql.append("		  VDILIVERADDRESS   ,           ");
			sql.append("		  VHEADNOTE2        ,           ");
			sql.append("		  VNOTE             ,           ");
			sql.append("		  VUSERDEF1         ,           ");
			sql.append("		  VUSERDEF10        ,           ");
			sql.append("		  VUSERDEF11        ,           ");
			sql.append("		  VUSERDEF12        ,           ");
			sql.append("		  VUSERDEF13        ,           ");
			sql.append("		  VUSERDEF14        ,           ");
			sql.append("		  VUSERDEF15        ,           ");
			sql.append("		  VUSERDEF16        ,           ");
			sql.append("		  VUSERDEF17        ,           ");
			sql.append("		  VUSERDEF18        ,           ");
			sql.append("		  VUSERDEF19        ,           ");
			sql.append("		  VUSERDEF2         ,           ");
			sql.append("		  VUSERDEF20        ,           ");
			sql.append("		  VUSERDEF3         ,           ");
			sql.append("		  VUSERDEF4         ,           ");
			sql.append("		  VUSERDEF5         ,           ");
			sql.append("		  VUSERDEF6         ,           ");
			sql.append("		  VUSERDEF7         ,           ");
			sql.append("		  VUSERDEF8         ,           ");
			sql.append("		  VUSERDEF9         ,            ");
			sql.append("		  DR                     ");
			sql.append("		  from ic_general_h gh          ");
			sql.append("		  where gh.ts >= '").append(beginDate+"'");
			sql.append("		  and gh.ts <= '").append(endDate+"'");
			sql.append("		  and gh.pk_corp != '1020'       ");
			sql.append("		  and gh.pk_corp != '1021'       ");
			sql.append("		  and gh.pk_corp != '1023'       ");
			sql.append("		  and gh.pk_corp != '1024'       ");
			sql.append("		  and gh.pk_corp != '1032'       ");
			//System.out.println("查询sql:"+sql);
			pstNC = conNC.prepareStatement(sql.toString());
			restNC = pstNC.executeQuery();
			ResultSetMetaData rsmd = restNC.getMetaData();
			int resultcount = rsmd.getColumnCount();
			
			while(restNC.next()){
				StringBuilder insetSql = new StringBuilder();
				insetSql.append("insert into ODS_IC_GENERAL_H (BASSETCARD        ,");
				insetSql.append("	  BDELIVEDTORM      ,                        ");
				insetSql.append("	  BDIRECTTRANFLAG   ,                        ");
				insetSql.append("	  BOUTRETFLAG       ,                        ");
				insetSql.append("	  BSALECOOPPUR      ,                        ");
				insetSql.append("	  CAUDITORID        ,                        ");
				insetSql.append("	  CBILLTYPECODE     ,                        ");
				insetSql.append("	  CBIZID            ,                        ");
				insetSql.append("	  CBIZTYPE          ,                        ");
				insetSql.append("	  CCUSTOMERID       ,                        ");
				insetSql.append("	  CDILIVERTYPEID    ,                        ");
				insetSql.append("	  CDISPATCHERID     ,                        ");
				insetSql.append("	  CDPTID            ,                        ");
				insetSql.append("	  CENDREPORTID      ,                        ");
				insetSql.append("	  CGENERALHID       ,                        ");
				insetSql.append("	  CINVENTORYID      ,                        ");
				insetSql.append("	  CLASTMODIID       ,                        ");
				insetSql.append("	  COPERATORID       ,                        ");
				insetSql.append("	  COTHERCALBODYID   ,                        ");
				insetSql.append("	  COTHERCORPID      ,                        ");
				insetSql.append("	  COTHERWHID        ,                        ");
				insetSql.append("	  COUTCALBODYID     ,                        ");
				insetSql.append("	  COUTCORPID        ,                        ");
				insetSql.append("	  CPROVIDERID       ,                        ");
				insetSql.append("	  CREGISTER         ,                        ");
				insetSql.append("	  CSETTLEPATHID     ,                        ");
				insetSql.append("	  CTRANCUSTID       ,                        ");
				insetSql.append("	  CWAREHOUSEID      ,                        ");
				insetSql.append("	  CWASTEWAREHOUSEID ,                        ");
				insetSql.append("	  CWHSMANAGERID     ,                        ");
				insetSql.append("	  DACCOUNTDATE      ,                        ");
				insetSql.append("	  DAUDITDATE        ,                        ");
				insetSql.append("	  DBILLDATE         ,                        ");
				insetSql.append("	  FALLOCFLAG        ,                        ");
				insetSql.append("	  FBILLFLAG         ,                        ");
				insetSql.append("	  FREPLENISHFLAG    ,                        ");
				insetSql.append("	  FSPECIALFLAG      ,                        ");
				insetSql.append("	  IPRINTCOUNT       ,                        ");
				insetSql.append("	  NDISCOUNTMNY      ,                        ");
				insetSql.append("	  NNETMNY           ,                        ");
				insetSql.append("	  PK_CALBODY        ,                        ");
				insetSql.append("	  PK_CORP           ,                        ");
				insetSql.append("	  PK_CUBASDOC       ,                        ");
				insetSql.append("	  PK_CUBASDOCC      ,                        ");
				insetSql.append("	  PK_MEASWARE       ,                        ");
				insetSql.append("	  PK_PURCORP        ,                        ");
				insetSql.append("	  TACCOUNTTIME      ,                        ");
				insetSql.append("	  TLASTMODITIME     ,                        ");
				insetSql.append("	  TMAKETIME         ,                        ");
				insetSql.append("	  TS                ,                        ");
				insetSql.append("	  VBILLCODE         ,                        ");
				insetSql.append("	  VDILIVERADDRESS   ,                        ");
				insetSql.append("	  VHEADNOTE2        ,                        ");
				insetSql.append("	  VNOTE             ,                        ");
				insetSql.append("	  VUSERDEF1         ,                        ");
				insetSql.append("	  VUSERDEF10        ,                        ");
				insetSql.append("	  VUSERDEF11        ,                        ");
				insetSql.append("	  VUSERDEF12        ,                        ");
				insetSql.append("	  VUSERDEF13        ,                        ");
				insetSql.append("	  VUSERDEF14        ,                        ");
				insetSql.append("	  VUSERDEF15        ,                        ");
				insetSql.append("	  VUSERDEF16        ,                        ");
				insetSql.append("	  VUSERDEF17        ,                        ");
				insetSql.append("	  VUSERDEF18        ,                        ");
				insetSql.append("	  VUSERDEF19        ,                        ");
				insetSql.append("	  VUSERDEF2         ,                        ");
				insetSql.append("	  VUSERDEF20        ,                        ");
				insetSql.append("	  VUSERDEF3         ,                        ");
				insetSql.append("	  VUSERDEF4         ,                        ");
				insetSql.append("	  VUSERDEF5         ,                        ");
				insetSql.append("	  VUSERDEF6         ,                        ");
				insetSql.append("	  VUSERDEF7         ,                        ");
				insetSql.append("	  VUSERDEF8         ,                        ");
				insetSql.append("	  VUSERDEF9         ,              ");
				insetSql.append("	  DR          ) values (              ");
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
					//System.out.println(insetSql);
					try {
						//执行存入数据
						pstBQ = conBQ.prepareStatement(insetSql.toString());
						boolean result = pstBQ.execute();
						if(!result){
							//System.out.println("保存成功");
						}else{
							//System.out.println("保存失败");
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
			 }                                                         
			System.out.println("出入库单主表数据抽取完毕");
			System.out.println("结束时间为"+new Timestamp(new Date().getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("出入库单主表NC connection close");
			BaseDao.closeAll(pstNC, restNC, conNC);
			System.out.println("出入库单主表NC connection closed");
			
			System.out.println("出入库单主表BQ connection close");
			BaseDao.closeAll(pstBQ, restBQ, conBQ);
			System.out.println("出入库单主表BQ connection closed");
		}

	}
}
