package com.synear.sale;

import net.htjs.srvapp.util.*;
import net.htjs.srvapp.web.BaseBean;

import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import java.net.HttpURLConnection;
import java.net.URL;

public class OrderSender implements Runnable {

	private boolean flag;
	private int time;
	private boolean validate;
	private String toncurl;

	public OrderSender() {
		time = 1000;
		flag = true;
		validate = true;
	}

	private int handleOrder() {
		try {
			Map map = new HashMap();
			Map membersMap = new HashMap();
			Vector editSql = new Vector();
			BaseBean bdao = new BaseBean("Login");
			List udata = null;
			String sql = "";
			map.put("sql", sql);
			sql = "updateBySql";
			editSql = FunctionLib.split(sql, "##");
			udata = bdao.getList("selectBySql", map);
			if (udata != null) {
				if (udata.size() == 0) {
					return 2;
				}
				Iterator it = udata.iterator();
				while (it.hasNext()) {
					membersMap = (Map) it.next();
					map = new HashMap();
					map.put("pk", membersMap.get("PK_PREORDER").toString());
					map.put("spk", membersMap.get("PK_CALBODY").toString());
					bdao.execteBatch(editSql, map);
				}
				// System.out.println("审核订单！"+membersMap.get("PK_PREORDER").toString());
				return 1;
			} else {
				System.out.println("没有抢报！");
				return 2;
			}
		} catch (Exception ex) {
			System.out.println("审核时出现错误！");
			ex.printStackTrace();
			return 0;
		}
	}

	private int handleOrderNew() {
		try {
			Map map = new HashMap();
			Map membersMap = new HashMap();
			Vector editSql = new Vector();
			BaseBean bdao = new BaseBean("Login");
			List udata = null;
			String sql = "";
			// map.put("sql", sql);
			sql = "updateBySql";
			editSql = FunctionLib.split(sql, "##");
			udata = bdao.getList("selectBySql", map);
			if (udata != null) {
				if (udata.size() == 0) {
					return 2;
				}
				Iterator it = udata.iterator();
				while (it.hasNext()) {
					membersMap = (Map) it.next();
					map = new HashMap();
					StringBuffer sb = new StringBuffer();
					sb.append("pk=").append(
							membersMap.get("PK_PREORDER").toString());
					sb.append("&").append("spk=")
							.append(membersMap.get("PK_CALBODY").toString());
					sb.append("&").append("billcls=")
							.append(membersMap.get("BILLCLS").toString());

					map.put("form_pk", membersMap.get("PK_PREORDER").toString());
					map.put("form_spk", membersMap.get("PK_CALBODY").toString());
					map.put("form_billcls", membersMap.get("BILLCLS")
							.toString());
					List mdata = null;
					if (membersMap.get("BILLCLS").toString().equals("Q")) {
						mdata = bdao.getList("getToNcBill2", map);
						if (mdata != null) {
							if (mdata.size() > 0) {
								BufferedOutputStream outb = null;
								InputStream in = null;
								String msg = null;
								URL realURL = new URL(toncurl + "?"
										+ sb.toString());
								HttpURLConnection connection = (HttpURLConnection) realURL
										.openConnection();
								connection.setRequestProperty("Content-type",
										"text/html");
								connection.setRequestMethod("POST");
								connection.connect();
								/*
								 * BufferedReader br = new BufferedReader(new
								 * InputStreamReader
								 * (connection.getInputStream())); String line ;
								 * String result =""; while( (line
								 * =br.readLine()) != null ){ result +=line; }
								 * System.out.println(result);
								 */
								InputStream br = new DataInputStream(
										connection.getInputStream());
								byte[] b = new byte[br.available()];
								br.read(b);
								String result = new String(b, "UTF-8");
								System.out.println(result);
								// br.close();
								if (result.trim().equals("OK")) {
									System.out.println("向NC中写入数据成功！");
									bdao.execteBatch(editSql, map);
								} else {
									System.out.println("向NC中写入数据时出错！");
								}
							} else {
								System.out.println(membersMap
										.get("PK_PREORDER").toString()
										+ "订单0满足！");
								bdao.execteBatch(editSql, map);
							}
						} else {
							System.out.println(membersMap.get("PK_PREORDER")
									.toString() + "订单0满足！");
							bdao.execteBatch(editSql, map);
						}
					} else {
						BufferedOutputStream outb = null;
						InputStream in = null;
						String msg = null;
						URL realURL = new URL(toncurl + "?" + sb.toString());
						HttpURLConnection connection = (HttpURLConnection) realURL
								.openConnection();
						connection.setRequestProperty("Content-type",
								"text/html");
						connection.setRequestMethod("POST");
						connection.connect();
						InputStream br = new DataInputStream(
								connection.getInputStream());
						byte[] b = new byte[br.available()];
						br.read(b);
						String result = new String(b, "UTF-8");
						if (result.trim().equals("OK")) {
							System.out.println("上报数据成功！");
							bdao.execteBatch(editSql, map);
						} else {
							System.out.println("上报数据时出错！");
						}
					}
				}
				// System.out.println("审核订单！"+membersMap.get("PK_PREORDER").toString());
				return 1;
			} else {
				System.out.println("没有抢报或上报数据！");
				return 2;
			}
		} catch (Exception ex) {
			System.out.println("审核时出现错误！");
			ex.printStackTrace();
			return 0;
		}
	}

	public static String getCurrentDateTime(String sFormat) {
		String sResult = null;
		Date dNow = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
		sResult = formatter.format(dNow);
		return sResult;
	}

	public static String getCurrentDateTime2() {
		Calendar C = new GregorianCalendar();
		return C.get(11) + ":" + C.get(12);
	}

	public void run() {
		long lastTime;
		System.out.println("保存前一次发送的时间！");

		lastTime = (new Date()).getTime();
		long k;
		int m = 0;
		while (true) {
			k = (new Date()).getTime() - lastTime;
			// System.out.println(" 时间间隔k"+k);
			if (k < -1000l) {
				lastTime = (new Date()).getTime();
				continue; /* Loop/switch isn't completed */
			}
			if (k > (long) time) {
				System.out.println(" 开始审核订单！");
				m = handleOrderNew();
				if (m == 0) {
					System.out.println(" 审核出错！");
				} else if (m == 1) {
					System.out.println(" 审核完毕！");
				} else {
					System.out.println(" 没有抢报或上报数据！");
				}
				lastTime = (new Date()).getTime();
			}
			try {
				Thread.sleep(3000L);
			} catch (Exception e) {
			}
		}
	}

	public void setTime(int minute) {
		time = minute * 2 * 1000;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public void setToncurl(String tonc) {
		this.toncurl = tonc;
	}

	public void stop() {
		flag = false;
	}
}
