package com.tend.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.htjs.util.FunctionLib;
import com.jspsmart.upload.SmartUpload;

public class FileDownload extends HttpServlet {
	 private Properties initProps;

	    public FileDownload()
	    {
	        initProps = new Properties();
	    }

	    public void init(ServletConfig config)
	        throws ServletException
	    {
	        super.init(config);
	        String currParam;
	        for(Enumeration e = config.getInitParameterNames(); 
			e.hasMoreElements(); 
			initProps.put(currParam, config.getInitParameter(currParam)))
	            currParam = (String)e.nextElement();

	    }

	    public void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException
	    {
	        doGet(request, response);
	    }

	    public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException
	    {
	        String epassmsg = "";
	        String qryDoc = "";
	        try
	        {
	            if(request.getPathInfo() == null)
	            {
	                epassmsg = request.getServletPath();
	                qryDoc = getServletConfig().getServletContext().getRealPath(epassmsg);
	            } else
	            {
	                qryDoc = request.getPathTranslated();
	            }
	            SmartUpload su = new SmartUpload();
	            su.initialize(getServletConfig(), request, response);
	            su.setContentDisposition(null);
	            su.downloadFile(qryDoc);
	        }
	        catch(Exception e)
	        {
	            try
	            {
	                if(request.getPathInfo() == null)
	                {
	                    epassmsg = new String(epassmsg.getBytes("8859_1"), "GB2312");
	                    qryDoc = getServletConfig().getServletContext().getRealPath(epassmsg);
	                } else
	                {
	                    qryDoc = request.getPathTranslated();
	                }
	                SmartUpload su = new SmartUpload();
	                su.initialize(getServletConfig(), request, response);
	                su.setContentDisposition(null);
	                su.downloadFile(qryDoc);
	            }
	            catch(Exception ex)
	            {
	                PrintWriter out = response.getWriter();
	                out.println("<html>\n");
	                out.println("<head>\n");
	                out.println("<script type=\"text/javascript\" language=\"JavaScript\" src=\"../js/title.js\"></script>\n");
	                out.println("<meta http-equiv=\"Content-Type\" content=\"text ml; charset=gb2312\">\n");
	                out.println("<script>");
	                out.println(FunctionLib.gbToIso("alert('下载失败！文件可能已被删除')"));
	                out.println("history.back();");
	                out.println("</script>");
	                out.println("</head>\n");
	                out.println("  < ml>\n");
	                String errorMsg = "下载时出现错误" + request.getServletPath() + ex.getMessage();
	                System.out.println(errorMsg);
	            }
	        }
	    }
}
