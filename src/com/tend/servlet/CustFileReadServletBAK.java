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

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class CustFileReadServletBAK extends HttpServlet{
	private static final long serialVersionUID = -4464318344629260022L;
	String poNum = "";
	static String aa = "";
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	public CustFileReadServletBAK(){
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
			CustFileReadServletBAK t = new CustFileReadServletBAK();
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
			try {
				InputStream is = new FileInputStream(new File(req.getRealPath(req.getParameter("paper"))));
				Workbook wbk = null;
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
				wbk = Workbook.getWorkbook(is);
				/*Sheet sheet = wbk.getSheet(0);
				Cell c00 = sheet.getCell(0,0);
				corpname = c00.getContents();
				Cell c01 = sheet.getCell(0,1);
				ordertype = c01.getContents();
				Cell c02 = sheet.getCell(0,2);
				orderno = c02.getContents();
				if(orderno.indexOf("单据号：")!= -1){
					orderno = orderno.substring("单据号：".length());
				Cell c12 = sheet.getCell(1,2);
				zhidandate = c12.getContents();
				Cell c03 = sheet.getCell(0,3);
				storeno = c03.getContents();
				storeno = storeno.substring("连锁店编码：".length());
				Cell c13 = sheet.getCell(1,3);
				storename = c13.getContents();
				Cell c04 = sheet.getCell(0,4);
				vendorcode = c04.getContents();
				Cell c14 = sheet.getCell(1,4);
				vendorname = c14.getContents();
				Cell c05 = sheet.getCell(0,5);
				deptname = c05.getContents();
				Cell c06 = sheet.getCell(0,6);
				remark = c06.getContents();
				Connection conn = DBOracleconn.getDBConn();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select ordercode from oermorder where ordercode='"+orderno+"' and pk_corp = '"+unitname+"'");
				if(rs.next()){
	//				out.print("<script language=\"javascript\" >");
	//		        out.print("window.alert(\"此订单号已存在!\");");
	//		        out.print("window.close();");
	//		        out.print("document.location.href='/synear/Operation/orderimp.aspx';");
	//		        out.print("</script>");
				}else{
					for(int i = 0 ; i < sheet.getRows(); i ++){
						if(i > 7){
							Cell cell = sheet.getCell(0,i);
							invcode = cell.getContents();
							if(invcode.indexOf("总 共 合 计") != -1){
								break;
							}else{
								Cell cell0 = sheet.getCell(1,i);
								invhuohao = cell0.getContents();
								Cell cell1 = sheet.getCell(2,i);
								invtiaoma = cell1.getContents();
								Cell cell2 = sheet.getCell(3,i);
								itemspec1 = cell2.getContents();
								Cell cell3 = sheet.getCell(4,i);
								invname = cell3.getContents();
								Cell cell4 = sheet.getCell(5,i);
								orderjianshu = cell4.getContents();
								Cell cell5 = sheet.getCell(6,i);
								orderquant = cell5.getContents();
								Cell cell6 = sheet.getCell(7,i);
								hanshuimoney = cell6.getContents();
								Cell cell7 = sheet.getCell(8,i);
								wushuimoney = cell7.getContents();
								Cell cell8 = sheet.getCell(9,i);
								tihi = cell8.getContents();
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
	//					out.print("<script language=\"javascript\" >");
	//			        out.print("window.alert(\"数据导入成功!\");");
	//			        out.print("window.close();");
	//			        out.print("document.location.href='/synear/Operation/orderimp.aspx';");
	//			        out.print("</script>");
					}
				}
				}else{
					
				}*/
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
//				try {
//					DBOracleconn.closeDBconn();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
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
	
	public static void main(String[] args) throws Exception {
		try {
			InputStream is = new FileInputStream(new File("G:/Spdd_020250.xls"));
			Workbook wbk = null;
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
			String unitname = "1007";
			String sysuser = "njdmc";
			String custnameid = "3";
			wbk = Workbook.getWorkbook(is);
			Sheet sheet = wbk.getSheet(0);
			Cell c00 = sheet.getCell(0,0);
			corpname = c00.getContents();
			Cell c01 = sheet.getCell(0,1);
			ordertype = c01.getContents();
			Cell c02 = sheet.getCell(0,2);
			orderno = c02.getContents();
			if(orderno.indexOf("单据号：")!= -1){
				orderno = orderno.substring("单据号：".length());
			Cell c12 = sheet.getCell(1,2);
			zhidandate = c12.getContents();
			Cell c03 = sheet.getCell(0,3);
			storeno = c03.getContents();
			storeno = storeno.substring("连锁店编码：".length());
			Cell c13 = sheet.getCell(1,3);
			storename = c13.getContents();
			Cell c04 = sheet.getCell(0,4);
			vendorcode = c04.getContents();
			Cell c14 = sheet.getCell(1,4);
			vendorname = c14.getContents();
			Cell c05 = sheet.getCell(0,5);
			deptname = c05.getContents();
			Cell c06 = sheet.getCell(0,6);
			remark = c06.getContents();
			Connection conn = DBOracleconn.getDBConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select ordercode from oermorder where ordercode='"+orderno+"' and pk_corp = '"+unitname+"'");
			if(rs.next()){
//				out.print("<script language=\"javascript\" >");
//		        out.print("window.alert(\"此订单号已存在!\");");
//		        out.print("window.close();");
//		        out.print("document.location.href='/synear/Operation/orderimp.aspx';");
//		        out.print("</script>");
			}else{
				for(int i = 0 ; i < sheet.getRows(); i ++){
					if(i > 7){
						Cell cell = sheet.getCell(0,i);
						invcode = cell.getContents();
						if(invcode.indexOf("总 共 合 计") != -1){
							break;
						}else{
							Cell cell0 = sheet.getCell(1,i);
							invhuohao = cell0.getContents();
							Cell cell1 = sheet.getCell(2,i);
							invtiaoma = cell1.getContents();
							Cell cell2 = sheet.getCell(3,i);
							itemspec1 = cell2.getContents();
							Cell cell3 = sheet.getCell(4,i);
							invname = cell3.getContents();
							Cell cell4 = sheet.getCell(5,i);
							orderjianshu = cell4.getContents();
							Cell cell5 = sheet.getCell(6,i);
							orderquant = cell5.getContents();
							Cell cell6 = sheet.getCell(7,i);
							hanshuimoney = cell6.getContents();
							Cell cell7 = sheet.getCell(8,i);
							wushuimoney = cell7.getContents();
							Cell cell8 = sheet.getCell(9,i);
							tihi = cell8.getContents();
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
//					out.print("<script language=\"javascript\" >");
//			        out.print("window.alert(\"数据导入成功!\");");
//			        out.print("window.close();");
//			        out.print("document.location.href='/synear/Operation/orderimp.aspx';");
//			        out.print("</script>");
				}
			}
			}else{
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
//			try {
//				DBOracleconn.closeDBconn();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
	}

}
