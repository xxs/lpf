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

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

public class PersonListServlet extends HttpServlet {

	private static final long serialVersionUID = 4518447301449717427L;
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
			//System.out.println("createdate:"+time);
			String vname = request.getParameter("vname");
			String vremark = request.getParameter("vremark");
			String vdate = request.getParameter("vdate");
			String vdatescope = request.getParameter("vdatescope");
			String vaddress = request.getParameter("vaddress");
			String vmaker = request.getParameter("vmaker");
			String vdeptid = request.getParameter("vdeptid");

			String names = "";
			String path = request.getRealPath("/")+"/fileupload/file/personlistfile";
			String syspath = path;
			String tpath = path + "/temp/";
			String file1 = "";
			File FileUploadDir = new File(path);
			File FileUploadDir1 = new File(tpath);
			if (!FileUploadDir.exists())
				FileUploadDir.mkdir(); // 生成上传文件的目录
			if (!FileUploadDir1.exists())
			FileUploadDir1.mkdir();
			path += "/pcexcel";
			tpath = path + "/temp/";
			FileUploadDir = new File(path);
			FileUploadDir1 = new File(tpath);
			if (!FileUploadDir.exists())
				FileUploadDir.mkdir(); // 生成上传文件的目录
			if (!FileUploadDir1.exists())
				FileUploadDir1.mkdir();
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
							+ "pcexcel"
							+ names.substring((names.lastIndexOf("-") + 1),
									names.length());
					item.write(new File(path + "/" + names));
					// size = size/(1024);
					// String s = size+"K";
					file.add(names);
					// size1.add(s);
				}
			}
			file1 = "/fileupload/file/personlistfile/pcexcel/" + file.get(0);
			try {

				insertVsession(request.getRealPath(file1), vname,vremark,vdate,vdatescope,vaddress,vmaker,vdeptid,resp);

				out.print("<script language=\"javascript\" >");
				out.print("window.alert(\"申请会议成功!！！\");");
				out.print("window.close();");
				out.print("opener.location.reload();");
				out.print("</script>");
			} catch (Exception e) {
				e.printStackTrace();
				out.print("<script language=\"javascript\" >");
				out.print("window.alert(\"申请会议失败1!\");");
				out.print("window.close();");
				out.print("opener.location.reload();");
				out.print("</script>");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			out.print("<script language=\"javascript\" >");
			out.print("window.alert(\"申请会议失败2!\");");
			out.print("window.close();");
			out.print("opener.location.reload();");
			out.print("</script>");
		}
		out.close();
	}

	public static void insertVsession(String path, String vname,String vremark,String vdate,String vdatescope,String vaddress,String vmaker,String vdeptid,HttpServletResponse resp) throws Exception {
		//System.out.println("开始执行保存方法");
		PrintWriter out = resp.getWriter();
		String sql = "";
		//执行插入表头操作
		conn = DBOracleconn.getDBConn();
		conn.setAutoCommit(false); //设置不会自动提交 
		stmt = conn.createStatement();
		sql = "insert into XX_VSESSION (PK_VID, VNAME, VREMARK,VDATE,VDATESCOPE,VADDRESS,CREATEDATE,VSTATE,VMAKER,VDEPTID)";
        sql = sql + " values(XX_VSESSION_SEQ.nextval,'" + vname + "','" + vremark + "','" + vdate + "','" + vdatescope + "','" + vaddress + "',to_char(sysdate,'YYYY-MM-DD HH24:MI:SS'),'申请','"+vmaker+"',"+vdeptid+")";
        //System.out.println(sql);
        Integer pk_vid = 0;
		try {
			stmt.execute(sql);
			//System.out.println("select max(pk_vid) pk_vid from XX_VSESSION  where vname = '"+vname+"' and vremark = '"+vremark+"' and vmaker = '"+vmaker+"'");
			rs = stmt.executeQuery("select max(pk_vid) pk_vid from XX_VSESSION  where vname = '"+vname+"' and vremark = '"+vremark+"' and vmaker = '"+vmaker+"' and vdeptid = "+vdeptid+"");
			//System.out.println(pk_vid);
			while(rs.next()){
				pk_vid = rs.getInt("pk_vid");
			}
			//System.out.println(pk_vid);
		} catch (Exception e) {
			e.printStackTrace();
			out.print("<script language=\"javascript\" >");
			out.print("window.alert(\"申请会议失败!\");");
			out.print("window.close();");
			out.print("opener.location.reload();");
			out.print("</script>");
		} 
		
		//执行插入表体操作
		try {
			InputStream is = new FileInputStream(new File(path));
			Workbook wbk = null;
			String username = "";
			String userphone = "";
			wbk = Workbook.getWorkbook(is);
			Sheet sheet = wbk.getSheet(0);
			try {
				for (int i = 1; i < sheet.getRows(); i++) {
					username = sheet.getCell(0, i).getContents();
					userphone = sheet.getCell(1, i).getContents();
						if(username!="" && userphone!=""){
							sql = "insert into XX_VSESSION_INFO (PK_VID, USERNAME, USERPHONE)";
							sql = sql + " values(" + pk_vid + ",'" + username + 
									"','" + userphone + "')";
							//System.out.println(sql);
							stmt.execute(sql);
						}
					}
				out.print("<script language=\"javascript\" >");
				out.print("window.alert(\"申请会议成功!！！\");");
				out.print("window.close();");
				out.print("opener.location.reload();");
				out.print("</script>");
				conn.commit();
			} catch (Exception e) {
				e.printStackTrace();
				out.print("<script language=\"javascript\" >");
				out.print("window.alert(\"申请会议失败!\");");
				out.print("window.close();");
				out.print("opener.location.reload();");
				out.print("</script>");
			} finally {
				try {
					DBOracleconn.closeDBconn();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("<script language=\"javascript\" >");
			out.print("window.alert(\"申请会议失败!导入的excel必须是office2003版的模板文件\");");
			out.print("window.close();");
			out.print("opener.location.reload();");
			out.print("</script>");
		}
		out.close();
	}

}
