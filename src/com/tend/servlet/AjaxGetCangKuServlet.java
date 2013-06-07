package com.tend.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htjs.srvapp.web.BaseBean;

/**
 * 根据库存组织获取仓库列表
 * 
 * @author xxs
 */
public class AjaxGetCangKuServlet extends HttpServlet {

	private static final long serialVersionUID = 2272236837023694175L;

	public AjaxGetCangKuServlet() {

	}

	public void init() throws ServletException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("text/xml; charset=UTF-8");
		// 以下两句为取消在本地的缓存
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		PrintWriter out = response.getWriter();
		String pk_calbody = request.getParameter("pk_calbody");
		String row = request.getParameter("row");
		String pk_stordoc = "";
		String storcode = "";
		String storname = "";
		StringBuilder resultStr = new StringBuilder();
		resultStr.append(row).append(";");
		try {
			Map<String, String> map = new HashMap<String, String>();
			BaseBean bdao = new BaseBean("Login");
			List udata = null;
			map.put("form_pk_calbody", pk_calbody);
			udata = bdao.getList("sqlcangku", map);
			Map ddMap = new HashMap();

			if (udata != null && udata.size() > 0) {
				Iterator it = udata.iterator();
				while (it.hasNext()) {
					ddMap = (Map) it.next();
					pk_stordoc = (String) ddMap.get("PK_STORDOC");
					storcode = (String) ddMap.get("STORCODE");
					storname = (String) ddMap.get("STORNAME");
					resultStr.append(pk_stordoc).append(",").append(storcode)
							.append(",").append(storname).append(";");
				}
			}
			System.out.println("resultStr:"+resultStr);
			out.write(resultStr.toString());// 注意这里向jsp输出的流，在script中的截获方法
			out.close();
		} catch (Exception ex) {

		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}
