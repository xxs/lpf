/**
 *  报表服务总Servlet
 */
package com.newapp.report.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class JasperReportsView extends HttpServlet {

	private ServletConfig config;
	private static final String pagecount = "PAGE_COUNT" ;
	private static final String page_key = "CURRENT_PAGE" ;
	private static Map jasperFiles = new HashMap();
	static {
		jasperFiles.put("monthtask", "/report/monthtask.jasper");
		jasperFiles.put("orgtask", "/report/orgtask.jasper");
		jasperFiles.put("monthtasknum", "/report/monthtakenum.jasper");
		jasperFiles.put("orgtasknum", "/report/orgtasknum.jasper");
		jasperFiles.put("plandetai", "/report/plandetai.jasper");
		jasperFiles.put("orgplandetai", "/report/orgplandetai.jasper");
		jasperFiles.put("rerequire", "/report/rerequire.jasper");
		jasperFiles.put("xun", "/report/xun.jasper");
		jasperFiles.put("orgtaskbook", "/report/orgtaskbook.jasper");
		jasperFiles.put("orgxum", "/report/orgxum.jasper");
		jasperFiles.put("areataskbook", "/report/areataskbook.jasper");
		jasperFiles.put("conductor_report", "/report/conductor_report.jasper");
		jasperFiles.put("allplandetai", "/report/allplandetai.jasper");
		jasperFiles.put("area_conductor_report", "/report/area_conductor_report.jasper");
		jasperFiles.put("goodplandetai", "/report/goodplandetai.jasper");
		jasperFiles.put("forecast", "/report/forecast.jasper");
		jasperFiles.put("org_forecast", "/report/org_forecast.jasper");
		jasperFiles.put("planexact", "/report/planexact.jasper");
		jasperFiles.put("order", "/report/order.jasper");
		jasperFiles.put("dealer", "/report/dealer.jasper");
		jasperFiles.put("dealer2", "/report/dealer2.jasper");
		jasperFiles.put("dealer3", "/report/dealer3.jasper");
		jasperFiles.put("branchfee", "/report/branchfee.jasper");
		
		
		
		
		
		
		
		
		
		
		
		
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req,response);
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.config = config;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map parameters = convertReportParam(request);
		String exp = request.getParameter("expType");
		String beanType = request.getParameter("beanType");
		String jasperFile = request.getParameter("jasperFile");
		ServletContext servletContext = request.getSession().getServletContext();
		byte[] output;
		JasperPrint jasperPrint = null;
		//读取报表路径
		String readjasperFile = servletContext.getRealPath((String) jasperFiles.get(jasperFile));
        
		try {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(readjasperFile);
			if (StringUtils.isEmpty(beanType)) {
				WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
				String dataSource = this.config.getInitParameter("dataSource");//
				DataSource reportSource = (DataSource) wc.getBean(dataSource);
				jasperPrint = fillReportForJdbc(parameters, reportSource,jasperReport);
			} else {

			}
			JRExporter exporter;
			
			if ("pdf".equals(exp)) {
				response.setContentType("application/pdf");
				exporter = new JRPdfExporter();
			} else if ("text".equals(exp)) {
				response.setContentType("text/plain");
				exporter = new JRCsvExporter();
			} else if ("html".equals(exp)) {
				response.setContentType("text/html");
				 String Stringpage = request.getParameter("page");
				 int countpage = jasperPrint.getPages().size();
				 int page = NumberUtils.toInt(Stringpage,0);
				 if(page>countpage){
					 page = countpage-1;
				 }
				 request.setAttribute(page_key, page);
				 request.setAttribute(pagecount, countpage);
				 Map imagesMap = new HashMap();
				 request.getSession(true).setAttribute("IMAGES_MAP",imagesMap);
				
				exporter = new JRHtmlExporter();
			    exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP,imagesMap);
			    String imageServletUrl = this.config.getInitParameter("imgpath");
			    imageServletUrl = StringUtils.isEmpty(imageServletUrl)?"img":imageServletUrl;
			    //exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath()+"/"+imageServletUrl+"/"); 
			    //exporter.setParameter(JRHtmlExporterParameter.PAGE_INDEX, page);
			    //exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, "<script>var pagecount = "+countpage+";var page = page </script>");
				exporter.setParameter( JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
			    
			    exporter.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE); 
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				request.getSession().setAttribute("net.sf.jasperreports.j2ee.jasper_print", jasperPrint);
			} else if ("excel".equals(exp)) {
				response.setContentType("application/vnd.ms-excel");
				exporter = new JRXlsExporter();
				exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
				//exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);

			} else if ("xml".equals(exp)) {
				response.setContentType("text/xml");
				exporter = new JRXmlExporter();
			} else if ("rtf".equals(exp)) {
				response.setContentType("application/rtf");
				exporter = new JRRtfExporter();
			} else {
				throw new ServletException("Unknown report format: " + exp);
			}

			output = exportReportToBytes(jasperPrint, exporter);
		} catch (Exception e) {
			String message = "Error producing " + exp + " report for uri "+ readjasperFile;

			throw new ServletException(e.getMessage(), e);
		}
		response.setContentLength(output.length);
		writeReport(response, output);

	}
     //为报表连结数据
	private JasperPrint fillReportForJdbc(Map parameters,
			DataSource reportSource, JasperReport jasperReport)
			throws SQLException, JRException {
		JasperPrint jasperPrint;
		Connection conn = reportSource.getConnection();
		try{
			jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, conn);
		}finally{
			conn.close();
		}
		return jasperPrint;
	}
    //request参数转Map值
	private Map convertReportParam(HttpServletRequest request) {
		Map orgParam = request.getParameterMap();
		Map param = new HashMap();
		Set keys = orgParam.keySet();  
		for (Iterator it = keys.iterator(); it.hasNext();) {
			String key = (String) it.next();
			Object obj = orgParam.get(key);
			if (obj instanceof Object[]) {
				param.put(key, ((Object[]) obj)[0]);
			} else {
				param.put(key, obj);
			}
		}
		return param;
	}

	private byte[] exportReportToBytes(JasperPrint jasperPrint,
			JRExporter exporter) throws JRException {
		byte[] output;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
		exporter.exportReport();
		output = baos.toByteArray();
		return output;
	}

	private void writeReport(HttpServletResponse response, byte[] output)
			throws ServletException {
		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			outputStream.write(output);
			outputStream.flush();
		} catch (IOException e) {
			// LOG.error("Error writing report output", e);
			throw new ServletException(e.getMessage(), e);
		} finally {
			try {
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				// /LOG.error("Error closing report output stream", e);
				throw new ServletException(e.getMessage(), e);
			}
		}
	}
	



}
