package com.tend.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class DownFileServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7837273446140667417L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String filename=request.getParameter("filepath");
		//String filename=request.getParameter("filepath");
		String filename1 = filename.substring(filename.lastIndexOf("/")+1);
		java.io.BufferedInputStream bis=null;
		java.io.BufferedOutputStream  bos=null;
		String filepath = request.getRealPath(filename);
		System.out.println(filepath);
		File file = new File(filepath);
		//if(file.exists()){
			try{
				System.out.println(response.getCharacterEncoding());
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-disposition","attachment; filename="+new String(filename1.getBytes("gb2312"), "ISO_8859_1"));  
				bis =new BufferedInputStream(new FileInputStream(filepath));
				bos=new BufferedOutputStream(response.getOutputStream()); 
				byte[] buff = new byte[4096];
				int bytesRead;
				while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff,0,bytesRead);
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally {
			 if (bis != null)bis.close();
			 if (bos != null)bos.close();
			}
		//}else{
//			PrintWriter out = response.getWriter();
//			out.println("<script language='javascript'>window.alert('"+new String("该文件不存在！".getBytes("gb2312"), "ISO_8859_1")+"');history.back(-1);</script>");
//			out.flush();
//			out.close();
//		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}
	
	public static String encodingFileName(String fileName) {   
        String returnFileName = "";   
        try {   
            returnFileName = URLEncoder.encode(fileName, "UTF-8");   
            returnFileName = StringUtils.replace(returnFileName, "+", "%20");   
            if (returnFileName.length() > 150) {   
                returnFileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");   
                returnFileName = StringUtils.replace(returnFileName, " ", "%20");   
            }
        } catch (UnsupportedEncodingException e) {   
            e.printStackTrace();  
        }   
        return returnFileName;   
    }  

}
