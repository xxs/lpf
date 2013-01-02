package com.tend.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ScImpServlet1111111 extends HttpServlet{
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
								sql = "insert into sn_year_plan_product2 (BILLNO,PK_PROD,PLANNUM,REALHOURS,PLANNUMOLD,planmemo)";
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
					if(!ssss[j].equals("")){
					  sql2 = "insert into sn_year_plan_product (BILLNO,PK_FACTORY,years,MAKERUSER,FSTATUS)";
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

   public static void insertsnquantity(String path,String billno,String pk_factory) throws Exception{
	   //String path1 = "D:\\生产通知单.xls";
	   String user = "";
	   String sql = "";
	   String sql2 = "";
	   System.out.println(billno);
		try{
			InputStream is = new FileInputStream(new File(path));
			HSSFWorkbook workBook = new HSSFWorkbook(is);
			HSSFSheet sheet = workBook.getSheetAt(0);
		    int rows = sheet.getPhysicalNumberOfRows();
		    String scdate = "";
			String invcode = "";
			String plannum = "";
			String pk_invbasdoc = "";
			String sdate = "";
			String sdate1 = "";
			String bc = "";
			String remark = "";
			String banci = "";
			
			HSSFRow row02 = sheet.getRow(1);
			HSSFCell cel03 = row02.getCell((short) 5); 
			user = cel03.getRichStringCellValue().toString();
			conn = DBOracleconn.getDBConn();
			stmt = conn.createStatement();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			int count = 0;
			for(int i = 0 ; i < rows; i ++){
				if(i > 2){
					HSSFRow row0 = sheet.getRow(i);
					String value = "";
					Date date = new Date();
					for(short j = 0 ; j < 14; j ++){
						HSSFCell cel0 = row0.getCell(j);
						switch (cel0.getCellType()) {
							case HSSFCell.CELL_TYPE_NUMERIC: // 数值型 
								 if(HSSFDateUtil.isCellDateFormatted(cel0)) { 
		                             //如果是date类型则 ，获取该cell的date值 
									 if(j == 0){
										 date = HSSFDateUtil.getJavaDate(cel0.getNumericCellValue());
									     value = sf.format(date);
									 }
		                         }else{//纯数字 
		                        	 //if(j == 2){
		                        		// DecimalFormat df = new DecimalFormat("0");
		                        		// String whatYourWant = df.format(cel0.getNumericCellValue());
		                        		// value = whatYourWant;
		                        	 //}else{
		                        		 value = String.valueOf(cel0.getNumericCellValue()); 
		                        	 //} 
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
						if(j == 0){
							scdate = value;
						}
						System.out.println(i);
						if(!scdate.equals("") || null !=scdate || scdate.indexOf("计划编制：") != -1){
							System.out.print("aa");
							break;
						}else{System.out.print("bb");
							if(j == 0){ 
								sdate+=","+scdate;
								if(sdate1.indexOf(scdate) == -1 ){
									sdate1+=","+scdate;
									count ++;
								}
								//System.out.println(scdate);
							}
							if(j == 1){ 
								banci = value;
								//System.out.println(banci+"**");
							}
							if(j == 2){ 
								invcode = value;
							}
							if(j == 11){ 
								plannum = value;
							}
							if(j == 13){ 
								remark = value;
							}
						}
						if(!scdate.equals("") || scdate.indexOf("计划编制：") != -1){
							break;
						}else{
						/*rs = stmt.executeQuery("select pk_invbasdoc from bd_invbasdoc where invcode = '"+invcode+"'");
						while(rs.next()){
							pk_invbasdoc = rs.getString("pk_invbasdoc");
						}
						String[] ssss = sdate1.split(",");
						sql = "insert into sn_plan_product2 (BILLNO,PK_PROD,PLANNUM,REALHOURS,PLANNUMOLD,planmemo,classes)";
						sql += " values('"+billno+j+"','"+pk_invbasdoc+"','"+plannum+"','0','"+plannum+"','"+remark+"','"+banci+"')";
						//System.out.println(sql);
						//stmt.execute(sql);
						System.out.println(sql); */
						}
					}
				}
			}
			String[] ssss = sdate1.split(",");
			for(int j = 0 ; j < ssss.length ; j ++){
				if(!ssss[j].equals("")){
				  sql2 = "insert into sn_plan_product (BILLNO,PK_FACTORY,BILLDATE,MAKERUSER,FSTATUS)";
				  sql2 += " values('"+billno+j+"','"+pk_factory+"','"+ssss[j]+"','"+user+"','1')";
				  //stmt.execute(sql2);
				  System.out.println(sql2);
				}
			}
			/*Workbook wbk = null;
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
						
						SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
						
						System.out.println(sf.format(scdate));
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
			}*/
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
