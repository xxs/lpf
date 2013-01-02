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

public class PaichanUploadFileServlet1 extends HttpServlet{
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
		         insertsnquantity(request.getRealPath(file1));
		         out.print("<script language=\"javascript\" >");
			     out.print("window.alert(\"文件1导入成功!\");");
			     out.print("window.close();");
			     out.print("</script>");
		      }catch(Exception e){
		    	  e.printStackTrace();
		    	  out.print("<script language=\"javascript\" >");
			      out.print("window.alert(\"文件1导入失败!\");");
			      out.print("window.close();");
			      out.print("</script>");
		      }
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		out.close();
   }
   public static void insertsnquantity(String path) throws Exception{
	   //String path = "D:\\NC产品.xls";
		String sql = "";
		try{
			InputStream is = new FileInputStream(new File(path));
			Workbook wbk = null;
			String pk_calbody = "";
			String pk_invbasdoc = "";
			String plannum = "";
			String outnnumber = "";
			String innumber = "";
			int ncquantity = 0;
			int weiquantity = 0;
			int stockquantity = 0 ;
			String month = "";
			wbk = Workbook.getWorkbook(is);
			Sheet sheet = wbk.getSheet(0);
			Cell c00 = sheet.getCell(0,0);
			pk_calbody = c00.getContents();
			Cell c01 = sheet.getCell(2,0);
			pk_invbasdoc = c01.getContents();
			Cell c02 = sheet.getCell(5,0);
			plannum = c02.getContents();
			Cell c03 = sheet.getCell(6,0);
			outnnumber = c03.getContents();
			Cell c04 = sheet.getCell(7,0);
			innumber = c04.getContents();
			conn = DBOracleconn.getDBConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select to_char(sysdate+7,'yyyy-mm') month from dual");
			while(rs.next()){
				month = rs.getString("month");
			}
			try{
				for(int i = 1 ; i < sheet.getRows(); i ++){
					Cell c10 = sheet.getCell(0,i);
					pk_calbody = c10.getContents();
					Cell c11 = sheet.getCell(2,i);
					pk_invbasdoc = c11.getContents();
					Cell c12 = sheet.getCell(5,i);
					plannum = c12.getContents();
					if(plannum.equals("")){
						plannum = "0";
					}
					Cell c13 = sheet.getCell(6,i);
					outnnumber = c13.getContents();
					if(outnnumber.equals("")){
						outnnumber = "0";
					}
					Cell c14 = sheet.getCell(7,i);
					innumber = c14.getContents();
					if(innumber.equals("")){
						innumber = "0";
					}
					rs = stmt.executeQuery("select nc_quantity,stoknnumber from sn_quantity_query where pk_calbody = '"+pk_calbody+"' and nc_prod_pk = '"+pk_invbasdoc+"' and month = '"+month+"'");
					while(rs.next()){
						ncquantity = rs.getInt("nc_quantity");
						weiquantity = rs.getInt("stoknnumber");
					}
					System.out.println(ncquantity-weiquantity+Integer.parseInt(plannum)-Integer.parseInt(outnnumber)+Integer.parseInt(innumber));
					stockquantity = ncquantity-weiquantity+Integer.parseInt(plannum)-Integer.parseInt(outnnumber)+Integer.parseInt(innumber);
					sql = "update sn_quantity_query set plannumber = '"+plannum+"',outnnumber = '"+outnnumber+"',innumber = '"+innumber+"',STOCKQUANTITY='"+stockquantity+"' ";
					sql += " where pk_calbody = '"+pk_calbody+"' and nc_prod_pk = '"+pk_invbasdoc+"' and month = '"+month+"'";
					//stmt.addBatch(sql);
					//stmt.executeBatch();
					stmt.execute(sql);
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
}
