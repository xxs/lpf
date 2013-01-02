package com.nc.print;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Cs extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding( "gb2312"); 
		resp.setContentType( "text/html;   charset=gb2312 "); 
		resp.setHeader("Cache-Control", "no-cache");
		// TODO Auto-generated method stub
//      通俗理解就是书、文档
	      Book book = new Book();
	    //      设置成竖打
	      PageFormat pf = new PageFormat();
	      pf.setOrientation(PageFormat.PORTRAIT);
	    //      通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。
	      Paper p = new Paper();
	      p.setSize(590,840);//纸张大小 
	      //p.setImageableArea(10,10, 590,840);//A4(595 X 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
	      p.setImageableArea(10,10, 590,280);//A4(595 X 842)设置打印区域，其实0，0应该是72，72，因为A4纸的默认X,Y边距是72
	      pf.setPaper(p);
	    //      把 PageFormat 和 Printable 添加到书中，组成一个页面
	      book.append(new PrintTest1(), pf);

	     //获取打印服务对象
	       PrinterJob job = PrinterJob.getPrinterJob();      
	     // 设置打印类
	       job.setPageable(book);
	     
	     try {
	         //可以用printDialog显示打印对话框，在用户确认后打印；也可以直接打印
	         //boolean a=job.printDialog();
	         //if(a)
	         //{        
	           job.print();
	         //}
	       } catch (PrinterException e) {
	           e.printStackTrace();
	       }
	}
	
}
