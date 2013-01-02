package com.nc.sendMessage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RecReqMess extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		String phones = req.getParameter("phones");
		String Text = req.getParameter("Text");
		try {
			SendTextServer.dopost(Text, phones);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
		throws ServletException, IOException {
		this.doGet(request, resp);
	}
}
