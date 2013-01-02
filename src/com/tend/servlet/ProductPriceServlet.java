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

public class ProductPriceServlet extends HttpServlet {
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
		try {
			GetDate date = new GetDate();
			String time = date.getDate();
			String type = request.getParameter("type");
			String tendtypeid = request.getParameter("tendtypeid");
			String startdate = request.getParameter("startdate");
			String remark = request.getParameter("remark");
			remark = new String(remark.getBytes("iso-8859-1"),"GB2312");
			String makerID = request.getParameter("makerID");

			String names = "";
			String path = request.getRealPath("/");
			String syspath = "/fileupload/file/";
			path += syspath + type;
			String tpath = path + "/temp/";
			String file1 = "";
			File FileUploadDir = new File(path);
			File FileUploadDir1 = new File(tpath);
			if (!FileUploadDir.exists())
				FileUploadDir.mkdir(); // 生成上传文件的目录
			if (!FileUploadDir1.exists())
				FileUploadDir1.mkdir();
			if (tendtypeid != null || tendtypeid != "") {
				path += "/" + tendtypeid;
				tpath = path + "/temp/";
				FileUploadDir = new File(path);
				FileUploadDir1 = new File(tpath);
				if (!FileUploadDir.exists())
					FileUploadDir.mkdir(); // 生成上传文件的目录
				if (!FileUploadDir1.exists())
					FileUploadDir1.mkdir();
			}
			String tempPath = tpath;
			DiskFileUpload fu = new DiskFileUpload();
			fu.setHeaderEncoding("gb2312");
			resp.setCharacterEncoding("gb2312");
			fu.setSizeMax(100 * 1024 * 1024);// 设置最大文件尺寸，这里是1M
			fu.setSizeThreshold(4096);// 设置缓冲区大小，这里是4kb
			fu.setRepositoryPath(tempPath);
			// 设置临时目录：
			List fileItems = fu.parseRequest(request);
			// System.out.println(fileItems);// 得到所有的文件：
			Iterator iter = fileItems.iterator();
			ArrayList file = new ArrayList();
			ArrayList size1 = new ArrayList();
			while (iter.hasNext()) { // 依次处理每一个文件：
				FileItem item = (FileItem) iter.next();
				// 忽略其他不是文件域的所有表单信息
				if (!item.isFormField()) {
					names = item.getName();
					long size = item.getSize();
					if ((names == null || names.equals("")) && size == 0) {
						continue;
					}
					names = names.replace('\\', '-');
					names = time
							+ tendtypeid
							+ names.substring((names.lastIndexOf("-") + 1),
									names.length());
					item.write(new File(path + "/" + names));
					// size = size/(1024);
					// String s = size+"K";
					file.add(names);
					// size1.add(s);
				}
			}
			if (tendtypeid != null || tendtypeid != "") {
				file1 = syspath + type + "/" + tendtypeid + "/" + file.get(0);
			} else {
				file1 = syspath + type + file.get(0);
			}
			try {

				insertMaterialPrice(request.getRealPath(file1), startdate,
						remark, makerID);

				out.print("<script language=\"javascript\" >");
				out.print("window.alert(\"文件导入成功!\");");
				out.print("window.close();");
				out.print("opener.location.reload();");
				out.print("</script>");
			} catch (Exception e) {
				e.printStackTrace();
				out.print("<script language=\"javascript\" >");
				out.print("window.alert(\"文件导入失败!\");");
				out.print("window.close();");
				out.print("opener.location.reload();");
				out.print("</script>");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		out.close();
	}

	public static void insertMaterialPrice(String path, String startdate,
			String remark, String kamerID) throws Exception {
		String sql = "";
		System.out.println(startdate);
		System.out.println(remark);
		System.out.println(kamerID);
		try {
			InputStream is = new FileInputStream(new File(path));
			Workbook wbk = null;
			String data = "";
			String prod_nc_pk = "";
			String oldcostprice = "0";
			String costprice = "";
			wbk = Workbook.getWorkbook(is);
			Sheet sheet = wbk.getSheet(0);
			conn = DBOracleconn.getDBConn();
			stmt = conn.createStatement();
			int count = 0;
			try {
				for (int i = 1; i < sheet.getRows(); i++) {
					Cell c10 = sheet.getCell(0, i);
					prod_nc_pk = c10.getContents();
					Cell cs = sheet.getCell(3, i);
					costprice = cs.getContents();

					if (!"".equals(prod_nc_pk)) {
						rs = stmt.executeQuery("select count(0) con from sn_product_price_qc  where prod_nc_pk = '"+prod_nc_pk+"' and startdate = '"+startdate+"'");
						while(rs.next()){
							count = rs.getInt("con");
						}
							if(count<=0){
								 sql = "insert into SN_PRODUCT_PRICE_QC (PROD_NC_PK, OLDCOSTPRICE, COSTPRICE, REMARK, MAKERID, STARTDATE)";
					              sql = sql + " values('" + prod_nc_pk + "','" + oldcostprice + 
					                "','" + costprice + "','" + remark + "','" + kamerID + "','" + 
					                startdate + "')";
								System.out.println(sql);
								stmt.execute(sql);
								System.out.println(sql);
							}
						}
					}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					conn.commit();
					DBOracleconn.closeDBconn();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
