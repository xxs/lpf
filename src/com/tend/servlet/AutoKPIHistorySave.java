package com.tend.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.apache.commons.httpclient.params.HttpMethodParams;

public class AutoKPIHistorySave implements Runnable {

	String username = "";
	String pwd = "";
	String loadurl = "";
	Integer monthnum;
	Integer kpitime;

	public Integer getKpitime() {
		return kpitime;
	}

	public void setKpitime(Integer kpitime) {
		this.kpitime = kpitime;
	}

	public String getLoadurl() {
		return loadurl;
	}

	public void setLoadurl(String loadurl) {
		this.loadurl = loadurl;
	}

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

	public Integer getMonthnum() {
		return monthnum;
	}

	public void setMonthnum(Integer monthnum) {
		this.monthnum = monthnum;
	}

	public AutoKPIHistorySave() {
		System.out.println("AutoKPIHistoryHttpSender 无参构造函数");
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
			if (k > (long) kpitime) {
				System.out.println("发送KPI历史销售的方法");
				try {
					saveKPIHistory();
				} catch (Exception e) {
					System.out.println("发送KPI历史销售方法出现异常");
					e.printStackTrace();
				}
				lastTime = (new Date()).getTime();
			}
			try {
				// Thread.sleep(500000L);
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
	 * 发送KPI历史销售数据
	 * 
	 * @throws Exception
	 */
	public void saveKPIHistory() throws Exception {
		System.out.println("KPI历史销售开始统计数据");
		String zdid = "";
		String cpid = "";
		int xsnd = 0;
		int xsyf = 0;
		float sl = 0;
		Date date = new Date();
		Map map = new HashMap();
		Map cpMap = new HashMap();
		Vector insertSql = new Vector();
		BaseBean bdao = new BaseBean("Login");
		List udata = null;
		String sql =  "saveKPIHistory";
		insertSql = FunctionLib.split(sql, "##");
		System.out.println("begin loop.........");
		System.out.println("循环总此时为：(此参数是在web.xml中配置的):" + monthnum);
		for (int i = 0; i < monthnum; i++) {
			Date tempDate = getPreMonthTime(i, date);
			xsnd = Integer.parseInt(formatDate(tempDate, "yyyy"));
			xsyf = tempDate.getMonth() + 1;
			map.put("begintempmonth", formatDate(tempDate, "yyyy-MM") + "-01");
			map.put("endtempmonth", formatDate(tempDate, "yyyy-MM") + "-31");
			udata = bdao.getList("getKPIHistory", map);
			System.out.println("第" + i + "次for循环");
			if (udata != null && udata.size() > 0) {
				Iterator it = udata.iterator();
				int n = 1;
				while (it.hasNext()) {
					System.out.println("第" + n + "次while循环");
					n = n + 1;
					cpMap = (Map) it.next();
					zdid = (String) cpMap.get("CCUSTOMERID");
					cpid = (String) cpMap.get("CINVBASDOCID");
					sl = Float.parseFloat(cpMap.get("SNUM").toString());
					map = new HashMap();
					map.put("zdid", zdid);
					map.put("cpid", cpid);
					map.put("sl", sl);
					map.put("xsnd", xsnd);
					map.put("xsyf", xsyf);
					bdao.execteBatch(insertSql, map);
				}
			}
		}
	}

	/**
	 * 把一个日期，按照某种格式 格式化输出
	 * 
	 * @param date
	 *            日期对象
	 * @param pattern
	 *            格式模型
	 * @return 返回字符串类型
	 */
	public String formatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**
	 * 得到前几个月的现在 利用GregorianCalendar类的set方法来实现
	 * 
	 * @param num
	 * @param date
	 * @return
	 */
	public Date getPreMonthTime(int num, Date date) {
		GregorianCalendar gregorianCal = new GregorianCalendar();
		gregorianCal
				.set(Calendar.MONTH, gregorianCal.get(Calendar.MONTH) - num);
		return gregorianCal.getTime();
	}
}
