package com.newapp.report.action;


import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class AnalysereportExcel {
	private WritableWorkbook wwb = null;
	public void processListToExcel(OutputStream stream,List datas){

		try{

			WritableWorkbook wwb =  Workbook.createWorkbook(stream);	
			WritableSheet ws = wwb.createSheet("单品销售分析", 0);
			Label l = new Label(0, 0, "品名", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(0,40);//设置列宽
		    
		    l = new Label(1, 0, "本期销量", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(1,30);//设置列宽
		    
		    l = new Label(2, 0, "上期销量", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(2,30);//设置列宽
		    
		    l = new Label(3, 0, "同期销量", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(3,30);//设置列宽
		    
		    l = new Label(4, 0, "销量环比增量", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(4,30);//设置列宽
		    
		    l = new Label(5, 0, "销量环比增量长率", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(5,30);//设置列宽
		    
		    l = new Label(6, 0, "销量同比增量", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(6,30);//设置列宽
		    
		    l = new Label(7, 0, "销量同比增量长率", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(7,30);//设置列宽
		    
		    l = new Label(8, 0, "本期销售额", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(8,30);//设置列宽
		    
		    l = new Label(9, 0, "上期销售额", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(9,30);//设置列宽
		    
		    l = new Label(10, 0, "同期销售额", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(10,30);//设置列宽
		    
		    l = new Label(11, 0, "销售额环比增量", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(11,30);//设置列宽
		    
		    l = new Label(12, 0, "销售额环比增量长率", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(12,30);//设置列宽
		    
		    l = new Label(13, 0, "销售额同比增量", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(13,30);//设置列宽
		    
		    l = new Label(14, 0, "销售额同比增量长率", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(14,30);//设置列宽
		    
		    l = new Label(15, 0, "成本", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(15,30);//设置列宽
		    
		    l = new Label(16, 0, "上期成本", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(16,30);//设置列宽
		    
		    l = new Label(17, 0, "同期成本", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(17,30);//设置列宽
		    
		    l = new Label(18, 0, "毛利", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(18,30);//设置列宽
		    
		    l = new Label(19, 0, "毛利率", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(1,30);//设置列宽
		    
		    l = new Label(20, 0, "毛利占比", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(19,30);//设置列宽
		    
		    l = new Label(21, 0, "退货金额", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(21,30);//设置列宽
		    
		    l = new Label(22, 0, "退货数量", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(22,30);//设置列宽
		    
		    l = new Label(23, 0, "预计下期销量", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(23,30);//设置列宽
		    
		    l = new Label(24, 0, "销售额占比", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(24,30);//设置列宽
		    
		    l = new Label(25, 0, "销售量占比", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(25,30);//设置列宽
		    
		    l = new Label(26, 0, "退货率", getTitle());
		    ws.addCell(l);
		    ws.setColumnView(26,30);//设置列宽
		    
		    
		    
		    Iterator rows = datas.iterator();
		    for(int i=0;rows.hasNext();i++){
		    	 Map mapdata = (Map)rows.next();
		    	 l = new Label(0, i+1, (String)mapdata.get("ITEMNAME"), getNormolCell());
				 ws.addCell(l);
				 
				 java.math.BigDecimal SALENUM = (java.math.BigDecimal)mapdata.get("SALENUM");//本期销量
				 java.math.BigDecimal HBSALENUM = (java.math.BigDecimal)mapdata.get("HBSALENUM");//上期销量
				 java.math.BigDecimal TBSALENUM = (java.math.BigDecimal)mapdata.get("TBSALENUM");//同期销量
				 
				 java.math.BigDecimal NUM1 = SALENUM.subtract(HBSALENUM);  //销量环比增量
				 java.math.BigDecimal NUM2 = NUM1.intValue()>0&&HBSALENUM.intValue()>0? NUM1.divide(HBSALENUM, 6, BigDecimal.ROUND_HALF_UP):new java.math.BigDecimal(0);//销量环比增量长率
			     java.math.BigDecimal NUM3 = SALENUM.subtract(TBSALENUM) ;//销量同比增量
				 java.math.BigDecimal NUM4 = NUM3.intValue()>0&&TBSALENUM.intValue()>0?NUM3.divide(TBSALENUM, 6, BigDecimal.ROUND_HALF_UP):new java.math.BigDecimal(0);//销量同比增量长率
				 //
				 
				 
				 l = new Label(1, i+1, SALENUM.toString(), getNormolCell());
				 ws.addCell(l);
				 l = new Label(2, i+1, HBSALENUM.toString(), getNormolCell());
				 ws.addCell(l);
				 l = new Label(3, i+1, TBSALENUM.toString(), getNormolCell());
				 ws.addCell(l);
				 
				 l = new Label(4, i+1, NUM1.toString(), getNormolCell());
				 ws.addCell(l);
				 l = new Label(5, i+1, NUM2.toString(), getNormolCell());
				 ws.addCell(l);
				 l = new Label(6, i+1, NUM3.toString(), getNormolCell());
				 ws.addCell(l);
				 l = new Label(7, i+1, NUM4.toString(), getNormolCell());
				 ws.addCell(l);
				 
				 
				 java.math.BigDecimal SALEMONEY = (java.math.BigDecimal)mapdata.get("SALEMONEY");//本期销售额
				 java.math.BigDecimal HBSALEMONEY = (java.math.BigDecimal)mapdata.get("HBSALEMONEY");//上期销售额
				 java.math.BigDecimal TBSALEMONEY = (java.math.BigDecimal)mapdata.get("TBSALEMONEY");//同期销售额
				 
				 java.math.BigDecimal MONEY1 = SALEMONEY.subtract(HBSALEMONEY);  //销售额环比增量
				 java.math.BigDecimal MONEY2 = MONEY1.intValue()>0&&HBSALEMONEY.intValue()>0? MONEY1.divide(HBSALEMONEY, 6, BigDecimal.ROUND_HALF_UP):new java.math.BigDecimal(0);//销售额环比增量长率
			     java.math.BigDecimal MONEY3 = SALENUM.subtract(TBSALENUM) ;//销售额同比增量
				 java.math.BigDecimal MONEY4 = MONEY3.intValue()>0&&TBSALEMONEY.intValue()>0?NUM3.divide(TBSALEMONEY, 6, BigDecimal.ROUND_HALF_UP):new java.math.BigDecimal(0);//销售额同比增量长率
				 
				 
				 l = new Label(8, i+1, SALEMONEY.toString(), getNormolCell());
				 ws.addCell(l);
				 l = new Label(9, i+1, HBSALEMONEY.toString(), getNormolCell());
				 ws.addCell(l);
				 l = new Label(10, i+1, TBSALEMONEY.toString(), getNormolCell());
				 ws.addCell(l);
				 
				 l = new Label(11, i+1, MONEY1.toString(), getNormolCell());
				 ws.addCell(l);
				 l = new Label(12, i+1, MONEY2.toString(), getNormolCell());
				 ws.addCell(l);
				 l = new Label(13, i+1, MONEY3.toString(), getNormolCell());
				 ws.addCell(l);
				 l = new Label(14, i+1, MONEY4.toString(), getNormolCell());
				 ws.addCell(l);
				 
			
				 
				 java.math.BigDecimal COSTMNY = (java.math.BigDecimal)mapdata.get("COSTMNY");//成本
				 java.math.BigDecimal HBCOSTMNY = (java.math.BigDecimal)mapdata.get("HBCOSTMNY");//上期成本
				 java.math.BigDecimal TBCOSTMNY = (java.math.BigDecimal)mapdata.get("TBCOSTMNY");//同期成本
				 java.math.BigDecimal GROSS = (java.math.BigDecimal)mapdata.get("GROSS");//毛利
				 
				 java.math.BigDecimal ALLGROSS = (java.math.BigDecimal)mapdata.get("ALLGROSS");//总毛利
				 
				 
				 java.math.BigDecimal GROSSLV =  GROSS.intValue()>0&& COSTMNY.intValue()>0? GROSS.divide(COSTMNY, 6, BigDecimal.ROUND_HALF_UP):new java.math.BigDecimal(0); // 毛利率
					 
					 
			     l = new Label(15, i+1, COSTMNY.toString(), getNormolCell());
				 ws.addCell(l);
				 l = new Label(16, i+1, HBCOSTMNY.toString(), getNormolCell());
				 ws.addCell(l);
				 l = new Label(17, i+1, TBCOSTMNY.toString(), getNormolCell());
				 ws.addCell(l);	
				 l = new Label(18, i+1, GROSS.toString(), getNormolCell());
				 ws.addCell(l);		
				 
				 l = new Label(19, i+1, GROSSLV.toString(), getNormolCell());
				 ws.addCell(l);	
				 
				 java.math.BigDecimal ALLMONEY = (java.math.BigDecimal)mapdata.get("ALLMONEY");//总销售额
				 java.math.BigDecimal ALLSALENUM = (java.math.BigDecimal)mapdata.get("ALLSALENUM");//总销售量
				 
				 java.math.BigDecimal GROSSLV1 = GROSS.intValue()>0&&ALLGROSS.intValue()>0?GROSS.divide(ALLGROSS, 6, BigDecimal.ROUND_HALF_UP):new java.math.BigDecimal(0);//毛利占比
				 
				 java.math.BigDecimal CANCELMONEY = (java.math.BigDecimal)mapdata.get("CANCELNUM");//退货金额
				 java.math.BigDecimal CANCELNUM = (java.math.BigDecimal)mapdata.get("CANCELNUM");//退货数量
				
				 java.math.BigDecimal YUJINUM =   SALENUM.multiply(new java.math.BigDecimal(1+NUM2.intValue()));//预计下期销量
					 
			     java.math.BigDecimal SALLV =	SALEMONEY.intValue()>0&&ALLMONEY.intValue()>0? SALEMONEY.divide(ALLMONEY, 6, BigDecimal.ROUND_HALF_UP):new java.math.BigDecimal(0);//销售额占比 
			     //java.math.BigDecimal SALLV1 =	SALENUM.intValue()>0&&ALLMONEY.intValue()>0? SALEMONEY.divide(ALLMONEY, 3, BigDecimal.ROUND_HALF_UP):new java.math.BigDecimal(0);//销售额占比 

			     l = new Label(20, i+1, GROSSLV1.toString(), getNormolCell());
				 ws.addCell(l);	
				 
				 l = new Label(21, i+1 , CANCELMONEY.toString(), getNormolCell());
				 ws.addCell(l);	
				 
				 l = new Label(22, i+1, CANCELNUM.toString(), getNormolCell());
				 ws.addCell(l);	
				 
				 l = new Label(23, i+1, YUJINUM.toString(), getNormolCell());
				 ws.addCell(l);	
				 
				 l = new Label(24, i+1, SALLV.toString(), getNormolCell());
				 ws.addCell(l);	
				 
				 java.math.BigDecimal SALLV2 =	SALENUM.intValue()>0&&ALLSALENUM.intValue()>0? SALENUM.divide(ALLSALENUM, 6, BigDecimal.ROUND_HALF_UP):new java.math.BigDecimal(0);//销售额占比 
				 l = new Label(25, i+1, SALLV2.toString(), getNormolCell());
				 ws.addCell(l);	
				 
				 java.math.BigDecimal TUILV = CANCELNUM.intValue()>0&&SALENUM.intValue()>0?CANCELNUM.divide(SALENUM.multiply(CANCELNUM), 6, BigDecimal.ROUND_HALF_UP):new java.math.BigDecimal(0);//销售额占比 
				 l = new Label(26, i+1, TUILV.toString(), getNormolCell());
				 ws.addCell(l);	
				 
				 
		    	
		    }
	
		    wwb.write();
		    wwb.close();


		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		

		
	}
	
	 /**
	  * 设置其他单元格样式
	  * @return
	  */
	 public static WritableCellFormat getNormolCell(){//12号字体,上下左右居中,带黑色边框
	  WritableFont font = new  WritableFont(WritableFont.TIMES, 12);
	  WritableCellFormat format = new  WritableCellFormat(font);
	  try {
	   format.setAlignment(jxl.format.Alignment.CENTRE);
	   format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
	   format.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);
	  } catch (WriteException e) {
	   // TODO 自动生成 catch 块
	   e.printStackTrace();
	  }
	  return format;
	 }

	
	/**
	  * 设置标题样式
	  * @return
	  */
	 public static WritableCellFormat getTitle(){
	  WritableFont font = new  WritableFont(WritableFont.TIMES, 14);
	  try {
	   font.setColour(Colour.BLUE);//蓝色字体
	  } catch (WriteException e1) {
	   // TODO 自动生成 catch 块
	   e1.printStackTrace();
	  }
	  WritableCellFormat format = new  WritableCellFormat(font);
	  
	  try {
	   format.setAlignment(jxl.format.Alignment.CENTRE);
	   format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
	   format.setBorder(Border.ALL,BorderLineStyle.THIN,Colour.BLACK);
	  } catch (WriteException e) {
	   // TODO 自动生成 catch 块
	   e.printStackTrace();
	  }
	  return format;
	 }
	 


}
