package com.tend.servlet;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDate {
	public String getDate() throws Exception{
		String time = "";
		try{
			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
			String today = sf.format(date);
			int hour = date.getHours();
			if(hour < 10){
				today += "0"+hour;
			}else{
				today += hour;
			}
			int minute = date.getMinutes();
			if(minute < 10){
				today += "0"+minute;
			}else{
				today += minute;
			}
			int second = date.getSeconds();
			if(second < 10){
				today += "0"+second;
			}else{
				today += second;
			}
			time = today;
		}catch(Exception e){
			e.printStackTrace();
		}
		return time;
	}
	
	public static void main(String[] args){
		GetDate da = new GetDate();
		try {
			da.getDate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
