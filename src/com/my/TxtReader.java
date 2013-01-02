package com.my;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tend.servlet.DBOracleconn;

public class TxtReader extends HttpServlet{
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding( "utf-8"); 
		response.setContentType( "text/html;   charset=utf-8 "); 		
		PrintWriter out = response.getWriter();
		   String sysuser = request.getParameter("sysuser");
		   String unitname = request.getParameter("unitname");
		   String custnameid = request.getParameter("custnameid");
		   try {	
			   //设定预读取订单txt文件的路径
	         
	           FileReader r=new FileReader(new File(request.getRealPath(request.getParameter("paper"))));
	           BufferedReader bf = new BufferedReader(r);
	           StringBuffer bu = new StringBuffer();
	          // List list = new ArrayList();
	           String line;
	           String t  ="";
	           String invname="";
	           String spc="";
	           String qty="";
	           String storeid="";
	           //int index = 2;
	           //Oermorder oer = new Oermorder();
	           while((line=bf.readLine())!=null){
	        	 
	        	  bu.append(line+"###");
	        	
	           }
	           t=bu.toString();
	          // System.out.println(t);
	           
	           String[] a =t.split("────────────────────────────────────────────────"); 
	           //int num = a.length/5;
	           for(int i =0;i<a.length-3;i+=5){
	           //System.out.println(num);
	           String a0[]=a[i].split(":");
	           String store1 = a0[1].split("   ")[0];
	           String s[]={"东莞常平店","厚街鼎盛店","东莞虎门店","佛山天佑店","佛山环市店","广州圣地店","广州中六店","深圳龙城店","华南生鲜统仓"};
	           String store =store1.trim();
	           System.out.println(store);
	              if(store.equals(s[0])){
		        	  storeid="4003";
		          }else if(store.equals(s[1])){
		        	  storeid="4004";
		          }
		           else if(store.equals(s[2])){
		        	  storeid="4002";
		          }else if(store.equals(s[3])){
		        	  storeid="4001";
		          }else if(store.equals(s[4])){
		        	  storeid="4010";
		          }else if(store.equals(s[5])){
		        	  storeid="4006";
		          }else if(store.equals(s[6])){
		        	  storeid="4007";
		          }else if(store.equals(s[7])){
		        	  storeid="4005";
		          }else if(store.equals(s[8])){
		        	  storeid="0801";
		          }	 
	           System.out.println("ahgeif"+storeid);
	           
	           String[] a1 = a[i+1].split("  ");
	           String ordercode = a1[2];
	          // System.out.println(ordercode);
	           Connection conn = DBOracleconn.getDBConn();
				stmt = conn.createStatement();
				rs = stmt.executeQuery("select ordercode from oermorder where ordercode='"+ordercode+"' and pk_corp = '"+unitname+"'");
				if(rs.next()){
					out.print("<script language=\"javascript\" >");
			        out.print("window.alert(\"此订单号已存在!\");");
			        out.print("window.close();");
			        out.print("document.location.href='/synear/Operation/baijia.aspx';");
			        out.print("</script>");
				}else{
	           //将多个空格变为一个空格
	           
	           String m=a[i+3].trim().replaceAll("( )+"," ");
	           String[] a3 = m.split("###");
	           //System.out.println(a3.length);
	         // System.out.println(a3[1].trim().split(" ")[1]);
	        // System.out.println(storeid);
	          for(int j=0;j<a3.length-1;j++){
	        	
	        	 if(a3[j].length()>25){
	        		  invname = a3[j].trim().split(" ")[1]+ a3[j+1].trim().split(" ")[0];
	        		  spc = a3[j].trim().split(" ")[4];
	        		  qty = a3[j].trim().split(" ")[5];
	        		 
	        		 
	        		  try {
	        			 
	        			   conn = DBOracleconn.getDBConn();
		      			   stmt = conn.createStatement();
		      			  
		     			   System.out.println("我么"+storeid);
	        			    String sql = "insert into oermorder(invname,store,qty,username,tiaoma,inspec,price,ordercode,pk_corp,custnameid)";
							sql += " values('"+invname+"','"+storeid+"','"+qty+"','"+sysuser+"','','"+spc+"','','"+ordercode+"','"+unitname+"','"+custnameid+"')";
							stmt.addBatch(sql);
		      			 
						} catch (Exception e) {
							e.printStackTrace();
						}finally{
							stmt.executeBatch();
							
				            conn.commit();
				            if (rs != null) {
			                    rs.close();
			                }
			                if (stmt != null) {
			                    stmt.close();
			                }
			                if (conn != null) {
			                    conn.close();
			                };
							out.print("<script language=\"javascript\" >");
					        out.print("window.alert(\"数据导入成功!\");");
					        out.print("window.close();");
					        out.print("document.location.href='/synear/Operation/baijia.aspx';");
					        out.print("</script>");
						}
	        	 }else{
	        		 continue;
	        	 }
	        	// System.out.println("****"+a3[j].length());
	        		 
	        	  
	        		
	        	  
	        	 
	          }
	        
	          
				}  
	           }
	           
	         
	      
	}catch (Exception e) {
		System.out.println(e.toString());
	}finally{
		try
		{
			if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            };
		}
		catch (Exception e2)
		{
			e2.printStackTrace();
		}
	}
	}
}
