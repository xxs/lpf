package com.nc.print;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;


public class JasperPrintServlet extends HttpServlet {
	public void service( 
			HttpServletRequest request, 
			HttpServletResponse response 
			) throws IOException, ServletException 
			{ 
			ServletContext context = this.getServletConfig().getServletContext(); 

			File reportFile = new File(context.getRealPath("/reports/WebappReport.jasper")); 
			if (!reportFile.exists()) 
			throw new JRRuntimeException("File WebappReport.jasper not found. The report design must be compiled first."); 

			Map parameters = new HashMap(); 
			parameters.put("ReportTitle", "Address Report"); 
			parameters.put("BaseDir", reportFile.getParentFile()); 

			JasperPrint jasperPrint = null; 

			try 
			{ 
			JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath()); 

			jasperPrint = 
			JasperFillManager.fillReport( 
			jasperReport, 
			parameters, 
			new WebappDataSource() 
			); 
			} 
			catch (JRException e) 
			{ 
			response.setContentType("text/html"); 
			PrintWriter out = response.getWriter(); 
			out.println("<html>"); 
			out.println("<head>"); 
			out.println("<title>JasperReports - Web Application Sample</title>"); 
			out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../stylesheet.css\" title=\"Style\">"); 
			out.println("</head>"); 

			out.println("<body bgcolor=\"white\">"); 

			out.println("<span class=\"bnew\">JasperReports encountered this error :</span>"); 
			out.println("<pre>"); 

			e.printStackTrace(out); 

			out.println("</pre>"); 

			out.println("</body>"); 
			out.println("</html>"); 

			return; 
			} 

			if (jasperPrint != null) 
			{ 
			response.setContentType("application/octet-stream"); 
			ServletOutputStream ouputStream = response.getOutputStream(); 

			ObjectOutputStream oos = new ObjectOutputStream(ouputStream); 
			oos.writeObject(jasperPrint); 
			oos.flush(); 
			oos.close(); 

			ouputStream.flush(); 
			ouputStream.close(); 
			} 
			else 
			{ 
			response.setContentType("text/html"); 
			PrintWriter out = response.getWriter(); 
			out.println("<html>"); 
			out.println("<head>"); 
			out.println("<title>JasperReports - Web Application Sample</title>"); 
			out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../stylesheet.css\" title=\"Style\">"); 
			out.println("</head>"); 

			out.println("<body bgcolor=\"white\">"); 

			out.println("<span class=\"bold\">Empty response.</span>"); 

			out.println("</body>"); 
			out.println("</html>"); 
			} 
			} 

}
