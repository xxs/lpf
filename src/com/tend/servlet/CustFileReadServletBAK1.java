package com.tend.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class CustFileReadServletBAK1 extends HttpServlet{
	private static final long serialVersionUID = -4464318344629260022L;
	String poNum = "";
	static String aa = "";
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	public CustFileReadServletBAK1(){
		aa = "";
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding( "gb2312"); 
		resp.setContentType( "text/html;   charset=gb2312 "); 
		resp.setHeader("Cache-Control", "no-cache");
		PrintWriter out = resp.getWriter();
		String orderquantity = "";
		String storenum = "";
		String invdesc = "";
		String orderprice = "";
		String itemspec = "";
		SAXReader reader = new SAXReader();
		Document document = null;
		String sysuser = req.getParameter("sysuser");
		String unitname = req.getParameter("unitname");
		String custnameid = req.getParameter("custnameid");
		if(custnameid.equals("2")){
			try {
				document = reader.read(new File(req.getRealPath(req.getParameter("paper"))));
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			CustFileReadServletBAK1 t = new CustFileReadServletBAK1();
			t.bar(root);
			String bb = aa;
			String[] firstarr = bb.split("&&&");
			String[] arrbb = firstarr[0].split("###");
			String ordernum = firstarr[2];
			String corpname = firstarr[1];
			try{
				Connection conn = DBOracleconn.getDBConn();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select ordercode from oermorder where ordercode='"+ordernum+"' and pk_corp = '"+unitname+"'");
				if(rs.next()){
					out.print("<script language=\"javascript\" >");
			        out.print("window.alert(\"此订单号已存在!\");");
			        out.print("window.close();");
			        out.print("document.location.href='/synear/Operation/orderimp.aspx';");
			        out.print("</script>");
				}else{
					for(int k = 0 ; k < arrbb.length;k++){
						String[] childarr = arrbb[k].split(";");
						for(int m = 0 ; m < childarr[0].split("----").length; m ++){
							String starr = childarr[0].split("----")[m];
							for(int n = 0 ; n < starr.split(",").length; n ++){
								orderquantity = starr.split(",")[0];
								storenum = starr.split(",")[1];
								invdesc = childarr[1].split(",")[2];
								orderprice = childarr[1].split(",")[0];
								itemspec = childarr[1].split(",")[1];
							}//System.out.println("itemspec="+itemspec+"**orderprice="+orderprice+"**orderquantity="+orderquantity+"**storenum="+storenum+"**invdesc="+invdesc+"**ordernum="+ordernum+"**corpname="+corpname);
					    	try {
								String sql = "insert into oermorder(invname,store,qty,username,tiaoma,inspec,price,ordercode,pk_corp,custnameid)";
								sql += " values('"+new String(invdesc.getBytes(),"UTF-8")+"','"+storenum+"','"+orderquantity+"','"+sysuser+"','','"+itemspec+"','"+orderprice+"','"+ordernum+"','"+unitname+"','"+custnameid+"')";
								stmt.addBatch(sql);
							} catch (SQLException e) {
								e.printStackTrace();
							}finally{
								try {
									stmt.executeBatch();
						            conn.commit();
									out.print("<script language=\"javascript\" >");
							        out.print("window.alert(\"数据导入成功!\");");
							        out.print("window.close();");
							        out.print("document.location.href='/synear/Operation/orderimp.aspx';");
							        out.print("</script>");
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
					DBOracleconn.closeDBconn();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else if(custnameid.equals("3")){
			InputStream is = null;
			try {
				is = new FileInputStream(new File(req.getRealPath(req.getParameter("paper"))));
				HSSFWorkbook workBook = new HSSFWorkbook(is);
				HSSFSheet sheet = workBook.getSheetAt(0);
			    int rows = sheet.getPhysicalNumberOfRows();
				String corpname = "";
				String ordertype = "";
				String orderno = "";
				String zhidandate = "";
				String storeno = "";
				String storename = "";
				String vendorcode = "";
				String vendorname = "";
				String deptname = "";
				String remark = "";
				String invcode = "";
				String invhuohao = "";
				String invtiaoma = "";
				String itemspec1 = "";
				String invname = "";
				String orderjianshu = "";
				String orderquant = "";
				String hanshuimoney = "";
				String wushuimoney = "";
				String tihi = "";
				String bodyremark = "";
				sheet.getMargin(HSSFSheet.TopMargin); 
	            HSSFRow row00 = sheet.getRow(0);
	            HSSFCell cel00 = row00.getCell((short) 0); 
	            corpname = cel00.getRichStringCellValue().toString();
	            
	            HSSFRow row01 = sheet.getRow(1); 
	            HSSFCell cel01 = row01.getCell((short) 0); 
	            ordertype = cel01.getRichStringCellValue().toString();
	            
	            HSSFRow row02 = sheet.getRow(2);
	            HSSFCell cel02 = row02.getCell((short) 0); 
	            orderno = cel02.getRichStringCellValue().toString();
	            if(orderno.indexOf("单据号：")!= -1){
					orderno = orderno.substring("单据号：".length());
	            }
	            HSSFCell cel12 = row02.getCell((short) 1); 
	            zhidandate = cel12.getRichStringCellValue().toString();
	            
	            HSSFRow row03 = sheet.getRow(3);
	            HSSFCell cel03 = row03.getCell((short) 0); 
	            storeno = cel03.getRichStringCellValue().toString();
	            storeno = storeno.substring("连锁店编码：".length());
	            HSSFCell cel13 = row03.getCell((short) 1); 
	            storename = cel13.getRichStringCellValue().toString();
	            
	            HSSFRow row04 = sheet.getRow(4);
	            HSSFCell cel04 = row04.getCell((short) 0); 
	            vendorcode = cel04.getRichStringCellValue().toString();
	            HSSFCell cel14 = row04.getCell((short) 1); 
	            vendorname = cel14.getRichStringCellValue().toString();
	            
	            HSSFRow row05 = sheet.getRow(5);
	            HSSFCell cel05 = row05.getCell((short) 0); 
	            deptname = cel05.getRichStringCellValue().toString();
	            
	            HSSFRow row06 = sheet.getRow(6);
				HSSFCell cel06 = row06.getCell((short) 0); 
				remark = cel06.getRichStringCellValue().toString();
                
				Connection conn = DBOracleconn.getDBConn();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select ordercode from oermorder where ordercode='"+orderno+"' and pk_corp = '"+unitname+"'");
				if(rs.next()){
					out.print("<script language=\"javascript\" >");
			        out.print("window.alert(\"此订单号已存在!\");");
			        out.print("window.close();");
			        out.print("document.location.href='/synear/Operation/orderimp.aspx';");
			        out.print("</script>");
				}else{
					for(int i = 0 ; i < rows; i ++){
						if(i > 7){
							HSSFRow row0 = sheet.getRow(i);
							HSSFCell cel0 = row0.getCell((short) 0); 
							switch (cel0.getCellType()) {
								case HSSFCell.CELL_TYPE_NUMERIC: // 数值型 
									 if(HSSFDateUtil.isCellDateFormatted(cel0)) { 
			                             //如果是date类型则 ，获取该cell的date值 
										 invcode = HSSFDateUtil.getJavaDate(cel0.getNumericCellValue()).toString(); 
			                         }else{//纯数字 
			                             invcode = String.valueOf(cel0.getNumericCellValue()); 
			                         } 
			                         break; 
//			                         /* 此行表示单元格的内容为string类型 */ 
//		                        case HSSFCell.CELL_TYPE_STRING: // 字符串型 
//		                        	invcode = cel0.getRichStringCellValue().toString(); 
//		                            break; 
//		                        case HSSFCell.CELL_TYPE_FORMULA://公式型 
//		                            //读公式计算值 
//		                        	invcode = String.valueOf(cel0.getNumericCellValue()); 
//		                             if(invcode.equals("NaN")){//如果获取的数据值为非法值,则转换为获取字符串 
//		                            	 invcode = cel0.getRichStringCellValue().toString(); 
//		                             } 
//		                        break; 
//		                        case HSSFCell.CELL_TYPE_BOOLEAN://布尔 
//		                        	invcode = " " + cel0.getBooleanCellValue();  
//		                         break; 
//		                        /* 此行表示该单元格值为空 */ 
//		                        case HSSFCell.CELL_TYPE_BLANK: // 空值 
//		                        	invcode = ""; 
//		                            break; 
//		                        case HSSFCell.CELL_TYPE_ERROR: // 故障 
//		                        	invcode = ""; 
//		                            break; 
		                        default: 
		                        	invcode = cel0.getRichStringCellValue().toString(); 
//		                        } 
							}
							if(invcode.indexOf("总 共 合 计") != -1){
								break;
							}else{
								HSSFCell cel1 = row0.getCell((short) 1); 
								invhuohao = cel1.getRichStringCellValue().toString();
								System.out.println(invhuohao);
								
								HSSFCell cel2 = row0.getCell((short) 2); 
								invtiaoma = String.valueOf(cel2.getNumericCellValue());
								System.out.println(invtiaoma);
								
								HSSFCell cel3 = row0.getCell((short) 3); 
								itemspec1 = cel3.getRichStringCellValue().toString();
								System.out.println(itemspec1);
								
								HSSFCell cel4 = row0.getCell((short) 4); 
								invname = cel4.getRichStringCellValue().toString();
								System.out.println(invname);
								
								HSSFCell cel5 = row0.getCell((short) 5); 
								orderjianshu = String.valueOf(cel5.getNumericCellValue()); 
								System.out.println(orderjianshu);
								
								HSSFCell cel6 = row0.getCell((short) 6); 
								orderquant = String.valueOf(cel6.getNumericCellValue());
								System.out.println(orderquant);
								
								HSSFCell cel7 = row0.getCell((short) 7); 
								hanshuimoney = String.valueOf(cel7.getNumericCellValue());
								System.out.println(hanshuimoney);
								
								HSSFCell cel8 = row0.getCell((short) 8); 
								wushuimoney = String.valueOf(cel8.getNumericCellValue()); 
								System.out.println(wushuimoney);
								
								HSSFCell cel9 = row0.getCell((short) 9); 
								tihi = cel9.getRichStringCellValue().toString();
								System.out.println(tihi);
								String sql = "insert into oermorder(invname,store,qty,username,tiaoma,inspec,price,ordercode";
								sql += ",pk_corp,custnameid)";
								sql += " values('"+invname+"','"+storeno+"','"+orderjianshu+"','"+sysuser+"','"+invtiaoma+"'";
								sql += ",'"+itemspec1+"','"+hanshuimoney+"','"+orderno+"','"+unitname+"','"+custnameid+"')";
								System.out.println(sql);
								stmt.addBatch(sql);
								try {
									stmt.executeBatch();
						            conn.commit();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							
						}
						out.print("<script language=\"javascript\" >");
				        out.print("window.alert(\"数据导入成功!\");");
				        out.print("window.close();");
				        out.print("document.location.href='/synear/Operation/orderimp.aspx';");
				        out.print("</script>");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					DBOracleconn.closeDBconn();
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		out.close();
	}
	public void bar(Element root){
		String orderinfo1 = "";
		for (Iterator i = root.elementIterator(); i.hasNext(); ) {
			Element element = (Element) i.next();
			String storenum = "";
			String storesalenum = "";
			String styleDescription = "";
			String chargedCompanyName = "";
			String orderprice = "";
			String itemspec = "";
			StringBuffer sb = new StringBuffer("");
			if(element.getName().equals("storeNum")){
				storenum = element.getText();
				sb.append(storenum+"----");
			}
			if(element.getName().equals("unitCost")){
				orderprice = element.getText();
				sb.append(";"+orderprice);
			}
			if(element.getName().equals("packSize")){
				itemspec = element.getText();
				sb.append(","+itemspec);
			}
			if(element.getName().equals("quantityOrderedOrPredistributed")){
				storesalenum = element.getText();
				sb.append(storesalenum+",");
			}
			if(element.getName().equals("styleDescription")){
				styleDescription = element.getText();
				try {
					System.out.println(styleDescription);
					System.out.println(new String(styleDescription.getBytes(),"UTF-8"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					styleDescription = URLDecoder.decode(styleDescription,"UTF-8");
					//System.out.println(styleDescription);
					System.out.println(new String(styleDescription.getBytes(),"UTF-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				sb.append(",");
				sb.append(styleDescription+"###");
			}
			if(element.getName().equals("poNum")){
				poNum = element.getText();
				sb.append("&&&"+poNum);
			}
			if(element.getName().equals("chargedCompanyName")){
				chargedCompanyName = element.getText();
				sb.append("&&&"+chargedCompanyName);
			}
			//orderinfo = sb.toString();
			for( int j = 0 ; j < element.nodeCount(); j ++){
				Node node = element.node(j);
				if(node instanceof Element){
					 bar((Element)node);
				}
			}
			orderinfo1 += sb.toString();
		}
		aa += orderinfo1;
	}

}
