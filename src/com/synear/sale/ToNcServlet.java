package com.synear.sale;

//Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
//Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
//Decompiler options: packimports(3) fieldsfirst ansi 
//Source File Name:   OrderSenderServlet.java
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.context.Context;

import net.htjs.srvapp.util.FunctionLib;
import net.htjs.srvapp.web.WebApp;
import net.htjs.srvapp.web.ToNcEngine;

import org.apache.velocity.*;
import org.apache.velocity.app.*;

import java.net.HttpURLConnection;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
//Referenced classes of package com.sunny.sms:
//         OrderSender

public class ToNcServlet extends HttpServlet {
	Logger log = Logger.getLogger(ToNcServlet.class);
	private String server;
	private static List<String> returnMsg = new ArrayList<String>();
	private Properties props = null;

	public ToNcServlet() {
	}

	public void init() throws ServletException {
		// String path = getServletContext().getRealPath("/");
		// propsFileName=path.substring(0,
		// path.length()-1)+"/WEB-INF/velocity.properties";
		// getServletConfig().getInitParameter("org.apache.velocity.properties");
		server = getInitParameter("ncserver");
		// System.out.println("fffpropsFileName"+propsFileName+"...");
		if (server == null)
			server = "http://100.100.1.61:81/service/XChangeServlet?account=0002";
		System.out.println("server:" + server + "...");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String qryDoc;
		String username;
		username = "";
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("GBK");
		try {
			request.getSession().setMaxInactiveInterval(60000);
			username = request.getSession().getAttribute("USERNAME") != null ? request
					.getSession().getAttribute("USERNAME").toString()
					: "";
		}
		// Misplaced declaration of an exception variable
		catch (Exception e) {
			e.printStackTrace();
		}
		if (request.getPathInfo() == null)
			qryDoc = getServletConfig().getServletContext().getRealPath(
					request.getServletPath());
		else
			qryDoc = request.getPathTranslated();
		try {
			WebApp app = new WebApp(FunctionLib.openFile(qryDoc));
			String path = getServletContext().getRealPath("/");
			VelocityEngine ve = new VelocityEngine();
			ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path);
			VelocityContext context = new VelocityContext();

			BufferedOutputStream outb = null;
			InputStream in = null;
			String msg = null;

			ve.init();

			ToNcEngine mainMod = new ToNcEngine(request, response, app,
					context, getServletConfig());
			// 处理用户请求
			Vector vsqls = app.getSqls();
			if (app.getSqlType().equals("select")) {
				List ldata = null;
				for (int i = 0; i < vsqls.size(); i++) {
					ldata = new ArrayList();
					ldata = mainMod.getData(vsqls.elementAt(i).toString(),
							app.getEscape());
					context.put(vsqls.elementAt(i).toString(), ldata);
				}
				context.put("CGI_url", request.getRequestURL().toString());
			}
			Template t = ve.getTemplate(app.getTemplate());
			StringWriter sw = new StringWriter();
			t.merge(context, sw);
			System.out.println("aaaaaaaa" + sw.toString());
			// String url = "http://10.7.3.225:8080/service/XchangeServlet";
			URL realURL = new URL(server);
			HttpURLConnection connection = (HttpURLConnection) realURL
					.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-type", "text/xml");
			connection.setRequestMethod("POST");
			outb = new BufferedOutputStream(connection.getOutputStream());
			outb.write(sw.toString().getBytes());
			outb.close();
			in = new DataInputStream(connection.getInputStream());
			byte[] b = new byte[in.available()];
			in.read(b);
			msg = new String(b, "UTF-8");
			// System.out.println("返回信息:"+msg);
			Document resDoc = DocumentHelper.parseText(msg);

			List resCode_list = resDoc
					.selectNodes("ufinterface/sendresult/resultcode");
			List resDes_list = resDoc
					.selectNodes("ufinterface/sendresult/resultdescription");
			// 从回执信息中获得错误信息详细描述
			String invaliddoc_value = null;
			returnMsg.clear();
			for (Object obj : resDes_list) {
				Element invaliddoc = (Element) obj;
				invaliddoc_value = invaliddoc.getText();
				System.out.println("返回信息:" + invaliddoc_value);
				// 将回执信息赋值给静态变量returnMsg
				returnMsg.add(invaliddoc_value);
			}
			// 从回执信息中获取错误信息编号
			List<String> resCodeList = new ArrayList<String>();
			for (Object obj : resCode_list) {
				Element resCode = (Element) obj;
				String resCode_value = resCode.getText();
				// System.out.println("失败信息:"+resCode_value);
				// 只有resCode编号为1时才是正常处理完毕，没有错误
				if (!resCode_value.equals("1")) {
					resCodeList.add(resCode_value);
				}
			}
			if (resCodeList.size() == 0) {
				if (app.getUrl().equals("qbTonc")) {
					out.println("OK");
					out.close();
				} else {
					if (!app.getSuccessMsg().equals(""))
						invaliddoc_value = app.getSuccessMsg();
					out.println("<html>\n");
					out.println("<head>\n");
					out.println("<script type=\"text/javascript\" language=\"JavaScript\" src=\"../js/title.js\"></script>\n");
					out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n");
					out.println("<script>");
					out.println(FunctionLib.gbToIso("alert('"
							+ invaliddoc_value + "')"));
					out.println("window.location='" + app.getUrl() + "'");
					out.println("</script>");
					out.println("</head>\n");
					out.println("  </html>\n");
					out.close();
				}
			} else {
				if (!app.getFailMsg().equals(""))
					invaliddoc_value = app.getFailMsg();
				out.println("<html>\n");
				out.println("<head>\n");
				out.println("<script type=\"text/javascript\" language=\"JavaScript\" src=\"../js/title.js\"></script>\n");
				out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n");
				out.println("<script>");
				out.println(FunctionLib.gbToIso("alert('" + invaliddoc_value
						+ "')"));
				out.println("window.history.go(-1);");
				out.println("</script>");
				out.println("</head>\n");
				out.println("  </html>\n");
				out.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			out.println("<html>\n");
			out.println("<head>\n");
			out.println("<script type=\"text/javascript\" language=\"JavaScript\" src=\"../js/title.js\"></script>\n");
			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n");
			out.println("<script>");
			out.println(FunctionLib.gbToIso("alert('提取数据时出错！')"));
			out.println("</script>");
			out.println("</head>\n");
			out.println("  </html>\n");
			out.close();
		}
	}
}
