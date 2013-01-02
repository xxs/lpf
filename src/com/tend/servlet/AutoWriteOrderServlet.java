package com.tend.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
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

public class AutoWriteOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static String jiema(String str) throws UnsupportedEncodingException {
		if (null == str) {
			return null;
		} else {
			return URLDecoder.decode(str, "GBK");
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("GBK");
		response.setContentType("text/html;   charset=GBK ");
		response.setHeader("Cache-Control", "no-cache");
		System.out.println("开始获取参数");
		String cpid = request.getParameter("cpid");
		String sl = request.getParameter("sl");
		String djh = request.getParameter("djh");
		String rq = request.getParameter("rq");
		String khid = request.getParameter("khid");
		String bz = request.getParameter("bz");
		String u = request.getParameter("u");
		String p = request.getParameter("p");
		System.out.println("完成获取参数");
		System.out.println("cpid" + cpid);
		System.out.println("sl" + sl);
		System.out.println("djh" + djh);
		System.out.println("rq" + rq);
		System.out.println("khid" + khid);
		System.out.println("bz" + bz);
		System.out.println("u" + u);
		System.out.println("p" + p);
		System.out.println("完成输出参数");
		cpid = jiema(request.getParameter("cpid"));
		sl = jiema(request.getParameter("sl"));
		djh = jiema(request.getParameter("djh"));
		rq = jiema(request.getParameter("rq"));
		khid = jiema(request.getParameter("khid"));
		bz = jiema(request.getParameter("bz"));
		u = jiema(request.getParameter("u"));
		p = jiema(request.getParameter("p"));
		System.out.println("完成解码参数");
		// 判断参数是否完整
		if (null == cpid || null == sl || null == djh || null == rq
				|| null == khid) {
			response.getWriter().write("-1");// 参数有误(缺少参数)
		} else if (null == u || null == p) {
			response.getWriter().write("-100");// 用户名或密码为空
		} else {
			// 截取订单中的商品和数量
			String[] ids = cpid.split(",");
			String[] nums = sl.split(",");

			int count = 0;// 查询用户ID行数
			int count2 = 0;// 查询产品ID行数
			int count3 = 0;// 查询单据号返回行数
			int count4 = 0;// 查询是否可以询到价格
			boolean insertchecks = true;
			boolean userchecks = true;
			String username = "";
			String userpwd = "";
			int results = 0;// 产品ID验证结果
			int priceresults = 0;// 询价验证结果
			int resultsnum = 0;// 产品数量验证结果
			String resStr = "";// 未能询到加的产品ID

			Map map = new HashMap();
			Map ddMap = new HashMap();
			BaseBean bdao = new BaseBean("Login");
			List udata = null;
			udata = bdao.getList("getJITData", map);
			System.out.println("size" + udata.size());
			if (udata != null && udata.size() > 0) {
				Iterator it = udata.iterator();
				while (it.hasNext()) {
					ddMap = (Map) it.next();
					username = (String) ddMap.get("USERNAME");
					userpwd = (String) ddMap.get("USERPWD");
				}
			}
			userchecks = username.equals(u) && userpwd.equals(p);
			if (!userchecks) {
				System.out.println("用户验证失败");
				response.getWriter().write("-100");// 用户验证失败
			} else {
				System.out.println("用户验证成功");
				if ("".equals(djh) || djh.length() <= 0 || "".equals(rq)
						|| rq.length() <= 0 || "".equals(khid)
						|| khid.length() <= 0 || ids.length != nums.length) {
					response.getWriter().write("-1");// 参数有误
				} else {
					map.put("khid", khid);
					udata = bdao.getList("testKHID", map);
					if (udata != null && udata.size() > 0) {
						Iterator it = udata.iterator();
						while (it.hasNext()) {
							Map ki = (Map) it.next();
							count = Integer.parseInt(ki.get("CON").toString());
						}
					}
					if (count <= 0) {
						System.out.println("客户ID验证失败");
						response.getWriter().write("-3");// 客户ID（终端ID）不存在
						insertchecks = false;
					} else {
						System.out.println("客户ID验证通过");
						map.put("djh", djh);
						udata = bdao.getList("testDJH", map);
						if (udata != null && udata.size() > 0) {
							Iterator it = udata.iterator();
							while (it.hasNext()) {
								Map kii = (Map) it.next();
								count3 = Integer.parseInt(kii.get("CON")
										.toString());
							}
						}
						if (count3 > 0) {
							System.out.println("单据号有重复");
							response.getWriter().write("-4");// 单据号是否重复
							insertchecks = false;
						} else {
							System.out.println("单据号验证通过");
							for (int i = 0; i < ids.length; i++) {
								String spID = ids[i];// 开始验证产品编号
								map.put("spID", spID);
								map.put("khid", khid);
								udata = bdao.getList("testCPID", map);// 验证存在和是否可以询到价
								if (udata != null && udata.size() > 0) {
									Iterator it = udata.iterator();
									while (it.hasNext()) {
										Map cps = (Map) it.next();
										count2 = Integer.parseInt(cps
												.get("CON").toString());
									}
								}
								udata = bdao.getList("testExistPrice", map);// 验证存在和是否可以询到价
								if (udata != null && udata.size() > 0) {
									Iterator it = udata.iterator();
									while (it.hasNext()) {
										Map cps = (Map) it.next();
										count4 = Integer.parseInt(cps.get(
												"PRICECOUNT").toString());
									}
								}
								if (count2 <= 0) {
									results = results + 1;
								}
								if (count4 != 1) {
									priceresults = priceresults + 1;
									resStr = "," + ids[i];
								}
								String num = nums[i];// 开始验证产品数量
								float checknum = 0;
								try {
									checknum = Float.parseFloat(num);
								} catch (Exception e) {
									System.out.println("订单中产品数量有误");
									e.printStackTrace();
								}
								if (checknum <= 0) {
									resultsnum = resultsnum + 1;
								}
							}

							if (results > 0) {
								System.out.println("产品ID验证失败，含有未知编号");
								insertchecks = false;
								response.getWriter().write("-2");// 产品ID不存在
							} else if (priceresults > 0) {
								System.out.println("产品ID验证失败，含有未能询到价格的产品ID:"
										+ resStr);
								insertchecks = false;
								response.getWriter().write("-5");// 产品ID不存在
							} else if (resultsnum > 0) {
								System.out.println("订单中产品数量有误");
								insertchecks = false;
								response.getWriter().write("-1");// 产品数量不正确
							} else {
								System.out.println("产品ID验证通过");

								// 所有的验证通过
								String sql = "";
								sql = "insertDDCP";
								Vector insertSql1 = new Vector();
								insertSql1 = FunctionLib.split(sql, "##");
								for (int i = 0; i < ids.length; i++) {
									String spID = ids[i];
									String num = nums[i];
									// 执行插入产品的sql语句
									map.put("spID", spID);
									map.put("num", num);
									map.put("djh", djh);
									try {
										bdao.execteBatch(insertSql1, map);
									} catch (SQLException e) {
										System.out.println("添加订单商品信息异常");
										response.getWriter().write("-999");// 成功
										e.printStackTrace();
									}
								}
								System.out.println("添加订单商品信息成功");
								insertchecks = true;
							}
						}
					}
					if (insertchecks) {
						// 执行插入订单信息的sql语句
						String sql = "";
						sql = "insertDDKH";
						Vector insertSql = new Vector();
						insertSql = FunctionLib.split(sql, "##");
						map.put("djh", djh);
						map.put("rq", rq);
						map.put("khid", khid);
						map.put("bz", bz);
						map.put("zt", "0");
						try {
							bdao.execteBatch(insertSql, map);
							System.out.println("添加订单用户信息成功");
							response.getWriter().write("0");// 成功
						} catch (SQLException e) {
							System.out.println("添加订单用户信息异常");
							response.getWriter().write("-999");// 未知错误
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}