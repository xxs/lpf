package com.tend.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DbConnection {
	private Connection dbConnection = null;
    private Statement selectPro = null; //用于 select 操作
    private Statement updatePro = null; //用于 update 操作
    private ResultSet dbResultSet = null; //操作 select 结果集
    
    private String driverName;//声明MySql驱动类
    private String dbHost;
    private String dbPort;
    private String dbName;
    private String dbUserName;
    private String dbPassword;
    private String enCoding; 
    /** *//**
     * 实例化DbConnection对象
     * @param host 数据库主机（IP）
     * @param port 数据库端口
     * @param dName 数据库名称
     * @param uName 用户名
     * @param password 用户密码
     */
    public DbConnection(String host, String port, String dName, String uName, String password)
    {
        driverName = "com.mysql.jdbc.Driver";
        dbHost = host;
        dbPort = port;
        dbName = dName;
        dbUserName = uName;
        dbPassword = password;
        enCoding = "?useUnicode=true&characterEncoding=gb2312&autoReconnect=true";
    }//end DbConnection(...)
    
    /** *//**
     * 连接数据库
     * @return 连接成功返回true，连接失败返回false
     */
    public boolean dbConnection()
    {
        StringBuilder urlTem = new StringBuilder();
        urlTem.append("jdbc:mysql://");
        urlTem.append(dbHost);
        urlTem.append(":");
        urlTem.append(dbPort);
        urlTem.append("/");
        urlTem.append(dbName);
        urlTem.append(enCoding);
        String url = urlTem.toString();
        try
        {
            Class.forName(driverName).newInstance();
            dbConnection = DriverManager.getConnection(url, dbUserName, dbPassword);
            return true;
        }catch(Exception e){
            System.err.println("数据库联接失败！");
            System.out.println("url = " + url);
            e.printStackTrace(); //得到详细的出错消息
            return false;
        }
    }// end dbConnection()
    
    /** *//**
     * 执行专门的select操作，注意：在selectSql中的字段和fields中的个数、名称要保持一致
     * @param selectSql 用于执行的select语句
     * @param fields 要选择的字段
     * @return 含有Map的ArrayList，一条记录形成一个Map
     */
    public ArrayList dbSelect(String selectSql, ArrayList fields)
    {
        ArrayList<Map> selectResult = new ArrayList<Map>();
        Map<String, String> recordInfo;
        try{
            selectPro = dbConnection.createStatement();//定义Statement对象
            dbResultSet = selectPro.executeQuery(selectSql);
            while(dbResultSet.next()){
                recordInfo = new HashMap<String, String>();
                for(int i = 0; i<fields.size(); ++i)
                    recordInfo.put((String)fields.get(i), dbResultSet.getString((String)fields.get(i)));
                selectResult.add(recordInfo);
            }
            dbResultSet.close(); //断开结果集
            selectPro.close(); //断开Statement对象
        }catch(Exception e){
            System.out.println("选择操作失败");
            System.out.println("Sql = " + selectSql);
            e.printStackTrace();
        }
        return selectResult;
    }//end dbSelect(...)
    
    /** *//**
     * 对数据库执行update，delete或insert操作
     * @param sql 要执行操作的SQL语句
     * @return 执行成功返回true，失败返回false
     */
    public boolean dbUpdate(String sql)
    {
        try
        {
            updatePro = dbConnection.createStatement(); //定义Statement对象
            updatePro.executeUpdate(sql);
            updatePro.close();// -------------关闭Statement对象
            return true;
        }catch(Exception err){
            System.out.println("数据库操作失败！");
            System.out.println("Sql = " + sql);
            err.printStackTrace();
            return false;
        }
    }//end dbUpdate(...)
    
    /** *//**
     * 关闭数据库连接
     * @return 成功返回true， 失败返回false
     */
    public boolean closeDatabase()
    {
        try{
            if(dbConnection != null)
                dbConnection.close();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }//end closeDatabase()
    
    public static void main(String[] args)
    {
        String dbHost = "localhost";
        String dbPort = "3336";
        String dbName = "TD_OA";
        String dbUserName = "root";
        String dbPassword = "myoa888";
        DbConnection conn = new DbConnection(dbHost, dbPort, dbName, dbUserName, dbPassword);
        boolean bool = conn.dbConnection();
        if(!bool)
            return;
        String selectSql = "select * from sms";
        ArrayList<String> fieldsList = new ArrayList<String>();
        fieldsList.add("user_name");
        fieldsList.add("ad");
        ArrayList<Map> userInfoList = conn.dbSelect(selectSql, fieldsList);
        int infoSize = userInfoList.size();
        String userName;
        String ad;
        if(infoSize == 0)
            System.out.println("没有选出符合条件的数据");
        else{
            for(int i = 0; i < infoSize; ++i){
                userName = (String)userInfoList.get(i).get("user_name");
                ad = (String)(((Map)userInfoList.get(i)).get("ad"));
                System.out.println("userName = " + userName + "  ad = " + ad);
            }
        }
        conn.closeDatabase();
    }//end main(...)

}//end calss DbConnection

