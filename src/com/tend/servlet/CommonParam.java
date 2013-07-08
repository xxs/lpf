package com.tend.servlet;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class CommonParam {
	private String propertyFileName;
	private ResourceBundle resourceBundle;

	public CommonParam() {
		propertyFileName = "com/tend/servlet/nctobq";
		resourceBundle = ResourceBundle.getBundle(propertyFileName);
	}

	public String getString(String key) {
		if (key == null || key.equals("") || key.equals("null")) {
			return "";
		}
		String result = "";
		try {
			result = resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args) {
		 CommonParam commonParam = new CommonParam();
	     System.out.println(commonParam.getString("111"));
	}
}
