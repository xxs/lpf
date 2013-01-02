/**
 * 系统常用工具类
 */
package com.nc.comm;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class NewAppUtil {

	private NewAppUtil() {

	}

	/**
	 * MD5生成摘要
	 * 
	 * @param s
	 * @return
	 */
	public static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Class applicationClass(String className)
			throws ClassNotFoundException {
		return applicationClass(className, null);
	}

	public static Class applicationClass(String className,
			ClassLoader classLoader) throws ClassNotFoundException {
		if (classLoader == null) {
			// Look up the class loader to be used
			classLoader = Thread.currentThread().getContextClassLoader();

			if (classLoader == null) {
				classLoader = NewAppUtil.class.getClassLoader();
			}
		}

		// Attempt to load the specified class
		return (classLoader.loadClass(className));
	}

	public static Object applicationInstance(String className)
			throws ClassNotFoundException, IllegalAccessException,
			InstantiationException {
		return applicationInstance(className, null);
	}

	public static Object applicationInstance(String className,
			ClassLoader classLoader) throws ClassNotFoundException,
			IllegalAccessException, InstantiationException {
		return (applicationClass(className, classLoader).newInstance());
	}
	
	
	public static Map convertReportParam(HttpServletRequest request) {
		Map orgParam = request.getParameterMap();
		Map param = new HashMap();
		Set keys = orgParam.keySet();
		for (Iterator it = keys.iterator(); it.hasNext();) {
			String key = (String) it.next();
			Object obj = orgParam.get(key);
			if (obj instanceof Object[]) {
				param.put(key, ((Object[]) obj)[0]);
			} else {
				param.put(key, obj);
			}
		}
		return param;
	}

}
