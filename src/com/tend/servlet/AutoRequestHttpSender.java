package com.tend.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.htjs.srvapp.util.FunctionLib;
import net.htjs.srvapp.web.BaseBean;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class AutoRequestHttpSender implements Runnable {

	String username = "";
	String pwd = "";
	String loadurl = "";
	Integer time;
	Integer monthnum;
	Integer kpitime;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getLoadurl() {
		return loadurl;
	}

	public void setLoadurl(String loadurl) {
		this.loadurl = loadurl;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Integer getMonthnum() {
		return monthnum;
	}

	public void setMonthnum(Integer monthnum) {
		this.monthnum = monthnum;
	}

	public Integer getKpitime() {
		return kpitime;
	}

	public void setKpitime(Integer kpitime) {
		this.kpitime = kpitime;
	}

	public AutoRequestHttpSender() {
		System.out.println("AutoRequestHttpSender 无参构造函数");
	}

	/**
	 * 自动执行的run方法
	 */
	public void run() {
		System.out.println("开始执行RUN方法");
		long lastTime;

		lastTime = (new Date()).getTime();
		long k;
		while (true) {
			k = (new Date()).getTime() - lastTime;
			if (k < -1000l) {
				lastTime = (new Date()).getTime();
				continue;
			}
			if (k > (long) time) {
				System.out.println("检测方法");
				try {
					addCPXX();
					updateCPXX();
					addYHXX();
					updateYHXX();
					addZDXX();
					updateZDXX();
					sendKPIHistory();// KPI历史销售数据
				} catch (Exception e) {
					System.out.println("检测方法出现异常");
					e.printStackTrace();
				}
				lastTime = (new Date()).getTime();
			}
			try {
				// Thread.sleep(240000);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * get方式
	 * 
	 * @param param1
	 * @param param2
	 * @return
	 */
	public static String getHttp(String url) {
		String responseMsg = "";

		// 1.构造HttpClient的实例
		HttpClient httpClient = new HttpClient();

		// 2.创建GetMethod的实例
		GetMethod getMethod = new GetMethod(url);

		// 使用系统系统的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());

		try {
			// 3.执行getMethod,调用http接口
			httpClient.executeMethod(getMethod);

			// 4.读取内容
			byte[] responseBody = getMethod.getResponseBody();

			// 5.处理返回的内容
			responseMsg = new String(responseBody);
			System.out.println("检测的http返回值为" + responseMsg);

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 6.释放连接
			getMethod.releaseConnection();
		}
		return responseMsg;
	}

	/**
	 * post方式
	 * 
	 * @param param1
	 * @param param2
	 * @return
	 */
	public static String postHttp(String url) {
		String responseMsg = "";

		// 1.构造HttpClient的实例
		HttpClient httpClient = new HttpClient();

		httpClient.getParams().setContentCharset("GBK");

		// 2.构造PostMethod的实例
		PostMethod postMethod = new PostMethod(url);

		// 3.把参数值放入到PostMethod对象中
		// 方式1：
		/*
		 * NameValuePair[] data = { new NameValuePair("param1", param1), new
		 * NameValuePair("param2", param2) }; postMethod.setRequestBody(data);
		 */

		// 方式2：
		// postMethod.addParameter("param1", param1);
		// postMethod.addParameter("param2", param2);

		try {
			// 4.执行postMethod,调用http接口
			httpClient.executeMethod(postMethod);// 200

			// 5.读取内容
			responseMsg = postMethod.getResponseBodyAsString().trim();

			// 6.处理返回的内容

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 7.释放连接
			postMethod.releaseConnection();
		}
		return responseMsg;
	}

	private static String bianma(String str)
			throws UnsupportedEncodingException {
		if (null == str) {
			str = "";
		} else {
			str.replace(" ", "");
		}
		return URLEncoder.encode(str, "GBK");
	}

	// 解决值为空不能存储数据库的问题
	private static void putFull(Map map, String key, String value) {
		if (null == value || value.isEmpty()) {
			map.put(key, "");
		} else {
			map.put(key, value);
		}
	}

	/**
	 * 添加用户表信息
	 * 
	 * @throws Exception
	 */
	public void addYHXX() throws Exception {
		System.out.println("用户检测........................添加");
		String pk_psnbasdoc = "";
		String psnname = "";
		String city = "";
		String zhiwei = "销售代表";
		int sex = 0;
		int usertype;
		String amcode = "";// 助记码

		Map map = new HashMap();
		Map cpMap = new HashMap();
		Vector insertSql = new Vector();
		BaseBean bdao = new BaseBean("Login");
		List udata = null;
		String sql = "";
		sql = "insertYH";
		insertSql = FunctionLib.split(sql, "##");
		udata = bdao.getList("getYHdetector", map);
		if (udata != null && udata.size() > 0) {
			Iterator it = udata.iterator();
			while (it.hasNext()) {
				cpMap = (Map) it.next();
				pk_psnbasdoc = (String) cpMap.get("PK_PSNBASDOC");
				psnname = (String) cpMap.get("PSNNAME");
				String cityold = (String) cpMap.get("UNITNAME");
				amcode = (String) cpMap.get("AMCODE");
				if (null != cityold && !"".equals(cityold)) {
					city = cityold.substring(0, 2);
				}
				String xingbie = (String) cpMap.get("SEX");
				if ("男".equals(xingbie)) {
					sex = 1;
				} else if ("女".equals(xingbie)) {
					sex = 2;
				} else {
					sex = 0;
				}
				String clerkflag1 = (String) cpMap.get("CLERKFLAG");
				if ("Y".equals(clerkflag1)) {
					usertype = 0;
				} else if ("N".equals(clerkflag1)) {
					usertype = 2;
				} else {
					usertype = 2;
				}
				// 拼接参数对象(map)
				map = new HashMap();
				putFull(map, "pk_psnbasdoc", pk_psnbasdoc);
				putFull(map, "psnname", psnname);
				putFull(map, "city", cityold);
				putFull(map, "zhiwei", zhiwei);
				putFull(map, "xingbie", xingbie);
				putFull(map, "clerkflag1", clerkflag1);
				putFull(map, "zjm", amcode);
				// 调用http的接口，将需要添加的数据，添加到远程的移动商品平台中，确认返回值为0，确认插入成功，在自己创建的表中，添加上更新后的数据。
				// 1.构建url请求路径
				String url = loadurl + "User/?action=1&yhid=" + pk_psnbasdoc
						+ "&xm=" + bianma(psnname) + "&sscs=" + bianma(city)
						+ "&zjm=" + bianma(amcode) + "&zw=" + bianma(zhiwei);
				if (sex != 0) {
					url = url + "&xb=" + sex + "&yhlx=" + usertype + "&u="
							+ bianma(username) + "&p=" + pwd + "";
				} else {
					if (usertype != -1) {
						url = url + "&yhlx=" + 2 + "&u=" + bianma(username)
								+ "&p=" + pwd + "";
					}
				}
				System.out.println("添加用户信息的http请求:" + url);
				String url1 = loadurl + "User/?action=1&yhid=" + pk_psnbasdoc
						+ "&xm=" + psnname + "&sscs=" + city + "&zw=" + zhiwei
						+ "&xb=" + sex + "&yhlx=" + usertype + "&u=" + username
						+ "&p=" + pwd + "";
				System.out.println("编码前的http请求：" + url1);
				// 请求并接收返回值
				String result = getHttp(url);
				if (result.trim().equals("0")) {
					System.out.println("成功");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("1")) {
					System.out.println("成功，城市被默认为NULL");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("2")) {
					System.out.println("成功，插入了新的职位");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("3")) {
					System.out.println("成功，插入了新的职位和城市被默认为NULL");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("-1")) {
					System.out.println("失败，参数有错误");
				}
				if (result.trim().equals("-3")) {
					System.out.println("失败，用户ID已存在");
				}
				if (result.trim().equals("-4")) {
					System.out.println("失败，用户ID不存在");
				}
				if (result.trim().equals("-100")) {
					System.out.println("失败，身份验证失败");
				}
				if (result.trim().equals("-999")) {
					System.out.println("失败，未知错误");
				}
			}
		}
	}

	/**
	 * 更新用户表信息
	 * 
	 * @throws Exception
	 * 
	 */
	public void updateYHXX() throws Exception {
		System.out.println("用户检测........................更新");
		String pk_psnbasdoc = "";
		String psnname = "";
		String city = "";
		String zhiwei = "销售代表";
		int sex;
		int usertype = 0;
		String amcode = "";// 助记码

		Map map = new HashMap();
		Map cpMap = new HashMap();
		Vector insertSql = new Vector();
		BaseBean bdao = new BaseBean("Login");
		List udata = null;
		String sql = "";
		// map.put("sql", sql);
		sql = "modifyYH";
		insertSql = FunctionLib.split(sql, "##");
		udata = bdao.getList("updateYHdetector", map);
		if (udata != null && udata.size() > 0) {
			Iterator it = udata.iterator();
			while (it.hasNext()) {
				cpMap = (Map) it.next();
				pk_psnbasdoc = (String) cpMap.get("PK_PSNBASDOC");
				psnname = (String) cpMap.get("PSNNAME");
				String cityold = (String) cpMap.get("UNITNAME");
				amcode = (String) cpMap.get("AMCODE");
				if (null != cityold && !"".equals(cityold)) {
					city = cityold.substring(0, 2);
				}
				String xingbie = (String) cpMap.get("SEX");
				if ("男".equals(xingbie)) {
					sex = 1;
				} else if ("女".equals(xingbie)) {
					sex = 2;
				} else {
					sex = 0;
				}
				String clerkflag1 = (String) cpMap.get("CLERKFLAG");
				if ("Y".equals(clerkflag1)) {
					usertype = 0;
				} else if ("N".equals(clerkflag1)) {
					usertype = 2;
				} else {
					usertype = 2;
				}
				// 拼接参数对象(map)
				map = new HashMap();
				putFull(map, "pk_psnbasdoc", pk_psnbasdoc);
				putFull(map, "psnname", psnname);
				putFull(map, "city", cityold);
				putFull(map, "zhiwei", zhiwei);
				putFull(map, "xingbie", xingbie);
				putFull(map, "clerkflag1", clerkflag1);
				putFull(map, "zjm", amcode);
				// 调用http的接口，将需要更新的数据，更新到远程的移动商品平台中，确认返回值为0，确认更新成功，在自己创建的表中，更新修改的数据。
				// 1.构建url请求路径
				String url = loadurl + "User/?action=3&yhid=" + pk_psnbasdoc
						+ "&xm=" + bianma(psnname) + "&sscs=" + bianma(city)
						+ "&zjm=" + bianma(amcode) + "&zw=" + bianma(zhiwei);
				if (sex != 0) {
					url = url + "&xb=" + sex + "&yhlx=" + usertype + "&u="
							+ bianma(username) + "&p=" + pwd + "";
				} else {
					if (usertype != -1) {
						url = url + "&yhlx=" + 2 + "&u=" + bianma(username)
								+ "&p=" + pwd + "";
					}
				}
				System.out.println("请求为：" + url);
				// 请求并接收返回值
				String result = getHttp(url);
				if (result.trim().equals("0")) {
					System.out.println("成功");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("1")) {
					System.out.println("成功,插入了新城市");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("2")) {
					System.out.println("成功，插入了新的职位");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("3")) {
					System.out.println("成功，插入了新的城市和新的职位");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("-1")) {
					System.out.println("失败，参数有错误");
				}
				if (result.trim().equals("-3")) {
					System.out.println("失败，用户ID已存在");
				}
				if (result.trim().equals("-4")) {
					System.out.println("失败，用户ID不存在");
				}
				if (result.trim().equals("-100")) {
					System.out.println("失败，身份验证失败");
				}
				if (result.trim().equals("-999")) {
					System.out.println("失败，未知错误");
				}
			}
		}
	}

	/**
	 * 添加终端表信息
	 * 
	 * @throws Exception
	 */
	public void addZDXX() throws Exception {
		System.out.println("终端检测........................添加");
		String zdid = "";
		String qd = "";
		String zqd = "";
		String ssfgs = "";
		String zdmc = "";
		String zdbm = "";
		String zddz = "";
		String lxrdh = "";
		String lxrsj = "";
		String lxr = "";
		String zjm = "";
		int popdb_type = 0;

		Map map = new HashMap();
		Map cpMap = new HashMap();
		Vector insertSql = new Vector();
		BaseBean bdao = new BaseBean("Login");
		List udata = null;
		String sql = "";
		sql = "insertZDXX";
		insertSql = FunctionLib.split(sql, "##");
		udata = bdao.getList("getZDXXdetector", map);
		if (udata != null && udata.size() > 0) {
			Iterator it = udata.iterator();
			while (it.hasNext()) {
				cpMap = (Map) it.next();
				zdid = (String) cpMap.get("PK_CUMANDOC");
				qd = (String) cpMap.get("FIRSTDOCNAME");
				zqd = (String) cpMap.get("LASTDOCNAME");
				ssfgs = (String) cpMap.get("UNITNAME");
				zdmc = (String) cpMap.get("CUSTNAME");
				zdbm = (String) cpMap.get("CUSTCODE");
				zddz = (String) cpMap.get("CONADDR");
				lxrdh = (String) cpMap.get("PHONE1");
				lxrsj = (String) cpMap.get("MOBILEPHONE1");
				lxr = (String) cpMap.get("LINKMAN1");
				zjm = (String) cpMap.get("MNECODE");
				if ("KA".equals(qd) || "KA".equals(zqd) || "地采".equals(qd)
						|| "地采".equals(zqd)) {
					popdb_type = 0;
				}
				if ("现金客户".equals(qd) || "现金客户".equals(zqd) || "经销商".equals(qd)
						|| "经销商".equals(zqd)) {
					popdb_type = 1;
				}
				// 拼接参数对象(map)
				map = new HashMap();
				putFull(map, "zdid", zdid);
				putFull(map, "zdmc", zdmc);
				putFull(map, "zdbm", zdbm);
				putFull(map, "ssfgs", ssfgs);
				putFull(map, "qd", qd);
				putFull(map, "zqd", zqd);
				putFull(map, "zddz", zddz);
				putFull(map, "lxrdh", lxrdh);
				putFull(map, "lxrsj", lxrsj);
				putFull(map, "lxr", lxr);
				putFull(map, "zjm", zjm);

				// 调用http的接口，将需要添加的数据，添加到远程的移动商品平台中，确认返回值为0，确认插入成功，在自己创建的表中，添加上更新后的数据。
				// 1.构建url请求路径
				String url = loadurl + "POPDB/?action=1&zdid=" + zdid + "&qd="
						+ bianma(qd) + "&zqd=" + bianma(zqd) + "&ssfgs="
						+ bianma(ssfgs) + "&zdmc=" + bianma(zdmc) + "&zbdm="
						+ bianma(zdbm) + "&zddz=" + bianma(zddz) + "&zjm="
						+ bianma(zjm) + "&lxrdh=" + bianma(lxrdh) + "&lxrsj="
						+ bianma(lxrsj) + "&popdb_type=" + popdb_type + "&lxr="
						+ bianma(lxr) + "&u=" + bianma(username) + "&p=" + pwd
						+ "";
				System.out.println("添加终端信息的http请求:" + url);
				// 请求并接收返回值
				String result = getHttp(url);
				// String result = "0";
				if (result.trim().equals("0")) {
					System.out.println("成功");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("1")) {
					System.out.println("成功，插入了新的渠道");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("2")) {
					System.out.println("成功，插入了新的子渠道");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("3")) {
					System.out.println("成功，插入了新的渠道和子渠道");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("-1")) {
					System.out.println("失败，参数有误");
				}
				if (result.trim().equals("-2")) {
					System.out.println("失败，终端ID已存在");
				}
				if (result.trim().equals("-3")) {
					System.out.println("失败，终端ID不存在");
				}
				if (result.trim().equals("-100")) {
					System.out.println("失败，身份验证失败");
				}
				if (result.trim().equals("-999")) {
					System.out.println("失败，未知错误");
				}
			}
		}

	}

	/**
	 * 更新终端表信息
	 * 
	 * @throws Exception
	 * 
	 */
	public void updateZDXX() throws Exception {
		System.out.println("终端检测........................更新");
		String zdid = "";
		String qd = "";
		String zqd = "";
		String ssfgs = "";
		String zdmc = "";
		String zdbm = "";
		String zddz = "";
		String lxrdh = "";
		String lxrsj = "";
		String lxr = "";
		String zjm = "";

		Map map = new HashMap();
		Map cpMap = new HashMap();
		Vector insertSql = new Vector();
		BaseBean bdao = new BaseBean("Login");
		List udata = null;
		String sql = "";
		// map.put("sql", sql);
		sql = "modifyZDXX";
		insertSql = FunctionLib.split(sql, "##");
		udata = bdao.getList("updateZDXXdetector", map);
		System.out.println("size" + udata.size());
		if (udata != null && udata.size() > 0) {

			Iterator it = udata.iterator();
			while (it.hasNext()) {
				cpMap = (Map) it.next();
				zdid = (String) cpMap.get("PK_CUMANDOC");
				qd = (String) cpMap.get("FIRSTDOCNAME");
				zqd = (String) cpMap.get("LASTDOCNAME");
				ssfgs = (String) cpMap.get("UNITNAME");
				zdmc = (String) cpMap.get("CUSTNAME");
				zdbm = (String) cpMap.get("CUSTCODE");
				zddz = (String) cpMap.get("CONADDR");
				lxrdh = (String) cpMap.get("PHONE1");
				lxrsj = (String) cpMap.get("MOBILEPHONE1");
				lxr = (String) cpMap.get("LINKMAN1");
				zjm = (String) cpMap.get("MNECODE");

				// 拼接参数对象(map)
				System.out.println("zdid:" + zdid);
				System.out.println("qd:" + qd);
				System.out.println("zqd:" + zqd);
				System.out.println("ssfgs:" + ssfgs);
				System.out.println("zdmc:" + zdmc);
				System.out.println("zdbm:" + zdbm);
				System.out.println("zddz:" + zddz);
				System.out.println("lxrdh:" + lxrdh);
				System.out.println("lxrsj:" + lxrsj);
				System.out.println("lxr:" + lxr);
				map = new HashMap();
				putFull(map, "zdid", zdid);
				putFull(map, "zdmc", zdmc);
				putFull(map, "zdbm", zdbm);
				putFull(map, "ssfgs", ssfgs);
				putFull(map, "qd", qd);
				putFull(map, "zqd", zqd);
				putFull(map, "zddz", zddz);
				putFull(map, "lxrdh", lxrdh);
				putFull(map, "lxrsj", lxrsj);
				putFull(map, "lxr", lxr);
				putFull(map, "zjm", zjm);
				// 调用http的接口，将需要更新的数据，更新到远程的移动商品平台中，确认返回值为0，确认更新成功，在自己创建的表中，更新修改的数据。
				// 1.构建url请求路径
				String url = loadurl + "POPDB/?action=3&zdid=" + zdid + "&qd="
						+ bianma(qd) + "&zqd=" + bianma(zqd) + "&ssfgs="
						+ bianma(ssfgs) + "&zdmc=" + bianma(zdmc) + "&zbdm="
						+ bianma(zdbm) + "&zddz=" + bianma(zddz) + "&zjm="
						+ bianma(zjm) + "&lxrdh=" + bianma(lxrdh) + "&lxrsj="
						+ bianma(lxrsj) + "&lxr=" + bianma(lxr) + "&u="
						+ bianma(username) + "&p=" + pwd + "";
				// 请求并接收返回值
				String result = getHttp(url);
				// String result = "0";
				if (result.trim().equals("0")) {
					System.out.println("成功");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("1")) {
					System.out.println("成功，插入了新的渠道");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("2")) {
					System.out.println("成功，插入了新的子渠道");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("3")) {
					System.out.println("成功，插入了新的渠道和子渠道");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("-1")) {
					System.out.println("失败，参数有误");
				}
				if (result.trim().equals("-2")) {
					System.out.println("失败，终端ID已存在");
				}
				if (result.trim().equals("-3")) {
					System.out.println("失败，终端ID不存在");
				}
				if (result.trim().equals("-100")) {
					System.out.println("失败，身份验证失败");
				}
				if (result.trim().equals("-999")) {
					System.out.println("失败，未知错误");
				}
			}
		}

	}

	/**
	 * 添加产品表信息
	 * 
	 * @throws Exception
	 */
	public void addCPXX() throws Exception {
		System.out.println("产品检测........................添加");
		String invcode = "";
		String invname = "";
		String pk_invbasdoc = "";
		String invclassname = "";
		String flcodes = "";
		String invmnecode = "";

		Map map = new HashMap();
		Map cpMap = new HashMap();
		Vector insertSql = new Vector();
		BaseBean bdao = new BaseBean("Login");
		List udata = null;
		String sql = "";
		// map.put("sql", sql);
		sql = "insertCP";
		insertSql = FunctionLib.split(sql, "##");
		udata = bdao.getList("getCPdetector", map);
		System.out.println("size" + udata.size());
		if (udata != null && udata.size() > 0) {

			Iterator it = udata.iterator();
			while (it.hasNext()) {
				cpMap = (Map) it.next();
				invcode = (String) cpMap.get("INVCODE");
				invname = (String) cpMap.get("INVNAME");
				pk_invbasdoc = (String) cpMap.get("PK_INVBASDOC");
				invclassname = (String) cpMap.get("INVCLASSNAME");
				flcodes = (String) cpMap.get("INVCLASSCODE");
				String resultfl = getFL(flcodes);
				invmnecode = (String) cpMap.get("INVMNECODE");
				// 拼接参数对象(map)
				System.out.println("1" + invcode);
				System.out.println("2" + invname);
				System.out.println("3" + pk_invbasdoc);
				System.out.println("4" + invclassname);
				System.out.println("5" + flcodes);
				System.out.println("invmnecode" + invmnecode);
				map = new HashMap();
				putFull(map, "invname", invname);
				putFull(map, "pk_invbasdoc", pk_invbasdoc);
				putFull(map, "resultfl", resultfl);
				putFull(map, "invclassname", invclassname);
				putFull(map, "invcode", invcode);
				putFull(map, "flcodes", flcodes);
				putFull(map, "zjm", invmnecode);
				// 调用http的接口，将需要添加的数据，添加到远程的移动商品平台中，确认返回值为0，确认插入成功，在自己创建的表中，添加上更新后的数据。
				// 1.构建url请求路径
				String url = loadurl + "SKU/?action=1&cpmc=" + bianma(invname)
						+ "&cpid=" + pk_invbasdoc + "&lb=" + bianma(resultfl)
						+ "&zlb=" + bianma(invclassname) + "&cpbm="
						+ bianma(invcode) + "&zjm=" + bianma(invmnecode)
						+ "&u=" + bianma(username) + "&p=" + pwd + "";
				System.out.println("添加产品信息的http请求:" + url);
				// 请求并接收返回值
				String result = getHttp(url);
				// String result = "0";
				if (result.trim().equals("0")) {
					System.out.println("成功");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("1")) {
					System.out.println("成功，插入了新的品牌");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("2")) {
					System.out.println("成功，插入了新的子品牌");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("3")) {
					System.out.println("成功，插入了新的品牌和新的子品牌");
					bdao.execteBatch(insertSql, map);
				}
				if (result.trim().equals("-1")) {
					System.out.println("失败，参数有误");
				}
				if (result.trim().equals("-2")) {
					System.out.println("失败，产品ID已存在");
				}
				if (result.trim().equals("-3")) {
					System.out.println("失败，产品ID不存在");
				}
				if (result.trim().equals("-100")) {
					System.out.println("失败，身份验证失败");
				}
				if (result.trim().equals("-999")) {
					System.out.println("失败，未知错误");
				}
			}
		}

	}

	/**
	 * 截取分类等级，组成字符串
	 * 
	 * @param String
	 *            flcodes
	 */
	private static String getFL(String flcodes) {
		String querySql = "getCPByCode";
		String result = "";
		String newcode = "";
		String temp = "";

		Map map = new HashMap();
		Map cpMap = new HashMap();
		BaseBean bdao = new BaseBean("Login");
		List udata = null;

		for (int i = 0; i < flcodes.length(); i++) {
			newcode = flcodes.substring(0, i + 2);
			map.put("newcode", newcode);
			udata = bdao.getList(querySql, map);
			if (udata != null && udata.size() > 0) {

				Iterator it = udata.iterator();
				while (it.hasNext()) {
					cpMap = (Map) it.next();
					temp = (String) cpMap.get("INVCLASSNAME");
					result = result + "," + temp;
				}
				i = i + 1;
			}
		}
		result = result.substring(1, result.length());
		return result;
	}

	/**
	 * 更新产品表信息
	 * 
	 * @throws Exception
	 * 
	 */
	public void updateCPXX() throws Exception {
		System.out.println("产品检测........................更新");
		String invcode = "";
		String invname = "";
		String pk_invbasdoc = "";
		String invclassname = "";
		String flcodes = "";
		String invmnecode = "";

		Map map = new HashMap();
		Map cpMap = new HashMap();
		Vector updateSql = new Vector();
		BaseBean bdao = new BaseBean("Login");
		List udata = null;
		String sql = "";
		// map.put("sql", sql);
		sql = "modifyCP";
		updateSql = FunctionLib.split(sql, "##");
		udata = bdao.getList("updateCPdetector", map);
		if (udata != null && udata.size() > 0) {

			Iterator it = udata.iterator();
			while (it.hasNext()) {
				cpMap = (Map) it.next();
				invcode = (String) cpMap.get("INVCODE");
				invname = (String) cpMap.get("INVNAME");
				pk_invbasdoc = (String) cpMap.get("PK_INVBASDOC");
				invclassname = (String) cpMap.get("INVCLASSNAME");
				flcodes = (String) cpMap.get("INVCLASSCODE");
				String resultfl = getFL(flcodes);
				invmnecode = (String) cpMap.get("INVMNECODE");
				map = new HashMap();
				putFull(map, "invname", invname);
				putFull(map, "pk_invbasdoc", pk_invbasdoc);
				putFull(map, "resultfl", resultfl);
				putFull(map, "invclassname", invclassname);
				putFull(map, "invcode", invcode);
				putFull(map, "flcodes", flcodes);
				putFull(map, "zjm", invmnecode);
				// 调用http的接口，将需要更新的数据，更新到远程的移动商品平台中，确认返回值为0，确认更新成功，在自己创建的表中，更新修改的数据。
				// 1.构建url请求路径
				String url = loadurl + "SKU/?action=3&cpmc=" + bianma(invname)
						+ "&cpid=" + pk_invbasdoc + "&lb=" + bianma(resultfl)
						+ "&zlb=" + bianma(invclassname) + "&cpbm="
						+ bianma(invcode) + "&zjm=" + bianma(invmnecode)
						+ "&u=" + bianma(username) + "&p=" + pwd + "";
				// 请求并接收返回值
				String result = getHttp(url);
				if (result.trim().equals("0")) {
					System.out.println("成功");
					bdao.execteBatch(updateSql, map);
				}
				if (result.trim().equals("1")) {
					System.out.println("成功，插入了新的品牌");
					bdao.execteBatch(updateSql, map);
				}
				if (result.trim().equals("2")) {
					System.out.println("成功，插入了新的子品牌");
					bdao.execteBatch(updateSql, map);
				}
				if (result.trim().equals("3")) {
					System.out.println("成功，插入了新的品牌和新的子品牌");
					bdao.execteBatch(updateSql, map);
				}
				if (result.trim().equals("-1")) {
					System.out.println("失败，参数有误");
				}
				if (result.trim().equals("-2")) {
					System.out.println("失败，产品ID已存在");
				}
				if (result.trim().equals("-3")) {
					System.out.println("失败，产品ID不存在");
				}
				if (result.trim().equals("-100")) {
					System.out.println("失败，身份验证失败");
				}
				if (result.trim().equals("-999")) {
					System.out.println("失败，未知错误");
				}
			}
		}
	}

	/**
	 * 发送KPI历史销售数据
	 * 
	 * @throws Exception
	 */
	public void sendKPIHistory() throws Exception {
		System.out.println("KPI历史销售开始统计数据");
		String zdid = "";
		String cpid = "";
		int xsnd = 0;
		int xsyf = 0;
		float sl = 0;

		Map map = new HashMap();
		Map cpMap = new HashMap();
		Vector insertSql = new Vector();
		BaseBean bdao = new BaseBean("Login");
		List udata = null;
		String sql = "delKPIHistory";
		insertSql = FunctionLib.split(sql, "##");
		udata = bdao.getList("getKPIs", map);
		if (udata != null && udata.size() > 0) {
			Iterator it = udata.iterator();
			while (it.hasNext()) {
				cpMap = (Map) it.next();
				zdid = (String) cpMap.get("ZDID");
				cpid = (String) cpMap.get("CPID");
				sl = Float.parseFloat(cpMap.get("SL").toString());
				xsnd = Integer.parseInt(cpMap.get("XSND").toString());
				xsyf = Integer.parseInt(cpMap.get("XSYF").toString());
				map = new HashMap();
				map.put("zdid", zdid);
				map.put("cpid", cpid);
				map.put("sl", sl);
				map.put("xsnd", xsnd);
				map.put("xsyf", xsyf);
				// 调用http的接口，将需要添加的数据，添加到远程的移动商品平台中，确认返回值为0.
				// 1.构建url请求路径
				String url = loadurl + "History/?zdid=" + zdid + "&cpid="
						+ bianma(cpid) + "&xsnd=" + xsnd + "&xsyf=" + xsyf
						+ "&sl=" + sl + "&u=" + bianma(username) + "&p=" + pwd
						+ "";
				System.out.println("添加用户信息的http请求:" + url);
				String url1 = loadurl + "History/?zdid=" + zdid + "&cpid="
						+ cpid + "&xsnd=" + xsnd + "&xsyf=" + xsyf + "&sl="
						+ sl + "&u=" + username + "&p=" + pwd + "";
				System.out.println("编码前的http请求：" + url1);
				// 请求并接收返回值
				String result = getHttp(url);
				if (result.trim().equals("0")) {
					System.out.println("成功");
					bdao.execteBatch(insertSql, map);
					System.out.println("清除成功");
				}
				if (result.trim().equals("-1")) {
					System.out.println("失败，参数有错误");
				}
				if (result.trim().equals("-2")) {
					System.out.println("失败，终端ID不存在");
				}
				if (result.trim().equals("-3")) {
					System.out.println("失败，产品ID不存在");
				}
				if (result.trim().equals("-100")) {
					System.out.println("失败，身份验证失败");
				}
				if (result.trim().equals("-999")) {
					System.out.println("失败，未知错误");
				}
			}
		}
	}

}
