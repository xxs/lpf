package com.tend.servlet;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

/**
 * 生成销售订单
 * @author Administrator
 */
public class MakeSaleOrderToNcServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(MakeSaleOrderToNcServlet.class);
	private static List<String> returnMsg = new ArrayList<String>();
	String toncsaleorder = "";// 通信NC的url地址

	public MakeSaleOrderToNcServlet() {

	}

	public void init() throws ServletException {
		System.out.println("生成销售订单程序初始化参数!");
		ServletConfig config = getServletConfig();
		System.out.println(config.getInitParameter("toncsaleorder"));
		if (config.getInitParameter("toncsaleorder") != null) {
			toncsaleorder = config.getInitParameter("toncsaleorder");
		}
		System.out.println("生成销售订单程序初始化参数完成!");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		//设置request的请求编码方式
		request.setCharacterEncoding("GB2312");
		response.setContentType("text/html; charset=UTF-8");
		// 以下两句为取消在本地的缓存
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		System.out.println("执行了生成NC订单的操作，此操作来自分支客户生成订单的模块!!!");
		PrintWriter out = response.getWriter();
		String qryDoc = "";// 访问mso的文件路径
		
		System.out.println("ooooooooooooo:"+request.getParameter("addrname"));
//		String[] rowlist = request.getParameterValues("rowlist");
//		for (int i = 0; i < rowlist.length; i++) {
//			System.out.println("第"+i+"行的行号为："+rowlist[i]);
//		}
//		List<String> list = new ArrayList<String>(Arrays.asList(rowlist));
//		System.out.println(list);
		if (request.getPathInfo() == null) {
			qryDoc = getServletConfig().getServletContext().getRealPath(
					request.getServletPath());
		} else {
			qryDoc = request.getPathTranslated();
		}
		System.out.println("mso文件路径：" + qryDoc);
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
			//将数组rowlist存入context
			//context.put("rowlist", list);
			//System.out.println("list in context");
			ToNcEngine mainMod = new ToNcEngine(request, response, app,
					context, getServletConfig());
			// 处理用户请求
//			Vector vsqls = app.getSqls();
//			System.out.println("size:" + vsqls.size());
//			if (app.getSqlType().equals("select")) {
//				List ldata = null;
//				for (int i = 0; i < vsqls.size(); i++) {
//					ldata = new ArrayList();
//					// ldata = mainMod.getData(vsqls.elementAt(i).toString(),app.getEscape());
//					// context.put(vsqls.elementAt(i).toString(), ldata);
//				}
//				context.put("CGI_url", request.getRequestURL().toString());
//			}
			
			Template t = ve.getTemplate(app.getTemplate());
			StringWriter sw = new StringWriter();
			t.merge(context, sw);
			System.out.println("生成后的Vm生成后的文件内容：" + sw.toString());
			URL realURL = new URL(toncsaleorder);
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
			System.out.println("原生Document："+resDoc.toString());
			List resCode_list = resDoc.selectNodes("ufinterface/sendresult/resultcode");
			List resDes_list = resDoc.selectNodes("ufinterface/sendresult/resultdescription");
			// 从回执信息中获得错误信息详细描述
			String invaliddoc_value = null;
			returnMsg.clear();
			for (Object obj : resDes_list) {
				Element invaliddoc = (Element) obj;
				//System.out.println("未处理前："+invaliddoc.getText());
				invaliddoc_value = invaliddoc.getText().replace("\n", "");
				//invaliddoc_value = invaliddoc_value.replace("\n", "");
				System.out.println("返回信息:" + invaliddoc_value);
				// 将回执信息赋值给静态变量returnMsg
				returnMsg.add(invaliddoc_value);
			}
			invaliddoc_value = invaliddoc_value.replace("\n", "");
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
				out.print("<script language=\"javascript\" >");
				out.print("window.alert(\"订单保存成功!\");");
				out.print("window.close();");
				out.print("</script>");
			} else {
				out.print("<script language=\"javascript\" >");
				out.print("window.alert(\"订单保存失败失败!"+invaliddoc_value+"\");");
				out.print("window.close();");
				out.print("</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.print("<script language=\"javascript\" >");
			out.print("window.alert(\"出现异常,订单保存失败!\");");
			out.print("window.close();");
			out.print("</script>");
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}
