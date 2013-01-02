package com.tend.servlet;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Text {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filename = "/ab/cd/ef.txt";
		System.out.println(filename.substring(filename.lastIndexOf("/")+1));
		Date date = new Date();
		System.out.println(date.getTime()+"**"+date.getDate());
		System.out.println(date.getYear()+date.getMonth()+"*"+date.getDay());
	}
	
	public static Date getNowDate() {

	       Date currentTime = new Date();

	       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	       String dateString = formatter.format(currentTime);

	       ParsePosition pos = new ParsePosition(8);

	       Date currentTime_2 = formatter.parse(dateString, pos);

	       return currentTime_2;

	} 

	/**

	       * ��ȡ����ʱ��

	       * 

	       * @return���ض�ʱ���ʽ yyyy-MM-dd

	       */

	public static Date getNowDateShort() {

	       Date currentTime = new Date();

	       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	       String dateString = formatter.format(currentTime);

	       ParsePosition pos = new ParsePosition(8);

	       Date currentTime_2 = formatter.parse(dateString, pos);

	       return currentTime_2;

	} 

	/**

	       * ��ȡ����ʱ��

	       * 

	       * @return�����ַ�����ʽ yyyy-MM-dd HH:mm:ss

	       */

	public static String getStringDate() {

	       Date currentTime = new Date();

	       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	       String dateString = formatter.format(currentTime);

	       return dateString;

	} 

	/**

	       * ��ȡ����ʱ��

	       * 

	       * @return ���ض�ʱ���ַ�����ʽyyyy-MM-dd

	       */

	public static String getStringDateShort() {

	       Date currentTime = new Date();

	       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	       String dateString = formatter.format(currentTime);

	       return dateString;

	} 

	/**

	       * ��ȡʱ�� Сʱ:��;�� HH:mm:ss

	       * 

	       * @return

	       */

	public static String getTimeShort() {

	       SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

	       Date currentTime = new Date();

	       String dateString = formatter.format(currentTime);

	       return dateString;

	} 

	/**

	       * ����ʱ���ʽ�ַ���ת��Ϊʱ�� yyyy-MM-dd HH:mm:ss

	       * 

	       * @param strDate

	       * @return

	       */

	public static Date strToDateLong(String strDate) {

	       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	       ParsePosition pos = new ParsePosition(0);

	       Date strtodate = formatter.parse(strDate, pos);

	       return strtodate;

	} 

	/**

	       * ����ʱ���ʽʱ��ת��Ϊ�ַ��� yyyy-MM-dd HH:mm:ss

	       * 

	       * @param dateDate

	       * @return

	       */

	public static String dateToStrLong(java.util.Date dateDate) {

	       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	       String dateString = formatter.format(dateDate);

	       return dateString;

	} 

	/**

	       * ����ʱ���ʽʱ��ת��Ϊ�ַ��� yyyy-MM-dd

	       * 

	       * @param dateDate

	       * @param k

	       * @return

	       */

	public static String dateToStr(java.util.Date dateDate) {

	       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	       String dateString = formatter.format(dateDate);

	       return dateString;

	} 

	/**

	       * ����ʱ���ʽ�ַ���ת��Ϊʱ�� yyyy-MM-dd 

	       * 

	       * @param strDate

	       * @return

	       */

	public static Date strToDate(String strDate) {

	       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	       ParsePosition pos = new ParsePosition(0);

	       Date strtodate = formatter.parse(strDate, pos);

	       return strtodate;

	} 

	/**

	       * �õ�����ʱ��

	       * 

	       * @return

	       */

	public static Date getNow() {

	       Date currentTime = new Date();

	       return currentTime;

	} 

	/**

	       * ��ȡһ�����е����һ��

	       * 

	       * @param day

	       * @return

	       */

	public static Date getLastDate(long day) {

	       Date date = new Date();

	       long date_3_hm = date.getTime() - 3600000 * 34 * day;

	       Date date_3_hm_date = new Date(date_3_hm);

	       return date_3_hm_date;

	} 

	/**

	       * �õ�����ʱ��

	       * 

	       * @return �ַ��� yyyyMMdd HHmmss

	       */

	public static String getStringToday() {

	       Date currentTime = new Date();

	       SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");

	       String dateString = formatter.format(currentTime);

	       return dateString;

	} 

	// ���㵱�����һ��,�����ַ���

	public String getDefaultDay(){  

	      String str = "";

	      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");    

	      Calendar lastDate = Calendar.getInstance();

	      lastDate.set(Calendar.DATE,1);//��Ϊ��ǰ�µ�1��

	      lastDate.add(Calendar.MONTH,1);//��һ���£���Ϊ���µ�1��

	      lastDate.add(Calendar.DATE,-1);//��ȥһ�죬��Ϊ�������һ��

	   

	      str=sdf.format(lastDate.getTime());

	      return str;  

	}

	/**

	       * �õ�����Сʱ

	       */

	public static String getHour() {

	       Date currentTime = new Date();

	       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	       String dateString = formatter.format(currentTime);

	       String hour;

	       hour = dateString.substring(11, 13);

	       return hour;

	} 

	/**

	       * �õ����ڷ���

	       * 

	       * @return

	       */

	public static String getTime() {

	       Date currentTime = new Date();

	       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	       String dateString = formatter.format(currentTime);

	       String min;

	       min = dateString.substring(14, 16);

	       return min;

	} 

	/**

	       * �����û������ʱ���ʾ��ʽ�����ص�ǰʱ��ĸ�ʽ �����yyyyMMdd��ע����ĸy���ܴ�д��

	       * 

	       * @param sformat

	       *                 yyyyMMddhhmmss

	       * @return

	       */

	public static String getUserDate(String sformat) {

	       Date currentTime = new Date();

	       SimpleDateFormat formatter = new SimpleDateFormat(sformat);

	       String dateString = formatter.format(currentTime);

	       return dateString;

	} 

	/**

	       * ����Сʱʱ���Ĳ�ֵ,���뱣֤����ʱ�䶼��"HH:MM"�ĸ�ʽ�������ַ��͵ķ���

	       */

	public static String getTwoHour(String st1, String st2) {

	       String[] kk = null;

	       String[] jj = null;

	       kk = st1.split(":");

	       jj = st2.split(":");

	       if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))

	        return "0";

	       else {

	        double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;

	        double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;

	        if ((y - u) > 0)

	         return y - u + "";

	        else

	         return "0";

	       }

	} 

	/**

	       * �õ��������ڼ�ļ������

	       */

	public static String getTwoDay(String sj1, String sj2) {

	       SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");

	       long day = 0;

	       try {

	        java.util.Date date = myFormatter.parse(sj1);

	        java.util.Date mydate = myFormatter.parse(sj2);

	        day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);

	       } catch (Exception e) {

	        return "";

	       }

	       return day + "";

	} 

	/**

	       * ʱ��ǰ�ƻ���Ʒ���,����JJ��ʾ����.

	       */

	public static String getPreTime(String sj1, String jj) {

	       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	       String mydate1 = "";

	       try {

	        Date date1 = format.parse(sj1);

	        long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;

	        date1.setTime(Time * 1000);

	        mydate1 = format.format(date1);

	       } catch (Exception e) {

	       }

	       return mydate1;

	} 

	/**

	       * �õ�һ��ʱ���Ӻ��ǰ�Ƽ����ʱ��,nowdateΪʱ��,delayΪǰ�ƻ���ӵ�����

	       */

	public static String getNextDay(String nowdate, String delay) {

	       try{

	       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	       String mdate = "";

	       Date d = strToDate(nowdate);

	       long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;

	       d.setTime(myTime * 1000);

	       mdate = format.format(d);

	       return mdate;

	       }catch(Exception e){

	        return "";

	       }

	} 

	/**

	       * �ж��Ƿ�����

	       * 

	       * @param ddate

	       * @return

	       */

	public static boolean isLeapYear(String ddate) { 

	       /**

	        * ��ϸ��ƣ� 1.��400���������꣬���� 2.���ܱ�4������������ 3.�ܱ�4����ͬʱ���ܱ�100������������

	        * 3.�ܱ�4����ͬʱ�ܱ�100������������

	        */

	       Date d = strToDate(ddate);

	       GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();

	       gc.setTime(d);

	       int year = gc.get(Calendar.YEAR);

	       if ((year % 400) == 0)

	        return true;

	       else if ((year % 4) == 0) {

	        if ((year % 100) == 0)

	         return false;

	        else

	         return true;

	       } else

	        return false;

	} 

	/**

	       * ��������ʱ���ʽ 26 Apr 2006

	       * 

	       * @param str

	       * @return

	       */

	public static String getEDate(String str) {

	       SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	       ParsePosition pos = new ParsePosition(0);

	       Date strtodate = formatter.parse(str, pos);

	       String j = strtodate.toString();

	       String[] k = j.split(" ");

	       return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);

	} 

	/**

	       * ��ȡһ���µ����һ��

	       * 

	       * @param dat

	       * @return

	       */

	public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd

	       String str = dat.substring(0, 8);

	       String month = dat.substring(5, 7);

	       int mon = Integer.parseInt(month);

	       if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {

	        str += "31";

	       } else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {

	        str += "30";

	       } else {

	        if (isLeapYear(dat)) {

	         str += "29";

	        } else {

	         str += "28";

	        }

	       }

	       return str;

	} 

	/**

	       * �ж϶���ʱ���Ƿ���ͬһ����

	       * 

	       * @param date1

	       * @param date2

	       * @return

	       */

	public static boolean isSameWeekDates(Date date1, Date date2) {

	       Calendar cal1 = Calendar.getInstance();

	       Calendar cal2 = Calendar.getInstance();

	       cal1.setTime(date1);

	       cal2.setTime(date2);

	       int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);

	       if (0 == subYear) {

	        if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))

	         return true;

	       } else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {

	        // ���12�µ����һ�ܺ�������һ�ܵĻ������һ�ܼ���������ĵ�һ��

	        if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))

	         return true;

	       } else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {

	        if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))

	         return true;

	       }

	       return false;

	} 

	/**

	       * ����������,���õ���ǰʱ�����ڵ�����ǵڼ���

	       * 

	       * @return

	       */

	public static String getSeqWeek() {

	       Calendar c = Calendar.getInstance(Locale.CHINA);

	       String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));

	       if (week.length() == 1)

	        week = "0" + week;

	       String year = Integer.toString(c.get(Calendar.YEAR));

	       return year + week;

	} 


}
