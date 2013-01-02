package com.tend.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ReadExcel extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6828375316878049810L;
	public ReadExcel(){
		
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/html;charset=gb2312");
		PrintWriter out = resp.getWriter();
		String filepath = new String(req.getParameter("F:\\newsynear\\WebRoot\\fileupload\\file\\custfile\\3\\1104110949453Spdd_020251.xls"));
		try{
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filepath));
			HSSFWorkbook workBook = new HSSFWorkbook(fs);
			HSSFSheet sheet = workBook.getSheetAt(0);
		    
		}catch(Exception e){
			
		}
		out.println();
	}
	
	
}
