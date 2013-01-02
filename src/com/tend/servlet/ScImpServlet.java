package com.tend.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;



public class ScImpServlet extends HttpServlet{
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
	// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
		throws ServletException, IOException {
	// TODO Auto-generated method stub
		resp.setContentType("text/html; charset=gb2312");
		resp.setHeader("Cache-Control", "no-cache");
		PrintWriter out = resp.getWriter();
		try{
			GetDate date = new GetDate();
			String time = date.getDate();
			String formname=request.getParameter("formname");
			String picurl=request.getParameter("picurl");
			String type = request.getParameter("type");
			String tendtypeid = request.getParameter("tendtypeid");
			String myframeurl = request.getParameter("myframeurl");
			String billno = request.getParameter("billno");
			String pk_factory = request.getParameter("custpk");
			String pctype = request.getParameter("pctype");
			String names = "";
			String path = request.getRealPath("/");
			String syspath = "/fileupload/file/";
			path += syspath+type;
			String tpath = path +"/temp/";
			String file1 = "";
			File FileUploadDir=new File(path);
			File FileUploadDir1=new File(tpath);
			if(!FileUploadDir.exists())
			   FileUploadDir.mkdir(); //生成上传文件的目录
			if(!FileUploadDir1.exists())
			   FileUploadDir1.mkdir();
			if(tendtypeid != null || tendtypeid != ""){
			  path += "/"+tendtypeid;
			  tpath = path +"/temp/";
			  FileUploadDir=new File(path);
			  FileUploadDir1=new File(tpath);
				if(!FileUploadDir.exists())
				   FileUploadDir.mkdir(); //生成上传文件的目录
				if(!FileUploadDir1.exists())
				   FileUploadDir1.mkdir();
			}
			String tempPath = tpath;
			DiskFileUpload fu = new DiskFileUpload();
			fu.setHeaderEncoding("gb2312");
			resp.setCharacterEncoding("gb2312");
		    fu.setSizeMax(100*1024*1024);// 设置最大文件尺寸，这里是1M
		    fu.setSizeThreshold(4096);// 设置缓冲区大小，这里是4kb
		    fu.setRepositoryPath(tempPath);												
			  // 设置临时目录：
		      List fileItems = fu.parseRequest(request);
			  //System.out.println(fileItems);// 得到所有的文件：
		      Iterator iter = fileItems.iterator();
		      ArrayList file = new ArrayList();
		      ArrayList size1 = new ArrayList();
		      while (iter.hasNext()) {			// 依次处理每一个文件：
		        FileItem item = (FileItem) iter.next();	
				//忽略其他不是文件域的所有表单信息
		        if (!item.isFormField()) {
		          names = item.getName();
		          long size = item.getSize();
		          if ( (names == null || names.equals("")) && size == 0) {
		            continue;
		          }
		          names = names.replace('\\', '-');
		          names = time+tendtypeid+names.substring((names.lastIndexOf("-")+1),names.length());
		          item.write(new File(path+"/"+names));
		          //size = size/(1024);
		          //String s = size+"K";
		          file.add(names);
		          //size1.add(s);
		        }
		       }
		      if(tendtypeid != null || tendtypeid != ""){
		    	  file1 = syspath+type+"/"+tendtypeid+"/"+file.get(0);
		      }else{
		    	  file1 = syspath+type+file.get(0);
		      }
		      try{
		    	 if(pctype.equals("B")){
		    		insertmonthpc(request.getRealPath(file1),billno,pk_factory);
		    	 }else if(pctype.equals("A")){
		            insertsnquantity(request.getRealPath(file1),billno,pk_factory);
		    	 }else if(pctype.equals("C")){
		    		insertyearpc(request.getRealPath(file1),billno,pk_factory);
		    	 }
		         out.print("<script language=\"javascript\" >");
			     out.print("window.alert(\"文件导入成功!\");");
			     out.print("window.close();");
			     out.print("opener.location.reload();");
			     out.print("</script>");
		      }catch(Exception e){
		    	  e.printStackTrace();
		    	  out.print("<script language=\"javascript\" >");
			      out.print("window.alert(\"文件导入失败!\");");
			      out.print("window.close();");
			      out.print("opener.location.reload();");
			      out.print("</script>");
		      }
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		out.close();
   }

   public static void insertmonthpc(String path,String billno,String pk_factory) throws Exception{
	   String user = "";
	   String sql = "";
	   String sql2 = "";
	   System.out.println(billno);
		try{
			InputStream is = new FileInputStream(new File(path));
			Workbook wbk = null;
			String scdate = "";
			String invcode = "";
			String plannum = "";
			String pk_invbasdoc = "";
			String sdate = "";
			String sdate1 = "";
			//String bc = "";
			String remark = "";
			//String banci = "";
			wbk = Workbook.getWorkbook(is);
			Sheet sheet = wbk.getSheet(0);
			user = sheet.getCell(4,1).getContents();
			conn = DBOracleconn.getDBConn();
			stmt = conn.createStatement();
			int count = 0;
			try{
				for(int i = 3 ; i < sheet.getRows(); i ++){
					Cell c10 = sheet.getCell(0,i);
					scdate = c10.getContents();
					Cell cs = sheet.getCell(1,i);
					invcode = cs.getContents();
					Cell c12 = sheet.getCell(10,i);
					plannum = c12.getContents();
					Cell c13 = sheet.getCell(12,i);
					remark = c13.getContents();
					if(!"".equals(scdate) && !scdate.equals("计划编制：")){
						scdate = scdate.replaceAll("/", "-");
						sdate+=","+scdate;
						if(sdate1.indexOf(scdate) == -1 ){
							sdate1+=","+scdate;
							count ++;
						}
						rs = stmt.executeQuery("select pk_invbasdoc from bd_invbasdoc where invcode = '"+invcode+"'");
						while(rs.next()){
							pk_invbasdoc = rs.getString("pk_invbasdoc");
						}
						String[] ssss = sdate1.split(",");
						for(int j = 0 ; j < ssss.length ; j ++){
							if(!ssss[j].equals("") && ssss[j].equals(scdate)){
								sql = "insert into sn_month_plan_product2 (BILLNO,PK_PROD,PLANNUM,REALHOURS,PLANNUMOLD,planmemo)";
								sql += " values('"+billno+j+"','"+pk_invbasdoc+"','"+plannum+"','0','"+plannum+"','"+remark+"')";
								System.out.println(sql);
								stmt.execute(sql);
								System.out.println(sql);
							}
						}
					}
				}
				String[] ssss = sdate1.split(",");
				for(int j = 0 ; j < ssss.length ; j ++){
					System.out.println(j);
					if(!ssss[j].equals("")){
					  sql2 = "insert into sn_month_plan_product (BILLNO,PK_FACTORY,months,MAKERUSER,FSTATUS)";
					  sql2 += " values('"+billno+j+"','"+pk_factory+"','"+ssss[j]+"','"+user+"','1')";
					  stmt.execute(sql2);
					  System.out.println(sql2);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
		            conn.commit();
		            DBOracleconn.closeDBconn();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
   }
   
   public static void insertyearpc(String path,String billno,String pk_factory) throws Exception{
	   String user = "";
	   String sql = "";
	   String sql2 = "";
		try{
			InputStream is = new FileInputStream(new File(path));
			Workbook wbk = null;
			String scdate = "";
			String year = "";
			String invcode = "";
			String plannum = "";
			String pk_invbasdoc = "";
			String sdate = "";
			String sdate1 = "";
			String ayear = "";
			String remark = "";
			//String banci = "";
			wbk = Workbook.getWorkbook(is);
			Sheet sheet = wbk.getSheet(0);
			user = sheet.getCell(4,1).getContents();
			conn = DBOracleconn.getDBConn();
			stmt = conn.createStatement();
			int count = 0;
			try{
				for(int i = 3 ; i < sheet.getRows(); i ++){
					Cell c10 = sheet.getCell(0,i);
					year = c10.getContents();
					Cell c11 = sheet.getCell(1,i);
					scdate = c11.getContents();
					Cell cs = sheet.getCell(2,i);
					invcode = cs.getContents();
					Cell c12 = sheet.getCell(11,i);
					plannum = c12.getContents();
					Cell c13 = sheet.getCell(13,i);
					remark = c13.getContents();
					
					if(!"".equals(year) && !year.equals("计划编制：")){
						ayear = year;
						scdate = scdate.replaceAll("/", "-");
						sdate+=","+scdate;
						if(sdate1.indexOf(scdate) == -1 ){
							sdate1+=","+scdate;
							count ++;
						}
						rs = stmt.executeQuery("select pk_invbasdoc from bd_invbasdoc where invcode = '"+invcode+"'");
						while(rs.next()){
							pk_invbasdoc = rs.getString("pk_invbasdoc");
						}
						String[] ssss = sdate1.split(",");
						for(int j = 0 ; j < ssss.length ; j ++){
							if(!ssss[j].equals("") && ssss[j].equals(scdate)){
								sql = "insert into sn_year_plan_product2 (BILLNO,PK_PROD,PLANNUM,REALHOURS,PLANNUMOLD,planmemo)";
								sql += " values('"+billno+j+"','"+pk_invbasdoc+"','"+plannum+"','0','"+plannum+"','"+remark+"')";
								//System.out.println(sql);
								stmt.execute(sql);
							}
						}
					}
				}
				//System.out.println(ayear+"****"+sdate1);
				String[] ssss = sdate1.split(",");
				for(int j = 0 ; j < ssss.length ; j ++){
					if(!ssss[j].equals("")){
					  sql2 = "insert into sn_year_plan_product (BILLNO,PK_FACTORY,years,MAKERUSER,FSTATUS,months)";
					  sql2 += " values('"+billno+j+"','"+pk_factory+"','"+ayear+"','"+user+"','1','"+ssss[j]+"')";
					  stmt.execute(sql2);
					  //System.out.println(sql2);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
		            conn.commit();
		            DBOracleconn.closeDBconn();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
   }

   public static void insertsnquantity(String path,String billno,String pk_factory) throws Exception{
	   //String path1 = "D:\\生产通知单.xls";
	   String user = "";
	   String sql = "";
	   String sql2 = "";
	   System.out.println(billno);
		try{
			InputStream is = new FileInputStream(new File(path));
			Workbook wbk = null;
			String scdate = "";
			String invcode = "";
			String plannum = "";
			String pk_invbasdoc = "";
			String sdate = "";
			String sdate1 = "";
			String bc = "";
			String remark = "";
			String banci = "";
			wbk = Workbook.getWorkbook(is);
			Sheet sheet = wbk.getSheet(0);
			user = sheet.getCell(5,1).getContents();
			conn = DBOracleconn.getDBConn();
			stmt = conn.createStatement();
			int count = 0;
			try{
				for(int i = 3 ; i < sheet.getRows(); i ++){
					Cell c10 = sheet.getCell(0,i);
					scdate = c10.getContents();
					Cell c11 = sheet.getCell(1,i);
					banci = c11.getContents();
					Cell cs = sheet.getCell(2,i);
					invcode = cs.getContents();
					Cell c12 = sheet.getCell(11,i);
					plannum = c12.getContents();
					Cell c13 = sheet.getCell(13,i);
					remark = c13.getContents();
					if(!"".equals(scdate) && !scdate.equals("计划编制：")){
						scdate = scdate.replaceAll("/", "-");
						sdate+=","+scdate;
						if(sdate1.indexOf(scdate) == -1 ){
							sdate1+=","+scdate;
							count ++;
						}
						rs = stmt.executeQuery("select pk_invbasdoc from bd_invbasdoc where invcode = '"+invcode+"'");
						while(rs.next()){
							pk_invbasdoc = rs.getString("pk_invbasdoc");
						}
						String[] ssss = sdate1.split(",");
						for(int j = 0 ; j < ssss.length ; j ++){
							if(!ssss[j].equals("") && ssss[j].equals(scdate)){
								//System.out.print(billno+ j+"*****");
								//System.out.println(ssss[j]+"---"+j);
								sql = "insert into sn_plan_product2 (BILLNO,PK_PROD,PLANNUM,REALHOURS,PLANNUMOLD,planmemo,classes)";
								sql += " values('"+billno+j+"','"+pk_invbasdoc+"','"+plannum+"','0','"+plannum+"','"+remark+"','"+banci+"')";
								System.out.println(sql);
								stmt.execute(sql);
								System.out.println(sql);
							}
						}
						//System.out.println(plannum);
					}
				}
				//System.out.println(sdate1);
				String[] ssss = sdate1.split(",");
				for(int j = 0 ; j < ssss.length ; j ++){
					if(!ssss[j].equals("")){
					  sql2 = "insert into sn_plan_product (BILLNO,PK_FACTORY,BILLDATE,MAKERUSER,FSTATUS)";
					  sql2 += " values('"+billno+j+"','"+pk_factory+"','"+ssss[j]+"','"+user+"','1')";
					  stmt.execute(sql2);
					  System.out.println(sql2);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
		            conn.commit();
		            DBOracleconn.closeDBconn();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
   }
   
   public static void main(String[] args){
	   try {
		//insertsnquantity("aa","aa","aa");
	} catch (Exception e) {
		e.printStackTrace();
	}
   }
}
