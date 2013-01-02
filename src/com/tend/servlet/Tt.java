package com.tend.servlet;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class Tt {

	/**
	 * @param args
	 * @throws DocumentException 
	 */
	String poNum = "";
	String orderinfo = "";
	StringBuffer sb1 = new StringBuffer("");
	static String aa = "";
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	public void bar(Element root){
		String orderinfo1 = "";
		for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
			Element element = (Element) i.next();
			String storenum = "";
			String storesalenum = "";
			String styleDescription = "";
			String chargedCompanyName = "";
			String orderprice = "";
			String itemspec = "";
			StringBuffer sb = new StringBuffer("");
			//System.out.println(element.getName());
			if(element.getName().equals("storeNum")){
				storenum = element.getText();
				//System.out.println(m+"***storeNum***"+element.getText());
				sb.append(storenum+"----");
				//System.out.println(element.getName());
				//System.out.print("storeNum:"+sb.toString()+",");
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
				//System.out.println(m+"***storesalenum***"+storesalenum);
				sb.append(storesalenum+",");
				//System.out.print("storesalenum:"+sb.toString()+"-");
			}
			if(element.getName().equals("styleDescription")){
				styleDescription = element.getText();
				sb.append(",");
				sb.append(styleDescription+"###");
				//System.out.println(sb.toString()+";");
			}
			if(element.getName().equals("poNum")){
				poNum = element.getText();
				sb.append("&&&"+poNum);
				//System.out.println(element.getName());
				//System.out.println(poNum);
				//System.out.println(sb.toString());
			}
			if(element.getName().equals("chargedCompanyName")){
				chargedCompanyName = element.getText();
				sb.append("&&&"+chargedCompanyName);
			}
			//System.out.print(sb.toString());
			//System.out.print(sb1.toString());
			orderinfo = sb.toString();
			//orderno1 = sb2.toString();
			//orderinfo1=orderinfo;
			//System.out.print(orderinfo1);
			//System.out.print(sb2.toString());
			//System.out.println("**********="+sb.toString());
			for( int j = 0 ; j < element.nodeCount(); j ++){
				Node node = element.node(j);
				if(node instanceof Element){
					 bar((Element)node);
				}
			}
			//System.out.print(orderinfo);
			orderinfo1 += orderinfo;
		}
		//orderinfo1 = orderinfo;
		//aa = orderinfo1;
		aa += orderinfo1;
		//sb1.append(orderinfo1);
		//System.out.print("))))))))))"+orderinfo);
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String orderquantity = "";
		String storenum = "";
		String invdesc = "";
		String orderprice = "";
		String itemspec = "";
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("d:/PO_2011.xml"));
		Element root = document.getRootElement();
		Tt t = new Tt();
		t.bar(root);
		String bb = aa;
		System.out.println(bb);
		//System.out.println("---"+aa.split("*"));
		String[] firstarr = bb.split("&&&");
		String[] arrbb = firstarr[0].split("###");
		String ordernum = firstarr[2];
		String corpname = firstarr[1];
		for(int k = 0 ; k < arrbb.length;k++){
			//System.out.println(arrbb[k]);
			String[] childarr = arrbb[k].split(";");
			//System.out.println(childarr[1]);
			//String[] storearr = childarr[1];
			for(int m = 0 ; m < childarr[0].split("----").length; m ++){
				String starr = childarr[0].split("----")[m];
				for(int n = 0 ; n < starr.split(",").length; n ++){
					//System.out.println(starr.split(",")[n]);
					orderquantity = starr.split(",")[0];
					storenum = starr.split(",")[1];
					invdesc = childarr[1].split(",")[2];
					orderprice = childarr[1].split(",")[0];
					itemspec = childarr[1].split(",")[1];
				}System.out.println("itemspec="+itemspec+"**orderprice="+orderprice+"**orderquantity="+orderquantity+"**storenum="+storenum+"**invdesc="+invdesc+"**ordernum="+ordernum+"**corpname="+corpname);
				//System.out.println(childarr[0].split("----")[m]+childarr[1]);
			    Connection conn = DBOracleconn.getDBConn();
		    	try {
					stmt = conn.createStatement();
					String sql = "insert into oermorder(invname,store,qty,username,tiaoma,inspec,price,ordercode)";
					sql += " values('"+invdesc+"','"+storenum+"','"+orderquantity+"','ljf','','"+itemspec+"','"+orderprice+"','"+ordernum+"')";
					stmt.addBatch(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					try {
						stmt.executeBatch();
			            conn.commit();
						DBOracleconn.closeDBconn();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}
