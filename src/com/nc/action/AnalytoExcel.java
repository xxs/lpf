/**
 * 单品销售导出EXCEL处理
 */
package com.nc.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.nc.comm.NewAppUtil;
import com.nc.dao.ibatis.IDAO;
import com.newapp.report.action.AnalysereportExcel;

public class AnalytoExcel extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(req, response);
	}
	/**
	 * 导出操作
	 * 
	 * AnalysereportExcel生成Excel
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		try{
			AnalysereportExcel exceltool = new AnalysereportExcel();
			ServletContext servletContext = request.getSession().getServletContext();
			Map param = NewAppUtil.convertReportParam(request);
			WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			IDAO baseDao = (IDAO) wc.getBean("salBatisDao");
			List datas =baseDao.getDataByObject("SalAnalyseSql", param);
			response.setContentType("application/x-download");
			response.setContentType("application/vnd.ms-excel");
			//response.setHeader("Content-Disposition", "attachment; filename=excle" );
			exceltool.processListToExcel(response.getOutputStream(), datas);
			
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

	}

}
