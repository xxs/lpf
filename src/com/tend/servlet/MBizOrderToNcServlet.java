package com.tend.servlet;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htjs.srvapp.util.FunctionLib;
import net.htjs.srvapp.web.ToNcEngine;
import net.htjs.srvapp.web.WebApp;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class MBizOrderToNcServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(MBizOrderToNcServlet.class);
	private static List<String> returnMsg = new ArrayList<String>();
	public MBizOrderToNcServlet() {
	}

	public void init() throws ServletException {
//		ServletConfig config = getServletConfig();
//		System.out.println("server1.....................:" + server + "...");
//		if (server == null){
//			server = "http://100.100.1.141:9080/service/XChangeServlet?account=test882";
//		}
//		System.out.println("server2:" + server + "...");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String qryDoc = "";
		String djh = "";
		String khid = "";
		String ncserver = "";
		request.setCharacterEncoding("GBK");
		System.out.println("000000:"+request.getParameter("ncserver"));
		try {
			djh = request.getParameter("djh") != null ? request.getParameter(
					"djh").toString() : "";
			khid = request.getParameter("khid") != null ? request.getParameter(
					"khid").toString() : "";
			ncserver = request.getParameter("ncserver") != null ? request.getParameter(
					"ncserver").toString() : "";
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("单据号为：" + djh);
		System.out.println("客户ID为：" + khid);
		System.out.println("NC交互路径为：" + ncserver);

		// qryDoc = getServletConfig().getServletContext().getRealPath("/")+"synear\\makeorder.nc";
		if (request.getPathInfo() == null) {
			qryDoc = getServletConfig().getServletContext().getRealPath(
					request.getServletPath());
		} else {
			qryDoc = request.getPathTranslated();
		}
		System.out.println("qryDoc的值为：" + qryDoc);
		try {
			System.out.println(FunctionLib.openFile(qryDoc));
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
			System.out.println("size:" + vsqls.size());
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
			System.out.println("ccccccccc");
			URL realURL = new URL(ncserver);
			System.out.println("7777777"+ncserver);
			HttpURLConnection connection = (HttpURLConnection) realURL
					.openConnection();
			System.out.println("gggggggg");
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
			System.out.println("qqqqqqqqq");
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
			System.out.println("ttttttt");
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
					System.out.println("成功");
					response.getWriter().write("0"); // 成功
				} else {
					if (!app.getSuccessMsg().equals(""))
						invaliddoc_value = app.getSuccessMsg();
					System.out.println("成功2");
				}
			} else {
				if (!app.getFailMsg().equals(""))
					System.out.println("mmmmmmm");
					invaliddoc_value = app.getFailMsg();
				response.getWriter().write("-1"); // 创建的结果
				System.out.println("失败1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write("-999"); // 出现错误
			System.out.println("失败，-999");
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}
