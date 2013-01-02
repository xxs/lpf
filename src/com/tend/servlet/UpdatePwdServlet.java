package com.tend.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htjs.srvapp.util.FunctionLib;
import net.htjs.srvapp.web.BaseBean;

public class UpdatePwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Connection conn = null;
	static Statement stmt = null;
	static ResultSet rs = null;

	private static String jiema(String str) throws UnsupportedEncodingException {
		return URLDecoder.decode(str, "GBK");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("GBK");
		String np = jiema(request.getParameter("np"));
		String u = jiema(request.getParameter("u"));
		String p = jiema(request.getParameter("p"));

		System.out.println("np=" + np);
		System.out.println("u=" + u);
		System.out.println("p=" + p);

		boolean userchecks = true;
		String username = "";
		String userpwd = "";

		Map map = new HashMap();
		Map jitMap = new HashMap();
		BaseBean bdao = new BaseBean("Login");
		List udata = null;
		map.put("u", u);
		map.put("p", p);
		udata = bdao.getList("testJITData", map);
		System.out.println("size" + udata.size());
		if (udata != null && udata.size() > 0) {
			System.out.println("hava more......");
			Iterator it = udata.iterator();
			while (it.hasNext()) {
				jitMap = (Map) it.next();
				username = (String) jitMap.get("USERNAME");
				userpwd = (String) jitMap.get("USERPWD");
			}
		}
		userchecks = username.equals(u) && userpwd.equals(p);
		if (!userchecks) {
			System.out.println("用户验证失败");
			response.getWriter().write("-100");// 用户验证失败
		} else {
			System.out.println("用户验证成功");
			if ("".equals(np) || np.length() <= 0 || "".equals(u)
					|| u.length() <= 0 || "".equals(p) || p.length() <= 0) {
				response.getWriter().write("-1");// 参数有误
			} else {
				System.out.println("参数有效性验证通过");
				String sql = "";
				sql = "modifyJITData";
				Vector insertSql1 = new Vector();
				insertSql1 = FunctionLib.split(sql, "##");
				map.put("np", np);
				map.put("username", username);
						try {
							bdao.execteBatch(insertSql1, map);
							System.out.println("更新密码成功");
							response.getWriter().write("0");//成功
						} catch (SQLException e) {
							System.out.println("更新密码异常");
							response.getWriter().write("-999");//未知错误
							e.printStackTrace();
						}
				

			}
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}