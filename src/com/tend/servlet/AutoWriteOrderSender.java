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

public class AutoWriteOrderSender implements Runnable {

	String username = "";
	String pwd = "";
	String loadurl = "";
	String requesturl = "";
	String ncserver = "";
	Integer requesttime;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNcserver() {
		return ncserver;
	}

	public void setNcserver(String ncserver) {
		this.ncserver = ncserver;
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

	public String getRequesturl() {
		return requesturl;
	}

	public void setRequesturl(String requesturl) {
		this.requesturl = requesturl;
	}

	public Integer getRequesttime() {
		return requesttime;
	}

	public void setRequesttime(Integer requesttime) {
		this.requesttime = requesttime;
	}

	public AutoWriteOrderSender() {
		System.out.println("AutoWriteOrderSender 无参构造函数");
	}

	/**
	 * 自动执行的run方法
	 */
	public void run() {
		long lastTime;

		lastTime = (new Date()).getTime();
		long k;
		while (true) {
			k = (new Date()).getTime() - lastTime;
			if (k < -1000l) {
				lastTime = (new Date()).getTime();
				continue;
			}
			if (k > (long) requesttime) {
				System.out.println("检测方法");
				try {
					makeOrder();
					sendOrderState();
				} catch (Exception e) {
					System.out.println("检测方法出现异常");
					e.printStackTrace();
				}
				lastTime = (new Date()).getTime();
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

	/**
	 * 生成订单
	 * 
	 * @throws Exception
	 */
	public void makeOrder() throws Exception {
		System.out.println("生成订单........................");
		String cpid = "";
		float sl = 0;
		String djh = "";
		String rq = "";
		String khid = "";
		String bz = "";
		Map map = new HashMap();
		Map cpMap = new HashMap();
		Vector modifySql = new Vector();
		BaseBean bdao = new BaseBean("Login");
		List udata = null;
		String sql = "";
		sql = "modifyMOrder";
		modifySql = FunctionLib.split(sql, "##");
		udata = bdao.getList("getMOrderByState", map);
		if (udata != null && udata.size() > 0) {
			Iterator it = udata.iterator();
			while (it.hasNext()) {
				cpMap = (Map) it.next();
				djh = (String) cpMap.get("DJH");
				khid = (String) cpMap.get("KHID");
				System.out.println("djh为：" + djh);
				System.out.println("客户ID为：" + khid);
				// 调用http的接口，将需要添加的数据，添加到远程的移动商品平台中，确认返回值为0，确认插入成功，在自己创建的表中，添加上更新后的数据。
				// 1.构建url请求路径
				String url = requesturl + "?djh=" + bianma(djh) + "&khid="
						+ bianma(khid)+"&ncserver=" + bianma(ncserver);
				System.out.println("待处理的订单请求生成订单的url：" + url);
				// 请求并接收返回值
				String result = getHttp(url);
				// String result = "0";
				if (result.trim().equals("0")) {
					map = new HashMap();
					map.put("djh", djh);
					map.put("state", "1");
					bdao.execteBatch(modifySql, map);
					System.out.println("NC订单已生成，不会重新生成单据号为："+djh+"的订单");
					
					
					bz = "ok";
					String adviceUrl = this.loadurl + "OrderReturn/?djh="
							+ bianma(djh) + "&bz=" + bianma(bz) + "&u="
							+ username + "&p=" + pwd;
					System.out.println("订单回送通知的adviceUrl：" + adviceUrl);
					String adviceresult = getHttp(adviceUrl);
//					String adviceresult = "0";
					if (adviceresult.trim().equals("0")) {
						// 拼接参数对象(map)
						System.out.println("成功");
						map = new HashMap();
						map.put("djh", djh);
						map.put("state", "2");
						bdao.execteBatch(modifySql, map);
						System.out.println("订单状态已通知杰亦特，单据号为："+djh+"的订单不会再发送状态回送通知了");
					}
					if (adviceresult.trim().equals("-1")) {
						System.out.println("失败，参数有错误");
					}
					if (adviceresult.trim().equals("-100")) {
						System.out.println("失败，身份验证失败");
					}
					if (adviceresult.trim().equals("-999")) {
						System.out.println("失败，未知错误");
					}
				}
				if (result.trim().equals("-1")) {
					System.out.println("生成订单失败！");
				}
			}
		}
	}
	/**
	 * 发送订单状态通知
	 * @throws Exception
	 */
	public void sendOrderState() throws Exception {   
		System.out.println("发送订单通知........................");
		String djh = "";
		String bz = "";
		Map map = new HashMap();
		Map cpMap = new HashMap();
		Vector modifySql = new Vector();
		BaseBean bdao = new BaseBean("Login");
		List udata = null;
		String sql = "modifyMOrder";
		modifySql = FunctionLib.split(sql, "##");
		udata = bdao.getList("getMOrderSender", map);
		if (udata != null && udata.size() > 0) {
			Iterator it = udata.iterator();
			while (it.hasNext()) {
				cpMap = (Map) it.next();
				djh = (String) cpMap.get("DJH");
				System.out.println("djh为：" + djh);
				bz = "ok";
					String adviceUrl = this.loadurl + "OrderReturn/?djh="
							+ bianma(djh) + "&bz=" + bianma(bz) + "&u="
							+ username + "&p=" + pwd;
				System.out.println("订单回送通知的adviceUrl：" + adviceUrl);
				String adviceresult = getHttp(adviceUrl);
				if (adviceresult.trim().equals("0")) {
						// 拼接参数对象(map)
						System.out.println("已收到通知");
						map = new HashMap();
						map.put("djh", djh);
						map.put("state", "2");
						bdao.execteBatch(modifySql, map);
						System.out.println("订单状态已通知杰亦特，单据号为："+djh+"的订单不会再发送状态回送通知了");
				}
			}
		}
	}

}
