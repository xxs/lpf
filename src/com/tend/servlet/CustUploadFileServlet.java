package com.tend.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

public class CustUploadFileServlet extends HttpServlet{
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
	      out.print("<script language=\"javascript\" >window.opener.document."+formname+"."+picurl+".value='"+file1+"'</script>");
	      //out.print("<script language=\"javascript\" >window.opener.document.getElementById('"+myframeurl+"').src='"+file1+"'</script>");
	      out.print("<script language=\"javascript\" >");
	      out.print("window.alert(\"文件上传成功!\");");
	      out.print("window.close();");
	      out.print("</script>");
	      System.out.println("<script language=\"javascript\" >window.opener.document.getElementById('"+myframeurl+"').src='"+file1+"'</script>");
	}catch(Exception e){
		System.out.println(e.getMessage());
	}
	out.close();
}
}
