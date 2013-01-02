package com.tend.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
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

public class CustFileReadServlet extends HttpServlet{
	private static final long serialVersionUID = -4464318344629260022L;
	String poNum = "";
	static String aa = "";
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	public CustFileReadServlet(){
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
		System.out.println(sysuser+"**"+unitname+"**"+custnameid);
		if(custnameid.equals("4")){
			try {
				document = reader.read(new File(req.getRealPath(req.getParameter("paper"))));
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			Element root = document.getRootElement();
			CustFileReadServlet t = new CustFileReadServlet();
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
								//orderquantity = starr.split(",")[0];
								storenum = starr.split(",")[0];
								invdesc = childarr[1].split(",")[3];
								orderprice = childarr[1].split(",")[0];
								orderquantity = childarr[1].split(",")[1];
								itemspec = childarr[1].split(",")[2];
							}//System.out.println("itemspec="+itemspec+"**orderprice="+orderprice+"**orderquantity="+orderquantity+"**storenum="+storenum+"**invdesc="+invdesc+"**ordernum="+ordernum+"**corpname="+corpname);
					    	try {
								String sql = "insert into oermorder(invname,store,qty,username,tiaoma,inspec,price,ordercode,pk_corp,custnameid)";
								sql += " values('"+invdesc+"','"+storenum+"','"+orderquantity+"','"+sysuser+"','','"+itemspec+"','"+orderprice+"','"+ordernum+"','"+unitname+"','"+custnameid+"')";
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
		}else if(custnameid.equals("5")){
			InputStream is = null;
			try {
				is = new FileInputStream(new File(req.getRealPath(req.getParameter("paper"))));
				HSSFWorkbook workBook = new HSSFWorkbook(is);
				HSSFSheet sheet = workBook.getSheetAt(0);
			    int rows = sheet.getPhysicalNumberOfRows();
				String orderno = "";
				String storeno = "";
				String invcode = "";
				String invhuohao = "";
				String invtiaoma = "";
				String itemspec1 = "";
				String invname = "";
				String orderquant = "";
				String hanshuimoney = "";
				HSSFRow row02 = sheet.getRow(5);
	            HSSFCell cel03 = row02.getCell((short) 3); 
	            storeno = cel03.getRichStringCellValue().toString().split(" ")[0];
	            System.out.println(storeno.split(" ")[0]);
	            HSSFCell cel02 = row02.getCell((short) 14); 
	            orderno = cel02.getRichStringCellValue().toString();
	            System.out.println(orderno);
                
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
							String value = "";
							for(short j = 1 ; j < 30; j ++){
								HSSFCell cel0 = row0.getCell(j);
								switch (cel0.getCellType()) {
									case HSSFCell.CELL_TYPE_NUMERIC: // 数值型 
										 if(HSSFDateUtil.isCellDateFormatted(cel0)) { 
				                             //如果是date类型则 ，获取该cell的date值 
											 value = HSSFDateUtil.getJavaDate(cel0.getNumericCellValue()).toString(); 
				                         }else{//纯数字 
				                        	 if(j == 2){
				                        		 DecimalFormat df = new DecimalFormat("0");
				                        		 String whatYourWant = df.format(cel0.getNumericCellValue());
				                        		 value = whatYourWant;
				                        	 }else{
				                        		 value = String.valueOf(cel0.getNumericCellValue()); 
				                        	 } 
				                         } 
				                         break; 
				                         /* 此行表示单元格的内容为string类型 */ 
			                        case HSSFCell.CELL_TYPE_STRING: // 字符串型 
			                        	value = cel0.getRichStringCellValue().toString(); 
			                            break; 
			                        case HSSFCell.CELL_TYPE_FORMULA://公式型 
			                            //读公式计算值 
			                        	invcode = String.valueOf(cel0.getNumericCellValue()); 
			                             if(invcode.equals("NaN")){//如果获取的数据值为非法值,则转换为获取字符串 
			                            	 value = cel0.getRichStringCellValue().toString(); 
			                             } 
			                        break; 
			                        case HSSFCell.CELL_TYPE_BOOLEAN://布尔 
			                        	value = " " + cel0.getBooleanCellValue();  
			                         break; 
			                        /* 此行表示该单元格值为空 */ 
			                        case HSSFCell.CELL_TYPE_BLANK: // 空值 
			                        	value = ""; 
			                            break; 
			                        case HSSFCell.CELL_TYPE_ERROR: // 
			                        	value = ""; 
			                            break; 
			                        default: 
			                        	value = cel0.getRichStringCellValue().toString(); 
								}
								if(j == 1){
									invcode = value;
								}
								if(invcode.indexOf("小    计：") != -1){
									break;
								}else{
									if(j == 5){ 
									   invhuohao = value;
									}
									if(j == 4){ 
										invtiaoma = value;
									}
									if(j == 12){ 
										itemspec1 = value;
									}
									if(j == 9){ 
										invname = value;
									}
									if(j == 17){ 
										orderquant = value;
									}
									if(j == 20){ 
										hanshuimoney = value;
									}
								}
							}
							if(invcode.indexOf("小    计：") != -1){
								break;
							}else{
								String sql = "insert into oermorder(invname,store,qty,username,tiaoma,inspec,price,ordercode";
								sql += ",pk_corp,custnameid)";
								sql += " values('"+invname+"','"+storeno+"','"+orderquant+"','"+sysuser+"','"+invtiaoma+"'";
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
				}
					out.print("<script language=\"javascript\" >");
			        out.print("window.alert(\"数据导入成功!\");");
			        out.print("window.close();");
			        out.print("document.location.href='/synear/Operation/orderimp.aspx';");
			        out.print("</script>");
				}
			} catch (Exception e) {
				out.print("<script language=\"javascript\" >");
		        out.print("window.alert(\"数据读取失败，请检查导入文件的格式!\");");
		        out.print("window.close();");
		        out.print("document.location.href='/synear/Operation/orderimp.aspx';");
		        out.print("</script>");
				e.printStackTrace();
			}finally{
				try {
					DBOracleconn.closeDBconn();
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else if(custnameid.equals("3")){
			FileReader r=new FileReader(new File(req.getRealPath(req.getParameter("paper"))));
	        BufferedReader bf = new BufferedReader(r);
	        StringBuffer bu = new StringBuffer();
	        String line;
            String t  ="";
            String invname="";
            String spc="";
            String qty="";
            String storeid="";
            try{
	            while((line=bf.readLine())!=null){
	            	bu.append(line+"###");
	            }
	            Connection conn = DBOracleconn.getDBConn();
	            t=bu.toString();
	            String[] a =t.split("────────────────────────────────────────────────");
	            for(int i =0;i<a.length-3;i+=5){
	            	String a0[]=a[i].split(":");
	 	            String store1 = a0[1].split("   ")[0];
	 	            String store =store1.trim();
 					stmt = conn.createStatement();
 					rs = stmt.executeQuery("select storeid from oermsotre where pk_corp='"+unitname+"' and custnameid = '"+custnameid+"' and instr(storename,'"+store+"',1,1) > 0");
 					while(rs.next()){
 						storeid = rs.getString("storeid");
 					}
 					String[] a1 = a[i+1].split("  ");
 		            String ordercode = a1[2];
 		            System.out.println(ordercode);
 		            stmt = conn.createStatement();
 					rs = stmt.executeQuery("select ordercode from oermorder where ordercode='"+ordercode+"' and pk_corp = '"+unitname+"'");
 					if(rs.next()){
 						out.print("<script language=\"javascript\" >");
 				        out.print("window.alert(\"此订单号已存在!\");");
 				        out.print("window.close();");
 				        out.print("document.location.href='/synear/Operation/orderimp.aspx';");
 				        out.print("</script>");
 					}else{
 						String m=a[i+3].trim().replaceAll("( )+"," ");
 			            String[] a3 = m.split("###");
 			            for(int j=0;j<a3.length-1;j++){
 				        	 if(a3[j].length()>25){
 				        		  invname = a3[j].trim().split(" ")[1]+ a3[j+1].trim().split(" ")[0];
 				        		  spc = a3[j].trim().split(" ")[4];
 				        		  qty = a3[j].trim().split(" ")[5];
 				        		  try {
 				        			   conn = DBOracleconn.getDBConn();
 					      			   stmt = conn.createStatement();
 				        			   String sql = "insert into oermorder(invname,store,qty,username,tiaoma,inspec,price,ordercode,pk_corp,custnameid)";
 									   sql += " values('"+invname+"','"+storeid+"','"+qty+"','"+sysuser+"','','"+spc+"','','"+ordercode+"','"+unitname+"','"+custnameid+"')";
 									   stmt.addBatch(sql);
 									} catch (Exception e) {
 										e.printStackTrace();
 									}finally{
 										stmt.executeBatch();
 							            conn.commit();
 										out.print("<script language=\"javascript\" >");
 								        out.print("window.alert(\"数据导入成功!\");");
 								        out.print("window.close();");
 								        out.print("document.location.href='/synear/Operation/orderimp.aspx';");
 								        out.print("</script>");
 									}
 				        	 }else{
 				        		 continue;
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
		}
		out.close();
	}
	public void bar(Element root){
		String orderinfo1 = "";
		for (Iterator i = root.elementIterator(); i.hasNext(); ) {
			Element element = (Element) i.next();
			String storenum = "";
			String storesalenum = "";
			String storesalenum1 = "";
			String styleDescription = "";
			String chargedCompanyName = "";
			String orderprice = "";
			String itemspec = "";
			StringBuffer sb = new StringBuffer("");
			if(element.getName().equals("storeNum")){
				storenum = element.getText();
				sb.append(storenum);
			}
			if(element.getName().equals("unitCost")){
				orderprice = element.getText();
				sb.append(";"+orderprice);
			}
			if(element.getName().equals("packSize")){
				itemspec = element.getText();
				sb.append(","+itemspec);
			}
			if(element.getName().equals("quantityOrdered")){
				storesalenum = element.getText();
				sb.append(","+storesalenum);
				//System.out.println("***"+storesalenum+"***");
			}
			//if(element.getName().equals("quantityOrderedOrPredistributed")){
			//	storesalenum1 = element.getText();
			//	sb.append("888"+storesalenum1+",");
			//}
			if(element.getName().equals("styleDescription")){
				styleDescription = element.getText();
				try {
					System.out.println(styleDescription);
					//System.out.println(new String(styleDescription.getBytes(),"UTF-8"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					styleDescription = URLDecoder.decode(styleDescription,"UTF-8");
					//System.out.println(styleDescription);
					//System.out.println(new String(styleDescription.getBytes(),"UTF-8"));
					//System.out.println(new String(styleDescription.getBytes("UTF-8"),"gb2312"));
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
		//System.out.print(aa);
	}

}
