package com.tend.servlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;    
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class BudgetFyFileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private  static String fileUploadName="";
	private   static String fileUpLoadPath="";
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);

	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
	
			throws ServletException, IOException {
        String  area=request.getParameter("area");
        String  year=request.getParameter("year");
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) { 
					String fieldName = item.getFieldName();
				} else { 
					String fileName = item.getName();
				    fileUploadName = fileName.substring(fileName.lastIndexOf("\\") + 1);
				    fileUpLoadPath = request.getSession().getServletContext().getRealPath("/uploadFile/");
					File file = new File(fileUpLoadPath);
					if (!file.exists()) {
						file.mkdir();
					}
					item.write(new File(fileUpLoadPath, fileUploadName));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String  path= fileUpLoadPath+"\\"+fileUploadName;
		if ("".equals(area)||area==null) {
			out.print("<script language=\"javascript\" >");
			out.print("window.alert(\"导入失败!请填写区域\");");
			out.print("window.close();");
     		out.print("opener.location.reload();");
			out.print("</script>");
			
		}else{
			try {
				analyzingExcle(path,resp,area,year);
			} catch (Exception e) {
				e.printStackTrace();
			}
			out.print("<script language=\"javascript\" >");
			out.print("window.alert(\"导入成功!\");");
			out.print("window.close();");
			out.print("opener.location.reload();");
			out.print("</script>");
			
		}
	
		out.flush();
		out.close();
		//解析上传到服务器的文件，并把其中的数据存储到数据库
		
		
		  
	}

	private static void analyzingExcle(String Path,HttpServletResponse resp,String area,String year) throws Exception  {
		PrintWriter out = resp.getWriter();
		System.out.println(Path);
		String sql = "";
		conn = DBOracleconn.getDBConn();
		conn.setAutoCommit(false);
		stmt = conn.createStatement();
		Map map = new HashMap();
		InputStream is = null;
		HSSFWorkbook hssfWorkbook = null;
		String newyear = String.valueOf((Integer.parseInt(year)+1));
		String deletesql = "delete gy_BUDGETEDCOSTTABLE where area = '1002AV100000000CI6ZY' and mon>='"+year+"-07' and mon<='"+newyear+"-06'";
		System.out.println("deletesql:"+deletesql);
		stmt.execute(deletesql);
		conn.commit();
		try {
			is = new FileInputStream(Path);
			hssfWorkbook = new HSSFWorkbook(is);
		} catch (Exception e) {
		}
		
		try {
			
			for (int i = 2; i < 28 ; i++) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(year), 06, 01);
				for (int j = 1; j < 38; j++) {
					if (j % 3 == 2) {
						map.put("月份", sdf.format(gc.getTime()));
						map.put("直营卖场", hssfWorkbook.getSheetAt(0).getRow(i)
								.getCell((short) j));
						gc.add(2, 1);
						continue;
					}
					if (j % 3 == 0) {
						map.put("经销商", hssfWorkbook.getSheetAt(0).getRow(i)
								.getCell((short) j));
						continue;
					}
					if (j % 3 == 1) {
						if (j == 1) {
							map.put("编码", hssfWorkbook.getSheetAt(0).getRow(i)
									.getCell((short) 1));
							continue;
						} else {
							map.put("合计", hssfWorkbook.getSheetAt(0).getRow(i)
									.getCell((short) j));
							
						}
					}
					if (map.size() >= 5) {
						if ("".equals(map.get("经销商"))||map.get("经销商")==null) {
							map.put("经销商", 0);
						}
						if ("".equals(map.get("直营卖场"))||map.get("直营卖场")==null) {
							map.put("直营卖场", 0);
						}
						if ("".equals(map.get("合计"))||map.get("合计")==null) {
							map.put("合计", 0);
						}
						
						System.out.println(map);
						sql = "insert into gy_BUDGETEDCOSTTABLE (CNUMBER, MON, STOREPLACE,AGENCY,AREA,TOTAL)";
						sql = sql + "values('" + map.get("编码") + "','"
								+ map.get("月份") + "','" + map.get("直营卖场")  
								+ "','" +  map.get("经销商") + "','"+ area + "','"
								+  map.get("合计") + "')";
						 // 设置不会自动提交
						
						stmt.addBatch(sql);
						stmt.executeBatch();
						
					}

				}

			}
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			System.out.println("adfdsafeaasdfdasfdsa");
			e.printStackTrace();
//			out.print(" <script type='javascript/text'>alert('注册失败');</script>");
			out.print("<script language=\"javascript\" >");
			out.print("window.alert(\"导入失败!\");");
			out.print("window.close();");
     		out.print("opener.location.reload();");
			out.print("</script>");
		}
		
		finally {
			
			try {
				DBOracleconn.closeDBconn();
			} catch (Exception e) {
				e.printStackTrace();
			}
			is.close();
		}
		

	}
	

}
